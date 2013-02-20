/**
 * Copyright (c) 2013 James Dennes
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.createsend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;

import com.createsend.models.OAuthTokenDetails;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.Configuration;
import com.createsend.util.JerseyClient;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.OAuthAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;

public abstract class CreateSendBase {
	protected static final String urlEncodingScheme = "UTF-8";

	protected JerseyClient jerseyClient = null;

	/**
	 * Refresh the current OAuth token using the current refresh token.
	 * 
	 * @return New OAuthTokenDetails instance containing the new access token,
	 *         'expires in' value, and refresh token.
	 */
	public OAuthTokenDetails refreshToken() throws CreateSendException {
		OAuthTokenDetails result = null;
		AuthenticationDetails auth = this.jerseyClient
				.getAuthenticationDetails();
		if (auth != null && auth instanceof OAuthAuthenticationDetails) {
			OAuthAuthenticationDetails oauthDetails = (OAuthAuthenticationDetails) auth;
			String body = "grant_type=refresh_token";
			try {
				body += "&refresh_token="
						+ URLEncoder.encode(oauthDetails.getRefreshToken(),
								urlEncodingScheme);
			} catch (UnsupportedEncodingException e) {
				body = null;
			}
			JerseyClient oauthClient = new JerseyClientImpl(null);
			
	    	// TODO: Use a custom error deserialiser in the following post

			result = oauthClient.post(Configuration.Current.getOAuthBaseUri(),
					OAuthTokenDetails.class, body,
					MediaType.APPLICATION_FORM_URLENCODED_TYPE, "token");
			if (result != null && result.access_token != null
					&& result.refresh_token != null) {
				AuthenticationDetails newAuthDetails = new OAuthAuthenticationDetails(
						result.access_token, result.refresh_token);
				this.jerseyClient.setAuthenticationDetails(newAuthDetails);
			}
		}
		return result;
	}
}
