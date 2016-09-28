package com.mantra.checkin.UiFragments.Urls;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantra.checkin.Entities.Models.UrlModel;
import com.mantra.checkin.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by nravishankar on 9/28/2016.
 */
public class UrlListViewAdapter extends ArrayAdapter<UrlModel> {

    private final Context context;
    private List<UrlModel> values;

    public UrlListViewAdapter(Context context, int resource, List<UrlModel> list) {
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

        textViewTitle.setText(values.get(position).UrlName);
        textViewAddress.setText(values.get(position).UrlAddress);
        textViewTime.setVisibility(View.GONE);

        if(!values.get(position).UrlIconAddress.isEmpty()){
            Picasso.with(context).load(values.get(position).UrlIconAddress).into(image);
        }

        final int mPosition = position;

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(values.get(mPosition).UrlAddress));
                context.startActivity(browserIntent);
            }
        });

        return rowView;
    }
}
