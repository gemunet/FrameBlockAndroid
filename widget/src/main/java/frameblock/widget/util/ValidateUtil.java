package frameblock.widget.util;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ValidateUtil {

    public static <T> boolean checkNotEmpty(final String invalidMessage, final T... elements) {
        boolean isFormValid = true;

        for(T elem : elements) {
            if(elem instanceof EditText) {
                EditText et =  (EditText)elem;
                if(et.getText().length() == 0) {
                    isFormValid = false;
                    et.setError(invalidMessage);
                }
            }
            else if(elem instanceof Spinner) {
                //TODO: si la lista esta vacia no marca el icono de error porque getSelectedView es null
                final Spinner et =  (Spinner)elem;
                if(et.getSelectedItem() == null) {
                    isFormValid = false;
                    if(et.getSelectedView() != null) {
                        TextView errorText = (TextView) et.getSelectedView();
                        errorText.setError(invalidMessage);
                    }
                }
            }
        }

        return isFormValid;
    }

}
