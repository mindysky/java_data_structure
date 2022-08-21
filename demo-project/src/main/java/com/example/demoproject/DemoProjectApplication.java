package com.example.demoproject;

import com.example.demoproject.demo.GetDatefromJson;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoProjectApplication {

    public static void main(String[] args) throws IOException, JSONException {

        SpringApplication.run(DemoProjectApplication.class, args);

        GetDatefromJson getDatefromJson = new GetDatefromJson();
        getDatefromJson.getMap();

    }

}
