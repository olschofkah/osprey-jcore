
package com.osprey.marketdata.feed.yahoo.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "maxAge",
    "period",
    "buyInfoCount",
    "buyInfoShares",
    "sellInfoCount",
    "sellInfoShares",
    "sellPercentInsiderShares",
    "netInfoCount",
    "netInfoShares",
    "netPercentInsiderShares",
    "netInstSharesBuying",
    "netInstBuyingPercent",
    "totalInsiderShares"
})
public class NetSharePurchaseActivity {

    @JsonProperty("maxAge")
    private Long maxAge;
    @JsonProperty("period")
    private String period;
    @JsonProperty("buyInfoCount")
    private BuyInfoCount buyInfoCount;
    @JsonProperty("buyInfoShares")
    private BuyInfoShares buyInfoShares;
    @JsonProperty("sellInfoCount")
    private SellInfoCount sellInfoCount;
    @JsonProperty("sellInfoShares")
    private SellInfoShares sellInfoShares;
    @JsonProperty("sellPercentInsiderShares")
    private SellPercentInsiderShares sellPercentInsiderShares;
    @JsonProperty("netInfoCount")
    private NetInfoCount netInfoCount;
    @JsonProperty("netInfoShares")
    private NetInfoShares netInfoShares;
    @JsonProperty("netPercentInsiderShares")
    private NetPercentInsiderShares netPercentInsiderShares;
    @JsonProperty("netInstSharesBuying")
    private NetInstSharesBuying netInstSharesBuying;
    @JsonProperty("netInstBuyingPercent")
    private NetInstBuyingPercent netInstBuyingPercent;
    @JsonProperty("totalInsiderShares")
    private TotalInsiderShares totalInsiderShares;

    /**
     * 
     * @return
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    /**
     * 
     * @param maxAge
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * 
     * @return
     *     The period
     */
    @JsonProperty("period")
    public String getPeriod() {
        return period;
    }

    /**
     * 
     * @param period
     *     The period
     */
    @JsonProperty("period")
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 
     * @return
     *     The buyInfoCount
     */
    @JsonProperty("buyInfoCount")
    public BuyInfoCount getBuyInfoCount() {
        return buyInfoCount;
    }

    /**
     * 
     * @param buyInfoCount
     *     The buyInfoCount
     */
    @JsonProperty("buyInfoCount")
    public void setBuyInfoCount(BuyInfoCount buyInfoCount) {
        this.buyInfoCount = buyInfoCount;
    }

    /**
     * 
     * @return
     *     The buyInfoShares
     */
    @JsonProperty("buyInfoShares")
    public BuyInfoShares getBuyInfoShares() {
        return buyInfoShares;
    }

    /**
     * 
     * @param buyInfoShares
     *     The buyInfoShares
     */
    @JsonProperty("buyInfoShares")
    public void setBuyInfoShares(BuyInfoShares buyInfoShares) {
        this.buyInfoShares = buyInfoShares;
    }

    /**
     * 
     * @return
     *     The sellInfoCount
     */
    @JsonProperty("sellInfoCount")
    public SellInfoCount getSellInfoCount() {
        return sellInfoCount;
    }

    /**
     * 
     * @param sellInfoCount
     *     The sellInfoCount
     */
    @JsonProperty("sellInfoCount")
    public void setSellInfoCount(SellInfoCount sellInfoCount) {
        this.sellInfoCount = sellInfoCount;
    }

    /**
     * 
     * @return
     *     The sellInfoShares
     */
    @JsonProperty("sellInfoShares")
    public SellInfoShares getSellInfoShares() {
        return sellInfoShares;
    }

    /**
     * 
     * @param sellInfoShares
     *     The sellInfoShares
     */
    @JsonProperty("sellInfoShares")
    public void setSellInfoShares(SellInfoShares sellInfoShares) {
        this.sellInfoShares = sellInfoShares;
    }

    /**
     * 
     * @return
     *     The sellPercentInsiderShares
     */
    @JsonProperty("sellPercentInsiderShares")
    public SellPercentInsiderShares getSellPercentInsiderShares() {
        return sellPercentInsiderShares;
    }

    /**
     * 
     * @param sellPercentInsiderShares
     *     The sellPercentInsiderShares
     */
    @JsonProperty("sellPercentInsiderShares")
    public void setSellPercentInsiderShares(SellPercentInsiderShares sellPercentInsiderShares) {
        this.sellPercentInsiderShares = sellPercentInsiderShares;
    }

