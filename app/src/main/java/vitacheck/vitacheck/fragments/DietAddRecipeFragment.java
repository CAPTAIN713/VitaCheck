package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseObject;


import vitacheck.vitacheck.R;

public class DietAddRecipeFragment extends Fragment {


    Button saveRecipesButton;
    private EditText mRecipeName;
    private EditText mRecipeURL;
    private EditText mRecipeDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_recipe_add, container, false);

        saveRecipesButton = (Button) view.findViewById(R.id.saveRecipeButton);
        saveRecipesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            mRecipeName = (EditText) getView().findViewById(R.id.recipe_name);
            mRecipeURL = (EditText) getView().findViewById(R.id.recipe_URL);
            mRecipeDescription = (EditText) getView().findViewById(R.id.recipe_description);
            ParseObject recipe = new ParseObject("diet_recipe");
            recipe.put("name", mRecipeName.getText().toString());
            recipe.put("URL", mRecipeURL.getText().toString());
            recipe.put("note", mRecipeDescription.getText().toString());

            recipe.saveInBackground();
        }

    });

        return view;
    }

    private void saveRecipe()
    {

    }
}
