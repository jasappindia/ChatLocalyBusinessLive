package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 2/7/2018.
 */

public class CheckStatusModel {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    public class Data {

        @SerializedName("b_id")
        @Expose
        private String bId;
        @SerializedName("b_user_id")
        @Expose
        private String bUserId;
        @SerializedName("business_status")
        @Expose
        private String businessStatus;
        @SerializedName("login_key")
        @Expose
        private String loginKey;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("resultCode")
        @Expose
        private String resultCode;

        @SerializedName("app_version")
        @Expose
        private String app_version;
        @SerializedName("status_comment")
        @Expose
        private String status_comment;

        @SerializedName("changes")
        @Expose
        private String changes;
        @SerializedName("status_title")
        @Expose
        private String status_title;

        public void setStatus_comment(String status_comment) {
            this.status_comment = status_comment;
        }

        public String getStatus_title() {
            return status_title;
        }

        public void setStatus_title(String status_title) {
            this.status_title = status_title;
        }

        public String getChanges() {
            return changes;
        }

        public void setChanges(String changes) {
            this.changes = changes;
        }

        public String getStatus_comment()
        {
            return status_comment;
        }   public String getApp_version()
        {
            return app_version;
        }
        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
        }

        public String getBUserId() {
            return bUserId;
        }

        public void setBUserId(String bUserId) {
            this.bUserId = bUserId;
        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

    }
}

