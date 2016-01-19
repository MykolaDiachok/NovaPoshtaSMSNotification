package ua.radioline.novaposhtasmsnotification.sms;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.radioline.novaposhtasmsnotification.DB.DBHelper;
import ua.radioline.novaposhtasmsnotification.MainActivity;
import ua.radioline.novaposhtasmsnotification.basic.BaseValues;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;

/**
 * Created by mikoladyachok on 1/16/16.
 */
public class BasicSendSMS {
    private boolean itsTest=true;

    private Context context;
    private boolean bSendImmediately,bsaveSMSInBox;

    public BasicSendSMS(Context context, boolean bSendImmediately, boolean bsaveSMSInBox) {
        this.bSendImmediately = bSendImmediately;
        this.bsaveSMSInBox = bsaveSMSInBox;
        this.context = context;
    }

    public BasicSendSMS(Context context) {
        this.bSendImmediately = Boolean.valueOf(BaseValues.GetValue("SendImmediately"));
        this.bsaveSMSInBox = Boolean.valueOf(BaseValues.GetValue("saveSMSInBox"));
        this.context = context;
    }

    public void SendSMS(InternetDocument idoc)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sdate = idoc.EstimatedDeliveryDate.substring(0, 10);
        try {
            Date date = format.parse(idoc.EstimatedDeliveryDate);
            format = new SimpleDateFormat("dd.MM.yyyy");
            sdate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String smsinfo = "RL otpravleno Nova Poshta " + idoc.IntDocNumber + " mest " + idoc.SeatsAmount + " data " + sdate;

        if (bSendImmediately) {
            SmsManager smsManager = SmsManager.getDefault();
            if (itsTest) {
                smsManager.sendTextMessage("+380676112798", null, smsinfo, null, null);
            }
            else
            {
                smsManager.sendTextMessage(idoc.RecipientContactPhone, null, smsinfo, null, null);
            }
            if (bsaveSMSInBox) {
                ContentValues values = new ContentValues();
                values.put("address", idoc.RecipientContactPhone);//sender name
                values.put("body", smsinfo);
                MainActivity.contextOfApplication.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
            }


        } else {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            if (itsTest) {
                smsIntent.putExtra("address", "+380676112798");
            }
            else
            {
                smsIntent.putExtra("address", idoc.RecipientContactPhone);
            }
            smsIntent.putExtra("sms_body", smsinfo);
            context.startActivity(smsIntent);
        }


        idoc.SendSMS = true;
        DBHelper dbHelper = new DBHelper();
        dbHelper.insert(idoc.IntDocNumber, idoc.RecipientContactPhone, idoc.IntDocNumber, "SMS info", true);
        Toast.makeText(context, idoc.RecipientContactPhone, Toast.LENGTH_LONG).show();
//        adapter.notifyDataSetChanged();
    }
}
