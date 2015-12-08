package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 11/29/2015.
 */
public class DietFoodFragmentEditPage extends Fragment implements View.OnClickListener {
    private Context context;
    private DietFoodInfo selectedDietFoood;
    private String selectedDietFoodParseId;

    private TextView dietFoodNameTB,dietFoodCaloriesTB,dietFoodDateTB;
    private Button saveButton;

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
        View layout = inflater.inflate(R.layout.fragment_diet_food_edit_page, container, false);
        context= layout.getContext();

        dietFoodNameTB=(EditText) layout.findViewById(R.id.dietFoodNameEditField);
        dietFoodCaloriesTB=(EditText) layout.findViewById(R.id.dietFoodCaloriesEditField);

        dietFoodDateTB=(EditText) layout.findViewById(R.id.dietFoodDateEditField);

        saveButton = (Button) layout.findViewById(R.id.dietFoodSaveEditChanges);

        saveButton.setOnClickListener(this);

        ParseObject.registerSubclass(DietFoodInfo.class);
        ParseQuery<DietFoodInfo> query = ParseQuery.getQuery("diet_food");
        query.getInBackground(selectedItemParseID, new GetCallback<DietFoodInfo>() {
            @Override
            public void done(DietFoodInfo object, ParseException e) {
                if(e==null){
                    //something went right
                    dietFoodNameTB.setText(object.getFoodName());
                    dietFoodCaloriesTB.setText(String.valueOf(object.getFoodCalories()));
                    dietFoodDateTB.setText(DateFormat.getDateInstance().format(object.getFoodDate()));
                }
                else{

                    //something went wrong
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
            case R.id.dietFoodSaveEditChanges:
                if((dietFoodNameTB.getText().toString()).compareTo("")==0){
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }


                ParseObject.registerSubclass(DietFoodInfo.class);
                ParseQuery<DietFoodInfo> query = ParseQuery.getQuery("diet_food");
                query.getInBackground(selectedItemParseID, new GetCallback<DietFoodInfo>() {
                    @Override
                    public void done(DietFoodInfo object, ParseException e) {
                        if (e == null) {
                            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                            //something went right
                            object.setFoodName((dietFoodNameTB.getText().toString()));
                            object.setFoodCalories(Integer.parseInt(dietFoodCaloriesTB.getText().toString()));
                            try {
                                Date date = format.parse(dietFoodDateTB.getText().toString());
                                object.setFoodDate(date);
                            } catch (java.text.ParseException error) {
                                // TODO Auto-generated catch block
                                error.printStackTrace();
                            }
                            object.saveInBackground();
                            Toast.makeText(context, "Saved Changes", Toast.LENGTH_SHORT).show();
                            if (getFragmentManager().getBackStackEntryCount() > 1) {
                                //if at least one thing on fragment stack go back to that one
                                getFragmentManager().popBackStack();

                            }
                        }
                    }
                });
                /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment dietFoodFragment = new DietFoodFragmentIndividualPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                dietFoodFragment.setArguments(extrasBundle);
                transaction.replace(R.id.dietFoodActivityContainer, dietFoodFragment);
                transaction.commit();*/
                break;

        }
    }

}//end of DietFoodFragment class