package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 11/28/2015.
 */
public class DoctorFragmentIndividualPage extends Fragment implements View.OnClickListener{

    private Context context;
    private DoctorInfo selectedDoctor;
    private String selectedDoctorParseId;

    private TextView doctorNameTB,doctorTypeTB,insuranceTB,phoneNumberTB,emailTB;
    private TextView addressTB,websiteTB,visitDateTB;
    private Button editButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_doctor_indivual_page, container, false);
        context= layout.getContext();

        doctorNameTB=(TextView) layout.findViewById(R.id.doctorNameIndv);
        doctorTypeTB=(TextView) layout.findViewById(R.id.doctorTypeIndv);
        insuranceTB=(TextView) layout.findViewById(R.id.doctorInsuranceIndv);
        phoneNumberTB=(TextView) layout.findViewById(R.id.doctorPhoneNumberIndv);
        //phoneNumberTB.setOnClickListener(this);
        //http://stackoverflow.com/questions/8599657/dialing-a-phone-call-on-click-of-textview-in-android
        emailTB=(TextView) layout.findViewById(R.id.doctorEmailIndv);
        addressTB=(TextView) layout.findViewById(R.id.doctorAddressIndv);
        websiteTB=(TextView) layout.findViewById(R.id.doctorWebsiteIndv);
        visitDateTB=(TextView) layout.findViewById(R.id.doctorVistDatesIndv);

        editButton = (Button) layout.findViewById(R.id.editDoctorButton);

        editButton.setOnClickListener(this);

        ParseObject.registerSubclass(DoctorInfo.class);
        ParseQuery<DoctorInfo> query = ParseQuery.getQuery("doctor");
        query.getInBackground("r9wx5IWNWY", new GetCallback<DoctorInfo>() {
            @Override
            public void done(DoctorInfo object, ParseException e) {
                if(e==null){
                    doctorNameTB.setText(object.getName());
                    doctorTypeTB.setText(object.getDoctorType());
                    insuranceTB.setText(object.getInsurance());
                    phoneNumberTB.setText(String.valueOf(object.getPhoneNum()));
                    emailTB.setText(object.getEmail());
                    addressTB.setText(object.getAddress());
                    websiteTB.setText(object.getURL());
                    visitDateTB.setText(DateFormat.getDateInstance().format(object.getVisitDate()));
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
            case R.id.editDoctorButton:
                Toast.makeText(context,"edit button",Toast.LENGTH_SHORT).show();
                break;

        }
    }

}//end of DoctorsFragment class