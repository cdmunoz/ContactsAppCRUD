package co.cdmunoz.contactsapp;

import android.app.Application;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.injection.ContactsModule;
import co.cdmunoz.contactsapp.injection.DaggerContactsAppComponent;
import com.jakewharton.threetenabp.AndroidThreeTen;
import timber.log.Timber;

public class ContactsAppApplication extends Application {

  private final ContactsAppComponent contactsAppComponent = createContactsAppComponent();

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
