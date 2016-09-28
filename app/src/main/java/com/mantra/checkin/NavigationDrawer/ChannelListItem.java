package com.mantra.checkin.NavigationDrawer;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.mantra.checkin.Entities.Enums.ResourceType;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;
import com.mantra.checkin.Entities.ViewModel.NavDrawerChildViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/21/2016.
 */
public class ChannelListItem implements ParentListItem {

    private ChannelModel mChannelModel;
    private List<NavDrawerChildViewItem> childrenList;

    public ChannelListItem(ChannelModel model){
        childrenList = new ArrayList<>();
        if(model.Urls.size() > 0){
            NavDrawerChildViewItem navItem = new NavDrawerChildViewItem();
            navItem.Name = "Urls";
            navItem.ChannelId = model.getChannelId();
            childrenList.add(navItem);
        }
        if(model.Applications.size() > 0){
            NavDrawerChildViewItem navItem = new NavDrawerChildViewItem();
            navItem.Name = "Applications";
            navItem.ChannelId = model.getChannelId();
            childrenList.add(navItem);
        }
        if(model.Contacts.size() > 0){
            NavDrawerChildViewItem navItem = new NavDrawerChildViewItem();
            navItem.Name = "Contacts";
            navItem.ChannelId = model.getChannelId();
            childrenList.add(navItem);
        }
        if(model.Venues.size() > 0){
            NavDrawerChildViewItem navItem = new NavDrawerChildViewItem();
            navItem.Name = "Venues";
            navItem.ChannelId = model.getChannelId();
            childrenList.add(navItem);
        }
        if(model.ChatRooms.size() > 0){
            NavDrawerChildViewItem navItem = new NavDrawerChildViewItem();
            navItem.Name = "ChatRooms";
            navItem.ChannelId = model.getChannelId();
            childrenList.add(navItem);
        }
        mChannelModel = model;
    }

    @Override
    public List<NavDrawerChildViewItem> getChildItemList() {
        return this.childrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }

    public ChannelModel getChannelModel(){
        return this.mChannelModel;
    }
}
