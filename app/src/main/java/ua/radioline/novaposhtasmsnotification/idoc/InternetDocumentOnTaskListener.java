package ua.radioline.novaposhtasmsnotification.idoc;

import org.json.JSONObject;

import java.util.ArrayList;

import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;
import ua.radioline.novaposhtasmsnotification.util.Response;

/**
 * Created by mikoladyachok on 12/30/15.
 */
public interface InternetDocumentOnTaskListener {
    void onTaskStarted();
    void onTaskCompleted(ArrayList<InternetDocument> internetDocuments, boolean forsend);

}
