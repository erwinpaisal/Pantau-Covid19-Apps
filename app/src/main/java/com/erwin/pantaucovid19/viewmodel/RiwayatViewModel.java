package com.erwin.pantaucovid19.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.erwin.pantaucovid19.api.ApiEndPoint;
import com.erwin.pantaucovid19.api.ApiService;
import com.erwin.pantaucovid19.model.RiwayatModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RiwayatViewModel extends ViewModel {

    private MutableLiveData<ArrayList<RiwayatModel>> mutableLiveData = new MutableLiveData<>();

    public void setTodayData() {
        Retrofit retrofit = ApiService.getRetrofitService();
        ApiEndPoint apiEndpoint = retrofit.create(ApiEndPoint.class);

        Call<List<RiwayatModel>> call = apiEndpoint.getHistoryList(getFormattedDate());
        call.enqueue(new Callback<List<RiwayatModel>>() {
            @Override
            public void onResponse(Call<List<RiwayatModel>> call, Response<List<RiwayatModel>> response) {
                mutableLiveData.setValue((ArrayList<RiwayatModel>) response.body());
            }

            @Override
            public void onFailure(Call<List<RiwayatModel>> call, Throwable t) {

            }
        });
    }

    private String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        return simpleDateFormat.format(yesterday());
    }

    // fetch hari kemarin
    private Date yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public LiveData<ArrayList<RiwayatModel>> getTodayListData() {
        return mutableLiveData;
    }
}
