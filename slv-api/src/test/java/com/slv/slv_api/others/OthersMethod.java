package com.slv.slv_api.others;

/**
 * Rest api URLs for others methods
 * @author cmarchive
 *
 */
public enum OthersMethod {
	RECOVER_PASSWORD("publicconfig/sendResetPasswordRequestByMail");

	private String url;

	private OthersMethod(final String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}
}
