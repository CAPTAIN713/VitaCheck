package vitacheck.vitacheck.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by ERIC on 12/5/2015.
 */
public class VitalCholesterolAdapter extends RecyclerView.Adapter<VitalCholesterolAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private final Context vitalCholesterolContext;
    /*i used emptyList() so that there is no null expect error if data does not load*/
    private List<VitalCholesterolInfo> cholesterolList;//= Collections.emptyList();

    public VitalCholesterolAdapter(Context context, List<VitalCholesterolInfo> data){
        /*inflates the recyclerView doctor_row xml file*/
        inflater=LayoutInflater.from(context);
        vitalCholesterolContext=context;
        this.cholesterolList=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    /*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        /*view represents the root of the recyclerview_doctor_row xml file*/
        View view=inflater.inflate(R.layout.recyclerview_vitals_cholesterol_row, parent, false);

        /*passes the view (root) to the view holder class. view holder class is at the bottom*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*gets data, notice current is a doctor info object class*/
        VitalCholesterolInfo current = cholesterolList.get(position);

        /*sets data that will be displayed in the recyclerview_doctor_row xml file*/
        holder.cholesterol.setText(String.valueOf(current.getCholesterol())); //error here
        if(current.getUploadDate() != null) {
            holder.logDate.setText(DateFormat.getDateInstance().format(current.getUploadDate()));
        }
    }

    @Override
    public int getItemCount() {   return cholesterolList.size();}

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*stuff that i want to be displayed in the recyclerview*/
        TextView cholesterol;
        TextView logDate;


        public MyViewHolder(View itemView) {
            super(itemView);
            cholesterol= (TextView) itemView.findViewById(R.id.vitalsCholesterolTV);
            logDate= (TextView) itemView.findViewById(R.id.vitalsCholesterolDateTV);
            cholesterol.setOnClickListener(this);
            logDate.setOnClickListener(this);

        }

        @Override
        /*video on how to handle recycler clicks found here: https://www.youtube.com/watch?v=zE1E1HOK_E4   */
        public void onClick(View v) {
            //Link on how to get the position of clicked item: http://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview
            deleteCholesterolDialog();
        }

        public void deleteCholesterolDialog(){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

            final int position=this.getAdapterPosition();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(vitalCholesterolContext);
            alertDialogBuilder.setMessage("Delete Cholesterol Log?");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Toast.makeText(vitalCholesterolContext, "You clicked yes button "+String.valueOf(position), Toast.LENGTH_LONG).show();

                    ParseObject.registerSubclass(VitalCholesterolInfo.class);
                    ParseQuery<VitalCholesterolInfo> query = ParseQuery.getQuery("vital_cholesterol");
                    query.getInBackground(cholesterolList.get(position).getParseId(), new GetCallback<VitalCholesterolInfo>() {
                        @Override
                        public void done(VitalCholesterolInfo object, ParseException e) {
                            if (e == null) {
                                //something went right
                                object.deleteInBackground();
                                cholesterolList.remove(position);
                                notifyItemRemoved(position);
                            } else {
                                //someting went wrong
                            }
                        }
                    });

                } //end of onClick method
            }); //end of setPositiveButton method

            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                            /*do nothing when answer is cancel*/  }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

}//end of Adapter class