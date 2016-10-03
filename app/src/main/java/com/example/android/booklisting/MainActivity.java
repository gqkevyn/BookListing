package com.example.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String GB_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private BookAdapter mAdapter;
    Button mSearch;
    ArrayList<Book> books;

    /**
     * Save the value of the bookArrayList variable
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("books", books);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = (Button)findViewById(R.id.search);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        if(savedInstanceState == null || !savedInstanceState.containsKey("books")) {
            books = new ArrayList<>();
        }
        else {
            books = savedInstanceState.getParcelableArrayList("books");
        }

        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText bookSearch = (EditText)findViewById(R.id.search_field);
                String searchText = bookSearch.getText().toString().replaceAll(" ","+");
                if (searchText.isEmpty()){
                    Toast.makeText(MainActivity.this, "Nothing to search", Toast.LENGTH_SHORT).show();
                }
                String searchUrl = GB_REQUEST_URL + searchText + "&maxResults=15";
                BookAsyncTask task = new BookAsyncTask();

                if (isNetworkAvailable(MainActivity.this)){
                    task.execute(searchUrl);
                } else{
                    Toast.makeText(MainActivity.this, "No internet Connection!", Toast.LENGTH_LONG).show();
                }

            }
        });

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                Book currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        BookAsyncTask task = new BookAsyncTask();
        task.execute(GB_REQUEST_URL);

    }

    /**
     * Returns true if network is available or about to become available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>>{
        @Override
        protected List<Book> doInBackground(String... urls){
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> results = QueryUtils.fetchBookData(urls[0]);
            return results;
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            mAdapter.clear();

            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
