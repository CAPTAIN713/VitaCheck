package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseObject;


import vitacheck.vitacheck.R;

public class DietAddHealthArticleFragment extends Fragment {


    Button saveHealthArticleButton;
    private EditText mHealthName;
    private EditText mHealthURL;
    private EditText mHealthDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_diet_health_add, container, false);


        saveHealthArticleButton = (Button) view.findViewById(R.id.saveHealthButton);
        saveHealthArticleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mHealthName = (EditText) getView().findViewById(R.id.health_name);
                mHealthURL = (EditText) getView().findViewById(R.id.health_URL);
                mHealthDescription = (EditText) getView().findViewById(R.id.health_description);
                DietHealthArticleInfo health = new DietHealthArticleInfo();
                health.put("name", mHealthName.getText().toString());
                health.put("URL", mHealthURL.getText().toString());
                health.put("note", mHealthDescription.getText().toString());

                health.saveInBackground();
            }
        });
        return view;

    }

}