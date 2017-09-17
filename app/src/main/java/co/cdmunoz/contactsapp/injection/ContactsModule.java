package co.cdmunoz.contactsapp.injection;

import android.arch.persistence.room.Room;
import android.content.Context;
import co.cdmunoz.contactsapp.ContactsAppApplication;
import co.cdmunoz.contactsapp.db.ContactDatabase;
import co.cdmunoz.contactsapp.repository.ContactsRepository;
import co.cdmunoz.contactsapp.repository.ContactsRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ContactsModule {

  private ContactsAppApplication contactsAppApplication;

  public ContactsModule(ContactsAppApplication contactsAppApplication) {
    this.contactsAppApplication = contactsAppApplication;
  }

  @Provides Context applicationContext() {
    return contactsAppApplication;
  }

  @Provides @Singleton ContactsRepository providesContactsRepository(ContactDatabase contactDatabase) {
    return new ContactsRepositoryImpl(contactDatabase);
  }

  @Provides @Singleton ContactDatabase providesContactsDatabase(Context context) {
    return Room.databaseBuilder(context.getApplicationContext(), ContactDatabase.class,
        "contacts_db").build();
  }
}
