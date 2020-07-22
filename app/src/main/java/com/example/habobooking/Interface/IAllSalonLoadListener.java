package com.example.habobooking.Interface;

import java.util.List;

public interface IAllSalonLoadListener {
    void onAllSalonLoadSuccess(List<String> areaNameList);
    void onAllSalonLoadFailed(String message);
}
