package com.m24.seeditfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import helperclass.*;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    LinearLayout formcondainer,loginform,registerform;
    EditText logemail,logpass,regemail,regname,regpass,regphoneno;
    TextView registertxt,logintxt;
    Button loginbt,registerbt;
    RadioGroup selectorrdbt,selectorrdbts;
    TextView appname;
    String loginemailtxt,loginpasstxt,registeremailtxt,registerphonenumbertxt,registernametxt,registerpasstxt,uids,selector="";
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Handler handler = new Handler();
        mAuth = FirebaseAuth.getInstance();
        formcondainer = findViewById(R.id.condainer);
        appname = findViewById(R.id.appname);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences settings = MainActivity.this.getSharedPreferences("preference", MODE_PRIVATE);
                if (settings.getString("uid", "").equals("")) {
                    formcondainer.setVisibility(View.VISIBLE);
                    appname.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(MainActivity.this, Homeactivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
        db = FirebaseFirestore.getInstance();
        loginform = findViewById(R.id.loginform);
        registerform = findViewById(R.id.registerform);
        logemail = findViewById(R.id.loginemailid);
        logpass = findViewById(R.id.loginpassword);
        regemail = findViewById(R.id.registeremailid);
        regphoneno = findViewById(R.id.registerphonenumber);
        regname = findViewById(R.id.registername);
        regpass = findViewById(R.id.registerpassword);
        registertxt = findViewById(R.id.registertxt);
        logintxt = findViewById(R.id.logintext);
        loginbt = findViewById(R.id.loginbt);
        registerbt = findViewById(R.id.registerbt);
        selectorrdbt = findViewById(R.id.selectorradiobt);
        selectorrdbts = findViewById(R.id.selectorradiobts);
        registertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginform.setVisibility(View.GONE);
                registerform.setVisibility(View.VISIBLE);
            }
        });
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginform.setVisibility(View.VISIBLE);
                registerform.setVisibility(View.GONE);
            }
        });
        selectorrdbt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.doctor:
                        selector = "doctor";
                        Toast.makeText(MainActivity.this, selector, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.patient:
                        selector = "patient";
                        Toast.makeText(MainActivity.this, selector, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        selectorrdbts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.doctors:
                        selector = "doctor";
                        Toast.makeText(MainActivity.this, selector, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.patients:
                        selector = "patient";
                        Toast.makeText(MainActivity.this, selector, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             loginemailtxt = logemail.getText().toString().trim();
             loginpasstxt = logpass.getText().toString().trim();
             if (loginemailtxt.isEmpty() || loginpasstxt.isEmpty()){
                 Toast.makeText(MainActivity.this, "Empty feilds!", Toast.LENGTH_SHORT).show();
             }
             else{
                 mAuth.signInWithEmailAndPassword(loginemailtxt,loginpasstxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {
                         if (selector.equals("doctor")){
                             uids = authResult.getUser().getUid();
                             db.collection("Doctorappdoctordata").whereEqualTo("uid",uids).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                 @Override
                                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                     if (task.isSuccessful()) {
                                         QuerySnapshot document = task.getResult();
                                         // Log.d(TAG, "Document exists in user!");
                                         //Toast.makeText(MainActivity.this, "Document exists in user!", Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(MainActivity.this, Homeactivity.class);
                                         startActivity(intent);
                                         Toast.makeText(MainActivity.this, uids, Toast.LENGTH_SHORT).show();
                                         SharedPreferences settings = MainActivity.this.getSharedPreferences("preference", MODE_PRIVATE);
                                         SharedPreferences.Editor editor = settings.edit();
                                         editor.putString("uid", String.valueOf(uids));
                                         editor.putString("selector", "doctor");
                                         editor.commit();
                                         finish();

                                     } else {
                                         Log.d(TAG, "Failed with: ", task.getException());
                                     }
                                 }
                             });
                         }else{
                             uids = loginemailtxt;
                             db.collection("Doctorappdoctordata").whereEqualTo("emailid",uids).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                 @Override
                                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                     if (task.isSuccessful()) {
                                         QuerySnapshot document = task.getResult();
                                         // Log.d(TAG, "Document exists in user!");
                                         //Toast.makeText(MainActivity.this, "Document exists in user!", Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(MainActivity.this, Homeactivity.class);
                                         startActivity(intent);
                                         Toast.makeText(MainActivity.this, uids, Toast.LENGTH_SHORT).show();
                                         SharedPreferences settings = MainActivity.this.getSharedPreferences("preference", MODE_PRIVATE);
                                         SharedPreferences.Editor editor = settings.edit();
                                         editor.putString("uid", String.valueOf(uids));
                                         editor.putString("selector", "patient");
                                         editor.commit();
                                         finish();
                                     } else {
                                         Log.d(TAG, "Failed with: ", task.getException());
                                     }
                                 }
                             });
                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                     }
                 });
             }
            }
        });
        registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             registeremailtxt = regemail.getText().toString().trim();
             registernametxt = regname.getText().toString().trim();
             registerpasstxt = regpass.getText().toString().trim();
             registerphonenumbertxt = regphoneno.getText().toString().trim();
                Toast.makeText(MainActivity.this, selector, Toast.LENGTH_SHORT).show();
             if (registeremailtxt.isEmpty() || registernametxt.isEmpty() || registerpasstxt.isEmpty() || selector.equals("") || registerphonenumbertxt.isEmpty()){
                 Toast.makeText(MainActivity.this, "Empty feilds!", Toast.LENGTH_SHORT).show();
             }
             else {
                 mAuth.createUserWithEmailAndPassword(registeremailtxt,registerpasstxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {
                         uids = authResult.getUser().getUid();
                         if (selector == "patient"){
                            userdataupdater user = new userdataupdater(uids,registernametxt,registeremailtxt,registerphonenumbertxt,"",selector);
                            db.collection("Doctorappuserdata").document(registeremailtxt).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(MainActivity.this, Homeactivity.class);
                                    startActivity(intent);
                                    SharedPreferences settings = MainActivity.this.getSharedPreferences("preference", MODE_PRIVATE);
                                    SharedPreferences.Editor  editor = settings.edit();
                                    editor.putString("uid", registeremailtxt);
                                    editor.putString("selector", selector);
                                    editor.commit();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                }
                            });
                         }
                         else if (selector == "doctor"){
                             doctordata doctordata = new doctordata(registeremailtxt,registerphonenumbertxt,registernametxt,uids,selector);
                             db.collection("Doctorappdoctordata").document(uids).set(doctordata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     Intent intent = new Intent(MainActivity.this, Homeactivity.class);
                                     startActivity(intent);
                                     SharedPreferences settings = MainActivity.this.getSharedPreferences("preference", MODE_PRIVATE);
                                     SharedPreferences.Editor editor = settings.edit();
                                     editor.putString("uid", String.valueOf(uids));
                                     editor.putString("selector", selector);
                                     editor.commit();
                                     finish();
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(MainActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                 }
                             });

                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MainActivity.this, "Failed to registermail", Toast.LENGTH_SHORT).show();

                     }
                 });
             }
            }
        });
    }
}