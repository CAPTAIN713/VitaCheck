package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import vitacheck.vitacheck.R;

public class DoctorActivity extends AppCompatActivity {

    private String selectedItemParseID;
    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        extrasBundle = intentExtras.getExtras();

        if( !(extrasBundle.isEmpty()) && (extrasBundle.containsKey("parseID")) ){
            //checks if bundle is empty and if it has the parse id string
            selectedItemParseID=extrasBundle.getString("parseID");
        }
        else{
            //either bundle was empty or did not have parse id. should find a way to go back to previous activity
            //put finish()
        }

        setContentView(R.layout.activity_doctor);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment doctorFragment = new DoctorFragmentIndividualPage();
        //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
        doctorFragment.setArguments(extrasBundle);
        transaction.replace(R.id.doctorActivityContainer,doctorFragment);
        transaction.commit();
    }
}
