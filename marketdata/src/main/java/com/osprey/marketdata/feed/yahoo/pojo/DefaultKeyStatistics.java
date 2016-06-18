
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
    "enterpriseValue",
    "forwardPE",
    "profitMargins",
    "floatShares",
    "sharesOutstanding",
    "sharesShort",
    "sharesShortPriorMonth",
    "heldPercentInsiders",
    "heldPercentInstitutions",
    "shortRatio",
    "shortPercentOfFloat",
    "beta",
    "morningStarOverallRating",
    "morningStarRiskRating",
    "category",
    "bookValue",
    "priceToBook",
    "annualReportExpenseRatio",
    "ytdReturn",
    "beta3Year",
    "totalAssets",
    "yield",
    "fundFamily",
    "fundInceptionDate",
    "legalType",
    "threeYearAverageReturn",
    "fiveYearAverageReturn",
    "priceToSalesTrailing12Months",
    "lastFiscalYearEnd",
    "nextFiscalYearEnd",
    "mostRecentQuarter",
    "earningsQuarterlyGrowth",
    "revenueQuarterlyGrowth",
    "netIncomeToCommon",
    "trailingEps",
    "forwardEps",
    "pegRatio",
    "lastSplitFactor",
    "lastSplitDate",
    "enterpriseToRevenue",
    "enterpriseToEbitda",
    "52WeekChange",
    "SandP52WeekChange",
    "lastDividendValue",
    "lastCapGain",
    "annualHoldingsTurnover"
})
public class DefaultKeyStatistics {

    @JsonProperty("maxAge")
    private Integer maxAge;
    @JsonProperty("enterpriseValue")
    private EnterpriseValue enterpriseValue;
    @JsonProperty("forwardPE")
    private ForwardPE_ forwardPE;
    @JsonProperty("profitMargins")
    private ProfitMargins profitMargins;
    @JsonProperty("floatShares")
    private FloatShares floatShares;
    @JsonProperty("sharesOutstanding")
    private SharesOutstanding sharesOutstanding;
    @JsonProperty("sharesShort")
    private SharesShort sharesShort;
    @JsonProperty("sharesShortPriorMonth")
    private SharesShortPriorMonth sharesShortPriorMonth;
    @JsonProperty("heldPercentInsiders")
    private HeldPercentInsiders heldPercentInsiders;
    @JsonProperty("heldPercentInstitutions")
    private HeldPercentInstitutions heldPercentInstitutions;
    @JsonProperty("shortRatio")
    private ShortRatio shortRatio;
    @JsonProperty("shortPercentOfFloat")
    private ShortPercentOfFloat shortPercentOfFloat;
    @JsonProperty("beta")
    private Beta_ beta;
    @JsonProperty("morningStarOverallRating")
    private MorningStarOverallRating morningStarOverallRating;
    @JsonProperty("morningStarRiskRating")
    private MorningStarRiskRating morningStarRiskRating;
    @JsonProperty("category")
    private Object category;
    @JsonProperty("bookValue")
    private BookValue bookValue;
    @JsonProperty("priceToBook")
    private PriceToBook priceToBook;
    @JsonProperty("annualReportExpenseRatio")
    private AnnualReportExpenseRatio annualReportExpenseRatio;
    @JsonProperty("ytdReturn")
    private YtdReturn_ ytdReturn;
    @JsonProperty("beta3Year")
    private Beta3Year beta3Year;
    @JsonProperty("totalAssets")
    private TotalAssets_ totalAssets;
    @JsonProperty("yield")
    private Yield_ yield;
    @JsonProperty("fundFamily")
    private Object fundFamily;
    @JsonProperty("fundInceptionDate")
    private FundInceptionDate fundInceptionDate;
    @JsonProperty("legalType")
    private Object legalType;
    @JsonProperty("threeYearAverageReturn")
    private ThreeYearAverageReturn threeYearAverageReturn;
    @JsonProperty("fiveYearAverageReturn")
    private FiveYearAverageReturn fiveYearAverageReturn;
    @JsonProperty("priceToSalesTrailing12Months")
    private PriceToSalesTrailing12Months_ priceToSalesTrailing12Months;
    @JsonProperty("lastFiscalYearEnd")
    private LastFiscalYearEnd lastFiscalYearEnd;
    @JsonProperty("nextFiscalYearEnd")
    private NextFiscalYearEnd nextFiscalYearEnd;
    @JsonProperty("mostRecentQuarter")
    private MostRecentQuarter mostRecentQuarter;
    @JsonProperty("earningsQuarterlyGrowth")
    private EarningsQuarterlyGrowth earningsQuarterlyGrowth;
    @JsonProperty("revenueQuarterlyGrowth")
    private RevenueQuarterlyGrowth revenueQuarterlyGrowth;
    @JsonProperty("netIncomeToCommon")
    private NetIncomeToCommon netIncomeToCommon;
    @JsonProperty("trailingEps")
    private TrailingEps trailingEps;
    @JsonProperty("forwardEps")
    private ForwardEps forwardEps;
    @JsonProperty("pegRatio")
    private PegRatio pegRatio;
    @JsonProperty("lastSplitFactor")
    private String lastSplitFactor;
    @JsonProperty("lastSplitDate")
    private LastSplitDate lastSplitDate;
    @JsonProperty("enterpriseToRevenue")
    private EnterpriseToRevenue enterpriseToRevenue;
    @JsonProperty("enterpriseToEbitda")
    private EnterpriseToEbitda enterpriseToEbitda;
    @JsonProperty("52WeekChange")
    private com.osprey.marketdata.feed.yahoo.pojo._52WeekChange _52WeekChange;
    @JsonProperty("SandP52WeekChange")
    private SandP52WeekChange sandP52WeekChange;
    @JsonProperty("lastDividendValue")
    private LastDividendValue lastDividendValue;
    @JsonProperty("lastCapGain")
    private LastCapGain lastCapGain;
    @JsonProperty("annualHoldingsTurnover")
    private AnnualHoldingsTurnover annualHoldingsTurnover;
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
     *     The enterpriseValue
     */
    @JsonProperty("enterpriseValue")
    public EnterpriseValue getEnterpriseValue() {
        return enterpriseValue;
    }

