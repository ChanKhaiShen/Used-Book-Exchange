package com.example.usedbookexchange;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedbookexchange.databinding.FragmentBookListBinding;
import com.example.usedbookexchange.databinding.ListItemBookBinding;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavedBooksFragment extends Fragment {
    private FragmentBookListBinding bookListBinding;
    private BookAdapter mBookAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookListBinding = FragmentBookListBinding.inflate(getLayoutInflater());
        return bookListBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookListBinding.title.setText(getTitle());
        bookListBinding.bookListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();
    }

    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class BookHolder extends RecyclerView.ViewHolder {
        private TextView mBookNameTextView;
        private TextView mBookGenreTextView;
        private Book mBook;

        public BookHolder(@NonNull ListItemBookBinding itemBinding) {
            super(itemBinding.getRoot());
            mBookNameTextView = (TextView) itemBinding.bookName;
            mBookGenreTextView = (TextView) itemBinding.bookGenre;

            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Book2Activity.newIntent(getActivity(), mBook.getId());
                    startActivity(intent);
                }
            });
        }

        public void bindBook(Book book) {
            mBook = book;
            mBookNameTextView.setText(book.getName());
            mBookGenreTextView.setText(book.getGenre());
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> mBooks;

        public BookAdapter(List<Book> books) {
            mBooks = books;
        }

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemBookBinding itemBinding = ListItemBookBinding.inflate(getLayoutInflater());
            return new BookHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.bindBook(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }

    protected String getTitle() {
        return getString(R.string.title_activity_saved_books);
    }


    protected void updateUI() {
        BookLab2 bookLab = BookLab2.getBookLab(getActivity());
        mBookAdapter = new BookAdapter(bookLab.getList());
        bookListBinding.bookListRecyclerView.setAdapter(mBookAdapter);
    }
}
