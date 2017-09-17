package co.cdmunoz.contactsapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import co.cdmunoz.contactsapp.dao.ContactDao;
import co.cdmunoz.contactsapp.entity.Contact;

@Database(entities = { Contact.class }, version = 1) @TypeConverters(DateTypeConverter.class)
public abstract class ContactDatabase extends RoomDatabase {

  public abstract ContactDao contactDao();
}
