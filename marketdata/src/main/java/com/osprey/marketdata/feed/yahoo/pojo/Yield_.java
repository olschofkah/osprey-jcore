
package com.osprey.marketdata.feed.yahoo.pojo;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "raw" })
public class Yield_ {

	@JsonProperty("raw")
	private Double raw;

	/**
	 * 
	 * @return The raw
	 */
	@JsonProperty("raw")
	public Double getRaw() {
		return raw;
	}

	/**
	 * 
	 * @param raw
	 *            The raw
	 */
	@JsonProperty("raw")
	public void setRaw(Double raw) {
		this.raw = raw;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
