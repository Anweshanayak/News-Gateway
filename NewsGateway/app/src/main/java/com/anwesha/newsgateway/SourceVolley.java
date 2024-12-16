package com.anwesha.newsgateway;

import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceVolley {
    private static final String dataURL = "";
    private static final String yourAPIKey = "";
    private static MainActivity mainActivity;
    private static ArrayList<Source> sourceArrayList = new ArrayList<>();
    private static ArrayList<String> categoryArrayList = new ArrayList<>();
    private static String category;
    private static String TAG="SourceVolley";
    private static RequestQueue queue;
    public static void performDownload(MainActivity ma, String category1) {
        mainActivity = ma;
        queue = Volley.newRequestQueue(mainActivity);
        if (category1.isEmpty() || category1.equalsIgnoreCase("all")) {
            category = "";
        } else {
            category = category1;
        }
        Response.Listener<JSONObject> listener =
                response -> {

                    parseJSON(response.toString());
                    mainActivity.updateData(sourceArrayList,categoryArrayList);


                };

        Response.ErrorListener error =
                error1 -> {
                    Toast.makeText(mainActivity, "No network connection", Toast.LENGTH_SHORT).show();

                };

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, dataURL,
                        null, listener, error) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "News-App");
                        return headers;
                    }
                };


        queue.add(jsonObjectRequest);


    }
    private static void parseJSON(String s) {

        try {
            sourceArrayList.clear();
            categoryArrayList.clear();
            //add the vary color code here
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonObjectJSONArray = jsonObject.getJSONArray("sources");
            for (int i = 0; i < jsonObjectJSONArray.length(); i++) {
                Source setSource = new Source();
                setSource.setSourceid(jsonObjectJSONArray.getJSONObject(i).getString("id"));
                setSource.setSourcename(jsonObjectJSONArray.getJSONObject(i).getString("name"));
                setSource.setSourceurl(jsonObjectJSONArray.getJSONObject(i).getString("url"));
                setSource.setSourcecategory(jsonObjectJSONArray.getJSONObject(i).getString("category"));
                sourceArrayList.add(setSource);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < sourceArrayList.size(); i++) {
            if (!categoryArrayList.contains(sourceArrayList.get(i).getSourcecategory())) {
                categoryArrayList.add(sourceArrayList.get(i).getSourcecategory());
            }
        }
    }

}
