package it.mapler.walla.api.response;

  public class LoginResponse extends AbsResponse {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1336513212981279784L;
	public String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	
}
