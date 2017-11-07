
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
    "yearly",
    "quarterly"
})
public class FinancialsChart {

    @JsonProperty("yearly")
    private List<Yearly> yearly = new ArrayList<Yearly>();
    @JsonProperty("quarterly")
    private List<Quarterly_> quarterly = new ArrayList<Quarterly_>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The yearly
     */
    @JsonProperty("yearly")
    public List<Yearly> getYearly() {
        return yearly;
    }

    /**
     * 
     * @param yearly
     *     The yearly
     */
    @JsonProperty("yearly")
    public void setYearly(List<Yearly> yearly) {
        this.yearly = yearly;
    }

    /**
     * 
     * @return
     *     The quarterly
     */
    @JsonProperty("quarterly")
    public List<Quarterly_> getQuarterly() {
        return quarterly;
    }

    /**
     * 
     * @param quarterly
     *     The quarterly
     */
    @JsonProperty("quarterly")
    public void setQuarterly(List<Quarterly_> quarterly) {
        this.quarterly = quarterly;
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
