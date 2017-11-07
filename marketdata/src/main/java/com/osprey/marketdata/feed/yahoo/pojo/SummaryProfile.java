
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
    "address1",
    "city",
    "state",
    "zip",
    "country",
    "phone",
    "website",
    "industry",
    "sector",
    "longBusinessSummary",
    "fullTimeEmployees",
    "companyOfficers",
    "maxAge"
})
public class SummaryProfile {

    @JsonProperty("address1")
    private String address1;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("website")
    private String website;
    @JsonProperty("industry")
    private String industry;
    @JsonProperty("sector")
    private String sector;
    @JsonProperty("longBusinessSummary")
    private String longBusinessSummary;
    @JsonProperty("fullTimeEmployees")
    private Integer fullTimeEmployees;
    @JsonProperty("companyOfficers")
    private List<Object> companyOfficers = new ArrayList<Object>();
    @JsonProperty("maxAge")
    private Integer maxAge;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The address1
     */
    @JsonProperty("address1")
    public String getAddress1() {
        return address1;
    }

    /**
     * 
     * @param address1
     *     The address1
     */
    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 
     * @return
     *     The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The zip
     */
    @JsonProperty("zip")
    public String getZip() {
        return zip;
    }

    /**
     * 
     * @param zip
     *     The zip
     */
    @JsonProperty("zip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 
     * @return
     *     The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The phone
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The website
     */
    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    /**
     * 
     * @param website
     *     The website
     */
    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 
     * @return
     *     The industry
     */
    @JsonProperty("industry")
    public String getIndustry() {
        return industry;
    }

    /**
     * 
     * @param industry
     *     The industry
     */
    @JsonProperty("industry")
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * 
     * @return
     *     The sector
     */
    @JsonProperty("sector")
    public String getSector() {
        return sector;
    }

    /**
     * 
     * @param sector
     *     The sector
     */
    @JsonProperty("sector")
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * 
     * @return
     *     The longBusinessSummary
     */
    @JsonProperty("longBusinessSummary")
    public String getLongBusinessSummary() {
        return longBusinessSummary;
    }

    /**
     * 
     * @param longBusinessSummary
     *     The longBusinessSummary
     */
    @JsonProperty("longBusinessSummary")
    public void setLongBusinessSummary(String longBusinessSummary) {
        this.longBusinessSummary = longBusinessSummary;
    }

    /**
     * 
     * @return
     *     The fullTimeEmployees
     */
    @JsonProperty("fullTimeEmployees")
    public Integer getFullTimeEmployees() {
        return fullTimeEmployees;
    }

    /**
     * 
     * @param fullTimeEmployees
     *     The fullTimeEmployees
     */
    @JsonProperty("fullTimeEmployees")
    public void setFullTimeEmployees(Integer fullTimeEmployees) {
        this.fullTimeEmployees = fullTimeEmployees;
    }

    /**
     * 
     * @return
     *     The companyOfficers
     */
    @JsonProperty("companyOfficers")
    public List<Object> getCompanyOfficers() {
        return companyOfficers;
    }

    /**
     * 
     * @param companyOfficers
     *     The companyOfficers
     */
    @JsonProperty("companyOfficers")
    public void setCompanyOfficers(List<Object> companyOfficers) {
        this.companyOfficers = companyOfficers;
    }

    /**
     * 
     * @return
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * 
     * @param maxAge
     *     The maxAge
     */
    @JsonProperty("maxAge")
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
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
