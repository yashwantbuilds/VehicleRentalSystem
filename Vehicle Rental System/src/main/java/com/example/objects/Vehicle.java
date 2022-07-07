package com.example.objects;

import java.util.Objects;

public class Vehicle {

    private String type;
    private String vehicleId;
    private int price;

    public static Vehicle build(String type, String vehicleId, int price){
        Vehicle vehicle = new Vehicle();
        vehicle.type = type;
        vehicle.vehicleId = vehicleId;
        vehicle.price = price;
        return vehicle;
    }

    public String getType() {
        return type;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleId.equals(vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }
}
