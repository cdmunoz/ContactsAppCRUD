package co.cdmunoz.contactsapp.ui.contact.add;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import co.cdmunoz.contactsapp.repository.ContactsRepository;
import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddContactViewModelTest {

  AddContactViewModel addContactViewModel;

  @Mock ContactsRepository contactsRepository;

  @Rule public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @BeforeClass public static void setUpClass() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
  }

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    addContactViewModel = new AddContactViewModel();
    addContactViewModel.contactsRepository = contactsRepository;
  }

  @AfterClass public static void tearDownClass() {
    RxAndroidPlugins.reset();
  }

  @Test public void addContact() {
    when(contactsRepository.addContact(any())).thenReturn(Completable.complete());
    addContactViewModel.addContact();
    verify(contactsRepository).addContact(any());
  }

  @Test public void updateContact() {
    when(contactsRepository.updateContact(any())).thenReturn(Completable.complete());
    addContactViewModel.updateContact(any());
    verify(contactsRepository).updateContact(any());
  }
}