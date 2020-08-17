/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.facebook.connect;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Facebook ServiceProvider implementation.
 *
 * @author Keith Donald
 * @author Craig Walls
 */
public class FacebookServiceProvider extends AbstractOAuth2ServiceProvider<Facebook> {
	
	private final String appNamespace;
	
	private final String appSecret;
	
	private final String appId;
	
	private final String apiVersion;
	
	public static final String DEFAULT_API_VERSION = "8.0";
	
	private static final String GRAPH_API_URL_FORMAT = "https://graph.facebook.com/v%s/oauth/access_token";
	
	private static final String OAUTH_API_URL_FORMAT = "https://www.facebook.com/v%s/dialog/oauth";
	
	
	/**
	 * Creates a FacebookServiceProvider for the given application ID, secret, and namespace.
	 *
	 * @param appId        The application's App ID as assigned by Facebook
	 * @param appSecret    The application's App Secret as assigned by Facebook
	 * @param appNamespace The application's App Namespace as configured with Facebook. Enables use of Open Graph operations.
	 */
	public FacebookServiceProvider(String appId, String appSecret, String appNamespace) {
		this(appId, appSecret, appNamespace, DEFAULT_API_VERSION);
	}
	
	public FacebookServiceProvider(String appId, String appSecret, String appNamespace, String apiVersion) {
		super(getOAuth2Template(appId, appSecret, apiVersion));
		this.appNamespace = appNamespace;
		this.appSecret = appSecret;
		this.appId = appId;
		this.apiVersion = apiVersion;
	}
	
	private static OAuth2Template getOAuth2Template(String appId, String appSecret, String apiVersion) {
		OAuth2Template oAuth2Template = new OAuth2Template(appId, appSecret, String.format(OAUTH_API_URL_FORMAT, apiVersion),
														   String.format(GRAPH_API_URL_FORMAT, apiVersion));
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}
	
	public Facebook getApi(String accessToken) {
		final FacebookTemplate facebookTemplate = new FacebookTemplate(accessToken, appNamespace, appId, appSecret);
		facebookTemplate.setApiVersion(apiVersion);
		return facebookTemplate;
	}
	
}
