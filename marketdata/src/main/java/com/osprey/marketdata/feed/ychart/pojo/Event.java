
package com.osprey.marketdata.feed.ychart.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "event_group",
    "company",
    "upcoming",
    "today",
    "event_group_display",
    "local_begin_time",
    "event_description",
    "local_begin_date",
    "url"
})
public class Event {

    @JsonProperty("event_group")
    private String eventGroup;
    @JsonProperty("company")
    private String company;
    @JsonProperty("upcoming")
    private boolean upcoming;
    @JsonProperty("today")
    private boolean today;
    @JsonProperty("event_group_display")
    private String eventGroupDisplay;
    @JsonProperty("local_begin_time")
    private String localBeginTime;
    @JsonProperty("event_description")
    private String eventDescription;
    @JsonProperty("local_begin_date")
    private String localBeginDate;
    @JsonProperty("url")
    private String url;

    /**
     * 
     * @return
     *     The eventGroup
     */
    @JsonProperty("event_group")
    public String getEventGroup() {
        return eventGroup;
    }

    /**
     * 
     * @param eventGroup
     *     The event_group
     */
    @JsonProperty("event_group")
    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
    }

    /**
     * 
     * @return
     *     The company
     */
    @JsonProperty("company")
    public String getCompany() {
        return company;
    }

    /**
     * 
     * @param company
     *     The company
     */
    @JsonProperty("company")
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 
     * @return
     *     The upcoming
     */
    @JsonProperty("upcoming")
    public boolean isUpcoming() {
        return upcoming;
    }

    /**
     * 
     * @param upcoming
     *     The upcoming
     */
    @JsonProperty("upcoming")
    public void setUpcoming(boolean upcoming) {
        this.upcoming = upcoming;
    }

    /**
     * 
     * @return
     *     The today
     */
    @JsonProperty("today")
    public boolean isToday() {
        return today;
    }

    /**
     * 
     * @param today
     *     The today
     */
    @JsonProperty("today")
    public void setToday(boolean today) {
        this.today = today;
    }

    /**
     * 
     * @return
     *     The eventGroupDisplay
     */
    @JsonProperty("event_group_display")
    public String getEventGroupDisplay() {
        return eventGroupDisplay;
    }

    /**
     * 
     * @param eventGroupDisplay
     *     The event_group_display
     */
    @JsonProperty("event_group_display")
    public void setEventGroupDisplay(String eventGroupDisplay) {
        this.eventGroupDisplay = eventGroupDisplay;
    }

    /**
     * 
     * @return
     *     The localBeginTime
     */
    @JsonProperty("local_begin_time")
    public String getLocalBeginTime() {
        return localBeginTime;
    }

    /**
     * 
     * @param localBeginTime
     *     The local_begin_time
     */
    @JsonProperty("local_begin_time")
    public void setLocalBeginTime(String localBeginTime) {
        this.localBeginTime = localBeginTime;
    }

    /**
     * 
     * @return
     *     The eventDescription
     */
    @JsonProperty("event_description")
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * 
     * @param eventDescription
     *     The event_description
     */
    @JsonProperty("event_description")
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    /**
     * 
     * @return
     *     The localBeginDate
     */
    @JsonProperty("local_begin_date")
    public String getLocalBeginDate() {
        return localBeginDate;
    }

    /**
     * 
     * @param localBeginDate
     *     The local_begin_date
     */
    @JsonProperty("local_begin_date")
    public void setLocalBeginDate(String localBeginDate) {
        this.localBeginDate = localBeginDate;
    }

    /**
     * 
     * @return
     *     The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
