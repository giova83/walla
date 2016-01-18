package it.mapler.walla.api.response;

public class RedirectResponse extends AbsResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String redirectUrl;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	

}
