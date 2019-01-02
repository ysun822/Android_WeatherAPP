package com.ja.getdevicelocation;

import org.json.JSONException;
import org.junit.Test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.*;

import com.ja.getdevicelocation.CityAPI;
import com.ja.getdevicelocation.ForecastAPI;
import com.ja.getdevicelocation.ForecastCityAPI;
import com.ja.getdevicelocation.WeatherAPI;
/**
 * Example local unit test, which will execute on the development machine (host).
 *@author yilinsun
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {




    CityAPI city;
    ForecastAPI forecastWeather;
    ForecastCityAPI forecastCity;
    WeatherAPI weather;

    // @BeforeEach
//    public void setUp() {
//        city=new CityAPI();
//        forecastWeather=new ForecastAPI();
//        forecastCity=new ForecastCityAPI();
//        weather=new WeatherAPI();
//    }

    //Test Purpose: tests calling cityAPI
    @Test
    public void cityURLTest() {
        city=new CityAPI();
        String a=city.createURL("Chicago");

        assertEquals(true, a.contains("Chicago"));
    }
    //Test Purpose:tests the response of the cityAPI
    @Test
    public void cityURLResonseTest() throws IOException {
        city=new CityAPI();
        String a=city.createURL("Chicago");
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=city.makeAPICall(url);

        assertEquals(true, b.contains("temp"));
    }
    //Test Purpose:tests the parse of the cityAPI
    @Test
    public void cityURLParseTest() throws IOException {
        city=new CityAPI();
        String a=city.createURL("Chicago");
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=city.makeAPICall(url);
        String c=city.getCityWeather(b).getCity();

        assertEquals("Chicago", c);
    }
    //Test if the max temp is larger or equal to the min temp.
    @Test
    public void cityURLParseTest2() throws IOException {
        city=new CityAPI();
        String a=city.createURL("Chicago");
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=city.makeAPICall(url);
        boolean c=false;
        if(city.getCityWeather(b).getTemMax()-city.getCityWeather(b).getTemMin()>=0){
            c=true;
        }


        assertEquals(true, c);
    }
    //Test Purpose: tests calling forecastAPI
    @Test
    public void forecastURLTest() {
        forecastWeather=new ForecastAPI();
        String a=forecastWeather.createURL(70.0, 90.0);

        assertEquals(true, a.contains("70.0"));
    }

    //Test Purpose:tests the response of the forecastAPI
    @Test
    public void forecastURLResonseTest() throws IOException {
        forecastWeather=new ForecastAPI();
        String a=forecastWeather.createURL(60.0,80.0);
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=forecastWeather.makeAPICall(url);

        assertEquals(true, b.contains("temp"));
    }
    //Test Purpose:tests if the certain value TEMP_COVERT is right
    @Test
    public void forecastURLParseTestTemp() throws IOException{
        forecastWeather=new ForecastAPI();
        String a=forecastWeather.createURL(39.13,117.2);
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=forecastWeather.makeAPICall(url);
        String c=Double.toString(forecastWeather.TEMP_CONVERT);

        assertEquals("273.15", c);
    }
    //Test Purpose: tests calling forecastCityAPI
    @Test
    public void forecastCityURLTest() {
        forecastCity=new ForecastCityAPI();
        String a=forecastCity.createURL("London");

        assertEquals(true, a.contains("London"));
    }
    //Test Purpose:tests the response of the forecastCityAPI
    @Test
    public void forecastCityURLResonseTest() throws IOException {
        forecastCity=new ForecastCityAPI();
        String a=forecastCity.createURL("Beijing");
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=forecastCity.makeAPICall(url);

        assertEquals(true, b.contains("humid"));
    }
    //Test Purpose:tests the parse of the forecastCityAPI
    @Test
    public void forecastCityURLParseTest() throws IOException,JSONException {
        forecastCity=new ForecastCityAPI();
        String a=forecastCity.createURL("Chicago");
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=forecastCity.makeAPICall(url);
        String c=forecastCity.parseWeatherJSON(b).get(1).getCity();

        assertEquals("Chicago",c);
    }
    //Test Purpose:tests the parse of the forecastCityAPI
    @Test
    public void forecastCityURLParseTest2() throws IOException,JSONException {
        forecastCity=new ForecastCityAPI();
        String a=forecastCity.createURL("Chicago");
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=forecastCity.makeAPICall(url);

        double d=forecastCity.parseWeatherJSON(b).get(1).getTemMin();
        boolean e=false;
        if (d >= -100&&d<=100) {

            e=true;
        }
        assertEquals(true,e);
    }
    //Test Purpose: tests calling weatherAPI
    @Test
    public void weatherURLTest() {
        weather=new WeatherAPI();
        String a=weather.createURL(80.0,60.0);

        assertEquals(true, a.contains("80.0"));
    }
    //Test Purpose:tests the response of the weatherAPI
    @Test
    public void weatherURLResonseTest() throws IOException {
        weather=new WeatherAPI();
        String a=weather.createURL(10.0,20.0);
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=weather.makeAPICall(url);

        assertEquals(true, b.contains("temp"));
    }
    //Test Purpose:tests the parse of the cityAPI
    @Test
    public void weatherURLParseTest() throws IOException {
        weather=new WeatherAPI();
        String a=weather.createURL(39.13,117.2);
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=weather.makeAPICall(url);
        String c=weather.getWgeo(b).getCity();

        assertEquals("Tianjin Shi", c);
    }
    //Test if the max temp is larger or equal to the min temp.
    @Test
    public void weatherURLParseTest2() throws IOException {
        weather=new WeatherAPI();
        String a=weather.createURL(39.13,117.2);
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String b=weather.makeAPICall(url);
        boolean c=false;
        if(weather.getWgeo(b).getTemMax()-weather.getWgeo(b).getTemMin()>=0){
            c=true;
        }


        assertEquals(true, c);

    }
}