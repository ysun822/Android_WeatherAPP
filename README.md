## Android Weather App Project -- WeatherSplash
* This project is an android weather app application for CIT 591 Intro to Software Engineering, University of Pennsylvannia.
Authors: Jiaying Guo, Yilin Sun, Xueyuan Yu

Use 4 OpenWeatherMap APIs to get current and weather forecast information of different locations and cities. Run multiple Expresso and Junit tests on the application and use PIXELXL and NEXUS emulators for running and testing the app.

### Sample Links For API calls:
You need to generate your own API key and add it into 4 API.java files before making the calls.
1. https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22 (current by city namesgeo-coordinates)
2. https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22 (current by city names)
3. https://samples.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22  (5-day 3-hour forecast by geo-coordinates)
4. https://samples.openweathermap.org/data/2.5/forecast?q=London,usl&appid=b6907d289e10d714a6e88b30761fae22 (5-day 3-hour forecast by city names)

### Major Features:
1. GPS Positioning 

Ask for user's permission to access their GPS information and apply LocationServices class to get user's current location. When user press the
Get Current Location button and jump to the view with current day's weather info of user's location.

<div align="center">
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/main.jpg"/>
</div>



2. City Search

User enter a city name in the main page, and press Search button. Then they will enter the page with current day weather info and 4-day forecasts of that city.

<div align="center">
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/search%20weather%20by%20city.jpg"/>
</div>


3. Current Weather Info

Use the OpenWeatherMap API to get instant weather information of specified locations and cities. Display in search views and current location's views

<div align="center">
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/currentWeather.jpg"/>
</div>

4. Future 4 days forecast info

 Define a swipe gesture listener and generic API call class. User can swipe right to check out forecast on future days after entering the page with current location's weather. However, all infos are displayed on the same page when user enter the search view.

<div align="center">
  
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/getSecondDayWeather.jpg"/>
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/getThirdDayWeather.jpg"/>
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/getFourthDayWeather.jpg"/>
  <img width="150" height="300" src="https://github.com/591Project2018/Working_Swipe/blob/master/APP_Screenshot/getFifthDayWeather.jpg"/>

</div>


