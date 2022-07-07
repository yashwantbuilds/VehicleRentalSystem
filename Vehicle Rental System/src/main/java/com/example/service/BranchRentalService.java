package com.example.service;

import com.example.objects.Vehicle;

import java.util.*;

public interface BranchRentalService {

    Boolean addVehicle(String type, String vehicleId, int price);

    int bookAVehicle(String type, int startTime, int endTime);

    Set<Vehicle> getAvailableVehicle(int startTime, int endTime, String vehicleType);

    String getAllAvailableVehicles(int startTime, int endTime);

    void increasePrice();
}
