
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
    "earningsChart",
    "financialsChart"
})
public class Earnings_ {

    @JsonProperty("maxAge")
    private Long maxAge;
    @JsonProperty("earningsChart")
    private EarningsChart earningsChart;
    @JsonProperty("financialsChart")
    private FinancialsChart financialsChart;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The earningsChart
     */
    @JsonProperty("earningsChart")
    public EarningsChart getEarningsChart() {
        return earningsChart;
    }

    /**
     * 
     * @param earningsChart
     *     The earningsChart
     */
    @JsonProperty("earningsChart")
    public void setEarningsChart(EarningsChart earningsChart) {
        this.earningsChart = earningsChart;
    }

    /**
     * 
     * @return
     *     The financialsChart
     */
    @JsonProperty("financialsChart")
    public FinancialsChart getFinancialsChart() {
        return financialsChart;
    }

    /**
     * 
     * @param financialsChart
     *     The financialsChart
     */
    @JsonProperty("financialsChart")
    public void setFinancialsChart(FinancialsChart financialsChart) {
        this.financialsChart = financialsChart;
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
