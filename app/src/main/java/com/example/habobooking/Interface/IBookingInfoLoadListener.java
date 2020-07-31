package com.example.habobooking.Interface;

import com.example.habobooking.Model.BookingInformation;

public interface IBookingInfoLoadListener {
    void onBookingInfoLoadEmpty();
    void onBookingInfoLoadSuccess(BookingInformation bookingInformation);
    void onBookingInfoLoadFailed(String message);
}
