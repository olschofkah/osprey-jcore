
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
    "date",
    "revenue",
    "earnings"
})
public class Quarterly_ {

    @JsonProperty("date")
    private String date;
    @JsonProperty("revenue")
    private Revenue_ revenue;
    @JsonProperty("earnings")
    private Earnings___ earnings;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The revenue
     */
    @JsonProperty("revenue")
    public Revenue_ getRevenue() {
        return revenue;
    }

    /**
     * 
     * @param revenue
     *     The revenue
     */
    @JsonProperty("revenue")
    public void setRevenue(Revenue_ revenue) {
        this.revenue = revenue;
    }

    /**
     * 
     * @return
     *     The earnings
     */
    @JsonProperty("earnings")
    public Earnings___ getEarnings() {
        return earnings;
    }

    /**
     * 
     * @param earnings
     *     The earnings
     */
    @JsonProperty("earnings")
    public void setEarnings(Earnings___ earnings) {
        this.earnings = earnings;
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
