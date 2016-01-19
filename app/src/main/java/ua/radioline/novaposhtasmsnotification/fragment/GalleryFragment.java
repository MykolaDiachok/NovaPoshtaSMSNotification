package ua.radioline.novaposhtasmsnotification.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
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
import ua.radioline.novaposhtasmsnotification.basic.EditTextDatePicker;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentOnTaskListener;
import ua.radioline.novaposhtasmsnotification.sms.BasicSendSMS;


public class GalleryFragment extends Fragment implements InternetDocumentOnTaskListener, EditTextDatePickerOnCompleted {

    private boolean itsTest = false;

    private EditText etDate = null;
    private ListView lvArrayEN = null;
    private Date tDate;
    private InternetDocumentAdapter adapter;
    private boolean bSendImmediately, bsaveSMSInBox;
    private InternetDocument internetDocument;
    private ProgressDialog mProgressDialog;
    private EditTextDatePicker datepicker;
    private FloatingActionButton fabRefresh, fab;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("etDate", etDate.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public GalleryFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String tempDate = savedInstanceState.getString("etDate");
            etDate.setText(tempDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
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


        if ((savedInstanceState != null) && savedInstanceState.getString("etDate") != null) {

            String tempDate = savedInstanceState.getString("etDate");
            etDate.setText(tempDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            try {
                tDate = dateFormat.parse(tempDate);
                datepicker = new EditTextDatePicker(container.getContext(), etDate, tDate, this);
                datepicker.setDate(tDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            datepicker = new EditTextDatePicker(container.getContext(), etDate, tDate, this);
            datepicker.setCurrentDate();
        }


        fabRefresh = (FloatingActionButton) rootView.findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = etDate.getText().toString();
                new InternetDocumentAsyncTask(getActivity(), GalleryFragment.this).execute(date);
            }
        });

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String currentDate = sdf.format(new Date());
                new InternetDocumentAsyncTask(getActivity(), GalleryFragment.this).execute(currentDate, "true");

            }
        });


        return rootView;
    }


    private void SendSMS(InternetDocument idoc) {
        BasicSendSMS basicSendSMS = new BasicSendSMS(MainActivity.contextOfApplication);
        basicSendSMS.SendSMS(idoc);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onTaskStarted() {


        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Waiting...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);


        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if ((mProgressDialog != null) && (mProgressDialog.isShowing()))
            mProgressDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissProgressDialog();
    }

    @Override
    public void onTaskCompleted(ArrayList<InternetDocument> internetDocuments, boolean forSent) {
        if (!forSent) {
            adapter = new InternetDocumentAdapter(getActivity(), internetDocuments);
            lvArrayEN.setAdapter(adapter);
        } else if (forSent) {
            BasicSendSMS basicSend = new BasicSendSMS(getActivity());
            for (InternetDocument idoc : internetDocuments
                    ) {
                if (!idoc.SendSMS) {
                    basicSend.SendSMS(idoc);
                }
            }
            adapter = new InternetDocumentAdapter(getActivity(), internetDocuments);
            lvArrayEN.setAdapter(adapter);
        }


    dismissProgressDialog();

}

    @Override
    public void OnCompleted(String date) {
        new InternetDocumentAsyncTask(getActivity(), this).execute(date);
    }


}
