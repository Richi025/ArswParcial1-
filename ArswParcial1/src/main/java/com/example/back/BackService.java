package com.example.back;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;


@Service
public class BackService {
    private static final String USER_AGENT = "Mozilla/5.0";
    private final String MSFT_key = "demo";
    private final String api_url = "https://www.alphavantage.co/query?";

    public String getMercadoService(String function, String symbol, String interval){
      
        String url = api_url + "function=" + function + "&symbol=" + symbol +"&interval="+ interval + "&apikey="+ MSFT_key;
        System.err.println(url);
        String httpResponse = null;
 
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                httpResponse = response.toString();
                System.err.println(response.toString());

            } else {
                System.out.println("GET request not worked");
                return "{}";
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        
        return httpResponse;
    }
}
