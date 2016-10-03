package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevinlu on 10/2/16.
 */

public class Book implements Parcelable {

    private String mBookTitle;

    private String mBookAuthor;

    private String mUrl;

    public Book (String bookTitle, String bookAuthor, String url){
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mUrl = url;
    }

    public String getBookTitle(){
        return mBookTitle;
    }

    public String getBookAuthor(){
        return mBookAuthor;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }


    protected Book(Parcel in) {
        mBookTitle = in.readString();
        mBookAuthor = in.readString();
        mUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookTitle);
        dest.writeString(mBookAuthor);
        dest.writeString(mUrl);
    }
    
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
