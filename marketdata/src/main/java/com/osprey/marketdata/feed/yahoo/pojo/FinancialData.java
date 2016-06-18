
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
    "currentPrice",
    "targetHighPrice",
    "targetLowPrice",
    "targetMeanPrice",
    "targetMedianPrice",
    "recommendationMean",
    "recommendationKey",
    "numberOfAnalystOpinions",
    "totalCash",
    "totalCashPerShare",
    "ebitda",
    "totalDebt",
    "quickRatio",
    "currentRatio",
    "totalRevenue",
    "debtToEquity",
    "revenuePerShare",
    "returnOnAssets",
    "returnOnEquity",
    "grossProfits",
    "freeCashflow",
    "operatingCashflow",
    "earningsGrowth",
    "revenueGrowth",
    "grossMargins",
    "ebitdaMargins",
    "operatingMargins",
    "profitMargins"
})
public class FinancialData {

    @JsonProperty("maxAge")
    private Integer maxAge;
    @JsonProperty("currentPrice")
    private CurrentPrice currentPrice;
    @JsonProperty("targetHighPrice")
    private TargetHighPrice targetHighPrice;
    @JsonProperty("targetLowPrice")
    private TargetLowPrice targetLowPrice;
    @JsonProperty("targetMeanPrice")
    private TargetMeanPrice targetMeanPrice;
    @JsonProperty("targetMedianPrice")
    private TargetMedianPrice targetMedianPrice;
    @JsonProperty("recommendationMean")
    private RecommendationMean recommendationMean;
    @JsonProperty("recommendationKey")
    private String recommendationKey;
    @JsonProperty("numberOfAnalystOpinions")
    private NumberOfAnalystOpinions numberOfAnalystOpinions;
    @JsonProperty("totalCash")
    private TotalCash totalCash;
    @JsonProperty("totalCashPerShare")
    private TotalCashPerShare totalCashPerShare;
    @JsonProperty("ebitda")
    private Ebitda ebitda;
    @JsonProperty("totalDebt")
    private TotalDebt totalDebt;
    @JsonProperty("quickRatio")
    private QuickRatio quickRatio;
    @JsonProperty("currentRatio")
    private CurrentRatio currentRatio;
    @JsonProperty("totalRevenue")
    private TotalRevenue totalRevenue;
    @JsonProperty("debtToEquity")
    private DebtToEquity debtToEquity;
    @JsonProperty("revenuePerShare")
    private RevenuePerShare revenuePerShare;
    @JsonProperty("returnOnAssets")
    private ReturnOnAssets returnOnAssets;
    @JsonProperty("returnOnEquity")
    private ReturnOnEquity returnOnEquity;
    @JsonProperty("grossProfits")
    private GrossProfits grossProfits;
    @JsonProperty("freeCashflow")
    private FreeCashflow freeCashflow;
    @JsonProperty("operatingCashflow")
    private OperatingCashflow operatingCashflow;
    @JsonProperty("earningsGrowth")
    private EarningsGrowth earningsGrowth;
    @JsonProperty("revenueGrowth")
    private RevenueGrowth revenueGrowth;
    @JsonProperty("grossMargins")
    private GrossMargins grossMargins;
    @JsonProperty("ebitdaMargins")
    private EbitdaMargins ebitdaMargins;
    @JsonProperty("operatingMargins")
    private OperatingMargins operatingMargins;
    @JsonProperty("profitMargins")
    private ProfitMargins_ profitMargins;
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
     *     The currentPrice
     */
    @JsonProperty("currentPrice")
    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    /**
     * 
     * @param currentPrice
     *     The currentPrice
     */
    @JsonProperty("currentPrice")
    public void setCurrentPrice(CurrentPrice currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * 
     * @return
     *     The targetHighPrice
     */
    @JsonProperty("targetHighPrice")
    public TargetHighPrice getTargetHighPrice() {
        return targetHighPrice;
    }

    /**
     * 
     * @param targetHighPrice
     *     The targetHighPrice
     */
    @JsonProperty("targetHighPrice")
    public void setTargetHighPrice(TargetHighPrice targetHighPrice) {
        this.targetHighPrice = targetHighPrice;
    }

    /**
     * 
     * @return
     *     The targetLowPrice
     */
    @JsonProperty("targetLowPrice")
    public TargetLowPrice getTargetLowPrice() {
        return targetLowPrice;
    }

    /**
     * 
     * @param targetLowPrice
     *     The targetLowPrice
     */
    @JsonProperty("targetLowPrice")
    public void setTargetLowPrice(TargetLowPrice targetLowPrice) {
        this.targetLowPrice = targetLowPrice;
    }

    /**
     * 
     * @return
     *     The targetMeanPrice
     */
    @JsonProperty("targetMeanPrice")
    public TargetMeanPrice getTargetMeanPrice() {
        return targetMeanPrice;
    }

    /**
     * 
     * @param targetMeanPrice
     *     The targetMeanPrice
     */
    @JsonProperty("targetMeanPrice")
    public void setTargetMeanPrice(TargetMeanPrice targetMeanPrice) {
        this.targetMeanPrice = targetMeanPrice;
    }

    /**
     * 
     * @return
     *     The targetMedianPrice
     */
    @JsonProperty("targetMedianPrice")
    public TargetMedianPrice getTargetMedianPrice() {
        return targetMedianPrice;
    }

    /**
     * 
     * @param targetMedianPrice
     *     The targetMedianPrice
     */
    @JsonProperty("targetMedianPrice")
    public void setTargetMedianPrice(TargetMedianPrice targetMedianPrice) {
        this.targetMedianPrice = targetMedianPrice;
    }

    /**
     * 
     * @return
     *     The recommendationMean
     */
    @JsonProperty("recommendationMean")
    public RecommendationMean getRecommendationMean() {
        return recommendationMean;
    }

    /**
     * 
     * @param recommendationMean
     *     The recommendationMean
     */
    @JsonProperty("recommendationMean")
    public void setRecommendationMean(RecommendationMean recommendationMean) {
        this.recommendationMean = recommendationMean;
    }

    /**
     * 
     * @return
     *     The recommendationKey
     */
    @JsonProperty("recommendationKey")
    public String getRecommendationKey() {
        return recommendationKey;
    }

    /**
     * 
     * @param recommendationKey
     *     The recommendationKey
     */
    @JsonProperty("recommendationKey")
    public void setRecommendationKey(String recommendationKey) {
        this.recommendationKey = recommendationKey;
    }

    /**
     * 
     * @return
     *     The numberOfAnalystOpinions
     */
    @JsonProperty("numberOfAnalystOpinions")
    public NumberOfAnalystOpinions getNumberOfAnalystOpinions() {
        return numberOfAnalystOpinions;
    }

    /**
     * 
     * @param numberOfAnalystOpinions
     *     The numberOfAnalystOpinions
     */
    @JsonProperty("numberOfAnalystOpinions")
    public void setNumberOfAnalystOpinions(NumberOfAnalystOpinions numberOfAnalystOpinions) {
        this.numberOfAnalystOpinions = numberOfAnalystOpinions;
    }

    /**
     * 
     * @return
     *     The totalCash
     */
    @JsonProperty("totalCash")
    public TotalCash getTotalCash() {
        return totalCash;
    }

    /**
     * 
     * @param totalCash
     *     The totalCash
     */
    @JsonProperty("totalCash")
    public void setTotalCash(TotalCash totalCash) {
        this.totalCash = totalCash;
    }

    /**
     * 
     * @return
     *     The totalCashPerShare
     */
    @JsonProperty("totalCashPerShare")
    public TotalCashPerShare getTotalCashPerShare() {
        return totalCashPerShare;
    }

    /**
     * 
     * @param totalCashPerShare
     *     The totalCashPerShare
     */
    @JsonProperty("totalCashPerShare")
    public void setTotalCashPerShare(TotalCashPerShare totalCashPerShare) {
        this.totalCashPerShare = totalCashPerShare;
    }

    /**
     * 
     * @return
     *     The ebitda
     */
    @JsonProperty("ebitda")
    public Ebitda getEbitda() {
        return ebitda;
    }

    /**
     * 
     * @param ebitda
     *     The ebitda
     */
    @JsonProperty("ebitda")
    public void setEbitda(Ebitda ebitda) {
        this.ebitda = ebitda;
    }

    /**
     * 
     * @return
     *     The totalDebt
     */
    @JsonProperty("totalDebt")
    public TotalDebt getTotalDebt() {
        return totalDebt;
    }

    /**
     * 
     * @param totalDebt
     *     The totalDebt
     */
    @JsonProperty("totalDebt")
    public void setTotalDebt(TotalDebt totalDebt) {
        this.totalDebt = totalDebt;
    }

    /**
     * 
     * @return
     *     The quickRatio
     */
    @JsonProperty("quickRatio")
    public QuickRatio getQuickRatio() {
        return quickRatio;
    }

    /**
     * 
     * @param quickRatio
     *     The quickRatio
     */
    @JsonProperty("quickRatio")
    public void setQuickRatio(QuickRatio quickRatio) {
        this.quickRatio = quickRatio;
    }

    /**
     * 
     * @return
     *     The currentRatio
     */
    @JsonProperty("currentRatio")
    public CurrentRatio getCurrentRatio() {
        return currentRatio;
    }

    /**
     * 
     * @param currentRatio
     *     The currentRatio
     */
    @JsonProperty("currentRatio")
    public void setCurrentRatio(CurrentRatio currentRatio) {
        this.currentRatio = currentRatio;
    }

    /**
     * 
     * @return
     *     The totalRevenue
     */
    @JsonProperty("totalRevenue")
    public TotalRevenue getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * 
     * @param totalRevenue
     *     The totalRevenue
     */
    @JsonProperty("totalRevenue")
    public void setTotalRevenue(TotalRevenue totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    /**
     * 
     * @return
     *     The debtToEquity
     */
    @JsonProperty("debtToEquity")
    public DebtToEquity getDebtToEquity() {
        return debtToEquity;
    }

    /**
     * 
     * @param debtToEquity
     *     The debtToEquity
     */
    @JsonProperty("debtToEquity")
    public void setDebtToEquity(DebtToEquity debtToEquity) {
        this.debtToEquity = debtToEquity;
    }

    /**
     * 
     * @return
     *     The revenuePerShare
     */
    @JsonProperty("revenuePerShare")
    public RevenuePerShare getRevenuePerShare() {
        return revenuePerShare;
    }

    /**
     * 
     * @param revenuePerShare
     *     The revenuePerShare
     */
    @JsonProperty("revenuePerShare")
    public void setRevenuePerShare(RevenuePerShare revenuePerShare) {
        this.revenuePerShare = revenuePerShare;
    }

    /**
     * 
     * @return
     *     The returnOnAssets
     */
    @JsonProperty("returnOnAssets")
    public ReturnOnAssets getReturnOnAssets() {
        return returnOnAssets;
    }

    /**
     * 
     * @param returnOnAssets
     *     The returnOnAssets
     */
    @JsonProperty("returnOnAssets")
    public void setReturnOnAssets(ReturnOnAssets returnOnAssets) {
        this.returnOnAssets = returnOnAssets;
    }

    /**
     * 
     * @return
     *     The returnOnEquity
     */
    @JsonProperty("returnOnEquity")
    public ReturnOnEquity getReturnOnEquity() {
        return returnOnEquity;
    }

    /**
     * 
     * @param returnOnEquity
     *     The returnOnEquity
     */
    @JsonProperty("returnOnEquity")
    public void setReturnOnEquity(ReturnOnEquity returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    /**
     * 
     * @return
     *     The grossProfits
     */
    @JsonProperty("grossProfits")
    public GrossProfits getGrossProfits() {
        return grossProfits;
    }

    /**
     * 
     * @param grossProfits
     *     The grossProfits
     */
    @JsonProperty("grossProfits")
    public void setGrossProfits(GrossProfits grossProfits) {
        this.grossProfits = grossProfits;
    }

    /**
     * 
     * @return
     *     The freeCashflow
     */
    @JsonProperty("freeCashflow")
    public FreeCashflow getFreeCashflow() {
        return freeCashflow;
    }

    /**
     * 
     * @param freeCashflow
     *     The freeCashflow
     */
    @JsonProperty("freeCashflow")
    public void setFreeCashflow(FreeCashflow freeCashflow) {
        this.freeCashflow = freeCashflow;
    }

    /**
     * 
     * @return
     *     The operatingCashflow
     */
    @JsonProperty("operatingCashflow")
    public OperatingCashflow getOperatingCashflow() {
        return operatingCashflow;
    }

    /**
     * 
     * @param operatingCashflow
     *     The operatingCashflow
     */
    @JsonProperty("operatingCashflow")
    public void setOperatingCashflow(OperatingCashflow operatingCashflow) {
        this.operatingCashflow = operatingCashflow;
    }

    /**
     * 
     * @return
     *     The earningsGrowth
     */
    @JsonProperty("earningsGrowth")
    public EarningsGrowth getEarningsGrowth() {
        return earningsGrowth;
    }

    /**
     * 
     * @param earningsGrowth
     *     The earningsGrowth
     */
    @JsonProperty("earningsGrowth")
    public void setEarningsGrowth(EarningsGrowth earningsGrowth) {
        this.earningsGrowth = earningsGrowth;
    }

    /**
     * 
     * @return
     *     The revenueGrowth
     */
    @JsonProperty("revenueGrowth")
    public RevenueGrowth getRevenueGrowth() {
        return revenueGrowth;
    }

    /**
     * 
     * @param revenueGrowth
     *     The revenueGrowth
     */
    @JsonProperty("revenueGrowth")
    public void setRevenueGrowth(RevenueGrowth revenueGrowth) {
        this.revenueGrowth = revenueGrowth;
    }

    /**
     * 
     * @return
     *     The grossMargins
     */
    @JsonProperty("grossMargins")
    public GrossMargins getGrossMargins() {
        return grossMargins;
    }

    /**
     * 
     * @param grossMargins
     *     The grossMargins
     */
    @JsonProperty("grossMargins")
    public void setGrossMargins(GrossMargins grossMargins) {
        this.grossMargins = grossMargins;
    }

    /**
     * 
     * @return
     *     The ebitdaMargins
     */
    @JsonProperty("ebitdaMargins")
    public EbitdaMargins getEbitdaMargins() {
        return ebitdaMargins;
    }

    /**
     * 
     * @param ebitdaMargins
     *     The ebitdaMargins
     */
    @JsonProperty("ebitdaMargins")
    public void setEbitdaMargins(EbitdaMargins ebitdaMargins) {
        this.ebitdaMargins = ebitdaMargins;
    }

    /**
     * 
     * @return
     *     The operatingMargins
     */
    @JsonProperty("operatingMargins")
    public OperatingMargins getOperatingMargins() {
        return operatingMargins;
    }

    /**
     * 
     * @param operatingMargins
     *     The operatingMargins
     */
    @JsonProperty("operatingMargins")
    public void setOperatingMargins(OperatingMargins operatingMargins) {
        this.operatingMargins = operatingMargins;
    }

    /**
     * 
     * @return
     *     The profitMargins
     */
    @JsonProperty("profitMargins")
    public ProfitMargins_ getProfitMargins() {
        return profitMargins;
    }

    /**
     * 
     * @param profitMargins
     *     The profitMargins
     */
    @JsonProperty("profitMargins")
    public void setProfitMargins(ProfitMargins_ profitMargins) {
        this.profitMargins = profitMargins;
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
