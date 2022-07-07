package com.example.enums;


public enum CommandTypes {

    ADD_BRANCH,//default branch rental service implemenation
    ADD_BRANCH_TYPE,//decidable branch rental service implemenation
    ADD_VEHICLE,
    BOOK,
    DISPLAY_VEHICLES;

    public static CommandTypes getByType(String command){
        CommandTypes[] types = CommandTypes.values();
        for(CommandTypes type : types){
            if(command.equals(type.name()))
                return type;
        }
        return null;
    }
}
