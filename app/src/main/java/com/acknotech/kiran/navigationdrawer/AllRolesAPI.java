package com.acknotech.kiran.navigationdrawer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by kiran on 6/9/16.
 */
public interface AllRolesAPI {
    @GET("/SportsApp/allroles")
    Call<AllRolesParams> getAllRoles();
}
