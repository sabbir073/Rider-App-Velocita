package com.parcelwala.parcelwalarider.Adaptar.DeliveryParcelListAdaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parcelwala.parcelwalarider.Adaptar.listener.DeliveryAcceptMenuClickListener;
import com.parcelwala.parcelwalarider.Model.DeliveryParcel.DeliveryParcel;
import com.parcelwala.parcelwalarider.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AcceptDeliveryAdaptar extends RecyclerView.Adapter<AcceptDeliveryAdaptar.Viewholders> {
    List<DeliveryParcel> delivery = new ArrayList<>();
    Context context;
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public AcceptDeliveryAdaptar(List<DeliveryParcel> delivery, Context context, OnItemClickListener listener) {
        this.delivery = delivery;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_parcel, parent, false);
        return new AcceptDeliveryAdaptar.Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholders holder, int position) {

        holder.Invoice.setText(String.valueOf(delivery.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(delivery.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(delivery.get(position).getCustomerContactNumber()));
        holder.CustomerAddress.setText(String.valueOf(delivery.get(position).getCustomerAddress()));
        holder.TotalCollectAmount.setText("Collection Amount : " + String.valueOf(delivery.get(position).getCollectAmount()));
        holder.MerchantName.setText("Merchant Name : " + String.valueOf(delivery.get(position).getMerchantName()));
        holder.MerchantPhn.setText("Merchant Phone : " + String.valueOf(delivery.get(position).getMerchantContactNumber()));
        holder.MerchantAddress.setText("Merchant Address : " + String.valueOf(delivery.get(position).getMerchantAddress()));
        holder.ParcelStatus.setText(String.valueOf(delivery.get(position).getParcelStatus()));


      /*  holder.Invoice.setText(String.valueOf(delivery.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(delivery.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(delivery.get(position).getCustomerContactNumber()));
        holder.CustomerAddress.setText(String.valueOf(delivery.get(position).getCustomerAddress()));
        holder.TotalCollectAmount.setText("Collection Amount :" + String.valueOf(delivery.get(position).getCollectAmount()));
        holder.MerchantName.setText("Merchant Name :" + String.valueOf(delivery.get(position).getMerchantName()));
        holder.ParcelStatus.setText(String.valueOf(delivery.get(position).getParcelStatus()));*/
       // holder.optionMenu.setVisibility(View.GONE);
        holder.optionMenu.setOnClickListener(new DeliveryAcceptMenuClickListener(this, listener, holder, delivery.get(position)));
    }

    @Override
    public int getItemCount() {
        return delivery.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder {
        TextView Invoice, CustomerName,MerchantPhn,MerchantAddress, CustomerPhn, CustomerAddress, TotalCollectAmount, MerchantName, ParcelStatus;
        ImageView optionMenu;
        public Viewholders(@NonNull @NotNull View itemView) {
            super(itemView);


            Invoice = itemView.findViewById(R.id.tv_invoice);
            CustomerName = itemView.findViewById(R.id.tv_customer_name);
            CustomerPhn = itemView.findViewById(R.id.tv_phone);
            CustomerAddress = itemView.findViewById(R.id.tv_address);
            TotalCollectAmount = itemView.findViewById(R.id.tv_amount);
            MerchantName = itemView.findViewById(R.id.tv_merchant_name);
            MerchantPhn = itemView.findViewById(R.id.tv_merchant_no);
            ParcelStatus = itemView.findViewById(R.id.tv_status);
            MerchantAddress = itemView.findViewById(R.id.tv_merchant_address);
            optionMenu = itemView.findViewById(R.id.iv_menu);
        }
        public ImageView getoption() {
            return this.optionMenu;
        }
    }
}
