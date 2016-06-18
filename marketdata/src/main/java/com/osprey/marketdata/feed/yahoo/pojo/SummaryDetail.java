
package com.osprey.marketdata.feed.yahoo.pojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "maxAge",
    "previousClose",
    "open",
    "dayLow",
    "dayHigh",
    "regularMarketPreviousClose",
    "regularMarketOpen",
    "regularMarketDayLow",
    "regularMarketDayHigh",
    "dividendRate",
    "dividendYield",
    "exDividendDate",
    "payoutRatio",
    "fiveYearAvgDividendYield",
    "beta",
    "trailingPE",
    "forwardPE",
    "volume",
    "regularMarketVolume",
    "averageVolume",
    "averageVolume10days",
    "averageDailyVolume10Day",
    "bid",
    "ask",
    "bidSize",
    "askSize",
    "marketCap",
    "yield",
    "ytdReturn",
    "totalAssets",
    "expireDate",
    "strikePrice",
    "openInterest",
    "fiftyTwoWeekLow",
    "fiftyTwoWeekHigh",
    "priceToSalesTrailing12Months",
    "fiftyDayAverage",
    "twoHundredDayAverage",
    "trailingAnnualDividendRate",
    "trailingAnnualDividendYield",
    "navPrice"
})
public class SummaryDetail {

    @JsonProperty("maxAge")
    private Integer maxAge;
    @JsonProperty("previousClose")
    private PreviousClose previousClose;
    @JsonProperty("open")
    private Open open;
    @JsonProperty("dayLow")
    private DayLow dayLow;
    @JsonProperty("dayHigh")
    private DayHigh dayHigh;
    @JsonProperty("regularMarketPreviousClose")
    private RegularMarketPreviousClose regularMarketPreviousClose;
    @JsonProperty("regularMarketOpen")
    private RegularMarketOpen regularMarketOpen;
    @JsonProperty("regularMarketDayLow")
    private RegularMarketDayLow regularMarketDayLow;
    @JsonProperty("regularMarketDayHigh")
    private RegularMarketDayHigh regularMarketDayHigh;
    @JsonProperty("dividendRate")
    private DividendRate dividendRate;
    @JsonProperty("dividendYield")
    private DividendYield dividendYield;
    @JsonProperty("exDividendDate")
    private ExDividendDate exDividendDate;
    @JsonProperty("payoutRatio")
    private PayoutRatio payoutRatio;
    @JsonProperty("fiveYearAvgDividendYield")
    private FiveYearAvgDividendYield fiveYearAvgDividendYield;
    @JsonProperty("beta")
    private Beta beta;
    @JsonProperty("trailingPE")
    private TrailingPE trailingPE;
    @JsonProperty("forwardPE")
    private ForwardPE forwardPE;
    @JsonProperty("volume")
    private Volume volume;
    @JsonProperty("regularMarketVolume")
    private RegularMarketVolume regularMarketVolume;
    @JsonProperty("averageVolume")
    private AverageVolume averageVolume;
    @JsonProperty("averageVolume10days")
    private AverageVolume10days averageVolume10days;
    @JsonProperty("averageDailyVolume10Day")
    private AverageDailyVolume10Day averageDailyVolume10Day;
    @JsonProperty("bid")
    private Bid bid;
    @JsonProperty("ask")
    private Ask ask;
    @JsonProperty("bidSize")
    private BidSize bidSize;
    @JsonProperty("askSize")
    private AskSize askSize;
    @JsonProperty("marketCap")
    private MarketCap marketCap;
    @JsonProperty("yield")
    private Yield yield;
    @JsonProperty("ytdReturn")
    private YtdReturn ytdReturn;
    @JsonProperty("totalAssets")
    private TotalAssets totalAssets;
    @JsonProperty("expireDate")
    private ExpireDate expireDate;
    @JsonProperty("strikePrice")
    private StrikePrice strikePrice;
    @JsonProperty("openInterest")
    private OpenInterest openInterest;
    @JsonProperty("fiftyTwoWeekLow")
    private FiftyTwoWeekLow fiftyTwoWeekLow;
    @JsonProperty("fiftyTwoWeekHigh")
    private FiftyTwoWeekHigh fiftyTwoWeekHigh;
    @JsonProperty("priceToSalesTrailing12Months")
    private PriceToSalesTrailing12Months priceToSalesTrailing12Months;
    @JsonProperty("fiftyDayAverage")
    private FiftyDayAverage fiftyDayAverage;
    @JsonProperty("twoHundredDayAverage")
    private TwoHundredDayAverage twoHundredDayAverage;
    @JsonProperty("trailingAnnualDividendRate")
    private TrailingAnnualDividendRate trailingAnnualDividendRate;
    @JsonProperty("trailingAnnualDividendYield")
    private TrailingAnnualDividendYield trailingAnnualDividendYield;
    @JsonProperty("navPrice")
    private NavPrice navPrice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * 
     * @param maxAge
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * 
     * @return
     *     The previousClose
     */
    @JsonProperty("previousClose")
    public PreviousClose getPreviousClose() {
        return previousClose;
    }

