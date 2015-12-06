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
public class VitalSignsWeightFragment extends Fragment {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private VitalWeightAdapter adapter;
    private List<VitalWeightInfo> vitalWeightList = new ArrayList<VitalWeightInfo>();
    
    public VitalSignsWeightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //((MainActivity) getActivity()).setTitle("Weights");

        vitalWeightList = new ArrayList<VitalWeightInfo>();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_vitals_weight, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.vitalsWeightList);

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        ParseObject.registerSubclass(VitalWeightInfo.class);
        ParseQuery<VitalWeightInfo> query = new ParseQuery<VitalWeightInfo>("vital_weight");
        query.findInBackground(new FindCallback<VitalWeightInfo>() {
            @Override
            public void done(List<VitalWeightInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalWeightInfo hrObject : objects) {
                    if (hrObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalWeightInfo newWeight = new VitalWeightInfo();
                        newWeight.setParseId(hrObject.getObjectId());
                        newWeight.setWeight(hrObject.getWeight());
                        newWeight.setUploadDate(hrObject.getCreatedAt());
                        vitalWeightList.add(newWeight);
                    }
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new VitalWeightAdapter(getActivity(), vitalWeightList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });
        return layout;
    }

    public void update(){
        vitalWeightList = new ArrayList<VitalWeightInfo>();

        // Inflate the layout for this fragment
        //View layout = inflater.inflate(R.layout.fragment_vitals_weight, container, false);

        //recyclerView = (RecyclerView) layout.findViewById(R.id.vitalsWeightList);

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        ParseObject.registerSubclass(VitalWeightInfo.class);
        ParseQuery<VitalWeightInfo> query = new ParseQuery<VitalWeightInfo>("vital_weight");
        query.findInBackground(new FindCallback<VitalWeightInfo>() {
            @Override
            public void done(List<VitalWeightInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalWeightInfo hrObject : objects) {
                    if (hrObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalWeightInfo newWeight = new VitalWeightInfo();
                        newWeight.setParseId(hrObject.getObjectId());
                        newWeight.setWeight(hrObject.getWeight());
                        newWeight.setUploadDate(hrObject.getCreatedAt());
                        vitalWeightList.add(newWeight);
                    }
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                //adapter = new VitalWeightAdapter(getActivity(), vitalWeightList);
                //recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.notifyDataSetChanged();

            }
        });
    }
} //end of fragment class