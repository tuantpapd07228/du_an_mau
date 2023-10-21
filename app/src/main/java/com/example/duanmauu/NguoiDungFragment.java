package com.example.duanmauu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duanmauu.data.GetIP;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.itf.SelectNguoidung;

import java.util.HashMap;
import java.util.Map;


public class NguoiDungFragment extends Fragment {
    com.example.duanmauu.model.NguoiDung nguoiDung;
    Button btnUD;
    ReadData readData;
    View view;
    Home_Activity activity;
    String url = GetIP.IP +":8686/duanmau/update_nguoidung.php";
    String urlgetnguoidung =GetIP.IP +":8686/duanmau/get_nguoidung.php";
    SelectNguoidung mselectNguoidung;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readData = new ReadData(getContext());
        activity = (Home_Activity) getActivity();
        nguoiDung = activity.getNguoidung();
        view = inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
        btnUD = view.findViewById(R.id.btnchangeinfo);
        setData();
        return view;
    }
    public void setData(){
        TextView name = view.findViewById(R.id.name);
        TextView username = view.findViewById(R.id.userName1);
        TextView diachi = view.findViewById(R.id.diachi1);
        TextView email = view.findViewById(R.id.email1);
        TextView phone = view.findViewById(R.id.sdt1);
        name.setText(nguoiDung.getHoten());
        username.setText(nguoiDung.getUserName());
        diachi.setText(nguoiDung.getDiachi());
        email.setText(nguoiDung.getEmail());
        phone.setText(nguoiDung.getPhone());
        btnUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.update_info);
                    EditText name = dialog.findViewById(R.id.name2);
                    EditText email = dialog.findViewById(R.id.email2);
                    EditText addrr = dialog.findViewById(R.id.adrress2);
                    EditText phone = dialog.findViewById(R.id.phone2);
                    Button btnud = dialog.findViewById(R.id.btnupdateinfo);
                    name.setText(nguoiDung.getHoten());
                    email.setText(nguoiDung.getEmail());
                    addrr.setText(nguoiDung.getDiachi());
                    phone.setText(nguoiDung.getPhone());

                    btnud.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || addrr.getText().toString().isEmpty()|| phone.getText().toString().isEmpty()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Vui lòng xem lại thông tin đã nhập!");
                                builder.setPositiveButton("OK", null);
                                builder.show();
                            }else {
                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (requestQueue != null) {
                                            Toast.makeText(getContext(), "ban da update thanh cong", Toast.LENGTH_SHORT).show();
                                            nguoiDung.setHoten(name.getText().toString().trim());
                                            nguoiDung.setEmail(email.getText().toString().trim());
                                            nguoiDung.setDiachi(addrr.getText().toString().trim());
                                            nguoiDung.setPhone(phone.getText().toString().trim());
                                            setData();
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("idnguoidung", nguoiDung.getId());
                                        map.put("hoten", name.getText().toString().trim());
                                        map.put("diachi", addrr.getText().toString().trim());
                                        map.put("email", email.getText().toString().trim());
                                        map.put("sdt", phone.getText().toString().trim());
                                        return map;
                                    }
                                };
                                requestQueue.add(stringRequest);
                            }
                        }
                    });
                    dialog.show();
                }catch (Exception e){
                    Log.e("EEEEEEEEEEEEEEEE", e.getMessage());
                }
            }
        });

    }


}