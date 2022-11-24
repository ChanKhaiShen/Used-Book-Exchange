package com.example.usedbookexchange;

import androidx.fragment.app.Fragment;

public class FindBookActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new FindBookFragment();
    }
}
