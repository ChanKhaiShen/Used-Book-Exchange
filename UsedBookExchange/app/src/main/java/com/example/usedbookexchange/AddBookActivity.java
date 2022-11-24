package com.example.usedbookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.usedbookexchange.databinding.ActivityAddBookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddBookActivity extends AppCompatActivity {
    private static final String TAG = "AddBookActivity";
    private ActivityAddBookBinding addBookBinding;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBookBinding = ActivityAddBookBinding.inflate(getLayoutInflater());
        View view = addBookBinding.getRoot();
        setContentView(view);

        addBookBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkResult = checkAllFields();
                if (checkResult.equals("Everything passed."))
                    submitBook();
                else
                    Toast.makeText(AddBookActivity.this, checkResult, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String checkAllFields(){
        String bookName = addBookBinding.bookName.getText().toString();
        if (bookName.equals(""))
            return "Book name must be filled.";

        String author = addBookBinding.author.getText().toString();
        if (author.equals(""))
            return "Author must be filled.";

        String yearOfPrintingString = addBookBinding.yearOfPrinting.getText().toString();
        try{
            int yearOfPrinting = Integer.parseInt(yearOfPrintingString);
        }
        catch (NumberFormatException nfe) {
            return "Year of printing must be integer.";
        }

        String genre = addBookBinding.genre.getText().toString();
        if (genre.equals(""))
            return "Genre must be filled.";

        String yearOfBuyingString = addBookBinding.yearOfBuying.getText().toString();
        if (yearOfBuyingString.equals(""))
            return "Year of buying must be filled.";
        try{
            int yearOfBuying = Integer.parseInt(yearOfBuyingString);
        }
        catch (NumberFormatException nfe) {
            return "Bought in (year) must be integer.";
        }

        String buyingPriceString = addBookBinding.buyingPrice.getText().toString();
        if (buyingPriceString.equals(""))
            return "Buying price must be filled.";
        try{
            double buyingPrice = Double.parseDouble(buyingPriceString);
            if (buyingPrice <= 0)
                return "Bought in price must be greater tha 0";
        }
        catch (NumberFormatException exception){
            return "Bought in price must be number.";
        }

        String condition = addBookBinding.condition.getText().toString();
        if (condition.equals(""))
            return "Current condition must be filled.";

        String priceString = addBookBinding.price.getText().toString();
        if (priceString.equals(""))
            return "Price must be filled.";
        try{
            double price = Double.parseDouble(priceString);
            if (price <= 0)
                return "Price must be greater than 0.";
        }
        catch (NumberFormatException exception){
            return "Price must be number.";
        }

        String numberAvailableString = addBookBinding.numberAvailable.getText().toString();
        if (numberAvailableString.equals(""))
            return "Number available must be filled";
        try{
            int numberAvailable = Integer.parseInt(numberAvailableString);
            if (numberAvailable < 1)
                return "Number available must be at least 1.";
        }
        catch (NumberFormatException exception) {
            return "Number available must be integer.";
        }

        return "Everything passed.";
    }

    private void submitBook(){

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            Log.d(TAG, "submitBook: user is null");
            Toast.makeText(AddBookActivity.this, "No user.", Toast.LENGTH_SHORT).show();
            return;
        }

        String ownerName = mUser.getDisplayName();
        String ownerUid = mUser.getUid();
        String bookName = addBookBinding.bookName.getText().toString();

        Book book = new Book();

        book.name = bookName;
        book.ownerName = ownerName;
        book.ownerUid = ownerUid;

        String author = addBookBinding.author.getText().toString();
        book.author = author;

        String genre = addBookBinding.genre.getText().toString();
        book.genre = genre;

        String summary = addBookBinding.summary.getText().toString();
        book.summary = summary;

        String condition = addBookBinding.condition.getText().toString();
        book.condition = condition;

        String yearOfPrintingString = addBookBinding.yearOfPrinting.getText().toString();
        int yearOfPrinting = Integer.parseInt(yearOfPrintingString);
        book.yearOfPrinting = yearOfPrinting;

        String yearOfBuyingString = addBookBinding.yearOfBuying.getText().toString();
        int yearOfBuying = Integer.parseInt(yearOfBuyingString);
        book.yearOfBuying = yearOfBuying;

        String numberAvailableString = addBookBinding.numberAvailable.getText().toString();
        int numberAvailable = Integer.parseInt(numberAvailableString);
        book.numberAvailable = numberAvailable;

        String buyingPriceString = addBookBinding.buyingPrice.getText().toString();
        double buyingPrice = Double.parseDouble(buyingPriceString);
        buyingPrice = Math.round(buyingPrice*100)/100;  // Round to 2 decimal places
        book.buyingPrice = buyingPrice;

        String priceString = addBookBinding.price.getText().toString();
        double price = Double.parseDouble(priceString);
        price = Math.round(price*100)/100;
        book.price = price;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String key = mDatabase.child("books").push().getKey();
        mDatabase.child("books").child(key).setValue(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: submit to books");

                        mDatabase.child("owners").child(ownerUid).child(key).setValue(book)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: submit to owners");
                                        Toast.makeText(AddBookActivity.this, "Submitted.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "onFailure: submit to owners", e);
                                        Toast.makeText(AddBookActivity.this, "Submission failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: submit to books", e);
                        Toast.makeText(AddBookActivity.this, "Submission failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class Book{
        public String ownerUid;
        public String ownerName;
        public String name;
        public String author;
        public int yearOfPrinting;
        public int yearOfBuying;
        public double buyingPrice;
        public String condition;
        public String genre;
        public double price;
        public int numberAvailable;
        public String summary;
        public Boolean sold;

        public Book(){
            sold = false;
        };
    }

}