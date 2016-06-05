package com.manhnv.fev.services;

/**
 * Created by ManhNV on 12/27/2015.
 */
public class RestService   {
    private static final String URL = "http://fevsystem-001-site1.1tempurl.com/apii";
    private retrofit.RestAdapter restAdapter;
    private IUserService userService;

    public RestService()
    {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();
        userService = restAdapter.create(IUserService.class);

    }

    public IUserService getUserService(){return userService;}

}
