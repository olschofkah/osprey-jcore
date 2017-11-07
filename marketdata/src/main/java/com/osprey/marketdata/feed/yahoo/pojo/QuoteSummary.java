
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
    "result",
    "error"
})
public class QuoteSummary {

    @JsonProperty("result")
    private List<Result> result = new ArrayList<Result>();
    @JsonProperty("error")
    private Object error;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The result
     */
    @JsonProperty("result")
    public List<Result> getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    @JsonProperty("result")
    public void setResult(List<Result> result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The error
     */
    @JsonProperty("error")
    public Object getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    @JsonProperty("error")
    public void setError(Object error) {
        this.error = error;
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
