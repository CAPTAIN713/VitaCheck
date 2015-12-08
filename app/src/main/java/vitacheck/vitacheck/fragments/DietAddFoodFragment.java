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

import java.text.SimpleDateFormat;
import java.util.Date;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 11/30/2015.
 */
public class DietAddFoodFragment  extends Fragment implements View.OnClickListener {
    private Context context;
    private DoctorInfo selectedDoctor;
    private String selectedDoctorParseId;

    private EditText foodNameTB, foodCaloriesTB;
    private EditText  foodDateTB;
    private Button saveFoodButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_diet_food_add, container, false);
        context = layout.getContext();

        foodNameTB = (EditText) layout.findViewById(R.id.editFoodText);
        foodCaloriesTB = (EditText) layout.findViewById(R.id.editCaloriesText);
        foodDateTB = (EditText) layout.findViewById(R.id.editDateText);

        saveFoodButton = (Button) layout.findViewById(R.id.saveFoodButton);
        saveFoodButton.setOnClickListener(this);

        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveFoodButton:
                ParseObject.registerSubclass(DietFoodInfo.class);
                DietFoodInfo food = new DietFoodInfo();
                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                if ((foodNameTB.getText().toString()).compareTo("") == 0) {
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }
                food.setFoodName(foodNameTB.getText().toString());
                if ((foodCaloriesTB.getText().toString()).compareTo("") != 0) {
                    food.setFoodCalories(Integer.valueOf(foodCaloriesTB.getText().toString()));
                }

                try {
                    Date date = format.parse(foodDateTB.getText().toString());
                    food.setFoodDate(date);
                } catch (java.text.ParseException error) {
                    // TODO Auto-generated catch block
                    error.printStackTrace();
                }

                food.setUserId(GlobalVariable.getUserId(this));
                food.saveInBackground();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    //if at least one thing on fragment stack go back to that one
                    getFragmentManager().popBackStack();
                }


                break;
        }
    }

}