package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vitacheck.vitacheck.R;

public class DietAddFoodFragment extends Fragment {


    Button saveFoodButton;

    private EditText mFoodName;
    private EditText mFoodCalories;
    private EditText mFoodDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_diet_food_add, container, false);



        saveFoodButton = (Button) view.findViewById(R.id.saveButton);
        saveFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                mFoodName = (EditText) getView().findViewById(R.id.editFoodText);
                mFoodCalories = (EditText) getView().findViewById(R.id.editCaloriesText);
                mFoodDate = (EditText) getView().findViewById(R.id.editDateText);
                ParseObject food = new ParseObject("diet_food");
                try{
                    Date date = format.parse(mFoodDate.getText().toString());
                    food.put("Date", date);
                }
                catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                food.put("name", mFoodName.getText().toString());
                food.put("calories", Integer.parseInt(mFoodCalories.getText().toString()));


                food.saveInBackground();
            }
        });
        return view;

    }

}
