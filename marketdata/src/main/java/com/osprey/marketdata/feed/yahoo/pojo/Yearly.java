
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
public class Yearly {

    @JsonProperty("date")
    private Long date;
    @JsonProperty("revenue")
    private Revenue revenue;
    @JsonProperty("earnings")
    private Earnings__ earnings;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public Long getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The revenue
     */
    @JsonProperty("revenue")
    public Revenue getRevenue() {
        return revenue;
    }

    /**
     * 
     * @param revenue
     *     The revenue
     */
    @JsonProperty("revenue")
    public void setRevenue(Revenue revenue) {
        this.revenue = revenue;
    }

    /**
     * 
     * @return
     *     The earnings
     */
    @JsonProperty("earnings")
    public Earnings__ getEarnings() {
        return earnings;
    }

    /**
     * 
     * @param earnings
     *     The earnings
     */
    @JsonProperty("earnings")
    public void setEarnings(Earnings__ earnings) {
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
