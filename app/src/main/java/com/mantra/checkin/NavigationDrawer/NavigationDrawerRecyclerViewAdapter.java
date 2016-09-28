package com.mantra.checkin.NavigationDrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.mantra.checkin.Entities.Interfaces.OnItemClick;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;
import com.mantra.checkin.Entities.ViewModel.NavDrawerChildViewItem;
import com.mantra.checkin.R;

import java.util.List;

/**
 * Created by nravishankar on 9/21/2016.
 */
public class NavigationDrawerRecyclerViewAdapter extends ExpandableRecyclerAdapter<ChannelViewHolder, ChannelChildrenViewHolder> {

    private LayoutInflater mInflator;
    private OnItemClick mListener;

    public NavigationDrawerRecyclerViewAdapter(Context context, @NonNull List<ChannelListItem> parentItemList, OnItemClick listener) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public ChannelViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = mInflator.inflate(R.layout.channel_view_holder, parentViewGroup, false);
        return new ChannelViewHolder(parentView);
    }

    @Override
    public ChannelChildrenViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.channel_child_layout, childViewGroup, false);
        return new ChannelChildrenViewHolder(childView, mListener);
    }

    @Override
    public void onBindParentViewHolder(ChannelViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        ChannelListItem channel = (ChannelListItem) parentListItem;
        parentViewHolder.bind(channel.getChannelModel());
    }

    @Override
    public void onBindChildViewHolder(ChannelChildrenViewHolder childViewHolder, int position, Object childListItem) {
        NavDrawerChildViewItem item = (NavDrawerChildViewItem) childListItem;
        childViewHolder.bind(item);
    }
}