    /**
     * 
     * @param previousClose
     *     The previousClose
     */
    @JsonProperty("previousClose")
    public void setPreviousClose(PreviousClose previousClose) {
        this.previousClose = previousClose;
    }

    /**
     * 
     * @return
     *     The open
     */
    @JsonProperty("open")
    public Open getOpen() {
        return open;
    }

    /**
     * 
     * @param open
     *     The open
     */
    @JsonProperty("open")
    public void setOpen(Open open) {
        this.open = open;
    }

    /**
     * 
     * @return
     *     The dayLow
     */
    @JsonProperty("dayLow")
    public DayLow getDayLow() {
        return dayLow;
    }

    /**
     * 
     * @param dayLow
     *     The dayLow
     */
    @JsonProperty("dayLow")
    public void setDayLow(DayLow dayLow) {
        this.dayLow = dayLow;
    }

    /**
     * 
     * @return
     *     The dayHigh
     */
    @JsonProperty("dayHigh")
    public DayHigh getDayHigh() {
        return dayHigh;
    }

    /**
     * 
     * @param dayHigh
     *     The dayHigh
     */
    @JsonProperty("dayHigh")
    public void setDayHigh(DayHigh dayHigh) {
        this.dayHigh = dayHigh;
    }

    /**
     * 
     * @return
     *     The regularMarketPreviousClose
     */
    @JsonProperty("regularMarketPreviousClose")
    public RegularMarketPreviousClose getRegularMarketPreviousClose() {
        return regularMarketPreviousClose;
    }

    /**
     * 
     * @param regularMarketPreviousClose
     *     The regularMarketPreviousClose
     */
    @JsonProperty("regularMarketPreviousClose")
    public void setRegularMarketPreviousClose(RegularMarketPreviousClose regularMarketPreviousClose) {
        this.regularMarketPreviousClose = regularMarketPreviousClose;
    }

    /**
     * 
     * @return
     *     The regularMarketOpen
     */
    @JsonProperty("regularMarketOpen")
    public RegularMarketOpen getRegularMarketOpen() {
        return regularMarketOpen;
    }

    /**
     * 
     * @param regularMarketOpen
     *     The regularMarketOpen
     */
    @JsonProperty("regularMarketOpen")
    public void setRegularMarketOpen(RegularMarketOpen regularMarketOpen) {
        this.regularMarketOpen = regularMarketOpen;
    }

    /**
     * 
     * @return
     *     The regularMarketDayLow
     */
    @JsonProperty("regularMarketDayLow")
    public RegularMarketDayLow getRegularMarketDayLow() {
        return regularMarketDayLow;
    }

    /**
     * 
     * @param regularMarketDayLow
     *     The regularMarketDayLow
     */
    @JsonProperty("regularMarketDayLow")
    public void setRegularMarketDayLow(RegularMarketDayLow regularMarketDayLow) {
        this.regularMarketDayLow = regularMarketDayLow;
    }

    /**
     * 
     * @return
     *     The regularMarketDayHigh
     */
    @JsonProperty("regularMarketDayHigh")
    public RegularMarketDayHigh getRegularMarketDayHigh() {
        return regularMarketDayHigh;
    }

    /**
     * 
     * @param regularMarketDayHigh
     *     The regularMarketDayHigh
     */
    @JsonProperty("regularMarketDayHigh")
    public void setRegularMarketDayHigh(RegularMarketDayHigh regularMarketDayHigh) {
        this.regularMarketDayHigh = regularMarketDayHigh;
    }

    /**
     * 
     * @return
     *     The dividendRate
     */
    @JsonProperty("dividendRate")
    public DividendRate getDividendRate() {
        return dividendRate;
    }

    /**
     * 
     * @param dividendRate
     *     The dividendRate
     */
    @JsonProperty("dividendRate")
    public void setDividendRate(DividendRate dividendRate) {
        this.dividendRate = dividendRate;
    }

