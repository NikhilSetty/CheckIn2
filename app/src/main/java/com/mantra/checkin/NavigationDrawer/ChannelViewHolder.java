package com.mantra.checkin.NavigationDrawer;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.R;

/**
 * Created by nravishankar on 9/21/2016.
 */
public class ChannelViewHolder extends ParentViewHolder {

    private TextView mChannelViewHolder;

    public ChannelViewHolder(View itemView) {
        super(itemView);
        mChannelViewHolder = (TextView) itemView.findViewById(R.id.textViewChannelName);
    }

    public void bind(ChannelModel model) {
        mChannelViewHolder.setText(model.getName());
    }
}
