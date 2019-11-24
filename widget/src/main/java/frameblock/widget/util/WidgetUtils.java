package frameblock.widget.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import frameblock.widget.DateTimeFragment;
import frameblock.widget.R;
import frameblock.widget.adapter.RecyclerArrayAdapter;

public class WidgetUtils {

    public static class ArraySpinner<T> {
        private Context context;
        private Spinner spinner;
        private T defaultItem;

        public ArraySpinner(Spinner spinner) {
            this.context = spinner.getContext();
            this.spinner = spinner;
        }

        public ArraySpinner<T> init() {
            ArrayAdapter<T> adapter  = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new ArrayList<T>());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            if(spinner instanceof SearchableSpinner) {
                ((SearchableSpinner)spinner).setTitle(context.getResources().getString(R.string.fb_spinner_select_title));
                ((SearchableSpinner)spinner).setPositiveButton(context.getResources().getString(R.string.fb_cancel));
            }
            return this;
        }

        public ArraySpinner<T> update(List<T> items) {
            ArrayAdapter<T> adapter = (ArrayAdapter<T>)spinner.getAdapter();
            adapter.clear();
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
            set(this.defaultItem);
            return this;
        }

        public ArraySpinner<T> set(T item) {
            ArrayAdapter<T> adapter = (ArrayAdapter<T>)spinner.getAdapter();
            spinner.setSelection(adapter.getPosition(item));
            return this;
        }

        public ArraySpinner<T> setDefaultItem(T item) {
            this.defaultItem = item;
            return this;
        }

        public ArraySpinner<T> update(@NonNull final LifecycleOwner owner, final LiveData<List<T>> items) {
            items.observe(owner, new Observer<List<T>>() {
                @Override
                public void onChanged(List<T> ts) {
                    update(ts);
                }
            });
            return this;
        }
    }

    public static class RecyclerHelper<T> {
        private Context context;
        private RecyclerView recList;

        public RecyclerHelper(RecyclerView recList) {
            this.context = recList.getContext();
            this.recList = recList;
        }

        public RecyclerHelper<T> init(RecyclerArrayAdapter adapter) {
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            llm.setOrientation(RecyclerView.VERTICAL);
            recList.setLayoutManager(llm);
            recList.setAdapter(adapter);
            return this;
        }

        public RecyclerHelper<T> update(List<T> items) {
            RecyclerArrayAdapter adapter = (RecyclerArrayAdapter)recList.getAdapter();
            adapter.update(items);
            return this;
        }
    }

    public static void TimeDatePicker(final EditText et, final FragmentManager fragmentManager, Date defaultItem) {
        initDateTime(et, fragmentManager, DateTimeFragment.DialogType.DIALOG_TIME, defaultItem);
    }

    public static void DateTimePicker(final EditText et, final FragmentManager fragmentManager, Date defaultItem) {
        initDateTime(et, fragmentManager, DateTimeFragment.DialogType.DIALOG_DATE, defaultItem);
    }

    private static void initDateTime(final EditText et, final FragmentManager fragmentManager, final DateTimeFragment.DialogType type, final Date defaultItem) {
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment dialogFragment = DateTimeFragment.newInstance(type,
                        view.getTag() != null ? (Date)view.getTag() : defaultItem, new DateTimeFragment.DateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        Log.d("DATE", ""+date);
                        EditText editText = (EditText)view;
                        editText.setText(StringUtil.dateFormat(date));
                        editText.setError(null);
                        editText.setTag(date);
                    }
                });
                dialogFragment.show(fragmentManager, "dialog");
            }
        });
        et.setText(StringUtil.dateFormat(defaultItem));
        et.setTag(defaultItem);
    }
}
