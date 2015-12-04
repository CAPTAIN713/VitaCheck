package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by Robert on 12/3/2015.
 */
public class MedicineFragmentIndividualPage extends Fragment implements View.OnClickListener{

    private Context context;
    private MedicineInfo selectedMedicine;
    private String selectedMedicineParseId;

    private TextView medicineNameTB,medicineNoteTB,dosageAmountTB,dosagePerDayTB,prescribedByTB;
    private Button editButton;

    private String selectedItemParseID;
    private  Bundle extrasBundle;


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
        View layout = inflater.inflate(R.layout.fragment_medicine_individual_page, container, false);
        context= layout.getContext();

        medicineNameTB=(TextView) layout.findViewById(R.id.medicineNameIndv);
        medicineNoteTB=(TextView) layout.findViewById(R.id.medicineNoteIndv);
        dosageAmountTB=(TextView) layout.findViewById(R.id.dosageAmountIndv);
        dosagePerDayTB=(TextView) layout.findViewById(R.id.dosagePerDayIndv);
        prescribedByTB=(TextView) layout.findViewById(R.id.prescribedByIndv);

        editButton = (Button) layout.findViewById(R.id.editMedicineButton);

        editButton.setOnClickListener(this);

        ParseObject.registerSubclass(MedicineInfo.class);
        ParseQuery<MedicineInfo> query = ParseQuery.getQuery("medicine");
        query.getInBackground(selectedItemParseID, new GetCallback<MedicineInfo>() {
            @Override
            public void done(MedicineInfo object, ParseException e) {
                if(e==null){
                    medicineNameTB.setText(object.getName());
                    medicineNoteTB.setText(object.getNote());
                    dosageAmountTB.setText(String.valueOf(object.getDosageAmount()));
                    dosagePerDayTB.setText(String.valueOf(object.getDosagePerDay()));
                    prescribedByTB.setText(object.getPrescribedBy());
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
            case R.id.editMedicineButton: //checks if bundle is empty and if it has the parse id string

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment medicineEditFragment = new MedicineFragmentEditPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                medicineEditFragment.setArguments(extrasBundle);
                transaction.replace(R.id.medicineActivityContainer, medicineEditFragment);
                transaction.commit();
                break;
        }
    }

}//end of DoctorsFragment class