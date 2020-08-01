package com.erwin.pantaucovid19.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erwin.pantaucovid19.R;
import com.erwin.pantaucovid19.adapter.HistoryListAdapter;
import com.erwin.pantaucovid19.model.RiwayatModel;
import com.erwin.pantaucovid19.viewmodel.RiwayatViewModel;
import com.erwin.pantaucovid19.adapter.HistoryListAdapter;
import com.erwin.pantaucovid19.model.RiwayatModel;
import com.erwin.pantaucovid19.viewmodel.RiwayatViewModel;

import java.util.ArrayList;

public class RiwayatFragment extends Fragment {

    private HistoryListAdapter adapter;
    private ProgressDialog mProgressApp;

    public RiwayatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        mProgressApp = new ProgressDialog(getActivity());
        mProgressApp.setTitle("Mohon tunggu");
        mProgressApp.setCancelable(false);
        mProgressApp.setMessage("Sedang menampilkan data...");

        RecyclerView recyclerView = view.findViewById(R.id.listRecycler);
        adapter = new HistoryListAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        loadListData();
        return view;
    }

    private void loadListData() {
        RiwayatViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(RiwayatViewModel.class);
        viewModel.setTodayData();
        mProgressApp.show();
        viewModel.getTodayListData().observe(this, new Observer<ArrayList<RiwayatModel>>() {
            @Override
            public void onChanged(ArrayList<RiwayatModel> riwayatModels) {
                adapter.setRiwayatModels(riwayatModels);
                mProgressApp.dismiss();
            }
        });
    }
}
