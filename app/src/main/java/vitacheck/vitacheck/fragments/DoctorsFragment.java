package vitacheck.vitacheck.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

public class DoctorsFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private DoctorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_doctors, container, false);
        
        recyclerView= (RecyclerView) layout.findViewById(R.id.doctorsList);
        adapter= new DoctorAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        return layout;
    }
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