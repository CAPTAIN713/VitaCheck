package vitacheck.vitacheck.fragments;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by ERIC on 11/20/2015.
 */
//the ParseClassName must be the as the parse table name
@ParseClassName("diet_food")
public class DietFoodInfo extends ParseObject {
    String id;
    /*create a new class to hold what every data you are working with.
    * follow the same set up as this class. have a get and set for every object you need
    * to get from parse. in the getString() and put() the item in the " " must be the same
    * as the column name that is in the parse table.
    *   -eric*/
    public String getParseID() {return id;}
    public void setParseID(String ID) {id=ID;}

    public String getUserId() {return getString("user_id");}
    public void setUserId(String name) {put("user_id",name);}

    public String getFoodName() {return getString("name");}
    public void setFoodName(String name) {put("name",name);}

    public int getFoodCalories() {return getInt("calories");}
    public void setFoodCalories(int num) {put("calories",num);}

    public Date getFoodDate() {return getDate("Date");}
    public void setFoodDate(Date date) {put("Date",date);}
}
