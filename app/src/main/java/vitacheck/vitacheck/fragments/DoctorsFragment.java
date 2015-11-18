package vitacheck.vitacheck.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

public class DoctorsFragment extends Fragment {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k*/
    private RecyclerView recyclerView;
    private DoctorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_doctors, container, false);
        
        recyclerView= (RecyclerView) layout.findViewById(R.id.doctorsList);
        adapter= new DoctorAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter); //sets adapter to recyclerview

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        return layout;
    }
    /*
    * Used just to load hard code test data. this is more than likely how we will
    * get data from parse and load it into the classes
    * */
    public static List<DoctorSavedDoctorInfo> getData(){
        
        List<DoctorSavedDoctorInfo> data = new ArrayList<>();
        String[] titles={"Sarge","Church","youtube","blarg","caboose","reds","blues","RvB","Rooster","Teeth","delta","echo","alpha","omega"};
        for( int i=0;i<titles.length;i++){
            DoctorSavedDoctorInfo current = new DoctorSavedDoctorInfo();
            current.setName(titles[i]);
            current.setDoctorType(titles[i]);
            data.add(current);
        }
        return data;
    }
    
}//end of DoctorsFragment class