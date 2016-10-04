package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevinlu on 10/2/16.
 */

public class Book {

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
}
