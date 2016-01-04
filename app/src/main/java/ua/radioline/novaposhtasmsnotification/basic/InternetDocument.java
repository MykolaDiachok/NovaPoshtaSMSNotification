package ua.radioline.novaposhtasmsnotification.basic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mikoladyachok on 12/30/15.
 */
public class InternetDocument {

    public String IntDocNumber;
    public String PayerType;
    public String RecipientContactPhone;
    public String RecipientContactPerson;
    public String EstimatedDeliveryDate;
    public String RecipientAddressDescription;
    public String CityRecipientDescription;
    public String SeatsAmount;
    public String Cost;
    public String CostOnSite;
    public String Printed;
    public String StateName;
    public String DateTime;
    public String RecipientDescription;
    public boolean SendSMS;

    public InternetDocument(String intDocNumber, String payerType, String recipientContactPhone, String recipientContactPerson, String estimatedDeliveryDate, String recipientAddressDescription, String cityRecipientDescription, String seatsAmount, String cost, String costOnSite, String printed, String stateName, String dateTime, String recipientDescription) {
        IntDocNumber = intDocNumber;
        PayerType = payerType;
        RecipientContactPhone = recipientContactPhone;
        RecipientContactPerson = recipientContactPerson;
        EstimatedDeliveryDate = estimatedDeliveryDate;
        RecipientAddressDescription = recipientAddressDescription;
        CityRecipientDescription = cityRecipientDescription;
        SeatsAmount = seatsAmount;
        Cost = cost;
        CostOnSite = costOnSite;
        Printed = printed;
        StateName = stateName;
        DateTime = dateTime;
        RecipientDescription = recipientDescription;
        SendSMS = false;
    }




    // Constructor to convert JSON object into a Java class instance
    public InternetDocument(JSONObject object){
        try {



            this.IntDocNumber = object.getString("IntDocNumber");
            this.PayerType = object.getString("PayerType");
            this.RecipientContactPhone = "+"+object.getString("RecipientContactPhone").toString();
            this.RecipientContactPerson = object.getString("RecipientContactPerson");
            this.EstimatedDeliveryDate = object.getString("EstimatedDeliveryDate");
            this.RecipientAddressDescription =
                     object.getString("RecipientAddressDescription");

            this.CityRecipientDescription = object.getString("CityRecipientDescription");
            this.SeatsAmount = object.getString("SeatsAmount");
            this.Cost = object.getString("Cost");
            this.CostOnSite = object.getString("CostOnSite");
            this.Printed = object.getString("Printed");
            this.StateName = object.getString("StateName");
            this.DateTime = object.getString("DateTime");
            this.RecipientDescription = object.getString("RecipientDescription");
            this.SendSMS = false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<InternetDocument> fromJson(JSONArray jsonObjects) {
        ArrayList<InternetDocument> internetDocuments = new ArrayList<InternetDocument>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                internetDocuments.add(new InternetDocument(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return internetDocuments;
    }


}
