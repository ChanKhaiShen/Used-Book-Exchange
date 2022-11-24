package com.example.usedbookexchange;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Book";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_GENRE = "genre";
        public static final String COLUMN_NAME_YEAROFPRINTING = "yearOfPrinting";
        public static final String COLUMN_NAME_YEAROFBUYING = "yearOfBuying";
        public static final String COLUMN_NAME_BUYINGPRICE = "buyingPrice";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_CONDITION = "condition";
        public static final String COLUMN_NAME_SUMMARY = "summary";
        public static final String COLUMN_NAME_KEY = "bookKey";
        public static final String COLUMN_NAME_OWNERNAME = "ownerName";
        public static final String COLUMN_NAME_OWNERUID = "ownerUid";
    }

}