    /**
     * 
     * @param enterpriseValue
     *     The enterpriseValue
     */
    @JsonProperty("enterpriseValue")
    public void setEnterpriseValue(EnterpriseValue enterpriseValue) {
        this.enterpriseValue = enterpriseValue;
    }

    /**
     * 
     * @return
     *     The forwardPE
     */
    @JsonProperty("forwardPE")
    public ForwardPE_ getForwardPE() {
        return forwardPE;
    }

    /**
     * 
     * @param forwardPE
     *     The forwardPE
     */
    @JsonProperty("forwardPE")
    public void setForwardPE(ForwardPE_ forwardPE) {
        this.forwardPE = forwardPE;
    }

    /**
     * 
     * @return
     *     The profitMargins
     */
    @JsonProperty("profitMargins")
    public ProfitMargins getProfitMargins() {
        return profitMargins;
    }

    /**
     * 
     * @param profitMargins
     *     The profitMargins
     */
    @JsonProperty("profitMargins")
    public void setProfitMargins(ProfitMargins profitMargins) {
        this.profitMargins = profitMargins;
    }

    /**
     * 
     * @return
     *     The floatShares
     */
    @JsonProperty("floatShares")
    public FloatShares getFloatShares() {
        return floatShares;
    }

    /**
     * 
     * @param floatShares
     *     The floatShares
     */
    @JsonProperty("floatShares")
    public void setFloatShares(FloatShares floatShares) {
        this.floatShares = floatShares;
    }

    /**
     * 
     * @return
     *     The sharesOutstanding
     */
    @JsonProperty("sharesOutstanding")
    public SharesOutstanding getSharesOutstanding() {
        return sharesOutstanding;
    }

