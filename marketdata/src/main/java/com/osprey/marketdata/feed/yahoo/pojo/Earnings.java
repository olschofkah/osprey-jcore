
package com.osprey.marketdata.feed.yahoo.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "earningsDate",
    "earningsAverage",
    "earningsLow",
    "earningsHigh",
    "revenueAverage",
    "revenueLow",
    "revenueHigh"
})
public class Earnings {

    @JsonProperty("earningsDate")
    private List<EarningsDate> earningsDate = new ArrayList<EarningsDate>();
    @JsonProperty("earningsAverage")
    private EarningsAverage earningsAverage;
    @JsonProperty("earningsLow")
    private EarningsLow earningsLow;
    @JsonProperty("earningsHigh")
    private EarningsHigh earningsHigh;
    @JsonProperty("revenueAverage")
    private RevenueAverage revenueAverage;
    @JsonProperty("revenueLow")
    private RevenueLow revenueLow;
    @JsonProperty("revenueHigh")
    private RevenueHigh revenueHigh;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The earningsDate
     */
    @JsonProperty("earningsDate")
    public List<EarningsDate> getEarningsDate() {
        return earningsDate;
    }

    /**
     * 
     * @param earningsDate
     *     The earningsDate
     */
    @JsonProperty("earningsDate")
    public void setEarningsDate(List<EarningsDate> earningsDate) {
        this.earningsDate = earningsDate;
    }

    /**
     * 
     * @return
     *     The earningsAverage
     */
    @JsonProperty("earningsAverage")
    public EarningsAverage getEarningsAverage() {
        return earningsAverage;
    }

    /**
     * 
     * @param earningsAverage
     *     The earningsAverage
     */
    @JsonProperty("earningsAverage")
    public void setEarningsAverage(EarningsAverage earningsAverage) {
        this.earningsAverage = earningsAverage;
    }

    /**
     * 
     * @return
     *     The earningsLow
     */
    @JsonProperty("earningsLow")
    public EarningsLow getEarningsLow() {
        return earningsLow;
    }

    /**
     * 
     * @param earningsLow
     *     The earningsLow
     */
    @JsonProperty("earningsLow")
    public void setEarningsLow(EarningsLow earningsLow) {
        this.earningsLow = earningsLow;
    }

    /**
     * 
     * @return
     *     The earningsHigh
     */
    @JsonProperty("earningsHigh")
    public EarningsHigh getEarningsHigh() {
        return earningsHigh;
    }

    /**
     * 
     * @param earningsHigh
     *     The earningsHigh
     */
    @JsonProperty("earningsHigh")
    public void setEarningsHigh(EarningsHigh earningsHigh) {
        this.earningsHigh = earningsHigh;
    }

    /**
     * 
     * @return
     *     The revenueAverage
     */
    @JsonProperty("revenueAverage")
    public RevenueAverage getRevenueAverage() {
        return revenueAverage;
    }

    /**
     * 
     * @param revenueAverage
     *     The revenueAverage
     */
    @JsonProperty("revenueAverage")
    public void setRevenueAverage(RevenueAverage revenueAverage) {
        this.revenueAverage = revenueAverage;
    }

    /**
     * 
     * @return
     *     The revenueLow
     */
    @JsonProperty("revenueLow")
    public RevenueLow getRevenueLow() {
        return revenueLow;
    }

    /**
     * 
     * @param revenueLow
     *     The revenueLow
     */
    @JsonProperty("revenueLow")
    public void setRevenueLow(RevenueLow revenueLow) {
        this.revenueLow = revenueLow;
    }

    /**
     * 
     * @return
     *     The revenueHigh
     */
    @JsonProperty("revenueHigh")
    public RevenueHigh getRevenueHigh() {
        return revenueHigh;
    }

    /**
     * 
     * @param revenueHigh
     *     The revenueHigh
     */
    @JsonProperty("revenueHigh")
    public void setRevenueHigh(RevenueHigh revenueHigh) {
        this.revenueHigh = revenueHigh;
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
