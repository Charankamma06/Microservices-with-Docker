package com.example.dockerapp1.Controller;

import com.example.dockerapp1.Dto.JsonInput;
import com.example.dockerapp1.Dto.StoreProductsJSON;

import java.io.*;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebApp1 {

    // This is the store-file api
    @PostMapping(value = "/store-file")
    public JSONObject storeProducts (@RequestBody StoreProductsJSON storeProductsJSON) {
        String fileName = storeProductsJSON.getFile();
        String productList = storeProductsJSON.getData();
        JSONObject result = new JSONObject();
        result.put("file", fileName);
        String errorMessage = "";

        // If file name is not provided, return error
        if(fileName == null){
            errorMessage = "Invalid JSON input.";
            result.put("error", errorMessage);
            return result;
        }

       try{
           FileWriter fileWriter = new FileWriter("/vishaka_PV_dir/" + fileName);
           fileWriter.write(productList);
           fileWriter.close();
           result.put("message", "Success.");

       } catch (Exception e){
           errorMessage = "Error while storing the file to the storage.";
            result.put("error", errorMessage);
       }

        return result;
    }

    // this is the calculate api
    @PostMapping(value = "/calculate")
    public JSONObject getJsonInput (@RequestBody JsonInput jsonInput){
        String fileName = jsonInput.getFile();
        String productName = jsonInput.getProduct();
        JSONObject result = new JSONObject();
        result.put("file", fileName);
        String errorMessage = "";

        //If file name is not provided, return error
        if(fileName == null){
            errorMessage = "Invalid JSON input.";
            result.put("error", errorMessage);
            return result;
        }

        //Read file
        String filePath = "/vishaka_PV_dir/" + fileName;
        boolean isFileExist = validateFile(filePath);

        //If file is not found, return error
        if(!isFileExist){
            errorMessage = "File not found.";
            result.put("error", errorMessage);
            return result;
        }

        boolean isFileCsv = validateCsv(filePath);
        //If file is found but not in CSV format, return error
        if(!isFileCsv){
            errorMessage = "Input file not in CSV format.";
            result.put("error", errorMessage);
            return result;
        }

        RestTemplate restTemplate = new RestTemplate();

        Integer sum = restTemplate
                .postForObject("http://dockerapp2-service:8000/sum", jsonInput, Integer.class);
        result.put("sum", sum);

        return result;
    }

    public static boolean validateFile(String fileName) {
        try{
            new BufferedReader(new FileReader(fileName));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean validateCsv(String fileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                // Check if the line has the expected number of parts
                if (columns.length != 2) {
                    System.err.println("Invalid format in line: " + line);
                    return false;
                }

                //Check the first row
                if(isFirstLine){
                    if(!columns[0].trim().equalsIgnoreCase("product")){
                        System.out.println("Invalid column header");
                        return false;
                    }
                    if(!columns[1].trim().equalsIgnoreCase("amount")){
                        System.out.println("Invalid column header");
                        return false;
                    }
                    isFirstLine = false;
                } else {
                    //Check the other rows
                    try {
                        int productAmount = Integer.parseInt(columns[1].trim());
                        if (productAmount < 0) {
                            System.err.println("Negative amount in line: " + line);
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid amount in line: " + line);
                        return false;
                    }
                }
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}