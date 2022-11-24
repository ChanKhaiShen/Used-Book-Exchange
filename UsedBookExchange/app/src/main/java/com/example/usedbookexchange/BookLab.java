package com.example.usedbookexchange;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookLab {
    private static BookLab sBookLab;
    private ArrayList<Book> mBooks;

    public static BookLab getBookLab(Context context){
        if (sBookLab == null)
            sBookLab = new BookLab(context);
        return sBookLab;
    }

    private BookLab(Context context){
        mBooks = new ArrayList<Book>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("books").get().
                addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", "success");
                            for (DataSnapshot snapshot: task.getResult().getChildren()){
                                Book book = new Book();
                                for (DataSnapshot snapshot1: snapshot.getChildren()){
                                    if (snapshot1.getKey().equals("name"))
                                        book.setName(snapshot1.getValue().toString());
                                    if (snapshot1.getKey().equals("ownerUid"))
                                        book.setOwnerUid(snapshot1.getValue().toString());
                                    if (snapshot1.getKey().equals("ownerName"))
                                        book.setOwnerName(snapshot1.getValue().toString());
                                    if (snapshot1.getKey().equals("genre"))
                                        book.setGenre(snapshot1.getValue().toString());
                                    if (snapshot1.getKey().equals("summary"))
                                        book.setSummary(snapshot1.getValue().toString());
                                    if (snapshot1.getKey().equals("yearOfPrinting"))
                                        book.setYearOfPrinting((long)snapshot1.getValue());
                                    if (snapshot1.getKey().equals("yearOfBuying"))
                                        book.setYearOfBuying((long)snapshot1.getValue());
                                    if (snapshot1.getKey().equals("condition"))
                                        book.setCondition(snapshot1.getValue().toString());
                                    if (snapshot1.getKey().equals("buyingPrice"))
                                        book.setBuyingPrice((long)snapshot1.getValue());
                                    if (snapshot1.getKey().equals("price"))
                                        book.setPrice((long)snapshot1.getValue());
                                }
                                mBooks.add(book);
                            }

                        }
                    }
                });
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
