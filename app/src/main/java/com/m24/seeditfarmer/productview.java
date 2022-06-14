package com.m24.seeditfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;
import helperclass.*;
import Adapter.conformationAdapter;

public class productview extends AppCompatActivity {
    ImageView productimagev;
    TextView productnameview,productpriveview,productdiscriptionview;
    Button chatbt,buybt;
    String imagestring,farmeruid,productname,userid,productprice;
    private FirebaseFirestore db;
    String uniqueID;
    int ok = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);
        db = FirebaseFirestore.getInstance();
        SharedPreferences settings = productview.this.getSharedPreferences("preference", MODE_PRIVATE);
        userid = settings.getString("uid","");
        productimagev = findViewById(R.id.productimagevw);
        productnameview = findViewById(R.id.productnametv);
        productpriveview = findViewById(R.id.productpricetv);
        productdiscriptionview = findViewById(R.id.productdiscriptiontv);
        buybt = findViewById(R.id.buyproduct);
        chatbt = findViewById(R.id.chatproduct);
        Bundle editproduct = getIntent().getExtras();
        if (editproduct != null) {
            productname = editproduct.getString("productname","");
            productnameview.setText(productname);
            productprice = editproduct.getString("productprice","");
            productpriveview.setText(productprice);
            productdiscriptionview.setText(editproduct.getString("productdiscription",""));
            farmeruid = editproduct.getString("farmeruid","");
            imagestring = editproduct.getString("productimage","");
            Glide.with(productview.this).load(imagestring).into(productimagev);
        }
        buybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ok==0){
                 conformationAdapter conformationAdapter = new conformationAdapter(productview.this,userid,ok);
                 conformationAdapter.Startconformation();
                 ok=1;
                }
                else {
                    uniqueID = UUID.randomUUID().toString();
                    orderupdater orders = new orderupdater(productname,productprice,userid);
                    db.collection("farmerdata").document(farmeruid).collection("Order").document(uniqueID).set(orders).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    db.collection("userdata").document(userid).collection("Order").document(uniqueID).set(orders).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(productview.this, "order sussessfull", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(productview.this, "order failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        chatbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uniqueID = UUID.randomUUID().toString();
            chatdatabaseupdater chatdatabase = new chatdatabaseupdater(productname,farmeruid,userid,uniqueID);
            db.collection("userdata").document(userid).collection("chat").document(uniqueID).set(chatdatabase)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            db.collection("farmerdata").document(farmeruid).collection("chat").document(uniqueID).
                                    set(chatdatabase).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent chat = new Intent(productview.this, chatview.class);
                                    chat.putExtra("chatinguseruid",userid);
                                    chat.putExtra("chatfarmeruid",farmeruid);
                                    chat.putExtra("product",productname);
                                    chat.putExtra("uniqueid",uniqueID);
                                    startActivity(chat);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(productview.this, "failed to chat", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(productview.this, "failed to chat", Toast.LENGTH_SHORT).show();
                }
            });
            }
        });
    }
}