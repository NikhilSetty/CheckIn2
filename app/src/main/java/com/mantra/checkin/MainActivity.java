package com.mantra.checkin;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.mantra.checkin.Adapters.ChannelListForUserAdapter;
import com.mantra.checkin.Entities.Interfaces.OnItemClick;
import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.ViewModel.NavDrawerChildViewItem;
import com.mantra.checkin.NavigationDrawer.ChannelListItem;
import com.mantra.checkin.NavigationDrawer.NavigationDrawerRecyclerViewAdapter;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.UiFragments.Applications.ApplicationFragment;
import com.mantra.checkin.UiFragments.ChatRooms.ChatRoomFragment;
import com.mantra.checkin.UiFragments.Contacts.ContactsFragment;
import com.mantra.checkin.UiFragments.HomeFragment;
import com.mantra.checkin.UiFragments.TabComponents.PagerAdapter;
import com.mantra.checkin.UiFragments.Urls.UrlFragment;
import com.mantra.checkin.UiFragments.Venues.VenueFragment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClick {

    private RecyclerView mNavigationDrawerRecyclerView;
    private RecyclerView.Adapter mNavigationDrawerAdapter;
    private RecyclerView.LayoutManager mNavigationDrawerLayoutManager;

    public static String currentChannelId = "";

    private final static String TAG = "MainActivity";

    private static Fragment initialFragment;
    private static String mTitle = "";

    private static boolean isChatActive = false;
    public static Map<Integer, String> fragmentMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Initialize Header
        ImageView imageProfilePhoto = (ImageView) findViewById(R.id.imageViewProfilePhoto);
        Picasso.with(getApplicationContext()).load(SessionHelper.user.RemotePhotoServerURL).into(imageProfilePhoto);
        TextView textViewUserName = (TextView) findViewById(R.id.navDrawerUserName);
        textViewUserName.setText(SessionHelper.user.UserName);
        TextView textViewEmail = (TextView) findViewById(R.id.textViewNavDrawerEmail);
        textViewEmail.setText(SessionHelper.user.UserEmail);

        /*// Initialize Navigation Drawer ListView
        mNavigationDrawerRecyclerView = (RecyclerView) findViewById(R.id.navigation_drawer_recycler_view);
        mNavigationDrawerLayoutManager = new LinearLayoutManager(this);
        List<ChannelListItem> viewList = new ArrayList<>();
        List<ChannelListItem> viewListPrivate = new ArrayList<>();
        List<ChannelListItem> viewListPublic = new ArrayList<>();
        ChannelModel privateTitle = new ChannelModel();
        privateTitle.Name = "privatChTitle";
        privateTitle.ChannelId = "-123";
        viewListPrivate.add(new ChannelListItem(privateTitle));
        ChannelModel publicTitle = new ChannelModel();
        publicTitle.Name = "publicChTitle";
        publicTitle.ChannelId = "-124";
        viewListPublic.add(new ChannelListItem(publicTitle));

        for(int i = 0; i < SessionHelper.channelModelList.size(); i++){
            ChannelModel tModel = SessionHelper.channelModelList.get(i);
            if(tModel.IsPublic){
                viewListPublic.add(new ChannelListItem(tModel));
            }else {
                viewListPrivate.add(new ChannelListItem(tModel));
            }
        }

        for(int i = 0; i < viewListPrivate.size(); i++){
            viewList.add(viewListPrivate.get(i));
        }
        for(int i = 0; i < viewListPublic.size(); i++){
            viewList.add(viewListPublic.get(i));
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
        replaceFragment();*/

        ChannelModel currentModel = new ChannelModel();
        fragmentMapper = new HashMap<>();

        for(int i = 0; i < SessionHelper.channelModelList.size(); i++){
            if(SessionHelper.channelModelList.get(i).getChannelId().equals(currentChannelId)){
                currentModel = SessionHelper.channelModelList.get(i);
                break;
            }
        }

        //toolbar.setTitleTextColor(getResources().getColor(R.color.primary));
        toolbar.setTitle(currentModel.Name);
        toolbar.setTitleTextColor(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(currentModel.getChannelId())).get(ChannelJsonKeys.ChannelTertiaryColor)));
        toolbar.setBackgroundColor(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(currentModel.getChannelId())).get(ChannelJsonKeys.ChannelPrimaryColor)));
        setSupportActionBar(toolbar);


        // Mapping integer
        int i = 0;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // Applications
        TabLayout.Tab applicationsTab = tabLayout.newTab().setText("Applications");
        if(currentModel.Applications.size() > 0){
            tabLayout.addTab(applicationsTab);
            fragmentMapper.put(i, "Applications");
            i += 1;
        }

        // Contacts
        TabLayout.Tab contactsTab = tabLayout.newTab().setText("Contacts");
        if(currentModel.Applications.size() > 0){
            tabLayout.addTab(contactsTab);
            fragmentMapper.put(i, "Contacts");
            i += 1;
        }

        // Urls
        TabLayout.Tab urlsTab = tabLayout.newTab().setText("Urls");
        if(currentModel.Applications.size() > 0){
            tabLayout.addTab(urlsTab);
            fragmentMapper.put(i, "Urls");
            i += 1;
        }

        // Contacts
        TabLayout.Tab venuesTab = tabLayout.newTab().setText("Venues");
        if(currentModel.Applications.size() > 0){
            tabLayout.addTab(venuesTab);
            fragmentMapper.put(i, "Venues");
            i += 1;
        }

        // Contacts
        TabLayout.Tab chatroomTab = tabLayout.newTab().setText("Chat");
        if(currentModel.Applications.size() > 0){
            tabLayout.addTab(chatroomTab);
            fragmentMapper.put(i, "Chat");
            i += 1;
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setTabTextColors(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(currentModel.getChannelId())).get(ChannelJsonKeys.ChannelTertiaryColor)),Color.parseColor("white"));
        tabLayout.setBackgroundColor(Color.parseColor(ChannelListForUserAdapter.brandingmap.get(Integer.parseInt(currentModel.getChannelId())).get(ChannelJsonKeys.ChannelPrimaryColor)));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the acti    on bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getSupportActionBar();
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        activityResumed();

        /*// Initialize Navigation Drawer ListView
        mNavigationDrawerRecyclerView = (RecyclerView) findViewById(R.id.navigation_drawer_recycler_view);
        mNavigationDrawerLayoutManager = new LinearLayoutManager(this);
        List<ChannelListItem> viewList = new ArrayList<>();
        List<ChannelListItem> viewListPrivate = new ArrayList<>();
        List<ChannelListItem> viewListPublic = new ArrayList<>();
        ChannelModel privateTitle = new ChannelModel();
        privateTitle.Name = "privatChTitle";
        privateTitle.ChannelId = "-123";
        viewListPrivate.add(new ChannelListItem(privateTitle));
        ChannelModel publicTitle = new ChannelModel();
        publicTitle.Name = "publicChTitle";
        publicTitle.ChannelId = "-124";
        viewListPublic.add(new ChannelListItem(publicTitle));

        for(int i = 0; i < SessionHelper.channelModelList.size(); i++){
            ChannelModel tModel = SessionHelper.channelModelList.get(i);
            if(tModel.IsPublic){
                viewListPublic.add(new ChannelListItem(tModel));
            }else {
                viewListPrivate.add(new ChannelListItem(tModel));
            }
        }

        for(int i = 0; i < viewListPrivate.size(); i++){
            viewList.add(viewListPrivate.get(i));
        }
        for(int i = 0; i < viewListPublic.size(); i++){
            viewList.add(viewListPublic.get(i));
        }

        mNavigationDrawerAdapter = new NavigationDrawerRecyclerViewAdapter(this, viewList, this);
        mNavigationDrawerRecyclerView.setAdapter(mNavigationDrawerAdapter);
        mNavigationDrawerRecyclerView.setLayoutManager(mNavigationDrawerLayoutManager);*/

    }
    @Override
    public void onPause(){
        super.onPause();
        activityPaused();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
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
            case "Web Clip":
                initialFragment = new UrlFragment();
                break;
            case "Applications":
                initialFragment = new ApplicationFragment();
                break;
            case "Contacts":
                initialFragment = new ContactsFragment();
                break;
            case "Locations":
                initialFragment = new VenueFragment();
                break;
            case "Chat Room":
                isChatActive = true;
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
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        *//*Fragment temp = initialFragment;
        if(!SessionHelper.fragmentStack.lastElement().equals(new HomeFragment()) && SessionHelper.fragmentStack.size() != 1) {
            SessionHelper.fragmentStack.push(temp);
        }*//*
        SessionHelper.fragmentStack.push(initialFragment);
        if(initialFragment.equals(new ChatRoomFragment())) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, initialFragment, "CHAT_ROOM")
                    .commit();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, initialFragment)
                .commit();*/
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
/*        if(isChatActive){
            isChatActive = false;
        }
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
        }*/
    }

    public static Boolean getChatFragment(){
        if(SessionHelper.fragmentStack.size() > 0){
            try {
                if(initialFragment.equals(new ChatRoomFragment())){
                    ChatRoomFragment frag = (ChatRoomFragment) initialFragment;
                    frag.PopulateListView();
                }
            }catch (Exception e){
                Log.e(TAG, e.getMessage());
            }
        }
        return null;
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

}
