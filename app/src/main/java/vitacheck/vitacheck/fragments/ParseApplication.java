package vitacheck.vitacheck.fragments;

/**
 * Created by ERIC on 11/20/2015.
 */
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends android.app.Application {
    /*parse would not work for me if i didn't have this class.
    * not sure if ya'll need it.
    *   -eric*/
//http://stackoverflow.com/questions/28721509/unable-to-start-activity-componentinfo-parse
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xJQq6UTUqt4IxtnQPmOTWAqtJAVmXHZtbVFhtDdb", "h56YvQcc77YsEmYj0RbfuJuDut8MUA5IXECwVqoP");
    }

}