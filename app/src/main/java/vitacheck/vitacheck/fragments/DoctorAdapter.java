package vitacheck.vitacheck.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 11/17/2015.
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<DoctorSavedDoctorInfo> data= Collections.emptyList();

    public DoctorAdapter(Context context, List<DoctorSavedDoctorInfo> data){
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {/*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        View view=inflater.inflate(R.layout.recyclerview_doctor_row,parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DoctorSavedDoctorInfo current = data.get(position);
        holder.doctorName.setText(current.getName());
        holder.doctorTypeSubText.setText(current.getDoctorType());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName;
        TextView doctorTypeSubText;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            doctorName= (TextView) itemView.findViewById(R.id.doctorNameField);
            doctorTypeSubText= (TextView) itemView.findViewById(R.id.doctorTypeField);
        }
    }

}//end of DoctorAdapter class
