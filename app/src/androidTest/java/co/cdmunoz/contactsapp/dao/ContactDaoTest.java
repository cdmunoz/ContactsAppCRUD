package co.cdmunoz.contactsapp.dao;

import android.app.Instrumentation;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import co.cdmunoz.contactsapp.MockContactsAppApplication;
import co.cdmunoz.contactsapp.db.ContactDatabase;
import co.cdmunoz.contactsapp.entity.Contact;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.injection.MockContactsAppModule;
import dagger.Component;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) public class ContactDaoTest {

  ContactDao contactDao;

  @Inject ContactDatabase contactDatabase;

  @Singleton @Component(modules = { MockContactsAppModule.class })
  public interface MockContactsAppComponent extends ContactsAppComponent {
    void inject(ContactDaoTest contactDaoTest);
  }

  @Before public void setup() {
    Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    MockContactsAppApplication app =
        (MockContactsAppApplication) instrumentation.getTargetContext().getApplicationContext();
    MockContactsAppComponent component = (MockContactsAppComponent) app.getContactsAppComponent();
    component.inject(this);

    contactDao = contactDatabase.contactDao();
  }

  @Test public void addContact_SuccessfullyAddsContact() throws InterruptedException {
    Contact contact =
        generateContactTestData(0, "first", "last", "3002223333", "a@a.com", "Main Street");
    contactDao.addContact(contact);

    List<Contact> contactRetrieved = getValue(contactDao.getContacts());

    assertEquals(contact.getFirstName(), contactRetrieved.get(0).getFirstName());
    contactDao.deleteContact(contactRetrieved.get(0));
  }

  @Test public void deleteContact_SuccessfullyDeletesContact() throws InterruptedException {
    Contact contact =
        generateContactTestData(0, "first", "last", "3002223333", "a@a.com", "Main Street");
    contactDao.addContact(contact);

    List<Contact> contactRetrieved = getValue(contactDao.getContacts());
    assertEquals(contact.getFirstName(), contactRetrieved.get(0).getFirstName());

    contactDao.deleteContact(contactRetrieved.get(0));
    List<Contact> contactRetrievedAfterUpdate = getValue(contactDao.getContacts());

    assertEquals(0, contactRetrievedAfterUpdate.size());
  }

  @Test public void updateContact_SuccessfullyUpdatesContact() throws InterruptedException {
    Contact contact =
        generateContactTestData(0, "first", "last", "3002223333", "a@a.com", "Main Street");
    contactDao.addContact(contact);

    List<Contact> contactsRetrieved = getValue(contactDao.getContacts());
    Contact contactRetrieved = contactsRetrieved.get(0);
    assertEquals(contact.getFirstName(), contactRetrieved.getFirstName());

    String newName = "New Name";
    contactRetrieved.setFirstName(newName);
    contactDao.updateContact(contactRetrieved);

    List<Contact> contactRetrievedAfterUpdate = getValue(contactDao.getContacts());
    assertEquals(newName, contactRetrievedAfterUpdate.get(0).getFirstName());
    contactDao.deleteContact(contactRetrievedAfterUpdate.get(0));
  }

  private Contact generateContactTestData(int id, String firstName, String lastName,
      String phoneNumber, String email, String address) {
    return new Contact(id, firstName, lastName, phoneNumber, LocalDateTime.now().minusYears(25),
        email, address);
  }

  /**
   * This is used to make sure the method waits till data is available from the observer.
   */
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
