
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
	"raw"
})
public class TotalAssets_ {

	@JsonProperty("raw")
	private Long raw;

	/**
	 * 
	 * @return The raw
	 */
	@JsonProperty("raw")
	public Long getRaw() {
		return raw;
	}

	/**
	 * 
	 * @param raw
	 *            The raw
	 */
	@JsonProperty("raw")
	public void setRaw(Long raw) {
		this.raw = raw;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
