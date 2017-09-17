package co.cdmunoz.contactsapp.ui.contact;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.threeten.bp.LocalDateTime;

public class ViewUtilities {

  public static String getInputFromEditTexts(LinearLayout mParentLayout) {
    String[] inputArray = new String[mParentLayout.getChildCount()];
    StringBuilder sb = new StringBuilder();
    int length = inputArray.length;
    for (int i = 0; i < length; i++) {
      EditText editText = (EditText) mParentLayout.getChildAt(i);
      sb.append(editText.getText().toString());
      if (i != (length - 1)) sb.append("-");
    }
    return sb.toString();
  }

  public static int getPixelsFromDp(Context context, int dp) {
    float density = context.getResources().getDisplayMetrics().density;
    return Math.round(dp * density);
  }

  public static boolean checkIfValueSet(EditText text, String description) {
    if (TextUtils.isEmpty(text.getText())) {
      text.setError("Missing field " + description);
      return false;
    } else {
      text.setError(null);
      return true;
    }
  }

  public static LocalDateTime getLocalDateTimeFromString(String date) {
    String[] yearMonthDay = date.split("-");
    int year = Integer.parseInt(yearMonthDay[0]);
    int month = Integer.parseInt(yearMonthDay[1]);
    int day = Integer.parseInt(yearMonthDay[2]);
    return LocalDateTime.of(year, month, day, 0, 0);
  }
}
