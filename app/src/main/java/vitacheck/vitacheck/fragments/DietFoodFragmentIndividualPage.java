package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class DietFoodFragmentIndividualPage extends Fragment implements View.OnClickListener{

    private Context context;
    private DietFoodInfo selectedDietFood;
    private String selectedDietFoodParseId;

    private TextView dietFoodNameTB,dietFoodCaloriesTB,dietFoodDateTB;
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
        View layout = inflater.inflate(R.layout.fragment_diet_food_individual_page, container, false);
        context= layout.getContext();

        dietFoodNameTB=(TextView) layout.findViewById(R.id.dietFoodNameIndv);
        dietFoodCaloriesTB=(TextView) layout.findViewById(R.id.dietFoodCaloriesIndv);
        dietFoodDateTB=(TextView) layout.findViewById(R.id.dietFoodDateIndv);
        editButton = (Button) layout.findViewById(R.id.editDietFoodButton);

        editButton.setOnClickListener(this);

        ParseObject.registerSubclass(DietFoodInfo.class);
        ParseQuery<DietFoodInfo> query = ParseQuery.getQuery("diet_food");
        query.getInBackground(selectedItemParseID, new GetCallback<DietFoodInfo>() {
            @Override
            public void done(DietFoodInfo object, ParseException e) {
                if(e==null){

                    dietFoodNameTB.setText(object.getFoodName());
                    dietFoodCaloriesTB.setText(String.valueOf(object.getFoodCalories()));
                    dietFoodDateTB.setText(DateFormat.getDateInstance().format(object.getFoodDate()));

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
            case R.id.editDietFoodButton: /*checks if bundle is empty and if it has the parse id string */

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment dietFoodEditFragment = new DietFoodFragmentEditPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                dietFoodEditFragment.setArguments(extrasBundle);
                transaction.replace(R.id.dietFoodActivityContainer, dietFoodEditFragment);
                transaction.commit();
                break;
        }
    }

}//end of DietFoodFragment class