package vitacheck.vitacheck.fragments;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by ERIC on 12/5/2015.
 */
//the ParseClassName must be the as the parse table name
@ParseClassName("vital_heart_rate")
public class VitalHeartRateInfo extends ParseObject
{
    private String id;
    private Date uploadDate;

    public String getParseId() { return id;}
    public void setParseId(String ID) { id=ID;}

    public String getUserId() {return getString("user_id");}
    public void setUserId(String user_id) {put("user_id",user_id);}

    public int getHeartRate() {return getInt("bpm");}
    public void setHeartRate(int num) {put("bpm",num);}

    public Date getUploadDate() {return uploadDate;}
    public void setUploadDate(Date date) {uploadDate=date;}

}
