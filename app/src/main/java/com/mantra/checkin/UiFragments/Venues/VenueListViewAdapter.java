package com.mantra.checkin.UiFragments.Venues;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mantra.checkin.Entities.Models.UrlModel;
import com.mantra.checkin.Entities.Models.VenueModel;
import com.mantra.checkin.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by nravishankar on 9/28/2016.
 */
public class VenueListViewAdapter extends ArrayAdapter<VenueModel>{

    private final Context context;
    private List<VenueModel> values;

    public VenueListViewAdapter(Context context, int resource, List<VenueModel> list) {
        super(context, resource, list);
        this.context = context;
        this.values = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView textViewAddress = (TextView) rowView.findViewById(R.id.textViewAddress);
        TextView textViewTime = (TextView) rowView.findViewById(R.id.textViewTime);
        ImageView image = (ImageView) rowView.findViewById(R.id.list_image);
        LinearLayout layoutLeft = (LinearLayout) rowView.findViewById(R.id.thumbnail);
        layoutLeft.setVisibility(View.GONE);

        textViewTitle.setText(values.get(position).VenueName);
        textViewTime.setVisibility(View.GONE);
        textViewAddress.setVisibility(View.GONE);

        textViewTitle.setPadding(30, 10, 10, 10);

        RelativeLayout layoutRight = (RelativeLayout) rowView.findViewById(R.id.thumbnailRight);
        layoutRight.setVisibility(View.VISIBLE);
        Button buttonNavigate = (Button) rowView.findViewById(R.id.buttonNavigate);
        buttonNavigate.setVisibility(View.VISIBLE);

        final int mPosition = position;

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f (" + values.get(mPosition).VenueName + ")", values.get(mPosition).VenueLocation.getLatitude(), values.get(mPosition).VenueLocation.getLongitude())));
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}
