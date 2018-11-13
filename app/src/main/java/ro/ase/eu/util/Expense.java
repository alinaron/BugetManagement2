package ro.ase.eu.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class Expense implements Parcelable { /*implements Serializable {*/

    private Date date;
    private String category;
    private Double amount;
    private String description;

    public Expense(Date date, String category, Double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }

    private Expense(Parcel in) {
        try {
            date = Constants.simpleDateFormat
                    .parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        category = in.readString();
        amount = in.readDouble();
        description = in.readString();
    }

    public static final Creator<Expense> CREATOR =
            new Creator<Expense>() {
                @Override
                public Expense createFromParcel(Parcel parcel) {
                    return new Expense(parcel);
                }

                @Override
                public Expense[] newArray(int i) {
                    return new Expense[i];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date != null ?
                Constants.simpleDateFormat.format(date) : null);
        parcel.writeString(category);
        parcel.writeDouble(amount);
        parcel.writeString(description);
    }
}
