package com.osprey.securitymaster.repository.jdbctemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.osprey.securitymaster.EnhancedSecurity;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

// @Repository
public class SecurityMasterJdbcRepository implements ISecurityMasterRepository {

	private JdbcTemplate jdbc;

	public SecurityMasterJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

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
			+ " from oc_security_ohlc_hist where symbol = ? and date >= ? and date =< ?";
	private static final String DELETE_OHLC_HIST_FOR_SYMBOL = "delete from oc_security_ohlc_hist where symbol = ?";
	private static final String INSERT_OHLC_HIST = " insert into oc_security_ohlc_hist (symbol, date, open, high, low, close, adj_close, volume, timestamp) "
			+ " values (?,?,?,?,?,?,?,?,clock_timestamp())";
	
	private static final String EXISTS_OC_SECURITY_NEXT_EVENT = "select exists (select 1 from oc_security_next_events where symbol = ? )";
	private static final String INSERT_OC_SECURITY_NEXT_EVENT = "insert into oc_security_next_events "
			+ " (symbol, next_earnings_date_est_low, next_earnings_date_est_high, next_div_date, next_ex_div_date, next_revenue, timestamp) "
			+ " values (?,?,?,?,?,?,clock_timestamp())";
	private static final String UPDATE_OC_SECURITY_NEXT_EVENT = "update oc_security_next_events set "
			+ " next_earnings_date_est_low = ?, next_earnings_date_est_high = ?, next_div_date = ?, next_ex_div_date = ?, next_revenue = ?, timestamp = clock_timestamp() "
			+ " where symbol = ? ";
	
	private static final String INSERT_OC_SECURITY_QUOTE = "insert into oc_security_quote "
			+ " (symbol, timestamp, last, bid, ask, bid_size, ask_size, volume, open, close, high, low, data_currency, open_interest) "
			+ " values (?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?)";
	
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
			" yield ) " +
			" values " +
			" (?,?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
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
			" yield = ? " +
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
			" yield "
			+ " from oc_security_fundamental where symbol = ? and date = ?";
	
	private static final String SELECT_CLOSE = "select close from oc_security_ohlc_hist where symbol = ? and date = ?";

	public Security findSecurity(final SecurityKey key) {
		return jdbc.queryForObject(SELECT_OC_SECURITY, new SecurityRowMapper(), key.getSymbol());
	}

