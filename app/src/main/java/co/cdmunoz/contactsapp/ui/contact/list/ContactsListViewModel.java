package co.cdmunoz.contactsapp.ui.contact.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import co.cdmunoz.contactsapp.entity.Contact;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.repository.ContactsRepository;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class ContactsListViewModel extends ViewModel implements ContactsAppComponent.Injectable {

  @Inject ContactsRepository contactsRepository;
  private LiveData<List<Contact>> contacts = new MutableLiveData<>();

  @Override public void inject(ContactsAppComponent contactsAppComponent) {
    contactsAppComponent.inject(this);
    contacts = contactsRepository.getContacts();
  }

  public LiveData<List<Contact>> getContacts() {
    return contacts;
  }

  public void deleteContact(Contact contact) {
    contactsRepository.deleteContact(contact)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onComplete() {
            Timber.d("onComplete - deleted contact");
          }

          @Override public void onError(Throwable e) {
            Timber.e("OnError - deleted contact: ", e);
          }
        });
  }
}
