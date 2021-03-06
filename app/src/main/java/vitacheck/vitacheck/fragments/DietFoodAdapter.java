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

import java.util.Collections;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by btter_000 on 11/25/2015.
 */
public class DietFoodAdapter extends RecyclerView.Adapter<DietFoodAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    /*i used emptyList() so that there is no null expect error if data does not load*/
    private List<DietFoodInfo> foodList;
    private final Context foodContext;

    public DietFoodAdapter(Context context, List<DietFoodInfo> data){
        /*inflates the recyclerview_diet_food_row xml file*/
        inflater=LayoutInflater.from(context);
        foodContext=context;
        this.foodList=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    /*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        /*view represents the root of the recyclerview_food_row xml file*/
        View view=inflater.inflate(R.layout.recyclerview_diet_food_row,parent, false);

        /*passes the view (root) to the view holder class. view holder class is at the bottom*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*gets data, notice current is a food info object class*/
        DietFoodInfo fCurrent = foodList.get(position);

        /*sets data that will be displayed in the recyclerview_diet_food_row xml file*/
        holder.foodName.setText(fCurrent.getFoodName());
        holder.foodCalories.setText("" + fCurrent.getFoodCalories());
    }

    @Override
    public int getItemCount() {   return foodList.size();}

    public void deleteFood(int position){
        /*look up notifyDataSetChanged() method (use as last resort) instead use
        * more specific changes, read more on it you'll find out*/
        foodList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*stuff that i want to be displayed in the recyclerview*/
        TextView foodName;
        TextView foodCalories;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.foodView);
            foodCalories = (TextView) itemView.findViewById(R.id.caloriesView);
            foodName.setOnClickListener(this);
            foodCalories.setOnClickListener(this);

            //set the listeners for when you swipe right on a item in the list
            foodName.setOnTouchListener(new OnSwipeTouchListener(foodContext) {
                public void onSwipeRight() {
                    deleteFoodDialog();
                }
            });
            foodCalories.setOnTouchListener(new OnSwipeTouchListener(foodContext) {
                public void onSwipeRight() {
                    deleteFoodDialog();
                }
            });
        }

        @Override
        /*video on how to handle recycler clicks found here: https://www.youtube.com/watch?v=zE1E1HOK_E4   */
        public void onClick(View v) {
            //deleteFood(getPosition());
            int clickPosition = this.getAdapterPosition();
            DietFoodInfo current = foodList.get(clickPosition);
            //Link on how to use bundles: http://www.101apps.co.za/index.php/articles/passing-data-between-activities.html
            Bundle bundle = new Bundle();
            bundle.putString("parseID", current.getParseID());
            Intent myIntent = new Intent(foodContext, DietFoodActivity.class); //video on starting new activity in onClick: https://www.youtube.com/watch?v=K9F6U7yN2vI
            myIntent.putExtras(bundle);
            foodContext.startActivity(myIntent); //or just look at Michael's MainActivity.java class
        }

        public void deleteFoodDialog() {
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

            final int position = this.getAdapterPosition();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(foodContext);
            alertDialogBuilder.setMessage("Delete Food?");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Toast.makeText(foodContext, "You clicked yes button "+String.valueOf(position), Toast.LENGTH_LONG).show();

                    ParseObject.registerSubclass(DietFoodInfo.class);
                    ParseQuery<DietFoodInfo> query = ParseQuery.getQuery("diet_food");
                    query.getInBackground(foodList.get(position).getParseID(), new GetCallback<DietFoodInfo>() {
                        @Override
                        public void done(DietFoodInfo object, ParseException e) {
                            if (e == null) {
                                //something went right
                                object.deleteInBackground();
                                foodList.remove(position);
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

}//end of DietFoodAdapter class
