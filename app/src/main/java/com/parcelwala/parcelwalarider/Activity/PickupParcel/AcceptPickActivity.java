package com.parcelwala.parcelwalarider.Activity.PickupParcel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parcelwala.parcelwalarider.Adaptar.PickupParcelAdaptar.RequestParcelAdaptar;
import com.parcelwala.parcelwalarider.Model.PickUpParcel.Parcel;
import com.parcelwala.parcelwalarider.Model.PickUpParcel.PickupParcel;
import com.parcelwala.parcelwalarider.Network.Api;
import com.parcelwala.parcelwalarider.Network.RetrofitClient;
import com.parcelwala.parcelwalarider.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptPickActivity extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ConstraintLayout nodatafound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_pick);

        recyclerView = findViewById(R.id.rv_pickup_list);

        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Accept Pickup Parcel");
        ImageView toolbarBack = findViewById(R.id.tv_back);
        nodatafound = findViewById(R.id.nodatafound);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(AcceptPickActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);

        datainitialize();


    }

    public void datainitialize() {
        progressDialog.show();
        api.getparcel().enqueue(new Callback<PickupParcel>() {
            @Override
            public void onResponse(Call<PickupParcel> call, Response<PickupParcel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {

                    List<Parcel> parcelInfos = new ArrayList<>();
                    for (int i = 0; i < response.body().getParcels().size(); i++) {
                        Parcel orderInfo = response.body().getParcels().get(i);
                        if (orderInfo.getStatus() == 8) {
                            parcelInfos.add(orderInfo);
                        }
                    }
                    if (parcelInfos.size() > 0) {
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        RequestParcelAdaptar adaptar = new RequestParcelAdaptar(parcelInfos, getApplicationContext(),
                                new RequestParcelAdaptar.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        datainitialize();
                                    }
                                });
                        nodatafound.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adaptar);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        nodatafound.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    nodatafound.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<PickupParcel> call, Throwable t) {
                progressDialog.dismiss();
                // recyclerView.setVisibility(View.INVISIBLE);
                nodatafound.setVisibility(View.VISIBLE);
            }
        });
    }
}