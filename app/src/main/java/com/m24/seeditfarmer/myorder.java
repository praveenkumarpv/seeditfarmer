package com.m24.seeditfarmer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import helperclass.orderupdater;

import static android.content.Context.MODE_PRIVATE;


public class myorder extends Fragment {
    String userid;
    RecyclerView orderrecycler;
    FirestoreRecyclerAdapter orderadapter;
    private FirebaseFirestore db;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public myorder() {
        // Required empty public constructor
    }

    public static myorder newInstance(String param1, String param2) {
        myorder fragment = new myorder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorder, container, false);
        SharedPreferences settings =getActivity().getSharedPreferences("preference", MODE_PRIVATE);
        userid = settings.getString("uid","");
        Query myorder = FirebaseFirestore.getInstance().collection("userdata").document(userid).collection("Order");
        FirestoreRecyclerOptions<orderupdater> orderviewbuilder = new FirestoreRecyclerOptions.Builder<orderupdater>()
                .setQuery(myorder, orderupdater.class)
                .build();
        return view;
    }
}