package com.herokuapp.ezhao.calvinandhobbes;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {
    @InjectView(R.id.tvDate) TextView tvDate;
    @InjectView(R.id.ivComic) ImageView ivComic;
    Random random;
    Locale locale;
    GoComicsClient goComicsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        random = new Random();
        goComicsClient = new GoComicsClient();
        locale = Locale.getDefault();

        updateComic();
    }

    @OnClick(R.id.btnNext)
    public void updateComic() {
        // First day of comic: November 18th, 1985
        GregorianCalendar calendar = new GregorianCalendar(1985, 10, 18);

        // Get random day
        calendar.add(Calendar.DATE, random.nextInt(3696));

        // Get comic for that day
        goComicsClient.getComicUrl(calendar, this);

        // Format date string
        String day_of_week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        tvDate.setText(day_of_week + " " + month + " " + day + ", " + year);
    }

    public void applyImage(String sourceUrl) {
        Picasso.with(this).load(sourceUrl).into(ivComic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
