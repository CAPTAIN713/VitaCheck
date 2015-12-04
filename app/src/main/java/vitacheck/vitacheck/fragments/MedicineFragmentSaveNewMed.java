package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
 * Created by Robert on 12/4/2015.
 */
public class MedicineFragmentSaveNewMed  extends Fragment implements View.OnClickListener {
    private Context context;
    private MedicineInfo selectedMedicine;
    private String selectedMedicineParseId;

    private EditText medicineNameTB,medicineNoteTB,dosageAmountTB,dosagePerDayTB,prescribedByTB;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_medicine_save_new_med, container, false);
        context= layout.getContext();

        medicineNameTB=(EditText) layout.findViewById(R.id.medicineNameSaveField);
        medicineNoteTB=(EditText) layout.findViewById(R.id.medicineNoteSaveField);
        dosageAmountTB=(EditText) layout.findViewById(R.id.dosageAmountSaveField);
        dosagePerDayTB=(EditText) layout.findViewById(R.id.dosagePerDaySaveField);
        prescribedByTB=(EditText) layout.findViewById(R.id.prescribedBySaveField);

        saveButton = (Button) layout.findViewById(R.id.medicineSaveNewMedicineButton);
        saveButton.setOnClickListener(this);

        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.medicineSaveNewMedicineButton:
                ParseObject.registerSubclass(MedicineInfo.class);
                MedicineInfo newMedicine= new MedicineInfo();
                if((medicineNameTB.getText().toString()).compareTo("")==0){
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }
                newMedicine.setName(medicineNameTB.getText().toString());
                newMedicine.setNote(medicineNoteTB.getText().toString());
                if((dosageAmountTB.getText().toString()).compareTo("")!=0) {
                    newMedicine.setDosageAmount(Integer.valueOf(dosageAmountTB.getText().toString()));
                }
                if((dosagePerDayTB.getText().toString()).compareTo("")!=0) {
                    newMedicine.setDosagePerDay(Integer.valueOf(dosagePerDayTB.getText().toString()));
                }
                newMedicine.setPrescribedBy(prescribedByTB.getText().toString());

                newMedicine.saveInBackground();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                Fragment fragment = new MedicineFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
                break;
        }
    }

}//end of DoctorsFragment class