package com.ja.getdevicelocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * author: Jiaying Guo
 * MainActivity is the entry page that users see, containing a search box for users to enter the city and checkout its weather
 * and forecast information, and a button to get the weather info of user's current location (GPS locating). Two private classes are
 * for making API calls concurrently.
 */
public class MainActivity extends AppCompatActivity {

    TextView city, mainWeather, pressure, temperature, humid, tempMax, tempMin;
    ImageView weatherImage,  weatherImage2, weatherImage3, weatherImage4, weatherImage5;
    TextView mainWeather2, mainWeather3, mainWeather4, mainWeather5;


    private FusedLocationProviderClient client;
    public static String textResult = "";
    public static String textResult2="";
    public static String cityURL = "";
    public static String forecastCityURL="";

    /**
     * Override the default onCreate method, when this class's activity and view is created. User can either hit the get current location button
     * and start a weatherDisplay activity or enter a city name to get its weather information by starting a SearchInfo activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WeatherAPI weatherAPI = new WeatherAPI();
        final ForecastAPI forecastAPI = new ForecastAPI();

        // Ask for user's permission to access its GPS function
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        // Define the event of search button
        Button buttonSearch = findViewById(R.id.citySearch);
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.i("MainActivity","Search!");



                // Get user's input in the search box
                EditText editText = (EditText) findViewById(R.id.editText);
                String cityName = editText.getText().toString();

                // Make city API call and city forecast API call, start a searchInfo activity
                Intent intent=new Intent(MainActivity.this, SearchInfo.class);
                CityAPI cityAPI=new CityAPI();
                String cityURL=cityAPI.createURL(cityName);

                ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
                String forecastCityURL=forecastCityAPI.createURL(cityName);

                CityTask cityTask=new CityTask();
                cityTask.execute(cityURL, forecastCityURL);

            }
        });

        // Define the event of get-current-location button
        Button button = findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "Clicked!");

                // Confirm user's permission, if fails, pop-up the permission failed message
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission to get your location failed!", Toast.LENGTH_LONG).show();
                    return;
                }


                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {


                    // If successfully get user's current location, make geographic coordinates API calls and start a weatherDisplay activity
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            // Create the API calls URLs
                            String forecastURL = forecastAPI.createURL(location.getLatitude(), location.getLongitude());
                            String locationURL = weatherAPI.createURL(location.getLatitude(), location.getLongitude());

                            // Make API calls
                            GeoTask geoTask = new GeoTask();
                            geoTask.execute(forecastURL, locationURL,""+location.getLatitude(),""+location.getLongitude());

                        }
                        // If the location is null, pop-up the message without continuing
                        else{
                            Toast.makeText(MainActivity.this,"Sorry,couldn't get your location!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    // Ask for user's permission to access its GPS function
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    // Private class to make geographic API calls and start the weatherDisplay activity
    private class GeoTask extends AsyncTask<String, Void, String[]> {

        /**
         * Take in a stream of string arguments, the 1st argv is the forecast URL,
         * 2nd one is the weather URL, 3rd one is latitude and 4th one is longitude
         * @param urls
         * @return a reponse string array
         */
        @Override
        protected String[] doInBackground(String... urls) {
            ForecastAPI forecastAPI = new ForecastAPI();
            WeatherAPI weatherAPI = new WeatherAPI();
            URL locationURL;
            URL forecastURL;
            String[] response = new String[4];

            try {
                locationURL = new URL(urls[1]);
                forecastURL = new URL(urls[0]);
                String locationResponse = weatherAPI.makeAPICall(locationURL);
                response[0] = locationResponse;
                Log.i("Mainactivity", response[0]);
                String forecastResponse = forecastAPI.makeAPICall(forecastURL);
                response[1] = forecastResponse;
                response[2]=urls[2];
                response[3]=urls[3];

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return response;
        }

        /**
         * Take in a string array of response, the 1st element is the weather json response, 2nd one is the forecast json response,
         * 3rd one is latitude and 4th one is longitude, send the information to the weather display view
         * @param response
         */
        @Override
        protected void onPostExecute(String[] response) {
            WeatherAPI weatherAPI = new WeatherAPI();
            ForecastAPI forecastAPI = new ForecastAPI();

            // If the response is null and pop-up the message
            if(response == null || response[0] == null || response[1] == null || response[0].length()==0 || response[1].length()==0){
                Toast.makeText(MainActivity.this, "Sorry, can't get your location's weather!", Toast.LENGTH_LONG).show();
            }
            // Otherwise send the information to the weatherDisplay view and start a weatherDisplay activity
            else{
                List<DailyWeather> res = new ArrayList<>();
                res=forecastAPI.getGeoForecast(response[1]);

                Intent intent = new Intent(MainActivity.this, WeatherDisplay.class);
                intent.putExtra("mainWeather",weatherAPI.getWgeo(response[0]).getMainWeather());
                intent.putExtra("city",weatherAPI.getWgeo(response[0]).getCity());
                intent.putExtra("pressure","Pressure \n"+String.valueOf(weatherAPI.getWgeo(response[0]).getPressure()));

                // Calculate the average temperature with the min and max temperature
                double temp=weatherAPI.getWgeo(response[0]).getTemMax()/2+weatherAPI.getWgeo(response[0]).getTemMin()/2;
                temp=(double)(Math.round(temp*100)/100.0);
                intent.putExtra("temperature",String.valueOf(temp)+" °C");
                intent.putExtra("humid","Humid \n"+String.valueOf(weatherAPI.getWgeo(response[0]).getHumid()));
                intent.putExtra("tempMax","Max\n"+String.valueOf(weatherAPI.getWgeo(response[0]).getTemMax())+" °C");
                intent.putExtra("tempMin","Min\n"+String.valueOf(weatherAPI.getWgeo(response[0]).getTemMin())+" °C");
                intent.putExtra("lat",response[2]);
                intent.putExtra("lon",response[3]);

                startActivity(intent);
            }



        }
    }

