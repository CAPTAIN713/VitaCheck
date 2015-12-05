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
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Collections;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by btter_000 on 11/28/2015.
 */
public class DietHealthArticleAdapter extends RecyclerView.Adapter<DietHealthArticleAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    /*i used emptyList() so that there is no null expect error if data does not load*/
    private List<DietHealthArticleInfo> dietHealthList;//= Collections.emptyList();
    private final Context healthContext;

    public DietHealthArticleAdapter(Context context, List<DietHealthArticleInfo> data){
        /*inflates the recyclerview_doctor_row xml file*/
        inflater=LayoutInflater.from(context);
        healthContext=context;
        this.dietHealthList=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    /*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        /*view represents the root of the recyclerview_doctor_row xml file*/
        View view=inflater.inflate(R.layout.recyclerview_diet_health_row, parent, false);

        /*passes the view (root) to the view holder class. view holder class is at the bottom*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*gets data, notice current is a doctor info object class*/
        DietHealthArticleInfo current = dietHealthList.get(position);

        /*sets data that will be displayed in the recyclerview_doctor_row xml file*/
        holder.dietHealthName.setText(current.getHealthName());
        //holder.dietHealthURL.setText(current.getHealthURL());
        //holder.dietHealthDescription.setText(current.getHealthDescription());
    }

    @Override
    public int getItemCount() {   return dietHealthList.size();}

    public void deleteHealth(int position){
        /*look up notifyDataSetChanged() method (use as last resort) instead use
        * more specific changes, read more on it you'll find out*/
        dietHealthList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /*stuff that i want to be displayed in the recyclerview*/
        TextView dietHealthName;
        //TextView dietHealthURL;
        //TextView dietHealthDescription;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            dietHealthName= (TextView) itemView.findViewById(R.id.healthNameView);
            //dietHealthURL= (TextView) itemView.findViewById(R.id.healthURLView);
            //dietHealthDescription= (TextView) itemView.findViewById(R.id.HealthDescriptionView);
            dietHealthName.setOnClickListener(this);
            //dietHealthURL.setOnClickListener(this);
            //dietHealthDescription.setOnClickListener(this);
            dietHealthName.setOnTouchListener(new OnSwipeTouchListener(healthContext) {
                public void onSwipeRight() {
                    deleteHealthArticleDialog();
                }
            });
        }

        @Override
        /*video on how to handle recycler clicks found here: https://www.youtube.com/watch?v=zE1E1HOK_E4   */
        public void onClick(View v) {

            int clickPosition = this.getAdapterPosition();
            DietHealthArticleInfo current = dietHealthList.get(clickPosition);
            //Link on how to use bundles: http://www.101apps.co.za/index.php/articles/passing-data-between-activities.html
            Bundle bundle = new Bundle();
            bundle.putString("parseID", current.getParseID());
            Intent myIntent = new Intent(healthContext, DietHealthArticleActivity.class); //video on starting new activity in onClick: https://www.youtube.com/watch?v=K9F6U7yN2vI
            myIntent.putExtras(bundle);
            healthContext.startActivity(myIntent); //or just look at Michael's MainActivity.java class


        }
        public void deleteHealthArticleDialog(){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

            final int position=this.getAdapterPosition();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(healthContext);
            alertDialogBuilder.setMessage("Delete Health Article?");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Toast.makeText(healthContext, "You clicked yes button "+String.valueOf(position), Toast.LENGTH_LONG).show();

                    ParseObject.registerSubclass(DietHealthArticleInfo.class);
                    ParseQuery<DietHealthArticleInfo> query = ParseQuery.getQuery("diet_health_article");
                    query.getInBackground(dietHealthList.get(position).getParseID(), new GetCallback<DietHealthArticleInfo>() {
                        @Override
                        public void done(DietHealthArticleInfo object, ParseException e) {
                            if (e == null) {
                                //something went right
                                object.deleteInBackground();
                                dietHealthList.remove(position);
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


}//end of HealthArticleAdapter class