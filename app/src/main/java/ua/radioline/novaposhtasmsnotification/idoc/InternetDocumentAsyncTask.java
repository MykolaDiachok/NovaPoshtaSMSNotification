package ua.radioline.novaposhtasmsnotification.idoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ua.radioline.novaposhtasmsnotification.MainActivity;
import ua.radioline.novaposhtasmsnotification.basic.BaseValues;
import ua.radioline.novaposhtasmsnotification.basic.InternetDocument;
import ua.radioline.novaposhtasmsnotification.util.HttpUtil;
import ua.radioline.novaposhtasmsnotification.util.Response;

/**
 * Created by mikoladyachok on 12/30/15.
 */
public class InternetDocumentAsyncTask extends AsyncTask<String, Void, Response> {
    private InternetDocumentOnTaskCompleted listener;
    private final static String SERVICE_URL = "https://api.novaposhta.ua/v2.0/json/";


    private ProgressDialog mProgressDialog;
    private Context mContext;



    public InternetDocumentAsyncTask(Context mContext, InternetDocumentOnTaskCompleted listener) {
        this.mContext = mContext;
        this.listener=listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Waiting...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);


        mProgressDialog.show();
    }


    @Override
    protected Response doInBackground(String... params) {
        Response response = null;


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apiKey", BaseValues.GetValue("KeyAPI"));
            jsonObject.put("modelName", "InternetDocument");
            jsonObject.put("calledMethod", "getDocumentList");
            jsonObject.put("methodProperties", new JSONObject().put("DateTime",params[0]));

//            jsonObject.put("nome", mUser.getUsername());
//            jsonObject.put("senha", mUser.getPassword());


            response = HttpUtil.sendJSONPostResquest(jsonObject, SERVICE_URL);
        } catch (IOException ioex) {
            Log.e("IOException", ioex.getMessage());
        } catch (JSONException jsonex) {
            Log.e("JSONException", jsonex.getMessage());
        }


        return response;
    }


    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);


        mProgressDialog.dismiss();


        JSONObject jsonObject = null;


        try {
            jsonObject = new JSONObject(response.getContentValue());
            ArrayList<InternetDocument> internetDocuments = InternetDocument.fromJson(jsonObject.getJSONArray("data"));
            listener.onTaskCompleted(internetDocuments);
        } catch (JSONException jsonex) {
            Toast.makeText(MainActivity.getContextOfApplication(),"JSON Errors",Toast.LENGTH_LONG);
            Log.e("JSONException", jsonex.getMessage());
        }catch (NullPointerException ex){
            Toast.makeText(MainActivity.getContextOfApplication(),"No data!",Toast.LENGTH_LONG);
            Log.e("NullPointerException", ex.getMessage());
        }


//        if(response.getStatusCodeHttp() == HttpURLConnection.HTTP_OK){
//            try {
//                Toast.makeText(mContext, jsonObject.getString("key"), Toast.LENGTH_SHORT).show();
//            } catch (JSONException jsonex) {
//                Log.e("JSONException", jsonex.getMessage());
//            }
//        }else{
//            try {
//                Toast.makeText(mContext, jsonObject.getString("mensagem"), Toast.LENGTH_SHORT).show();
//            } catch (JSONException jsonex) {
//                Log.e("JSONException", jsonex.getMessage());
//            }
//        }
    }
}