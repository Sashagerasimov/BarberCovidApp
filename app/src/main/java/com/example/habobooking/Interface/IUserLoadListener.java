package com.example.habobooking.Interface;

import com.example.habobooking.Model.User;

public interface IUserLoadListener {
    void onUserLoadSuccess(User user, String name);
    void onUserLoadFailed(String message);
}
