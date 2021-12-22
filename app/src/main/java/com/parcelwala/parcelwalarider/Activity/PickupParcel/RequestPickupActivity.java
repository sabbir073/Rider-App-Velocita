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

public class RequestPickupActivity extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ConstraintLayout nodatafound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_pickup);

        nodatafound=findViewById(R.id.nodatafound);
        recyclerView = findViewById(R.id.rv_pickup_list);

        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Request Pickup Parcel");
        ImageView toolbarBack = findViewById(R.id.tv_back);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        progressDialog = new ProgressDialog(RequestPickupActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        datainitialize();


    }

    @Override
    public void onResume() {
        super.onResume();
        datainitialize();

    }

    void datainitialize() {
        progressDialog.show();
        api = RetrofitClient.get(getApplicationContext()).create(Api.class);

        api.getparcel().enqueue(new Callback<PickupParcel>() {
            @Override
            public void onResponse(Call<PickupParcel> call, Response<PickupParcel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {

                    if(response.body().getParcels().size()>0) {
                        List<Parcel> parcelInfos = new ArrayList<>();
                        for (int i = 0; i < response.body().getParcels().size(); i++) {
                            Parcel orderInfo = response.body().getParcels().get(i);
                            if (orderInfo.getStatus() == 6) {
                                parcelInfos.add(orderInfo);
                            }
                        }
             // Log.d("tesst",response.body().getParcels().get(0).getTotalCollectAmount());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        RequestParcelAdaptar adaptar = new RequestParcelAdaptar(parcelInfos,
                                getApplicationContext(), new RequestParcelAdaptar.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                //  Toast.makeText(RequestPickupActivity.this, "sssssssss", Toast.LENGTH_SHORT).show();
                                datainitialize();
                            }
                        });
                        nodatafound.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adaptar);

                    }else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        nodatafound.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    recyclerView.setVisibility(View.INVISIBLE);
                    nodatafound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PickupParcel> call, Throwable t) {
                progressDialog.dismiss();
              //  recyclerView.setVisibility(View.INVISIBLE);
                nodatafound.setVisibility(View.VISIBLE);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}