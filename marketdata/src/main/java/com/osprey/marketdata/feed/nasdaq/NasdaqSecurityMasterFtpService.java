package com.osprey.marketdata.feed.nasdaq;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.osprey.marketdata.feed.ISecurityMasterService;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDatatFeedConnectionException;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.constants.Exchange;
import com.osprey.securitymaster.constants.InstrumentType;

public class NasdaqSecurityMasterFtpService implements ISecurityMasterService {

	final static Logger logger = LogManager.getLogger(NasdaqSecurityMasterFtpService.class);

	@Value("${nasdaq.security.master.ftp.domain}")
	private String nasdaqDomain;

	@Value("${nasdaq.security.master.ftp.user}")
	private String user;

	@Value("${nasdaq.security.master.ftp.password}")
	private String password;

	@Value("${nasdaq.security.master.ftp.dir}")
	private String nasdaqSymbolsDir;

	@Value("${nasdaq.security.master.ftp.listed.file}")
	private String listedFileName;

	@Value("${nasdaq.security.master.ftp.external.listed.file}")
	private String externalListedFileName;

	@Override
	public Set<Security> fetchSecurityMaster() {
		Set<Security> securities = new HashSet<>();

		List<String> lines = ftpPull(listedFileName);
		securities.addAll(parseNasdaqListedSecurity(lines));

		lines = ftpPull(externalListedFileName);
		securities.addAll(parseExternalListedSecurity(lines));
		
		filter(securities);

		return securities;
	}

	private void filter(Set<Security> securities) {
		// TODO filter out everything other than common stock & ETFs. 
		
	}

	public List<String> ftpPull(String file) {
		FTPClient ftp = null;
		List<String> lines = null;

		try {

			ftp = new FTPClient();
			ftp.connect(nasdaqDomain);
			ftp.login(user, password);

			int reply = ftp.getReplyCode();
			// FTPReply stores a set of constants for FTP reply codes.
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new MarketDatatFeedConnectionException();
			}

			// enter passive mode
			ftp.enterLocalPassiveMode();

			// change current directory
			ftp.changeWorkingDirectory(nasdaqSymbolsDir);

			logger.info("Current directory NASDAQ FTP directory {}", ftp.printWorkingDirectory());

			InputStream is = null;

			try {
				is = ftp.retrieveFileStream(file);
				lines = IOUtils.readLines(is, Charset.forName("UTF-8"));
			} finally {
				is.close();
			}

		} catch (IOException e) {
			logger.error("Failed to obtain Security Master Data.", e);
			throw new MarketDataIOException(e);
		} finally {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				logger.error("Failed to disconnect.", e);
				throw new MarketDataIOException(e);
			}
		}

		return lines;
	}

	private Set<Security> parseExternalListedSecurity(List<String> list) {
		// ACT Symbol|Security Name|Exchange|CQS Symbol|ETF|Round Lot Size|Test
		// Issue|NASDAQ Symbol
		// A|Agilent Technologies, Inc. Common Stock|N|A|N|100|N|A

		list.remove(0);
		Set<Security> parsedSecurities = new HashSet<>(list.size());

		ZonedDateTime now = ZonedDateTime.now();

		String[] split;
		int line = 1;
		Security sec;
		for (String raw : list) {
			split = raw.split("\\|");

			if (split.length != 8) {
				logger.warn("line {} has an unxpected length of {} ", new Object[] { line, split.length });
				continue;
			}

			// Using CQS symbology
			sec = process(split[3], split[1], split[6], split[5], split[4], "N", Exchange.fromCode(split[2]), now);

			if (sec != null) {
				parsedSecurities.add(sec);
			}

			++line;
		}

		return parsedSecurities;
	}

	private Set<Security> parseNasdaqListedSecurity(List<String> list) {
		// Symbol|Security Name|Market Category|Test Issue|Financial
		// Status|Round Lot Size|ETF|NextShares
		// AAAP|Advanced Accelerator Applications S.A. - American Depositary
		// Shares|Q|N|N|100|N|N,

		list.remove(0);
		Set<Security> parsedSecurities = new HashSet<>(list.size());

		ZonedDateTime now = ZonedDateTime.now();

		String[] split;
		int line = 1;
		Security sec;
		for (String raw : list) {
			split = raw.split("\\|");

			if (split.length != 8) {
				logger.warn("line {} has an unxpected length of {} ", new Object[] { line, split.length });
				continue;
			}

			// TODO Convert symbol to CQS
			sec = process(split[0], split[1], split[3], split[5], split[6], split[7], Exchange.NASDAQ, now);

			if (sec != null) {
				parsedSecurities.add(sec);
			}

			++line;
		}

		return parsedSecurities;
	}

	private Security process(String ticker, String companyName, String testSecurity, String lotSize, String etf,
			String nextShare, Exchange exchange, ZonedDateTime timestamp) {

		if ("Y".equalsIgnoreCase(testSecurity)) {
			logger.info("Skipping test security for symbol {}.", ticker);
			return null;
		}

		if (exchange == null) {
			logger.info("Missing Exchange for security for symbol {}.", ticker);
		}

		// Scan names for security types to ignore ... fragile
		if (StringUtils.containsAny(companyName, "Warrant", "Depositary", "Notes", "Preferred", "Closed End Fund",
				"Right", "Units", "Beneficial") || "Y".equals(nextShare)) {
			logger.info("Skipping security {} due to instrument type found scanning the name ( {} ).",
					new Object[] { ticker, companyName });
			return null;
		}

		Security sec = new Security(ticker);
		sec.setTimestamp(timestamp);
		sec.setCompanyName(companyName);
		sec.setLotSize(Integer.parseInt(lotSize));
		sec.setExchange(exchange);

		InstrumentType type = deriveInstrumentTypeOfListed(ticker, companyName, "Y".equals(etf));
		sec.setInstrumentType(type);

		return sec;
	}

	private InstrumentType deriveInstrumentTypeOfListed(String symbol, String name, boolean isEtf) {

		// TODO need a lot more logic here ...

		if (isEtf) {
			return InstrumentType.ETF;
		} else {
			return InstrumentType.STOCK;
		}
	}

}
