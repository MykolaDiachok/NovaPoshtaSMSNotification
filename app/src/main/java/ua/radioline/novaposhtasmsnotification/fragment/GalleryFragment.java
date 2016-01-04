package ua.radioline.novaposhtasmsnotification.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Date;




import ua.radioline.novaposhtasmsnotification.R;

import ua.radioline.novaposhtasmsnotification.basic.EditTextDatePickerOnCompleted;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentAdapter;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentAsyncTask;
import ua.radioline.novaposhtasmsnotification.idoc.InternetDocumentOnTaskCompleted;
import ua.radioline.novaposhtasmsnotification.basic.EditTextDatePicker;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment implements InternetDocumentOnTaskCompleted,EditTextDatePickerOnCompleted {

    private EditText etDate = null;
    private ListView lvArrayEN = null;
    private Date tDate;
    private InternetDocumentAdapter adapter;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_gallery,container,false);

        etDate = (EditText) rootView.findViewById(R.id.etDate);
        lvArrayEN = (ListView) rootView.findViewById(R.id.lvArrayEN);

        lvArrayEN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InternetDocument idoc = adapter.getInternetDocument(position);
                Toast.makeText(getActivity(),idoc.RecipientContactPhone, Toast.LENGTH_LONG).show();
            }
        });

        EditTextDatePicker datepicker = new EditTextDatePicker(container.getContext(), etDate,tDate,this);
        datepicker.setCurrentDate();
//        new InternetDocumentAsyncTask(getActivity(),this).execute();
        return  rootView;
    }



    @Override
    public void onTaskCompleted(ArrayList<InternetDocument> internetDocuments) {

        adapter = new InternetDocumentAdapter(getActivity(), internetDocuments);
        lvArrayEN.setAdapter(adapter);
    }

    @Override
    public void OnCompleted(String date) {
        new InternetDocumentAsyncTask(getActivity(),this).execute(date);
    }
}
