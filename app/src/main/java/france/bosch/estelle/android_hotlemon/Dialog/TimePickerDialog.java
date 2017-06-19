package france.bosch.estelle.android_hotlemon.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import france.bosch.estelle.android_hotlemon.Fragments.Fragment_CreateEvent;

/**
 * Created by ESTEL on 30/05/2017.
 */


public class TimePickerDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private Calendar c;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        c = Calendar.getInstance();


       // c.setTimeInMillis(Fragment_CreateEvent.date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        c.set(year, month, day, 0, 0, 0);
      //  Fragment_CreateEvent.setDate(c.getTimeInMillis());
       // Fragment_CreateEvent.date = c.getTimeInMillis();
    }
}
