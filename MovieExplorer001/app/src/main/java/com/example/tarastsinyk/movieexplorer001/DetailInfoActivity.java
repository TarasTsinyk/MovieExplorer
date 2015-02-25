package com.example.tarastsinyk.movieexplorer001;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailInfoActivity extends ActionBarActivity {
    ImageView filmInfoImage;
    ImageView imageTitle;
    TextView filmName;
    TextView filmOverview;
    TextView filmTagline;
    TextView filmRating;
    RatingBar ratingBar;
    String original_title, overview, tagline, vote_average, backdrop_path;
    String url_datail;
    String ID;
    Animation animation_trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra("Original title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        filmInfoImage = (ImageView) findViewById(R.id.filmInfoImage);
        imageTitle = (ImageView) findViewById(R.id.imageTitle);
        filmName = (TextView) findViewById(R.id.originalTitle);
        filmOverview = (TextView) findViewById(R.id.overView);
        filmTagline = (TextView) findViewById(R.id.tagLine);
        filmRating = (TextView) findViewById(R.id.filmRating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.isIndicator();
        ratingBar.setStepSize((float) 0.5);
        ID = intent.getStringExtra("ID");
        url_datail = "https://api.themoviedb.org/3/movie/" + ID + "?api_key=b553192b3062439e257ea463c8d3341e";

        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("URL id"))
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .resize(150, 200)
                .rotate(0)
                .into(filmInfoImage);
        new GetInfoResult().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_info, menu);
        return super.onCreateOptionsMenu(menu);
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

    private class GetInfoResult extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url_datail, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    original_title = jsonObj.getString("original_title");
                    overview = jsonObj.getString("overview");
                    tagline = jsonObj.getString("tagline");
                    vote_average = jsonObj.getString("vote_average");
                    backdrop_path = jsonObj.getString("backdrop_path");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            animation_trans = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.trans);
            filmName.setText(original_title);
            filmOverview.setText(overview);
            filmOverview.startAnimation(animation_trans);
            filmTagline.setText(tagline);
            filmRating.setText(vote_average);
            Picasso.with(getApplicationContext())
                    .load("http://image.tmdb.org/t/p/w780/" + backdrop_path)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .resize(400, 250)
                    .rotate(0)
                    .into(imageTitle);
            ratingBar.setRating(Float.parseFloat(vote_average) / (float) 3.33);
        }

    }
}
