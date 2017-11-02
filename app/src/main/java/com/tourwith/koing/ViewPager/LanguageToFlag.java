package com.tourwith.koing.ViewPager;

import android.content.Context;

import com.tourwith.koing.R;

import java.util.HashMap;

/**
 * Created by Munak on 2017. 10. 30..
 */

/*
    <string-array name="language">
        <item >English</item>
        <item >Korean</item>
        <item >Japanese</item>
        <item >Chinese</item>
        <item >Spanish</item>
        <item >French</item>
    </string-array>
 */


public class LanguageToFlag {

    Context context;
    String[] nations;
    HashMap<String, Integer> natToFlagMap;

    public LanguageToFlag(Context context) {
        this.context = context;

        nations = new String[7];

        nations = context.getResources().getStringArray(R.array.nation);

        natToFlagMap = new HashMap();

        natToFlagMap.put(nations[0],R.drawable.ko);
        natToFlagMap.put(nations[1],R.drawable.cn);
        natToFlagMap.put(nations[2],R.drawable.us);
        natToFlagMap.put(nations[3],R.drawable.jp);
        natToFlagMap.put(nations[4],R.drawable.es);
        natToFlagMap.put(nations[5],R.drawable.fr);
        natToFlagMap.put(nations[6],R.drawable.gb);

    }

    public int Converter(String language) {
        return natToFlagMap.get(language);
    }


}
