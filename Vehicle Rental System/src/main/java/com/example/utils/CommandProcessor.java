package com.example.utils;

import com.example.enums.BranchRentalServiceTypes;
import com.example.enums.CommandTypes;
import com.example.service.RentalService;

import java.util.Arrays;

public class CommandProcessor {

    RentalService rentalService;

    public CommandProcessor(RentalService rentalService){
        this.rentalService = rentalService;
    }

    public Object processCommand(String command){

        String[] commandInputs = command.split(" ");
        CommandTypes commandTypes = CommandTypes.getByType(commandInputs[0]);
        Object output;
        switch (commandTypes){
            case ADD_BRANCH:{//for default strategy branch rental service
                output = performAddBranch(commandInputs);
                break;
            }
            case ADD_BRANCH_TYPE:{//for a specific strategy branch rental service
                output = performAddBranchForAType(commandInputs);
                break;
            }
            case ADD_VEHICLE:{
                output = performAddVehicle(commandInputs);
                break;
            }
            case BOOK:{
                output = performBooking(commandInputs);
                break;
            }
            case DISPLAY_VEHICLES:{
                output = performDisplayVehicles(commandInputs);
                break;
            }
            default:
                output = "Invalid Command";
        }
        if(output instanceof Boolean){
            System.out.println((Boolean) output?"TRUE":"FALSE");
        }
        else {
            System.out.println(output);
        }
        return output;
    }

    private Object performAddBranch(String[] commandInputs){
        String branchName = commandInputs[1];
        String[] vehicleTypes = commandInputs[2].split(",");
        return rentalService.onBoardBranch(branchName, Arrays.asList(vehicleTypes));
    }

    private Object performAddBranchForAType(String[] commandInputs){
        String branchName = commandInputs[1];
        String type = commandInputs[2];
        String[] vehicleTypes = commandInputs[3].split(",");
        return rentalService.onBoardBranchForType(branchName, BranchRentalServiceTypes.getByType(type), Arrays.asList(vehicleTypes));
    }

    private Object performAddVehicle(String[] commandInputs){
        String branchName = commandInputs[1];
        String vehicleType = commandInputs[2];
        String vehicleId = commandInputs[3];
        String price = commandInputs[4];
        return rentalService.onBoardVehicle(branchName, vehicleType, vehicleId, Integer.valueOf(price));
    }

    private Object performBooking(String[] commandInputs){
        String branchName = commandInputs[1];
        String vehicleType = commandInputs[2];
        String startTime = commandInputs[3];
        String endTime = commandInputs[4];
        return rentalService.bookAVehicle(branchName, vehicleType, Integer.valueOf(startTime), Integer.valueOf(endTime));
    }

    private Object performDisplayVehicles(String[] commandInputs){
        String branchName = commandInputs[1];
        String startTime = commandInputs[2];
        String endTime = commandInputs[3];
        return rentalService.displayVehicles(branchName, Integer.valueOf(startTime), Integer.valueOf(endTime));
    }
}
