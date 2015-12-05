package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
public class DietRecipeFragmentIndividualPage extends Fragment implements View.OnClickListener{

    private Context context;
    private DietRecipeInfo selectedDietRecipe;
    private String selectedDietParseParseId;

    private TextView dietRecipeNameTB,dietRecipeURLTB,dietRecipeNoteTB;
    private Button editButton;

    private String selectedItemParseID;
    private  Bundle extrasBundle;
    private String dietRecipeURL;


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
        View layout = inflater.inflate(R.layout.fragment_diet_recipe_individual_page, container, false);
        context= layout.getContext();

        dietRecipeNameTB=(TextView) layout.findViewById(R.id.dietRecipeNameIndv);
        dietRecipeURLTB=(TextView) layout.findViewById(R.id.dietRecipeURLIndv);
        dietRecipeNoteTB=(TextView) layout.findViewById(R.id.dietRecipeNoteIndv);
        editButton = (Button) layout.findViewById(R.id.editDietRecipeButton);

        editButton.setOnClickListener(this);
        dietRecipeURLTB.setOnClickListener(this);

        ParseObject.registerSubclass(DietRecipeInfo.class);
        ParseQuery<DietRecipeInfo> query = ParseQuery.getQuery("diet_recipes");
        query.getInBackground(selectedItemParseID, new GetCallback<DietRecipeInfo>() {
            @Override
            public void done(DietRecipeInfo object, ParseException e) {
                if(e==null){

                    dietRecipeNameTB.setText(object.getRecipeName());
                    dietRecipeURLTB.setText(object.getRecipeURL());
                    dietRecipeNoteTB.setText(object.getRecipeDescription());
                    dietRecipeURL=String.valueOf(object.getRecipeURL());
                }
                else{
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
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
            case R.id.editDietRecipeButton: /*checks if bundle is empty and if it has the parse id string */

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment dietRecipeEditFragment = new DietRecipeFragmentEditPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                dietRecipeEditFragment.setArguments(extrasBundle);
                transaction.replace(R.id.dietRecipeActivityContainer, dietRecipeEditFragment);
                transaction.commit();
                break;
            case R.id.dietRecipeURLIndv:
                //link to video on going to web browser: https://www.youtube.com/watch?v=9-3OCc5g5oE
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dietRecipeURL));
                    startActivity(browserIntent);
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getActivity(),"Unable to open link",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}//end of DietFoodFragment class1