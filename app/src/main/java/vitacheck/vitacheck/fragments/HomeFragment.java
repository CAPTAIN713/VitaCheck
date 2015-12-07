package vitacheck.vitacheck.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import vitacheck.vitacheck.MainActivity;
import vitacheck.vitacheck.R;

public class HomeFragment extends Fragment {
    private List<VitalHeartRateInfo> vitalHeartRateList = new ArrayList<VitalHeartRateInfo>();
    private List<VitalBloodPressureInfo> vitalBloodPressureList = new ArrayList<VitalBloodPressureInfo>();
    private List<VitalGlucoseInfo> vitalGlucoseList = new ArrayList<VitalGlucoseInfo>();
    private List<VitalCholesterolInfo> vitalCholesterolList = new ArrayList<VitalCholesterolInfo>();
    private List<VitalWeightInfo> vitalWeightList = new ArrayList<VitalWeightInfo>();

    TextView heartRate;
    TextView bloodPressure;
    TextView glucoseLevel;
    TextView cholesterol;
    TextView weightLoss;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setTitle("Home");

        vitalHeartRateList = new ArrayList<VitalHeartRateInfo>();
        vitalBloodPressureList = new ArrayList<VitalBloodPressureInfo>();
        vitalGlucoseList = new ArrayList<VitalGlucoseInfo>();
        vitalCholesterolList = new ArrayList<VitalCholesterolInfo>();
        vitalWeightList = new ArrayList<VitalWeightInfo>();

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        heartRate = (TextView) layout.findViewById(R.id.heart_rate);
        bloodPressure = (TextView) layout.findViewById(R.id.blood_pressure);
        glucoseLevel = (TextView) layout.findViewById(R.id.glucose_level);
        weightLoss = (TextView) layout.findViewById(R.id.weight_loss);
        cholesterol = (TextView) layout.findViewById(R.id.cholesterol);


