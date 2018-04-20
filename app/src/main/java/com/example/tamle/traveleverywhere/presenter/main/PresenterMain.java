package com.example.tamle.traveleverywhere.presenter.main;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.ComplexPlace;

import java.util.ArrayList;

/**
 * Created by tamle on 07/04/2018.
 */

public class PresenterMain {

    private ViewHandleMain mViewHandleMain;

    public PresenterMain(ViewHandleMain viewHandleMain){
        this.mViewHandleMain = viewHandleMain;
    }

    public void createListComplexPlace(){
        ArrayList<ComplexPlace> arrListDomesticComplexPlace = new ArrayList<>();
        arrListDomesticComplexPlace.add(new ComplexPlace("ChIJ0T2NLikpdTERKxE8d61aX_E", R.drawable.image_hcm, "Hồ Chí Minh", "Thành phố không ngủ"));
        arrListDomesticComplexPlace.add(new ComplexPlace("ChIJ9QxPVdRvdTERQPpB9jvST7I", R.drawable.image_vung_tau, "Vũng Tàu", "Du lịch, nghĩ dưỡng"));
        arrListDomesticComplexPlace.add(new ComplexPlace("ChIJsYgJ8v4ScTERMPkrZ4siX60", R.drawable.image_da_lat, "Đà Lạt", "Thành phố mộng mơ"));
        arrListDomesticComplexPlace.add(new ComplexPlace("ChIJb4jMEXhncDERudweqAq8S1w", R.drawable.image_nha_trang, "Nha Trang", "Thành phố biển"));
        arrListDomesticComplexPlace.add(new ComplexPlace("ChIJEyolkscZQjERBn5yhkvL8B0", R.drawable.image_da_nang, "Đà Nẵng", "Thành phố đáng sống"));
        arrListDomesticComplexPlace.add(new ComplexPlace("ChIJtV5TN4xXSjERO6ZIAuhUR_4", R.drawable.image_vhl, "Vịnh Hạ Long", "Di sản thế giới"));

        ArrayList<ComplexPlace> arrListWorldComplexPlace = new ArrayList<>();
        arrListWorldComplexPlace.add(new ComplexPlace("ChIJdd4hrwug2EcRmSrV3Vo6llI", R.drawable.image_london, "Luân Đôn", "Thành phố sương mù"));
        arrListWorldComplexPlace.add(new ComplexPlace("ChIJybDUc_xKtUYRTM9XV8zWRD0", R.drawable.image_moscow, "Moscow", "Thành phố vĩ đại"));
        arrListWorldComplexPlace.add(new ComplexPlace("ChIJD7fiBh9u5kcRYJSMaMOCCwQ", R.drawable.image_paris, "Paris", "Thành phố tình yêu"));
        arrListWorldComplexPlace.add(new ComplexPlace("ChIJw0rXGxGKJRMRAIE4sppPCQM", R.drawable.image_roma, "Rome", "Biểu tượng của nước Ý"));
        arrListWorldComplexPlace.add(new ComplexPlace("ChIJdZOLiiMR2jERxPWrUs9peIg", R.drawable.image_singapore, "Singapore", "Đất nước xinh đẹp"));


        mViewHandleMain.OnCreateListSuccessful(arrListDomesticComplexPlace, arrListWorldComplexPlace);

    }

}
