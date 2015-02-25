package com.example.tarastsinyk.movieexplorer001;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import static android.support.v7.widget.SearchView.OnQueryTextListener;


public class MainActivity extends ActionBarActivity
implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnQueryTextListener {




    SearchView searchView;
    RawListViewFragment searchFragment;
    RawListViewFragment deFragment;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
        getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
       mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        deFragment =  new RawListViewFragment();


        switch (position) {
            case 0://popular

                Var.url = getString(R.string.pupular_url);

                break;
            case 1://top_rated

                Var.url = getString(R.string.top_rated_url);

                break;
            case 2://upcoming
                Var.url = getString(R.string.upcoming_url);

                break;
            case 3:

                break;
            case 4:
                showAboute();
                break;

        }

        onSectionAttached(position + 1);//change mTitle navigation menu
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, deFragment)
                .commit();
        restoreActionBar();


    }
        public void showAboute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Aboute").setIcon(R.drawable.about_icon).setMessage(getString(R.string.aboute)).
                setCancelable(true).setNegativeButton("Close", null).create().show();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

       MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Film to search");
        searchView.setOnQueryTextListener(this);

        searchFragment=new RawListViewFragment();
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        Var.url = getString(R.string.search_url) + "&query=" + s;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .remove(deFragment)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.container,deFragment).addToBackStack(null)
                .commit();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
