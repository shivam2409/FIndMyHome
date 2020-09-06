package com.example.findmyhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment {

    @Nullable
    //@Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.home_fragament,
                container, false);
        return view;
    }

}
