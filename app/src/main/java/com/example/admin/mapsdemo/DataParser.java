package com.example.admin.mapsdemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String,String> getPlace (JSONObject jsonObject){

           HashMap<String,String> hashMap = new HashMap<>();
           String placeName = "-NA-";
           String vicinity = "-NA-";
           String latitude = "";
           String longitude = "";
           String reference = "";

           try {
               if (!jsonObject.isNull("name")){
                   placeName = jsonObject.getString("name");
               }
               if (!jsonObject.isNull("vicinity")){
                   vicinity = jsonObject.getString("vicinity");
               }
               latitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat");
               longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng");

               reference = jsonObject.getString("reference");

               hashMap.put("places_name",placeName);
               hashMap.put("places_vicinity",vicinity);
               hashMap.put("places_lat",latitude);
               hashMap.put("places_lng",longitude);
               hashMap.put("places_reference",reference);



           } catch (JSONException e) {
               e.printStackTrace();
           }
           return hashMap;
    }

    private List<HashMap<String,String>> getPlaces (JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>> placeList = new ArrayList<>();
        HashMap<String,String> listHashmap = null;

        for (int i= 0; i< count; i++){
            try {
                listHashmap = getPlace((JSONObject) jsonArray.get(i));
                placeList.add(listHashmap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeList;
    }

    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);

    }


}
