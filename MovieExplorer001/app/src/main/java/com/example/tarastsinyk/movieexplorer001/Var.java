package com.example.tarastsinyk.movieexplorer001;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TarasTsinyk on 2/12/2015.
 */
public  abstract class Var {
    public static final String TAG_ID = "id";
    public static final String TAG_ORIGINAL_TITLE = "original_title";
    public static final String TAG_RELEASE_DATE = "release_date";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String TAG_VOTE_AVERAGE = "vote_average";
    public static final String TAG_RESULTS = "results";
    public static String url ;
    public static ArrayList<HashMap<String, String>> resultList;
    public static int RESULTS_LENGHT;
}
