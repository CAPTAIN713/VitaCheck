package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 11/29/2015.
 */
public class DoctorFragmentEditPage extends Fragment implements View.OnClickListener {
    private Context context;
    private DoctorInfo selectedDoctor;
    private String selectedDoctorParseId;

    private EditText doctorNameTB,doctorTypeTB,insuranceTB,phoneNumberTB,emailTB;
    private EditText addressTB,websiteTB,visitDateTB;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_doctor_edit_page, container, false);
        context= layout.getContext();

        doctorNameTB=(EditText) layout.findViewById(R.id.doctorNameEditField);
        doctorTypeTB=(EditText) layout.findViewById(R.id.doctorTypeEditField);
        insuranceTB=(EditText) layout.findViewById(R.id.doctorInsuranceEditField);
        phoneNumberTB=(EditText) layout.findViewById(R.id.doctorPhoneNumberEditField);
        emailTB=(EditText) layout.findViewById(R.id.doctorEmailEditField);
        addressTB=(EditText) layout.findViewById(R.id.doctorAddressEditField);
        websiteTB=(EditText) layout.findViewById(R.id.doctorWebsiteEditField);
        visitDateTB=(EditText) layout.findViewById(R.id.doctorVistDateEditField);

        saveButton = (Button) layout.findViewById(R.id.doctorSaveEditChanges);

        saveButton.setOnClickListener(this);

        ParseObject.registerSubclass(DoctorInfo.class);
        ParseQuery<DoctorInfo> query = ParseQuery.getQuery("doctor");
        query.getInBackground("OslWwXOJfA", new GetCallback<DoctorInfo>() {
            @Override
            public void done(DoctorInfo object, ParseException e) {
                if(e==null){
                    //something went right
                    doctorNameTB.setText(object.getName());
                    doctorTypeTB.setText(object.getDoctorType());
                    insuranceTB.setText(object.getInsurance());
                    phoneNumberTB.setText(String.valueOf(object.getPhoneNum()));
                    emailTB.setText(object.getEmail());
                    addressTB.setText(object.getAddress());
                    websiteTB.setText(object.getURL());
                    //visitDateTB.setText(DateFormat.getDateInstance().format(object.getVisitDate()));
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
            case R.id.doctorSaveEditChanges:
                if((doctorNameTB.getText().toString()).compareTo("")==0){
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                }
                ParseObject.registerSubclass(DoctorInfo.class);
                ParseQuery<DoctorInfo> query = ParseQuery.getQuery("doctor");
                query.getInBackground("OslWwXOJfA", new GetCallback<DoctorInfo>() {
                    @Override
                    public void done(DoctorInfo object, ParseException e) {
                        if (e == null) {
                            //something went right
                            object.setName((doctorNameTB.getText().toString()));
                            object.setDoctorType(doctorTypeTB.getText().toString());
                            object.setInsurance(insuranceTB.getText().toString());
                            object.setPhoneNum(Long.parseLong(phoneNumberTB.getText().toString()));
                            object.setEmail(emailTB.getText().toString());
                            object.setAddress(addressTB.getText().toString());
                            object.setURL(websiteTB.getText().toString());
                            //object.setVisitDate(visitDateTB.getText().toString());
                            object.saveInBackground();
                            Toast.makeText(context, "Saved Changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

        }
    }

}//end of DoctorsFragment class