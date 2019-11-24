package frameblock.widget.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static String dateFormat(Date date) {
        if(date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(date);
        }
        return "";
    }
}
