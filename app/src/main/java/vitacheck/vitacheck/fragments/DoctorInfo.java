package vitacheck.vitacheck.fragments;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by ERIC on 11/20/2015.
 */
//the ParseClassName must be the as the parse table name
@ParseClassName("doctor")
public class DoctorInfo extends ParseObject {
    /*create a new class to hold what every data you are working with.
    * follow the same set up as this class. have a get and set for every object you need
    * to get from parse. in the getString() and put() the item in the " " must be the same
    * as the column name that is in the parse table.
    *   -eric*/
    public String getObjectID() {return getString("objectId");}
    public void setObjectID(String id) {put("objectId",id);}

    public String getName() {return getString("doctor_name");}
    public void setName(String name) {put("doctor_name",name);}

    public String getDoctorType() {return getString("doctor_type");}
    public void setDoctorType(String type) {put("doctor_type",type);}

    public String getInsurance() {return getString("insurance_type");}
    public void setInsurance(String IT) {put("insurance_type",IT);}

    public int getPhoneNum() {return getInt("phone_number");}
    public void setPhoneNum(String num) {put("phone_number",num);}

    public String getEmail() {return getString("email");}
    public void setEmail(String mail) {put("email",mail);}

    public String getAddress() {return getString("address");}
    public void setAddress(String adres) {put("address",adres);}

    public String getURL() {return getString("website_link");}
    public void setURL(String URL) {put("website_link",URL);}

    public Date getVisitDate() {return getDate("visit_dates");}
    public void setVisitDate(Date date) {put("visit_dates",date);}
}
