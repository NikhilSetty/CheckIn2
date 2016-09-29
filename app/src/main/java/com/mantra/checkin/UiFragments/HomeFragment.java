package com.mantra.checkin.UiFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantra.checkin.ChannelListForUser;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = super.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);

        ImageView profilePhoto = (ImageView) layout.findViewById(R.id.imageViewHomeProfilePhoto);
        Picasso.with(getActivity()).load(SessionHelper.user.RemotePhotoServerURL).into(profilePhoto);
        TextView textViewUserName = (TextView) layout.findViewById(R.id.homeUserName);
        textViewUserName.setText(SessionHelper.user.UserName);

        Button buttonNewChannels = (Button) layout.findViewById(R.id.buttonNewChannel);
        buttonNewChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChannelListForUser.class);
                i.putExtra("IsFromHome", true);
                startActivity(i);
            }
        });

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached("Home");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
