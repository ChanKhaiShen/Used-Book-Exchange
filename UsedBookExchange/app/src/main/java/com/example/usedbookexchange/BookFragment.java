package com.example.usedbookexchange;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usedbookexchange.databinding.FragmentBookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookFragment extends Fragment {
    private static final String TAG = "BookFragment";
    private static final String ARG_BOOK_ID = "bookId";
    private Book mBook;
    private FragmentBookBinding bookBinding;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private FeedReaderDbHelper mDbHelper;

    public static BookFragment newInstance(UUID bookId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_ID, bookId);

        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getArguments().getSerializable(ARG_BOOK_ID);
        BookLab bookLab = BookLab.getBookLab(getActivity());
        mBook = bookLab.getBook(bookId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookBinding = FragmentBookBinding.inflate(getLayoutInflater());
        return bookBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookBinding.bookName.setText(mBook.getName());
        bookBinding.author.setText(mBook.getAuthor());
        bookBinding.genre.setText(mBook.getGenre());
        bookBinding.summary.setText(mBook.getSummary());
        bookBinding.condition.setText(mBook.getCondition());
        bookBinding.ownerName.setText(mBook.getOwnerName());
        bookBinding.yearOfBuying.setText(String.format("%d", mBook.getYearOfBuying()));
        bookBinding.yearOfPrinting.setText(String.format("%d", mBook.getYearOfPrinting()));
        bookBinding.buyingPrice.setText(String.format("%.2f", mBook.getBuyingPrice()));
        bookBinding.price.setText(String.format("%.2f", mBook.getPrice()));

        bookBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

    }

    private void saveBook() {
        if (mDbHelper == null)
            mDbHelper = new FeedReaderDbHelper(getContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_KEY, mBook.getKey());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, mBook.getName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_OWNERNAME, mBook.getOwnerName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_OWNERUID, mBook.getOwnerUid());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AUTHOR, mBook.getAuthor());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_GENRE, mBook.getGenre());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_YEAROFPRINTING, mBook.getYearOfPrinting());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONDITION, mBook.getCondition());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_YEAROFBUYING, mBook.getYearOfBuying());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BUYINGPRICE, mBook.getBuyingPrice());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRICE, mBook.getPrice());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUMMARY, mBook.getSummary());

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Log.d(TAG, "saveBook: save failed");
            Toast.makeText(getActivity(), "Save failed.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "saveBook: save success");
            Toast.makeText(getActivity(), "Saved.", Toast.LENGTH_SHORT).show();
        }
    }

}