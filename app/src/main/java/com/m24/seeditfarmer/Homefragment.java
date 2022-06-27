package com.m24.seeditfarmer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import helperclass.*;


public class Homefragment extends Fragment {
    String uid,selected;
    FloatingActionButton addfab,viewfab;
    RecyclerView productrecycler,prisview;
    FirestoreRecyclerAdapter farmeradapter,useradapter;
    ConstraintLayout doctorv,patientv;
    TextView welcomenote,bgtv,bptv;
    private List<String> bplist = new ArrayList<>();
    private List<String> bglist = new ArrayList<>();
    int bplength,bglength;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Homefragment() {

    }


    public static Homefragment newInstance(String param1, String param2) {
        Homefragment fragment = new Homefragment();
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
        SharedPreferences settings = getActivity().getSharedPreferences("preference", MODE_PRIVATE);
        uid = settings.getString("uid", "");
        selected = settings.getString("selector","");
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        doctorv = view.findViewById(R.id.doctorview);
        patientv = view.findViewById(R.id.patientviewlayout);
        productrecycler = view.findViewById(R.id.productrecyclerview);
        prisview = view.findViewById(R.id.userviewprescription);
        addfab = view.findViewById(R.id.addpatientfab);
        viewfab = view.findViewById(R.id.patientview);
        welcomenote = view.findViewById(R.id.welcomenote);
        bgtv = view.findViewById(R.id.bgtextview);
        bptv = view.findViewById(R.id.bptextview);
        addfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addproductintent = new Intent(getActivity(), addproduct.class);
                startActivity(addproductintent);
            }
        });
        viewfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewpatient = new Intent(getActivity(), viewpatient.class);
                startActivity(viewpatient);
            }
        });
        if (selected.equals("doctor")){
            addfab.setVisibility(View.VISIBLE);
            patientv.setVisibility(View.GONE);
            doctorv.setVisibility(View.VISIBLE);
//            Query farmerquery = FirebaseFirestore.getInstance().collection("Product").whereEqualTo("farmeruid",uid);
//            FirestoreRecyclerOptions<productupdater> farmerproduct = new FirestoreRecyclerOptions.Builder<productupdater>()
//                    .setQuery(farmerquery, productupdater.class)
//                    .build();
//            farmeradapter = new FirestoreRecyclerAdapter<productupdater, productview>(farmerproduct){
//
//                @NonNull
//                @Override
//                public productview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlayout,parent,false);
//                    return new productview(view);
//                }
//
//                @Override
//                protected void onBindViewHolder(@NonNull productview holder, int position, @NonNull productupdater model) {
//                    Glide.with(getContext()).load(model.getProductimage()).into(holder.productimage);
//                    holder.productname.setText(model.getProductname());
//                    holder.productprice.setText("₹-"+model.getProductprice());
//                    holder.productlayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent editproduct = new Intent(getActivity(), addproduct.class);
//                            editproduct.putExtra("Activity","edit");
//                            editproduct.putExtra("productname",model.getProductname());
//                            editproduct.putExtra("productprice",model.getProductprice());
//                            editproduct.putExtra("productdiscription",model.getProductdiscription());
//                            editproduct.putExtra("productimage",model.getProductimage());
//                            editproduct.putExtra("farmeruid",model.getFarmeruid());
//                            startActivity(editproduct);
//                        }
//                    });
//                }
//            };
//            productrecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//            productrecycler.setItemAnimator(null);
//            productrecycler.setAdapter(farmeradapter);
        }
        else{
            FirebaseFirestore.getInstance().collection("Doctorappuserdata").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userdataupdater userdataupdater = documentSnapshot.toObject(userdataupdater.class);
                    welcomenote.setText("Hello,"+userdataupdater.getName());
                    bgtv.setText(userdataupdater.getBg());
                    bptv.setText(userdataupdater.getBp());
                    Toast.makeText(getContext(),String.valueOf(bplength), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),String.valueOf(bglength), Toast.LENGTH_SHORT).show();


                                }
            });
            Query pe = FirebaseFirestore.getInstance().collection("Doctorappuserdata").document(uid).collection("prescription");
            FirestoreRecyclerOptions<prescription> pres = new FirestoreRecyclerOptions.Builder<prescription>()
                    .setQuery(pe, prescription.class)
                    .build();
            useradapter= new FirestoreRecyclerAdapter<prescription, presview>(pres){


                @NonNull
                @Override
                public presview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription,parent,false);
                    return new presview(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull presview holder, int position, @NonNull prescription model) {
                    holder.date.setText(model.getDate());
                    holder.priscription.setText(model.getPrescription());
                }
            };
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            prisview.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
            prisview.setItemAnimator(null);
            prisview.setAdapter(useradapter);
        }
        return view;
    }


    private class productview extends RecyclerView.ViewHolder {
        ConstraintLayout productlayout;
        ImageView productimage;
        TextView productname,productprice;
        public productview(@NonNull View itemView) {
            super(itemView);
            productlayout = itemView.findViewById(R.id.productlayouts);
            productimage = itemView.findViewById(R.id.productimage);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.produtprice);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (selected.equals("doctor")){
          //  farmeradapter.startListening();
        }else{
            useradapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (selected.equals("doctor")){
            //farmeradapter.stopListening();
        }else{
            useradapter.stopListening();
        }
    }

    private class presview extends RecyclerView.ViewHolder {
        TextView date,priscription;
        public presview(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.prescriptiondate);
            priscription = itemView.findViewById(R.id.prescrptiontxt);
        }
    }
}