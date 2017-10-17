package com.tourwith.koing.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by MSI on 2017-10-11.
 */
@Root(name="body")
public class TourInfoBody {
    @Element(name="items")
    private TourInfoItems items;

    @Element(name="numOfRows", required=false)
    private String numOfRows;

    @Element(name="pageNo", required=false)
    private String pageNo;

    @Element(name="totalCount", required=false)
    private String totalCount;

    public TourInfoItems getItems(){
        return items;
    }
    public String getNumOfRows(){
        return numOfRows;
    }
    public String getPageNo(){
        return pageNo;
    }
    public String getTotalCount(){
        return totalCount;
    }
    public TourInfoBody(){
        items = new TourInfoItems();
    }
}
