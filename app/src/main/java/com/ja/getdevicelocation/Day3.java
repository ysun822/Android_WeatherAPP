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

public class Day3 extends AppCompatActivity {
    private TextView real_weather, real_pressure, location,real_temperature,real_humid,real_groundLevel,real_seaLevel;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day3);
        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        String pressure=intent.getStringExtra("pressure");
        String mainWeather=intent.getStringExtra("mainWeather");
        String humid=intent.getStringExtra("humid");
        String temperature=intent.getStringExtra("temp");

        String groundLevel=intent.getStringExtra("groundLevel");
        String seaLevel=intent.getStringExtra("seaLevel");

        ImageView view=findViewById(R.id.WeatherImage3);

        if(mainWeather.toLowerCase().contains("rain")) {
            Picasso.with(this).load(R.drawable.rainy).resize(300,300).into(view);
        }else if (mainWeather.toLowerCase().contains("snow")) {
            Picasso.with(this).load(R.drawable.snow).resize(300,300).into(view);
        }else if (mainWeather.toLowerCase().contains("cloud")) {
            Picasso.with(this).load(R.drawable.cloudy).resize(300,300).into(view);
        }else if (mainWeather.toLowerCase().contains("clear") || mainWeather.toLowerCase().contains("sun")) {
            Picasso.with(this).load(R.drawable.sunny).resize(300,300).into(view);
        } else if (mainWeather.toLowerCase().contains("fog") || mainWeather.toLowerCase().contains("haze")) {
            Picasso.with(this).load(R.drawable.fog).resize(300,300).into(view);
        }else{
            Picasso.with(this).load(R.drawable.sunny).resize(300,300).into(view);
        }
        location=findViewById(R.id.location3);
        real_weather=findViewById(R.id.weather3);
        real_pressure=findViewById(R.id.pressure3);
        real_temperature=findViewById(R.id.temperature3);
        real_groundLevel=findViewById(R.id.groundLevel3);
        real_seaLevel=findViewById(R.id.seaLevel3);
        real_humid=findViewById(R.id.humid3);

        location.setText(city+ " Day3");
        real_weather.setText(mainWeather);
        real_pressure.setText(pressure);
        real_temperature.setText(temperature);
        real_humid.setText(humid);
        real_seaLevel.setText(seaLevel);
        real_groundLevel.setText(groundLevel);
        url=intent.getStringExtra("forecastURL");


        Date now=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.add(calendar.DATE,2);
        Date tom2=calendar.getTime();
        String todayString=new SimpleDateFormat("yyyy-MM-dd").format(tom2);
        TextView third=findViewById(R.id.Day3);
        third.setText(todayString);

        location.setOnTouchListener(new OnSwipeTouchListener(Day3.this) {
            public void onSwipeTop() {
                Toast.makeText(Day3.this, "top", Toast.LENGTH_SHORT).show();
                //Toast.makeText(WeatherDisplay.this,  "Sorry, couldn't get your location!", Toast.LENGTH_LONG).show();

            }
            public void onSwipeRight() {
                ForecastAPI forecastAPI=new ForecastAPI();

                Toast.makeText(Day3.this, "right", Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(WeatherDisplay.this, Day2.class);
                ForecastTask forecastTask=new ForecastTask(Day3.this, Day4.class);
                forecastTask.execute(url,"2"); //checked
//                ForecastAPI forecastAPI=new ForecastAPI();


            }
            public void onSwipeLeft() {
                Toast.makeText(Day3.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(Day3.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }


}
