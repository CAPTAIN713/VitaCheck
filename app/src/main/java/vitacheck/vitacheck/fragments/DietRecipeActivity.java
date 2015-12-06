package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import vitacheck.vitacheck.R;

public class DietRecipeActivity extends AppCompatActivity {

    private String selectedItemParseID;
    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        extrasBundle = intentExtras.getExtras();


        if (!(extrasBundle.isEmpty()) && (extrasBundle.containsKey("parseID"))) {
            //checks if bundle is empty and if it has the parse id string
            selectedItemParseID = extrasBundle.getString("parseID");
        } else {
            //either bundle was empty or did not have parse id. should find a way to go back to previous activity
            //put finish()
        }

        setContentView(R.layout.activity_diet_recipe);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment dietRecipeFragment = new DietRecipeFragmentIndividualPage();
        //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
        dietRecipeFragment.setArguments(extrasBundle);
        transaction.replace(R.id.dietRecipeActivityContainer, dietRecipeFragment);
        transaction.commit();
    }
}