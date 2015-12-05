package vitacheck.vitacheck.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by Robert on 12/3/2015.
 */
public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private final Context medicineContext;
    /*i used emptyList() so that there is no null expect error if data does not load*/
    private List<MedicineInfo> medicineList;//= Collections.emptyList();

    public MedicineAdapter(Context context, List<MedicineInfo> data){
        /*inflates the recyclerView doctor_row xml file*/
        inflater=LayoutInflater.from(context);
        medicineContext=context;
        this.medicineList=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    /*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        /*view represents the root of the recyclerview_doctor_row xml file*/
        View view=inflater.inflate(R.layout.recyclerview_medicine_row,parent, false);

        /*passes the view (root) to the view holder class. view holder class is at the bottom*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*gets data, notice current is a doctor info object class*/
        MedicineInfo current = medicineList.get(position);

        /*sets data that will be displayed in the recyclerview_doctor_row xml file*/
        holder.medicineName.setText(current.getName());
        holder.noteSubText.setText(current.getNote());
    }

    @Override
    public int getItemCount() {   return medicineList.size();}

    public void deleteMedicine(int position){
        /*look up notifyDataSetChanged() method (use as last resort) instead use
        * more specific changes, read more on it you'll find out*/
        medicineList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /*stuff that i want to be displayed in the recyclerview*/
        TextView medicineName;
        TextView noteSubText;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            medicineName= (TextView) itemView.findViewById(R.id.medicineNameField);
            noteSubText= (TextView) itemView.findViewById(R.id.noteTypeField);
            medicineName.setOnClickListener(this);
            noteSubText.setOnClickListener(this);


            //set the listeners for when you swipe right on a item in the list
            medicineName.setOnTouchListener(new OnSwipeTouchListener(medicineContext) {
                public void onSwipeRight() {
                    deleteMedicineDialog();
                }
            });
            noteSubText.setOnTouchListener(new OnSwipeTouchListener(medicineContext) {
                public void onSwipeRight() {
                    deleteMedicineDialog();
                }
            });
        }

        @Override
        /*video on how to handle recycler clicks found here: https://www.youtube.com/watch?v=zE1E1HOK_E4   */
        public void onClick(View v) {
            //Link on how to get the position of clicked item: http://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview
            int clickPosition=this.getAdapterPosition();
            MedicineInfo current = medicineList.get(clickPosition);
            //Link on how to use bundles: http://www.101apps.co.za/index.php/articles/passing-data-between-activities.html
            Bundle bundle = new Bundle();
            bundle.putString("parseID", current.getParseId());
            Intent myIntent = new Intent(medicineContext ,MedicineActivity.class); //video on starting new activity in onClick: https://www.youtube.com/watch?v=K9F6U7yN2vI
            myIntent.putExtras(bundle);
            medicineContext.startActivity(myIntent); //or just look at Michael's MainActivity.java class
        }

        public void deleteMedicineDialog(){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

            final int position=this.getAdapterPosition();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(medicineContext);
            alertDialogBuilder.setMessage("Delete Medicine?");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Toast.makeText(doctorContext, "You clicked yes button "+String.valueOf(position), Toast.LENGTH_LONG).show();

                    ParseObject.registerSubclass(MedicineInfo.class);
                    ParseQuery<MedicineInfo> query = ParseQuery.getQuery("medicine");
                    query.getInBackground(medicineList.get(position).getParseId(), new GetCallback<MedicineInfo>() {
                        @Override
                        public void done(MedicineInfo object, ParseException e) {
                            if (e == null) {
                                //something went right
                                object.deleteInBackground();
                                medicineList.remove(position);
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
                            /*do nothing when answer is cancel*/
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

}//end of DoctorAdapter class
