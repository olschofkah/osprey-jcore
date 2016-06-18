
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
    "raw",
    "fmt",
    "longFmt"
})
public class Revenue {

    @JsonProperty("raw")
    private Long raw;
    @JsonProperty("fmt")
    private String fmt;
    @JsonProperty("longFmt")
    private String longFmt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The raw
     */
    @JsonProperty("raw")
    public Long getRaw() {
        return raw;
    }

    /**
     * 
     * @param raw
     *     The raw
     */
    @JsonProperty("raw")
    public void setRaw(Long raw) {
        this.raw = raw;
    }

    /**
     * 
     * @return
     *     The fmt
     */
    @JsonProperty("fmt")
    public String getFmt() {
        return fmt;
    }

    /**
     * 
     * @param fmt
     *     The fmt
     */
    @JsonProperty("fmt")
    public void setFmt(String fmt) {
        this.fmt = fmt;
    }

    /**
     * 
     * @return
     *     The longFmt
     */
    @JsonProperty("longFmt")
    public String getLongFmt() {
        return longFmt;
    }

    /**
     * 
     * @param longFmt
     *     The longFmt
     */
    @JsonProperty("longFmt")
    public void setLongFmt(String longFmt) {
        this.longFmt = longFmt;
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
