package com.slv.slv_api.services;

/**
 * Result of {@link JsonDiffService}. Contains information on the comparison
 * between two Json {@link String}.
 * 
 * @author atran
 */
public class JsonDiffResult {

	/**
	 * <p>
	 * Indicate if the result of the comparison: <code>true</code> if Json
	 * attributes are equals in the two Json {@link String}, <code>false</code>
	 * otherwise
	 * </p>
	 */
	private boolean equals;

	/**
	 * Error message. Blank if {@link #equals} is <code>true</code>.
	 */
	private String errorMessage;

	/**
	 * WS response
	 */
	private String response;

	/**
	 * Constructor of {@link JsonDiffResult}
	 * 
	 * @param equals
	 *            status of the comparison : <code>true</code> if Json
	 *            attributes are equals in the two Json {@link String},
	 *            <code>false</code> otherwise
	 * @param errorMessage
	 *            Blank if {@link #equals} is <code>true</code>, otherwise
	 *            formatted message containing information on the differences
	 */
	public JsonDiffResult(boolean equals, String errorMessage) {
		super();
		this.equals = equals;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return {@link JsonDiffResult#equals}
	 */
	public boolean isEquals() {
		return equals;
	}

	/**
	 * Set {@link JsonDiffResult#equals}
	 * 
	 * @param equals
	 *            equals to set.
	 */
	public void setEquals(boolean equals) {
		this.equals = equals;
	}

	/**
	 * @return {@link JsonDiffResult#errorMessage}
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Set {@link JsonDiffResult#errorMessage}
	 * 
	 * @param errorMessage errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return {@link JsonDiffResult#response}
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Set {@link JsonDiffResult#response}
	 * 
	 * @param response response to set.
	 */
	public void setResponse(String response) {
		this.response = response;
	}
}
