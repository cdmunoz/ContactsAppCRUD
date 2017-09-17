package co.cdmunoz.contactsapp.ui.contact.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import co.cdmunoz.contactsapp.R;

class ContactsViewHolder extends RecyclerView.ViewHolder {

  ImageView contactImage;
  TextView contactName;
  ImageButton deleteButton;

  ContactsViewHolder(View v) {
    super(v);
    contactName = v.findViewById(R.id.text_view_contact_name);
    deleteButton = v.findViewById(R.id.button_delete);
  }
}
