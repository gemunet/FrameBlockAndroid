package frameblock.frameblockandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import frameblock.widget.util.StringUtil;
import frameblock.widget.util.ValidateUtil;
import frameblock.widget.util.WidgetUtils;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final EditText etName = findViewById(R.id.etName);
        final EditText etBirthDate = findViewById(R.id.etBirthDate);
        final EditText etTime = findViewById(R.id.etTime);
        final EditText etSex = findViewById(R.id.etSex);
        final SearchableSpinner spCountry = findViewById(R.id.spCountry);

        WidgetUtils.DateTimePicker(etBirthDate, getSupportFragmentManager(), new Date(), StringUtil.DATETIME_FORMAT);
        WidgetUtils.TimeDatePicker(etTime, getSupportFragmentManager(), new Date(), StringUtil.TIME_FORMAT);

        List<String> items = Arrays.asList("Chile", "Argentina", "Inglaterra");
        new WidgetUtils.ArraySpinner<String>(spCountry).init().update(items).setDefaultItem(items.get(0));

        Button btnSumbit = findViewById(R.id.btnSumbit);
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = ValidateUtil.checkNotEmpty("Required fields",
                        etName, etBirthDate, etTime, etSex);
                if(isValid) {
                    finish();
                } else {
                    Toast.makeText(FormActivity.this, R.string.required_fields, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
