package co.cdmunoz.contactsapp.ui.contact.list;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import co.cdmunoz.contactsapp.entity.Contact;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.repository.ContactsRepository;
import co.cdmunoz.contactsapp.ui.contact.add.AddContactViewModel;
import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.threeten.bp.LocalDateTime;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactsListViewModelTest {

  ContactsListViewModel contactsListViewModel;

  @Mock ContactsRepository contactsRepository;

  @Rule public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @BeforeClass public static void setUpClass() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
  }

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    contactsListViewModel = new ContactsListViewModel();
    contactsListViewModel.contactsRepository = contactsRepository;
  }

  @AfterClass public static void tearDownClass() {
    RxAndroidPlugins.reset();
  }

  @Test public void getContacts() throws InterruptedException {
    MutableLiveData<List<Contact>> fakeContacts = getContactsListMutableData();
    when(contactsRepository.getContacts()).thenReturn(fakeContacts);

    contactsListViewModel.inject(new ContactsAppComponent() {
      @Override public void inject(ContactsListViewModel contactsListViewModel) {
        contactsListViewModel.contactsRepository = contactsRepository;
      }

      @Override public void inject(AddContactViewModel addContactViewModel) {

      }
    });
    List<Contact> contactsReturned = getValue(contactsListViewModel.getContacts());

    verify(contactsRepository).getContacts();
    assertEquals(1, contactsReturned.size());
    assertEquals("Name", contactsReturned.get(0).getFirstName());
  }

  @NonNull private MutableLiveData<List<Contact>> getContactsListMutableData() {
    List<Contact> contacts = new ArrayList<>();
    Contact contact =
        new Contact(1, "Name", "NameLast", "3009880000", LocalDateTime.now(), "a@a.com", "Cl 10 Cra 2");
    contacts.add(contact);
    MutableLiveData<List<Contact>> fakeContacts = new MutableLiveData<>();
    fakeContacts.setValue(contacts);
    return fakeContacts;
  }

  @Test public void deleteContact() {
    when(contactsRepository.deleteContact(any())).thenReturn(Completable.complete());

    contactsListViewModel.deleteContact(
        new Contact(1, "Name", "NameLast", "Description", LocalDateTime.now(), "a@a.com",
            "Cl 10 Cra 2"));

    verify(contactsRepository).deleteContact(any());
  }

  public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {
    final Object[] data = new Object[1];
    CountDownLatch latch = new CountDownLatch(1);
    Observer<T> observer = new Observer<T>() {
      @Override public void onChanged(@Nullable T o) {
        data[0] = o;
        latch.countDown();
        liveData.removeObserver(this);
      }
    };
    liveData.observeForever(observer);
    latch.await(2, TimeUnit.SECONDS);
    //noinspection unchecked
    return (T) data[0];
  }
}