    /**
     * 
     * @return
     *     The netInfoCount
     */
    @JsonProperty("netInfoCount")
    public NetInfoCount getNetInfoCount() {
        return netInfoCount;
    }

    /**
     * 
     * @param netInfoCount
     *     The netInfoCount
     */
    @JsonProperty("netInfoCount")
    public void setNetInfoCount(NetInfoCount netInfoCount) {
        this.netInfoCount = netInfoCount;
    }

    /**
     * 
     * @return
     *     The netInfoShares
     */
    @JsonProperty("netInfoShares")
    public NetInfoShares getNetInfoShares() {
        return netInfoShares;
    }

    /**
     * 
     * @param netInfoShares
     *     The netInfoShares
     */
    @JsonProperty("netInfoShares")
    public void setNetInfoShares(NetInfoShares netInfoShares) {
        this.netInfoShares = netInfoShares;
    }

    /**
     * 
     * @return
     *     The netPercentInsiderShares
     */
    @JsonProperty("netPercentInsiderShares")
    public NetPercentInsiderShares getNetPercentInsiderShares() {
        return netPercentInsiderShares;
    }

    /**
     * 
     * @param netPercentInsiderShares
     *     The netPercentInsiderShares
     */
    @JsonProperty("netPercentInsiderShares")
    public void setNetPercentInsiderShares(NetPercentInsiderShares netPercentInsiderShares) {
        this.netPercentInsiderShares = netPercentInsiderShares;
    }

    /**
     * 
     * @return
     *     The netInstSharesBuying
     */
    @JsonProperty("netInstSharesBuying")
    public NetInstSharesBuying getNetInstSharesBuying() {
        return netInstSharesBuying;
    }

    /**
     * 
     * @param netInstSharesBuying
     *     The netInstSharesBuying
     */
    @JsonProperty("netInstSharesBuying")
    public void setNetInstSharesBuying(NetInstSharesBuying netInstSharesBuying) {
        this.netInstSharesBuying = netInstSharesBuying;
    }

    /**
     * 
     * @return
     *     The netInstBuyingPercent
     */
    @JsonProperty("netInstBuyingPercent")
    public NetInstBuyingPercent getNetInstBuyingPercent() {
        return netInstBuyingPercent;
    }

    /**
     * 
     * @param netInstBuyingPercent
     *     The netInstBuyingPercent
     */
    @JsonProperty("netInstBuyingPercent")
    public void setNetInstBuyingPercent(NetInstBuyingPercent netInstBuyingPercent) {
        this.netInstBuyingPercent = netInstBuyingPercent;
    }

    /**
     * 
     * @return
     *     The totalInsiderShares
     */
    @JsonProperty("totalInsiderShares")
    public TotalInsiderShares getTotalInsiderShares() {
        return totalInsiderShares;
    }

    /**
     * 
     * @param totalInsiderShares
     *     The totalInsiderShares
     */
    @JsonProperty("totalInsiderShares")
    public void setTotalInsiderShares(TotalInsiderShares totalInsiderShares) {
        this.totalInsiderShares = totalInsiderShares;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(maxAge).append(period).append(buyInfoCount).append(buyInfoShares).append(sellInfoCount).append(sellInfoShares).append(sellPercentInsiderShares).append(netInfoCount).append(netInfoShares).append(netPercentInsiderShares).append(netInstSharesBuying).append(netInstBuyingPercent).append(totalInsiderShares).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NetSharePurchaseActivity) == false) {
            return false;
        }
        NetSharePurchaseActivity rhs = ((NetSharePurchaseActivity) other);
        return new EqualsBuilder().append(maxAge, rhs.maxAge).append(period, rhs.period).append(buyInfoCount, rhs.buyInfoCount).append(buyInfoShares, rhs.buyInfoShares).append(sellInfoCount, rhs.sellInfoCount).append(sellInfoShares, rhs.sellInfoShares).append(sellPercentInsiderShares, rhs.sellPercentInsiderShares).append(netInfoCount, rhs.netInfoCount).append(netInfoShares, rhs.netInfoShares).append(netPercentInsiderShares, rhs.netPercentInsiderShares).append(netInstSharesBuying, rhs.netInstSharesBuying).append(netInstBuyingPercent, rhs.netInstBuyingPercent).append(totalInsiderShares, rhs.totalInsiderShares).isEquals();
    }

}
