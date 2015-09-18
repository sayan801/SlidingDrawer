package technicise.com.demoslidingdrawerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DemoActivity extends Activity {

    /* Within which the entire activity is enclosed */
    private DrawerLayout mDrawerLayout;

    /* ListView represents Navigation Drawer */
    private ListView mDrawerList;

    /* ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar */
    private ActionBarDrawerToggle mDrawerToggle;

    /* Title of the action bar */
    private String mTitle = "Navigation Drawer";

    /* Getting navigation items from array */
    private String[] items;

    private int selectedPosition;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        /* Setting actionbar title */
        getActionBar().setTitle(mTitle);

        items = getResources().getStringArray(R.array.menus);

		/* Getting reference to the DrawerLayout */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        /* Creating an ArrayAdapter to add items to mDrawerList */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, items);

		/* Setting the adapter to mDrawerList */
        mDrawerList.setAdapter(adapter);

		/* Getting reference to the ActionBarDrawerToggle */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /* Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //Put your code here
            }

            /* Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                //Put your code here
            }
        };

		/* Setting DrawerToggle on DrawerLayout */
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Enabling Home button
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting item click listener for the listview mDrawerList
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;

                if(selectedPosition==0)
                {
                    updateFragment();}
                else if(selectedPosition==1){
                    updateFragment2();
                }
                else if(selectedPosition==2){
                    updateFragment3();
                }

				/* Closing the drawer */
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        /* Setting default fragment */
      selectedPosition = 2;
        updateFragment3();

    }

    public void updateFragment() {
        /* Getting reference to the FragmentManager */
        FragmentManager fragmentManager = getFragmentManager();

        /* Creating fragment instance */
        HomeFragment rFragment = new HomeFragment();

        /* Passing selected item information to fragment */
        Bundle data = new Bundle();
        data.putString("title", items[selectedPosition]);
        data.putString("url", getUrl());
        rFragment.setArguments(data);

        /* Replace fragment */
        android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();
    }

    public void updateFragment2() {
        /* Getting reference to the FragmentManager */
        FragmentManager fragmentManager = getFragmentManager();

        /* Creating fragment instance */
        ProfileFragment rFragment = new ProfileFragment();

        /* Replace fragment */
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();
    }

    public void updateFragment3() {
        /* Getting reference to the FragmentManager */
        FragmentManager fragmentManager = getFragmentManager();

        /* Creating fragment instance */
        VitalsFragment rFragment = new VitalsFragment();

        /* Replace fragment */
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();
    }

    protected String getUrl() {
        StringBuffer url = new StringBuffer("http://google.com");
        return url.toString();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}

