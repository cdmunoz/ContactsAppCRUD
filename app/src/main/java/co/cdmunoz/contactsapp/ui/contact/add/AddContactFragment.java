package co.cdmunoz.contactsapp.ui.contact.add;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import co.cdmunoz.contactsapp.ContactsAppApplication;
import co.cdmunoz.contactsapp.R;
import co.cdmunoz.contactsapp.entity.Contact;
import co.cdmunoz.contactsapp.injection.ContactsAppFactory;
import co.cdmunoz.contactsapp.ui.contact.ViewUtilities;
import org.threeten.bp.LocalDateTime;

public class AddContactFragment extends LifecycleFragment
    implements DatePickerDialog.OnDateSetListener {

  ActionBar actionBar;
  private EditText firstName, lastName, dateOfBirth, phoneNumber, email, address;
  private LinearLayout layoutPhone;
  private LinearLayout layoutEmail;
  private LinearLayout layoutAddress;
  private TextView buttonAddPhone;
  private TextView buttonAddEmail;
  private TextView buttonAddAddress;
  private AddContactViewModel addContactViewModel;

  Contact contact;
  int phoneIdNmbr = 0;
  int emailIdNmbr = 0;
  int addressIdNmbr = 0;
  int nbrOfPhones = 1;
  int nbrOfEmails = 1;
  int nbrOfAddress = 1;
  int maxLength = 3;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
    setupViews(view);
    setupClickListeners();
    setupViewModel();
    setHasOptionsMenu(true);
    return view;
  }

  private void setupViews(View view) {
    firstName = view.findViewById(R.id.edit_text_first_name);
    lastName = view.findViewById(R.id.edit_text_last_name);
    dateOfBirth = view.findViewById(R.id.text_view_date_of_birth);
    layoutPhone = view.findViewById(R.id.layout_phone);
    phoneNumber = view.findViewById(R.id.edit_text_phone_number0);
    buttonAddPhone = view.findViewById(R.id.btn_addPhone);
    layoutEmail = view.findViewById(R.id.layout_email);
    email = view.findViewById(R.id.edit_text_email);
    buttonAddEmail = view.findViewById(R.id.btn_addEmail);
    layoutAddress = view.findViewById(R.id.layout_address);
    address = view.findViewById(R.id.edit_text_address);
    buttonAddAddress = view.findViewById(R.id.btn_addAddress);
    actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
  }

  private void setupViewModel() {
    ContactsAppApplication contactsAppApplication =
        (ContactsAppApplication) getActivity().getApplication();
    addContactViewModel =
        ViewModelProviders.of(this, new ContactsAppFactory(contactsAppApplication))
            .get(AddContactViewModel.class);
    firstName.setText(addContactViewModel.getContactFirstName());
    lastName.setText(addContactViewModel.getContactLastName());
    String dob = addContactViewModel.getDobDateTime() != null ? addContactViewModel.getDobDateTime()
        .toString()
        .substring(0, 10) : "";
    dateOfBirth.setText(dob);
    phoneNumber.setText(addContactViewModel.getContactPhone());
    email.setText(addContactViewModel.getContactEmail());
    address.setText(addContactViewModel.getContactAddress());
  }

  private void setupClickListeners() {
    firstName.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        addContactViewModel.setContactFirstName(s.toString());
      }
    });
    lastName.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        addContactViewModel.setContactLastName(s.toString());
      }
    });
    dateOfBirth.setOnClickListener(v -> {
      LocalDateTime localDateTime =
          addContactViewModel.getDobDateTime() != null ? addContactViewModel.getDobDateTime()
              : LocalDateTime.now();
      DatePickerDialog datePickerDialog =
          new DatePickerDialog(getContext(), this, localDateTime.getYear(),
              localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth());
      datePickerDialog.show();
    });
    buttonAddPhone.setOnClickListener(v -> createPhoneEditTextDynamically(""));
    buttonAddEmail.setOnClickListener(v -> createEmailEditTextDynamically(""));
    buttonAddAddress.setOnClickListener(v -> createAddressEditTextDynamically(""));
  }

  private boolean allFieldsOk() {
    if (!ViewUtilities.checkIfValueSet(firstName, "first name")) {
      return false;
    }
    if (!ViewUtilities.checkIfValueSet(lastName, "last name")) {
      return false;
    }
    if (!ViewUtilities.checkIfValueSet(dateOfBirth, "last name")) {
      return false;
    }
    if (!ViewUtilities.checkIfValueSet(phoneNumber, "phone number")) {
      return false;
    }
    if (!ViewUtilities.checkIfValueSet(email, "email")) {
      return false;
    }
    return true;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    contact = ((AddContactActivity) getActivity()).getContact();
    String toolbarTitle = getResources().getString(R.string.add_contact);
    if (null != contact) {
      setContactInfo();
      toolbarTitle = getResources().getString(R.string.update_contact);
    }
    actionBar.setTitle(toolbarTitle);
  }

  private void setContactInfo() {
    firstName.setText(contact.getFirstName());
    lastName.setText(contact.getLastName());
    String dob =
        contact.getDateOfBirth() != null ? contact.getDateOfBirth().toString().substring(0, 10)
            : "";
    dateOfBirth.setText(dob);
    String[] phoneNumbers = contact.getPhoneNumber().split("-");
    setPhoneInfoToUpdate(phoneNumbers);
    String[] emails = contact.getEmail().split("-");
    setEmailInfoToUpdate(emails);
    if (!TextUtils.isEmpty(contact.getAddress())) {
      String[] addresses = contact.getAddress().split("-");
      setAddressInfoToUpdate(addresses);
    } else {
      address.setText("");
    }
  }

  private void createPhoneEditTextDynamically(String text) {
    if (nbrOfPhones < maxLength) {
      EditText editTxt = new EditText(getContext());
      phoneIdNmbr++;
      editTxt.setHint(getResources().getString(R.string.phone_number) + " " + phoneIdNmbr);
      editTxt.setInputType(InputType.TYPE_CLASS_PHONE);
      editTxt.setTag("edit_text_phone_number" + phoneIdNmbr);
      if (!TextUtils.isEmpty(text)) {
        editTxt.setText(text);
      }
      LinearLayout.LayoutParams layoutParams =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      int pixelsFromDp8 = ViewUtilities.getPixelsFromDp(getContext(), 8);
      int pixelsFromDp24 = ViewUtilities.getPixelsFromDp(getContext(), 24);
      layoutParams.setMargins(pixelsFromDp8, 0, pixelsFromDp24, pixelsFromDp8);
      editTxt.setLayoutParams(layoutParams);
      layoutPhone.addView(editTxt);
      nbrOfPhones++;
    }
  }

  private void createEmailEditTextDynamically(String text) {
    if (nbrOfEmails < maxLength) {
      EditText editTxt = new EditText(getContext());
      emailIdNmbr++;
      editTxt.setHint(getResources().getString(R.string.email) + " " + emailIdNmbr);
      editTxt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
      editTxt.setTag("edit_text_email" + emailIdNmbr);
      if (!TextUtils.isEmpty(text)) {
        editTxt.setText(text);
      }
      LinearLayout.LayoutParams layoutParams =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      int pixelsFromDp8 = ViewUtilities.getPixelsFromDp(getContext(), 8);
      int pixelsFromDp24 = ViewUtilities.getPixelsFromDp(getContext(), 24);
      layoutParams.setMargins(pixelsFromDp8, 0, pixelsFromDp24, pixelsFromDp8);
      editTxt.setLayoutParams(layoutParams);
      layoutEmail.addView(editTxt);
      nbrOfEmails++;
    }
  }

  private void createAddressEditTextDynamically(String text) {
    if (nbrOfAddress < maxLength) {
      EditText editTxt = new EditText(getContext());
      addressIdNmbr++;
      editTxt.setHint(getResources().getString(R.string.address) + " " + addressIdNmbr);
      editTxt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
      editTxt.setTag("edit_text_address" + addressIdNmbr);
      if (!TextUtils.isEmpty(text)) {
        editTxt.setText(text);
      }
      LinearLayout.LayoutParams layoutParams =
          new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      int pixelsFromDp8 = ViewUtilities.getPixelsFromDp(getContext(), 8);
      int pixelsFromDp24 = ViewUtilities.getPixelsFromDp(getContext(), 24);
      layoutParams.setMargins(pixelsFromDp8, 0, pixelsFromDp24, pixelsFromDp8);
      editTxt.setLayoutParams(layoutParams);
      layoutAddress.addView(editTxt);
      nbrOfAddress++;
    }
  }

  private void setPhoneInfoToUpdate(String[] phoneNumbers) {
    for (int i = 0; i < phoneNumbers.length; i++) {
      if (i == 0) {
        phoneNumber.setText(phoneNumbers[i]);
      } else {
        createPhoneEditTextDynamically(phoneNumbers[i]);
      }
    }
  }

  private void setEmailInfoToUpdate(String[] emails) {
    for (int i = 0; i < emails.length; i++) {
      if (i == 0) {
        email.setText(emails[i]);
      } else {
        createEmailEditTextDynamically(emails[i]);
      }
    }
  }

  private void setAddressInfoToUpdate(String[] addresses) {
    for (int i = 0; i < addresses.length; i++) {
      if (i == 0) {
        email.setText(addresses[i]);
      } else {
        createAddressEditTextDynamically(addresses[i]);
      }
    }
  }

  @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    addContactViewModel.setDobDateTime(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0));
    dateOfBirth.setText(addContactViewModel.getDobDateTime().toString().substring(0, 10));
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_save, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save:
        if (null == contact) {//save
          addContactViewModel.setContactPhone(ViewUtilities.getInputFromEditTexts(layoutPhone));
          addContactViewModel.setContactEmail(ViewUtilities.getInputFromEditTexts(layoutEmail));
          addContactViewModel.setContactAddress(ViewUtilities.getInputFromEditTexts(layoutAddress));
          if (allFieldsOk()) {
            addContactViewModel.addContact();
            getActivity().finish();
          }
        } else {//update
          if (allFieldsOk()) {
            Contact contactToUpdate = new Contact(contact.getId(), firstName.getText().toString(),
                lastName.getText().toString(), ViewUtilities.getInputFromEditTexts(layoutPhone),
                ViewUtilities.getLocalDateTimeFromString(
                    dateOfBirth.getText().toString().substring(0, 10)),
                ViewUtilities.getInputFromEditTexts(layoutEmail),
                ViewUtilities.getInputFromEditTexts(layoutAddress));
            addContactViewModel.updateContact(contactToUpdate);
            getActivity().finish();
          }
        }
        break;
      case android.R.id.home:
        getActivity().finish();
        break;
      default:
        break;
    }
    return true;
  }
}
