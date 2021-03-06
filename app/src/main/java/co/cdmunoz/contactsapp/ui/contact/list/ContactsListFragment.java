package co.cdmunoz.contactsapp.ui.contact.list;

import android.app.SearchManager;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import co.cdmunoz.contactsapp.ContactsAppApplication;
import co.cdmunoz.contactsapp.R;
import co.cdmunoz.contactsapp.entity.Contact;
import co.cdmunoz.contactsapp.injection.ContactsAppFactory;
import co.cdmunoz.contactsapp.ui.contact.add.AddContactActivity;
import java.util.ArrayList;
import java.util.List;

public class ContactsListFragment extends LifecycleFragment {

  private static final String TAG = "ContactsListFragment";

  FloatingActionButton floatingActionButton;
  RecyclerView recyclerView;

  private ContactsAdapter adapter;
  private ContactsListViewModel contactsListViewModel;
  private List<Contact> contacts;

  private View.OnClickListener deleteClickListener = v -> {
    Contact contact = (Contact) v.getTag();
    contactsListViewModel.deleteContact(contact);
  };

  private View.OnClickListener itemClickListener = v -> {
    Contact contact = (Contact) v.getTag();
    Intent intent = new Intent(getActivity(), AddContactActivity.class);
    intent.putExtra(AddContactActivity.CONTACT, contact);
    startActivity(intent);
  };

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_list_contacts, container, false);

    setupRecyclerView(v);
    floatingActionButton = v.findViewById(R.id.fab_add);
    floatingActionButton.setOnClickListener(
        view -> startActivity(new Intent(getContext(), AddContactActivity.class)));

    ContactsAppApplication application = (ContactsAppApplication) getActivity().getApplication();
    contactsListViewModel = ViewModelProviders.of(this, new ContactsAppFactory(application))
        .get(ContactsListViewModel.class);

    contactsListViewModel.getContacts().observe(this, contacts -> {
      adapter.setItems(contacts);
      this.contacts = contacts;
    });

    setHasOptionsMenu(true);
    return v;
  }

  private void setupRecyclerView(View v) {
    recyclerView = v.findViewById(R.id.recycler_view_list_contacts);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new ContactsAdapter(new ArrayList<>(), getContext(), itemClickListener,
        deleteClickListener);
    recyclerView.setAdapter(adapter);
    final DividerItemDecoration dividerItemDecoration =
        new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_list, menu);

    // Associate searchable configuration with the SearchView
    SearchManager searchManager =
        (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String s) {
        return false;
      }

      @Override public boolean onQueryTextChange(String s) {
        // Search Filter
        final List<Contact> filteredModelList = filter(contacts, s);
        if (filteredModelList.size() > 0) {
          adapter.setFilter(filteredModelList);
          return true;
        } else {
          // If not matching search filter data
          Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
          return false;
        }
      }
    });
  }

  private List<Contact> filter(List<Contact> models, String query) {
    query = query.toLowerCase().replaceAll("\\s", "");
    final List<Contact> filteredModelList = new ArrayList<>();
    String names;
    for (Contact model : models) {
      names = model.getFirstName().toLowerCase() + " " + model.getLastName().toLowerCase();
      names = names.replaceAll("\\s", "");
      if (names.contains(query)) {
        filteredModelList.add(model);
      }
    }
    adapter = new ContactsAdapter(filteredModelList, getContext(), itemClickListener,
        deleteClickListener);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();

    return filteredModelList;
  }
}
