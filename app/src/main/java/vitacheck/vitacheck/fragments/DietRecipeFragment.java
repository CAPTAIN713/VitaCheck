package vitacheck.vitacheck.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class DietRecipeFragment extends Fragment {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private DietRecipeAdapter adapter;

    private List<DietRecipeInfo> dietRecipeParseList = new ArrayList<DietRecipeInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_diet_recipe, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recipeList);

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ParseObject.registerSubclass(DietRecipeInfo.class);
        ParseQuery<DietRecipeInfo> query = new ParseQuery<DietRecipeInfo>("diet_recipes");
        query.findInBackground(new FindCallback<DietRecipeInfo>() {
            @Override
            public void done(List<DietRecipeInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (DietRecipeInfo doc : objects) {
                    DietRecipeInfo newrec = new DietRecipeInfo();
                    newrec.setRecipeName(doc.getRecipeName());
                    newrec.setRecipeURL(doc.getRecipeURL());
                    dietRecipeParseList.add(newrec);
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new DietRecipeAdapter(getActivity(), dietRecipeParseList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        return layout;
    }

}