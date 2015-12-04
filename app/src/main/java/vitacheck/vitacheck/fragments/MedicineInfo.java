package vitacheck.vitacheck.fragments;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Robert on 12/3/2015.
 */
//the ParseClassName must be the as the parse table name
@ParseClassName("medicine")
public class MedicineInfo extends ParseObject {
    String id;
    /*create a new class to hold what every data you are working with.
    * follow the same set up as this class. have a get and set for every object you need
    * to get from parse. in the getString() and put() the item in the " " must be the same
    * as the column name that is in the parse table.
    *   -eric*/
    public String getParseId() { return id;}
    public void setParseId(String ID) { id=ID;}

    public String getName() {return getString("medicine_name");}
    public void setName(String name) {put("medicine_name",name);}

    public String getPrescribedBy() {return getString("prescribed_by");}
    public void setPrescribedBy(String prescribed) {put("prescribed_by",prescribed);}

    public int getDosageAmount() {return getInt("dosage_amount");}
    public void setDosageAmount(int num) {put("dosage_amount",num);}

    public int getDosagePerDay() {return getInt("dosage_per_day");}
    public void setDosagePerDay(int num) {put("dosage_per_day",num);}

    public String getNote() {return getString("note");}
    public void setNote(String note) {put("note",note);}
}