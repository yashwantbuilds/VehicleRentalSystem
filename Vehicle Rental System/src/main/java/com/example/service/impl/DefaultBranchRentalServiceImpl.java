package com.example.service.impl;

import com.example.enums.VehicleType;
import com.example.objects.Vehicle;
import com.example.service.BranchRentalService;
import com.example.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultBranchRentalServiceImpl implements BranchRentalService {

    private List<String> allowedTypes;
    private Map<Integer, Map<String, Set<Vehicle>>> startTimeToVehicleTypeToIdsMap = new HashMap<>();
    private Map<Integer, Set<Vehicle>> priceToVehicleMap = new TreeMap<>();
    private Map<String, Set<String>> vehicleTypeToCountMap = new HashMap<>();
    private Map<String, Set<String>> bookedVehicleTypeToCountMap = new HashMap<>();

    public static DefaultBranchRentalServiceImpl build(List<String> types){
        DefaultBranchRentalServiceImpl branchRentalService = new DefaultBranchRentalServiceImpl();
        branchRentalService.allowedTypes = types;
        branchRentalService.priceToVehicleMap = new TreeMap<>();
        return branchRentalService;
    }

    public Boolean addVehicle(String type, String vehicleId, int price){
        if(Utils.nonNull(allowedTypes) && allowedTypes.contains(type)){
            Vehicle vehicle = Vehicle.build(type, vehicleId, price);
            Set<Vehicle> vehicles = priceToVehicleMap.getOrDefault(price, new LinkedHashSet<>());
            vehicles.add(vehicle);
            priceToVehicleMap.put(price, vehicles);
            Set<String> vehicleIdSet = vehicleTypeToCountMap.getOrDefault(type, new HashSet<>());
            vehicleIdSet.add(vehicleId);
            vehicleTypeToCountMap.put(type, vehicleIdSet);
            return true;
        }
        return false;
    }

    public int bookAVehicle(String type, int startTime, int endTime){
        Set<Vehicle> vehiclesAvailable = getAvailableVehicle(startTime, endTime, type);
        if(vehiclesAvailable!=null && vehiclesAvailable.size()>0){
            Vehicle vehicle = vehiclesAvailable.stream().findFirst().get();
            Set<String> vehicleIdSet = bookedVehicleTypeToCountMap.getOrDefault(type, new HashSet<>());
            vehicleIdSet.add(vehicle.getVehicleId());
            bookedVehicleTypeToCountMap.put(type, vehicleIdSet);
            for(int i = startTime;i<endTime;i++) {
                Map<String, Set<Vehicle>> vehicleMap = startTimeToVehicleTypeToIdsMap.getOrDefault(i, new HashMap<>());
                Set<Vehicle> vehicles = vehicleMap.getOrDefault(type, new LinkedHashSet<>());
                vehicles.add(vehicle);
                vehicleMap.put(type, vehicles);
                startTimeToVehicleTypeToIdsMap.put(i, vehicleMap);
            }
            increasePrice();
            int priceToPay = (endTime - startTime)*vehicle.getPrice();
            return priceToPay;
        }
        return -1;
    }

    public Set<Vehicle> getAvailableVehicle(int startTime, int endTime, String vehicleType){
        Set<Vehicle> allVehicleIds = new LinkedHashSet<>();
        for(Map.Entry<Integer, Set<Vehicle>> entry : priceToVehicleMap.entrySet()){
            allVehicleIds.addAll(entry.getValue().stream().filter(o -> o.getType().equals(vehicleType)).collect(Collectors.toSet()));
        }
        if(allVehicleIds.size()>0 && Utils.nonNull(startTimeToVehicleTypeToIdsMap) && !startTimeToVehicleTypeToIdsMap.isEmpty()) {
            //check start time of a slot
            for (int i = startTime; i < endTime; i++) {
                boolean existsForStartTimeAsStart = startTimeToVehicleTypeToIdsMap.containsKey(i);
                if (existsForStartTimeAsStart) {
                    ruleOutVehicles(i, vehicleType, allVehicleIds);
                }
            }
        }
        if(allVehicleIds!=null && allVehicleIds.size()>0){
            return allVehicleIds;
        }
        return null;
    }

    private void ruleOutVehicles(int time, String vehicleType, Set<Vehicle> allVehicleIds) {
        if (startTimeToVehicleTypeToIdsMap.get(time).get(vehicleType) != null) {
        List<Vehicle> vehicles = startTimeToVehicleTypeToIdsMap.get(time).get(vehicleType)
                .stream().collect(Collectors.toList());
        if (vehicles != null && vehicles.size() > 0)
            allVehicleIds.removeAll(vehicles);
        }
    }

    public String getAllAvailableVehicles(int startTime, int endTime){
        Set<String> vehicleIds = new HashSet<>();
        for(String type : allowedTypes) {
            Set<Vehicle> vehicles = getAvailableVehicle(startTime, endTime, type);
            if(vehicles!=null && vehicles.size()>0) {
                vehicleIds.addAll(vehicles.stream().map(o -> o.getVehicleId()).collect(Collectors.toList()));
            }
        }
        if(vehicleIds!=null && vehicleIds.size()>0){
            Set<String> sortedVIds = new LinkedHashSet<>();
            for(Map.Entry<Integer, Set<Vehicle>> entry : priceToVehicleMap.entrySet()){
                Set<String> ids = entry.getValue().stream().map(o->o.getVehicleId()).collect(Collectors.toSet());
                sortedVIds.addAll(ids);
            }
            sortedVIds.retainAll(vehicleIds);
            return String.join(",", vehicleIds);
        }
        return null;
    }

    @Override
    public void increasePrice() {
        if(Utils.nonNull(vehicleTypeToCountMap) && Utils.nonNull(bookedVehicleTypeToCountMap)){
            for(Map.Entry<String, Set<String>> entry : bookedVehicleTypeToCountMap.entrySet()){
                String type = entry.getKey();
                if(type.equals(VehicleType.CAR.name())) {
                    int bookedCount = entry.getValue().size();
                    int vehicleCount = vehicleTypeToCountMap.get(type).size();
                    if (bookedCount / vehicleCount >= 0.8) {
                        reEvaluateMap(type);
                    }
                }
            }
        }
    }

    private void reEvaluateMap(String type){
        Set<Vehicle> vehiclesForTYpe = new HashSet<>();
        for(Map.Entry<Integer, Set<Vehicle>> entry : priceToVehicleMap.entrySet()){
            Set<Vehicle> vehicleSet = entry.getValue().stream().filter(o->o.getType().equals(type)).collect(Collectors.toSet());
            vehiclesForTYpe.addAll(vehicleSet);
            entry.getValue().removeAll(vehicleSet);
        }

        //increase price by 10%
        if (vehiclesForTYpe!=null && vehiclesForTYpe.size()>0){
            for(Vehicle vehicle : vehiclesForTYpe){
                int price = vehicle.getPrice();
                int newPrice = price + price*10/100;
                vehicle.setPrice(newPrice);
                Set<Vehicle> vehicles = priceToVehicleMap.getOrDefault(newPrice, new HashSet<>());
                vehicles.add(vehicle);
                priceToVehicleMap.put(newPrice, vehicles);
            }
            List<Integer> keysToRemove = new ArrayList<>();
            for(Map.Entry<Integer, Set<Vehicle>> entry : priceToVehicleMap.entrySet()){
                if(entry.getValue().size()==0){
                    keysToRemove.add(entry.getKey());
                }
            }
            for(Integer key : keysToRemove) {
                priceToVehicleMap.remove(key);
            }
        }
    }
}
