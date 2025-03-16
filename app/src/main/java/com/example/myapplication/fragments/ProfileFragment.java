package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.view.authentication.Register;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private Button btnItRgt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnItRgt = view.findViewById(R.id.btnItRgt);
        if (btnItRgt == null) {
            Log.e("ProfileFragment", "btnItRgt is null");
        } else {
            btnItRgt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() == null) {
                        Log.e("ProfileFragment", "Activity is null");
                        return;
                    }
                    Intent intent = new Intent(getActivity(), Register.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}