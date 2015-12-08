package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;

import vitacheck.vitacheck.LoginActivity;
import vitacheck.vitacheck.R;

import java.util.Calendar;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



import android.app.Activity;

import android.app.AlarmManager;

import android.app.PendingIntent;

import android.content.Intent;

import android.os.Bundle;

import android.os.SystemClock;

import android.view.View;

import android.widget.Button;

import android.widget.Toast;

/**
 * Created by Robert on 12/3/2015.
 */
public class MedicineFragmentEditPage extends Fragment implements View.OnClickListener {
    private Context context;
    private MedicineInfo selectedMedicine;
    private String selectedMedicineParseId;

    private EditText medicineNameTB,medicineNoteTB,dosageAmountTB,dosagePerDayTB,prescribedByTB;
    private Button saveButton;
    private CheckBox reminderCheckBox;
    private AlarmTimePicker medicineTimePicker;

    private String selectedItemParseID;
    private Bundle extrasBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        extrasBundle = getArguments();

        if( !(extrasBundle.isEmpty()) && (extrasBundle.containsKey("parseID")) ){
            //checks if bundle is empty and if it has the parse id string
            selectedItemParseID=extrasBundle.getString("parseID");
        }
        else{
            //either bundle was empty or did not have parse id. should find a way to go back to previous activity
            //put finish()
        }

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_medicine_edit_page, container, false);
        context= layout.getContext();

        medicineNameTB=(EditText) layout.findViewById(R.id.medicineNameEditField);
        medicineNoteTB=(EditText) layout.findViewById(R.id.medicineNoteEditField);
        dosageAmountTB=(EditText) layout.findViewById(R.id.dosageAmountEditField);
        dosagePerDayTB=(EditText) layout.findViewById(R.id.dosagePerDayEditField);
        prescribedByTB=(EditText) layout.findViewById(R.id.prescribedByEditField);

        saveButton = (Button) layout.findViewById(R.id.medicineSaveEditChanges);
        reminderCheckBox = (CheckBox) layout.findViewById(R.id.reminderCheckBox);
        reminderCheckBox.setOnClickListener(this);

        medicineTimePicker = (AlarmTimePicker) layout.findViewById(R.id.medicineTimePicker);

        saveButton.setOnClickListener(this);

        ParseObject.registerSubclass(MedicineInfo.class);
        ParseQuery<MedicineInfo> query = ParseQuery.getQuery("medicine");
        query.getInBackground(selectedItemParseID, new GetCallback<MedicineInfo>() {
            @Override
            public void done(MedicineInfo object, ParseException e) {
                if(e==null){
                    //something went right
                    medicineNameTB.setText(object.getName());
                    medicineNoteTB.setText(object.getNote());
                    dosageAmountTB.setText(String.valueOf(object.getDosageAmount()));
                    dosagePerDayTB.setText(String.valueOf(object.getDosagePerDay()));
                    prescribedByTB.setText(object.getPrescribedBy());
                    int alarmHour =  object.getAlarmHour();
                    int alarmMinute = object.getAlarmMinute();
                    if(alarmHour != -1 && alarmMinute != -1){
                        medicineTimePicker.setCurrentHour(alarmHour);
                        medicineTimePicker.setCurrentMinute(alarmMinute);
                    }
                }
                else{
                    //someting went wrong
                }
            }
        });
        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.medicineSaveEditChanges:
                if((medicineNameTB.getText().toString()).compareTo("")==0){
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }

                int alarmHour = -1;
                int alarmMinute = -1;
                if(reminderCheckBox.isChecked()) {
                    Context baseContext = getActivity().getApplicationContext();

                    Intent intent = new Intent(context, MedicineActivityAlarmPopup.class);
                    intent.putExtra("name", medicineNameTB.getText().toString());
                    intent.putExtra("dosage", dosageAmountTB.getText().toString());

                    int id = new Random().nextInt(100);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Calendar alarmCalendar = Calendar.getInstance();
                    int timePickerMinute = medicineTimePicker.getCurrentMinute();
                    int timePickerHour = medicineTimePicker.getCurrentHour();
                    //If picked time has already passed, "roll" date forward.
                    if (timePickerHour < alarmCalendar.get(Calendar.HOUR_OF_DAY))
                        alarmCalendar.roll(Calendar.DATE, true);
                    else if (timePickerHour == alarmCalendar.get(Calendar.HOUR)) {
                        if (timePickerMinute < alarmCalendar.get(Calendar.MINUTE))
                            alarmCalendar.roll(Calendar.DATE, true);
                        else if (timePickerMinute == alarmCalendar.get(Calendar.MINUTE))
                            alarmCalendar.add(Calendar.SECOND, 5);
                    }

                    alarmCalendar.set(Calendar.HOUR, timePickerHour);
                    alarmCalendar.set(Calendar.MINUTE, timePickerMinute);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
                    alarmHour = alarmCalendar.get(Calendar.HOUR_OF_DAY);
                    alarmMinute = alarmCalendar.get(Calendar.MINUTE);

                    Toast.makeText(context, "Alarm set for " + alarmCalendar.getTime(), Toast.LENGTH_LONG).show();
                }

                final int finalHour = alarmHour;
                final int finalMinute = alarmMinute;

                ParseObject.registerSubclass(MedicineInfo.class);
                ParseQuery<MedicineInfo> query = ParseQuery.getQuery("medicine");
                query.getInBackground(selectedItemParseID, new GetCallback<MedicineInfo>() {
                    @Override
                    public void done(MedicineInfo object, ParseException e) {
                        if (e == null) {
                            //something went right
                            object.setName(medicineNameTB.getText().toString());
                            object.setNote(medicineNoteTB.getText().toString());
                            object.setDosageAmount(Integer.parseInt(dosageAmountTB.getText().toString()));
                            object.setDosagePerDay(Integer.parseInt(dosagePerDayTB.getText().toString()));
                            object.setPrescribedBy(prescribedByTB.getText().toString());
                            if(finalHour != -1) {
                                object.setAlarmHour(finalHour);
                                object.setAlarmMinute(finalMinute);
                            }
                            else{
                                object.setAlarmHour(-1);
                                object.setAlarmMinute(-1);
                            }
                            object.saveInBackground();
                            Toast.makeText(context, "Saved Changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment medicineFragment = new MedicineFragmentIndividualPage();
                //video on passing bundles to fragments https://samplewww.youtube.com/watch?v=Je9A8lxGDLY
                medicineFragment.setArguments(extrasBundle);
                transaction.replace(R.id.medicineActivityContainer, medicineFragment);
                transaction.commit();
                break;
        }
    }
}//end of DoctorsFragment class