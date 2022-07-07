package com.example.service.impl;

import com.example.enums.BranchRentalServiceTypes;
import com.example.service.BranchRentalService;
import com.example.service.BranchRentalServiceFactory;
import com.example.service.RentalService;
import com.example.storage.VehicleRentalStorage;
import com.example.utils.Utils;

import java.util.List;
import java.util.Map;

public class RentalServiceImpl implements RentalService {

    private VehicleRentalStorage storage = new VehicleRentalStorage();

    public Boolean onBoardBranch(String branchName, List<String> types){
        if(Utils.notEmpty(branchName) && Utils.nonNull(types)){
            Map<String, BranchRentalService> branchesMap = storage.branchMap;
            if(!branchesMap.containsKey(branchName)){
                branchesMap.put(branchName, BranchRentalServiceFactory.build(BranchRentalServiceTypes.DEFAULT, types));
                Utils.logInfo("Branch created",branchName);
                return true;
            }
            else{
                Utils.logError("Could not create branch ",branchName, "same branch already exists");
            }
        }
        else {
            Utils.logError("Could not create branch ", branchName, "Invalid input");
        }
        return false;
    }

    public Boolean onBoardBranchForType(String branchName, BranchRentalServiceTypes type, List<String> types){
        if(Utils.notEmpty(branchName) && Utils.nonNull(types)){
            Map<String, BranchRentalService> branchesMap = storage.branchMap;
            if(!branchesMap.containsKey(branchName)){
                branchesMap.put(branchName, BranchRentalServiceFactory.build(type, types));
                Utils.logInfo("Branch created",branchName);
                return true;
            }
            else{
                Utils.logError("Could not create branch ",branchName, "same branch already exists");
            }
        }
        else {
            Utils.logError("Could not create branch ", branchName, "Invalid input");
        }
        return false;
    }

    public Boolean onBoardVehicle(String branchName, String type, String id, int price){
        if(Utils.notEmpty(branchName) && Utils.notEmpty(type) && Utils.notEmpty(id) && storage.uniqueVehicleIds.add(id)){
            Map<String, BranchRentalService> branchesMap = storage.branchMap;
            if(branchesMap.containsKey(branchName)){
                BranchRentalService branchRentalService = branchesMap.get(branchName);
                Utils.logInfo("Vehicle adding",id);
                return branchRentalService.addVehicle(type, id, price);
            }
            else{
                Utils.logError("Could not add vehicle ",id, "Branch does not exist");
            }
        }
        else {
            Utils.logError("Could not add vehicle to branch " + branchName + "\r", type, "Invalid input");
        }
        return false;
    }

    public int bookAVehicle(String branchName, String type, int startTime, int endTime){
        if(Utils.notEmpty(branchName) && Utils.nonNull(type)) {
            Map<String, BranchRentalService> branchesMap = storage.branchMap;
            if(branchesMap.containsKey(branchName)){
                BranchRentalService branchRentalService = branchesMap.get(branchName);
                return branchRentalService.bookAVehicle(type, startTime, endTime);
            }
            else{
                Utils.logError("Could not book vehicle ",type, "Branch does not exist");
            }
        }
        Utils.logError("Could not book for"+branchName,type, "Invalid input");
        return -1;
    }

    public String displayVehicles(String branchName, int startTime, int endTime){
        if(Utils.notEmpty(branchName)) {
            Map<String, BranchRentalService> branchesMap = storage.branchMap;
            BranchRentalService branchRentalService = branchesMap.get(branchName);
            return branchRentalService.getAllAvailableVehicles(startTime, endTime);
        }
        return null;
    }
}
