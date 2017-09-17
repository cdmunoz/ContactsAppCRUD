package co.cdmunoz.contactsapp.ui.contact.add;

import android.arch.lifecycle.ViewModel;
import co.cdmunoz.contactsapp.entity.Contact;
import co.cdmunoz.contactsapp.injection.ContactsAppComponent;
import co.cdmunoz.contactsapp.repository.ContactsRepository;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import org.threeten.bp.LocalDateTime;
import timber.log.Timber;

public class AddContactViewModel extends ViewModel implements ContactsAppComponent.Injectable {

  @Inject ContactsRepository contactsRepository;

  private String contactFirstName;
  private String contactLastName;
  private LocalDateTime dobDateTime;
  private String contactPhone;
  private String contactEmail;
  private String contactAddress;

  public AddContactViewModel() {

  }

  public void addContact() {
    Contact contact =
        new Contact(0, contactFirstName, contactLastName, contactPhone, dobDateTime, contactEmail,
            contactAddress);
    contactsRepository.addContact(contact)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new CompletableObserver() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onComplete() {
            Timber.d("onComplete - successfully added contact");
          }

          @Override public void onError(Throwable e) {
            Timber.d("onError - add:", e);
          }
        });
  }

  @Override public void inject(ContactsAppComponent contactsAppComponent) {
    contactsAppComponent.inject(this);
  }

  public String getContactFirstName() {
    return contactFirstName;
  }

  public void setContactFirstName(String contactFirstName) {
    this.contactFirstName = contactFirstName;
  }

  public String getContactLastName() {
    return contactLastName;
  }

  public void setContactLastName(String contactLastName) {
    this.contactLastName = contactLastName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public LocalDateTime getDobDateTime() {
    return dobDateTime;
  }

  public void setDobDateTime(LocalDateTime dobDateTime) {
    this.dobDateTime = dobDateTime;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getContactAddress() {
    return contactAddress;
  }

  public void setContactAddress(String contactAddress) {
    this.contactAddress = contactAddress;
  }

  public void updateContact(Contact contact){
    contactsRepository.updateContact(contact)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new CompletableObserver() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onComplete() {
            Timber.d("onComplete - successfully updated contact");
          }

          @Override public void onError(Throwable e) {
            Timber.d("onError - update:", e);
          }
        });
  }
}
