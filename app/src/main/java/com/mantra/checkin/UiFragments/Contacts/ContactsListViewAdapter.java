package com.mantra.checkin.UiFragments.Contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mantra.checkin.Entities.Models.ContactModel;
import com.mantra.checkin.Entities.Models.UrlModel;
import com.mantra.checkin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nravishankar on 9/28/2016.
 */
public class ContactsListViewAdapter extends ArrayAdapter<ContactModel> {

    private final Context context;
    private List<ContactModel> values;

    public ContactsListViewAdapter(Context context, int resource, List<ContactModel> list) {
        super(context, resource, list);
        this.context = context;
        this.values = list;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView textViewAddress = (TextView) rowView.findViewById(R.id.textViewAddress);
        TextView textViewTime = (TextView) rowView.findViewById(R.id.textViewTime);
        ImageView image = (ImageView) rowView.findViewById(R.id.list_image);

        textViewTitle.setText(values.get(position).ContactName);
        textViewAddress.setText(values.get(position).ContactPosition);
        textViewTime.setVisibility(View.GONE);

        final int mPosition = position;

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Do you want to call this person?");
                alertDialogBuilder.setPositiveButton("Call",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + values.get(mPosition).ContactNumber));
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        Toast.makeText(context, "Not enough permissions to call!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    context.startActivity(callIntent);
                                } catch (ActivityNotFoundException activityException) {
                                    Log.e("Calling a Phone Number", "Call failed", activityException);
                                    Toast.makeText(context, "Call failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        return rowView;
    }
}