        // HEART RATE
        ParseObject.registerSubclass(VitalHeartRateInfo.class);
        ParseQuery<VitalHeartRateInfo> heart = new ParseQuery<VitalHeartRateInfo>("vital_heart_rate");
        heart.findInBackground(new FindCallback<VitalHeartRateInfo>() {
            @Override
            public void done(List<VitalHeartRateInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalHeartRateInfo hrObject : objects) {
                    if (hrObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalHeartRateInfo newHR = new VitalHeartRateInfo();
                        newHR.setParseId(hrObject.getObjectId());
                        newHR.setHeartRate(hrObject.getHeartRate());
                        newHR.setUploadDate(hrObject.getCreatedAt());
                        vitalHeartRateList.add(newHR);
                    }
                }

                if (vitalHeartRateList.isEmpty())
                {
                    heartRate.setText("---");
                } else {
                    int sum = 0;
                    for (int x=0; x<vitalHeartRateList.size(); x++)
                        sum += vitalHeartRateList.get(x).getHeartRate();
                    String output = Integer.toString(sum / vitalHeartRateList.size());
                    output += " bpm";
                    heartRate.setText(output);
                }
            }
        });

        // BLOOD PRESSURE
        ParseObject.registerSubclass(VitalBloodPressureInfo.class);
        final ParseQuery<VitalBloodPressureInfo> blood = new ParseQuery<VitalBloodPressureInfo>("vital_blood_pressure");
        blood.findInBackground(new FindCallback<VitalBloodPressureInfo>() {
            @Override
            public void done(List<VitalBloodPressureInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalBloodPressureInfo bpObject : objects) {
                    if (bpObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalBloodPressureInfo newBP = new VitalBloodPressureInfo();
                        newBP.setParseId(bpObject.getObjectId());
                        newBP.setFirstNumber(bpObject.getFirstNumber());
                        newBP.setSecondNumber(bpObject.getSecondNumber());
                        newBP.setUploadDate(bpObject.getCreatedAt());
                        vitalBloodPressureList.add(newBP);
                    }
                }

                if (vitalBloodPressureList.isEmpty())
                {
                    bloodPressure.setText("---/---");
                } else {
                    int top = 0;
                    int bot = 0;
                    for (int x=0; x<vitalBloodPressureList.size(); x++)
                    {
                        top += vitalBloodPressureList.get(x).getFirstNumber();
                        bot += vitalBloodPressureList.get(x).getSecondNumber();
                    }

                    String output = Integer.toString(top / vitalBloodPressureList.size());
                    output += "/";
                    output += Integer.toString(bot / vitalBloodPressureList.size());
                            bloodPressure.setText(output);
                }
            }
        });

        // GLUCOSE LEVEL
        ParseObject.registerSubclass(VitalGlucoseInfo.class);
        ParseQuery<VitalGlucoseInfo> glucose = new ParseQuery<VitalGlucoseInfo>("vital_glucose");
        glucose.findInBackground(new FindCallback<VitalGlucoseInfo>() {
            @Override
            public void done(List<VitalGlucoseInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalGlucoseInfo glucoseObject : objects) {
                    if (glucoseObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalGlucoseInfo newGlucose = new VitalGlucoseInfo();
                        newGlucose.setParseId(glucoseObject.getObjectId());
                        newGlucose.setGlucose(glucoseObject.getGlucose());
                        newGlucose.setUploadDate(glucoseObject.getCreatedAt());
                        vitalGlucoseList.add(newGlucose);
                    }
                }

                if (vitalGlucoseList.isEmpty())
                {
                    glucoseLevel.setText("---");
                } else {
                    int sum = 0;
                    for (int x=0; x<vitalGlucoseList.size(); x++)
                        sum += vitalGlucoseList.get(x).getGlucose();
                    String output = Integer.toString(sum / vitalGlucoseList.size());
                    output += " mg/dl";
                    glucoseLevel.setText(output);
                }
            }
        });

        // CHOLESTEROL
        ParseObject.registerSubclass(VitalCholesterolInfo.class);
        ParseQuery<VitalCholesterolInfo> cho = new ParseQuery<VitalCholesterolInfo>("vital_cholesterol");
        cho.findInBackground(new FindCallback<VitalCholesterolInfo>() {
            @Override
            public void done(List<VitalCholesterolInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalCholesterolInfo cholesterolObject : objects) {
                    if (cholesterolObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalCholesterolInfo newCholesterol = new VitalCholesterolInfo();
                        newCholesterol.setParseId(cholesterolObject.getObjectId());
                        newCholesterol.setCholesterol(cholesterolObject.getCholesterol());
                        newCholesterol.setUploadDate(cholesterolObject.getCreatedAt());
                        vitalCholesterolList.add(newCholesterol);
                    }
                }

                if (vitalCholesterolList.isEmpty())
                {
                    cholesterol.setText("---");
                } else {
                    int sum = 0;
                    for (int x=0; x<vitalCholesterolList.size(); x++)
                        sum += vitalCholesterolList.get(x).getCholesterol();
                    String output = Integer.toString(sum / vitalCholesterolList.size());
                    output += " mg/dl";
                    cholesterol.setText(output);
                }
            }
        });

        // WEIGHT
        ParseObject.registerSubclass(VitalWeightInfo.class);
        final ParseQuery<VitalWeightInfo> weight = new ParseQuery<VitalWeightInfo>("vital_weight");
        weight.findInBackground(new FindCallback<VitalWeightInfo>() {
            @Override
            public void done(List<VitalWeightInfo> objects, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getView().getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

                for (VitalWeightInfo hrObject : objects) {
                    if (hrObject.getUserId().equals(GlobalVariable.getUserId(getActivity()))) {
                        VitalWeightInfo newWeight = new VitalWeightInfo();
                        newWeight.setParseId(hrObject.getObjectId());
                        newWeight.setWeight(hrObject.getWeight());
                        newWeight.setUploadDate(hrObject.getCreatedAt());
                        vitalWeightList.add(newWeight);
                    }
                }

                if (vitalWeightList.isEmpty())
                {
                    weightLoss.setText("0 lbs");
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    double dWeight = Double.parseDouble(currentUser.get("weight").toString());
                    int endWeight = vitalWeightList.get(vitalWeightList.size()-1).getWeight();
                    int startWeight = (int) dWeight;
                    int diff = startWeight - endWeight;
                    String output = Integer.toString(diff);
                    output += " lbs";
                    weightLoss.setText(output);
                }
            }
        });

        
        return layout;
    }

} //end of fragment class