    /**
     * 
     * @param sharesOutstanding
     *     The sharesOutstanding
     */
    @JsonProperty("sharesOutstanding")
    public void setSharesOutstanding(SharesOutstanding sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    /**
     * 
     * @return
     *     The sharesShort
     */
    @JsonProperty("sharesShort")
    public SharesShort getSharesShort() {
        return sharesShort;
    }

    /**
     * 
     * @param sharesShort
     *     The sharesShort
     */
    @JsonProperty("sharesShort")
    public void setSharesShort(SharesShort sharesShort) {
        this.sharesShort = sharesShort;
    }

    /**
     * 
     * @return
     *     The sharesShortPriorMonth
     */
    @JsonProperty("sharesShortPriorMonth")
    public SharesShortPriorMonth getSharesShortPriorMonth() {
        return sharesShortPriorMonth;
    }

    /**
     * 
     * @param sharesShortPriorMonth
     *     The sharesShortPriorMonth
     */
    @JsonProperty("sharesShortPriorMonth")
    public void setSharesShortPriorMonth(SharesShortPriorMonth sharesShortPriorMonth) {
        this.sharesShortPriorMonth = sharesShortPriorMonth;
    }

    /**
     * 
     * @return
     *     The heldPercentInsiders
     */
    @JsonProperty("heldPercentInsiders")
    public HeldPercentInsiders getHeldPercentInsiders() {
        return heldPercentInsiders;
    }

    /**
     * 
     * @param heldPercentInsiders
     *     The heldPercentInsiders
     */
    @JsonProperty("heldPercentInsiders")
    public void setHeldPercentInsiders(HeldPercentInsiders heldPercentInsiders) {
        this.heldPercentInsiders = heldPercentInsiders;
    }

    /**
     * 
     * @return
     *     The heldPercentInstitutions
     */
    @JsonProperty("heldPercentInstitutions")
    public HeldPercentInstitutions getHeldPercentInstitutions() {
        return heldPercentInstitutions;
    }

    /**
     * 
     * @param heldPercentInstitutions
     *     The heldPercentInstitutions
     */
    @JsonProperty("heldPercentInstitutions")
    public void setHeldPercentInstitutions(HeldPercentInstitutions heldPercentInstitutions) {
        this.heldPercentInstitutions = heldPercentInstitutions;
    }

    /**
     * 
     * @return
     *     The shortRatio
     */
    @JsonProperty("shortRatio")
    public ShortRatio getShortRatio() {
        return shortRatio;
    }

    /**
     * 
     * @param shortRatio
     *     The shortRatio
     */
    @JsonProperty("shortRatio")
    public void setShortRatio(ShortRatio shortRatio) {
        this.shortRatio = shortRatio;
    }

    /**
     * 
     * @return
     *     The shortPercentOfFloat
     */
    @JsonProperty("shortPercentOfFloat")
    public ShortPercentOfFloat getShortPercentOfFloat() {
        return shortPercentOfFloat;
    }

    /**
     * 
     * @param shortPercentOfFloat
     *     The shortPercentOfFloat
     */
    @JsonProperty("shortPercentOfFloat")
    public void setShortPercentOfFloat(ShortPercentOfFloat shortPercentOfFloat) {
        this.shortPercentOfFloat = shortPercentOfFloat;
    }

    /**
     * 
     * @return
     *     The beta
     */
    @JsonProperty("beta")
    public Beta_ getBeta() {
        return beta;
    }

    /**
     * 
     * @param beta
     *     The beta
     */
    @JsonProperty("beta")
    public void setBeta(Beta_ beta) {
        this.beta = beta;
    }

    /**
     * 
     * @return
     *     The morningStarOverallRating
     */
    @JsonProperty("morningStarOverallRating")
    public MorningStarOverallRating getMorningStarOverallRating() {
        return morningStarOverallRating;
    }

    /**
     * 
     * @param morningStarOverallRating
     *     The morningStarOverallRating
     */
    @JsonProperty("morningStarOverallRating")
    public void setMorningStarOverallRating(MorningStarOverallRating morningStarOverallRating) {
        this.morningStarOverallRating = morningStarOverallRating;
    }

    /**
     * 
     * @return
     *     The morningStarRiskRating
     */
    @JsonProperty("morningStarRiskRating")
    public MorningStarRiskRating getMorningStarRiskRating() {
        return morningStarRiskRating;
    }

    /**
     * 
     * @param morningStarRiskRating
     *     The morningStarRiskRating
     */
    @JsonProperty("morningStarRiskRating")
    public void setMorningStarRiskRating(MorningStarRiskRating morningStarRiskRating) {
        this.morningStarRiskRating = morningStarRiskRating;
    }

    /**
     * 
     * @return
     *     The category
     */
    @JsonProperty("category")
    public Object getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    @JsonProperty("category")
    public void setCategory(Object category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The bookValue
     */
    @JsonProperty("bookValue")
    public BookValue getBookValue() {
        return bookValue;
    }

    /**
     * 
     * @param bookValue
     *     The bookValue
     */
    @JsonProperty("bookValue")
    public void setBookValue(BookValue bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * 
     * @return
     *     The priceToBook
     */
    @JsonProperty("priceToBook")
    public PriceToBook getPriceToBook() {
        return priceToBook;
    }

    /**
     * 
     * @param priceToBook
     *     The priceToBook
     */
    @JsonProperty("priceToBook")
    public void setPriceToBook(PriceToBook priceToBook) {
        this.priceToBook = priceToBook;
    }

    /**
     * 
     * @return
     *     The annualReportExpenseRatio
     */
    @JsonProperty("annualReportExpenseRatio")
    public AnnualReportExpenseRatio getAnnualReportExpenseRatio() {
        return annualReportExpenseRatio;
    }

    /**
     * 
     * @param annualReportExpenseRatio
     *     The annualReportExpenseRatio
     */
    @JsonProperty("annualReportExpenseRatio")
    public void setAnnualReportExpenseRatio(AnnualReportExpenseRatio annualReportExpenseRatio) {
        this.annualReportExpenseRatio = annualReportExpenseRatio;
    }

    /**
     * 
     * @return
     *     The ytdReturn
     */
    @JsonProperty("ytdReturn")
    public YtdReturn_ getYtdReturn() {
        return ytdReturn;
    }

    /**
     * 
     * @param ytdReturn
     *     The ytdReturn
     */
    @JsonProperty("ytdReturn")
    public void setYtdReturn(YtdReturn_ ytdReturn) {
        this.ytdReturn = ytdReturn;
    }

    /**
     * 
     * @return
     *     The beta3Year
     */
    @JsonProperty("beta3Year")
    public Beta3Year getBeta3Year() {
        return beta3Year;
    }

    /**
     * 
     * @param beta3Year
     *     The beta3Year
     */
    @JsonProperty("beta3Year")
    public void setBeta3Year(Beta3Year beta3Year) {
        this.beta3Year = beta3Year;
    }

    /**
     * 
     * @return
     *     The totalAssets
     */
    @JsonProperty("totalAssets")
    public TotalAssets_ getTotalAssets() {
        return totalAssets;
    }

    /**
     * 
     * @param totalAssets
     *     The totalAssets
     */
    @JsonProperty("totalAssets")
    public void setTotalAssets(TotalAssets_ totalAssets) {
        this.totalAssets = totalAssets;
    }

    /**
     * 
     * @return
     *     The yield
     */
    @JsonProperty("yield")
    public Yield_ getYield() {
        return yield;
    }

    /**
     * 
     * @param yield
     *     The yield
     */
    @JsonProperty("yield")
    public void setYield(Yield_ yield) {
        this.yield = yield;
    }

    /**
     * 
     * @return
     *     The fundFamily
     */
    @JsonProperty("fundFamily")
    public Object getFundFamily() {
        return fundFamily;
    }

    /**
     * 
     * @param fundFamily
     *     The fundFamily
     */
    @JsonProperty("fundFamily")
    public void setFundFamily(Object fundFamily) {
        this.fundFamily = fundFamily;
    }

    /**
     * 
     * @return
     *     The fundInceptionDate
     */
    @JsonProperty("fundInceptionDate")
    public FundInceptionDate getFundInceptionDate() {
        return fundInceptionDate;
    }

    /**
     * 
     * @param fundInceptionDate
     *     The fundInceptionDate
     */
    @JsonProperty("fundInceptionDate")
    public void setFundInceptionDate(FundInceptionDate fundInceptionDate) {
        this.fundInceptionDate = fundInceptionDate;
    }

    /**
     * 
     * @return
     *     The legalType
     */
    @JsonProperty("legalType")
    public Object getLegalType() {
        return legalType;
    }

    /**
     * 
     * @param legalType
     *     The legalType
     */
    @JsonProperty("legalType")
    public void setLegalType(Object legalType) {
        this.legalType = legalType;
    }

    /**
     * 
     * @return
     *     The threeYearAverageReturn
     */
    @JsonProperty("threeYearAverageReturn")
    public ThreeYearAverageReturn getThreeYearAverageReturn() {
        return threeYearAverageReturn;
    }

    /**
     * 
     * @param threeYearAverageReturn
     *     The threeYearAverageReturn
     */
    @JsonProperty("threeYearAverageReturn")
    public void setThreeYearAverageReturn(ThreeYearAverageReturn threeYearAverageReturn) {
        this.threeYearAverageReturn = threeYearAverageReturn;
    }

    /**
     * 
     * @return
     *     The fiveYearAverageReturn
     */
    @JsonProperty("fiveYearAverageReturn")
    public FiveYearAverageReturn getFiveYearAverageReturn() {
        return fiveYearAverageReturn;
    }

    /**
     * 
     * @param fiveYearAverageReturn
     *     The fiveYearAverageReturn
     */
    @JsonProperty("fiveYearAverageReturn")
    public void setFiveYearAverageReturn(FiveYearAverageReturn fiveYearAverageReturn) {
        this.fiveYearAverageReturn = fiveYearAverageReturn;
    }

    /**
     * 
     * @return
     *     The priceToSalesTrailing12Months
     */
    @JsonProperty("priceToSalesTrailing12Months")
    public PriceToSalesTrailing12Months_ getPriceToSalesTrailing12Months() {
        return priceToSalesTrailing12Months;
    }

    /**
     * 
     * @param priceToSalesTrailing12Months
     *     The priceToSalesTrailing12Months
     */
    @JsonProperty("priceToSalesTrailing12Months")
    public void setPriceToSalesTrailing12Months(PriceToSalesTrailing12Months_ priceToSalesTrailing12Months) {
        this.priceToSalesTrailing12Months = priceToSalesTrailing12Months;
    }

    /**
     * 
     * @return
     *     The lastFiscalYearEnd
     */
    @JsonProperty("lastFiscalYearEnd")
    public LastFiscalYearEnd getLastFiscalYearEnd() {
        return lastFiscalYearEnd;
    }

    /**
     * 
     * @param lastFiscalYearEnd
     *     The lastFiscalYearEnd
     */
    @JsonProperty("lastFiscalYearEnd")
    public void setLastFiscalYearEnd(LastFiscalYearEnd lastFiscalYearEnd) {
        this.lastFiscalYearEnd = lastFiscalYearEnd;
    }

    /**
     * 
     * @return
     *     The nextFiscalYearEnd
     */
    @JsonProperty("nextFiscalYearEnd")
    public NextFiscalYearEnd getNextFiscalYearEnd() {
        return nextFiscalYearEnd;
    }

    /**
     * 
     * @param nextFiscalYearEnd
     *     The nextFiscalYearEnd
     */
    @JsonProperty("nextFiscalYearEnd")
    public void setNextFiscalYearEnd(NextFiscalYearEnd nextFiscalYearEnd) {
        this.nextFiscalYearEnd = nextFiscalYearEnd;
    }

    /**
     * 
     * @return
     *     The mostRecentQuarter
     */
    @JsonProperty("mostRecentQuarter")
    public MostRecentQuarter getMostRecentQuarter() {
        return mostRecentQuarter;
    }

    /**
     * 
     * @param mostRecentQuarter
     *     The mostRecentQuarter
     */
    @JsonProperty("mostRecentQuarter")
    public void setMostRecentQuarter(MostRecentQuarter mostRecentQuarter) {
        this.mostRecentQuarter = mostRecentQuarter;
    }

    /**
     * 
     * @return
     *     The earningsQuarterlyGrowth
     */
    @JsonProperty("earningsQuarterlyGrowth")
    public EarningsQuarterlyGrowth getEarningsQuarterlyGrowth() {
        return earningsQuarterlyGrowth;
    }

    /**
     * 
     * @param earningsQuarterlyGrowth
     *     The earningsQuarterlyGrowth
     */
    @JsonProperty("earningsQuarterlyGrowth")
    public void setEarningsQuarterlyGrowth(EarningsQuarterlyGrowth earningsQuarterlyGrowth) {
        this.earningsQuarterlyGrowth = earningsQuarterlyGrowth;
    }

    /**
     * 
     * @return
     *     The revenueQuarterlyGrowth
     */
    @JsonProperty("revenueQuarterlyGrowth")
    public RevenueQuarterlyGrowth getRevenueQuarterlyGrowth() {
        return revenueQuarterlyGrowth;
    }

    /**
     * 
     * @param revenueQuarterlyGrowth
     *     The revenueQuarterlyGrowth
     */
    @JsonProperty("revenueQuarterlyGrowth")
    public void setRevenueQuarterlyGrowth(RevenueQuarterlyGrowth revenueQuarterlyGrowth) {
        this.revenueQuarterlyGrowth = revenueQuarterlyGrowth;
    }

    /**
     * 
     * @return
     *     The netIncomeToCommon
     */
    @JsonProperty("netIncomeToCommon")
    public NetIncomeToCommon getNetIncomeToCommon() {
        return netIncomeToCommon;
    }

    /**
     * 
     * @param netIncomeToCommon
     *     The netIncomeToCommon
     */
    @JsonProperty("netIncomeToCommon")
    public void setNetIncomeToCommon(NetIncomeToCommon netIncomeToCommon) {
        this.netIncomeToCommon = netIncomeToCommon;
    }

    /**
     * 
     * @return
     *     The trailingEps
     */
    @JsonProperty("trailingEps")
    public TrailingEps getTrailingEps() {
        return trailingEps;
    }

    /**
     * 
     * @param trailingEps
     *     The trailingEps
     */
    @JsonProperty("trailingEps")
    public void setTrailingEps(TrailingEps trailingEps) {
        this.trailingEps = trailingEps;
    }

    /**
     * 
     * @return
     *     The forwardEps
     */
    @JsonProperty("forwardEps")
    public ForwardEps getForwardEps() {
        return forwardEps;
    }

    /**
     * 
     * @param forwardEps
     *     The forwardEps
     */
    @JsonProperty("forwardEps")
    public void setForwardEps(ForwardEps forwardEps) {
        this.forwardEps = forwardEps;
    }

    /**
     * 
     * @return
     *     The pegRatio
     */
    @JsonProperty("pegRatio")
    public PegRatio getPegRatio() {
        return pegRatio;
    }

    /**
     * 
     * @param pegRatio
     *     The pegRatio
     */
    @JsonProperty("pegRatio")
    public void setPegRatio(PegRatio pegRatio) {
        this.pegRatio = pegRatio;
    }

    /**
     * 
     * @return
     *     The lastSplitFactor
     */
    @JsonProperty("lastSplitFactor")
    public String getLastSplitFactor() {
        return lastSplitFactor;
    }

    /**
     * 
     * @param lastSplitFactor
     *     The lastSplitFactor
     */
    @JsonProperty("lastSplitFactor")
    public void setLastSplitFactor(String lastSplitFactor) {
        this.lastSplitFactor = lastSplitFactor;
    }

    /**
     * 
     * @return
     *     The lastSplitDate
     */
    @JsonProperty("lastSplitDate")
    public LastSplitDate getLastSplitDate() {
        return lastSplitDate;
    }

    /**
     * 
     * @param lastSplitDate
     *     The lastSplitDate
     */
    @JsonProperty("lastSplitDate")
    public void setLastSplitDate(LastSplitDate lastSplitDate) {
        this.lastSplitDate = lastSplitDate;
    }

    /**
     * 
     * @return
     *     The enterpriseToRevenue
     */
    @JsonProperty("enterpriseToRevenue")
    public EnterpriseToRevenue getEnterpriseToRevenue() {
        return enterpriseToRevenue;
    }

    /**
     * 
     * @param enterpriseToRevenue
     *     The enterpriseToRevenue
     */
    @JsonProperty("enterpriseToRevenue")
    public void setEnterpriseToRevenue(EnterpriseToRevenue enterpriseToRevenue) {
        this.enterpriseToRevenue = enterpriseToRevenue;
    }

    /**
     * 
     * @return
     *     The enterpriseToEbitda
     */
    @JsonProperty("enterpriseToEbitda")
    public EnterpriseToEbitda getEnterpriseToEbitda() {
        return enterpriseToEbitda;
    }

    /**
     * 
     * @param enterpriseToEbitda
     *     The enterpriseToEbitda
     */
    @JsonProperty("enterpriseToEbitda")
    public void setEnterpriseToEbitda(EnterpriseToEbitda enterpriseToEbitda) {
        this.enterpriseToEbitda = enterpriseToEbitda;
    }

    /**
     * 
     * @return
     *     The _52WeekChange
     */
    @JsonProperty("52WeekChange")
    public com.osprey.marketdata.feed.yahoo.pojo._52WeekChange get52WeekChange() {
        return _52WeekChange;
    }

    /**
     * 
     * @param _52WeekChange
     *     The 52WeekChange
     */
    @JsonProperty("52WeekChange")
    public void set52WeekChange(com.osprey.marketdata.feed.yahoo.pojo._52WeekChange _52WeekChange) {
        this._52WeekChange = _52WeekChange;
    }

    /**
     * 
     * @return
     *     The sandP52WeekChange
     */
    @JsonProperty("SandP52WeekChange")
    public SandP52WeekChange getSandP52WeekChange() {
        return sandP52WeekChange;
    }

    /**
     * 
     * @param sandP52WeekChange
     *     The SandP52WeekChange
     */
    @JsonProperty("SandP52WeekChange")
    public void setSandP52WeekChange(SandP52WeekChange sandP52WeekChange) {
        this.sandP52WeekChange = sandP52WeekChange;
    }

    /**
     * 
     * @return
     *     The lastDividendValue
     */
    @JsonProperty("lastDividendValue")
    public LastDividendValue getLastDividendValue() {
        return lastDividendValue;
    }

    /**
     * 
     * @param lastDividendValue
     *     The lastDividendValue
     */
    @JsonProperty("lastDividendValue")
    public void setLastDividendValue(LastDividendValue lastDividendValue) {
        this.lastDividendValue = lastDividendValue;
    }

    /**
     * 
     * @return
     *     The lastCapGain
     */
    @JsonProperty("lastCapGain")
    public LastCapGain getLastCapGain() {
        return lastCapGain;
    }

    /**
     * 
     * @param lastCapGain
     *     The lastCapGain
     */
    @JsonProperty("lastCapGain")
    public void setLastCapGain(LastCapGain lastCapGain) {
        this.lastCapGain = lastCapGain;
    }

    /**
     * 
     * @return
     *     The annualHoldingsTurnover
     */
    @JsonProperty("annualHoldingsTurnover")
    public AnnualHoldingsTurnover getAnnualHoldingsTurnover() {
        return annualHoldingsTurnover;
    }

    /**
     * 
     * @param annualHoldingsTurnover
     *     The annualHoldingsTurnover
     */
    @JsonProperty("annualHoldingsTurnover")
    public void setAnnualHoldingsTurnover(AnnualHoldingsTurnover annualHoldingsTurnover) {
        this.annualHoldingsTurnover = annualHoldingsTurnover;
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
