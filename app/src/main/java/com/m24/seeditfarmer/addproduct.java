package com.m24.seeditfarmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import helperclass.*;

public class addproduct extends AppCompatActivity {
    private ImageView addproductimg;
    private EditText adpatientname,adpatientage,adpatientdiscription,adpatientemailid,date;
    String adpatientnametext,adpatientagetxt,adpatientemailtext,adpatientdiscriptiontxt,imagestring="no",doctoruid,activity,uids,dates,adpatientphoneno="";
    private Button deletbt,submitbt;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri image,downloaduri;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences settings = addproduct.this.getSharedPreferences("preference", MODE_PRIVATE);
        doctoruid = settings.getString("uid","");

        addproductimg = findViewById(R.id.productimageadd);
        adpatientname = findViewById(R.id.patientnameadd);
        adpatientage = findViewById(R.id.patientageadd);
        adpatientemailid = findViewById(R.id.patientemailidadd);
        adpatientdiscription = findViewById(R.id.addproductdiscription);
        adpatientdiscription = findViewById(R.id.addproductdiscription);
        deletbt = findViewById(R.id.deletproduct);
        submitbt = findViewById(R.id.submitproduct);
        date = findViewById(R.id.date);
//        Bundle editproduct = getIntent().getExtras();
//        if (editproduct != null) {
//            activity = editproduct.getString("Activity","");
//            adproductname.setText( editproduct.getString("productname",""));
//            adproductprice.setText( editproduct.getString("productprice",""));
//            adproductdiscription.setText( editproduct.getString("productdiscription",""));
//            imagestring = editproduct.getString("productimage","");
//            Glide.with(addproduct.this).load(imagestring).into(addproductimg);
//            deletbt.setVisibility(View.VISIBLE);
//        }
        addproductimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adpatientnametext = adpatientname.getText().toString().trim();
                adpatientagetxt = adpatientage.getText().toString().trim();
                adpatientemailtext = adpatientemailid.getText().toString().trim();
                adpatientdiscriptiontxt = adpatientdiscription.getText().toString().trim();
                dates = date.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(adpatientemailtext,adpatientemailtext).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        uids = authResult.getUser().getUid();
                        userdataupdater user = new userdataupdater(uids,adpatientnametext,adpatientemailtext,adpatientphoneno,"","patient");
                            db.collection("Doctorappuserdata").document(adpatientemailtext).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    UUID uuid= UUID.randomUUID();
                                    String id = String.valueOf(uuid);
                                    prescription prescription = new prescription(adpatientdiscriptiontxt,doctoruid,dates);
                                    db.collection("Doctorappuserdata").document(adpatientemailtext).
                                            collection("prescription").document(id).set(prescription).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(addproduct.this, "Added success fully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(addproduct.this, "Failed to add!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(addproduct.this, "Failed to add!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addproduct.this, "Failed to add", Toast.LENGTH_SHORT).show();

                    }
                });
//                if (imagestring.equals("")){
//                    Toast.makeText(addproduct.this, "please selet an image", Toast.LENGTH_SHORT).show();
//                }
//                else if (adprodecttext.isEmpty() || adprodutpricetxt.isEmpty() ||adproductdiscriptiontxt.isEmpty()){
//                    Toast.makeText(addproduct.this, "please fill the form", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    if (n==0){
//                        productupdater productupdater = new productupdater(farmeruid,adprodecttext,adprodutpricetxt,adproductdiscriptiontxt,imagestring);
//                        db.collection("Product").document(adprodecttext).set(productupdater)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(addproduct.this, "updated successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(addproduct.this, "Failed to update!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                    else{
//                        StorageReference storageReference = storageRef.child("seedit/product/"+adprodecttext);
//                        storageReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        downloaduri = uri;
//                                        imagestring = downloaduri.toString();
//                                        productupdater productupdater = new productupdater(farmeruid,adprodecttext,adprodutpricetxt,adproductdiscriptiontxt,imagestring);
//                                        db.collection("Product").document(adprodecttext).set(productupdater)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void unused) {
//                                                        n=0;
//                                                        Toast.makeText(addproduct.this, "updated successfully", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(addproduct.this, "Failed to update!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(addproduct.this, "failed to add product", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                }
            }
        });
//        deletbt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adprodecttext = adproductname.getText().toString().trim();
//                StorageReference storageReference = storageRef.child("seedit/product/"+adprodecttext);
//                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        db.collection("Product").document(adprodecttext).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                               finish();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(addproduct.this, "failed to Delet product", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(addproduct.this, "failed to Delet product", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final int unmasked = requestCode & 0x0000ffff;
        Toast.makeText(this, "inside result", Toast.LENGTH_SHORT).show();
        if (unmasked == PICK_IMAGE_REQUEST && data != null){
            image = data.getData();
            imagestring = image.toString();
            Glide.with(addproduct.this).load(image).into(addproductimg);
            n=1;
        }
    }
}