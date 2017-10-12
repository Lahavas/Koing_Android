package com.tourwith.koing.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by MSI on 2017-09-23.
 */

@Root(name="response")
public class TourInfoResponse {
    @Element(name="header")
    private TourInfoHeader header;
    @Element(name="body", required=false)
    private TourInfoBody body;

    public TourInfoBody getBody(){
        return body;
    }

    public TourInfoResponse(){
        body = new TourInfoBody();
    }
}



