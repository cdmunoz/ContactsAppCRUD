package co.cdmunoz.contactsapp.repository;

import android.arch.lifecycle.LiveData;
import co.cdmunoz.contactsapp.db.ContactDatabase;
import co.cdmunoz.contactsapp.entity.Contact;
import io.reactivex.Completable;
import java.util.List;
import javax.inject.Inject;

public class ContactsRepositoryImpl implements ContactsRepository {

  @Inject ContactDatabase contactDatabase;

  public ContactsRepositoryImpl(ContactDatabase contactDatabase) {
    this.contactDatabase = contactDatabase;
  }

  @Override public Completable addContact(Contact contact) {
    return Completable.fromAction(() -> contactDatabase.contactDao().addContact(contact));
  }

  @Override public LiveData<List<Contact>> getContacts() {
    return contactDatabase.contactDao().getContacts();
  }

  @Override public Completable deleteContact(Contact contact) {
    return Completable.fromAction(() -> contactDatabase.contactDao().deleteContact(contact));
  }

  @Override public Completable updateContact(Contact contact) {
    return Completable.fromAction(() -> contactDatabase.contactDao().updateContact(contact));
  }
}
