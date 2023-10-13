package com.example.duanmauu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmauu.databinding.ActivityHomeBinding;
import com.example.duanmauu.fragment.GioHangFragment;
import com.example.duanmauu.fragment.HoaDonFragment;
import com.example.duanmauu.fragment.SachFragment;
import com.example.duanmauu.fragment.TheLoaiSach_Fragment;
import com.example.duanmauu.fragment.TrangChuFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class Home_Activity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ActivityHomeBinding binding;
    Fragment fragment = null;
//    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getNguoidung();

        try {
//            getUSGG();
        }catch (Exception e){}
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Home_Activity.this,
                binding.drawlayout,
                binding.toolbar,
                R.string.open,
                R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);

        TextView tenuser = headerView.findViewById(R.id.tvuser);


        tenuser.setText(getNguoidung().getHoten());
        binding.drawlayout.addDrawerListener(toggle);
        binding.toolbar.setTitle("Trang chủ");
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home1:
                        fragment = new TrangChuFragment();
                        binding.toolbar.setTitle("Trang chủ");
                        break;
                    case R.id.menu_info:
                        fragment = new NguoiDung();
                        binding.toolbar.setTitle("Thông tin cá nhân");
                        break;
                    case R.id.menu_home:
                        fragment = new SachFragment();
                        binding.toolbar.setTitle("Gian hàng sách");
                        break;
                    case R.id.menu_theloai:
                        fragment = new TheLoaiSach_Fragment();
                        binding.toolbar.setTitle("Các thể loại sách");
                        break;
                    case R.id.menu_cart:
                        fragment = new GioHangFragment();
                        binding.toolbar.setTitle("Giỏ hàng");
                        break;
                    case R.id.menu_xemhoadon:
                        Intent intent = new Intent(Home_Activity.this, DonMuaActivity.class);
                        intent.putExtra("nguoidung", getNguoidung());
                        startActivity(intent);
//                        fragment = new HoaDonFragment();
//                        binding.toolbar.setTitle("Đơn hàng");
                        break;
                    case R.id.menu_logout:
                        logout();
                        break;
                }
                try {
                    binding.drawlayout.close();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, fragment);
                    fragmentTransaction.commit();
                }catch (Exception e){}
                return true;
            }
        });
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.framelayout, new TrangChuFragment()).commit();

    }
    public com.example.duanmauu.model.NguoiDung getNguoidung(){
        Intent intent = getIntent();
        com.example.duanmauu.model.NguoiDung nguoiDung = (com.example.duanmauu.model.NguoiDung) intent.getSerializableExtra("nguoidung");
        return nguoiDung;
    }



    private void logout(){
        try {
            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                    gsc = GoogleSignIn.getClient(getApplicationContext(), gso);
                    GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    try {
                        finish();
                        startActivity(new Intent(Home_Activity.this, MainActivity.class));
                    }catch (Exception e){
                        Toast.makeText(Home_Activity.this, "Some thing wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            finish();
            startActivity(new Intent(Home_Activity.this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(Home_Activity.this);
        menuInflater.inflate(R.menu.menu_cart_sach_theloai, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sach_toolbar:
                fragment = new SachFragment();
                binding.toolbar.setTitle("Gian hàng sách");
                break;
            case R.id.menu_theloai_toolbar:
                fragment = new TheLoaiSach_Fragment();
                binding.toolbar.setTitle("Các thể loại sách");
                break;
            case R.id.menu_giohang_toolbar:
                fragment = new GioHangFragment();
                binding.toolbar.setTitle("Giỏ hàng");
                break;
        }
        try {
            binding.drawlayout.close();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, fragment);
            fragmentTransaction.commit();
        }catch (Exception e){}
        return super.onOptionsItemSelected(item);
    }
}