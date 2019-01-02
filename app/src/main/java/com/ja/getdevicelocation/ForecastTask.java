package com.ja.getdevicelocation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;




/**
 * author: Jiaying Guo
 * This class is for making cityName forecast API calls concurrently and starting activities generically, mainly for swipe to
 * view weather forecast info on a daily basis.
 */
public class ForecastTask extends AsyncTask<String, Void, String[]> {
    Context mainContext;
    Class activityClass;

    /**
     * Constructor takes in the Context and Activity Class to initialize corresponding instance variables for starting an intent
     * @param mainContext
     * @param activityClass
     */
    public ForecastTask(Context mainContext, Class activityClass){
            this.mainContext=mainContext;
            this.activityClass=activityClass;

    }

        /**
         * Take in a stream of string arguments, the 1st argv is the cityName forecast URL,
         * 2nd one is the ith day integer
         * @param urls
         * @return a reponse string array
         */
        @Override
        protected String[] doInBackground(String... urls) {
            ForecastAPI forecastAPI = new ForecastAPI();
            String[] response=new String[3];
            URL forecastURL;

            try {
                forecastURL = new URL(urls[0]);
                response[0]=forecastAPI.makeAPICall(forecastURL);
                response[1]=urls[1];
                response[2]=urls[0];

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        /**
         * Take in a string array of response, the 1st element is the forecast json response, 2nd one is the ith day integer, 3rd one is
         * the cityName forecast URL
         * send the information to the activityClass view
         * @param response
         */
        @Override
        protected void onPostExecute(String[] response) {
            if(response == null || response[0] == null || response[1] == null || response[0].length()==0 || response[1].length()==0){
                Toast.makeText(mainContext, "Sorry, cannot get your location's forecast!", Toast.LENGTH_LONG).show();
            }

            // Otherwise send the information to the searchInfo view and start a searchInfo activity
            else {
                int i = Integer.parseInt(response[1]);
                ForecastAPI forecastAPI = new ForecastAPI();

                // Get a list of 4 day forecast Daily Weather objects
                List<DailyWeather> res = forecastAPI.getGeoForecast(response[0]);
                Intent intent = new Intent(mainContext, activityClass);
                intent.putExtra("city", res.get(i).getCity());
                intent.putExtra("pressure", "pressure \n" + res.get(i).getPressure());
                intent.putExtra("humid", "humid \n" + res.get(i).getHumid());
                intent.putExtra("mainWeather", res.get(i).getDiscription());

                // Calculate the average temperature with max Temp and min Temp
                double temp = res.get(i).getTemMin();
                temp = (double) (Math.round(temp * 100) / 100.0);
                intent.putExtra("temp", String.valueOf(temp) + " °C");
                double seaLevel=res.get(i).getSeaLevel();
                seaLevel=(double)(Math.round(seaLevel*100)/100.0);
                double groundLevel=res.get(i).getGroundLevel();
                groundLevel=(double)(Math.round(groundLevel*100)/100.0);

                intent.putExtra("seaLevel","sealevel:\n"+String.valueOf(seaLevel));
                intent.putExtra("groundLevel","grndlevel:\n"+String.valueOf(groundLevel));

                // Send the forecast URL to next view
                intent.putExtra("forecastURL", response[2]);
                mainContext.startActivity(intent);


            }

        }
    }

