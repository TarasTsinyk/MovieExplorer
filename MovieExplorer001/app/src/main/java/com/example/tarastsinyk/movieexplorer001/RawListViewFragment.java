package com.example.tarastsinyk.movieexplorer001;

/**
 * Created by TarasTsinyk on 1/23/2015.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RawListViewFragment extends ListFragment {


    public static RawListViewAdapter adapter;

    // contacts JSONArray
    JSONArray results = null;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Calling async task to get json

        Var.resultList = new ArrayList<HashMap<String, String>>();
        new GetResult().execute();
    }

    private class GetResult extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(Var.url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    results = jsonObj.getJSONArray(Var.TAG_RESULTS);

                    Var.RESULTS_LENGHT = results.length();

                    // looping through All Contacts
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String id = c.getString(Var.TAG_ID);
                        String name = c.getString(Var.TAG_ORIGINAL_TITLE);
                        String release_date = c.getString(Var.TAG_RELEASE_DATE);
                        String image_url = c.getString(Var.TAG_POSTER_PATH);
                        String vote_average = c.getString(Var.TAG_VOTE_AVERAGE);
                        // tmp hashmap for single result
                        HashMap<String, String> result = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        result.put(Var.TAG_ID, id);
                        result.put(Var.TAG_ORIGINAL_TITLE, name);
                        result.put(Var.TAG_RELEASE_DATE, release_date);
                        result.put(Var.TAG_POSTER_PATH, image_url);
                        result.put(Var.TAG_VOTE_AVERAGE, vote_average);


                        // adding result to result list
                        Var.resultList.add(result);

                    }

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
            adapter = new RawListViewAdapter(getActivity(), Var.RESULTS_LENGHT);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent( getActivity(), DetailInfoActivity.class);
                    intent.putExtra("ID", Var.resultList.get(position).get(Var.TAG_ID));
                    intent.putExtra("URL id", "http://image.tmdb.org/t/p/w780/" + Var.resultList.get(position).get(Var.TAG_POSTER_PATH));


                    intent.putExtra("Original title", Var.resultList.get(position).get(Var.TAG_ORIGINAL_TITLE));
                    startActivity(intent);
                }
            });
        }
    }
}






