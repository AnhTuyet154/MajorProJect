package com.example.quanlyactivity.retrofit;

import com.example.quanlyactivity.model.NotiResponse;
import com.example.quanlyactivity.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    @Headers({
            "Content-Type: application/json",
                "Authorization: key=AAAAQf1k_SQ:APA91bEo6EZP6fvZLfo5yOvTZWWczbdpQqBm4P9FCaNXtkeKyQ6wpkLK_J_ejWv0yr88PIp_M91MObgUWGmkp9eNLTA7wEzlJvfPdZuZ0ByNm_dmnUliIeGoNl1Ps3FLQVfahsD_UF8h"
    })
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
