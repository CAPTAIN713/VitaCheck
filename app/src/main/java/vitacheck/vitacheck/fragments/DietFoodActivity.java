package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import vitacheck.vitacheck.R;

public class DietFoodActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_diet_food);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment dietFoodFragment = new DietFoodFragmentIndividualPage();
        //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
        dietFoodFragment.setArguments(extrasBundle);
        transaction.replace(R.id.dietFoodActivityContainer, dietFoodFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
    /*pop stuff of the fragment stack
    http://stackoverflow.com/questions/28153397/adding-fragment-to-the-addtobackstack-when-you-have-a-single-activity-with-2-fra
    http://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-android-fragments
    docs: http://developer.android.com/reference/android/app/FragmentManager.html  */
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //if at least one thing on fragment stack go back to that one
            getFragmentManager().popBackStack();
        } else {
            //if nothing else on stack exit app
            super.onBackPressed();
        }
    }
}