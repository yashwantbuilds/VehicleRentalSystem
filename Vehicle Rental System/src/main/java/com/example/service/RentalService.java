package com.example.service;

import com.example.enums.BranchRentalServiceTypes;

import java.util.List;

public interface RentalService {

    Boolean onBoardBranch(String branchName, List<String> types);

    Boolean onBoardBranchForType(String branchName, BranchRentalServiceTypes branchRentalServiceTypes, List<String> types);

    Boolean onBoardVehicle(String branchName, String type, String id, int price);

    int bookAVehicle(String branchName, String type, int startTime, int endTime);

    String displayVehicles(String branchName, int startTime, int endTime);
}
