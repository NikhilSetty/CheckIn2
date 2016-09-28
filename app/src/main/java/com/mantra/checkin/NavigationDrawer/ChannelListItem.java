package com.mantra.checkin.NavigationDrawer;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;

import java.util.List;

/**
 * Created by nravishankar on 9/21/2016.
 */
public class ChannelListItem implements ParentListItem {

    private List<ChannelResourcesModel> childrenList;

    @Override
    public List<?> getChildItemList() {
        return null;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
