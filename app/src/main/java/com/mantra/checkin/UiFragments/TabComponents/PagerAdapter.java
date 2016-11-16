package com.mantra.checkin.UiFragments.TabComponents;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mantra.checkin.MainActivity;
import com.mantra.checkin.UiFragments.Applications.ApplicationFragment;
import com.mantra.checkin.UiFragments.ChatRooms.ChatRoomFragment;
import com.mantra.checkin.UiFragments.Contacts.ContactsFragment;
import com.mantra.checkin.UiFragments.Urls.UrlFragment;
import com.mantra.checkin.UiFragments.Venues.VenueFragment;

/**
 * Created by nravishankar on 11/16/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        String component = MainActivity.fragmentMapper.get(position);
        switch (component) {
            case "Applications":
                ApplicationFragment tab1 = new ApplicationFragment();
                return tab1;
            case "Contacts":
                ContactsFragment tab2 = new ContactsFragment();
                return tab2;
            case "Venues":
                VenueFragment tab3 = new VenueFragment();
                return tab3;
            case "Urls":
                UrlFragment tab4 = new UrlFragment();
                return tab4;
            case "Chat":
                ChatRoomFragment chat = new ChatRoomFragment();
                return chat;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}