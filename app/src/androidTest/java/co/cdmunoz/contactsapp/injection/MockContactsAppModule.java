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

@Module public class MockContactsAppModule {

  private final ContactsAppApplication application;

  public MockContactsAppModule(ContactsAppApplication contactsAppApplication) {
    this.application = contactsAppApplication;
  }

  @Provides Context getApplicationContext() {
    return application;
  }

  @Provides @Singleton ContactDatabase provideContactsDatabase(Context context) {
    return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), ContactDatabase.class)
        .build();
  }

  @Provides @Singleton ContactsRepository providesContactsRepository(
      ContactDatabase contactDatabase) {
    return new ContactsRepositoryImpl(contactDatabase);
  }
}
