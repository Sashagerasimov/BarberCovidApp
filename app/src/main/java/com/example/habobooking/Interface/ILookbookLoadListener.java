package com.example.habobooking.Interface;

import com.example.habobooking.Model.Banner;

import java.util.List;

public interface ILookbookLoadListener {
    void onLookbookLoadSuccess(List<Banner> banners);
    void onLookbookLoadFailed(String message);
}
