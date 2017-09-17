package co.cdmunoz.contactsapp.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import co.cdmunoz.contactsapp.ContactsAppApplication;

public class ContactsAppFactory extends ViewModelProvider.NewInstanceFactory {

  private ContactsAppApplication application;

  public ContactsAppFactory(ContactsAppApplication application) {
    this.application = application;
  }

  @Override public <T extends ViewModel> T create(Class<T> modelClass) {
    T t = super.create(modelClass);
    if (t instanceof ContactsAppComponent.Injectable) {
      ((ContactsAppComponent.Injectable) t).inject(application.getContactsAppComponent());
    }
    return t;
  }
}
