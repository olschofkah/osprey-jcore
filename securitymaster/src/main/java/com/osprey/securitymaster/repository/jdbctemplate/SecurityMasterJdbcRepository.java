package com.osprey.securitymaster.repository.jdbctemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.constants.SecurityEventType;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.utils.OspreyUtils;

// @Repository
public class SecurityMasterJdbcRepository implements ISecurityMasterRepository {
	
	final static Logger logger = LogManager.getLogger(SecurityMasterJdbcRepository.class);
	
	private LocalDate DEFAULT_HISTORICAL_LOAD_DATE_MIN = LocalDate.now().minusYears(4);
	private LocalDate DEFAULT_HISTORICAL_LOAD_DATE_MAX = LocalDate.now();

	private static final String SELECT_OC_SECURITY = " select symbol, cusip, instrument_cd, lot_size, company_name, country, state, employee_cnt, industry, sector, exchange_cd, currency, last_update_ts, description, previous_close "
			+ " from oc_security where symbol = ? ";
	private static final String SELECT_OC_SECURITIES = " select symbol, cusip, instrument_cd, lot_size, company_name, country, state, employee_cnt, industry, sector, exchange_cd, currency, last_update_ts, description, previous_close "
			+ " from oc_security ";
	private static final String UPDATE_OC_SECURITY = "update oc_security "
			+ " set description = ?, company_name = ?, country = ?, currency = ?, employee_cnt = ?, exchange_cd = ?, industry = ?, instrument_cd = ?, lot_size = ?, sector = ?, state = ?, last_update_ts = clock_timestamp(), previous_close = ?"
			+ " where symbol = ? ";
	private static final String INSERT_OC_SECURITY = " insert into oc_security "
			+ " (symbol, cusip, instrument_cd, lot_size, company_name, country, state, employee_cnt, industry, sector, exchange_cd, currency, last_update_ts, create_ts, description, previous_close) "
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, clock_timestamp(), clock_timestamp(), ?, ?) ";
	private static final String EXISTS_OC_SECURITY = "select exists (select 1 from oc_security where symbol = ? )";
	
	private static final String SELECT_OHLC_HIST = "select symbol, date, open, high, low, close, adj_close, volume, timestamp "
			+ " from oc_security_ohlc_hist where symbol = ? and date >= ? and date <= ? "
			+ " order by date desc ";
	private static final String DELETE_OHLC_HIST_FOR_SYMBOL = "delete from oc_security_ohlc_hist where symbol = ?";
	private static final String INSERT_OHLC_HIST = " insert into oc_security_ohlc_hist (symbol, date, open, high, low, close, adj_close, volume, timestamp) "
			+ " values (?,?,?,?,?,?,?,?,clock_timestamp())";
	
	private static final String EXISTS_OC_SECURITY_NEXT_EVENT = "select exists (select 1 from oc_security_next_events where symbol = ? )";
	private static final String INSERT_OC_SECURITY_NEXT_EVENT = "insert into oc_security_next_events "
			+ " (symbol, next_earnings_date_est_low, next_earnings_date_est_high, next_div_date, next_ex_div_date, next_revenue, timestamp) "
			+ " values (?,?,?,?,?,?,clock_timestamp())";
	private static final String SELECT_OC_SECURITY_NEXT_EVENT = " select "
			+ " symbol, next_earnings_date_est_low, next_earnings_date_est_high, next_div_date, next_ex_div_date, next_revenue, timestamp "
			+ " from oc_security_next_events where symbol = ? ";
	private static final String UPDATE_OC_SECURITY_NEXT_EVENT = "update oc_security_next_events set "
			+ " next_earnings_date_est_low = ?, next_earnings_date_est_high = ?, next_div_date = ?, next_ex_div_date = ?, next_revenue = ?, timestamp = clock_timestamp() "
			+ " where symbol = ? ";
	
	private static final String INSERT_OC_SECURITY_QUOTE = "insert into oc_security_quote "
			+ " (symbol, timestamp, last, bid, ask, bid_size, ask_size, volume, open, close, high, low, data_currency, open_interest) "
			+ " values (?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_OC_SECURITY_QUOTE = "select "
			+ " symbol, timestamp, last, bid, ask, bid_size, ask_size, volume, open, close, high, low, data_currency, open_interest "
			+ " from oc_security_quote sq where symbol = ? and timestamp = (select max(timestamp) from oc_security_quote isq where isq.symbol=sq.symbol) ";
	
	private static final String DELETE_OC_SECURITY_EVENT = "delete from oc_security_event where symbol = ? and date >= ?";
	//private static final String EXISTS_OC_SECURITY_EVENT = "select exists (select 1 from oc_security_event where symbol = ? and event_type_cd = ? and date = ? )";
	private static final String INSERT_OC_SECURITY_EVENT = " insert into oc_security_event "
			+ " (symbol, date, event_type_cd, amt, timestamp) values (?, ?, ?, ?, clock_timestamp()) ";

	private static final String SELECT_CLOSE = "select close from oc_security_ohlc_hist where symbol = ? and date = ?";

	private static final String SELECT_OC_SECURITY_EVENT = "select symbol, date, event_type_cd, amt, timestamp from oc_security_event where symbol = ?";
	private static final String EXISTS_OC_FUNDAMENTAL = "select exists (select 1 from oc_security_fundamental where symbol = ? and date = ?)";
	private static final String INSERT_OC_FUNDAMENTAL = "insert into oc_security_fundamental ( " +
			" symbol , " +
			" date , " +
			" last_update_ts  , " +
			" _10_day_avg_volume , " +
			" _200_day_average , " +
			" _50_day_average , " +
			" _52_week_high , " +
			" _52_week_low , " +
			" average_volume , " +
			" beta , " +
			" book_value , " +
			" current_ratio , " +
			" debt_to_equity , " +
			" div_rate , " +
			" div_yield , " +
			" earnings_avg , " +
			" earnings_growth , " +
			" earnings_high , " +
			" earnings_low , " +
			" earnings_qtr_growth , " +
			" ebitda , " +
			" ebitda_margins , " +
			" enterprise_to_ebitda , " +
			" enterprise_to_revenue , " +
			" enterprise_value , " +
			" float_shares , " +
			" forward_eps , " +
			" forward_pe , " +
			" free_cashflow , " +
			" gross_margins , " +
			" gross_profits , " +
			" held_pct_insiders , " +
			" held_pct_institutions , " +
			" market_cap , " +
			" net_income_to_common , " +
			" operating_cashflow , " +
			" operating_margins , " +
			" peg_ratio , " +
			" price_to_book , " +
			" price_to_sales , " +
			" profit_margins , " +
			" quick_ratio , " +
			" return_on_assets , " +
			" return_on_equity , " +
			" revenue_avg , " +
			" revenue_growth , " +
			" revenue_high , " +
			" revenue_low , " +
			" revenue_per_share , " +
			" revenue_qtr_growth , " +
			" shares_outstanding , " +
			" shares_short , " +
			" shares_short_prior_month , " +
			" short_percent_of_float , " +
			" short_ratio , " +
			" total_assets ,  " +
			" total_cash , " +
			" total_cash_per_share , " +
			" total_debt , " +
			" total_revenue , " +
			" trailing_eps , " +
			" trailing_pe , " +
			" yield , "
			+ "rotation_indicator , "
			+ "_8_day_ema , "
			+ "_10_day_ema , "
			+ "_15_day_ema , "
			+ "_20_day_ema , "
			+ "_50_day_ema , "
			+ "_100_day_ema , "
			+ "_200_day_ema , "
			+ " volatility ) "
			+ " values " 
			+ " (?,?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	
	private static final String UPDATE_OC_FUNDAMENTAL = "update oc_security_fundamental set " +
			" last_update_ts = clock_timestamp(), " +
			" _10_day_avg_volume = ?, " +
			" _200_day_average = ?, " +
			" _50_day_average = ?, " +
			" _52_week_high = ?, " +
			" _52_week_low = ?, " +
			" average_volume = ?, " +
			" beta = ?, " +
			" book_value = ?, " +
			" current_ratio = ?, " +
			" debt_to_equity = ?, " +
			" div_rate = ?, " +
			" div_yield = ?, " +
			" earnings_avg = ?, " +
			" earnings_growth = ?, " +
			" earnings_high = ?, " +
			" earnings_low = ?, " +
			" earnings_qtr_growth = ?, " +
			" ebitda = ?, " +
			" ebitda_margins = ?, " +
			" enterprise_to_ebitda = ?, " +
			" enterprise_to_revenue = ?, " +
			" enterprise_value = ?, " +
			" float_shares = ?, " +
			" forward_eps = ?, " +
			" forward_pe = ?, " +
			" free_cashflow = ?, " +
			" gross_margins = ?, " +
			" gross_profits = ?, " +
			" held_pct_insiders = ?, " +
			" held_pct_institutions = ?, " +
			" market_cap = ?, " +
			" net_income_to_common = ?, " +
			" operating_cashflow = ?, " +
			" operating_margins = ?, " +
			" peg_ratio = ?, " +
			" price_to_book = ?, " +
			" price_to_sales = ?, " +
			" profit_margins = ?, " +
			" quick_ratio = ?, " +
			" return_on_assets = ?, " +
			" return_on_equity = ?, " +
			" revenue_avg = ?, " +
			" revenue_growth = ?, " +
			" revenue_high = ?, " +
			" revenue_low = ?, " +
			" revenue_per_share = ?, " +
			" revenue_qtr_growth = ?, " +
			" shares_outstanding = ?, " +
			" shares_short = ?, " +
			" shares_short_prior_month = ?, " +
			" short_percent_of_float = ?, " +
			" short_ratio = ?, " +
			" total_assets = ?,  " +
			" total_cash = ?, " +
			" total_cash_per_share = ?, " +
			" total_debt = ?, " +
			" total_revenue = ?, " +
			" trailing_eps = ?, " +
			" trailing_pe = ?, " +
			" yield = ?, " +
			" rotation_indicator = ?, " +
			" _8_day_ema = ?, " +
			" _10_day_ema = ?, " +
			" _15_day_ema = ?, " +
			" _20_day_ema = ?, " +
			" _50_day_ema = ?, " +
			" _100_day_ema = ?, " +
			" _200_day_ema = ?, " +
			" volatility = ? " +
			" where symbol = ? and date = ? ";
	private static final String SELECT_OC_FUNDAMENTAL = "select " +
			" symbol , " +
			" date , " +
			" last_update_ts  , " +
			" _10_day_avg_volume , " +
			" _200_day_average , " +
			" _50_day_average , " +
			" _52_week_high , " +
			" _52_week_low , " +
			" average_volume , " +
			" beta , " +
			" book_value , " +
			" current_ratio , " +
			" debt_to_equity , " +
			" div_rate , " +
			" div_yield , " +
			" earnings_avg , " +
			" earnings_growth , " +
			" earnings_high , " +
			" earnings_low , " +
			" earnings_qtr_growth , " +
			" ebitda , " +
			" ebitda_margins , " +
			" enterprise_to_ebitda , " +
			" enterprise_to_revenue , " +
			" enterprise_value , " +
			" float_shares , " +
			" forward_eps , " +
			" forward_pe , " +
			" free_cashflow , " +
			" gross_margins , " +
			" gross_profits , "
			+ " held_pct_insiders , "
			+ " held_pct_institutions , "
			+ " market_cap , "
			+ " net_income_to_common , "
			+ " operating_cashflow , "
			+ " operating_margins , "
			+ " peg_ratio , "
			+ " price_to_book , "
			+ " price_to_sales , "
			+ " profit_margins , "
			+ " quick_ratio , "
			+ " return_on_assets , "
			+ " return_on_equity , "
			+ " revenue_avg , "
			+ " revenue_growth , "
			+ " revenue_high , "
			+ " revenue_low , "
			+ " revenue_per_share , "
			+ " revenue_qtr_growth , "
			+ " shares_outstanding , "
			+ " shares_short , "
			+ " shares_short_prior_month , "
			+ " short_percent_of_float , "
			+ " short_ratio , "
			+ " total_assets ,  "
			+ " total_cash , "
			+ " total_cash_per_share , "
			+ " total_debt , "
			+ " total_revenue , "
			+ " trailing_eps , "
			+ " trailing_pe , "
			+ " yield , "
			+ " rotation_indicator , "
			+ " _8_day_ema , "
			+ " _10_day_ema , "
			+ " _15_day_ema , "
			+ " _20_day_ema , "
			+ " _50_day_ema , "
			+ " _100_day_ema , "
			+ " _200_day_ema , "
			+ " volatility "
			+ " from oc_security_fundamental ocsf where symbol = ? and date = "
			+ " ( select max(date) from oc_security_fundamental ocsfi where ocsfi.symbol = ocsf.symbol ) ";

	private JdbcTemplate jdbc;

	public SecurityMasterJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void deleteHistoricals(SecurityKey key) {
		jdbc.update(DELETE_OHLC_HIST_FOR_SYMBOL, key.getSymbol());
	}

	public double fetchClosingPrice(String symbol, LocalDate date) {
		return jdbc.queryForObject(SELECT_CLOSE, new Object[] { symbol, Date.valueOf(date) }, Double.class);
	}

	public List<HistoricalQuote> findHistoricals(SecurityKey key, LocalDate minDate, LocalDate maxDate) {

		return jdbc.query(SELECT_OHLC_HIST, new RowMapper<HistoricalQuote>() {

			public HistoricalQuote mapRow(ResultSet rs, int rowNum) throws SQLException {
				Timestamp timestamp = rs.getTimestamp("timestamp");
				HistoricalQuote hq = new HistoricalQuote(rs.getString("symbol"), rs.getDate("date").toLocalDate());
				hq.setAdjClose(rs.getDouble("adj_close"));
				hq.setClose(rs.getDouble("close"));
				hq.setHigh(rs.getDouble("high"));
				hq.setLow(rs.getDouble("low"));
				hq.setOpen(rs.getDouble("open"));
				hq.setTimestamp(OspreyUtils.getZonedDateTimeFromEpoch(timestamp.getTime()));
				hq.setVolume(rs.getLong("volume"));

				return hq;
			}
		}, key.getSymbol(), Date.valueOf(minDate), Date.valueOf(maxDate));

	}

	public List<Security> findSecurities() {
		return jdbc.query(SELECT_OC_SECURITIES, new SecurityRowMapper());
	}

	public Security findSecurity(final SecurityKey key) {
		return jdbc.queryForObject(SELECT_OC_SECURITY, new SecurityRowMapper(), key.getSymbol());
	}

	public List<SecurityEvent> findSecurityEvents(SecurityKey key) {
		return jdbc.query(SELECT_OC_SECURITY_EVENT, new RowMapper<SecurityEvent>() {

			public SecurityEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
				Timestamp timestamp = rs.getTimestamp("timestamp");
				return new SecurityEvent(
						new SecurityKey(rs.getString("symbol"), null),
						rs.getDate("date").toLocalDate(),
						SecurityEventType.fromCode(rs.getString("event_type_cd")),
						rs.getDouble("amt"),
						OspreyUtils.getZonedDateTimeFromEpoch(timestamp.getTime())
					);

			}
		}, key.getSymbol());
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(FundamentalQuote fq) {

		boolean exists = jdbc.queryForObject(EXISTS_OC_FUNDAMENTAL,
				new Object[] { fq.getKey().getSymbol(), Date.valueOf(LocalDate.now()) }, Boolean.class);

		if (exists) {
			// update
			jdbc.update(UPDATE_OC_FUNDAMENTAL, fq.get_10DayAvgVolume(), fq.get_200DayAverage(), fq.get_50DayAverage(),
					fq.get_52WeekHigh(), fq.get_52WeekLow(), fq.getAverageVolume(), fq.getBeta(), fq.getBookValue(),
					fq.getCurrentRatio(), fq.getDebtToEquity(), fq.getDividendRate(), fq.getDividendYield(),
					fq.getEarningsAvg(), fq.getEarningsGrowth(), fq.getEarningsHigh(), fq.getEarningsLow(),
					fq.getEarningsQtrGrowth(), fq.getEbitda(), fq.getEbitdaMargins(), fq.getEnterpriseToEbitda(),
					fq.getEnterpriseToRevenue(), fq.getEnterpriseValue(), fq.getFloatShares(), fq.getForwardEps(),
					fq.getForwardPe(), fq.getFreeCashflow(), fq.getGrossMargins(), fq.getGrossProfits(),
					fq.getHeldPctInsiders(), fq.getHeldPctInstitutions(), fq.getMarketCap(), fq.getNetIncomeToCommon(),
					fq.getOperatingCashflow(), fq.getOperatingMargins(), fq.getPegRatio(), fq.getPriceToBook(),
					fq.getPriceToSales(), fq.getProfitMargins(), fq.getQuickRatio(), fq.getReturnOnAssets(),
					fq.getReturnOnEquity(), fq.getRevenueAvg(), fq.getRevenueGrowth(), fq.getRevenueHigh(),
					fq.getRevenueLow(), fq.getRevenuePerShare(), fq.getRevenueQtrGrowth(), fq.getSharesOutstanding(),
					fq.getSharesShort(), fq.getSharesShortPriorMonth(), fq.getShortPercentOfFloat(), fq.getShortRatio(),
					fq.getTotalAssets(), fq.getTotalCash(), fq.getTotalCashPerShare(), fq.getTotalDebt(),
					fq.getTotalRevenue(), fq.getTrailingEps(), fq.getTrailingPe(), fq.getYield(), 
					fq.getRotationIndicator(),
					fq.get_8DayEma(), fq.get_10DayEma(), fq.get_15DayEma(), fq.get_20DayEma(), fq.get_50DayEma(), fq.get_100DayEma(), fq.get_200DayEma(),
					fq.getVolatility(), 
					fq.getKey().getSymbol(), fq.getDate());

		} else {
			// insert
			jdbc.update(INSERT_OC_FUNDAMENTAL, fq.getKey().getSymbol(), fq.getDate(), fq.get_10DayAvgVolume(),
					fq.get_200DayAverage(), fq.get_50DayAverage(), fq.get_52WeekHigh(), fq.get_52WeekLow(),
					fq.getAverageVolume(), fq.getBeta(), fq.getBookValue(), fq.getCurrentRatio(), fq.getDebtToEquity(),
					fq.getDividendRate(), fq.getDividendYield(), fq.getEarningsAvg(), fq.getEarningsGrowth(),
					fq.getEarningsHigh(), fq.getEarningsLow(), fq.getEarningsQtrGrowth(), fq.getEbitda(),
					fq.getEbitdaMargins(), fq.getEnterpriseToEbitda(), fq.getEnterpriseToRevenue(),
					fq.getEnterpriseValue(), fq.getFloatShares(), fq.getForwardEps(), fq.getForwardPe(),
					fq.getFreeCashflow(), fq.getGrossMargins(), fq.getGrossProfits(), fq.getHeldPctInsiders(),
					fq.getHeldPctInstitutions(), fq.getMarketCap(), fq.getNetIncomeToCommon(),
					fq.getOperatingCashflow(), fq.getOperatingMargins(), fq.getPegRatio(), fq.getPriceToBook(),
					fq.getPriceToSales(), fq.getProfitMargins(), fq.getQuickRatio(), fq.getReturnOnAssets(),
					fq.getReturnOnEquity(), fq.getRevenueAvg(), fq.getRevenueGrowth(), fq.getRevenueHigh(),
					fq.getRevenueLow(), fq.getRevenuePerShare(), fq.getRevenueQtrGrowth(), fq.getSharesOutstanding(),
					fq.getSharesShort(), fq.getSharesShortPriorMonth(), fq.getShortPercentOfFloat(), fq.getShortRatio(),
					fq.getTotalAssets(), fq.getTotalCash(), fq.getTotalCashPerShare(), fq.getTotalDebt(),
					fq.getTotalRevenue(), fq.getTrailingEps(), fq.getTrailingPe(), fq.getYield(), 
					fq.getRotationIndicator(),
					fq.get_8DayEma(), fq.get_10DayEma(), fq.get_15DayEma(), fq.get_20DayEma(), fq.get_50DayEma(), fq.get_100DayEma(), fq.get_200DayEma(),
					fq.getVolatility());
		}

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(Security s) {

		boolean exists = jdbc.queryForObject(EXISTS_OC_SECURITY, new Object[] { s.getKey().getSymbol() },
				Boolean.class);

		if (exists) {
			// update
			jdbc.update(UPDATE_OC_SECURITY, s.getCompanyDescription(), s.getCompanyName(), s.getCountry(),
					s.getCurrency(), s.getEmployeeCount(), s.getExchange().getCode(), s.getIndustry(),
					s.getInstrumentType().getId(), s.getLotSize(), s.getSector(), s.getState(), s.getPreviousClose(),
					s.getKey().getSymbol());

		} else {
			// insert
			jdbc.update(INSERT_OC_SECURITY, s.getKey().getSymbol(), s.getKey().getCusip(),
					s.getInstrumentType().getId(), s.getLotSize(), s.getCompanyName(), s.getCountry(), s.getState(),
					s.getEmployeeCount(), s.getIndustry(), s.getSector(), s.getExchange().getCode(), s.getCurrency(),
					s.getCompanyDescription(), s.getPreviousClose());
		}

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityQuote sq) {

		jdbc.update(INSERT_OC_SECURITY_QUOTE, sq.getKey().getSymbol(), sq.getLast(), sq.getBid(), sq.getAsk(),
				sq.getBidSize(), sq.getAskSize(), sq.getVolume(), sq.getOpen(), sq.getClose(), sq.getHigh(),
				sq.getLow(), sq.getDataCurrency(), sq.getOpenInterest());

	}

	public SecurityQuoteContainer findSecurityQuoteContainer(SecurityKey key) {
		return findSecurityQuoteContainer(key, DEFAULT_HISTORICAL_LOAD_DATE_MIN, DEFAULT_HISTORICAL_LOAD_DATE_MAX);
	}
	
	public SecurityQuoteContainer findSecurityQuoteContainer(SecurityKey key, LocalDate minDate, LocalDate maxDate) {
		SecurityQuoteContainer sqc = new SecurityQuoteContainer(new SecurityKey(key));

		sqc.setEvents(findSecurityEvents(sqc.getKey()));
		sqc.setFundamentalQuote(findFundamentalQuote(sqc.getKey()));
		sqc.setHistoricalQuotes(
				findHistoricals(sqc.getKey(), minDate, maxDate));
		sqc.setSecurity(findSecurity(sqc.getKey()));
		sqc.setSecurityQuote(findSecurityQuote(sqc.getKey()));
		sqc.setUpcomingEvents(findUpcomingEvents(sqc.getKey()));

		return sqc;
	}

	private SecurityUpcomingEvents findUpcomingEvents(final SecurityKey key) {

		List<SecurityUpcomingEvents> query = jdbc.query(SELECT_OC_SECURITY_NEXT_EVENT,
				new RowMapper<SecurityUpcomingEvents>() {

					public SecurityUpcomingEvents mapRow(ResultSet rs, int rowNum) throws SQLException {
						SecurityUpcomingEvents nextEvents = new SecurityUpcomingEvents(new SecurityKey(key));
						
						Date date = rs.getDate("next_div_date");
						nextEvents.setNextDivDate(date == null ? null : date.toLocalDate());

						date = rs.getDate("next_earnings_date_est_high");
						nextEvents.setNextEarningsDateEstHigh(date == null ? null : date.toLocalDate());
						
						date = rs.getDate("next_earnings_date_est_low");
						nextEvents.setNextEarningsDateEstLow(date == null ? null : date.toLocalDate());
						
						date = rs.getDate("next_ex_div_date");
						nextEvents.setNextExDivDate(date == null ? null : date.toLocalDate());
						
						date = rs.getDate("next_revenue");
						nextEvents.setNextRevenue(date == null ? null : date.toLocalDate());
						
						nextEvents.setTimestamp(
								OspreyUtils.getZonedDateTimeFromEpoch(rs.getTimestamp("timestamp").getTime()));

						return nextEvents;
					}
				}, key.getSymbol());

		return query.isEmpty() ? new SecurityUpcomingEvents(new SecurityKey(key)) : query.get(0);
	}

	private SecurityQuote findSecurityQuote(final SecurityKey key) {
		List<SecurityQuote> query = jdbc.query(SELECT_OC_SECURITY_QUOTE, new RowMapper<SecurityQuote>() {

			public SecurityQuote mapRow(ResultSet rs, int rowNum) throws SQLException {
				SecurityQuote sq = new SecurityQuote(new SecurityKey(key));
				sq.setAsk(rs.getDouble("ask"));
				sq.setAskSize(rs.getLong("ask_size"));
				sq.setBid(rs.getDouble("bid"));
				sq.setBidSize(rs.getLong("bid_size"));
				sq.setClose(rs.getDouble("close"));
				sq.setDataCurrency(rs.getString("data_currency"));
				sq.setHigh(rs.getDouble("high"));
				sq.setLast(rs.getDouble("last"));
				sq.setLow(rs.getDouble("low"));
				sq.setOpen(rs.getDouble("open"));
				sq.setOpenInterest(rs.getInt("open_interest"));
				sq.setTimestamp(OspreyUtils.getZonedDateTimeFromEpoch(rs.getTimestamp("timestamp").getTime()));
				sq.setVolume(rs.getLong("volume"));
				return sq;
			}
		}, key.getSymbol());

		return query.isEmpty() ? new SecurityQuote(new SecurityKey(key)) : query.get(0);
	}

	public FundamentalQuote findFundamentalQuote(final SecurityKey key) {
		List<FundamentalQuote> query = jdbc.query(SELECT_OC_FUNDAMENTAL, new RowMapper<FundamentalQuote>() {

			public FundamentalQuote mapRow(ResultSet rs, int rowNum) throws SQLException {
				FundamentalQuote fq = new FundamentalQuote(new SecurityKey(key), rs.getDate("date").toLocalDate());
				fq.set_10DayAvgVolume(rs.getLong("_10_day_avg_volume"));
				fq.set_200DayAverage(rs.getDouble("_200_day_average"));
				fq.set_50DayAverage(rs.getDouble("_50_day_average"));
				fq.set_52WeekHigh(rs.getDouble("_52_week_high"));
				fq.set_52WeekLow(rs.getDouble("_52_week_low"));
				fq.setAverageVolume(rs.getLong("average_volume"));
				fq.setBeta(rs.getDouble("beta"));
				fq.setBookValue(rs.getDouble("book_value"));
				fq.setCurrentRatio(rs.getDouble("current_ratio"));
				fq.setDebtToEquity(rs.getDouble("debt_to_equity"));
				fq.setDividendRate(rs.getDouble("div_rate"));
				fq.setDividendYield(rs.getDouble("div_yield"));
				fq.setEarningsAvg(rs.getDouble("earnings_avg"));
				fq.setEarningsGrowth(rs.getDouble("earnings_growth"));
				fq.setEarningsHigh(rs.getDouble("earnings_high"));
				fq.setEarningsLow(rs.getDouble("earnings_low"));
				fq.setEarningsQtrGrowth(rs.getDouble("earnings_qtr_growth"));
				fq.setEbitda(rs.getDouble("ebitda"));
				fq.setEbitdaMargins(rs.getDouble("ebitda_margins"));
				fq.setEnterpriseToEbitda(rs.getDouble("enterprise_to_ebitda"));
				fq.setEnterpriseToRevenue(rs.getDouble("enterprise_to_revenue"));
				fq.setEnterpriseValue(rs.getDouble("enterprise_value"));
				fq.setFloatShares(rs.getDouble("float_shares"));
				fq.setForwardEps(rs.getDouble("forward_eps"));
				fq.setForwardPe(rs.getDouble("forward_pe"));
				fq.setFreeCashflow(rs.getDouble("free_cashflow"));
				fq.setGrossMargins(rs.getDouble("gross_margins"));
				fq.setGrossProfits(rs.getDouble("gross_profits"));
				fq.setHeldPctInsiders(rs.getDouble("held_pct_insiders"));
				fq.setHeldPctInstitutions(rs.getDouble("held_pct_institutions"));
				fq.setMarketCap(rs.getLong("market_cap"));
				fq.setNetIncomeToCommon(rs.getDouble("net_income_to_common"));
				fq.setOperatingCashflow(rs.getDouble("operating_cashflow"));
				fq.setOperatingMargins(rs.getDouble("operating_margins"));
				fq.setPegRatio(rs.getDouble("peg_ratio"));
				fq.setPriceToBook(rs.getDouble("price_to_book"));
				fq.setPriceToSales(rs.getDouble("price_to_sales"));
				fq.setProfitMargins(rs.getDouble("profit_margins"));
				fq.setQuickRatio(rs.getDouble("quick_ratio"));
				fq.setReturnOnAssets(rs.getDouble("return_on_assets"));
				fq.setReturnOnEquity(rs.getDouble("return_on_equity"));
				fq.setRevenueAvg(rs.getDouble("revenue_avg"));
				fq.setRevenueGrowth(rs.getDouble("revenue_growth"));
				fq.setRevenueHigh(rs.getDouble("revenue_high"));
				fq.setRevenueLow(rs.getDouble("revenue_low"));
				fq.setRevenuePerShare(rs.getDouble("revenue_per_share"));
				fq.setRevenueQtrGrowth(rs.getDouble("revenue_qtr_growth"));
				fq.setSharesOutstanding(rs.getDouble("shares_outstanding"));
				fq.setSharesShort(rs.getDouble("shares_short"));
				fq.setSharesShortPriorMonth(rs.getDouble("shares_short_prior_month"));
				fq.setShortPercentOfFloat(rs.getDouble("short_percent_of_float"));
				fq.setShortRatio(rs.getDouble("short_ratio"));
				fq.setTimestamp(OspreyUtils.getZonedDateTimeFromEpoch(rs.getTimestamp("last_update_ts").getTime()));
				fq.setTotalAssets(rs.getLong("total_assets"));
				fq.setTotalCash(rs.getDouble("total_cash"));
				fq.setTotalCashPerShare(rs.getDouble("total_cash_per_share"));
				fq.setTotalDebt(rs.getDouble("total_debt"));
				fq.setTotalRevenue(rs.getDouble("total_revenue"));
				fq.setTrailingEps(rs.getDouble("trailing_eps"));
				fq.setTrailingPe(rs.getDouble("trailing_pe"));
				fq.setYield(rs.getDouble("yield"));

				fq.setRotationIndicator(rs.getDouble("rotation_indicator"));
				fq.set_10DayEma(rs.getDouble("_8_day_ema"));
				fq.set_10DayEma(rs.getDouble("_10_day_ema"));
				fq.set_10DayEma(rs.getDouble("_15_day_ema"));
				fq.set_20DayEma(rs.getDouble("_20_day_ema"));
				fq.set_50DayEma(rs.getDouble("_50_day_ema"));
				fq.set_100DayEma(rs.getDouble("_100_day_ema"));
				fq.set_200DayEma(rs.getDouble("_200_day_ema"));
				fq.setVolatility(rs.getDouble("volatility"));
				
				return fq;
			}
		}, key.getSymbol());

		return query.isEmpty() ? new FundamentalQuote(new SecurityKey(key), LocalDate.now()) : query.get(0);
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityQuoteContainer sqc) {

		persist(sqc.getSecurity());
		persist(sqc.getFundamentalQuote());
		persist(sqc.getSecurityQuote());
		persist(sqc.getUpcomingEvents());

		deleteEvents(sqc.getEvents());
		persistEvents(sqc.getEvents());

		deleteHistoricals(sqc.getKey());
		persistHistoricals(sqc.getHistoricalQuotes());

	}

	public void deleteEvents(List<SecurityEvent> events) {

		if (events != null) {
			
			LocalDate dt = null;
			for (SecurityEvent event : events) {
				if (dt == null || dt.isAfter(event.getDate())) {
					dt = event.getDate();
				}
			}

			if (dt != null) {
				jdbc.update(DELETE_OC_SECURITY_EVENT, events.get(0).getKey().getSymbol(), Date.valueOf(dt));
			}
		}
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityUpcomingEvents s) {

		boolean exists = jdbc.queryForObject(EXISTS_OC_SECURITY_NEXT_EVENT, new Object[] { s.getKey().getSymbol() },
				Boolean.class);

		if (exists) {
			// update
			jdbc.update(UPDATE_OC_SECURITY_NEXT_EVENT,
					s.getNextEarningsDateEstLow() == null ? null : Date.valueOf(s.getNextEarningsDateEstLow()),
					s.getNextEarningsDateEstHigh() == null ? null : Date.valueOf(s.getNextEarningsDateEstHigh()),
					s.getNextDivDate() == null ? null : Date.valueOf(s.getNextDivDate()),
					s.getNextExDivDate() == null ? null : Date.valueOf(s.getNextExDivDate()),
					s.getNextRevenue() == null ? null : Date.valueOf(s.getNextRevenue()), s.getKey().getSymbol());

		} else {
			// insert
			jdbc.update(INSERT_OC_SECURITY_NEXT_EVENT, s.getKey().getSymbol(),
					s.getNextEarningsDateEstLow() == null ? null : Date.valueOf(s.getNextEarningsDateEstLow()),
					s.getNextEarningsDateEstHigh() == null ? null : Date.valueOf(s.getNextEarningsDateEstHigh()),
					s.getNextDivDate() == null ? null : Date.valueOf(s.getNextDivDate()),
					s.getNextExDivDate() == null ? null : Date.valueOf(s.getNextExDivDate()),
					s.getNextRevenue() == null ? null : Date.valueOf(s.getNextRevenue()));
		}

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persistEvents(final List<SecurityEvent> events) {

		if (events != null) {
			
			if(events.size() != new HashSet<>(events).size()){
				throw new RuntimeException("Unexpected Dupliate Events " + events);
			}
			
			jdbc.batchUpdate(INSERT_OC_SECURITY_EVENT, new BatchPreparedStatementSetter() {

				public int getBatchSize() {
					return events.size();
				}

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					SecurityEvent se = events.get(i);
					ps.setString(1, se.getKey().getSymbol());
					ps.setDate(2, Date.valueOf(se.getDate()));
					ps.setString(3, se.getEvent().getCode());
					ps.setDouble(4, se.getAmount());
				}
			});
		}
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persistHistoricals(final List<HistoricalQuote> hist) {

		jdbc.batchUpdate(INSERT_OHLC_HIST, new BatchPreparedStatementSetter() {

			public int getBatchSize() {
				return hist.size();
			}

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				HistoricalQuote h = hist.get(i);
				ps.setString(1, h.getKey().getSymbol());
				ps.setDate(2, Date.valueOf(h.getHistoricalDate()));
				ps.setDouble(3, h.getOpen());
				ps.setDouble(4, h.getHigh());
				ps.setDouble(5, h.getLow());
				ps.setDouble(6, h.getClose());
				ps.setDouble(7, h.getAdjClose());
				ps.setDouble(8, h.getVolume());
			}
		});

	}

}
