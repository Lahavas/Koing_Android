package com.tourwith.koing.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by MSI on 2017-10-11.
 */
@Root(name="header")
public class TourInfoHeader {
    public TourInfoHeader(){

    }
    @Element(name="resultCode", required=false)
    private String resultCode;
    @Element(name="responseTime", required=false)
    private String responseTime;
    @Element(name="resultMsg", required=false)
    private String resultMsg;
    public String getResultCode(){
        return resultCode;
    }
    public String getResponseTime(){
        return responseTime;
    }
    public String getResultMsg(){
        return resultMsg;
    }
}
