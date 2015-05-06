package com.herokuapp.ezhao.calvinandhobbes;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.apache.http.Header;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GoComicsClient {
    private AsyncHttpClient client;

    public GoComicsClient() {
        client = new AsyncHttpClient();
    }

    public void getComicUrl(GregorianCalendar calendar, final MainActivity mainActivity) {
        String sourceUrl = String.format("http://www.gocomics.com/calvinandhobbes/%02d/%02d/%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DAY_OF_MONTH));

        client.get(sourceUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                int classStart = responseString.lastIndexOf("class=\"strip\"");
                int srcStart = responseString.indexOf("src=", classStart);
                int urlStart = responseString.indexOf("\"", srcStart);
                int urlEnd = responseString.indexOf("\"", urlStart+1);
                String urlString = responseString.substring(urlStart+1, urlEnd);
                mainActivity.applyImage(urlString);
            }
        });
    }
}
