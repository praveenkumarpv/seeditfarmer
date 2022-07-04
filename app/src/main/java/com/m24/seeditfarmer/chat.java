package com.m24.seeditfarmer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import helperclass.*;

import static android.content.Context.MODE_PRIVATE;

public class chat extends Fragment {
    RecyclerView allchat;
    String uid,selected,docid;
    FirestoreRecyclerAdapter allchatadapterf,allchatadapteru;
    private FirebaseFirestore db;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public chat() {
        // Required empty public constructor
    }


    public static chat newInstance(String param1, String param2) {
        chat fragment = new chat();
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
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        SharedPreferences settings = getActivity().getSharedPreferences("preference", MODE_PRIVATE);
        uid = settings.getString("uid", "");
        selected = settings.getString("selector","");
        db = FirebaseFirestore.getInstance();
        allchat = view.findViewById(R.id.allchat);
        if (selected.equals("farmer")) {
            Query allchatqueryf = FirebaseFirestore.getInstance().collection("farmerdata").document(uid).collection("chat");
            FirestoreRecyclerOptions<chatdatabaseupdater> allchatf = new FirestoreRecyclerOptions.Builder<chatdatabaseupdater>()
                    .setQuery(allchatqueryf, chatdatabaseupdater.class)
                    .build();
            allchatadapterf = new FirestoreRecyclerAdapter<chatdatabaseupdater,allchatholder>(allchatf){
                @NonNull
                @Override
                public allchatholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist,parent,false);
                    return new allchatholder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull allchatholder holder, int position, @NonNull chatdatabaseupdater model) {
//                    db.collection("userdata").document(model.getUserid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            userdataupdater user = documentSnapshot.toObject(userdataupdater.class);
//                            holder.name.setText(user.getName());
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//                    holder.chatlistlayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent chat = new Intent(getActivity(), chatview.class);
//                            chat.putExtra("chatinguseruid",model.getUserid());
//                            chat.putExtra("chatfarmeruid",model.getFarmerid());
//                            chat.putExtra("product",model.getProductname());
//                            chat.putExtra("uniqueid",model.getDatabaseid());
//                            startActivity(chat);
//                        }
//                    });
                }
            };
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            allchat.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
            allchat.setItemAnimator(null);
            allchat.setAdapter(allchatadapterf);
        }
        else{
//            Toast.makeText(getContext(), "bhjhhnfb", Toast.LENGTH_SHORT).show();
            Query orders = db.collection("booking").whereEqualTo("userid",uid);
            FirestoreRecyclerOptions<bookingupdater> orderviewbuilder = new FirestoreRecyclerOptions.Builder<bookingupdater>()
                    .setQuery(orders, bookingupdater.class)
                    .build();
            allchatadapteru = new FirestoreRecyclerAdapter<bookingupdater,allchatholder>(orderviewbuilder){

                @NonNull
                @Override
                public allchatholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drbook,parent,false);
                    return new allchatholder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull allchatholder holder, int position, @NonNull bookingupdater model) {
                    holder.book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("booking").document(model.getUid()).delete();
                        }
                    });
                    db.collection("Doctorappdoctordata").document(model.getDrid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot != null){
                                doctordata doctordata  = documentSnapshot.toObject(doctordata.class);
                                holder.drname.setText(doctordata.getName());
                                holder.book.setText("Cancel");
                                holder.datetv.setVisibility(View.VISIBLE);
                                holder.timetv.setVisibility(View.VISIBLE);
                                holder.datetv.setText("Date : "+model.getDate());
                                holder.timetv.setText("Time : "+model.getTime());
                            }else {
                                Toast.makeText(getContext(),"null", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            };
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            allchat.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
            allchat.setItemAnimator(null);
            allchat.setAdapter(allchatadapteru);
        }
        return view;
    }

    private class allchatholder extends RecyclerView.ViewHolder {
        TextView drname,timetv,datetv;
        Button book;
        public allchatholder(@NonNull View itemView) {
            super(itemView);
            timetv = itemView.findViewById(R.id.times);
            datetv = itemView.findViewById(R.id.dates);
//            orderviewaddress = itemView.findViewById(R.id.orderproductaddress);
            book= itemView.findViewById(R.id.drbookbutton);
            drname = itemView.findViewById(R.id.drname);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (selected.equals("farmer")){
            allchatadapterf.startListening();
        }else{
            allchatadapteru.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (selected.equals("farmer")){
            allchatadapterf.stopListening();
        }else{
            allchatadapteru.stopListening();
        }
    }
}
