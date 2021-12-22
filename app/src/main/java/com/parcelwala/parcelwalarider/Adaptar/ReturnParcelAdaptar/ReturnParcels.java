package com.parcelwala.parcelwalarider.Adaptar.ReturnParcelAdaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parcelwala.parcelwalarider.Adaptar.listener.ReturnMenuClickListner;
import com.parcelwala.parcelwalarider.Model.Return.ReturnParcel;
import com.parcelwala.parcelwalarider.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReturnParcels extends RecyclerView.Adapter<ReturnParcels.Viewholders> {

    public ReturnParcels.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    List<ReturnParcel> returnS = new ArrayList<>();
    Context context;

    public ReturnParcels(List<ReturnParcel> returnS, Context context,OnItemClickListener listener) {
        this.returnS = returnS;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_parcel, parent, false);
        return new ReturnParcels.Viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Viewholders holder, int position) {
        holder.Invoice.setText(String.valueOf(returnS.get(position).getParcelInvoice()));
        holder.CustomerName.setText(String.valueOf(returnS.get(position).getCustomerName()));
        holder.CustomerPhn.setText(String.valueOf(returnS.get(position).getCustomerContactNumber()));
        holder.CustomerAddress.setText(String.valueOf(returnS.get(position).getCustomerAddress()));
        holder.TotalCollectAmount.setText("Collection Amount :" + String.valueOf(returnS.get(position).getTotalCollectAmount()));
        holder.MerchantName.setText("Merchant Name :" + String.valueOf(returnS.get(position).getMerchantName()));
        holder.ParcelStatus.setText(String.valueOf(returnS.get(position).getParcelStatus()));
        holder.optionMenu.setOnClickListener(new ReturnMenuClickListner(this,listener,holder,returnS.get(position)));
    }

    @Override
    public int getItemCount() {
        return returnS.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder {
        TextView Invoice, CustomerName, CustomerPhn, CustomerAddress, TotalCollectAmount, MerchantName, ParcelStatus;
        ImageView optionMenu;
        public Viewholders(@NonNull @NotNull View itemView) {
            super(itemView);

            Invoice = itemView.findViewById(R.id.tv_invoice);
            CustomerName = itemView.findViewById(R.id.tv_customer_name);
            CustomerPhn = itemView.findViewById(R.id.tv_phone);
            CustomerAddress = itemView.findViewById(R.id.tv_address);
            TotalCollectAmount = itemView.findViewById(R.id.tv_amount);
            MerchantName = itemView.findViewById(R.id.tv_merchant_name);
            ParcelStatus = itemView.findViewById(R.id.tv_status);
            optionMenu = itemView.findViewById(R.id.iv_menu);
        }

        public ImageView getoption() {
            return this.optionMenu;
        }
    }

}
