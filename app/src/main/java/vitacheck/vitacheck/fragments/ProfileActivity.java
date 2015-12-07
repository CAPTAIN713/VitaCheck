package vitacheck.vitacheck.fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import vitacheck.vitacheck.R;

public class ProfileActivity extends AppCompatActivity {

    private Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        extrasBundle = intentExtras.getExtras();

        setContentView(R.layout.activity_profile);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment profileFragment = new ProfileFragmentIndividualPage();
        //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
        profileFragment.setArguments(extrasBundle);
        transaction.replace(R.id.profileActivityContainer, profileFragment);
        transaction.commit();
    }
}
