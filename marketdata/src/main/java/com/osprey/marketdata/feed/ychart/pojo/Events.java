
package com.osprey.marketdata.feed.ychart.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
   // "paginationInfo",
    "events",
    "time"
})
public class Events {
//
//    @JsonProperty("paginationInfo")
//    private PaginationInfo paginationInfo;
	
    @JsonProperty("events")
    private List<Event> events = new ArrayList<Event>();
    @JsonProperty("time")
    private String time;

//    /**
//     * 
//     * @return
//     *     The paginationInfo
//     */
//    @JsonProperty("paginationInfo")
//    public PaginationInfo getPaginationInfo() {
//        return paginationInfo;
//    }
//
//    /**
//     * 
//     * @param paginationInfo
//     *     The paginationInfo
//     */
//    @JsonProperty("paginationInfo")
//    public void setPaginationInfo(PaginationInfo paginationInfo) {
//        this.paginationInfo = paginationInfo;
//    }

    /**
     * 
     * @return
     *     The events
     */
    @JsonProperty("events")
    public List<Event> getEvents() {
        return events;
    }

    /**
     * 
     * @param events
     *     The events
     */
    @JsonProperty("events")
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * 
     * @return
     *     The time
     */
    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
