package frameblock.widget;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeFragment extends DialogFragment {

    private static final String ARG_DIALOG_TYPE = "ARG_DIALOG_TYPE";
    private static final String ARG_INITIAL_DATE = "ARG_INITIAL_DATE";

    public enum DialogType {
        DIALOG_DATE,
        DIALOG_TIME,
    }

    public interface DateTimeListener {
        void onDateTimeSet(Date date);
    }

    private DateTimeListener dateTimeListener;

    public static DateTimeFragment newInstance(DateTimeFragment.DialogType type, Date initialDate, DateTimeListener dateTimeListener) {
        DateTimeFragment dtf = new DateTimeFragment(dateTimeListener);

        Bundle args = new Bundle();
        args.putSerializable(ARG_INITIAL_DATE, initialDate != null ? initialDate : new Date());
        args.putSerializable(ARG_DIALOG_TYPE, type);
        dtf.setArguments(args);

        return dtf;
    }

    public DateTimeFragment(DateTimeListener dateTimeListener) {
        this.dateTimeListener = dateTimeListener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_date_time, null);
        builder.setView(view);
        binding(view);

        return builder.create();
    }

    private void binding(View view) {
        DialogType type = (DialogType)getArguments().getSerializable(ARG_DIALOG_TYPE);
        Date initial = (Date)getArguments().getSerializable(ARG_INITIAL_DATE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(initial);


        final DatePicker date = view.findViewById(R.id.pickerDate);
        date.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        final TimePicker time = view.findViewById(R.id.pickerTime);
        time.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        time.setCurrentMinute(cal.get(Calendar.MINUTE));

        final Button btnCancel = view.findViewById(R.id.btnCancel);
        final Button btnOK = view.findViewById(R.id.btnOK);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getCurrentHour(), time.getCurrentMinute(), 0);

                if(dateTimeListener != null) {
                    dateTimeListener.onDateTimeSet(cal.getTime());
                }

                dismiss();
            }
        });


        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout .addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        date.setVisibility(View.VISIBLE);
                        time.setVisibility(View.GONE);
                        break;
                    case 1:
                        date.setVisibility(View.GONE);
                        time.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        TabLayout.Tab tabDate = tabLayout .getTabAt(0);
        TabLayout.Tab tabTime = tabLayout .getTabAt(1);

        SimpleDateFormat formatDate = new SimpleDateFormat("EEE dd/MM/yyyy");
        tabDate.setText(formatDate.format(initial));

        SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
        tabTime.setText(formatTime.format(initial));

        if(type == DialogType.DIALOG_DATE) {
            tabDate.select();
        } else {
            tabTime.select();
        }
    }


}
