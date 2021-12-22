package com.parcelwala.parcelwalarider.Activity.PaidAmount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parcelwala.parcelwalarider.Adaptar.PaidAmount.PaidAmountAdaptar;
import com.parcelwala.parcelwalarider.Model.PaidAmount.PaidAmountContainers;
import com.parcelwala.parcelwalarider.Network.Api;
import com.parcelwala.parcelwalarider.Network.RetrofitClient;
import com.parcelwala.parcelwalarider.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaidAmountActivity extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ConstraintLayout nodatafound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_amount);

        recyclerView = findViewById(R.id.rv_pickup_list);
        nodatafound=findViewById(R.id.nodatafound);
        TextView toolbar = findViewById(R.id.tv_toolbar_title);
        toolbar.setText("Paid Parcel List");
        ImageView toolbarBack = findViewById(R.id.tv_back);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(PaidAmountActivity.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
       progressDialog.show();
        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        api.getPaidparcel().enqueue(new Callback<PaidAmountContainers>() {
            @Override
            public void onResponse(Call<PaidAmountContainers> call, Response<PaidAmountContainers> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                   // Log.e("ddd", response.body().getParcels().get(0).getAreaName());
                    if(response.body().getParcels().size()>0) {
                        progressDialog.dismiss();
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        PaidAmountAdaptar adaptar = new PaidAmountAdaptar(response.body().getParcels(), getApplicationContext());
                        recyclerView.setAdapter(adaptar);
                        Log.e("ddd", response.body().getParcels().get(0).getAreaName());
                    }else{
                        nodatafound.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    nodatafound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PaidAmountContainers> call, Throwable t) {
                progressDialog.dismiss();
                nodatafound.setVisibility(View.VISIBLE);
            }
        });
    }
}