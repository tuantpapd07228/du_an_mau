package com.example.duanmauu.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class TrangChuFragment extends Fragment {

    View view;

    Home_Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_trang_chu, container, false);
        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtubeView);
        activity = (Home_Activity) getActivity();
        Button xemgianhang = view.findViewById(R.id.gianhang);
        xemgianhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new SachFragment()).commit();
            }
        });
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                String idvideo = "T57GRogU2FI";
                youTubePlayer.loadVideo(idvideo,0);
            }
        });

//        getLifecycle().addObserver(binding.youtubePlayerView);
//        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                super.onReady(youTubePlayer);
//                String idvideo = "S0Q4gqBUs7c";
//                youTubePlayer.loadVideo(idvideo,0);
//            }
//        });

        return view;
    }


}