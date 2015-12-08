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
public class DietAddRecipeFragment  extends Fragment implements View.OnClickListener {
    private Context context;
    private DietRecipeInfo selectedRecipe;
    private String selectedRecipeParseId;

    private EditText recipeNameTB, recipeURLTB;
    private EditText recipeNoteTB;
    private Button saveRecipeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_diet_recipe_add, container, false);
        context = layout.getContext();

        recipeNameTB = (EditText) layout.findViewById(R.id.editRecipeText);
        recipeURLTB = (EditText) layout.findViewById(R.id.editURLText);
        recipeNoteTB = (EditText) layout.findViewById(R.id.editDescriptionText);

        saveRecipeButton = (Button) layout.findViewById(R.id.saveRecipeButton);
        saveRecipeButton.setOnClickListener(this);

        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveRecipeButton:
                ParseObject.registerSubclass(DietRecipeInfo.class);
                DietRecipeInfo recipe = new DietRecipeInfo();

                if ((recipeNameTB.getText().toString()).compareTo("") == 0) {
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }
                recipe.setRecipeName(recipeNameTB.getText().toString());

                recipe.setRecipeURL(recipeURLTB.getText().toString());
                recipe.setRecipeDescription(recipeNoteTB.getText().toString());

                recipe.setUserId(GlobalVariable.getUserId(this));
                recipe.saveInBackground();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    //if at least one thing on fragment stack go back to that one
                    getFragmentManager().popBackStack();
                }


                break;
        }

    }
}