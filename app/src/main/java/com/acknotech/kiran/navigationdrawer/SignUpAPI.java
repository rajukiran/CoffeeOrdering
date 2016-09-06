package com.acknotech.kiran.navigationdrawer;

import com.acknotech.kiran.navigationdrawer.models.SignUpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by kiran on 20/8/16.
 */
public interface SignUpAPI {

    @POST("/SportsApp/registration")
    Call<SignUpParams> getErrDesc(@Body SignUpRequest registrationRequest);

}
