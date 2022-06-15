package com.m24.seeditfarmer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

import helperclass.*;


public class profile extends Fragment {
    EditText personnameed,emailided,addressed,phoneno;
    Button submit;
    CircleImageView personprofileimage;
    FirebaseFirestore db;
    String uid,selected,nametxt,emailidtxt,addresstxt,phonenotxt,pbptxt,pbgtxt;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public profile() {

    }

    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        db = FirebaseFirestore.getInstance();
        SharedPreferences settings = getActivity().getSharedPreferences("preference", MODE_PRIVATE);
        uid = settings.getString("uid", "");
        selected = settings.getString("selector","");
        //Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();
        personprofileimage= view.findViewById(R.id.personimage);
        personnameed = view.findViewById(R.id.personnameid);
        emailided = view.findViewById(R.id.personemailid);
        addressed = view.findViewById(R.id.personaddress);
        phoneno = view.findViewById(R.id.personphonenumber);
        submit = view.findViewById(R.id.submit);
        //Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
            if (selected.equals("doctor")) {
                addressed.setVisibility(View.GONE);
                db.collection("Doctorappdoctordata").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Toast.makeText(getContext(), "inside farmer", Toast.LENGTH_SHORT).show();
                        doctordata doctordata = documentSnapshot.toObject(doctordata.class);
                        personnameed.setText(doctordata.getName());
                        emailided.setText(doctordata.getEmailid());
                        phoneno.setText(doctordata.getPhonenumber());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to load  profile", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (selected.equals("patient")) {
                db.collection("Doctorappuserdata").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Toast.makeText(getContext(), "iside user", Toast.LENGTH_SHORT).show();
                        userdataupdater userdataupdater = documentSnapshot.toObject(userdataupdater.class);
                        personnameed.setText(userdataupdater.getName());
                        emailided.setText(userdataupdater.getEmailid());
                        addressed.setText(userdataupdater.getAge());
                        phoneno.setText(userdataupdater.getPhonenumber());
                        pbptxt = userdataupdater.getBp();
                        pbgtxt = userdataupdater.getBg();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to load  profile", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected.equals("patient")) {
                       // Toast.makeText(getContext(), "user", Toast.LENGTH_SHORT).show();
                        nametxt = personnameed.getText().toString().trim();
                        emailidtxt = emailided.getText().toString().trim();
                        addresstxt = addressed.getText().toString().trim();
                        phonenotxt = phoneno.getText().toString().trim();
                        if (nametxt.isEmpty() || emailidtxt.isEmpty() || addresstxt.isEmpty() || phonenotxt.isEmpty()) {
                            Toast.makeText(getContext(), "Empty feilds!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            userdataupdater userdataupdater = new userdataupdater(uid,nametxt,emailidtxt,phonenotxt,"",selected,addresstxt,pbptxt,pbgtxt);
                            db.collection("Doctorappuserdata").document(uid).set(userdataupdater).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "updated profile", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else{
                       // Toast.makeText(getContext(), "farmer", Toast.LENGTH_SHORT).show();
                        nametxt = personnameed.getText().toString().trim();
                        emailidtxt = emailided.getText().toString().trim();
                        phonenotxt = phoneno.getText().toString().trim();
                        if (nametxt.isEmpty() || emailidtxt.isEmpty()) {
                            Toast.makeText(getContext(), "Empty feilds!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            doctordata doctordata = new doctordata(emailidtxt,phonenotxt,nametxt,uid,selected);
                            db.collection("Doctorappdoctordata").document(uid).set(doctordata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "updated profile", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });

        return view;
    }
}