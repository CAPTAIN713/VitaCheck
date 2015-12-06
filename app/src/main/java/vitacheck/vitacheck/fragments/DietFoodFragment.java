package vitacheck.vitacheck.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by btter_000 on 11/25/2015.
 */
public class DietFoodFragment extends Fragment {

    /*video on recylerView can be found here: https://www.youtube.com/watch?v=Wq2o4EbM74k   */
    private RecyclerView recyclerView;
    private DietFoodAdapter adapter;

    private FloatingActionButton addFoodButton;
    private List<DietFoodInfo> dietFoodParseList = new ArrayList<DietFoodInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_diet_food, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.foodList);

        /*sets the layout on how we want the data to be displayed
        * if we wanted to we could set it to grid or staggered layout but in this case
        * we wanted it to be list going from top to bottom so we use LinearLayout*/
        ParseObject.registerSubclass(DietFoodInfo.class);
        ParseQuery<DietFoodInfo> query = new ParseQuery<DietFoodInfo>("diet_food");
        query.findInBackground(new FindCallback<DietFoodInfo>() {
            @Override
            public void done(List<DietFoodInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                }

                for (DietFoodInfo food : objects)
                    if(food.getUserId().equals(GlobalVariable.getUserId(getActivity())))
                {

                    DietFoodInfo newDietFood = new DietFoodInfo();
                    newDietFood.setParseID(food.getObjectId());
                    newDietFood.setFoodName(food.getFoodName());
                    newDietFood.setFoodCalories(food.getFoodCalories());
                    newDietFood.setFoodDate(food.getFoodDate());
                    dietFoodParseList.add(newDietFood);
                }
                /*have to make adapter and set here because if set outside done method and after
                data will not appear because it sets the data before it is pulled from parse
                https://www.youtube.com/watch?v=ZlUjJSE7YW4&list=PLBA5zvAwnCrCdIilqLW7tTA2Uuao1u8iH&index=5
                start at 10 min to see explanation.     -eric*/
                adapter = new DietFoodAdapter(getActivity(), dietFoodParseList);
                recyclerView.setAdapter(adapter); //sets adapter to recyclerview
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

            addFoodButton = (FloatingActionButton) layout.findViewById(R.id.addFoodButton);
            addFoodButton.setOnClickListener(new View.OnClickListener() {
                Fragment fragment = null;
                @Override
                public void onClick(View v) {
                    fragment = new DietAddFoodFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                }
            });






        return layout;
    }
}