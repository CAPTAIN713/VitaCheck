package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;

import vitacheck.vitacheck.LoginActivity;
import vitacheck.vitacheck.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



import android.app.Activity;

import android.app.AlarmManager;

import android.app.PendingIntent;

import android.content.Intent;

import android.os.Bundle;

import android.os.SystemClock;

import android.view.View;

import android.widget.Button;

import android.widget.Toast;

/**
 * Created by Robert on 12/3/2015.
 */
public class ProfileFragmentEditPage extends Fragment implements View.OnClickListener {
    private Context context;
    private ParseUser currentUser;

    private EditText profileNameTB, profileEmailTB, profilePasswordTB, profileDateOfBirthTB, profileWeightTB;
    private Button saveButton;

    boolean slash = false;
    boolean dash = false;
    SimpleDateFormat slashformat;
    SimpleDateFormat dashformat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentUser = ParseUser.getCurrentUser();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile_edit_page, container, false);
        context = layout.getContext();

        profileNameTB = (EditText) layout.findViewById(R.id.profileNameEditField);
        profileEmailTB = (EditText) layout.findViewById(R.id.profileEmailEditField);
        profilePasswordTB = (EditText) layout.findViewById(R.id.profilePasswordEditField);
        profileDateOfBirthTB = (EditText) layout.findViewById(R.id.profileDateOfBirthEditField);
        profileWeightTB = (EditText) layout.findViewById(R.id.profileWeightEditField);

        saveButton = (Button) layout.findViewById(R.id.profileSaveEditChanges);
        saveButton.setOnClickListener(this);

        profileNameTB.setText(String.valueOf(currentUser.get("name")));
        profileEmailTB.setText(String.valueOf(currentUser.get("email")));
        profileWeightTB.setText(String.valueOf(currentUser.get("weight")));

        Date dateOfBirth = (Date) currentUser.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        profileDateOfBirthTB.setText(dateFormat.format(dateOfBirth));
        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileSaveEditChanges:
                if(!validate())
                    break;
                currentUser.put("name", profileNameTB.getText().toString());
                currentUser.put("email", profileEmailTB.getText().toString());
                if(!profilePasswordTB.getText().toString().isEmpty())
                    currentUser.put("password", profilePasswordTB.getText().toString());
                Date date = null;
                if (dash)
                    try {
                        date = dashformat.parse(profileDateOfBirthTB.getText().toString());
                        currentUser.put("date", date);
                    }
                    catch (Exception e) {}

                if(slash)
                    try {
                        date = slashformat.parse(profileDateOfBirthTB.getText().toString());
                        currentUser.put("date", date);
                    }
                    catch (Exception e) {}
                currentUser.put("weight", Double.parseDouble(profileWeightTB.getText().toString()));
                currentUser.saveInBackground();
                Toast.makeText(context, "Saved Changes", Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment profileFragment = new ProfileFragmentIndividualPage();
                transaction.replace(R.id.profileActivityContainer, profileFragment);
                transaction.commit();
                break;
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = profileNameTB.getText().toString();
        String email = profileEmailTB.getText().toString();
        String password = profilePasswordTB.getText().toString();
        String dateofbirth = profileDateOfBirthTB.getText().toString();
        String sweight = profileWeightTB.getText().toString();

        try {
            slashformat = new SimpleDateFormat("MM/dd/yyyy");
            slash = true;
        } catch (Exception e) {
        }

        try {
            dashformat = new SimpleDateFormat("MM-dd-yyyy");
            dash = true;
        } catch (Exception e) {
        }

        if (name.isEmpty() || name.length() < 3) {
            profileNameTB.setError("at least 3 characters");
            valid = false;
        } else {
            profileNameTB.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            profileEmailTB.setError("enter a valid email address");
            valid = false;
        } else {
            profileEmailTB.setError(null);
        }

        if (!password.isEmpty() && (password.length() < 4 || password.length() > 10)) {
            profilePasswordTB.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            profilePasswordTB.setError(null);
        }

        if (!dateofbirth.isEmpty() && (!slash && !dash)) {
            profileDateOfBirthTB.setError("enter a valid date");
            valid = false;
        } else {
            profileDateOfBirthTB.setError(null);
        }

        Double weight = Double.parseDouble(sweight);
        if (!sweight.isEmpty() && (weight < 100.00 || weight > 400.00)) {
            profileWeightTB.setError("between 100 and 400");
            valid = false;
        } else {
            profileWeightTB.setError(null);
        }

        return valid;
    }
}