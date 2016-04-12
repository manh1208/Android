package com.manhnv.fev.services;

import com.manhnv.fev.dto.Member;
import com.manhnv.fev.response.SyncResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by ManhNV on 12/27/2015.
 */
public interface IUserService {
    @GET("/User")
    void getUser(Callback<List<Member>> callback);

    @POST("/Account/Register")
    void addUser(@Body Member member, Callback<String> callback);

    @POST("/Member/SyncData")
    void syncData(@Body List<Member> list, Callback<SyncResponse> callback);
}
