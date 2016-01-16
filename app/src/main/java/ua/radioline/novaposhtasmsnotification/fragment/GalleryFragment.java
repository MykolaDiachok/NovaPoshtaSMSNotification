package ua.radioline.novaposhtasmsnotification.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import ua.radioline.novaposhtasmsnotification.DB.DBHelper;
import ua.radioline.novaposhtasmsnotification.MainActivity;
import ua.radioline.novaposhtasmsnotification.R;

import ua.radioline.novaposhtasmsnotification.basic.BaseValues;
import ua.radioline.novaposhtasmsnotification.basic.EditTextDatePickerOnCompleted;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentAdapter;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentAsyncTask;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentOnTaskCompleted;
import ua.radioline.novaposhtasmsnotification.basic.EditTextDatePicker;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;
import ua.radioline.novaposhtasmsnotification.sms.BasicSendSMS;


public class GalleryFragment extends Fragment implements InternetDocumentOnTaskCompleted, EditTextDatePickerOnCompleted {

    private boolean itsTest=false;

    private EditText etDate = null;
    private ListView lvArrayEN = null;
    private Date tDate;
    private InternetDocumentAdapter adapter;
    private boolean bSendImmediately, bsaveSMSInBox;
    private InternetDocument internetDocument;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        bSendImmediately = Boolean.valueOf(BaseValues.GetValue("SendImmediately"));
        bsaveSMSInBox = Boolean.valueOf(BaseValues.GetValue("saveSMSInBox"));

        etDate = (EditText) rootView.findViewById(R.id.etDate);
        lvArrayEN = (ListView) rootView.findViewById(R.id.lvArrayEN);

        lvArrayEN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                internetDocument = adapter.getInternetDocument(position);
                if (internetDocument.SendSMS) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Attention")
                            .setMessage("This SMS has been sent")
                            .setIcon(R.mipmap.ic_stat_smssend)
                            .setCancelable(true)
                            .setPositiveButton("Yes, send again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SendSMS(internetDocument);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("No, send again",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();

                } else {
                    SendSMS(internetDocument);
                }
            }
        });

        EditTextDatePicker datepicker = new EditTextDatePicker(container.getContext(), etDate, tDate, this);
        datepicker.setCurrentDate();
        return rootView;
    }


    private void SendSMS(InternetDocument idoc)
    {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String sdate = idoc.EstimatedDeliveryDate.substring(0, 10);
//        try {
//            Date date = format.parse(idoc.EstimatedDeliveryDate);
//            format = new SimpleDateFormat("dd.MM.yyyy");
//            sdate = format.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String smsinfo = "RL otpravleno Nova Poshta " + idoc.IntDocNumber + " mest " + idoc.SeatsAmount + " data " + sdate;
//
//        if (bSendImmediately) {
//            SmsManager smsManager = SmsManager.getDefault();
//            if (itsTest) {
//                smsManager.sendTextMessage("+380676112798", null, smsinfo, null, null);
//            }
//            else
//            {
//                smsManager.sendTextMessage(idoc.RecipientContactPhone, null, smsinfo, null, null);
//            }
//            if (bsaveSMSInBox) {
//                ContentValues values = new ContentValues();
//                values.put("address", idoc.RecipientContactPhone);//sender name
//                values.put("body", smsinfo);
//                MainActivity.contextOfApplication.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
//            }
//
//
//        } else {
//            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//            smsIntent.setType("vnd.android-dir/mms-sms");
//            smsIntent.putExtra("address", idoc.RecipientContactPhone);
//            smsIntent.putExtra("sms_body", smsinfo);
//            startActivity(smsIntent);
//        }
//
//
//        idoc.SendSMS = true;
//        DBHelper dbHelper = new DBHelper();
//        dbHelper.insert(idoc.IntDocNumber, idoc.RecipientContactPhone, idoc.IntDocNumber, "SMS info", true);
//        Toast.makeText(getActivity(), idoc.RecipientContactPhone, Toast.LENGTH_LONG).show();
        BasicSendSMS basicSendSMS = new BasicSendSMS(MainActivity.contextOfApplication);
        basicSendSMS.SendSMS(idoc);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onTaskCompleted(ArrayList<InternetDocument> internetDocuments) {

        adapter = new InternetDocumentAdapter(getActivity(), internetDocuments);
        lvArrayEN.setAdapter(adapter);
    }

    @Override
    public void OnCompleted(String date) {
        new InternetDocumentAsyncTask(getActivity(), this).execute(date);
    }


}
