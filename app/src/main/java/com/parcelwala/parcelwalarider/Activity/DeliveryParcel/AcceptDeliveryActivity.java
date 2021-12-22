package com.parcelwala.parcelwalarider.Activity.DeliveryParcel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parcelwala.parcelwalarider.Adaptar.DeliveryParcelListAdaptar.AcceptDeliveryAdaptar;
import com.parcelwala.parcelwalarider.Model.DeliveryParcel.DeliveryContainer;
import com.parcelwala.parcelwalarider.Model.DeliveryParcel.DeliveryParcel;
import com.parcelwala.parcelwalarider.Network.Api;
import com.parcelwala.parcelwalarider.Network.RetrofitClient;
import com.parcelwala.parcelwalarider.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptDeliveryActivity extends AppCompatActivity {


    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ConstraintLayout nodatafound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_delivery);

        recyclerView = findViewById(R.id.rv_pickup_list);

        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Accept Delivery Parcel");
        ImageView toolbarBack = findViewById(R.id.tv_back);
        nodatafound = findViewById(R.id.nodatafound);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(AcceptDeliveryActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        dataInitilize();

    }

    private void dataInitilize() {

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        progressDialog.show();
        api.getDeliveryparcel().enqueue(new Callback<DeliveryContainer>() {
            @Override
            public void onResponse(Call<DeliveryContainer> call, Response<DeliveryContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {


                    List<DeliveryParcel> parcelInfos = new ArrayList<>();
                    for (int i = 0; i < response.body().getParcels().size(); i++) {
                        DeliveryParcel orderInfo = response.body().getParcels().get(i);
                        if (orderInfo.getStatus() == 19) {
                            parcelInfos.add(orderInfo);
                        }
                    }
                    if (parcelInfos.size() > 0) {
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        AcceptDeliveryAdaptar adaptar = new AcceptDeliveryAdaptar(parcelInfos, getApplicationContext(), new AcceptDeliveryAdaptar.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                dataInitilize();
                            }
                        });
                        recyclerView.setAdapter(adaptar);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        nodatafound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryContainer> call, Throwable t) {
                progressDialog.dismiss();
                //recyclerView.setVisibility(View.INVISIBLE);
                nodatafound.setVisibility(View.VISIBLE);
            }
        });
    }
}