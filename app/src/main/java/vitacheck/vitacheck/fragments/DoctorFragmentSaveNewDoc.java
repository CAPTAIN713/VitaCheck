package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 11/30/2015.
 */
public class DoctorFragmentSaveNewDoc  extends Fragment implements View.OnClickListener {
    private Context context;
    private DoctorInfo selectedDoctor;
    private String selectedDoctorParseId;

    private EditText doctorNameTB,doctorTypeTB,insuranceTB,phoneNumberTB,emailTB;
    private EditText addressTB,websiteTB,visitDateTB;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_doctor_save_new_doc, container, false);
        context= layout.getContext();

        doctorNameTB=(EditText) layout.findViewById(R.id.doctorSaveNameField);
        doctorTypeTB=(EditText) layout.findViewById(R.id.doctorSaveTypeField);
        insuranceTB=(EditText) layout.findViewById(R.id.doctorSaveInsuranceField);
        phoneNumberTB=(EditText) layout.findViewById(R.id.doctorSavePhoneNumberField);
        emailTB=(EditText) layout.findViewById(R.id.doctorSaveEmailField);
        addressTB=(EditText) layout.findViewById(R.id.doctorSaveAddressField);
        websiteTB=(EditText) layout.findViewById(R.id.doctorSaveWebsiteField);
        visitDateTB=(EditText) layout.findViewById(R.id.doctorSaveVistDateField);

        saveButton = (Button) layout.findViewById(R.id.doctorSaveNewDoctorButton);
        saveButton.setOnClickListener(this);

        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.doctorSaveNewDoctorButton:
                ParseObject.registerSubclass(DoctorInfo.class);
                DoctorInfo newDoctor= new DoctorInfo();
                if((doctorNameTB.getText().toString()).compareTo("")==0){
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }
                newDoctor.setName(doctorNameTB.getText().toString());
                newDoctor.setDoctorType(doctorTypeTB.getText().toString());
                newDoctor.setInsurance(insuranceTB.getText().toString());
                if((phoneNumberTB.getText().toString()).compareTo("")!=0) {
                    newDoctor.setPhoneNum(Long.valueOf(phoneNumberTB.getText().toString()));
                }
                newDoctor.setEmail(emailTB.getText().toString());
                newDoctor.setAddress(addressTB.getText().toString());
                newDoctor.setURL(websiteTB.getText().toString());
                //newDoctor.put(visitDateTB.getText().toString());

                newDoctor.saveInBackground();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                break;
        }
    }

}//end of DoctorsFragment class