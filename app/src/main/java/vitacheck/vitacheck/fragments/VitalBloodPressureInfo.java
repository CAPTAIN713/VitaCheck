package vitacheck.vitacheck.fragments;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by ERIC on 12/5/2015.
 */
//the ParseClassName must be the as the parse table name
@ParseClassName("vital_blood_pressure")
public class VitalBloodPressureInfo extends ParseObject
{
    private String id;
    private Date uploadDate;

    public String getParseId() { return id;}
    public void setParseId(String ID) { id=ID;}

    public String getUserId() {return getString("user_id");}
    public void setUserId(String user_id) {put("user_id",user_id);}

    public int getFirstNumber() {return getInt("bloodPressureFirstNum");}
    public void setFirstNumber(int num) {put("bloodPressureFirstNum",num);}

    public int getSecondNumber() {return getInt("bloodPressureSecondNum");}
    public void setSecondNumber(int num) {put("bloodPressureSecondNum",num);}

    public Date getUploadDate() {return uploadDate;}
    public void setUploadDate(Date date) {uploadDate=date;}

}
