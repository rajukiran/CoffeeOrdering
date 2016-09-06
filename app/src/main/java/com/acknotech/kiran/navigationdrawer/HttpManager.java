package com.acknotech.kiran.navigationdrawer;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kiran on 14/8/16.
 */
public class HttpManager {

    HttpURLConnection con;
    String headerName, headerValue, result, result1;

    public String getData(RequestPackage p, String userName, String password) {

        String uri = p.getUri();
        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());

            JSONObject json = new JSONObject();

            json.put("userName", userName);
            json.put("userPassword", password);

            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-type", "application/json");
            post.setEntity(new StringEntity(json.toString(), "UTF-8"));
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpresponse = client.execute(post);

            Header[] headers = httpresponse.getHeaders("JSESSION_ID");

            for (Header header : headers) {
                headerName = header.getName();
                headerValue = header.getValue();
            }

            HttpEntity entity = httpresponse.getEntity();
            InputStream stream = entity.getContent();
            result = convertStreamToString(stream);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertStreamToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        int i = 0;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getAllGames(RequestPackage p) {

        String uri = p.getUri();

        try {

            HttpGet get = new HttpGet(uri);
            get.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpresponse = client.execute(get);

            HttpEntity entity = httpresponse.getEntity();
            InputStream stream = entity.getContent();

            result1 = convertStreamToString(stream);

            return result1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
