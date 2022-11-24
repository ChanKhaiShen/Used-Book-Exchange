package com.example.usedbookexchange;

import androidx.fragment.app.Fragment;

public class SavedBooksActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new SavedBooksFragment();
    }
}
