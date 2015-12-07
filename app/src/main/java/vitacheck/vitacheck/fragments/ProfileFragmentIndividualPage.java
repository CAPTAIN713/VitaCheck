package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vitacheck.vitacheck.R;

/**
 * Created by Robert on 12/3/2015.
 */
public class ProfileFragmentIndividualPage extends Fragment implements View.OnClickListener{

    private Context context;

    private TextView profileNameTB,profileEmailTB,profileDateOfBirthTB,profileWeightTB;
    private Button editButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile_individual_page, container, false);
        context= layout.getContext();

        profileNameTB=(TextView) layout.findViewById(R.id.profileNameIndv);
        profileEmailTB=(TextView) layout.findViewById(R.id.profileEmailIndv);
        profileDateOfBirthTB=(TextView) layout.findViewById(R.id.profileDateOfBirthIndv);
        profileWeightTB=(TextView) layout.findViewById(R.id.profileWeightIndv);

        editButton = (Button) layout.findViewById(R.id.editProfileButton);
        editButton.setOnClickListener(this);

        ParseUser currentUser = ParseUser.getCurrentUser();
        profileNameTB.setText(String.valueOf(currentUser.get("name")));
        profileEmailTB.setText(String.valueOf(currentUser.get("email")));
        profileWeightTB.setText(String.valueOf(currentUser.get("weight")));

        Date dateOfBirth = (Date)currentUser.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        profileDateOfBirthTB.setText(dateFormat.format(dateOfBirth));

        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editProfileButton: //checks if bundle is empty and if it has the parse id string

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment profileEditFragment = new ProfileFragmentEditPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                transaction.replace(R.id.profileActivityContainer, profileEditFragment);
                transaction.commit();
                break;
        }
    }

}//end of DoctorsFragment class