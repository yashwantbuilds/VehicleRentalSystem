package com.example.enums;

public enum BranchRentalServiceTypes {

    DEFAULT(0);

    private int code;

    BranchRentalServiceTypes(int code){
        this.code = code;
    }

    public static BranchRentalServiceTypes getByType(String type){
        BranchRentalServiceTypes[] types = BranchRentalServiceTypes.values();
        for(BranchRentalServiceTypes t : types){
            if(t.name().equals(type)){
                return t;
            }
        }
        return null;
    }
}
