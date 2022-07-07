package com.example.service;

import com.example.enums.BranchRentalServiceTypes;
import com.example.service.impl.DefaultBranchRentalServiceImpl;

import java.util.List;

public class BranchRentalServiceFactory {

    public static BranchRentalService build(BranchRentalServiceTypes type, List<String> types){
        if(type!=null) {
            switch (type) {
                case DEFAULT:
                    return DefaultBranchRentalServiceImpl.build(types);
                default:
                    return null;
            }
        }
        return null;
    }
}
