package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import vitacheck.vitacheck.R;

import vitacheck.vitacheck.fragments.*;
/**
 * Created by ERIC on 11/17/2015.
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private final Context doctorContext;
    /*i used emptyList() so that there is no null expect error if data does not load*/
    private List<DoctorInfo> doctorList;//= Collections.emptyList();

    public DoctorAdapter(Context context, List<DoctorInfo> data){
        /*inflates the recyclerview_doctor_row xml file*/
        inflater=LayoutInflater.from(context);
        doctorContext=context;
        this.doctorList=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    /*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        /*view represents the root of the recyclerview_doctor_row xml file*/
        View view=inflater.inflate(R.layout.recyclerview_doctor_row,parent, false);

        /*passes the view (root) to the view holder class. view holder class is at the bottom*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*gets data, notice current is a doctor info object class*/
        DoctorInfo current = doctorList.get(position);

        /*sets data that will be displayed in the recyclerview_doctor_row xml file*/
        holder.doctorName.setText(current.getName());
        holder.doctorTypeSubText.setText(current.getDoctorType());
    }

    @Override
    public int getItemCount() {   return doctorList.size();}

    public void deleteDoctor(int position){
        /*look up notifyDataSetChanged() method (use as last resort) instead use
        * more specific changes, read more on it you'll find out*/
        doctorList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        /*stuff that i want to be displayed in the recyclerview*/
        TextView doctorName;
        TextView doctorTypeSubText;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            doctorName= (TextView) itemView.findViewById(R.id.doctorNameField);
            doctorTypeSubText= (TextView) itemView.findViewById(R.id.doctorTypeField);
            doctorName.setOnClickListener(this);
            doctorName.setOnLongClickListener(this);
            doctorTypeSubText.setOnClickListener(this);
            doctorTypeSubText.setOnLongClickListener(this);
        }

        @Override
        /*video on how to handle recycler clicks found here: https://www.youtube.com/watch?v=zE1E1HOK_E4   */
        public void onClick(View v) {
            //deleteDoctor(getPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}//end of DoctorAdapter class
