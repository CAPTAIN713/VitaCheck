package vitacheck.vitacheck.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

public class DoctorsFragment extends Fragment {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private DoctorAdapter adapter;

    private List<DoctorInfo> doctorParseList = new ArrayList<DoctorInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_doctors, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.doctorsList);

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ParseObject.registerSubclass(DoctorInfo.class);
        ParseQuery<DoctorInfo> query = new ParseQuery<DoctorInfo>("doctor");
        query.findInBackground(new FindCallback<DoctorInfo>() {
            @Override
            public void done(List<DoctorInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (DoctorInfo doc : objects) {
                    DoctorInfo newdoc = new DoctorInfo();
                    newdoc.setName(doc.getName());
                    newdoc.setDoctorType(doc.getDoctorType());
                    doctorParseList.add(newdoc);
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new DoctorAdapter(getActivity(), doctorParseList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        return layout;
    }

    /*
    * Used just to load hard code test data. this is more than likely how we will
    * get data from parse and load it into the classes
    * */
    public static List<DoctorSavedDoctorInfo> getData() {

        List<DoctorSavedDoctorInfo> data = new ArrayList<>();
        String[] titles = {"Sarge", "Church", "youtube", "blarg", "caboose", "reds", "blues", "RvB", "Rooster", "Teeth", "delta", "echo", "alpha", "omega"};
        for (int i = 0; i < titles.length; i++) {
            DoctorSavedDoctorInfo current = new DoctorSavedDoctorInfo();
            current.setName(titles[i]);
            current.setDoctorType(titles[i]);
            data.add(current);
        }
        return data;
    }

    
}//end of DoctorsFragment class