package com.example.tarastsinyk.movieexplorer001;

/**
 * Created by TarasTsinyk on 1/22/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class RawListViewAdapter extends BaseAdapter     {

    private Activity activity;

    private static LayoutInflater inflater = null;

    private int loaded_items;

    public RawListViewAdapter(Activity a, int loaded_items) {
        this.loaded_items = loaded_items;
        activity = a;


        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(inflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }

    }

    public int getCount() {
        return loaded_items;

    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_listview_item, null);

        TextView original_title = (TextView) vi.findViewById(R.id.originalTitle);
        TextView voteAverage = (TextView) vi.findViewById(R.id.voteAverage);
        TextView releaseDate = (TextView) vi.findViewById(R.id.filmDate);
        ImageView image = (ImageView) vi.findViewById(R.id.filmImage);
        String original_title_tmp, release_date_tmp, url_image_w500_tmp,vote_average_tmp;
        try {


            original_title_tmp= Var.resultList.get(position).get(Var.TAG_ORIGINAL_TITLE);
        } catch (Exception e) {

            original_title_tmp = "Can't load information !!!";
            e.printStackTrace();

        }

        try {
            release_date_tmp = Var.resultList.get(position).get(Var.TAG_RELEASE_DATE);

        } catch (Exception e) {

            release_date_tmp = "Can't load information !!!";
            e.printStackTrace();

        }
        try {
            vote_average_tmp = Var.resultList.get(position).get(Var.TAG_VOTE_AVERAGE);

        } catch (Exception e) {

            vote_average_tmp = "Can't load information !!!";
            e.printStackTrace();

        }
        try {
            url_image_w500_tmp = " http://image.tmdb.org/t/p/w500/" + Var.resultList.get(position).get(Var.TAG_POSTER_PATH);

        } catch (Exception e) {

            url_image_w500_tmp = null;
            e.printStackTrace();

        }
        


        original_title.setText(original_title_tmp);
        voteAverage.setText(vote_average_tmp);

        releaseDate.setText(release_date_tmp);


        Picasso.with(activity.getApplicationContext())
                .load(url_image_w500_tmp)
                .placeholder(R.drawable.ic_launcher)   // optional
                .error(R.drawable.ic_launcher)      // optional
                .resize(70, 100)                        // optional
                .rotate(0)                             // optional
                .into(image);

        return vi;
    }
}