package com.example.usedbookexchange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usedbookexchange.databinding.FragmentBook2Binding;

import java.util.UUID;

public class BookFragment2 extends Fragment {
    private static final String TAG = "BookFragment";
    private static final String ARG_BOOK_ID = "bookId";
    private Book mBook;
    private FragmentBook2Binding bookBinding;

    public static BookFragment2 newInstance(UUID bookId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_ID, bookId);

        BookFragment2 fragment = new BookFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getArguments().getSerializable(ARG_BOOK_ID);
        BookLab2 bookLab = BookLab2.getBookLab(getActivity());
        mBook = bookLab.getBook(bookId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookBinding = FragmentBook2Binding.inflate(getLayoutInflater());
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

    }
}
