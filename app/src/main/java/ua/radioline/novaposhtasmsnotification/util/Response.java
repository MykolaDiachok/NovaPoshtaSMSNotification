package ua.radioline.novaposhtasmsnotification.util;

/**
 * Created by mikoladyachok on 12/30/15.
 */
public class Response {
    private int statusCodeHttp;


    private String contentValue;


    public Response(int statusCodeHttp, String contentValue) {
        this.statusCodeHttp = statusCodeHttp;
        this.contentValue = contentValue;
    }


    public int getStatusCodeHttp() {
        return statusCodeHttp;
    }


    public void setStatusCodeHttp(int statusCodeHttp) {
        this.statusCodeHttp = statusCodeHttp;
    }


    public String getContentValue() {
        return contentValue;
    }


    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }
}