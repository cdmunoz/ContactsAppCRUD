package co.cdmunoz.contactsapp;

import android.app.Application;
import android.content.Context;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.injection.ContactsModule;
import co.cdmunoz.contactsapp.injection.DaggerContactsAppComponent;
import com.jakewharton.threetenabp.AndroidThreeTen;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import timber.log.Timber;

@ReportsCrashes(mailTo = "cdmunoz@gmail.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_report)
public class ContactsAppApplication extends Application {

  private final ContactsAppComponent contactsAppComponent = createContactsAppComponent();

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    ACRA.init(this);
  }

  @Override public void onCreate() {
    super.onCreate();
    AndroidThreeTen.init(this);
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  protected ContactsAppComponent createContactsAppComponent() {
    return DaggerContactsAppComponent.builder().contactsModule(new ContactsModule(this)).build();
  }

  public ContactsAppComponent getContactsAppComponent() {
    return contactsAppComponent;
  }
}
