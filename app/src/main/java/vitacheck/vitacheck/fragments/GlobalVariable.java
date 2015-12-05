package vitacheck.vitacheck.fragments;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Robert on 12/4/2015.
 */

//GlobalVariable.getUserID(Fragment or Activity) returns the current user's e-mail
public class GlobalVariable {
    public static String getUserId(Fragment frag){
        return ((ParseApplication) frag.getActivity().getApplication()).getUserId();
    }

    public static String getUserId(Activity act){
        return ((ParseApplication) act.getApplication()).getUserId();
    }
}
