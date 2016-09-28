package com.mantra.checkin.UiFragments.Applications;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantra.checkin.Entities.Models.ApplicationModel;
import com.mantra.checkin.Entities.Models.UrlModel;
import com.mantra.checkin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nravishankar on 9/28/2016.
 */
public class ApplicationListViewAdapter extends ArrayAdapter<ApplicationModel>{

    private final Context context;
    private List<ApplicationModel> values;

    public ApplicationListViewAdapter(Context context, int resource, List<ApplicationModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
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

        textViewTitle.setText(values.get(position).ApplicationName);
        textViewTime.setVisibility(View.GONE);

        final int mPosition = position;

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(values.get(mPosition).ApplicationStoreUrl));
                context.startActivity(browserIntent);
            }
        });

        return rowView;
    }
}
