package co.cdmunoz.contactsapp;

import co.cdmunoz.contactsapp.dao.DaggerContactDaoTest_MockContactsAppComponent;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.injection.MockContactsAppModule;

public class MockContactsAppApplication extends ContactsAppApplication {

  @Override public ContactsAppComponent createContactsAppComponent() {
    return DaggerContactDaoTest_MockContactsAppComponent.builder()
        .mockContactsAppModule(new MockContactsAppModule(this))
        .build();
  }
}
