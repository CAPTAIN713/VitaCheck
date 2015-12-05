package vitacheck.vitacheck.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by btter_000 on 11/28/2015.
 */
public class DietRecipeAdapter extends RecyclerView.Adapter<DietRecipeAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    /*i used emptyList() so that there is no null expect error if data does not load*/
    private List<DietRecipeInfo> dietRecipeList;//= Collections.emptyList();
    private final Context recipeContext;

    public DietRecipeAdapter(Context context, List<DietRecipeInfo> data){
        /*inflates the recyclerview_doctor_row xml file*/
        inflater=LayoutInflater.from(context);
        recipeContext=context;
        this.dietRecipeList=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    /*called when recyclerView needs a new RecyclerView.ViewHolder of a given type to represent an item*/
        /*view represents the root of the recyclerview_doctor_row xml file*/
        View view=inflater.inflate(R.layout.recyclerview_diet_recipe_row,parent, false);

        /*passes the view (root) to the view holder class. view holder class is at the bottom*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*gets data, notice current is a doctor info object class*/
        DietRecipeInfo sCurrent = dietRecipeList.get(position);

        /*sets data that will be displayed in the recyclerview_doctor_row xml file*/
        holder.dietRecipeName.setText(sCurrent.getRecipeName());
        holder.dietRecipeURL.setText(sCurrent.getRecipeURL());
        //holder.dietRecipeDescription.setText(sCurrent.getRecipeDescription());
    }

    @Override
    public int getItemCount() {   return dietRecipeList.size();}

    public void deleteRecipe(int position){
        /*look up notifyDataSetChanged() method (use as last resort) instead use
        * more specific changes, read more on it you'll find out*/
        dietRecipeList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        /*stuff that i want to be displayed in the recyclerview*/
        TextView dietRecipeName;
        TextView dietRecipeURL;
        //TextView dietRecipeDescription;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            dietRecipeName= (TextView) itemView.findViewById(R.id.recipeNameView);
            dietRecipeURL= (TextView) itemView.findViewById(R.id.recipeURLView);
            //dietRecipeDescription= (TextView) itemView.findViewById(R.id.recipeNoteView);
            dietRecipeName.setOnClickListener(this);
            dietRecipeURL.setOnClickListener(this);
            //dietRecipeDescription.setOnClickListener(this);
        }

        @Override
        /*video on how to handle recycler clicks found here: https://www.youtube.com/watch?v=zE1E1HOK_E4   */
        public void onClick(View v) {

            //deleteRecipe(getPosition());

            int clickPosition = this.getAdapterPosition();
            DietRecipeInfo current = dietRecipeList.get(clickPosition);
            //Link on how to use bundles: http://www.101apps.co.za/index.php/articles/passing-data-between-activities.html
            Bundle bundle = new Bundle();
            bundle.putString("parseID", current.getParseID());
            Intent myIntent = new Intent(recipeContext, DietRecipeActivity.class); //video on starting new activity in onClick: https://www.youtube.com/watch?v=K9F6U7yN2vI
            myIntent.putExtras(bundle);
            recipeContext.startActivity(myIntent); //or just look at Michael's MainActivity.java class
        }
    }

}//end of RecipeAdapter class