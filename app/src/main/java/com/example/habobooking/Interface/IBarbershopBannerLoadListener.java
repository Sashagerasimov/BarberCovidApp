package com.example.habobooking.Interface;

import com.example.habobooking.Model.Banner;
import com.example.habobooking.Model.Barbershop;

import java.util.List;

public interface IBarbershopBannerLoadListener
{
    void onBarbershopBannerLoadSuccess(List<Barbershop> banners);
    void onBarbershopBannerLoadFailed(String message);
}
