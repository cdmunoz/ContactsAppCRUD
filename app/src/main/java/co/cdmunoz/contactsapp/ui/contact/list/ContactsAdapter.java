package co.cdmunoz.contactsapp.ui.contact.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.cdmunoz.contactsapp.R;
import co.cdmunoz.contactsapp.entity.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {
  private final Context context;
  private List<Contact> items;
  private View.OnClickListener deleteClickListener;
  private View.OnClickListener viewClickListener;
  private ArrayList<Contact> mModel;

  ContactsAdapter(List<Contact> items, Context context, View.OnClickListener viewClickListener,
      View.OnClickListener deleteClickListener) {
    this.items = items;
    this.context = context;
    this.deleteClickListener = deleteClickListener;
    this.viewClickListener = viewClickListener;
  }

  @Override public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
    return new ContactsViewHolder(v);
  }

  @Override public void onBindViewHolder(ContactsViewHolder holder, int position) {
    Contact item = items.get(position);
    String contactName = item.getFirstName() + " " + item.getLastName();
    holder.contactName.setText(contactName);
    holder.deleteButton.setTag(item);
    holder.deleteButton.setOnClickListener(deleteClickListener);
    holder.itemView.setOnClickListener(viewClickListener);
    holder.itemView.setTag(item);
  }

  @Override public int getItemCount() {
    return items.size();
  }

  void setItems(List<Contact> contacts) {
    this.items = contacts;
    notifyDataSetChanged();
  }

  public void setFilter(List<Contact> contactModels) {
    mModel = new ArrayList<>();
    mModel.addAll(contactModels);
    notifyDataSetChanged();
  }
}