package com.chatlocalybusiness.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by windows on 2/5/2018.
 */

public class AdminContactMOdel {

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

    @SerializedName("w_b_user_id")
    @Expose
    private String wBUserId;
    @SerializedName("b_mobile_number")
    @Expose
    private String bMobileNumber;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    public String getWBUserId() {
        return wBUserId;
    }

    public void setWBUserId(String wBUserId) {
        this.wBUserId = wBUserId;
    }

    public String getBMobileNumber() {
        return bMobileNumber;
    }

    public void setBMobileNumber(String bMobileNumber) {
        this.bMobileNumber = bMobileNumber;
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