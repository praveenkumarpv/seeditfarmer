package com.m24.seeditfarmer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.UUID;

import helperclass.*;
import Adapter.*;

import static android.content.Context.MODE_PRIVATE;


public class myorder extends Fragment {
    String userid;
    RecyclerView orderrecycler;
    FirestoreRecyclerAdapter bookadapter;
    private FirebaseFirestore db;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public myorder() {
        // Required empty public constructor
    }

    public static myorder newInstance(String param1, String param2) {
        myorder fragment = new myorder();
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
        View view = inflater.inflate(R.layout.fragment_myorder, container, false);
        db = FirebaseFirestore.getInstance();
        orderrecycler = view.findViewById(R.id.bookingrecycler);
        SharedPreferences settings =getActivity().getSharedPreferences("preference", MODE_PRIVATE);
        userid = settings.getString("uid","");
        Query mybooking = FirebaseFirestore.getInstance().collection("Doctorappdoctordata");
        FirestoreRecyclerOptions<doctordata> drbook = new FirestoreRecyclerOptions.Builder<doctordata>()
                .setQuery(mybooking, doctordata.class)
                .build();
        bookadapter = new FirestoreRecyclerAdapter<doctordata, drbookview>(drbook){
            @NonNull
            @Override
            public drbookview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drbook,parent,false);
                return new drbookview(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull drbookview holder, int position, @NonNull doctordata model) {
              holder.drname.setText(model.getName());
              holder.book.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      UUID uuid= UUID.randomUUID();
                      String ids = String.valueOf(uuid);
//                      datepickeradpter datepickeradpter = new datepickeradpter(getActivity());
//                      datepickeradpter.date();
                      final Calendar c = Calendar.getInstance();
                      int mYear = c.get(Calendar.YEAR); // current year
                      int mMonth = c.get(Calendar.MONTH); // current month
                      int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                      // date picker dialog
                      datePickerDialog = new DatePickerDialog(getActivity(),
                              new DatePickerDialog.OnDateSetListener() {

                                  @Override
                                  public void onDateSet(DatePicker view, int year,
                                                        int monthOfYear, int dayOfMonth) {
                                      // set day of month , month and year value in the edit text
//                                      date.setText(dayOfMonth + "/"
//                                              + (monthOfYear + 1) + "/" + year);
                                      String date = dayOfMonth +"-"+(monthOfYear + 1)+"-"+year;

                                      Calendar mcurrentTime = Calendar.getInstance();
                                      int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                      int minute = mcurrentTime.get(Calendar.MINUTE);
                                      TimePickerDialog mTimePicker;
                                      mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                          @Override
                                          public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                                              time.setText(selectedHour + ":" + selectedMinute);
                                              String time = selectedHour +":"+selectedMinute;
                                              Toast.makeText(getContext(), time, Toast.LENGTH_SHORT).show();
                                              Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
                                              bookingupdater book = new bookingupdater(userid, model.getUid(),date,time,ids);
                                              db.collection("booking").document(ids).set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void unused) {
                                                      Toast.makeText(getContext(), "Booking Successful", Toast.LENGTH_SHORT).show();
                                                  }
                                              }).addOnFailureListener(new OnFailureListener() {
                                                  @Override
                                                  public void onFailure(@NonNull Exception e) {
                                                      Toast.makeText(getContext(), "Booking Failed", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }
                                      }, hour, minute, true);//Yes 24 hour time
                                      mTimePicker.setTitle("Select Time");
                                      mTimePicker.show();
                                  }
                              }, mYear, mMonth, mDay);
                      datePickerDialog.show();

                  }
              });
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        orderrecycler.setLayoutManager(layoutManager);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        orderrecycler.setItemAnimator(null);
        orderrecycler.setAdapter(bookadapter);
        return view;
    }

    private class drbookview extends RecyclerView.ViewHolder {
        TextView drname;
        Button book;
        public drbookview(@NonNull View itemView) {
            super(itemView);
            drname = itemView.findViewById(R.id.drname);
            book= itemView.findViewById(R.id.drbookbutton);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        bookadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bookadapter.stopListening();
    }
}