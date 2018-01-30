package com.example.gaipov.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity  {

    static final String APPID = "46f9e573e993ff6f3aa5c67cebe59033"; //класс UrlBuilder просит чтобы он был статиком
    String sity;
    String id;
    final String maxTempHours = "12:00";
    final String minTempHours = "00:00";
    ArrayList<TextView> TextArray = new ArrayList<>();


    private TextView mTempTextView;
    private TextView mDescriptionTextView;
    private EditText mGetSityEditTex;
    private TextView mMinTempTextView;
    private TextView mMaxTempTextView;
    private TextView mDateTextView;
    private TextView mRainTextView;

    private TextView m12hTempTextView;
    private TextView m15hTempTextView;
    private TextView m18hTempTextView;
    private TextView m21hTempTextView;
    private TextView m00hTempTextView;

    private TextView mTimeFirstTextView;
    private TextView mTimeTwoTextView;
    private TextView mTimeThreeTextView;
    private TextView mTimeFourTextView;
    private TextView mTimeFiveTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetSityEditTex = (EditText) findViewById(R.id.getcity_edit_text_view);
        mTempTextView = (TextView) findViewById(R.id.temperature_text_view);
        mDescriptionTextView = (TextView) findViewById(R.id.description_text_view);
        mMaxTempTextView = (TextView) findViewById(R.id.max_temp_text_view);
        mMinTempTextView = (TextView) findViewById(R.id.min_temp_text_view);
        mDateTextView = (TextView) findViewById(R.id.date_view);
        mRainTextView = (TextView) findViewById(R.id.rain_text_view);

        m12hTempTextView = (TextView) findViewById(R.id.H12_Temp_text_view);
        m15hTempTextView = (TextView) findViewById(R.id.H15_Temp_text_view);
        m18hTempTextView = (TextView) findViewById(R.id.H18_Temp_text_view);
        m21hTempTextView = (TextView) findViewById(R.id.H21_Temp_text_view);
        m00hTempTextView = (TextView) findViewById(R.id.H00_Temp_text_view);

        mTimeFirstTextView = (TextView) findViewById(R.id.time_first_text_view);
        mTimeTwoTextView = (TextView) findViewById(R.id.time_two_text_view);
        mTimeThreeTextView = (TextView) findViewById(R.id.time_three_text_view);
        mTimeFourTextView = (TextView) findViewById(R.id.time_four_text_view);
        mTimeFiveTextView = (TextView) findViewById(R.id.time_five_text_view);




        TextArray.add(m12hTempTextView);
        TextArray.add(m15hTempTextView);
        TextArray.add(m18hTempTextView);
        TextArray.add(m21hTempTextView);
        TextArray.add(m00hTempTextView);

        TextArray.add(mTimeFirstTextView);
        TextArray.add(mTimeTwoTextView);
        TextArray.add(mTimeThreeTextView);
        TextArray.add(mTimeFourTextView);
        TextArray.add(mTimeFiveTextView);

        URL url = createUrl();
        ConnectWorker worker = new ConnectWorker();
        worker.execute(url);


    }



    @Override
    protected void onResume(){
        super.onResume();
        setDate();
    }

    public static URL createUrl() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendQueryParameter("q", "Dmitrov,RU")
                .appendQueryParameter("lang", "ru")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", APPID);
        String link = builder.build().toString();


        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }




    public class ConnectWorker extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            if (urls[0] == null) {
                return "";
            }
            HttpURLConnection connection = null;
            StringBuilder builder = new StringBuilder();
            try {

                connection = (HttpURLConnection) urls[0].openConnection();
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setConnectTimeout(250);
                connection.setReadTimeout(250);

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        builder.append("\n");
                    }
                    reader.close();
                }

            } catch (Throwable cause) {
                cause.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String weather) {

            try {
                String temp;
                JSONObject WeatherJSONObject = (JSONObject) JSONValue.parseWithException(weather);
                JSONObject WeatherBuffer;
                JSONObject WeatherMain;
                JSONArray MainArray = (JSONArray) WeatherJSONObject.get("list");
                WeatherBuffer = (JSONObject) MainArray.get(0);


                //берем объект по ключю main
                WeatherMain = (JSONObject) WeatherBuffer.get("main");


                setMainScreen( WeatherMain, mTempTextView, "temp", true);
                setMainScreen( WeatherMain, mRainTextView, "humidity", false);

                JSONArray weatherArray = (JSONArray) WeatherBuffer.get("weather");
                WeatherBuffer = (JSONObject) weatherArray.get(0);
                setMainScreen(WeatherBuffer, mDescriptionTextView, "description", false);
                setView3Hours(MainArray);
                setDate();

            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public void setMainScreen(JSONObject WeatherBuffer, TextView view, String key, boolean hasRound) {
        String buf = String.valueOf(WeatherBuffer.get(key));
        if (hasRound) {
            Float round = Float.valueOf(buf);
            buf = String.valueOf(Math.round(round));
        }
        if (!key.equals("humidity")) {
            view.setText(buf);
        } else {
            view.setText("Влажность:" + buf + " %");
        }
    }

    public void setView3Hours(JSONArray mainArray){
        String buf;
        Float round;
        String temp;
        JSONObject WeatherData;
        for (int hours3 = 0; hours3 < 5; hours3++) {

             WeatherData = (JSONObject) mainArray.get(hours3);
            temp = String.valueOf(WeatherData.get("dt_txt"));
            temp = temp.substring(11,16);
            WeatherData = (JSONObject) WeatherData.get("main");

            buf = String.valueOf(WeatherData.get("temp"));
            round = Float.valueOf(buf);


            buf = String.valueOf(Math.round(round));

            if(temp.equals(maxTempHours)){
                mMaxTempTextView.setText(buf);

            }
            else if(temp.equals(minTempHours)) {
                mMinTempTextView.setText(buf);
            }
            TextArray.get(hours3).setText(buf);
            TextArray.get(hours3+5).setText(temp);

        }
    }

    public void setDate() {

        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, HH:mm");
        mDateTextView.setText(dateFormat.format(calendar.getTime()));

    }
}