    /**
     * 
     * @return
     *     The dividendYield
     */
    @JsonProperty("dividendYield")
    public DividendYield getDividendYield() {
        return dividendYield;
    }

    /**
     * 
     * @param dividendYield
     *     The dividendYield
     */
    @JsonProperty("dividendYield")
    public void setDividendYield(DividendYield dividendYield) {
        this.dividendYield = dividendYield;
    }

    /**
     * 
     * @return
     *     The exDividendDate
     */
    @JsonProperty("exDividendDate")
    public ExDividendDate getExDividendDate() {
        return exDividendDate;
    }

    /**
     * 
     * @param exDividendDate
     *     The exDividendDate
     */
    @JsonProperty("exDividendDate")
    public void setExDividendDate(ExDividendDate exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    /**
     * 
     * @return
     *     The payoutRatio
     */
    @JsonProperty("payoutRatio")
    public PayoutRatio getPayoutRatio() {
        return payoutRatio;
    }

    /**
     * 
     * @param payoutRatio
     *     The payoutRatio
     */
    @JsonProperty("payoutRatio")
    public void setPayoutRatio(PayoutRatio payoutRatio) {
        this.payoutRatio = payoutRatio;
    }

    /**
     * 
     * @return
     *     The fiveYearAvgDividendYield
     */
    @JsonProperty("fiveYearAvgDividendYield")
    public FiveYearAvgDividendYield getFiveYearAvgDividendYield() {
        return fiveYearAvgDividendYield;
    }

    /**
     * 
     * @param fiveYearAvgDividendYield
     *     The fiveYearAvgDividendYield
     */
    @JsonProperty("fiveYearAvgDividendYield")
    public void setFiveYearAvgDividendYield(FiveYearAvgDividendYield fiveYearAvgDividendYield) {
        this.fiveYearAvgDividendYield = fiveYearAvgDividendYield;
    }

    /**
     * 
     * @return
     *     The beta
     */
    @JsonProperty("beta")
    public Beta getBeta() {
        return beta;
    }

    /**
     * 
     * @param beta
     *     The beta
     */
    @JsonProperty("beta")
    public void setBeta(Beta beta) {
        this.beta = beta;
    }

    /**
     * 
     * @return
     *     The trailingPE
     */
    @JsonProperty("trailingPE")
    public TrailingPE getTrailingPE() {
        return trailingPE;
    }

    /**
     * 
     * @param trailingPE
     *     The trailingPE
     */
    @JsonProperty("trailingPE")
    public void setTrailingPE(TrailingPE trailingPE) {
        this.trailingPE = trailingPE;
    }

    /**
     * 
     * @return
     *     The forwardPE
     */
    @JsonProperty("forwardPE")
    public ForwardPE getForwardPE() {
        return forwardPE;
    }

    /**
     * 
     * @param forwardPE
     *     The forwardPE
     */
    @JsonProperty("forwardPE")
    public void setForwardPE(ForwardPE forwardPE) {
        this.forwardPE = forwardPE;
    }

    /**
     * 
     * @return
     *     The volume
     */
    @JsonProperty("volume")
    public Volume getVolume() {
        return volume;
    }

    /**
     * 
     * @param volume
     *     The volume
     */
    @JsonProperty("volume")
    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    /**
     * 
     * @return
     *     The regularMarketVolume
     */
    @JsonProperty("regularMarketVolume")
    public RegularMarketVolume getRegularMarketVolume() {
        return regularMarketVolume;
    }

    /**
     * 
     * @param regularMarketVolume
     *     The regularMarketVolume
     */
    @JsonProperty("regularMarketVolume")
    public void setRegularMarketVolume(RegularMarketVolume regularMarketVolume) {
        this.regularMarketVolume = regularMarketVolume;
    }

    /**
     * 
     * @return
     *     The averageVolume
     */
    @JsonProperty("averageVolume")
    public AverageVolume getAverageVolume() {
        return averageVolume;
    }

    /**
     * 
     * @param averageVolume
     *     The averageVolume
     */
    @JsonProperty("averageVolume")
    public void setAverageVolume(AverageVolume averageVolume) {
        this.averageVolume = averageVolume;
    }

    /**
     * 
     * @return
     *     The averageVolume10days
     */
    @JsonProperty("averageVolume10days")
    public AverageVolume10days getAverageVolume10days() {
        return averageVolume10days;
    }

    /**
     * 
     * @param averageVolume10days
     *     The averageVolume10days
     */
    @JsonProperty("averageVolume10days")
    public void setAverageVolume10days(AverageVolume10days averageVolume10days) {
        this.averageVolume10days = averageVolume10days;
    }

    /**
     * 
     * @return
     *     The averageDailyVolume10Day
     */
    @JsonProperty("averageDailyVolume10Day")
    public AverageDailyVolume10Day getAverageDailyVolume10Day() {
        return averageDailyVolume10Day;
    }

    /**
     * 
     * @param averageDailyVolume10Day
     *     The averageDailyVolume10Day
     */
    @JsonProperty("averageDailyVolume10Day")
    public void setAverageDailyVolume10Day(AverageDailyVolume10Day averageDailyVolume10Day) {
        this.averageDailyVolume10Day = averageDailyVolume10Day;
    }

    /**
     * 
     * @return
     *     The bid
     */
    @JsonProperty("bid")
    public Bid getBid() {
        return bid;
    }

    /**
     * 
     * @param bid
     *     The bid
     */
    @JsonProperty("bid")
    public void setBid(Bid bid) {
        this.bid = bid;
    }

    /**
     * 
     * @return
     *     The ask
     */
    @JsonProperty("ask")
    public Ask getAsk() {
        return ask;
    }

    /**
     * 
     * @param ask
     *     The ask
     */
    @JsonProperty("ask")
    public void setAsk(Ask ask) {
        this.ask = ask;
    }

    /**
     * 
     * @return
     *     The bidSize
     */
    @JsonProperty("bidSize")
    public BidSize getBidSize() {
        return bidSize;
    }

    /**
     * 
     * @param bidSize
     *     The bidSize
     */
    @JsonProperty("bidSize")
    public void setBidSize(BidSize bidSize) {
        this.bidSize = bidSize;
    }

    /**
     * 
     * @return
     *     The askSize
     */
    @JsonProperty("askSize")
    public AskSize getAskSize() {
        return askSize;
    }

    /**
     * 
     * @param askSize
     *     The askSize
     */
    @JsonProperty("askSize")
    public void setAskSize(AskSize askSize) {
        this.askSize = askSize;
    }

    /**
     * 
     * @return
     *     The marketCap
     */
    @JsonProperty("marketCap")
    public MarketCap getMarketCap() {
        return marketCap;
    }

    /**
     * 
     * @param marketCap
     *     The marketCap
     */
    @JsonProperty("marketCap")
    public void setMarketCap(MarketCap marketCap) {
        this.marketCap = marketCap;
    }

    /**
     * 
     * @return
     *     The yield
     */
    @JsonProperty("yield")
    public Yield getYield() {
        return yield;
    }

    /**
     * 
     * @param yield
     *     The yield
     */
    @JsonProperty("yield")
    public void setYield(Yield yield) {
        this.yield = yield;
    }

    /**
     * 
     * @return
     *     The ytdReturn
     */
    @JsonProperty("ytdReturn")
    public YtdReturn getYtdReturn() {
        return ytdReturn;
    }

    /**
     * 
     * @param ytdReturn
     *     The ytdReturn
     */
    @JsonProperty("ytdReturn")
    public void setYtdReturn(YtdReturn ytdReturn) {
        this.ytdReturn = ytdReturn;
    }

    /**
     * 
     * @return
     *     The totalAssets
     */
    @JsonProperty("totalAssets")
    public TotalAssets getTotalAssets() {
        return totalAssets;
    }

    /**
     * 
     * @param totalAssets
     *     The totalAssets
     */
    @JsonProperty("totalAssets")
    public void setTotalAssets(TotalAssets totalAssets) {
        this.totalAssets = totalAssets;
    }

    /**
     * 
     * @return
     *     The expireDate
     */
    @JsonProperty("expireDate")
    public ExpireDate getExpireDate() {
        return expireDate;
    }

    /**
     * 
     * @param expireDate
     *     The expireDate
     */
    @JsonProperty("expireDate")
    public void setExpireDate(ExpireDate expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * 
     * @return
     *     The strikePrice
     */
    @JsonProperty("strikePrice")
    public StrikePrice getStrikePrice() {
        return strikePrice;
    }

    /**
     * 
     * @param strikePrice
     *     The strikePrice
     */
    @JsonProperty("strikePrice")
    public void setStrikePrice(StrikePrice strikePrice) {
        this.strikePrice = strikePrice;
    }

    /**
     * 
     * @return
     *     The openInterest
     */
    @JsonProperty("openInterest")
    public OpenInterest getOpenInterest() {
        return openInterest;
    }

    /**
     * 
     * @param openInterest
     *     The openInterest
     */
    @JsonProperty("openInterest")
    public void setOpenInterest(OpenInterest openInterest) {
        this.openInterest = openInterest;
    }

    /**
     * 
     * @return
     *     The fiftyTwoWeekLow
     */
    @JsonProperty("fiftyTwoWeekLow")
    public FiftyTwoWeekLow getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    /**
     * 
     * @param fiftyTwoWeekLow
     *     The fiftyTwoWeekLow
     */
    @JsonProperty("fiftyTwoWeekLow")
    public void setFiftyTwoWeekLow(FiftyTwoWeekLow fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    /**
     * 
     * @return
     *     The fiftyTwoWeekHigh
     */
    @JsonProperty("fiftyTwoWeekHigh")
    public FiftyTwoWeekHigh getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    /**
     * 
     * @param fiftyTwoWeekHigh
     *     The fiftyTwoWeekHigh
     */
    @JsonProperty("fiftyTwoWeekHigh")
    public void setFiftyTwoWeekHigh(FiftyTwoWeekHigh fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    /**
     * 
     * @return
     *     The priceToSalesTrailing12Months
     */
    @JsonProperty("priceToSalesTrailing12Months")
    public PriceToSalesTrailing12Months getPriceToSalesTrailing12Months() {
        return priceToSalesTrailing12Months;
    }

    /**
     * 
     * @param priceToSalesTrailing12Months
     *     The priceToSalesTrailing12Months
     */
    @JsonProperty("priceToSalesTrailing12Months")
    public void setPriceToSalesTrailing12Months(PriceToSalesTrailing12Months priceToSalesTrailing12Months) {
        this.priceToSalesTrailing12Months = priceToSalesTrailing12Months;
    }

    /**
     * 
     * @return
     *     The fiftyDayAverage
     */
    @JsonProperty("fiftyDayAverage")
    public FiftyDayAverage getFiftyDayAverage() {
        return fiftyDayAverage;
    }

    /**
     * 
     * @param fiftyDayAverage
     *     The fiftyDayAverage
     */
    @JsonProperty("fiftyDayAverage")
    public void setFiftyDayAverage(FiftyDayAverage fiftyDayAverage) {
        this.fiftyDayAverage = fiftyDayAverage;
    }

    /**
     * 
     * @return
     *     The twoHundredDayAverage
     */
    @JsonProperty("twoHundredDayAverage")
    public TwoHundredDayAverage getTwoHundredDayAverage() {
        return twoHundredDayAverage;
    }

    /**
     * 
     * @param twoHundredDayAverage
     *     The twoHundredDayAverage
     */
    @JsonProperty("twoHundredDayAverage")
    public void setTwoHundredDayAverage(TwoHundredDayAverage twoHundredDayAverage) {
        this.twoHundredDayAverage = twoHundredDayAverage;
    }

    /**
     * 
     * @return
     *     The trailingAnnualDividendRate
     */
    @JsonProperty("trailingAnnualDividendRate")
    public TrailingAnnualDividendRate getTrailingAnnualDividendRate() {
        return trailingAnnualDividendRate;
    }

    /**
     * 
     * @param trailingAnnualDividendRate
     *     The trailingAnnualDividendRate
     */
    @JsonProperty("trailingAnnualDividendRate")
    public void setTrailingAnnualDividendRate(TrailingAnnualDividendRate trailingAnnualDividendRate) {
        this.trailingAnnualDividendRate = trailingAnnualDividendRate;
    }

    /**
     * 
     * @return
     *     The trailingAnnualDividendYield
     */
    @JsonProperty("trailingAnnualDividendYield")
    public TrailingAnnualDividendYield getTrailingAnnualDividendYield() {
        return trailingAnnualDividendYield;
    }

    /**
     * 
     * @param trailingAnnualDividendYield
     *     The trailingAnnualDividendYield
     */
    @JsonProperty("trailingAnnualDividendYield")
    public void setTrailingAnnualDividendYield(TrailingAnnualDividendYield trailingAnnualDividendYield) {
        this.trailingAnnualDividendYield = trailingAnnualDividendYield;
    }

    /**
     * 
     * @return
     *     The navPrice
     */
    @JsonProperty("navPrice")
    public NavPrice getNavPrice() {
        return navPrice;
    }

    /**
     * 
     * @param navPrice
     *     The navPrice
     */
    @JsonProperty("navPrice")
    public void setNavPrice(NavPrice navPrice) {
        this.navPrice = navPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
