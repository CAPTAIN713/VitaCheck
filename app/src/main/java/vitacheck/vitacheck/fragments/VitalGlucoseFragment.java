package vitacheck.vitacheck.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class VitalGlucoseFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private VitalGlucoseAdapter adapter;
    private List<VitalGlucoseInfo> vitalGlucoseList = new ArrayList<VitalGlucoseInfo>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    
    public VitalGlucoseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //((MainActivity) getActivity()).setTitle("Heart Rate");

        vitalGlucoseList = new ArrayList<VitalGlucoseInfo>();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_vitals_glucose, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.vitalsGlucoseList);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.vitalsGlucoseSwipeRefreshContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        
        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        ParseObject.registerSubclass(VitalGlucoseInfo.class);
        ParseQuery<VitalGlucoseInfo> query = new ParseQuery<VitalGlucoseInfo>("vital_glucose");
        query.findInBackground(new FindCallback<VitalGlucoseInfo>() {
            @Override
            public void done(List<VitalGlucoseInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalGlucoseInfo glucoseObject : objects) {
                    if (glucoseObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalGlucoseInfo newGlucose = new VitalGlucoseInfo();
                        newGlucose.setParseId(glucoseObject.getObjectId());
                        newGlucose.setGlucose(glucoseObject.getGlucose());
                        newGlucose.setUploadDate(glucoseObject.getCreatedAt());
                        vitalGlucoseList.add(newGlucose);
                    }
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new VitalGlucoseAdapter(getActivity(), vitalGlucoseList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });
        return layout;
    }

    @Override
    public void onRefresh() {
        vitalGlucoseList = new ArrayList<VitalGlucoseInfo>();
        ParseObject.registerSubclass(VitalGlucoseInfo.class);
        ParseQuery<VitalGlucoseInfo> query = new ParseQuery<VitalGlucoseInfo>("vital_glucose");
        query.findInBackground(new FindCallback<VitalGlucoseInfo>() {
            @Override
            public void done(List<VitalGlucoseInfo> objects, com.parse.ParseException e) {
                if (e != null) {Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();}
                for (VitalGlucoseInfo hrObject : objects) {
                    if (hrObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        if (!(vitalGlucoseList.contains(hrObject))) ;
                        VitalGlucoseInfo newGlucose = new VitalGlucoseInfo();
                        newGlucose.setParseId(hrObject.getObjectId());
                        newGlucose.setGlucose(hrObject.getGlucose());
                        newGlucose.setUploadDate(hrObject.getCreatedAt());
                        vitalGlucoseList.add(newGlucose);
                    }
                }
                adapter = new VitalGlucoseAdapter(getActivity(), vitalGlucoseList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
} //end of fragment class