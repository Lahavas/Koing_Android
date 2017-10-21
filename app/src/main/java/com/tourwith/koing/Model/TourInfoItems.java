package com.tourwith.koing.Model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by MSI on 2017-10-11.
 */
@Root(name="items")
public class TourInfoItems {
    @ElementList(name="item", inline=true, required=false)
    private List<TourInfoItem> item;

    public List<TourInfoItem> getItem(){
        return item;
    }
    public TourInfoItems(){

    }
}
