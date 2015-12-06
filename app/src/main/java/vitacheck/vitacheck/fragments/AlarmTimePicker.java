package vitacheck.vitacheck.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.TimePicker;

/**
 * Created by Robert on 12/6/2015.
 */
//Custom TimePicker class. Allows time to be picked, even though picker might be inside a ScrollView.
//Otherwise, when trying to pick times, the page would scroll instead.
public class AlarmTimePicker extends TimePicker {

    public AlarmTimePicker(Context context) {
        super(context);
    }

    public AlarmTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewParent parentView = getParent();

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (parentView != null) {
                parentView.requestDisallowInterceptTouchEvent(true);
            }
        }

        return false;
    }
}
