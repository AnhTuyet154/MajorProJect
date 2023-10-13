package com.example.triostream.retrofit;

import com.example.triostream.model.LoaiSpModel;
import retrofit2.http.GET;
import io.reactivex.rxjava3.core.Observable;

public interface TriostreamApp {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
}
