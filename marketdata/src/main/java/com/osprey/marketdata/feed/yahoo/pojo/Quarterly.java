
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
    "actual",
    "estimate"
})
public class Quarterly {

    @JsonProperty("date")
    private String date;
    @JsonProperty("actual")
    private Actual actual;
    @JsonProperty("estimate")
    private Estimate estimate;
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
     *     The actual
     */
    @JsonProperty("actual")
    public Actual getActual() {
        return actual;
    }

    /**
     * 
     * @param actual
     *     The actual
     */
    @JsonProperty("actual")
    public void setActual(Actual actual) {
        this.actual = actual;
    }

    /**
     * 
     * @return
     *     The estimate
     */
    @JsonProperty("estimate")
    public Estimate getEstimate() {
        return estimate;
    }

    /**
     * 
     * @param estimate
     *     The estimate
     */
    @JsonProperty("estimate")
    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
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
