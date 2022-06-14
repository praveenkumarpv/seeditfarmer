package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.m24.seeditfarmer.R;

import helperclass.*;

public class conformationAdapter {
    Activity activity;
    AlertDialog dialog;
    String uid,address="";
    EditText addressview;
    Button submitconformation;
    int ok;
    private FirebaseFirestore db;

    public conformationAdapter(Activity activity, String uid, int ok) {
        this.activity = activity;
        this.uid = uid;
        this.ok = ok;
    }

    public int Startconformation(){
        db = FirebaseFirestore.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setCancelable(true);
        View v = inflater.inflate(R.layout.conformationlayout,null);
        builder.setView(v);
        addressview = v.findViewById(R.id.addressviewconformation);
        submitconformation = v.findViewById(R.id.conformation);
        db.collection("userdata").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
             userdataupdater userdataupdater = documentSnapshot.toObject(helperclass.userdataupdater.class);
               addressview.setText(userdataupdater.getAddress());
            }
        });
        submitconformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = addressview.getText().toString().trim();
                if (address.equals("")){
                    Toast.makeText(activity, "Address is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.collection("userdata").document(uid).update("address",address).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                           stopplay();
                           ok = 1;
                        }
                    });
                }

            }
        });
        dialog = builder.create();
        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
     return ok;
    }
    public void stopplay(){
        dialog.dismiss();
    }
}
