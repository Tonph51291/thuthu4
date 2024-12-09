package com.example.thuthu4.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuthu4.DTO.Respondata;
import com.example.thuthu4.DTO.XeMayDTO;
import com.example.thuthu4.R;
import com.example.thuthu4.Service.HttpService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XeMayAdapter extends RecyclerView.Adapter<XeMayAdapter.ViewHolder> {

    private List<XeMayDTO> listXeMay;
    private Context context;

    public XeMayAdapter(List<XeMayDTO> listXeMay, Context context) {
        this.listXeMay = listXeMay;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        XeMayDTO xeMayDTO = listXeMay.get(position);
        holder.txt_tenxe.setText(xeMayDTO.getTen_xe());
        holder.txt_gia.setText(xeMayDTO.getGia_ban() + "");
        Picasso.get().load(xeMayDTO.getHinh_anh()).into(holder.img_xe);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_xe, null);
                dialog.setView(view);
                dialog.show();
                TextView tv_tenxe = view.findViewById(R.id.tv_tenxe_dialog);
                TextView tv_giaban = view.findViewById(R.id.tv_giaban_dialog);
                TextView tv_mota = view.findViewById(R.id.tv_mota_dialog);
                ImageView img_dialog = view.findViewById(R.id.img_dialog);
                tv_tenxe.setText(xeMayDTO.getTen_xe());
                tv_giaban.setText(xeMayDTO.getGia_ban() + "");
                tv_mota.setText(xeMayDTO.getMo_ta());
                Picasso.get().load(xeMayDTO.getHinh_anh()).into(img_dialog);


            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Xác nhận xóa");
                dialog.setMessage("Bạn có chắc chắn muốn xóa không?");
                dialog.setPositiveButton("Có", (dialog1, which) -> {
                    HttpService httpService = new HttpService();
                    httpService.getApiService().deleteXe(xeMayDTO.get_id()).enqueue(new Callback<Respondata<XeMayDTO>>() {
                        @Override
                        public void onResponse(Call<Respondata<XeMayDTO>> call, Response<Respondata<XeMayDTO>> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                listXeMay.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Không chạy vào", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Respondata<XeMayDTO>> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    });
                });
                dialog.setNegativeButton("Không", (dialog1, which) -> {
                });
                dialog.show();


            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null);
                dialog.setView(view);
                dialog.show();
                EditText tv_tenxe = view.findViewById(R.id.tv_tenxe_add);
                EditText tv_giaban = view.findViewById(R.id.tv_giaban_add);
                EditText tv_mota = view.findViewById(R.id.tv_mota_add);
                EditText tv_mausac = view.findViewById(R.id.tv_mausac_add);
                EditText tv_hinhanh = view.findViewById(R.id.tv_hinhanh_add);
                Button btn_add_dialog = view.findViewById(R.id.btn_add_dialog);
                tv_tenxe.setText(xeMayDTO.getTen_xe());
                tv_giaban.setText(xeMayDTO.getGia_ban() + "");
                tv_mota.setText(xeMayDTO.getMo_ta());
                tv_mausac.setText(xeMayDTO.getMau_sac());
                tv_hinhanh.setText(xeMayDTO.getHinh_anh());

                btn_add_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenxe = tv_tenxe.getText().toString();
                        int giaban = Integer.parseInt(tv_giaban.getText().toString());
                        String mota = tv_mota.getText().toString();
                        String mausac = tv_mausac.getText().toString();
                        String hinhanh = tv_hinhanh.getText().toString();
                        xeMayDTO.setTen_xe(tenxe);
                        xeMayDTO.setGia_ban(giaban);
                        xeMayDTO.setMo_ta(mota);
                        xeMayDTO.setMau_sac(mausac);
                        xeMayDTO.setHinh_anh(hinhanh);

                        HttpService httpService = new HttpService();
                        httpService.getApiService().updateXe(xeMayDTO.get_id(), xeMayDTO).enqueue(new Callback<Respondata<XeMayDTO>>() {
                            @Override
                            public void onResponse(Call<Respondata<XeMayDTO>> call, Response<Respondata<XeMayDTO>> response) {
                                if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {


                                    listXeMay.set(position, xeMayDTO);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<Respondata<XeMayDTO>> call, Throwable t) {
                                Log.d("AAA", t.getMessage());


                            }

                        });


                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return listXeMay.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenxe, txt_gia;
        ImageView img_xe;
        ImageView btn_delete, btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenxe = itemView.findViewById(R.id.txt_tenxe);
            txt_gia = itemView.findViewById(R.id.txt_giaban);
            img_xe = itemView.findViewById(R.id.img_anh);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
