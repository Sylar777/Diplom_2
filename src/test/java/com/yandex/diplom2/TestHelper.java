package com.yandex.diplom2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public class TestHelper {

    public static String bearerToken(String str){
        String[] list = str.split(",");
        for(String a : list){
            if(a.contains("Bearer")) {
                return a.substring(22, a.length()-1);
            }
        }
        return "";
    }

    public static ArrayList<String> ingredients(String str){
        JSONObject obj = new JSONObject(str);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> listOfIngredientsID = new ArrayList<String>();

        JSONArray jsonArray = new JSONArray(obj.get("data").toString());
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i=0;i<len;i++){
                list.add(jsonArray.get(i).toString());
                JSONObject listElement = new JSONObject(list.get(i));

                listOfIngredientsID.add(listElement.get("_id").toString());
            }
        }
        return listOfIngredientsID;
    }

    public static String bearerOfRegicteredOrLoginedUser(Response responseOfUserCreation, Response responseOfUserLogin){
        if(responseOfUserLogin == null && responseOfUserCreation != null){
            return TestHelper.bearerToken(responseOfUserCreation.asString());
        } else if (responseOfUserLogin != null){
            return TestHelper.bearerToken(responseOfUserLogin.asString());
        } else return null;
    }
}
