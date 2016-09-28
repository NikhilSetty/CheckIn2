package com.mantra.checkin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mantra.checkin.Entities.Interfaces.OnItemClick;
import com.mantra.checkin.Entities.ViewModel.NavDrawerChildViewItem;
import com.mantra.checkin.NavigationDrawer.ChannelListItem;
import com.mantra.checkin.NavigationDrawer.NavigationDrawerRecyclerViewAdapter;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.UiFragments.Applications.ApplicationFragment;
import com.mantra.checkin.UiFragments.ChatRooms.ChatRoomFragment;
import com.mantra.checkin.UiFragments.Contacts.ContactsFragment;
import com.mantra.checkin.UiFragments.HomeFragment;
import com.mantra.checkin.UiFragments.Urls.UrlFragment;
import com.mantra.checkin.UiFragments.Venues.VenueFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClick {

    private RecyclerView mNavigationDrawerRecyclerView;
    private RecyclerView.Adapter mNavigationDrawerAdapter;
    private RecyclerView.LayoutManager mNavigationDrawerLayoutManager;

    public static String currentChannelId = "";

    private final static String TAG = "MainActivity";

    private static Fragment initialFragment;
    private static String mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Initialize Navigation Drawer ListView
        mNavigationDrawerRecyclerView = (RecyclerView) findViewById(R.id.navigation_drawer_recycler_view);
        mNavigationDrawerLayoutManager = new LinearLayoutManager(this);
        List<ChannelListItem> viewList = new ArrayList<>();
        for(int i = 0; i < SessionHelper.channelModelList.size(); i++){
            viewList.add(new ChannelListItem(SessionHelper.channelModelList.get(i)));
        }
        mNavigationDrawerAdapter = new NavigationDrawerRecyclerViewAdapter(this, viewList, this);
        mNavigationDrawerRecyclerView.setAdapter(mNavigationDrawerAdapter);
        mNavigationDrawerRecyclerView.setLayoutManager(mNavigationDrawerLayoutManager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Fragments for all the profiles
        initialFragment = new HomeFragment();
        replaceFragment();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the acti    on bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClickItem(View caller, NavDrawerChildViewItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        SessionHelper.fragmentStack.push(new HomeFragment());
        currentChannelId = item.ChannelId;
        switch(item.Name) {
            case "Urls":
                initialFragment = new UrlFragment();
                break;
            case "Applications":
                initialFragment = new ApplicationFragment();
                break;
            case "Contacts":
                initialFragment = new ContactsFragment();
                break;
            case "Venues":
                initialFragment = new VenueFragment();
                break;
            case "ChatRooms":
                initialFragment = new ChatRoomFragment();
                break;
            default:
                initialFragment = new HomeFragment();
                break;
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        replaceFragment();
    }

    private void replaceFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*Fragment temp = initialFragment;
        if(!SessionHelper.fragmentStack.lastElement().equals(new HomeFragment()) && SessionHelper.fragmentStack.size() != 1) {
            SessionHelper.fragmentStack.push(temp);
        }*/
        fragmentManager.beginTransaction()
                .replace(R.id.container, initialFragment)
                .commit();
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!(SessionHelper.fragmentStack.size() == 0)) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, SessionHelper.fragmentStack.lastElement());
                SessionHelper.fragmentStack.pop();
                ft.commit();
            }
            else{
                finish();
            }
        }
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

}
