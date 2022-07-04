package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.m24.seeditfarmer.R;

public class datepickeradpter {
    DatePicker datePicker;
    String date;
    Activity activity;
    AlertDialog dialog;

    public datepickeradpter(Activity activity) {
        this.activity = activity;
    }

    public String date(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
//        builder.setCancelable(true);
        View v = inflater.inflate(R.layout.datepicker,null);
        builder.setView(v);

        dialog = builder.create();
        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        return date;
    }
    public void stop(){
        dialog.dismiss();
    }
}
