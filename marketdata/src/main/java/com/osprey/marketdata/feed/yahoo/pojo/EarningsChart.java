
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
    "quarterly",
    "currentQuarterEstimate",
    "currentQuarterEstimateDate",
    "currentQuarterEstimateYear"
})
public class EarningsChart {

    @JsonProperty("quarterly")
    private List<Quarterly> quarterly = new ArrayList<Quarterly>();
    @JsonProperty("currentQuarterEstimate")
    private CurrentQuarterEstimate currentQuarterEstimate;
    @JsonProperty("currentQuarterEstimateDate")
    private String currentQuarterEstimateDate;
    @JsonProperty("currentQuarterEstimateYear")
    private Integer currentQuarterEstimateYear;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The quarterly
     */
    @JsonProperty("quarterly")
    public List<Quarterly> getQuarterly() {
        return quarterly;
    }

    /**
     * 
     * @param quarterly
     *     The quarterly
     */
    @JsonProperty("quarterly")
    public void setQuarterly(List<Quarterly> quarterly) {
        this.quarterly = quarterly;
    }

    /**
     * 
     * @return
     *     The currentQuarterEstimate
     */
    @JsonProperty("currentQuarterEstimate")
    public CurrentQuarterEstimate getCurrentQuarterEstimate() {
        return currentQuarterEstimate;
    }

    /**
     * 
     * @param currentQuarterEstimate
     *     The currentQuarterEstimate
     */
    @JsonProperty("currentQuarterEstimate")
    public void setCurrentQuarterEstimate(CurrentQuarterEstimate currentQuarterEstimate) {
        this.currentQuarterEstimate = currentQuarterEstimate;
    }

    /**
     * 
     * @return
     *     The currentQuarterEstimateDate
     */
    @JsonProperty("currentQuarterEstimateDate")
    public String getCurrentQuarterEstimateDate() {
        return currentQuarterEstimateDate;
    }

    /**
     * 
     * @param currentQuarterEstimateDate
     *     The currentQuarterEstimateDate
     */
    @JsonProperty("currentQuarterEstimateDate")
    public void setCurrentQuarterEstimateDate(String currentQuarterEstimateDate) {
        this.currentQuarterEstimateDate = currentQuarterEstimateDate;
    }

    /**
     * 
     * @return
     *     The currentQuarterEstimateYear
     */
    @JsonProperty("currentQuarterEstimateYear")
    public Integer getCurrentQuarterEstimateYear() {
        return currentQuarterEstimateYear;
    }

    /**
     * 
     * @param currentQuarterEstimateYear
     *     The currentQuarterEstimateYear
     */
    @JsonProperty("currentQuarterEstimateYear")
    public void setCurrentQuarterEstimateYear(Integer currentQuarterEstimateYear) {
        this.currentQuarterEstimateYear = currentQuarterEstimateYear;
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
