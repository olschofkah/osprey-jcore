
package com.osprey.marketdata.feed.yahoo.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "maxAge",
    "preMarketChangePercent",
    "preMarketChange",
    "preMarketTime",
    "preMarketPrice",
    "preMarketSource",
    "postMarketChangePercent",
    "postMarketChange",
    "postMarketTime",
    "postMarketPrice",
    "postMarketSource",
    "regularMarketChangePercent",
    "regularMarketChange",
    "regularMarketTime",
    "regularMarketPrice",
    "regularMarketDayHigh",
    "regularMarketDayLow",
    "regularMarketVolume",
    "averageDailyVolume10Day",
    "averageDailyVolume3Month",
    "regularMarketPreviousClose",
    "regularMarketSource",
    "regularMarketOpen",
    "strikePrice",
    "openInterest",
    "exchange",
    "exchangeName",
    "marketState",
    "quoteType",
    "symbol",
    "underlyingSymbol",
    "shortName",
    "longName",
    "currency",
    "currencySymbol"
})
public class Price {

    @JsonProperty("maxAge")
    private Integer maxAge;
    @JsonProperty("preMarketChangePercent")
    private PreMarketChangePercent preMarketChangePercent;
    @JsonProperty("preMarketChange")
    private PreMarketChange preMarketChange;
    @JsonProperty("preMarketTime")
    private Integer preMarketTime;
    @JsonProperty("preMarketPrice")
    private PreMarketPrice preMarketPrice;
    @JsonProperty("preMarketSource")
    private String preMarketSource;
    @JsonProperty("postMarketChangePercent")
    private PostMarketChangePercent postMarketChangePercent;
    @JsonProperty("postMarketChange")
    private PostMarketChange postMarketChange;
    @JsonProperty("postMarketTime")
    private Integer postMarketTime;
    @JsonProperty("postMarketPrice")
    private PostMarketPrice postMarketPrice;
    @JsonProperty("postMarketSource")
    private String postMarketSource;
    @JsonProperty("regularMarketChangePercent")
    private RegularMarketChangePercent regularMarketChangePercent;
    @JsonProperty("regularMarketChange")
    private RegularMarketChange regularMarketChange;
    @JsonProperty("regularMarketTime")
    private Integer regularMarketTime;
    @JsonProperty("regularMarketPrice")
    private RegularMarketPrice regularMarketPrice;
    @JsonProperty("regularMarketDayHigh")
    private RegularMarketDayHigh regularMarketDayHigh;
    @JsonProperty("regularMarketDayLow")
    private RegularMarketDayLow regularMarketDayLow;
    @JsonProperty("regularMarketVolume")
    private RegularMarketVolume regularMarketVolume;
    @JsonProperty("averageDailyVolume10Day")
    private AverageDailyVolume10Day averageDailyVolume10Day;
    @JsonProperty("averageDailyVolume3Month")
    private AverageDailyVolume3Month averageDailyVolume3Month;
    @JsonProperty("regularMarketPreviousClose")
    private RegularMarketPreviousClose regularMarketPreviousClose;
    @JsonProperty("regularMarketSource")
    private String regularMarketSource;
    @JsonProperty("regularMarketOpen")
    private RegularMarketOpen regularMarketOpen;
    @JsonProperty("strikePrice")
    private StrikePrice strikePrice;
    @JsonProperty("openInterest")
    private OpenInterest openInterest;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("exchangeName")
    private String exchangeName;
    @JsonProperty("marketState")
    private String marketState;
    @JsonProperty("quoteType")
    private String quoteType;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("underlyingSymbol")
    private Object underlyingSymbol;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("longName")
    private String longName;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("currencySymbol")
    private String currencySymbol;

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
     *     The preMarketChangePercent
     */
    @JsonProperty("preMarketChangePercent")
    public PreMarketChangePercent getPreMarketChangePercent() {
        return preMarketChangePercent;
    }

    /**
     * 
     * @param preMarketChangePercent
     *     The preMarketChangePercent
     */
    @JsonProperty("preMarketChangePercent")
    public void setPreMarketChangePercent(PreMarketChangePercent preMarketChangePercent) {
        this.preMarketChangePercent = preMarketChangePercent;
    }

    /**
     * 
     * @return
     *     The preMarketChange
     */
    @JsonProperty("preMarketChange")
    public PreMarketChange getPreMarketChange() {
        return preMarketChange;
    }

