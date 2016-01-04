package ua.radioline.novaposhtasmsnotification.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import ua.radioline.novaposhtasmsnotification.R;
import ua.radioline.novaposhtasmsnotification.basic.BaseValues;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends Fragment {

    Button btnSave = null;
    EditText etKeyAPI = null;
    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tools,container,false);
        btnSave = (Button)rootView.findViewById(R.id.btnSave);
        etKeyAPI = (EditText) rootView.findViewById(R.id.etKeyAPI);
        String keyValue = BaseValues.GetValue("KeyAPI");
        if ((keyValue!=null))
            etKeyAPI.setText(keyValue);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    BaseValues.SetValue("KeyAPI", etKeyAPI.getText().toString());
            }
        });
        return  rootView;


    }

}
