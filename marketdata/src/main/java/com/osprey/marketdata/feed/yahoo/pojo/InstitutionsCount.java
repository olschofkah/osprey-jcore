
package com.osprey.marketdata.feed.yahoo.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "raw",
    "fmt",
    "longFmt"
})
public class InstitutionsCount {

    @JsonProperty("raw")
    private Long raw;
    @JsonProperty("fmt")
    private String fmt;
    @JsonProperty("longFmt")
    private String longFmt;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(raw).append(fmt).append(longFmt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InstitutionsCount) == false) {
            return false;
        }
        InstitutionsCount rhs = ((InstitutionsCount) other);
        return new EqualsBuilder().append(raw, rhs.raw).append(fmt, rhs.fmt).append(longFmt, rhs.longFmt).isEquals();
    }

}
