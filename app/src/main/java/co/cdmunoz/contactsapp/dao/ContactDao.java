package co.cdmunoz.contactsapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import co.cdmunoz.contactsapp.entity.Contact;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao public interface ContactDao {

  @Query("SELECT * FROM " + Contact.TABLE_NAME) LiveData<List<Contact>> getContacts();

  @Insert(onConflict = OnConflictStrategy.REPLACE) void addContact(Contact contact);

  @Delete void deleteContact(Contact contact);

  @Update void updateContact(Contact contact);
}
