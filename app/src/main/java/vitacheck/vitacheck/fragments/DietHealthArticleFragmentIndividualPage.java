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
public class DietHealthArticleFragmentIndividualPage extends Fragment implements View.OnClickListener{

    private Context context;
    private DietHealthArticleInfo selectedDietHealth;
    private String selectedDietParseParseId;

    private TextView dietHealthNameTB,dietHealthURLTB,dietHealthNoteTB;
    private Button editButton;

    private String selectedItemParseID;
    private  Bundle extrasBundle;
    private String dietHealthURL;


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
        View layout = inflater.inflate(R.layout.fragment_diet_health_individual_page, container, false);
        context= layout.getContext();

        dietHealthNameTB=(TextView) layout.findViewById(R.id.dietHealthNameIndv);
        dietHealthURLTB=(TextView) layout.findViewById(R.id.dietHealthURLIndv);
        dietHealthNoteTB=(TextView) layout.findViewById(R.id.dietHealthNoteIndv);
        editButton = (Button) layout.findViewById(R.id.editDietHealthButton);

        editButton.setOnClickListener(this);
        dietHealthURLTB.setOnClickListener(this);

        ParseObject.registerSubclass(DietHealthArticleInfo.class);
        ParseQuery<DietHealthArticleInfo> query = ParseQuery.getQuery("diet_health_article");
        query.getInBackground(selectedItemParseID, new GetCallback<DietHealthArticleInfo>() {
            @Override
            public void done(DietHealthArticleInfo object, ParseException e) {
                if(e==null){

                    dietHealthNameTB.setText(object.getHealthName());
                    dietHealthURLTB.setText(object.getHealthURL());
                    dietHealthNoteTB.setText(object.getHealthDescription());
                    dietHealthURL=String.valueOf(object.getHealthURL());
                }
                else{
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
            case R.id.editDietHealthButton: /*checks if bundle is empty and if it has the parse id string */
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment dietHealthEditFragment = new DietHealthArticleFragmentEditPage();
                //video on passing bundles to fragments https://www.youtube.com/watch?v=Je9A8lxGDLY
                dietHealthEditFragment.setArguments(extrasBundle);
                transaction.replace(R.id.dietHealthActivityContainer, dietHealthEditFragment);
                transaction.commit();
                break;
            case R.id.dietHealthURLIndv:
                //link to video on going to web browser: https://www.youtube.com/watch?v=9-3OCc5g5oE
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dietHealthURL));
                    startActivity(browserIntent);
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getActivity(),"Unable to open link",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}//end of DietFoodFragment class