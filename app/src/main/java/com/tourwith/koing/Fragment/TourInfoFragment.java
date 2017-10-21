package com.tourwith.koing.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tourwith.koing.CardSlider.CardSnapHelper;
import com.tourwith.koing.CardSlider.SliderAdapter;
import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.Model.TourInfoItem;
import com.tourwith.koing.Model.TourInfoResponse;
import com.tourwith.koing.R;
import com.tourwith.koing.TourAPI.GpsInfoService;
import com.tourwith.koing.TourAPI.MatchParsingSource;
import com.tourwith.koing.TourAPI.TourInfoRetrofitService;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class TourInfoFragment extends Fragment {

    private String baseUrl;
    private TextView textView3;
    private String korKey;
    private String engKey;
    private String decodedKorKey;
    private String decodedEngKey;
    private List<TourInfoItem> mItems;
    private View view;

    public TourInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tour_info, container, false);

        baseUrl = getString(R.string.retrofitBaseURL);
        korKey = getString(R.string.tourAPIKeyKor);
        engKey = getString(R.string.tourAPIKeyEng);
        try {
            decodedKorKey = URLDecoder.decode(korKey, "UTF-8");
            decodedEngKey = URLDecoder.decode(engKey, "UTF-8");
        }catch(Exception e){

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        TourInfoRetrofitService retrofitService = retrofit.create(TourInfoRetrofitService.class);
        //영문 관광정보 받아오기

        Call<TourInfoResponse> call = retrofitService.
                getEngTripInfo(decodedEngKey,
                        "78",
                        "1",
                        "","","","",
                        "Y",
                        "AND",
                        "Koing",
                        "P",
                        "24",
                        "1"
                );

        /*

        //한글 관광정보 받아오기
        Call<TourInfoResponse> call = retrofitService.
                getKorTripInfo(decodedKorKey,
                        MatchParsingSource.getQueryNum("문화시설"), //contentTypeId
                        MatchParsingSource.getQueryNum("서울"), //areaCode;
                        "","","","", //sigunguCode, cat1, cat2, cat3
                        "Y", //
                        "AND", //MobileOS
                        "Koing", //MobileApp
                        "B", //arrange
                        "24", //numOfRows
                        "1" //pageNo
                );
*/

        /*
        //영문 현재 위치 근처 관광정보 받아오기
        double latitude=37.568477;
        double longitude=126.981106;
        GpsInfoService gps = new GpsInfoService(getContext());
        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        */


        //한글 현재 위치 근처 관광정보 받아오기
/*
        double latitude=37.568477;
        double longitude=126.981106;
        GpsInfoService gps = new GpsInfoService(getContext());
        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        textView3.append("lat:" + lat + "\nlong:"+lon +"\n\n\n");
*/
/*
        Call<TourInfoResponse> call = retrofitService.getEngTripInfo2(decodedEngKey,
                MatchParsingSource.getQueryNum("Cultural Facilities"),
                lon,//mapX
                lat,//mapY
                "2000",
                "Y",
                "AND",
                "Koing",
                "P",
                "24",
                "1"
        );

*/


        call.enqueue(new Callback<TourInfoResponse>() {
            @Override
            public void onResponse(Call<TourInfoResponse> call, Response<TourInfoResponse> response) {
                TourInfoResponse r = response.body();
                if(r.getBody().getItems().getItem() != null) {
                    mItems = r.getBody().getItems().getItem();

                    SliderAdapter sliderAdapter = new SliderAdapter(20, null, getContext(), mItems);

                    RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
                    recyclerView.setHasFixedSize(true);

                    recyclerView.setAdapter(sliderAdapter);

                    new CardSnapHelper().attachToRecyclerView(recyclerView);
                }

            }

            @Override
            public void onFailure(Call<TourInfoResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "정보받아오기 실패" , Toast.LENGTH_SHORT).show();
            }
        });

        /*
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        */

        //RecyclerView.Adapter adapter = new HomeRecyclerAdapter(view.getContext(), items);
        //recyclerView.setAdapter(adapter);

        /*
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        */


        return view;
    }
}
