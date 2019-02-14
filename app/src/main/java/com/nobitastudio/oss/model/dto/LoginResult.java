package com.nobitastudio.oss.model.dto;

import com.google.gson.annotations.SerializedName;
import com.nobitastudio.oss.model.entity.User;

import java.io.Serializable;
import java.util.List;

public class LoginResult implements Serializable{

    @SerializedName("uuser")
    private User user;

    public LoginResult() {
    }
}
