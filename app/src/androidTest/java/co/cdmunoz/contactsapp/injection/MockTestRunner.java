package co.cdmunoz.contactsapp.injection;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;
import co.cdmunoz.contactsapp.MockContactsAppApplication;

public class MockTestRunner extends AndroidJUnitRunner {
  @Override public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, MockContactsAppApplication.class.getName(), context);
  }
}
