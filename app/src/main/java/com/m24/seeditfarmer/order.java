package com.m24.seeditfarmer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class order extends Fragment {
    RecyclerView orderrecycler;
    FirestoreRecyclerAdapter orderadapter;
    String userid,user,hint;
    private FirebaseFirestore db;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public order() {
        // Required empty public constructor
    }


    public static order newInstance(String param1, String param2) {
        order fragment = new order();
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
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        db = FirebaseFirestore.getInstance();
        orderrecycler = view.findViewById(R.id.orderrecycler);
        SharedPreferences settings = getActivity().getSharedPreferences("preference", MODE_PRIVATE);
       userid = settings.getString("uid","");
       user = settings.getString("selector","");
       if (user.equals("farmer")){
          hint = "farmerdata" ;
       }else{
           hint = "userdata";
       }
        Query orders = db.collection("booking").whereEqualTo("drid",userid);
        FirestoreRecyclerOptions<bookingupdater> orderviewbuilder = new FirestoreRecyclerOptions.Builder<bookingupdater>()
                .setQuery(orders, bookingupdater.class)
                .build();
        orderadapter = new FirestoreRecyclerAdapter<bookingupdater,orderview>(orderviewbuilder){

            @NonNull
            @Override
            public orderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drbook,parent,false);
                return new orderview(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull orderview holder, int position, @NonNull bookingupdater model) {
                //Toast.makeText(getContext(), model.getUserid(), Toast.LENGTH_SHORT).show();
               db.collection("Doctorappuserdata").document(model.getUserid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if (documentSnapshot != null){
                           userdataupdater userdataupdaterd = documentSnapshot.toObject(userdataupdater.class);
                           holder.drname.setText(userdataupdaterd.getName());
                           holder.book.setVisibility(View.GONE);
                       }else {
                           Toast.makeText(getContext(),"null", Toast.LENGTH_SHORT).show();
                       }

                   }
               });
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        orderrecycler.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        orderrecycler.setItemAnimator(null);
        orderrecycler.setAdapter(orderadapter);
        return view;
    }


    private class orderview extends RecyclerView.ViewHolder {
       TextView orderviewname,orderviewprice,orderviewaddress,drname;
        Button book;
        public orderview(@NonNull View itemView) {
            super(itemView);
//            orderviewname = itemView.findViewById(R.id.orderproductname);
//            orderviewprice = itemView.findViewById(R.id.orderproductprice);
//            orderviewaddress = itemView.findViewById(R.id.orderproductaddress);
            book= itemView.findViewById(R.id.drbookbutton);
            drname = itemView.findViewById(R.id.drname);

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        orderadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderadapter.stopListening();
    }
}