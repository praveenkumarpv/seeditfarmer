package com.m24.seeditfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import helperclass.*;

public class viewpatient extends AppCompatActivity {
    private  EditText searched;
    private String searchkey;
    private RecyclerView searchview;
    ImageView search;
    FirestoreRecyclerAdapter searchadp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpatient);
        searched = findViewById(R.id.searched);
        search = findViewById(R.id.searchimg);
        searchview = findViewById(R.id.searchview);
        Query searchquery = FirebaseFirestore.getInstance().collection("Doctorappuserdata");
        FirestoreRecyclerOptions<userdataupdater> userserchview = new FirestoreRecyclerOptions.Builder<userdataupdater>()
                .setQuery(searchquery, userdataupdater.class)
                .build();
        searchadp = new FirestoreRecyclerAdapter<userdataupdater, patientview>(userserchview) {
            @NonNull
            @Override
            public patientview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchview,parent,false);
                return new patientview(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull patientview holder, int position, @NonNull userdataupdater model) {
                holder.namev.setText(model.getName());
                holder.emailv.setText(model.getEmailid());
                holder.vholder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent patientdetailedview = new Intent(viewpatient.this, com.m24.seeditfarmer.patientdetailedview.class);
                        patientdetailedview.putExtra("patientuid",model.getEmailid());
                        startActivity(patientdetailedview);
                    }
                });
            }
        };
        searchadp.startListening();
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewpatient.this);
        searchview.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        searchview.setItemAnimator(null);
        searchview.setAdapter(searchadp);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               searchkey = searched.getText().toString().trim();
               if (searchkey.isEmpty()){
                   Toast.makeText(viewpatient.this, "Please enter an valied search", Toast.LENGTH_SHORT).show();
               }else{
                   searchadp.stopListening();
                   searchviewfuction(searchkey);
               }
            }
        });
    }

    private void searchviewfuction(String searchkey) {

        Toast.makeText(this, searchkey, Toast.LENGTH_SHORT).show();
        Query searchquery = FirebaseFirestore.getInstance().collection("Doctorappuserdata").whereEqualTo("name",searchkey);

        FirestoreRecyclerOptions<userdataupdater> userserchview = new FirestoreRecyclerOptions.Builder<userdataupdater>()
                .setQuery(searchquery, userdataupdater.class)
                .build();
        searchadp = new FirestoreRecyclerAdapter<userdataupdater, patientview>(userserchview) {
            @NonNull
            @Override
            public patientview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchview,parent,false);
                return new patientview(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull patientview holder, int position, @NonNull userdataupdater model) {
              holder.namev.setText(model.getName());
              holder.emailv.setText(model.getEmailid());
            }
        };
        searchadp.startListening();
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewpatient.this);
        searchview.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        searchview.setItemAnimator(null);
        searchview.setAdapter(searchadp);
    }

    private class patientview extends RecyclerView.ViewHolder {
        ConstraintLayout vholder;
        TextView namev,emailv,agev;

        public patientview(@NonNull View itemView) {
            super(itemView);
            vholder = itemView.findViewById(R.id.searchholder);
            namev = itemView.findViewById(R.id.viewname);
            emailv = itemView.findViewById(R.id.emailview);
            agev = itemView.findViewById(R.id.ageview);

        }
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//
//            searchadp.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//            searchadp.stopListening();
//
//    }
}