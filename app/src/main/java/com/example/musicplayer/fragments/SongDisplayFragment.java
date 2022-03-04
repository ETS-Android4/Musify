package com.example.musicplayer.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;
import com.example.musicplayer.model.MusicPlayer;
import com.example.musicplayer.model.Song;


public class SongDisplayFragment extends Fragment {

    private static final String ARG_SONG_RES_ID = "song_res_id";
    private static final String ARG_IMG_AS_STRING_BASE64 = "img_as_string_base64";
    private static final String ARG_SONG = "song";
    private static final String ARG_SINGER = "singer";
    private static final String ARG_DURATION = "duration";
    private static final String ARG_URI = "uri";

    MusicPlayer musicPlayer = MusicPlayer.getInstance();

    ImageView imageIv, exitIv;
    TextView songTv, singerTv;
    CheckBox playPauseCheckBox;

    private int mSongResId;
    private String mImgAsStringBase64 ,mSong, mSinger, mDuration, mUri;

    public static SongDisplayFragment newInstance(Song song) {

        SongDisplayFragment fragment = new SongDisplayFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SONG_RES_ID, song.getSongResId());
        args.putString(ARG_IMG_AS_STRING_BASE64, song.getImgAsStringBase64());
        args.putString(ARG_SONG, song.getSong());
        args.putString(ARG_SINGER, song.getSinger());
        args.putString(ARG_DURATION, song.getDuration());
        args.putString(ARG_URI, song.getUri());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSongResId = getArguments().getInt(ARG_SONG_RES_ID);
            mImgAsStringBase64 = getArguments().getString(ARG_IMG_AS_STRING_BASE64);
            mSong = getArguments().getString(ARG_SONG);
            mSinger = getArguments().getString(ARG_SINGER);
            mDuration = getArguments().getString(ARG_DURATION);
            mUri = getArguments().getString(ARG_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageIv = view.findViewById(R.id.imageview_display_image);

        // String empty = no picture was manually taken -> load songResId
        // else = picture was taken -> load from Base64 String.
        if (mImgAsStringBase64.equals("")) {
            imageIv.setImageResource(mSongResId);
        } else {
            // Convert Base64 String back to image representation.
            // Decode Base64 String
            byte[] bytes = Base64.decode(mImgAsStringBase64, Base64.DEFAULT);
            // Initialize Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            // Set Bitmap on ImageView as a round picture.
            Glide.with(this).load(bitmap).into(imageIv);
        }

        songTv = view.findViewById(R.id.textview_display_song);
        songTv.setText(mSong);

        singerTv = view.findViewById(R.id.textview_display_singer);
        singerTv.setText(mSinger);

        exitIv = view.findViewById(R.id.imageview_display_exit);
        exitIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        playPauseCheckBox = view.findViewById(R.id.imageview_display_play_pause);
        playPauseCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playPauseCheckBox.isChecked()) {
                    musicPlayer.proceed();
                } else {
                    musicPlayer.pause();
                }
            }
        });
    }
}