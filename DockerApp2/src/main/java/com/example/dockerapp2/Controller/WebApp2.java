package com.example.dockerapp2.Controller;


import com.example.dockerapp2.Dto.JsonInput;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;

@RestController
public class WebApp2 {
    // this is the sum api that calculates the product sum
    @PostMapping(value = "/sum")
    public Integer calculateSum (@RequestBody JsonInput jsonInput) throws Exception {
        String fileName = jsonInput.getFile();
        String product = jsonInput.getProduct();
        String filePath = "/vishaka_PV_dir/" + fileName;
        String message;
        String line;
        int sum = 0;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if(columns[0].equals(product)){
                    sum+=Integer.parseInt(columns[1].trim());
                }
            }
        } catch (Exception e){
            sum = -1;
        }
        return sum;
    }
}

