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
    private TextView mChannelViewTitle;
    private View mItemView;

    public ChannelViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mChannelViewHolder = (TextView) itemView.findViewById(R.id.textViewChannelName);
        mChannelViewTitle = (TextView) mItemView.findViewById(R.id.textViewViewHolderTitle);

    }

    public void bind(ChannelModel model) {
        if(model.getName().equals("privatChTitle")){
            mChannelViewHolder.setVisibility(View.GONE);
            mChannelViewTitle.setText("Private");
        }else if(model.getName().equals("publicChTitle")){
            mChannelViewHolder.setVisibility(View.GONE);
            mChannelViewTitle.setText("Public");
        }else {
            mChannelViewTitle.setVisibility(View.GONE);
            mChannelViewHolder.setText(model.getName());
        }
    }
}
