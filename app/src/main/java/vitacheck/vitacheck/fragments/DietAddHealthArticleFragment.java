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
public class DietAddHealthArticleFragment  extends Fragment implements View.OnClickListener {
    private Context context;
    private DietHealthArticleInfo selectedHealthArticle;
    private String selectedHealthArticleParseId;

    private EditText healthNameTB, healthURLTB;
    private EditText healthNoteTB;
    private Button saveHealthArticleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_diet_health_add, container, false);
        context = layout.getContext();

        healthNameTB = (EditText) layout.findViewById(R.id.editHealthText);
        healthURLTB = (EditText) layout.findViewById(R.id.editURLText);
        healthNoteTB = (EditText) layout.findViewById(R.id.editDescriptionText);

        saveHealthArticleButton = (Button) layout.findViewById(R.id.saveHealthButton);
        saveHealthArticleButton.setOnClickListener(this);

        return layout;
    }

    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveHealthButton:
                ParseObject.registerSubclass(DietHealthArticleInfo.class);
                DietHealthArticleInfo health = new DietHealthArticleInfo();
                health.setUserId(GlobalVariable.getUserId(this));
                if ((healthNameTB.getText().toString()).compareTo("") == 0) {
                    Toast.makeText(context, "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;
                }
                health.setHealthName(healthNameTB.getText().toString());

                health.setHealthURL(healthURLTB.getText().toString());
                health.setHealthDescription(healthNoteTB.getText().toString());


                health.saveInBackground();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    //if at least one thing on fragment stack go back to that one
                    getFragmentManager().popBackStack();
                }


                break;
        }

    }
}