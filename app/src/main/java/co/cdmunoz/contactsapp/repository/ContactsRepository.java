package co.cdmunoz.contactsapp.repository;

import android.arch.lifecycle.LiveData;
import co.cdmunoz.contactsapp.entity.Contact;
import io.reactivex.Completable;
import java.util.List;

public interface ContactsRepository {

  Completable addContact(Contact contact);

  LiveData<List<Contact>> getContacts();

  Completable deleteContact(Contact contact);

  Completable updateContact(Contact contact);
}
