package com.mantra.checkin.Entities.Interfaces;

import android.view.View;

import com.mantra.checkin.Entities.Enums.ResourceType;
import com.mantra.checkin.Entities.ViewModel.NavDrawerChildViewItem;

/**
 * Created by nravishankar on 9/28/2016.
 */
public interface OnItemClick {
    void onClickItem (View caller, NavDrawerChildViewItem item);
}
