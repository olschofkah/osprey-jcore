
package com.osprey.marketdata.feed.ychart.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "current_page_num",
    "start_index",
    "num_items",
    "num_pages",
    "end_index"
})
public class PaginationInfo {

    @JsonProperty("current_page_num")
    private int currentPageNum;
    @JsonProperty("start_index")
    private int startIndex;
    @JsonProperty("num_items")
    private int numItems;
    @JsonProperty("num_pages")
    private int numPages;
    @JsonProperty("end_index")
    private int endIndex;

    /**
     * 
     * @return
     *     The currentPageNum
     */
    @JsonProperty("current_page_num")
    public int getCurrentPageNum() {
        return currentPageNum;
    }

    /**
     * 
     * @param currentPageNum
     *     The current_page_num
     */
    @JsonProperty("current_page_num")
    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    /**
     * 
     * @return
     *     The startIndex
     */
    @JsonProperty("start_index")
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 
     * @param startIndex
     *     The start_index
     */
    @JsonProperty("start_index")
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 
     * @return
     *     The numItems
     */
    @JsonProperty("num_items")
    public int getNumItems() {
        return numItems;
    }

    /**
     * 
     * @param numItems
     *     The num_items
     */
    @JsonProperty("num_items")
    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    /**
     * 
     * @return
     *     The numPages
     */
    @JsonProperty("num_pages")
    public int getNumPages() {
        return numPages;
    }

    /**
     * 
     * @param numPages
     *     The num_pages
     */
    @JsonProperty("num_pages")
    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    /**
     * 
     * @return
     *     The endIndex
     */
    @JsonProperty("end_index")
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * 
     * @param endIndex
     *     The end_index
     */
    @JsonProperty("end_index")
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
