package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vitacheck.vitacheck.R;

/**
 * Created by btter_000 on 12/6/2015.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private RecyclerView doctorRecyclerView;
    private DoctorAdapter doctorAdapter;
    private RecyclerView healthRecyclerView;
    private DietHealthArticleAdapter healthAdapter;
    private RecyclerView foodRecyclerView;
    private DietFoodAdapter foodAdapter;
    private RecyclerView recipeRecyclerView;
    private DietRecipeAdapter recipeAdapter;
    private RecyclerView medicineRecyclerView;
    private MedicineAdapter medicineAdapter;



    private TextView search_input;
    private Context context;
    private Button searchButton;
    private List<DietFoodInfo> dietFoodList = new ArrayList<DietFoodInfo>();
    private List<DietRecipeInfo> dietRecipeList = new ArrayList<DietRecipeInfo>();
    private List<DietHealthArticleInfo> dietHealthList = new ArrayList<DietHealthArticleInfo>();
    private List<DoctorInfo> doctorList = new ArrayList<DoctorInfo>();
    private List<MedicineInfo> medicineList = new ArrayList<MedicineInfo>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        context = layout.getContext();


        dietFoodList = new ArrayList<DietFoodInfo>();
        dietRecipeList = new ArrayList<DietRecipeInfo>();
        dietHealthList = new ArrayList<DietHealthArticleInfo>();
        doctorList = new ArrayList<DoctorInfo>();
        medicineList = new ArrayList<MedicineInfo>();

        foodRecyclerView = (RecyclerView) layout.findViewById(R.id.foodList);
        recipeRecyclerView = (RecyclerView) layout.findViewById(R.id.recipeList);
        healthRecyclerView = (RecyclerView) layout.findViewById(R.id.healthList);
        doctorRecyclerView = (RecyclerView) layout.findViewById(R.id.doctorsList);
        medicineRecyclerView = (RecyclerView) layout.findViewById(R.id.medicinesList);

        search_input = (EditText) layout.findViewById(R.id.search_input);
        searchButton = (Button) layout.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        return layout;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                ParseObject.registerSubclass(DietFoodInfo.class);
                ParseQuery<DietFoodInfo> query = new ParseQuery<DietFoodInfo>("diet_food");
                query.whereContains("name", search_input.getText().toString());
                 query.findInBackground(new FindCallback<DietFoodInfo>() {
                     @Override
                     public void done(List<DietFoodInfo> objects, com.parse.ParseException e) {
                         if (e != null) {
                             Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                         }

                         for (DietFoodInfo food : objects) {
                             // Toast.makeText(getView().getContext(), search, Toast.LENGTH_SHORT).show();
                             if (food.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                 DietFoodInfo newFood = new DietFoodInfo();
                                 newFood.setParseID(food.getObjectId());
                                 newFood.setFoodName(food.getFoodName());
                                 newFood.setFoodCalories(food.getFoodCalories());
                                 newFood.setUserId(GlobalVariable.getUserId(getActivity()));

                                 dietFoodList.add(newFood);


                             }
                         }

                     }
                 });

                ParseObject.registerSubclass(DietRecipeInfo.class);
                ParseQuery<DietRecipeInfo> query2 = new ParseQuery<DietRecipeInfo>("diet_recipes");
                query2.whereContains("name", search_input.getText().toString());
                query2.findInBackground(new FindCallback<DietRecipeInfo>() {
                    @Override
                    public void done(List<DietRecipeInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DietRecipeInfo recipe : objects)
                            if (recipe.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DietRecipeInfo newRecipe = new DietRecipeInfo();
                                newRecipe.setParseID(recipe.getObjectId());
                                newRecipe.setRecipeName(recipe.getRecipeName());
                                newRecipe.setUserId(GlobalVariable.getUserId(getActivity()));

                                dietRecipeList.add(newRecipe);

                            }

                    }
                });
                ParseQuery<DietRecipeInfo> query3 = new ParseQuery<DietRecipeInfo>("diet_recipes");
                query3.whereContains("URL", search_input.getText().toString());
                query3.findInBackground(new FindCallback<DietRecipeInfo>() {
                    @Override
                    public void done(List<DietRecipeInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DietRecipeInfo recipe : objects)
                            if (recipe.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DietRecipeInfo newRecipe = new DietRecipeInfo();
                                newRecipe.setParseID(recipe.getObjectId());
                                newRecipe.setRecipeName(recipe.getRecipeName());
                                newRecipe.setUserId(GlobalVariable.getUserId(getActivity()));
                                dietRecipeList.add(newRecipe);

                            }

                    }
                });
                ParseQuery<DietRecipeInfo> query4 = new ParseQuery<DietRecipeInfo>("diet_recipes");
                query4.whereContains("note", search_input.getText().toString());
                query4.findInBackground(new FindCallback<DietRecipeInfo>() {
                    @Override
                    public void done(List<DietRecipeInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DietRecipeInfo recipe : objects)
                            if (recipe.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DietRecipeInfo newRecipe = new DietRecipeInfo();
                                newRecipe.setParseID(recipe.getObjectId());
                                newRecipe.setRecipeName(recipe.getRecipeName());
                                newRecipe.setUserId(GlobalVariable.getUserId(getActivity()));
                                dietRecipeList.add(newRecipe);

                            }

                    }
                });

                ParseObject.registerSubclass(DietHealthArticleInfo.class);
                ParseQuery<DietHealthArticleInfo> query5 = new ParseQuery<DietHealthArticleInfo>("diet_health_article");
                query5.whereContains("name", search_input.getText().toString());
                query5.findInBackground(new FindCallback<DietHealthArticleInfo>() {
                    @Override
                    public void done(List<DietHealthArticleInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DietHealthArticleInfo health : objects) {
                            if (health.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DietHealthArticleInfo newHealth = new DietHealthArticleInfo();
                                newHealth.setParseID(health.getObjectId());
                                newHealth.setHealthName(health.getHealthName());
                                newHealth.setUserId(GlobalVariable.getUserId(getActivity()));
                                dietHealthList.add(newHealth);
                            }
                        }

                    }
                });


                ParseQuery<DietHealthArticleInfo> query6 = new ParseQuery<DietHealthArticleInfo>("diet_health_article");
                query6.whereContains("URL", search_input.getText().toString());
                query6.findInBackground(new FindCallback<DietHealthArticleInfo>() {
                    @Override
                    public void done(List<DietHealthArticleInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DietHealthArticleInfo health : objects) {
                            if (health.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DietHealthArticleInfo newHealth = new DietHealthArticleInfo();
                                newHealth.setParseID(health.getObjectId());
                                newHealth.setHealthName(health.getHealthName());
                                newHealth.setUserId(GlobalVariable.getUserId(getActivity()));
                                dietHealthList.add(newHealth);
                            }
                        }

                    }
                });

                ParseQuery<DietHealthArticleInfo> query7 = new ParseQuery<DietHealthArticleInfo>("diet_health_article");
                query7.whereContains("name", search_input.getText().toString());
                query7.findInBackground(new FindCallback<DietHealthArticleInfo>() {
                    @Override
                    public void done(List<DietHealthArticleInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DietHealthArticleInfo health : objects) {
                            if (health.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DietHealthArticleInfo newHealth = new DietHealthArticleInfo();
                                newHealth.setParseID(health.getObjectId());
                                newHealth.setHealthName(health.getHealthName());
                                newHealth.setUserId(GlobalVariable.getUserId(getActivity()));
                                dietHealthList.add(newHealth);
                            }
                        }

                    }
                });

                ParseObject.registerSubclass(DoctorInfo.class);
                ParseQuery<DoctorInfo> query8 = new ParseQuery<DoctorInfo>("doctor");
                query8.whereContains("doctor_name", search_input.getText().toString());
                query8.findInBackground(new FindCallback<DoctorInfo>() {
                    @Override
                    public void done(List<DoctorInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DoctorInfo doc : objects)
                            if (doc.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DoctorInfo newDoc = new DoctorInfo();
                                newDoc.setParseId(doc.getObjectId());
                                newDoc.setName(doc.getName());
                                newDoc.setDoctorType(doc.getDoctorType());
                                newDoc.setUserId(GlobalVariable.getUserId(getActivity()));
                                doctorList.add(newDoc);

                            }

                    }
                });

                ParseQuery<DoctorInfo> query9 = new ParseQuery<DoctorInfo>("doctor");
                query9.whereContains("doctor_type", search_input.getText().toString());
                query9.findInBackground(new FindCallback<DoctorInfo>() {
                    @Override
                    public void done(List<DoctorInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DoctorInfo doc : objects)
                            if (doc.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DoctorInfo newDoc = new DoctorInfo();
                                newDoc.setParseId(doc.getObjectId());
                                newDoc.setName(doc.getName());
                                newDoc.setDoctorType(doc.getDoctorType());
                                newDoc.setUserId(GlobalVariable.getUserId(getActivity()));
                                doctorList.add(newDoc);

                            }

                    }
                });
                ParseQuery<DoctorInfo> query10 = new ParseQuery<DoctorInfo>("doctor");
                query10.whereContains("email", search_input.getText().toString());
                query10.findInBackground(new FindCallback<DoctorInfo>() {
                    @Override
                    public void done(List<DoctorInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DoctorInfo doc : objects)
                            if (doc.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DoctorInfo newDoc = new DoctorInfo();
                                newDoc.setParseId(doc.getObjectId());
                                newDoc.setName(doc.getName());
                                newDoc.setDoctorType(doc.getDoctorType());
                                newDoc.setUserId(GlobalVariable.getUserId(getActivity()));
                                doctorList.add(newDoc);

                            }

                    }
                });
                ParseQuery<DoctorInfo> query11 = new ParseQuery<DoctorInfo>("doctor");
                query11.whereContains("address", search_input.getText().toString());
                query11.findInBackground(new FindCallback<DoctorInfo>() {
                    @Override
                    public void done(List<DoctorInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DoctorInfo doc : objects)
                            if (doc.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DoctorInfo newDoc = new DoctorInfo();
                                newDoc.setParseId(doc.getObjectId());
                                newDoc.setName(doc.getName());
                                newDoc.setDoctorType(doc.getDoctorType());
                                newDoc.setUserId(GlobalVariable.getUserId(getActivity()));
                                doctorList.add(newDoc);

                            }

                    }
                });
                ParseQuery<DoctorInfo> query12 = new ParseQuery<DoctorInfo>("doctor");
                query12.whereContains("website_link", search_input.getText().toString());
                query12.findInBackground(new FindCallback<DoctorInfo>() {
                    @Override
                    public void done(List<DoctorInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (DoctorInfo doc : objects)
                            if (doc.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                DoctorInfo newDoc = new DoctorInfo();
                                newDoc.setParseId(doc.getObjectId());
                                newDoc.setName(doc.getName());
                                newDoc.setDoctorType(doc.getDoctorType());
                                newDoc.setUserId(GlobalVariable.getUserId(getActivity()));
                                doctorList.add(newDoc);

                            }

                    }
                });

                ParseObject.registerSubclass(MedicineInfo.class);
                ParseQuery<MedicineInfo> query13 = new ParseQuery<MedicineInfo>("medication");
                query13.whereContains("name", search_input.getText().toString());
                query13.findInBackground(new FindCallback<MedicineInfo>() {
                    @Override
                    public void done(List<MedicineInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (MedicineInfo med : objects)
                            if (med.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                MedicineInfo newmed = new MedicineInfo();
                                newmed.setParseId(med.getObjectId());
                                newmed.setName(med.getName());
                                newmed.setNote(med.getNote());
                                newmed.setUserId(GlobalVariable.getUserId(getActivity()));
                                medicineList.add(newmed);

                            }

                    }
                });
                ParseQuery<MedicineInfo> query14 = new ParseQuery<MedicineInfo>("medication");
                query14.whereContains("note", search_input.getText().toString());
                query14.findInBackground(new FindCallback<MedicineInfo>() {
                    @Override
                    public void done(List<MedicineInfo> objects, com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();

                        }

                        for (MedicineInfo med : objects)
                            if (med.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                                MedicineInfo newmed = new MedicineInfo();
                                newmed.setParseId(med.getObjectId());
                                newmed.setName(med.getName());
                                newmed.setNote(med.getNote());
                                newmed.setUserId(GlobalVariable.getUserId(getActivity()));
                                medicineList.add(newmed);
                            }

                    }
                });

                foodAdapter = new DietFoodAdapter(getActivity(), dietFoodList);
                foodRecyclerView.setAdapter(foodAdapter); //sets adapter to recyclerview
                foodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                recipeAdapter = new DietRecipeAdapter(getActivity(), dietRecipeList);
                recipeRecyclerView.setAdapter(recipeAdapter); //sets adapter to recyclerview
                recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                healthAdapter = new DietHealthArticleAdapter(getActivity(), dietHealthList);
                healthRecyclerView.setAdapter(healthAdapter); //sets adapter to recyclerview
                healthRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                doctorAdapter = new DoctorAdapter(getActivity(), doctorList);
                doctorRecyclerView.setAdapter(doctorAdapter); //sets adapter to recyclerview
                doctorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                medicineAdapter = new MedicineAdapter(getActivity(), medicineList);
                medicineRecyclerView.setAdapter(medicineAdapter); //sets adapter to recyclerview
                medicineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        }

    }

}



