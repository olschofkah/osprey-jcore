
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
    "earnings",
    "exDividendDate",
    "dividendDate"
})
public class CalendarEvents {

    @JsonProperty("maxAge")
    private Integer maxAge;
    @JsonProperty("earnings")
    private Earnings earnings;
    @JsonProperty("exDividendDate")
    private ExDividendDate_ exDividendDate;
    @JsonProperty("dividendDate")
    private DividendDate dividendDate;
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
     *     The earnings
     */
    @JsonProperty("earnings")
    public Earnings getEarnings() {
        return earnings;
    }

    /**
     * 
     * @param earnings
     *     The earnings
     */
    @JsonProperty("earnings")
    public void setEarnings(Earnings earnings) {
        this.earnings = earnings;
    }

    /**
     * 
     * @return
     *     The exDividendDate
     */
    @JsonProperty("exDividendDate")
    public ExDividendDate_ getExDividendDate() {
        return exDividendDate;
    }

    /**
     * 
     * @param exDividendDate
     *     The exDividendDate
     */
    @JsonProperty("exDividendDate")
    public void setExDividendDate(ExDividendDate_ exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    /**
     * 
     * @return
     *     The dividendDate
     */
    @JsonProperty("dividendDate")
    public DividendDate getDividendDate() {
        return dividendDate;
    }

    /**
     * 
     * @param dividendDate
     *     The dividendDate
     */
    @JsonProperty("dividendDate")
    public void setDividendDate(DividendDate dividendDate) {
        this.dividendDate = dividendDate;
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