    // Private class to make cityName API calls and start the weatherDisplay activity
    private class CityTask extends AsyncTask<String, Void, String[]>{

        /**
         * Take in a stream of string arguments, the 1st argv is the city URL,
         * 2nd one is the city forecast URL
         * @param urls
         * @return a reponse string array
         */
        @Override
        protected String[] doInBackground(String... urls) {
            ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
            CityAPI cityAPI=new CityAPI();
            URL cityURL;
            URL forecastCityURL;
            String[] response=new String[2];
            try{
                cityURL=new URL(urls[0]);
                forecastCityURL=new URL(urls[1]);
                String cityResponse=cityAPI.makeAPICall(cityURL);
                response[0]=cityResponse;
                String forecastCityResponse=forecastCityAPI.makeAPICall(forecastCityURL);
                response[1]=forecastCityResponse;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){

                e.printStackTrace();
            }

            return response;
        }


        /**
         * Take in a string array of response, the 1st element is the weather json response, 2nd one is the forecast json response,
         * send the information to the searchInfo view
         * @param response
         */
        @Override
        protected void onPostExecute(String[] response) {
            CityAPI cityAPI=new CityAPI();
            ForecastCityAPI forecastAPI=new ForecastCityAPI();
            // If the response is null and pop-up the message
            if(response == null || response[0] == null || response[1] == null || response[0].length()==0 || response[1].length()==0){
                Toast.makeText(MainActivity.this, "Sorry, city not existed!", Toast.LENGTH_LONG).show();
            }

            // Otherwise send the information to the searchInfo view and start a searchInfo activity
            else {

                // Get a list of 4 day forecast Daily Weather objects
                List<DailyWeather> res = forecastAPI.getCityForecast(response[1]);

                Intent intent = new Intent(MainActivity.this, SearchInfo.class);
                intent.putExtra("search_mainWeather", cityAPI.getCityWeather(response[0]).getMainWeather());
                intent.putExtra("search_city", cityAPI.getCityWeather(response[0]).getCity());
                intent.putExtra("search_pressure", "Pressure \n" + String.valueOf(cityAPI.getCityWeather(response[0]).getPressure()));
                intent.putExtra("search_temperature", String.valueOf(cityAPI.getCityWeather(response[0]).getTemMax() / 2 + cityAPI.getCityWeather(response[0]).getTemMin() / 2) + " °C");
                intent.putExtra("search_humid", "Humid \n" + String.valueOf(cityAPI.getCityWeather(response[0]).getHumid()));
                intent.putExtra("search_tempMax", "Max\n" + String.valueOf(cityAPI.getCityWeather(response[0]).getTemMax()) + " °C");
                intent.putExtra("search_tempMin", "Min\n" + String.valueOf(cityAPI.getCityWeather(response[0]).getTemMin()) + " °C");
                intent.putExtra("search_mainWeather2Image", res.get(0).getDiscription());

                // Calculate the average temperature with the min and max temperature
                double temp2 = res.get(0).getTemMin() ;
                temp2 = (double) (Math.round(temp2 * 100) / 100.0);
                intent.putExtra("search_mainWeather2", res.get(0).getDiscription() + "\n\n " + temp2 + " °C \n\n Humid: \n" + String.valueOf(res.get(0).getHumid()) + "\n\n Pressure: \n" + String.valueOf(res.get(0).getPressure()));

                intent.putExtra("search_mainWeather3Image", res.get(1).getDiscription());
                double temp3 = res.get(1).getTemMin() ;
                temp3 = (double) (Math.round(temp3 * 100) / 100.0);
                intent.putExtra("search_mainWeather3", res.get(1).getDiscription() + "\n\n " + temp3 + " °C \n\n Humid: \n" + String.valueOf(res.get(1).getHumid()) + "\n\n Pressure: \n" + String.valueOf(res.get(1).getPressure()));

                intent.putExtra("search_mainWeather4Image", res.get(2).getDiscription());
                double temp4 = res.get(2).getTemMin() ;
                temp4 = (double) (Math.round(temp4 * 100) / 100.0);
                intent.putExtra("search_mainWeather4", res.get(2).getDiscription() + "\n\n " + temp4 + " °C \n\n Humid: \n" + String.valueOf(res.get(2).getHumid()) + "\n\n Pressure: \n" + String.valueOf(res.get(2).getPressure()));

                intent.putExtra("search_mainWeather5Image", res.get(3).getDiscription());
                double temp5 = res.get(3).getTemMin() ;
                temp5 = (double) (Math.round(temp5 * 100) / 100.0);
                intent.putExtra("search_mainWeather5", res.get(3).getDiscription() + "\n\n " + temp5 + " °C \n\n Humid: \n" + String.valueOf(res.get(3).getHumid()) + "\n\n Pressure: \n" + String.valueOf(res.get(3).getPressure()));

                startActivity(intent);
            }
        }
    }


}