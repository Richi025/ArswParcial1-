package com.example.back;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BackApplicationTests {

    private static final String ENDPOINT = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=1min&apikey=demo";

    public static void main(String[] args) {
        testSimpleRequest();
        testConcurrency();
    }

    private static void testSimpleRequest() {
        try {
          
            URL url = new URL(ENDPOINT);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode(); 
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response Body : " + response.toString());
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
        // Cada hilo ejecuta una solicitud HTTP GET para obtener datos de una película específica
    private static void testConcurrency() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    
                    URL url = new URL(ENDPOINT);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode(); 
                    System.out.println("Thread " + Thread.currentThread().getId() + " - Response Code : " + responseCode);

                    
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine); 
                    }
                    in.close();

                    System.out.println("Thread " + Thread.currentThread().getId() + " - Response Body : " + response.toString());
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
            });
        }

        executorService.shutdown(); 
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}