    /**
     * 
     * @param preMarketChange
     *     The preMarketChange
     */
    @JsonProperty("preMarketChange")
    public void setPreMarketChange(PreMarketChange preMarketChange) {
        this.preMarketChange = preMarketChange;
    }

    /**
     * 
     * @return
     *     The preMarketTime
     */
    @JsonProperty("preMarketTime")
    public Integer getPreMarketTime() {
        return preMarketTime;
    }

    /**
     * 
     * @param preMarketTime
     *     The preMarketTime
     */
    @JsonProperty("preMarketTime")
    public void setPreMarketTime(Integer preMarketTime) {
        this.preMarketTime = preMarketTime;
    }

    /**
     * 
     * @return
     *     The preMarketPrice
     */
    @JsonProperty("preMarketPrice")
    public PreMarketPrice getPreMarketPrice() {
        return preMarketPrice;
    }

    /**
     * 
     * @param preMarketPrice
     *     The preMarketPrice
     */
    @JsonProperty("preMarketPrice")
    public void setPreMarketPrice(PreMarketPrice preMarketPrice) {
        this.preMarketPrice = preMarketPrice;
    }

    /**
     * 
     * @return
     *     The preMarketSource
     */
    @JsonProperty("preMarketSource")
    public String getPreMarketSource() {
        return preMarketSource;
    }

    /**
     * 
     * @param preMarketSource
     *     The preMarketSource
     */
    @JsonProperty("preMarketSource")
    public void setPreMarketSource(String preMarketSource) {
        this.preMarketSource = preMarketSource;
    }

    /**
     * 
     * @return
     *     The postMarketChangePercent
     */
    @JsonProperty("postMarketChangePercent")
    public PostMarketChangePercent getPostMarketChangePercent() {
        return postMarketChangePercent;
    }

    /**
     * 
     * @param postMarketChangePercent
     *     The postMarketChangePercent
     */
    @JsonProperty("postMarketChangePercent")
    public void setPostMarketChangePercent(PostMarketChangePercent postMarketChangePercent) {
        this.postMarketChangePercent = postMarketChangePercent;
    }

    /**
     * 
     * @return
     *     The postMarketChange
     */
    @JsonProperty("postMarketChange")
    public PostMarketChange getPostMarketChange() {
        return postMarketChange;
    }

    /**
     * 
     * @param postMarketChange
     *     The postMarketChange
     */
    @JsonProperty("postMarketChange")
    public void setPostMarketChange(PostMarketChange postMarketChange) {
        this.postMarketChange = postMarketChange;
    }

    /**
     * 
     * @return
     *     The postMarketTime
     */
    @JsonProperty("postMarketTime")
    public Integer getPostMarketTime() {
        return postMarketTime;
    }

    /**
     * 
     * @param postMarketTime
     *     The postMarketTime
     */
    @JsonProperty("postMarketTime")
    public void setPostMarketTime(Integer postMarketTime) {
        this.postMarketTime = postMarketTime;
    }

    /**
     * 
     * @return
     *     The postMarketPrice
     */
    @JsonProperty("postMarketPrice")
    public PostMarketPrice getPostMarketPrice() {
        return postMarketPrice;
    }

    /**
     * 
     * @param postMarketPrice
     *     The postMarketPrice
     */
    @JsonProperty("postMarketPrice")
    public void setPostMarketPrice(PostMarketPrice postMarketPrice) {
        this.postMarketPrice = postMarketPrice;
    }

    /**
     * 
     * @return
     *     The postMarketSource
     */
    @JsonProperty("postMarketSource")
    public String getPostMarketSource() {
        return postMarketSource;
    }

    /**
     * 
     * @param postMarketSource
     *     The postMarketSource
     */
    @JsonProperty("postMarketSource")
    public void setPostMarketSource(String postMarketSource) {
        this.postMarketSource = postMarketSource;
    }

    /**
     * 
     * @return
     *     The regularMarketChangePercent
     */
    @JsonProperty("regularMarketChangePercent")
    public RegularMarketChangePercent getRegularMarketChangePercent() {
        return regularMarketChangePercent;
    }

    /**
     * 
     * @param regularMarketChangePercent
     *     The regularMarketChangePercent
     */
    @JsonProperty("regularMarketChangePercent")
    public void setRegularMarketChangePercent(RegularMarketChangePercent regularMarketChangePercent) {
        this.regularMarketChangePercent = regularMarketChangePercent;
    }

