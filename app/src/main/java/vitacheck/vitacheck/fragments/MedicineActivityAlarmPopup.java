package vitacheck.vitacheck.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vitacheck.vitacheck.R;

/**
 * Created by Robert on 12/4/2015.
 */
public class MedicineActivityAlarmPopup extends Activity implements View.OnClickListener{

    private TextView medicineNameTB,dosageAmountTB;
    private Button okPopupButton;

    private String name, dosage;
    private  Bundle extrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medicine_alarm_popup);
        getWindow().setLayout(900, 600);

        extrasBundle = getIntent().getExtras();

        if( !(extrasBundle.isEmpty()) && (extrasBundle.containsKey("name")) ){
            //checks if bundle is empty and if it has the parse id string
            name=extrasBundle.getString("name");
            dosage=extrasBundle.getString("dosage");
        }
        else{
            finish();
        }

        medicineNameTB=(TextView) findViewById(R.id.medicineNameIndv);
        dosageAmountTB=(TextView) findViewById(R.id.dosageAmountIndv);
        okPopupButton = (Button) findViewById(R.id.okPopupButton);
        okPopupButton.setOnClickListener(this);

        medicineNameTB.setText(name);
        dosageAmountTB.setText(dosage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.okPopupButton: //checks if bundle is empty and if it has the parse id string
                finish();
                break;
        }
    }
}
