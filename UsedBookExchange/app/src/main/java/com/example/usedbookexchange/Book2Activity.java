package com.example.usedbookexchange;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class Book2Activity extends SingleFragmentActivity {
    private static final String EXTRA_BOOK_ID = "bookId";

    @Override
    protected Fragment createFragment() {
        UUID bookId = (UUID) getIntent().getSerializableExtra(EXTRA_BOOK_ID);
        return BookFragment2.newInstance(bookId);
    }

    public static Intent newIntent(Context packageContext, UUID bookId){
        Intent intent = new Intent(packageContext, Book2Activity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        return intent;
    }
}
