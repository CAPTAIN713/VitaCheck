package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vitacheck.vitacheck.R;
import vitacheck.vitacheck.drawer.NavigationDrawerFragment;


public class DietFragment extends Fragment  {

    //private NavigationDrawerFragment mNavigationDrawerFragment;

    Button foodButton;
    Button recipeButton;
    Button healthButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet, container, false);


        foodButton = (Button) view.findViewById(R.id.foodButton);
        foodButton.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new DietFoodFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        recipeButton = (Button) view.findViewById(R.id.recipeButton);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new DietRecipeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        healthButton = (Button) view.findViewById(R.id.healthButton);
        healthButton.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            @Override
            public void onClick(View v) {
                fragment = new DietHealthArticleFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }


}
