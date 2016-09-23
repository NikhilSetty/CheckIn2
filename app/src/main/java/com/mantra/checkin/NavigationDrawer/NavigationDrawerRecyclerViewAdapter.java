package com.mantra.checkin.NavigationDrawer;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by nravishankar on 9/21/2016.
 */
public class NavigationDrawerRecyclerViewAdapter extends ExpandableRecyclerAdapter<ChannelViewHolder, ChannelChildrenViewHolder> {


    public NavigationDrawerRecyclerViewAdapter(@NonNull List<ChannelListItem> parentItemList) {
        super(parentItemList);
    }

    @Override
    public ChannelViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        return null;
    }

    @Override
    public ChannelChildrenViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        return null;
    }

    @Override
    public void onBindParentViewHolder(ChannelViewHolder parentViewHolder, int position, ParentListItem parentListItem) {

    }

    @Override
    public void onBindChildViewHolder(ChannelChildrenViewHolder childViewHolder, int position, Object childListItem) {

    }
}