	public double fetchClosingPrice(String symbol, LocalDate date) {
		return jdbc.queryForObject(SELECT_CLOSE, new Object[]{symbol, Date.valueOf(date)}, Double.class);
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityQuoteContainer sqc) {

		persist(sqc.getEnhancedSecurity());
		persist(sqc.getFundamentalQuote());
		persist(sqc.getSecurity());
		persist(sqc.getSecurityQuote());
		persist(sqc.getUpcomingEvents());

		persistEvents(sqc.getEvents());

		deleteHistoricals(sqc.getKey());
		persistHistoricals(sqc.getHistoricalQuotes());

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityQuote sq) {
		
		jdbc.update(INSERT_OC_SECURITY_QUOTE, 
				sq.getKey().getSymbol(),
				sq.getLast(),
				sq.getBid(),
				sq.getAsk(),
				sq.getBidSize(),
				sq.getAskSize(),
				sq.getVolume(),
				sq.getOpen(),
				sq.getClose(),
				sq.getHigh(),
				sq.getLow(),
				sq.getDataCurrency(),
				sq.getOpenInterest());

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(Security s) {

		boolean exists = jdbc.queryForObject(EXISTS_OC_SECURITY, new Object[] { s.getKey().getSymbol() },
				Boolean.class);

		if (exists) {
			// update
			jdbc.update(UPDATE_OC_SECURITY, s.getCompanyDescription(),
					s.getCompanyName(),
					s.getCountry(),
					s.getCurrency(),
					s.getEmployeeCount(),
					s.getExchange().getCode(),
					s.getIndustry(),
					s.getInstrumentType().getId(),
					s.getLotSize(),
					s.getSector(),
					s.getState(),
					s.getPreviousClose(),
					s.getKey().getSymbol());

		} else {
			// insert
			jdbc.update(INSERT_OC_SECURITY, s.getKey().getSymbol(),
					s.getKey().getCusip(),
					s.getInstrumentType().getId(),
					s.getLotSize(),
					s.getCompanyName(),
					s.getCountry(),
					s.getState(),
					s.getEmployeeCount(),
					s.getIndustry(),
					s.getSector(),
					s.getExchange().getCode(),
					s.getCurrency(),
					s.getCompanyDescription(),
					s.getPreviousClose());
		}

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(FundamentalQuote fq) {

		
		boolean exists = jdbc.queryForObject(EXISTS_OC_FUNDAMENTAL,
				new Object[] { fq.getKey().getSymbol(), Date.valueOf(LocalDate.now()) }, Boolean.class);

		if (exists) {
			// update
			jdbc.update(UPDATE_OC_FUNDAMENTAL,
					fq.get_10DayAvgVolume(),
					fq.get_200DayAverage(),
					fq.get_50DayAverage(),
					fq.get_52WeekHigh(),
					fq.get_52WeekLow(),
					fq.getAverageVolume(),
					fq.getBeta(),
					fq.getBookValue(),
					fq.getCurrentRatio(),
					fq.getDebtToEquity(),
					fq.getDividendRate(),
					fq.getDividendYield(),
					fq.getEarningsAvg(),
					fq.getEarningsGrowth(),
					fq.getEarningsHigh(),
					fq.getEarningsLow(),
					fq.getEarningsQtrGrowth(),
					fq.getEbitda(),
					fq.getEbitdaMargins(),
					fq.getEnterpriseToEbitda(),
					fq.getEnterpriseToRevenue(),
					fq.getEnterpriseValue(),
					fq.getFloatShares(),
					fq.getForwardEps(),
					fq.getForwardPe(),
					fq.getFreeCashflow(),
					fq.getGrossMargins(),
					fq.getGrossProfits(),
					fq.getHeldPctInsiders(),
					fq.getHeldPctInstitutions(),
					fq.getMarketCap(),
					fq.getNetIncomeToCommon(),
					fq.getOperatingCashflow(),
					fq.getOperatingMargins(),
					fq.getPegRatio(),
					fq.getPriceToBook(),
					fq.getPriceToSales(),
					fq.getProfitMargins(),
					fq.getQuickRatio(),
					fq.getReturnOnAssets(),
					fq.getReturnOnEquity(),
					fq.getRevenueAvg(),
					fq.getRevenueGrowth(),
					fq.getRevenueHigh(),
					fq.getRevenueLow(),
					fq.getRevenuePerShare(),
					fq.getRevenueQtrGrowth(),
					fq.getSharesOutstanding(),
					fq.getSharesShort(),
					fq.getSharesShortPriorMonth(),
					fq.getShortPercentOfFloat(),
					fq.getShortRatio(),
					fq.getTotalAssets(),
					fq.getTotalCash(),
					fq.getTotalCashPerShare(),
					fq.getTotalDebt(),
					fq.getTotalRevenue(),
					fq.getTrailingEps(),
					fq.getTrailingPe(),
					fq.getYield(), 
					fq.getKey().getSymbol(),
					fq.getDate());

		} else {
			// insert
			jdbc.update(INSERT_OC_FUNDAMENTAL, fq.getKey().getSymbol(),
					fq.getDate(),
					fq.get_10DayAvgVolume(),
					fq.get_200DayAverage(),
					fq.get_50DayAverage(),
					fq.get_52WeekHigh(),
					fq.get_52WeekLow(),
					fq.getAverageVolume(),
					fq.getBeta(),
					fq.getBookValue(),
					fq.getCurrentRatio(),
					fq.getDebtToEquity(),
					fq.getDividendRate(),
					fq.getDividendYield(),
					fq.getEarningsAvg(),
					fq.getEarningsGrowth(),
					fq.getEarningsHigh(),
					fq.getEarningsLow(),
					fq.getEarningsQtrGrowth(),
					fq.getEbitda(),
					fq.getEbitdaMargins(),
					fq.getEnterpriseToEbitda(),
					fq.getEnterpriseToRevenue(),
					fq.getEnterpriseValue(),
					fq.getFloatShares(),
					fq.getForwardEps(),
					fq.getForwardPe(),
					fq.getFreeCashflow(),
					fq.getGrossMargins(),
					fq.getGrossProfits(),
					fq.getHeldPctInsiders(),
					fq.getHeldPctInstitutions(),
					fq.getMarketCap(),
					fq.getNetIncomeToCommon(),
					fq.getOperatingCashflow(),
					fq.getOperatingMargins(),
					fq.getPegRatio(),
					fq.getPriceToBook(),
					fq.getPriceToSales(),
					fq.getProfitMargins(),
					fq.getQuickRatio(),
					fq.getReturnOnAssets(),
					fq.getReturnOnEquity(),
					fq.getRevenueAvg(),
					fq.getRevenueGrowth(),
					fq.getRevenueHigh(),
					fq.getRevenueLow(),
					fq.getRevenuePerShare(),
					fq.getRevenueQtrGrowth(),
					fq.getSharesOutstanding(),
					fq.getSharesShort(),
					fq.getSharesShortPriorMonth(),
					fq.getShortPercentOfFloat(),
					fq.getShortRatio(),
					fq.getTotalAssets(),
					fq.getTotalCash(),
					fq.getTotalCashPerShare(),
					fq.getTotalDebt(),
					fq.getTotalRevenue(),
					fq.getTrailingEps(),
					fq.getTrailingPe(),
					fq.getYield());
		}

		
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(EnhancedSecurity fq) {
		// TODO Auto-generated method stub

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
					s.getNextRevenue() == null ? null : Date.valueOf(s.getNextRevenue()),
					s.getKey().getSymbol());

		} else {
			// insert
			jdbc.update(INSERT_OC_SECURITY_NEXT_EVENT, 
					s.getKey().getSymbol(),
					s.getNextEarningsDateEstLow() == null ? null : Date.valueOf(s.getNextEarningsDateEstLow()),
					s.getNextEarningsDateEstHigh() == null ? null : Date.valueOf(s.getNextEarningsDateEstHigh()),
					s.getNextDivDate() == null ? null : Date.valueOf(s.getNextDivDate()),
					s.getNextExDivDate() == null ? null : Date.valueOf(s.getNextExDivDate()),
					s.getNextRevenue() == null ? null : Date.valueOf(s.getNextRevenue()));
		}

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persistEvents(List<SecurityEvent> events) {
		// TODO Auto-generated method stub

	}

	public List<HistoricalQuote> findHistoricals(SecurityKey key, LocalDate minDate, LocalDate maxDate) {

		return jdbc.queryForList(SELECT_OHLC_HIST, HistoricalQuote.class, key.getSymbol(), Date.valueOf(minDate),
				Date.valueOf(maxDate));

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void deleteHistoricals(SecurityKey key) {
		jdbc.update(DELETE_OHLC_HIST_FOR_SYMBOL, key.getSymbol());
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persistHistoricals(final List<HistoricalQuote> hist) {

		jdbc.batchUpdate(INSERT_OHLC_HIST, new BatchPreparedStatementSetter() {

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

			public int getBatchSize() {
				return hist.size();
			}
		});

	}

	public List<Security> findSecurities() {
		return jdbc.query(SELECT_OC_SECURITIES, new SecurityRowMapper());
	}
}
