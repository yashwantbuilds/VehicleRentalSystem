package com.example.storage;

import com.example.service.BranchRentalService;

import java.util.*;

public class VehicleRentalStorage {

    public static Map<String, BranchRentalService> branchMap = new TreeMap<>();
    public static Set<String> uniqueVehicleIds = new HashSet<>();

}
