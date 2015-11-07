package com.wku.mandi.rest.response;

import org.codehaus.jackson.annotate.JsonProperty;

public class BooleanResult {
	
	@JsonProperty
	private boolean result;
	
	public BooleanResult(boolean result) {
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
