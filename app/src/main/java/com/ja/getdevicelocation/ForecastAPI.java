package com.ja.getdevicelocation;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class demos how to make an API call, parse the JSON response and uses the response
 * values to create an ArrayList of RecipePuppyRecipe objects.
 * @author Scarlett Yu
 *
 */
public class ForecastAPI {
	final double TEMP_CONVERT = 273.15;
	/**
	 * Parse the JSON response String
	 * @param jsonResponse
	 * @return ArrayList of RecipePuppyRecipe objects
	 * @throws JSONException
	 */
	public List<DailyWeather> parseWeatherJSON(String jsonResponse) throws JSONException {
		//create a JSON object with the String response
		JSONObject jObj = new JSONObject(jsonResponse);
		JSONArray jArray1 = jObj.getJSONArray("list");
		String city=jObj.getJSONObject("city").getString("name");
		ArrayList<DailyWeather> dWeatherArray = new ArrayList<DailyWeather>();

		for(int i=0;i<jArray1.length();i++) {

			JSONObject obj=jArray1.getJSONObject(i);
			JSONArray weather=obj.getJSONArray("weather");
			String descript=weather.getJSONObject(0).getString("main");


			JSONObject obMain = obj.getJSONObject("main");
			double tempMin = obMain.getDouble("temp_min")- TEMP_CONVERT;
			tempMin=(double)(Math.round(tempMin*100)/100.0);


			double seaLevel = obMain.getDouble("sea_level")- TEMP_CONVERT;
			seaLevel=(double)(Math.round(seaLevel*100)/100.0);

			double groundLevel = obMain.getDouble("grnd_level")- TEMP_CONVERT;
			groundLevel=(double)(Math.round(groundLevel*100)/100.0);

			double pressure = obMain.getDouble("pressure");


			double humidity = obMain.getDouble("humidity");


			String date = obj.getString("dt_txt");

//			String date="2017-01-30 18:00:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();


			try {
				cal.setTime(sdf.parse(date));               // parse input

			} catch (ParseException e) {
				e.printStackTrace();
			}

			DailyWeather dWeather = new DailyWeather(city,tempMin,seaLevel,groundLevel,descript,pressure,humidity, cal);
			dWeatherArray.add(dWeather);
		}

		List<DailyWeather> res=arrayProcess(dWeatherArray);
		Log.i("MainForecast",""+res.get(3).getPressure()+res.get(3).getHumid());
		return res;
	}

	private List<DailyWeather> arrayProcess(List<DailyWeather> dailyWeathers){
		int today=dailyWeathers.get(0).getCal().get(Calendar.DAY_OF_MONTH);
		List<DailyWeather> res=new ArrayList<>();
		for(DailyWeather day:dailyWeathers){
			if(day.getCal().get(Calendar.DAY_OF_MONTH)>today && day.getCal().get(Calendar.HOUR_OF_DAY)==0){
				res.add(day);
			}
		}
		return res;
	}


	/**
	 * Makes the API call and returns the JSON result as a String
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String makeAPICall(URL url) throws IOException {

		URLConnection yc;
		BufferedReader in;

		yc = url.openConnection();
		in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		//Why StringBuffer? - StringBuffer is mutable so we can append to it
		StringBuffer response = new StringBuffer();
		//BufferedReader does not have a "hasNext" type method so this is how to check for
		//more input
		//if it has more input append to the StringBuffer
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	public String createURL(Double latNum, Double lonNum){
		String endPoint="https://api.openweathermap.org";
		String url2="/data/2.5/forecast?lat=";
		String lon="&lon=";
		String key=enter your API key;
		String weatherUrl =endPoint+url2+latNum+lon+lonNum+key;
		return  weatherUrl;
	}
	public List<DailyWeather> getGeoForecast(String response) {

		ForecastAPI forecastAPI=new ForecastAPI();
		List<DailyWeather> forecast=new ArrayList<>();
		try {
			forecast= forecastAPI.parseWeatherJSON(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return forecast;
	}

}


