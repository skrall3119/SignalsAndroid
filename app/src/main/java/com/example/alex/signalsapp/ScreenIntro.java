package com.example.alex.signalsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScreenIntro extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final Button chordNav = view.findViewById(R.id.chord_nav);
        final Button borrowNav = view.findViewById(R.id.borrow_nav);

        View.OnClickListener handler = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v==chordNav){
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChordsFragment()).commit();

                }
                if(v==borrowNav){
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BorrowedFragment()).commit();
                }
            }
        };
        chordNav.setOnClickListener(handler);
        borrowNav.setOnClickListener(handler);


        return view;
    }
}
