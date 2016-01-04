package ua.radioline.novaposhtasmsnotification.basic;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentAsyncTask;

/**
 * Created by mikoladyachok on 12/28/15.
 */
public class EditTextDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText editText;
    private int day;
    private int month;
    private int year;
    private Context context;
    private Date date;
    private EditTextDatePickerOnCompleted listener;




    public EditTextDatePicker(Context context, EditText edit,Date date, EditTextDatePickerOnCompleted listener)
    {
        this.editText = edit;
        this.editText.setOnClickListener(this);
        this.context = context;
        this.date = date;
        this.listener = listener;

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
        updateDisplay();


    }


    public void onClick(View v) {
        DatePickerDialog dialog =  new DatePickerDialog(context, this, year, month, day);
        dialog.show();
    }


    // setting default current date
    public void setCurrentDate(){
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        date = c.getTime();
        updateDisplay();
    }


    // updates the date in the EditText
    private void updateDisplay() {

StringBuilder date = new StringBuilder()
        // Month is 0 based so add 1
        .append(day).append(".").append(month + 1).append(".").append(year);
        editText.setText(date.toString());
        listener.OnCompleted(date.toString());
//        new InternetDocumentAsyncTask(this.context,activity).execute(date.toString());

    }

    public Date getDate() {
        return date;
    }
}
