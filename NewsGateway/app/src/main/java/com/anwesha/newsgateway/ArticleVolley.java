package com.anwesha.newsgateway;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class ArticleVolley {
    private static final String DATE_FORMAT = "MMM dd, yyyy HH:mm";
    private static final String DATE_FORMAT_PARSE = "yyyy-MM-dd'T'HH:mm:ss";

    private static final SimpleDateFormat sdfFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat sdfParse = new SimpleDateFormat(DATE_FORMAT_PARSE);

//    static final ZoneId userTimeZone = ZoneId.of("CST");
//    @SuppressLint("ConstantLocale")
//    private static final DateTimeFormatter localizedFormatter = DateTimeFormatter
//            .ofLocalizedDateTime(FormatStyle.MEDIUM)
//            .withLocale(Locale.getDefault(Locale.Category.FORMAT));
    private static final String dataURL = "https://newsapi.org/v2/top-headlines";
    private static final String yourAPIKey = "";
    private static MainActivity mainActivity;
    private static ArrayList<Article> articleArrayList = new ArrayList<>();
    private static String source;
    private static RequestQueue queue;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Article> performDownload(MainActivity ma, String source1) {
        queue = Volley.newRequestQueue(ma);
        mainActivity = ma;
        source=source1;
        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("sources", source1);
        buildURL.appendQueryParameter("apiKey", yourAPIKey);
        String urlToUse = buildURL.build().toString();
        Response.Listener<JSONObject> listener =
                response -> {

                    parseJSON(response.toString());
                    mainActivity.updateDataA(articleArrayList);


                };

        Response.ErrorListener error =
                error1 -> {
                    Toast.makeText(mainActivity, "No network connection", Toast.LENGTH_SHORT).show();

                };

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "News-App");
                        return headers;
                    }
                };


        queue.add(jsonObjectRequest);
return articleArrayList;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void parseJSON(String s) {

        try {
            articleArrayList.clear();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jObjectJSONArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jObjectJSONArray.length(); i++) {
                Article articleJson = new Article();
                articleJson.setAuthor(jObjectJSONArray.getJSONObject(i).getString("author").replace("null", ""));
                articleJson.setTitle(jObjectJSONArray.getJSONObject(i).getString("title").replace("null", ""));
                articleJson.setUrl(jObjectJSONArray.getJSONObject(i).getString("url"));
                articleJson.setUrltoimage(jObjectJSONArray.getJSONObject(i).getString("urlToImage"));
            //    String newDate = Instant.parse(jObjectJSONArray.getJSONObject(i).getString("publishedAt")).atZone(userTimeZone).format(localizedFormatter);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date parsedDate = sdfParse.parse(jObjectJSONArray.getJSONObject(i).getString("publishedAt"));
                articleJson.setPublishedat(sdfFormat.format(parsedDate));
                articleJson.setDescription(jObjectJSONArray.getJSONObject(i).getString("description"));
               articleArrayList.add(articleJson);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }





}
