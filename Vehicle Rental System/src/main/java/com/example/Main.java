package com.example;

import com.example.service.impl.RentalServiceImpl;
import com.example.service.RentalService;
import com.example.utils.CommandProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static RentalService rentalService = new RentalServiceImpl();

    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        File file = new File(filePath);
        CommandProcessor commandProcessor = new CommandProcessor(rentalService);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String command = myReader.nextLine();
            commandProcessor.processCommand(command);
        }
        myReader.close();
    }
}
