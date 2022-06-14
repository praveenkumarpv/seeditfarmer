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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

import helperclass.prescription;
import helperclass.userdataupdater;

public class patientdetailedview extends AppCompatActivity {
    Button addprescription,viewoldpriscription,back;
    TextView name,age;
    EditText prescriptionnote,date;
    String patientuid;
    private FirebaseFirestore db;
    String doctoruid,datetxt,discriptio;
    LinearLayout addview,oldview;
    RecyclerView oldpriscriptionview;
    FirestoreRecyclerAdapter prescriptionadp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientdetailedview);
        patientuid = getIntent().getStringExtra("patientuid");
        SharedPreferences settings = patientdetailedview.this.getSharedPreferences("preference", MODE_PRIVATE);
        doctoruid = settings.getString("uid","");
        db = FirebaseFirestore.getInstance();
        addprescription = findViewById(R.id.prescriptionaddbt);
        viewoldpriscription = findViewById(R.id.prescriptionoldviewbt);
        name = findViewById(R.id.detailedviewname);
        age = findViewById(R.id.detailedviewage);
        prescriptionnote = findViewById(R.id.adpriscription);
        date = findViewById(R.id.datefromview);
        addview = findViewById(R.id.addlayout);
        oldview = findViewById(R.id.oldpriscriptonlayout);
        oldpriscriptionview = findViewById(R.id.oldpriscriptonview);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               oldview.setVisibility(View.GONE);
               addview.setVisibility(View.VISIBLE);
            }
        });
        viewoldpriscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldview.setVisibility(View.VISIBLE);
                addview.setVisibility(View.GONE);
            }
        });

        db.collection("Doctorappuserdata").document(patientuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userdataupdater userdataupdater = documentSnapshot.toObject(userdataupdater.class);
                name.setText(userdataupdater.getName());
                age.setText(userdataupdater.getAge());
            }
        });
        addprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              datetxt = date.getText().toString().trim();
              discriptio = prescriptionnote.getText().toString();
              if (datetxt.isEmpty() || discriptio.isEmpty()){
                  Toast.makeText(patientdetailedview.this, "fill the form", Toast.LENGTH_SHORT).show();
              }else{
                  UUID uuid= UUID.randomUUID();
                  String id = String.valueOf(uuid);
                  prescription prescription = new prescription(discriptio,doctoruid,datetxt);
                  db.collection("Doctorappuserdata").document(patientuid).
                          collection("prescription").document(id).set(prescription).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                          Toast.makeText(patientdetailedview.this, "Added success fully", Toast.LENGTH_SHORT).show();
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(patientdetailedview.this, "Failed to add!", Toast.LENGTH_SHORT).show();
                      }
                  });
              }
            }
        });
        Query prescriptionquery = FirebaseFirestore.getInstance().collection("Doctorappuserdata").document(patientuid).collection("prescription");
        FirestoreRecyclerOptions<prescription> prescriptions = new FirestoreRecyclerOptions.Builder<prescription>()
                .setQuery(prescriptionquery, prescription.class)
                .build();
        prescriptionadp = new FirestoreRecyclerAdapter<prescription,priscriptionviews>(prescriptions){
            @NonNull
            @Override
            public priscriptionviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription,parent,false);
                return new priscriptionviews(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull priscriptionviews holder, int position, @NonNull prescription model) {
                holder.date.setText(model.getDate());
                holder.priscription.setText(model.getPrescription());
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(patientdetailedview.this);
        oldpriscriptionview.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        oldpriscriptionview.setItemAnimator(null);
        oldpriscriptionview.setAdapter(prescriptionadp);
    }

    private class priscriptionviews extends RecyclerView.ViewHolder {
        TextView date,priscription;
        public priscriptionviews(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.prescriptiondate);
            priscription = itemView.findViewById(R.id.prescrptiontxt);
        }
    }
        @Override
    public void onStart() {
        super.onStart();

            prescriptionadp.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

            prescriptionadp.stopListening();

    }
}