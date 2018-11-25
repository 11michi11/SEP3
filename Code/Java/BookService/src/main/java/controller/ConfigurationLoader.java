package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Scanner;

public class ConfigurationLoader {

    private static Map<String, String> config;
    static {
        try {
            Gson gson = new Gson();
            Scanner in = new Scanner(new File("config.txt"));
            String json = in.nextLine();
            Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();
            config = gson.fromJson(json, typeOfHashMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Configuration not loaded");
        }
    }

   public static String getDbAddress(){
        return config.getOrDefault("dbAddress", "localhost");
   }

}
