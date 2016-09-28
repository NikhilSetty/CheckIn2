package com.mantra.checkin.NavigationDrawer;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.mantra.checkin.Entities.Enums.ResourceType;
import com.mantra.checkin.Entities.Interfaces.OnItemClick;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;
import com.mantra.checkin.Entities.ViewModel.NavDrawerChildViewItem;
import com.mantra.checkin.R;

/**
 * Created by nravishankar on 9/21/2016.
 */
public class ChannelChildrenViewHolder extends ChildViewHolder {

    private TextView mChildItemView;
    private OnItemClick mListener;
    private NavDrawerChildViewItem item;

    public ChannelChildrenViewHolder(final View itemView, final OnItemClick listener) {
        super(itemView);
        mChildItemView = (TextView) itemView.findViewById(R.id.textViewChannelChild);
        mChildItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(itemView, item);
            }
        });
    }

    public void bind(NavDrawerChildViewItem navItem) {
        mChildItemView.setText(navItem.Name);
        item = navItem;
    }
}
