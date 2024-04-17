package kk.projects.interviewtask.rest;


import java.util.List;

import kk.projects.interviewtask.model.task_model;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("posts")
    Call<List<task_model.ResponseItem>> taskload();

}
