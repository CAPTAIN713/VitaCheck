package vitacheck.vitacheck.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.R;

public class VitalSignsActivity extends AppCompatActivity implements View.OnClickListener{

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);
    } */

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*all icons found and downloaded at: https://icons8.com/
        * code guide found at: https://github.com/oguzbilgener/CircularFloatingActionMenu
        * video guide found at: https://www.youtube.com/watch?v=m5AZvqJ1YFg  */
        ImageView addIconImageView = new ImageView(this);
        addIconImageView.setImageResource(R.drawable.ic_plus_white_24dp);
        ImageView weightIcon = new ImageView(this);
        weightIcon.setImageResource(R.drawable.icon_weight_24dp);
        ImageView bloodPressureIcon = new ImageView(this);
        bloodPressureIcon.setImageResource(R.drawable.icon_blood_pressure_24dp);
        ImageView glucoseIcon = new ImageView(this);
        glucoseIcon.setImageResource(R.drawable.icon_water_drop_24dp);
        ImageView heartRateIcon = new ImageView(this);
        heartRateIcon.setImageResource(R.drawable.icon_medical_heart_24dp);
        ImageView cholesterolIcon = new ImageView(this);
        cholesterolIcon.setImageResource(R.drawable.icon_lipids_24dp);

        FloatingActionButton addActionButton = new FloatingActionButton.Builder(this)
                .setContentView(addIconImageView)
                .setBackgroundDrawable(R.drawable.icon_button_action_red_touch)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_button_action_red_touch));


        SubActionButton bloodPressureSubButton = itemBuilder.setContentView(bloodPressureIcon).build();
        bloodPressureSubButton.setTag("bloodPressure");
        bloodPressureSubButton.setOnClickListener(this);
        SubActionButton glucoseSubButton = itemBuilder.setContentView(glucoseIcon).build();
        glucoseSubButton.setTag("glucose");
        glucoseSubButton.setOnClickListener(this);
        SubActionButton heartRateSubButton = itemBuilder.setContentView(heartRateIcon).build();
        heartRateSubButton.setTag("heartRate");
        heartRateSubButton.setOnClickListener(this);
        SubActionButton weightSubButton = itemBuilder.setContentView(weightIcon).build();
        weightSubButton.setTag("weight");
        weightSubButton.setOnClickListener(this);
        SubActionButton cholesterolSubButton = itemBuilder.setContentView(cholesterolIcon).build();
        cholesterolSubButton.setTag("cholesterol");
        cholesterolSubButton.setOnClickListener(this);

        FloatingActionMenu addActionMenuButtons = new FloatingActionMenu.Builder(this)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.action_menu_radius))

                .addSubActionView(heartRateSubButton)
                .addSubActionView(bloodPressureSubButton)
                .addSubActionView(glucoseSubButton)
                .addSubActionView(weightSubButton)
                .addSubActionView(cholesterolSubButton)
                .attachTo(addActionButton)
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VitalHeartRateFragment(), "heart rate");
        adapter.addFragment(new VitalBloodPressureFragment(), "blood pressure");
        adapter.addFragment(new VitalGlucoseFragment(), "glucose");
        adapter.addFragment(new VitalSignsWeightFragment(), "weight");
        adapter.addFragment(new VitalCholesterolFragment(), "cholesterol");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        /* this is how i did updating: https://www.youtube.com/watch?v=2lcBx4KVUVk */
        if(v.getTag().equals("weight")){
            addWeight(GlobalVariable.getUserId(this));
            Log.i("Tag","weight");
        }
        if(v.getTag().equals("bloodPressure")){
            addBloodPressure(GlobalVariable.getUserId(this));
            Log.i("Tag","pressure");
        }
        if(v.getTag().equals("heartRate")){
            addHeartRate(GlobalVariable.getUserId(this));
            Log.i("Tag","rate");
        }
        if(v.getTag().equals("glucose")){
            addGlucose(GlobalVariable.getUserId(this));
            Log.i("Tag","g");
        }
        if(v.getTag().equals("cholesterol")){
            addCholesterol(GlobalVariable.getUserId(this));
            Log.i("Tag","c");
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void addWeight(final String uid){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Weight");
        //alertDialogBuilder.setMessage("Add Weight?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String inputedWeight = input.getText().toString();
                ParseObject.registerSubclass(VitalWeightInfo.class);
                VitalWeightInfo newWeight= new VitalWeightInfo();
                newWeight.setUserId(uid);
                newWeight.setWeight(Integer.valueOf(inputedWeight));
                newWeight.saveInBackground();
            } //end of onClick method
        }); //end of setPositiveButton method
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {/*do nothing when answer is cancel*/  }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void addHeartRate(final String uid){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Heart Rate");
        //alertDialogBuilder.setMessage("Add Weight?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String inputedHeartRate = input.getText().toString();
                ParseObject.registerSubclass(VitalHeartRateInfo.class);
                VitalHeartRateInfo newHR= new VitalHeartRateInfo();
                newHR.setUserId(uid);
                newHR.setHeartRate(Integer.valueOf(inputedHeartRate));
                newHR.saveInBackground();
            } //end of onClick method
        }); //end of setPositiveButton method
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {/*do nothing when answer is cancel*/  }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void addGlucose(final String uid){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Glucose");
        //alertDialogBuilder.setMessage("Add Weight?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String inputedGlucose = input.getText().toString();
                ParseObject.registerSubclass(VitalGlucoseInfo.class);
                VitalGlucoseInfo newHR= new VitalGlucoseInfo();
                newHR.setUserId(uid);
                newHR.setGlucose(Integer.valueOf(inputedGlucose));
                newHR.saveInBackground();
            } //end of onClick method
        }); //end of setPositiveButton method
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {/*do nothing when answer is cancel*/  }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void addCholesterol(final String uid){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add Cholesterol");
        //alertDialogBuilder.setMessage("Add Weight?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String inputedCholesterol = input.getText().toString();
                ParseObject.registerSubclass(VitalCholesterolInfo.class);
                VitalCholesterolInfo newObj= new VitalCholesterolInfo();
                newObj.setUserId(uid);
                newObj.setCholesterol(Integer.valueOf(inputedCholesterol));
                newObj.saveInBackground();
            } //end of onClick method
        }); //end of setPositiveButton method
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {/*do nothing when answer is cancel*/  }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void addBloodPressure(final String uid){
            /* link on popup dialogs: http://www.tutorialspoint.com/android/android_alert_dialoges.htm */
        /*link on multiple text views: http://stackoverflow.com/questions/7334292/set-multiple-text-boxes-in-a-dialog-in-android  */
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LinearLayout lilal= new LinearLayout(this);
        lilal.setOrientation(LinearLayout.HORIZONTAL);
        alertDialogBuilder.setTitle("Add Blood Pressure");
        final EditText inputNum1 = new EditText(this);
        final EditText inputNum2 = new EditText(this);
        final TextView divider = new TextView(this);
        divider.setText(R.string.dividerSlash);
        divider.setPadding(1,5,1,5);
        inputNum1.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputNum1.setPadding(5, 5, 1, 5);
        inputNum2.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputNum2.setPadding(1,5,5,5);
        lilal.addView(inputNum1);
        lilal.addView(divider);
        lilal.addView(inputNum2);
        alertDialogBuilder.setView(lilal);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String firstNum = inputNum1.getText().toString();
                String secondNum = inputNum2.getText().toString();
                ParseObject.registerSubclass(VitalBloodPressureInfo.class);
                VitalBloodPressureInfo newBP= new VitalBloodPressureInfo();
                newBP.setUserId(uid);
                newBP.setFirstNumber(Integer.valueOf(firstNum));
                newBP.setSecondNumber(Integer.valueOf(secondNum));
                newBP.saveInBackground();
            } //end of onClick method
        }); //end of setPositiveButton method
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {/*do nothing when answer is cancel*/  }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
