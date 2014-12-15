package com.tmiyamon.androidsampler.login;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by tmiyamon on 12/16/14.
 */
public class ParseClient {
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

    public static final ParseService instance = restAdapter.create(ParseService.class);

    private ParseClient(){};
}
