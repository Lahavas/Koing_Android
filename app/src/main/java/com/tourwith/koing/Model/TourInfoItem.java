package com.tourwith.koing.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by MSI on 2017-10-11.
 */
@Root(name="item")
public class TourInfoItem {
    @Element(name="addr1", required=false)
    private String addr1;

    @Element(name="title", required=false)
    private String title;

    @Element(name="firstimage", required=false)
    private String firstimage;

    @Element(name="firstimage2", required=false)
    private String firstimage2;

    @Element(name="mapx", required=false)
    private String mapx;

    @Element(name="mapy", required=false)
    private String mapy;

    @Element(name="addr2", required=false)
    private String addr2;

    @Element(name="areacode", required=false)
    private String areacode;

    @Element(name="cat1", required=false)
    private String cat1;

    @Element(name="cat2", required=false)
    private String cat2;

    @Element(name="cat3", required=false)
    private String cat3;

    @Element(name="contentid", required=false)
    private String contentid;

    @Element(name="contenttypeid", required=false)
    private String contenttypeid;

    @Element(name="createdtime", required=false)
    private String createdtime;

    @Element(name="mlevel", required=false)
    private String mlevel;

    @Element(name="modifiedtime", required=false)
    private String modifiedtime;

    @Element(name="readcount", required=false)
    private String readcount;

    @Element(name="sigungucode", required=false)
    private String sigungucode;

    @Element(name="zipcode", required=false)
    private String zipcode;

    @Element(name="tel",required=false)
    private String tel;

    @Element(name="booktour",required=false)
    private String booktour;

    @Element(name="dist",required=false)
    private String dist;

    @Element(name="masterid",required=false)
    private String masterid;

    public TourInfoItem(){

    }

    public String getContentid(){
        return contentid;
    }
    public String getAddr1(){
        return addr1;
    }
    public String getAddr2(){
        return addr2;
    }
    public String getTitle(){
        return title;
    }
    public String getFirstimage(){
        return firstimage;
    }
    public String getMapx(){
        return mapx;
    }
    public String getMapy(){
        return mapy;
    }
    public String getAreacode(){ return areacode;}
}
