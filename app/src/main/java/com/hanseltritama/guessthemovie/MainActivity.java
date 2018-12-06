package com.hanseltritama.guessthemovie;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();//open the browser window
                InputStream in = urlConnection.getInputStream();//stream to hold the input of data
                InputStreamReader reader = new InputStreamReader(in);//to read the content of the URL
                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;//grab all HTML elements on a site

                    data = reader.read();

                }

                return result;
            }
            catch(Exception e) {

                e.printStackTrace();
                return "Failed";

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        String movie_page_source = null;

        try {
            movie_page_source = downloadTask.execute("http://www.posh24.se/kandisar").get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.i("Test", movie_page_source);

        Pattern p = Pattern.compile("img\\ssrc=\"(.*?)\"");
        Matcher m = p.matcher(movie_page_source);

        while(m.find()) {

            Log.i("HAHA", m.group(1));

        }
    }
}
