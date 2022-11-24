package com.example.usedbookexchange;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookLab2 {

        private static BookLab2 sBookLab;
        private ArrayList<Book> mBooks;

        public static BookLab2 getBookLab(Context context){
            if (sBookLab == null)
                sBookLab = new BookLab2(context);
            return sBookLab;
        }

        private BookLab2(Context context){
            mBooks = new ArrayList<Book>();
            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while(cursor.moveToNext()) {
                String bookName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME));
                String ownerName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_OWNERNAME));
                String ownerUid = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_OWNERUID));
                Book book = new Book(bookName, ownerName, ownerUid);

                String author = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AUTHOR));
                book.setAuthor(author);

                int yearOfPrinting = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_YEAROFPRINTING));
                book.setYearOfPrinting(yearOfPrinting);

                String genre = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_GENRE));
                book.setGenre(genre);

                String summary = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUMMARY));
                book.setSummary(summary);

                double buyingPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BUYINGPRICE));
                book.setBuyingPrice(buyingPrice);

                String condition = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CONDITION));
                book.setCondition(condition);

                int yearOfBuying = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_YEAROFBUYING));
                book.setYearOfBuying(yearOfBuying);

                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRICE));
                book.setPrice(price);

                String key = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_KEY));
                book.setKey(key);

                mBooks.add(book);
            }
            cursor.close();
        }

        public List<Book> getList(){
            return mBooks;
        }

        public void updateBooks(List<Book> books){
            mBooks = (ArrayList<Book>) books;
        }

        public Book getBook(UUID bookId){
            for (Book book : mBooks){
                if (book.getId().equals(bookId))
                    return book;
            }
            return null;
        }
    }

