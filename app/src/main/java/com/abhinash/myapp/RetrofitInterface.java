package com.abhinash.myapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("/users/sumit2607/repos")
    Call<List<State>> getStates();
    @POST("details/Details/")
    Call<State> addState(@Body State state);
/*    @PUT("details/Details/{id}/")
    Call<List<State>> updateState(@Path("id") int id, @Body State state);*/
    @DELETE("details/Details/{id}")
    Call<State> deleteState(@Path("id") int id);
}