    /**
     * 
     * @return
     *     The regularMarketChange
     */
    @JsonProperty("regularMarketChange")
    public RegularMarketChange getRegularMarketChange() {
        return regularMarketChange;
    }

    /**
     * 
     * @param regularMarketChange
     *     The regularMarketChange
     */
    @JsonProperty("regularMarketChange")
    public void setRegularMarketChange(RegularMarketChange regularMarketChange) {
        this.regularMarketChange = regularMarketChange;
    }

    /**
     * 
     * @return
     *     The regularMarketTime
     */
    @JsonProperty("regularMarketTime")
    public Integer getRegularMarketTime() {
        return regularMarketTime;
    }

    /**
     * 
     * @param regularMarketTime
     *     The regularMarketTime
     */
    @JsonProperty("regularMarketTime")
    public void setRegularMarketTime(Integer regularMarketTime) {
        this.regularMarketTime = regularMarketTime;
    }

    /**
     * 
     * @return
     *     The regularMarketPrice
     */
    @JsonProperty("regularMarketPrice")
    public RegularMarketPrice getRegularMarketPrice() {
        return regularMarketPrice;
    }

    /**
     * 
     * @param regularMarketPrice
     *     The regularMarketPrice
     */
    @JsonProperty("regularMarketPrice")
    public void setRegularMarketPrice(RegularMarketPrice regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
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
     *     The averageDailyVolume3Month
     */
    @JsonProperty("averageDailyVolume3Month")
    public AverageDailyVolume3Month getAverageDailyVolume3Month() {
        return averageDailyVolume3Month;
    }

    /**
     * 
     * @param averageDailyVolume3Month
     *     The averageDailyVolume3Month
     */
    @JsonProperty("averageDailyVolume3Month")
    public void setAverageDailyVolume3Month(AverageDailyVolume3Month averageDailyVolume3Month) {
        this.averageDailyVolume3Month = averageDailyVolume3Month;
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
     *     The regularMarketSource
     */
    @JsonProperty("regularMarketSource")
    public String getRegularMarketSource() {
        return regularMarketSource;
    }

    /**
     * 
     * @param regularMarketSource
     *     The regularMarketSource
     */
    @JsonProperty("regularMarketSource")
    public void setRegularMarketSource(String regularMarketSource) {
        this.regularMarketSource = regularMarketSource;
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
     *     The exchange
     */
    @JsonProperty("exchange")
    public String getExchange() {
        return exchange;
    }

    /**
     * 
     * @param exchange
     *     The exchange
     */
    @JsonProperty("exchange")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * 
     * @return
     *     The exchangeName
     */
    @JsonProperty("exchangeName")
    public String getExchangeName() {
        return exchangeName;
    }

    /**
     * 
     * @param exchangeName
     *     The exchangeName
     */
    @JsonProperty("exchangeName")
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    /**
     * 
     * @return
     *     The marketState
     */
    @JsonProperty("marketState")
    public String getMarketState() {
        return marketState;
    }

    /**
     * 
     * @param marketState
     *     The marketState
     */
    @JsonProperty("marketState")
    public void setMarketState(String marketState) {
        this.marketState = marketState;
    }

    /**
     * 
     * @return
     *     The quoteType
     */
    @JsonProperty("quoteType")
    public String getQuoteType() {
        return quoteType;
    }

    /**
     * 
     * @param quoteType
     *     The quoteType
     */
    @JsonProperty("quoteType")
    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    /**
     * 
     * @return
     *     The symbol
     */
    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     * @param symbol
     *     The symbol
     */
    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 
     * @return
     *     The underlyingSymbol
     */
    @JsonProperty("underlyingSymbol")
    public Object getUnderlyingSymbol() {
        return underlyingSymbol;
    }

    /**
     * 
     * @param underlyingSymbol
     *     The underlyingSymbol
     */
    @JsonProperty("underlyingSymbol")
    public void setUnderlyingSymbol(Object underlyingSymbol) {
        this.underlyingSymbol = underlyingSymbol;
    }

    /**
     * 
     * @return
     *     The shortName
     */
    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * @param shortName
     *     The shortName
     */
    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * @return
     *     The longName
     */
    @JsonProperty("longName")
    public String getLongName() {
        return longName;
    }

    /**
     * 
     * @param longName
     *     The longName
     */
    @JsonProperty("longName")
    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The currencySymbol
     */
    @JsonProperty("currencySymbol")
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * 
     * @param currencySymbol
     *     The currencySymbol
     */
    @JsonProperty("currencySymbol")
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
