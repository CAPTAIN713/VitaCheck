package vitacheck.vitacheck.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 12/5/2015.
 */
public class VitalCholesterolFragment extends Fragment {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private VitalCholesterolAdapter adapter;
    private List<VitalCholesterolInfo> vitalCholesterolList = new ArrayList<VitalCholesterolInfo>();
    
    public VitalCholesterolFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //((MainActivity) getActivity()).setTitle("Cholesterol");

        vitalCholesterolList = new ArrayList<VitalCholesterolInfo>();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_vitals_cholesterol, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.vitalsCholesterolList);

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        ParseObject.registerSubclass(VitalCholesterolInfo.class);
        ParseQuery<VitalCholesterolInfo> query = new ParseQuery<VitalCholesterolInfo>("vital_cholesterol");
        query.findInBackground(new FindCallback<VitalCholesterolInfo>() {
            @Override
            public void done(List<VitalCholesterolInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalCholesterolInfo cholesterolObject : objects) {
                    if (cholesterolObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalCholesterolInfo newCholesterol = new VitalCholesterolInfo();
                        newCholesterol.setParseId(cholesterolObject.getObjectId());
                        newCholesterol.setCholesterol(cholesterolObject.getCholesterol());
                        newCholesterol.setUploadDate(cholesterolObject.getCreatedAt());
                        vitalCholesterolList.add(newCholesterol);
                    }
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new VitalCholesterolAdapter(getActivity(), vitalCholesterolList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });
        return layout;
    }

} //end of fragment class