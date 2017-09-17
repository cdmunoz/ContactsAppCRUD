package co.cdmunoz.contactsapp.injection;

import co.cdmunoz.contactsapp.ui.contact.add.AddContactViewModel;
import co.cdmunoz.contactsapp.ui.contact.list.ContactsListViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { ContactsModule.class }) public interface ContactsAppComponent {

  void inject(ContactsListViewModel contactsListViewModel);

  void inject(AddContactViewModel addContactViewModel);

  interface Injectable {
    void inject(ContactsAppComponent contactsAppComponent);
  }
}
