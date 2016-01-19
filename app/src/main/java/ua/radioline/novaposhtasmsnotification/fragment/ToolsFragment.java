package ua.radioline.novaposhtasmsnotification.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;

import ua.radioline.novaposhtasmsnotification.R;
import ua.radioline.novaposhtasmsnotification.basic.BaseValues;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends Fragment {

    private Button btnSave = null;
    private EditText etKeyAPI = null;
    private EditText etTamplate;
    private CheckBox cbSendImmediately;
    private CheckBox cbSaveSMS;
    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tools,container,false);
        btnSave = (Button)rootView.findViewById(R.id.btnSave);
        etKeyAPI = (EditText) rootView.findViewById(R.id.etKeyAPI);
        etTamplate = (EditText) rootView.findViewById(R.id.etTemplate);
        cbSendImmediately = (CheckBox) rootView.findViewById(R.id.cbSendImmediately);
        cbSaveSMS = (CheckBox) rootView.findViewById(R.id.cbSaveSMS);



        Boolean keySendImmediately = Boolean.valueOf(BaseValues.GetValue("SendImmediately"));
        cbSendImmediately.setChecked(keySendImmediately);
        cbSaveSMS.setChecked(Boolean.valueOf(BaseValues.GetValue("saveSMSInBox")));

        String keyValue = BaseValues.GetValue("KeyAPI");
        if ((keyValue!=null))
            etKeyAPI.setText(keyValue);
        etKeyAPI.setText("44179893298d959b14a31733cd82729e");
        String sTemplate = BaseValues.GetValue("Template");
        if ((sTemplate==null)||(sTemplate==""))
        {
            String smsinfo = "RL otpravleno Nova Poshta %IntDocNumber% " +
                    "<SeatsAmount>mest %SeatsAmount% </SeatsAmount>" +
                    "<EstimatedDeliveryDate>data %EstimatedDeliveryDate%</EstimatedDeliveryDate>" +
                    "<Pay>k oplate:" +
                    " <CostOnSite>%CostOnSite% za dostavku </CostOnSite>" +
                    " <Cost>%Cost% za zakaz<Cost></Pay>";
            etTamplate.setText(smsinfo);
        }
        else
        {
            etTamplate.setText(sTemplate);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String keyValue = etKeyAPI.getText().toString();
                    BaseValues.SetValue("KeyAPI", keyValue);
                    Crashlytics.setString("keyValue", keyValue);
                    BaseValues.SetValue("Template",etTamplate.getText().toString());
                    BaseValues.SetValue("SendImmediately",String.valueOf(cbSendImmediately.isChecked()));
                    BaseValues.SetValue("saveSMSInBox",String.valueOf(cbSaveSMS.isChecked()));
            }
        });
        return  rootView;


    }

}
