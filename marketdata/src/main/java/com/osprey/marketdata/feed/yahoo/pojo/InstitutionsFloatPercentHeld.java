
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
    "fmt"
})
public class InstitutionsFloatPercentHeld {

    @JsonProperty("raw")
    private Double raw;
    @JsonProperty("fmt")
    private String fmt;

    /**
     * 
     * @return
     *     The raw
     */
    @JsonProperty("raw")
    public Double getRaw() {
        return raw;
    }

    /**
     * 
     * @param raw
     *     The raw
     */
    @JsonProperty("raw")
    public void setRaw(Double raw) {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(raw).append(fmt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InstitutionsFloatPercentHeld) == false) {
            return false;
        }
        InstitutionsFloatPercentHeld rhs = ((InstitutionsFloatPercentHeld) other);
        return new EqualsBuilder().append(raw, rhs.raw).append(fmt, rhs.fmt).isEquals();
    }

}
