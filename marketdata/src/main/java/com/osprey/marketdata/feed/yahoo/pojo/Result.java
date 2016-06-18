
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
    "summaryDetail",
    "calendarEvents",
    "earnings",
    "defaultKeyStatistics",
    "summaryProfile",
    "financialData"
})
public class Result {

    @JsonProperty("summaryDetail")
    private SummaryDetail summaryDetail;
    @JsonProperty("calendarEvents")
    private CalendarEvents calendarEvents;
    @JsonProperty("earnings")
    private Earnings_ earnings;
    @JsonProperty("defaultKeyStatistics")
    private DefaultKeyStatistics defaultKeyStatistics;
    @JsonProperty("summaryProfile")
    private SummaryProfile summaryProfile;
    @JsonProperty("financialData")
    private FinancialData financialData;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The summaryDetail
     */
    @JsonProperty("summaryDetail")
    public SummaryDetail getSummaryDetail() {
        return summaryDetail;
    }

    /**
     * 
     * @param summaryDetail
     *     The summaryDetail
     */
    @JsonProperty("summaryDetail")
    public void setSummaryDetail(SummaryDetail summaryDetail) {
        this.summaryDetail = summaryDetail;
    }

    /**
     * 
     * @return
     *     The calendarEvents
     */
    @JsonProperty("calendarEvents")
    public CalendarEvents getCalendarEvents() {
        return calendarEvents;
    }

    /**
     * 
     * @param calendarEvents
     *     The calendarEvents
     */
    @JsonProperty("calendarEvents")
    public void setCalendarEvents(CalendarEvents calendarEvents) {
        this.calendarEvents = calendarEvents;
    }

    /**
     * 
     * @return
     *     The earnings
     */
    @JsonProperty("earnings")
    public Earnings_ getEarnings() {
        return earnings;
    }

    /**
     * 
     * @param earnings
     *     The earnings
     */
    @JsonProperty("earnings")
    public void setEarnings(Earnings_ earnings) {
        this.earnings = earnings;
    }

    /**
     * 
     * @return
     *     The defaultKeyStatistics
     */
    @JsonProperty("defaultKeyStatistics")
    public DefaultKeyStatistics getDefaultKeyStatistics() {
        return defaultKeyStatistics;
    }

    /**
     * 
     * @param defaultKeyStatistics
     *     The defaultKeyStatistics
     */
    @JsonProperty("defaultKeyStatistics")
    public void setDefaultKeyStatistics(DefaultKeyStatistics defaultKeyStatistics) {
        this.defaultKeyStatistics = defaultKeyStatistics;
    }

    /**
     * 
     * @return
     *     The summaryProfile
     */
    @JsonProperty("summaryProfile")
    public SummaryProfile getSummaryProfile() {
        return summaryProfile;
    }

    /**
     * 
     * @param summaryProfile
     *     The summaryProfile
     */
    @JsonProperty("summaryProfile")
    public void setSummaryProfile(SummaryProfile summaryProfile) {
        this.summaryProfile = summaryProfile;
    }

    /**
     * 
     * @return
     *     The financialData
     */
    @JsonProperty("financialData")
    public FinancialData getFinancialData() {
        return financialData;
    }

    /**
     * 
     * @param financialData
     *     The financialData
     */
    @JsonProperty("financialData")
    public void setFinancialData(FinancialData financialData) {
        this.financialData = financialData;
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
