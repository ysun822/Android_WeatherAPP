package com.ja.getdevicelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * author: Jiaying Guo
 * Get the current day weather and 4-day forecast information from Main Activity and display them in this view
 */
public class SearchInfo extends AppCompatActivity {
    private TextView real_weather, real_pressure, location,real_temperature,real_humid,real_tempMax,real_tempMin, real_Weather2,real_Weather3,real_Weather4,real_Weather5,real_Weather6;

    /**
     * Override the default onCreate method, when this class's activity and view is created, it will
     * receive and display the forecast information passed from MainActivity class, including temperature, pressure and etc.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        // Get the intent and passed-in forecast information
        Intent intent = getIntent();
        String city=intent.getStringExtra("search_city");
        String pressure=intent.getStringExtra("search_pressure");
        String mainWeather=intent.getStringExtra("search_mainWeather");
        String mainWeather2=intent.getStringExtra("search_mainWeather2Image");
        String mainWeather2Info=intent.getStringExtra("search_mainWeather2");
        String mainWeather3=intent.getStringExtra("search_mainWeather3Image");
        String mainWeather3Info=intent.getStringExtra("search_mainWeather3");
        String mainWeather4=intent.getStringExtra("search_mainWeather4Image");
        String mainWeather4Info=intent.getStringExtra("search_mainWeather4");
        String mainWeather5=intent.getStringExtra("search_mainWeather5Image");
        String mainWeather5Info=intent.getStringExtra("search_mainWeather5");
        String temperature=intent.getStringExtra("search_temperature");
        String tempMax=intent.getStringExtra("search_tempMax");
        String tempMin=intent.getStringExtra("search_tempMin");
        String humid=intent.getStringExtra("search_humid");


        // Find the views to fill in
        location=findViewById(R.id.search_location);
        real_weather=findViewById(R.id.search_weather);
        real_pressure=findViewById(R.id.search_pressure);
        real_temperature=findViewById(R.id.search_temp);
        real_tempMax=findViewById(R.id.search_tempMax);
        real_tempMin=findViewById(R.id.search_tempMin);
        real_humid=findViewById(R.id.search_humid);
        real_Weather2 =findViewById(R.id.search_tomInfo);
        real_Weather3 =findViewById(R.id.search_thirdInfo);
        real_Weather4 =findViewById(R.id.search_fourthInfo);
        real_Weather5 =findViewById(R.id.search_fifthInfo);

        // Set the views with passed-in information
        location.setText(city);
        real_weather.setText(mainWeather);
        real_pressure.setText(pressure);
        real_temperature.setText(temperature);
        real_humid.setText(humid);
        real_tempMax.setText(tempMax);
        real_tempMin.setText(tempMin);
        real_Weather2.setText(mainWeather2Info);
        real_Weather3.setText(mainWeather3Info);
        real_Weather4.setText(mainWeather4Info);
        real_Weather5.setText(mainWeather5Info);



        // Set the current day's weather icon
        ImageView view=findViewById(R.id.search_WeatherImage);
        if(mainWeather.toLowerCase().contains("rain")) {
            Picasso.with(this).load(R.drawable.rainy).resize(80,80).onlyScaleDown().into(view);
        }else if (mainWeather.toLowerCase().contains("snow")) {
            Picasso.with(this).load(R.drawable.snow).resize(80,80).onlyScaleDown().into(view);
        }else if (mainWeather.toLowerCase().contains("cloud")) {
            Picasso.with(this).load(R.drawable.cloudy).resize(80,80).onlyScaleDown().into(view);
        }else if (mainWeather.toLowerCase().contains("clear") || mainWeather.toLowerCase().contains("sun")) {
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view);
        } else if (mainWeather.toLowerCase().contains("fog") || mainWeather.toLowerCase().contains("haze")) {
            Picasso.with(this).load(R.drawable.fog).resize(80,80).onlyScaleDown().into(view);
        }else{
            Picasso.with(this).load(R.drawable.sunny).resize(300,300).onlyScaleDown().into(view);
        }

        // Set the second day's weather icon
        ImageView view1=findViewById(R.id.search_tommImage);
        if(mainWeather2.toLowerCase().contains("rain")) {
            Picasso.with(this).load(R.drawable.rainy).resize(80,80).onlyScaleDown().into(view1);
        }else if (mainWeather2.toLowerCase().contains("snow")) {
            Picasso.with(this).load(R.drawable.snow).resize(80,80).onlyScaleDown().into(view1);
        }else if (mainWeather2.toLowerCase().contains("cloud")) {
            Picasso.with(this).load(R.drawable.cloudy).resize(80,80).onlyScaleDown().into(view1);
        }else if (mainWeather2.toLowerCase().contains("clear") || mainWeather2.toLowerCase().contains("sun")) {
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view1);
        } else if (mainWeather2.toLowerCase().contains("fog") || mainWeather2.toLowerCase().contains("haze")) {
            Picasso.with(this).load(R.drawable.fog).resize(80,80).onlyScaleDown().into(view1);
        }else{
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view1);
        }


        // Set the third day's weather icon
        ImageView view2=findViewById(R.id.search_thirdImage);
        if(mainWeather3.toLowerCase().contains("rain")) {
            Picasso.with(this).load(R.drawable.rainy).resize(80,80).onlyScaleDown().into(view2);
        }else if (mainWeather3.toLowerCase().contains("snow")) {
            Picasso.with(this).load(R.drawable.snow).resize(80,80).onlyScaleDown().into(view2);
        }else if (mainWeather3.toLowerCase().contains("cloud")) {
            Picasso.with(this).load(R.drawable.cloudy).resize(80,80).onlyScaleDown().into(view2);
        }else if (mainWeather3.toLowerCase().contains("clear") || mainWeather3.toLowerCase().contains("sun")) {
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view2);
        } else if (mainWeather3.toLowerCase().contains("fog") || mainWeather3.toLowerCase().contains("haze")) {
            Picasso.with(this).load(R.drawable.fog).resize(80,80).onlyScaleDown().into(view2);
        }else{
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view2);
        }

        // Set the fourth day's weather icon
        ImageView view3=findViewById(R.id.search_fourthImage);
        if(mainWeather4.toLowerCase().contains("rain")) {
            Picasso.with(this).load(R.drawable.rainy).resize(80,80).onlyScaleDown().into(view3);
        }else if (mainWeather4.toLowerCase().contains("snow")) {
            Picasso.with(this).load(R.drawable.snow).resize(80,80).onlyScaleDown().into(view3);
        }else if (mainWeather4.toLowerCase().contains("cloud")) {
            Picasso.with(this).load(R.drawable.cloudy).resize(80,80).onlyScaleDown().into(view3);
        }else if (mainWeather4.toLowerCase().contains("clear") || mainWeather4.toLowerCase().contains("sun")) {
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view3);
        } else if (mainWeather4.toLowerCase().contains("fog") || mainWeather4.toLowerCase().contains("haze")) {
            Picasso.with(this).load(R.drawable.fog).resize(80,80).onlyScaleDown().into(view3);
        }else{
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view3);
        }


        // Set the fifth day's weather icon
        ImageView view4=findViewById(R.id.search_fifthImage);
        if(mainWeather5.toLowerCase().contains("rain")) {
            Picasso.with(this).load(R.drawable.rainy).resize(80,80).onlyScaleDown().into(view4);
        }else if (mainWeather5.toLowerCase().contains("snow")) {
            Picasso.with(this).load(R.drawable.snow).resize(80,80).onlyScaleDown().into(view4);
        }else if (mainWeather5.toLowerCase().contains("cloud")) {
            Picasso.with(this).load(R.drawable.cloudy).resize(80,80).onlyScaleDown().into(view4);
        }else if (mainWeather5.toLowerCase().contains("clear") || mainWeather5.toLowerCase().contains("sun")) {
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view4);
        } else if (mainWeather5.toLowerCase().contains("fog") || mainWeather5.toLowerCase().contains("haze")) {
            Picasso.with(this).load(R.drawable.fog).resize(80,80).onlyScaleDown().into(view4);
        }else{
            Picasso.with(this).load(R.drawable.sunny).resize(80,80).onlyScaleDown().into(view4);
        }

        // Display current day's date
        Date now=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.add(calendar.DATE,1);
        Date tomorrow1=calendar.getTime();
        String nowAsString=new SimpleDateFormat("yyyy-MM-dd" ).format(now);
        TextView day=findViewById(R.id.search_Day);
        day.setText(nowAsString);
        String tomAsString=new SimpleDateFormat("MM-dd").format(tomorrow1);
        TextView tomorrow=findViewById(R.id.search_tomorrow);
        tomorrow.setText(tomAsString);

        // Display 2nd day's date
        calendar.add(calendar.DATE,1);
        Date tom2=calendar.getTime();
        String thirdAsString=new SimpleDateFormat("MM-dd").format(tom2);
        TextView third=findViewById(R.id.search_third);
        third.setText(thirdAsString);

        // Display 3rd day's date
        calendar.add(calendar.DATE,1);
        Date tom3=calendar.getTime();
        String fourthAsString=new SimpleDateFormat("MM-dd").format(tom3);
        TextView fourth=findViewById(R.id.search_fourth);
        fourth.setText(fourthAsString);

        // Display 4th day's date
        calendar.add(calendar.DATE,1);
        Date tom4=calendar.getTime();
        String fifthAsString=new SimpleDateFormat("MM-dd").format(tom4);
        TextView fifth=findViewById(R.id.search_fifth);
        fifth.setText(fifthAsString);

    }

}