package com.mantra.checkin.Entities.Models;

import com.mantra.checkin.Entities.JSONKEYS.ContactsJsonKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ContactModel {
    public String ContactId = "";
    public String ContactName = "";
    public String ContactNumber = "";
    public String ContactPosition = "";

    public static List<ContactModel> getContactsListFromJsonObject(JSONArray contactsArray) throws JSONException {
        List<ContactModel> list = new ArrayList<>();
        for (int i = 0; i < contactsArray.length(); i++){
            ContactModel model = new ContactModel();
            JSONObject modelObject = new JSONObject(contactsArray.get(i).toString());

            model.ContactId = ""; // todo generate Contact Id
            model.ContactName = modelObject.getString(ContactsJsonKeys.ContactName);
            model.ContactNumber = modelObject.getString(ContactsJsonKeys.ContactNumber);
            model.ContactPosition = modelObject.getString(ContactsJsonKeys.ContactTitle);

            list.add(model);
        }

        return list;
    }
}
