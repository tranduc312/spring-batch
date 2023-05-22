package com.batch.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class JobParamRequest
{

	@JsonProperty("key")
	private String key;

	@JsonProperty("value")
	private String value;
}
