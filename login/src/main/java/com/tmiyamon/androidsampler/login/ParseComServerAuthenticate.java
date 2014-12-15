package com.tmiyamon.androidsampler.login;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Handles the comminication with Parse.com
 *
 * User: udinic
 * Date: 3/27/13
 * Time: 3:30 AM
 */
public class ParseComServerAuthenticate implements ServerAuthenticate{
    public static final String APPLICATION_ID = "BdQge5lVtYOuhjUbAKB4jFxxYreoisSPbWSKGCTo";
    public static final String REST_API_KEY = "JkGJpaJUOnfTK5nYmfBL1fLTgKM7Dm0fGMMTyE9o";

    private static RestAdapter restAdapter =
            new RestAdapter.Builder()
                .setEndpoint("https://api.parse.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("X-Parse-Application-Id", APPLICATION_ID);
                        request.addHeader("X-Parse-REST-API-Key", REST_API_KEY);
                    }
                })
                .build();

    public static ParseService parse = restAdapter.create(ParseService.class);

    @Override
    public String userSignUp(String name, String email, String pass, String authType) throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("username", email);
        body.put("password", pass);

        return parse.signup(body).get("sessionToken");
    }

    @Override
    public String userSignIn(String user, String pass, String authType) throws Exception {
        return parse.login(user, pass).get("sessionToken");
    }


    private class ParseComError implements Serializable {
        int code;
        String error;
    }
    private class User implements Serializable {

        private String firstName;
        private String lastName;
        private String username;
        private String phone;
        private String objectId;
        public String sessionToken;
        private String gravatarId;
        private String avatarUrl;


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public void setGravatarId(String gravatarId) {
            this.gravatarId = gravatarId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
