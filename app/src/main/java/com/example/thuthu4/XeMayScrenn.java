package com.example.thuthu4;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuthu4.Adapter.XeMayAdapter;
import com.example.thuthu4.DTO.Respondata;
import com.example.thuthu4.DTO.XeMayDTO;
import com.example.thuthu4.Service.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XeMayScrenn extends AppCompatActivity {
    ArrayList<XeMayDTO> list;
    RecyclerView rcv_xemay;
    Button btn_add;
    Dialog dialog;
    SearchView seach_xe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xe_may_screnn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rcv_xemay = findViewById(R.id.rcv_xemay);
        btn_add = findViewById(R.id.btn_add);
        seach_xe = findViewById(R.id.seach_xe);
        list = new ArrayList<>();
        rcv_xemay.setLayoutManager(new LinearLayoutManager(this));
        btn_add.setOnClickListener(v -> {
            AddXeMay();
        });
        seach_xe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String tenxe = seach_xe.getQuery().toString();
                HttpService httpService = new HttpService();
                httpService.getApiService().searchXe(tenxe).enqueue(new Callback<Respondata<List<XeMayDTO>>>() {
                    @Override
                    public void onResponse(Call<Respondata<List<XeMayDTO>>> call, Response<Respondata<List<XeMayDTO>>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
                            list.clear();
                            list.addAll(response.body().getData());
                            XeMayAdapter xeMayAdapter = new XeMayAdapter(list, XeMayScrenn.this);
                            rcv_xemay.setAdapter(xeMayAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<Respondata<List<XeMayDTO>>> call, Throwable t) {
                        Log.d("AAA", t.getMessage());

                    }
                });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String tenxe = seach_xe.getQuery().toString();
                if (newText.isEmpty()) {
                    list.clear();
                    LoadData();
                }
                HttpService httpService = new HttpService();
                httpService.getApiService().searchXe(tenxe).enqueue(new Callback<Respondata<List<XeMayDTO>>>() {
                    @Override
                    public void onResponse(Call<Respondata<List<XeMayDTO>>> call, Response<Respondata<List<XeMayDTO>>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
                            list.clear();
                            list.addAll(response.body().getData());
                            XeMayAdapter xeMayAdapter = new XeMayAdapter(list, XeMayScrenn.this);
                            rcv_xemay.setAdapter(xeMayAdapter);

                        }

                    }

                    @Override
                    public void onFailure(Call<Respondata<List<XeMayDTO>>> call, Throwable t) {
                        Log.d("AAA", t.getMessage());

                    }
                });

                return false;
            }
        });
        LoadData();
    }

    private void AddXeMay() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add);
        dialog.show();
        EditText edt_tenxe = dialog.findViewById(R.id.tv_tenxe_add);
        EditText edt_giaban = dialog.findViewById(R.id.tv_giaban_add);
        EditText edt_mota = dialog.findViewById(R.id.tv_mota_add);
        EditText edt_mausac = dialog.findViewById(R.id.tv_mausac_add);
        EditText edt_hinhanh = dialog.findViewById(R.id.tv_hinhanh_add);

        Button btn_add_dialog = dialog.findViewById(R.id.btn_add_dialog);
        btn_add_dialog.setOnClickListener(v -> {
            String tenxe = edt_tenxe.getText().toString();
            int giaban = Integer.parseInt(edt_giaban.getText().toString());
            String mota = edt_mota.getText().toString();
            String mausac = edt_mausac.getText().toString();
            String hinhanh = edt_hinhanh.getText().toString();

            XeMayDTO xeMayDTO = new XeMayDTO();
            xeMayDTO.setTen_xe(tenxe);
            xeMayDTO.setGia_ban(giaban);
            xeMayDTO.setMo_ta(mota);
            xeMayDTO.setMau_sac(mausac);
            xeMayDTO.setHinh_anh(hinhanh);

            HttpService httpService = new HttpService();
            httpService.getApiService().addXe(xeMayDTO).enqueue(new Callback<Respondata<XeMayDTO>>() {
                @Override
                public void onResponse(Call<Respondata<XeMayDTO>> call, Response<Respondata<XeMayDTO>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        list.clear();
                        Toast.makeText(XeMayScrenn.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        LoadData();
                    } else {
                        Toast.makeText(XeMayScrenn.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Respondata<XeMayDTO>> call, Throwable t) {
                    Log.d("AAA", t.getMessage());

                }
            });


        });


    }


    private void LoadData() {
        HttpService httpService = new HttpService();
        httpService.getApiService().getAllXe().enqueue(new Callback<Respondata<List<XeMayDTO>>>() {
            @Override
            public void onResponse(Call<Respondata<List<XeMayDTO>>> call, Response<Respondata<List<XeMayDTO>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    list.addAll(response.body().getData());
                    XeMayAdapter xeMayAdapter = new XeMayAdapter(list, XeMayScrenn.this);
                    rcv_xemay.setAdapter(xeMayAdapter);
                } else {
                    Toast.makeText(XeMayScrenn.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Respondata<List<XeMayDTO>>> call, Throwable t) {
                Log.d("AAA", t.getMessage());

            }
        });
    }

}