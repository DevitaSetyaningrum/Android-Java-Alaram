package com.example.alaram;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvOneTimeDate, tvOneTimeTime;
    private TextView tvRepeatingTime;
    private EditText edtOneTimeMessege, edtRepeatingMessege;
    private Button btnOneTimeDate, btnOneTimeTime, btnOneTime, btnRepeatingTime, btnRepeating, btnCancelRepeatingAlaram;
    private Calendar calOneTimeDate, calOneTimeTime, calRepeatTimeTime;

    private AlaramReceiver alaramReceiver;
    private AlaramPreference alaramPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOneTimeDate =(TextView) findViewById(R.id.tv_date);
        tvOneTimeTime = (TextView) findViewById(R.id.tv_time);
        btnOneTimeDate = (Button) findViewById(R.id.btn_date);
        btnOneTimeTime = (Button) findViewById(R.id.btn_time);
        btnOneTime = (Button) findViewById(R.id.btn_setalaram);
        tvRepeatingTime = (TextView) findViewById(R.id.tv_Repeat);
        edtOneTimeMessege = (EditText) findViewById(R.id.edt_message);
        btnRepeatingTime = (Button) findViewById(R.id.btn_repeating_time);
        btnRepeating = (Button) findViewById(R.id.btn_repeating);
        btnCancelRepeatingAlaram = (Button) findViewById(R.id.btn_cancel);

        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
        btnCancelRepeatingAlaram.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        alaramPreference = new AlaramPreference(this);
        alaramPreference = new AlaramPreference(this);


        if (!TextUtils.isEmpty(alaramPreference.getOneTimeDate())){
            setOneTimeText();
        }

        if (!TextUtils.isEmpty(alaramPreference.getOneRepeatingTime())){
            setRepeatingText ();
        }
    }



    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_date){
            final Calendar currentDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calOneTimeDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    tvOneTimeDate.setText(dateFormat.format(calOneTimeDate.getTime()));

                }
            }
            ,currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

        } else if (v.getId() == R.id.btn_time){
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calOneTimeTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:MM");
                    tvOneTimeTime.setText(dateFormat.format(calOneTimeTime.getTime()));

                }
            }, currentDate.get(Calendar.HOUR_OF_DAY),currentDate.get(Calendar.MINUTE),true).show();

        } else if (v.getId() == R.id.btn_setalaram){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
            String oneTimeMessage = edtOneTimeMessege.getText().toString();

            alaramPreference.setOneTimeDate(oneTimeDate);
            alaramPreference.setOneTimeMessage(oneTimeMessage);
            alaramPreference.setOneTimeTime(oneTimeTime);

            alaramReceiver.setOneTimeAlaram(this,AlaramReceiver.TYPE_ONE_TIME,
                    alaramPreference.getOneTimeDate(),
                    alaramPreference.getOneTimeTime(),
                    alaramPreference.getOneTimeMessage());
        }

    }

    private void setOneTimeText() {
        tvOneTimeTime.setText(alaramPreference.getOneTimeTime());
        tvOneTimeDate.setText(alaramPreference.getOneTimeDate());
        edtOneTimeMessege.setText(alaramPreference.getOneTimeMessage());
    }

    private void setRepeatingText() {

        tvRepeatingTime.setText(alaramPreference.getOneTimeTime());
        edtRepeatingMessege.setText(alaramPreference.getOneRepeatingMessage());
    }

}
