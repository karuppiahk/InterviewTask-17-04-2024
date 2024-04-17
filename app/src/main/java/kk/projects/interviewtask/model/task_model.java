package kk.projects.interviewtask.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class task_model {


    private List<ResponseItem> response;

    public List<ResponseItem> getResponse() {
        return response;
    }


    public class ResponseItem {


        @SerializedName("id")
        private String id;

        @SerializedName("userId")
        private String userId;

        @SerializedName("title")
        private String title;
        @SerializedName("body")
        private String body;


        public String getId() {
            return id;
        }

        public String getUserId() {
            return userId;
        }

        public String getBody() {
            return body;
        }

        public String getTitle() {
            return title;
        }
    }

}
