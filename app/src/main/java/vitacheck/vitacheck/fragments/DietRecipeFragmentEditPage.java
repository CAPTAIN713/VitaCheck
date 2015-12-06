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
public class DietRecipeFragmentEditPage extends Fragment implements View.OnClickListener {
    private Context context;
    private DietFoodInfo selectedDietFoood;
    private String selectedDietFoodParseId;

    private TextView dietRecipeNameTB,dietRecipeURLTB,dietRecipeNotesTB;
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
        View layout = inflater.inflate(R.layout.fragment_diet_recipe_edit_page, container, false);
        context= layout.getContext();

        dietRecipeNameTB=(EditText) layout.findViewById(R.id.dietRecipeNameEditField);
        dietRecipeURLTB=(EditText) layout.findViewById(R.id.dietRecipeURLEditField);
        dietRecipeNotesTB=(EditText) layout.findViewById(R.id.dietRecipeNoteEditField);

        saveButton = (Button) layout.findViewById(R.id.dietRecipeSaveEditChanges);

        saveButton.setOnClickListener(this);

        ParseObject.registerSubclass(DietRecipeInfo.class);
        ParseQuery<DietRecipeInfo> query = ParseQuery.getQuery("diet_recipes");
        query.getInBackground(selectedItemParseID, new GetCallback<DietRecipeInfo>() {
            @Override
            public void done(DietRecipeInfo object, ParseException e) {
                if(e==null){
                    //something went right
                    dietRecipeNameTB.setText(object.getRecipeName());
                    dietRecipeURLTB.setText(object.getRecipeURL());
                    dietRecipeNotesTB.setText(object.getRecipeDescription());
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
            case R.id.dietRecipeSaveEditChanges:
                if((dietRecipeNameTB.getText().toString()).compareTo("")==0){
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }


                ParseObject.registerSubclass(DietRecipeInfo.class);
                ParseQuery<DietRecipeInfo> query = ParseQuery.getQuery("diet_recipes");
                query.getInBackground(selectedItemParseID, new GetCallback<DietRecipeInfo>() {
                    @Override
                    public void done(DietRecipeInfo object, ParseException e) {
                        if (e == null) {
                            //something went right
                            object.setRecipeName((dietRecipeNameTB.getText().toString()));
                            object.setRecipeURL((dietRecipeURLTB.getText().toString()));
                            object.setRecipeDescription((dietRecipeNotesTB.getText().toString()));

                            object.saveInBackground();
                            Toast.makeText(context, "Saved Changes", Toast.LENGTH_SHORT).show();
                            if (getFragmentManager().getBackStackEntryCount() > 1) {
                                //if at least one thing on fragment stack go back to that one
                                getFragmentManager().popBackStack();

                            }
                        }
                    }
                });
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment dietRecipeFragment = new DietRecipeFragmentIndividualPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                dietRecipeFragment.setArguments(extrasBundle);
                transaction.replace(R.id.dietRecipeActivityContainer, dietRecipeFragment);
                transaction.commit();
                break;

        }
    }

}//end of DietFoodFragment class