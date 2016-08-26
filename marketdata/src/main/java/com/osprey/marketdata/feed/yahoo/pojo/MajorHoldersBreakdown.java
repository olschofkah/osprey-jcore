
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
    "maxAge",
    "insidersPercentHeld",
    "institutionsPercentHeld",
    "institutionsFloatPercentHeld",
    "institutionsCount"
})
public class MajorHoldersBreakdown {

    @JsonProperty("maxAge")
    private Long maxAge;
    @JsonProperty("insidersPercentHeld")
    private InsidersPercentHeld insidersPercentHeld;
    @JsonProperty("institutionsPercentHeld")
    private InstitutionsPercentHeld institutionsPercentHeld;
    @JsonProperty("institutionsFloatPercentHeld")
    private InstitutionsFloatPercentHeld institutionsFloatPercentHeld;
    @JsonProperty("institutionsCount")
    private InstitutionsCount institutionsCount;

    /**
     * 
     * @return
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    /**
     * 
     * @param maxAge
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * 
     * @return
     *     The insidersPercentHeld
     */
    @JsonProperty("insidersPercentHeld")
    public InsidersPercentHeld getInsidersPercentHeld() {
        return insidersPercentHeld;
    }

    /**
     * 
     * @param insidersPercentHeld
     *     The insidersPercentHeld
     */
    @JsonProperty("insidersPercentHeld")
    public void setInsidersPercentHeld(InsidersPercentHeld insidersPercentHeld) {
        this.insidersPercentHeld = insidersPercentHeld;
    }

    /**
     * 
     * @return
     *     The institutionsPercentHeld
     */
    @JsonProperty("institutionsPercentHeld")
    public InstitutionsPercentHeld getInstitutionsPercentHeld() {
        return institutionsPercentHeld;
    }

    /**
     * 
     * @param institutionsPercentHeld
     *     The institutionsPercentHeld
     */
    @JsonProperty("institutionsPercentHeld")
    public void setInstitutionsPercentHeld(InstitutionsPercentHeld institutionsPercentHeld) {
        this.institutionsPercentHeld = institutionsPercentHeld;
    }

    /**
     * 
     * @return
     *     The institutionsFloatPercentHeld
     */
    @JsonProperty("institutionsFloatPercentHeld")
    public InstitutionsFloatPercentHeld getInstitutionsFloatPercentHeld() {
        return institutionsFloatPercentHeld;
    }

    /**
     * 
     * @param institutionsFloatPercentHeld
     *     The institutionsFloatPercentHeld
     */
    @JsonProperty("institutionsFloatPercentHeld")
    public void setInstitutionsFloatPercentHeld(InstitutionsFloatPercentHeld institutionsFloatPercentHeld) {
        this.institutionsFloatPercentHeld = institutionsFloatPercentHeld;
    }

    /**
     * 
     * @return
     *     The institutionsCount
     */
    @JsonProperty("institutionsCount")
    public InstitutionsCount getInstitutionsCount() {
        return institutionsCount;
    }

    /**
     * 
     * @param institutionsCount
     *     The institutionsCount
     */
    @JsonProperty("institutionsCount")
    public void setInstitutionsCount(InstitutionsCount institutionsCount) {
        this.institutionsCount = institutionsCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(maxAge).append(insidersPercentHeld).append(institutionsPercentHeld).append(institutionsFloatPercentHeld).append(institutionsCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MajorHoldersBreakdown) == false) {
            return false;
        }
        MajorHoldersBreakdown rhs = ((MajorHoldersBreakdown) other);
        return new EqualsBuilder().append(maxAge, rhs.maxAge).append(insidersPercentHeld, rhs.insidersPercentHeld).append(institutionsPercentHeld, rhs.institutionsPercentHeld).append(institutionsFloatPercentHeld, rhs.institutionsFloatPercentHeld).append(institutionsCount, rhs.institutionsCount).isEquals();
    }

}
