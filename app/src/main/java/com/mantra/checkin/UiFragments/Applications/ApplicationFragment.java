package com.mantra.checkin.UiFragments.Applications;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;
import com.mantra.checkin.UiFragments.Urls.UrlListViewAdapter;

public class ApplicationFragment extends Fragment {

    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_application, container, false);
        mListView = (ListView) layout.findViewById(R.id.listViewApplications);
        PopulateListView();
        return layout;
    }

    private void PopulateListView() {
        ChannelModel model = new ChannelModel();
        for(int i = 0; i < SessionHelper.channelModelList.size(); i++){
            if(SessionHelper.channelModelList.get(i).ChannelId.equals(MainActivity.currentChannelId)){
                model = SessionHelper.channelModelList.get(i);
                break;
            }
        }

        ApplicationListViewAdapter adapter = new ApplicationListViewAdapter(getActivity(), 0, model.Applications);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
