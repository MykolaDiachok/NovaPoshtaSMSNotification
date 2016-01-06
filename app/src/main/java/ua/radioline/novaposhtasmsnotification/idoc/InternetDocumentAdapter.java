package ua.radioline.novaposhtasmsnotification.idoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import ua.radioline.novaposhtasmsnotification.R;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;

/**
 * Created by mikoladyachok on 12/30/15.
 */
public class InternetDocumentAdapter extends ArrayAdapter<InternetDocument> {


    public InternetDocument getInternetDocument(int position){
        return getItem(position);
    }

    private static class ViewHolder {
        TextView IntDocNumber;
        TextView PayerType;
        TextView RecipientContactPhone;
        TextView RecipientContactPerson;
        TextView EstimatedDeliveryDate;
        TextView RecipientAddressDescription;
        CheckBox CheckBoxSend;
    }


    public InternetDocumentAdapter(Context context, ArrayList<InternetDocument> users) {
        super(context, R.layout.list_inet_doc, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InternetDocument internetDocument = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_inet_doc, parent, false);
            viewHolder.IntDocNumber = (TextView) convertView.findViewById(R.id.tvIntDocNumber);
            viewHolder.PayerType = (TextView) convertView.findViewById(R.id.tvPayerType);
            viewHolder.RecipientContactPhone = (TextView) convertView.findViewById(R.id.tvRecipientContactPhone);
            viewHolder.RecipientContactPerson = (TextView) convertView.findViewById(R.id.tvRecipientContactPerson);
            viewHolder.EstimatedDeliveryDate = (TextView) convertView.findViewById(R.id.tvEstimatedDeliveryDate);
            viewHolder.RecipientAddressDescription = (TextView) convertView.findViewById(R.id.tvRecipientAddressDescription);
            viewHolder.CheckBoxSend = (CheckBox) convertView.findViewById(R.id.checkBoxSend);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.IntDocNumber.setText(internetDocument.IntDocNumber);
        viewHolder.PayerType.setText(internetDocument.PayerType);
        viewHolder.RecipientContactPhone.setText(internetDocument.RecipientContactPhone);
        viewHolder.RecipientContactPerson.setText(internetDocument.RecipientContactPerson);
        viewHolder.EstimatedDeliveryDate.setText(internetDocument.EstimatedDeliveryDate.substring(0,10).trim());
        viewHolder.RecipientAddressDescription.setText(internetDocument.CityRecipientDescription+" "+ internetDocument.RecipientAddressDescription);
        viewHolder.CheckBoxSend.setChecked(internetDocument.SendSMS);
        // Return the completed view to render on screen
        return convertView;
    }
}
