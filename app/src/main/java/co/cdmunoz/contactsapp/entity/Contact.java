package co.cdmunoz.contactsapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import static co.cdmunoz.contactsapp.entity.Contact.TABLE_NAME;

@Entity(tableName = TABLE_NAME) public class Contact implements Parcelable {
  public static final String TABLE_NAME = "contacts";
  public static final String DATE_FIELD = "dateOfBirth";

  @PrimaryKey(autoGenerate = true) private int id;
  private String firstName;
  private String lastName;
  @ColumnInfo(name = DATE_FIELD) private LocalDateTime dateOfBirth;
  private String phoneNumber;
  private String email;
  private String address;

  public Contact(int id, String firstName, String lastName, String phoneNumber,
      LocalDateTime dateOfBirth, String email, String address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public LocalDateTime getDateOfBirth() {
    return dateOfBirth;
  }

  @Override public String toString() {
    return "Contact{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", dateOfBirth="
        + dateOfBirth
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }

  public long getDaysUntil() {
    return ChronoUnit.DAYS.between(LocalDateTime.now(), getDateOfBirth());
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.firstName);
    dest.writeString(this.lastName);
    dest.writeSerializable(this.dateOfBirth);
    dest.writeString(this.phoneNumber);
    dest.writeString(this.email);
    dest.writeString(this.address);
  }

  protected Contact(Parcel in) {
    this.id = in.readInt();
    this.firstName = in.readString();
    this.lastName = in.readString();
    this.dateOfBirth = (LocalDateTime) in.readSerializable();
    this.phoneNumber = in.readString();
    this.email = in.readString();
    this.address = in.readString();
  }

  public static final Creator<Contact> CREATOR = new Creator<Contact>() {
    @Override public Contact createFromParcel(Parcel source) {
      return new Contact(source);
    }

    @Override public Contact[] newArray(int size) {
      return new Contact[size];
    }
  };
}
