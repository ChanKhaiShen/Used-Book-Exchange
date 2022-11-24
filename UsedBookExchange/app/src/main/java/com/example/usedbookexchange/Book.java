package com.example.usedbookexchange;

import java.util.UUID;

public class Book{
    private UUID mId;
    private String mKey;
    private String mOwnerUid;
    private String mOwnerName;
    private String mName;
    private String mAuthor;
    private long mYearOfPrinting;
    private long mYearOfBuying;
    private double mBuyingPrice;
    private String mCondition;
    private String mGenre;
    private double mPrice;
    private String mSummary;
    private long mNumberAvailable;
    private Boolean sold;

    public Boolean getSold() {
        return sold;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Book(String name, String ownerName, String ownerUid){
        mId = UUID.randomUUID();
        mName = name;
        mOwnerName = ownerName;
        mOwnerUid = ownerUid;
    }

    public Book(){
        mId = UUID.randomUUID();
    }

    public Book(Book book){
        mId = UUID.randomUUID();
        mName = book.getName();
        mOwnerUid = book.getOwnerUid();
        mOwnerName = book.getOwnerName();
        mAuthor = book.getAuthor();
        mBuyingPrice = book.getBuyingPrice();
        mCondition = book.getCondition();
        mGenre = book.getGenre();
        mNumberAvailable = book.getNumberAvailable();
        mPrice = book.getPrice();
        mSummary = book.getSummary();
        mYearOfBuying = book.getYearOfBuying();
        mYearOfPrinting = book.getYearOfPrinting();
        mKey = book.getKey();
        sold = book.getSold();
    }

    public void setOwnerUid(String ownerUid) {
        mOwnerUid = ownerUid;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public void setName(String name) {
        mName = name;
    }

    public UUID getId() {
        return mId;
    }

    public String getOwnerUid() {
        return mOwnerUid;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public int getYearOfPrinting() {
        return (int)mYearOfPrinting;
    }

    public void setYearOfPrinting(long yearOfPrinting) {
        mYearOfPrinting = yearOfPrinting;
    }

    public int getYearOfBuying() {
        return (int)mYearOfBuying;
    }

    public void setYearOfBuying(long yearOfBuying) {
        mYearOfBuying = yearOfBuying;
    }

    public double getBuyingPrice() {
        return mBuyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        mBuyingPrice = buyingPrice;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        mCondition = condition;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getNumberAvailable() {
        return (int)mNumberAvailable;
    }

    public void setNumberAvailable(int numberAvailable) {
        mNumberAvailable = numberAvailable;
    }
}
