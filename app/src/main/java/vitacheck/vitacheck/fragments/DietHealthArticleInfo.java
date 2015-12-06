package vitacheck.vitacheck.fragments;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by btter_000 on 11/28/2015.
 */
//the ParseClassName must be the as the parse table name
@ParseClassName("diet_health_article")
public class DietHealthArticleInfo extends ParseObject {

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

    public String getHealthName() {return getString("name");}
    public void setHealthName(String name) {put("name",name);}

    public String getHealthURL() {return getString("URL");}
    public void setHealthURL(String URL) {put("URL",URL);}

    public String getHealthDescription() {return getString("note");}
    public void setHealthDescription(String desc) {put("note",desc);}
}
