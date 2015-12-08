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
public class VitalBloodPressureFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private VitalBloodPressureAdapter adapter;
    private List<VitalBloodPressureInfo> vitalBloodPressureList = new ArrayList<VitalBloodPressureInfo>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    
    public VitalBloodPressureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //((MainActivity) getActivity()).setTitle("Blood Pressure");

        vitalBloodPressureList = new ArrayList<VitalBloodPressureInfo>();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_vitals_blood_pressure, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.vitalsBloodPressureList);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.vitalsBloodPressureSwipeRefreshContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        
        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        ParseObject.registerSubclass(VitalBloodPressureInfo.class);
        ParseQuery<VitalBloodPressureInfo> query = new ParseQuery<VitalBloodPressureInfo>("vital_blood_pressure");
        query.findInBackground(new FindCallback<VitalBloodPressureInfo>() {
            @Override
            public void done(List<VitalBloodPressureInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalBloodPressureInfo bpObject : objects) {
                    if (bpObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalBloodPressureInfo newBP = new VitalBloodPressureInfo();
                        newBP.setParseId(bpObject.getObjectId());
                        newBP.setFirstNumber(bpObject.getFirstNumber());
                        newBP.setSecondNumber(bpObject.getSecondNumber());
                        newBP.setUploadDate(bpObject.getCreatedAt());
                        vitalBloodPressureList.add(newBP);
                    }
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new VitalBloodPressureAdapter(getActivity(), vitalBloodPressureList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });
        return layout;
    }

    @Override
    public void onRefresh() {
        vitalBloodPressureList = new ArrayList<VitalBloodPressureInfo>();
        ParseObject.registerSubclass(VitalBloodPressureInfo.class);
        ParseQuery<VitalBloodPressureInfo> query = new ParseQuery<VitalBloodPressureInfo>("vital_blood_pressure");
        query.findInBackground(new FindCallback<VitalBloodPressureInfo>() {
            @Override
            public void done(List<VitalBloodPressureInfo> objects, com.parse.ParseException e) {
                if (e != null) {Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();}
                for (VitalBloodPressureInfo hrObject : objects) {
                    if (hrObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        if (!(vitalBloodPressureList.contains(hrObject))) ;
                        VitalBloodPressureInfo newBloodPressure = new VitalBloodPressureInfo();
                        newBloodPressure.setParseId(hrObject.getObjectId());
                        newBloodPressure.setFirstNumber(hrObject.getFirstNumber());
                        newBloodPressure.setSecondNumber(hrObject.getSecondNumber());
                        newBloodPressure.setUploadDate(hrObject.getCreatedAt());
                        vitalBloodPressureList.add(newBloodPressure);
                    }
                }
                adapter = new VitalBloodPressureAdapter(getActivity(), vitalBloodPressureList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
} //end of fragment class