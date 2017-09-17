package co.cdmunoz.contactsapp.ui.contact.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import co.cdmunoz.contactsapp.R;
import co.cdmunoz.contactsapp.entity.Contact;

public class AddContactActivity extends AppCompatActivity {

  public static final String CONTACT = "Contact";

  Contact contact;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_contact);

    if(null != getIntent().getExtras()){
      contact = getIntent().getExtras().getParcelable(CONTACT);
    }
  }

  public Contact getContact() {
    return this.contact;
  }
}
