package com.m24.seeditfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.type.DateTime;

import java.util.UUID;
import helperclass.*;

public class chatview extends AppCompatActivity {
    RecyclerView messageview;
    EditText message;
    FloatingActionButton send;
    String productname,useruid,farmeruid,uniqueid,useridinap,messagetxt,uniqueID,sender;
    private FirebaseFirestore db;
    private Timestamp created;
    FirestoreRecyclerAdapter messageadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatview);
        SharedPreferences settings = chatview.this.getSharedPreferences("preference", MODE_PRIVATE);
        useridinap = settings.getString("uid","");
        sender = settings.getString("selector","");
        db = FirebaseFirestore.getInstance();
        Bundle  chat = getIntent().getExtras();
        if ( chat != null) {
            productname =  chat.getString("product","");
            useruid =  chat.getString("chatinguseruid","");
            farmeruid = chat.getString("chatfarmeruid","");
            uniqueid = chat.getString("uniqueid","");
        }
        message = findViewById(R.id.messagewriter);
        send = findViewById(R.id.messagesend);
        messageview = findViewById(R.id.meassage_view);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagetxt = message.getText().toString().trim();
                if (messagetxt.isEmpty()){
                    Toast.makeText(chatview.this, "Message is empty!", Toast.LENGTH_SHORT).show();
                }
                else{
                    uniqueID = UUID.randomUUID().toString();
                    messageupdater messageupdater = new messageupdater(messagetxt,farmeruid,useruid,productname,sender,Timestamp.now());
                   db.collection("chat").document(uniqueid).collection("chatmessage").document(uniqueID).set(messageupdater).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           message.setText("");
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                       }
                   });
                }
            }
        });
        Query messagequery = FirebaseFirestore.getInstance().collection("chat").document(uniqueid).collection("chatmessage").orderBy("time");
        FirestoreRecyclerOptions<messageupdater> messages = new FirestoreRecyclerOptions.Builder<messageupdater>()
                .setQuery(messagequery, messageupdater.class)
                .build();
        messageadapter = new FirestoreRecyclerAdapter<messageupdater, messageholder>(messages){
            @NonNull
            @Override
            public messageholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatmessagelayout,parent,false);
                return new messageholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull messageholder holder, int position, @NonNull messageupdater model) {
             if (sender.equals(model.getSender())){
                 holder.right.setVisibility(View.VISIBLE);
                 holder.right.setText(model.getMessage());
//                 Toast.makeText(chatview.this, model.getMessage(), Toast.LENGTH_SHORT).show();
             }
             else{
                 holder.left.setVisibility(View.VISIBLE);
                 holder.left.setText(model.getMessage());
             }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(chatview.this);
        messageview.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        messageview.setItemAnimator(null);
        messageview.setAdapter(messageadapter);
    }
    public Timestamp getCreated() {
        return created;
    }

    private class messageholder extends RecyclerView.ViewHolder {
        TextView left,right;
        public messageholder(@NonNull View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.leftmessage);
            right = itemView.findViewById(R.id.rightmessage);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        messageadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        messageadapter.stopListening();
    }
}