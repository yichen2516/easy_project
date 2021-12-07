package online.lbprotocol.easy.hbapi.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TokenResponse {
	private String access_token;
	private String refresh_token;
	private String license;
	private UserInfo user_info;
	private String scope;
	private boolean active;
	private String token_type;
	private int expires_in;
}
