package vitacheck.vitacheck;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    EditText mNameText;
    EditText mEmailText;
    EditText mPasswordText;
    EditText mDoBText;
    EditText mWeightText;
    ProgressDialog mDialog;

    boolean slash = false;
    boolean dash = false;
    SimpleDateFormat slashformat;
    SimpleDateFormat dashformat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNameText = (EditText) findViewById(R.id.input_name);
        mEmailText = (EditText) findViewById(R.id.input_email);
        mPasswordText = (EditText) findViewById(R.id.input_password);
        mWeightText = (EditText) findViewById(R.id.input_weight);
        mDoBText = (EditText) findViewById(R.id.input_dob);

        Button createAccountButton = (Button) findViewById(R.id.btn_create);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    public void signup() {

        hideSoftKeyboard();

        if (!validate())
            return;

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Registering...");
        mDialog.setCancelable(false);
        mDialog.show();


        Date date = null;

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String sWeight = mWeightText.getText().toString();
        Double weight = Double.parseDouble(sWeight);

        if (dash)
            try {date = dashformat.parse(mDoBText.getText().toString());}
            catch (Exception e) {}

        if(slash)
            try {date = slashformat.parse(mDoBText.getText().toString());}
            catch (Exception e) {}

        // Save new user data into Parse.com Data Storage
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.put("name", name);
        user.put("date", date);
        user.put("weight", weight);
        user.signUpInBackground();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        mDialog.dismiss();
        Toast.makeText(getBaseContext(), "Account Created!", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, null);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String dateofbirth = mDoBText.getText().toString();
        String sweight = mWeightText.getText().toString();

        try {
            slashformat = new SimpleDateFormat("MM/dd/yyyy");
            slash = true;
        } catch (Exception e) {}

        try {
            dashformat = new SimpleDateFormat("MM-dd-yyyy");
            dash = true;
        } catch (Exception e) {}

        if (name.isEmpty() || name.length() < 3) {
            mNameText.setError("at least 3 characters");
            valid = false;
        } else {
            mNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        if (dateofbirth.isEmpty() || (!slash && !dash)) {
            mDoBText.setError("enter a valid date");
            valid = false;
        } else {
            mDoBText.setError(null);
        }

        if (sweight.isEmpty()) {
            mWeightText.setError("between 100 and 400");
            valid = false;
        } else {
            Double weight = Double.parseDouble(sweight);
            if (weight < 100.00 || weight > 400.00) {
                mWeightText.setError("between 100 and 400");
                valid = false;
            } else {
                mWeightText.setError(null);
            }
        }

        return valid;
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
