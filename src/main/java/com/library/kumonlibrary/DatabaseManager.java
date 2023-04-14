/*
Jafar Hashim
CS50 AP
May 18 2022
 */

package com.library.kumonlibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DatabaseManager {

    private final File databaseFile = new File("data/database.db");

    private Connection connection;

    public DatabaseManager() {
    }

    public Connection connectDB() {
        try {
            return getConnection("jdbc:mysql://192.168.1.169:3306/library", "jafar", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> userSelected(String selectedItem) {
        List<String> userData= new ArrayList<>();
        try {
            //System.out.println(selectedItem);
            PreparedStatement ps = connectDB().prepareStatement("SELECT * FROM users WHERE Name = ?");
            ps.setString(1, selectedItem);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userData.add(rs.getString("Name"));
                userData.add(rs.getString("BooksCheckedOut"));
                userData.add(rs.getString("Book1"));
                userData.add(rs.getString("DateCheckedOutBook1"));
                userData.add(rs.getString("Book2"));
                userData.add(rs.getString("DateCheckedOutBook2"));
                userData.add(rs.getString("Book3"));
                userData.add(rs.getString("DateCheckedOutBook3"));
            }
            //System.out.println(userData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

       return userData;
    }

    public List<String> userList() {
        List<String> users = new ArrayList<>();
        try{
            ResultSet rs = connectDB().prepareStatement("SELECT * FROM users;").executeQuery();

            while (rs.next()) {
                users.add(rs.getString("Name"));
            }
            System.out.println(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void newUserCreation(String newUserName) throws SQLException {
        try {
            PreparedStatement ps = connectDB().prepareStatement("INSERT INTO users (Name, BooksCheckedOut, Book1, DateCheckedOutBook1, Book2, DateCheckedOutBook2, Book3, DateCheckedOutBook3) VALUES (?, 0, ' ', ' ', ' ', ' ', ' ', ' ')");
            ps.setString(1, newUserName);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserInfo(String updateName, String updateAddress, String updatePhone, String updateEmail, String updateLevel) {
        try{
            PreparedStatement ps = connectDB().prepareStatement("Update users SET Address = (?), PhoneNumber = (?), Email = (?), level = (?) WHERE name = (?)");
            ps.setString(1, updateAddress);
            ps.setString(2, updatePhone);
            ps.setString(3, updateEmail);
            ps.setString(4, updateLevel);
            ps.setString(5, updateName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String updateName) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("DELETE FROM users WHERE Name = ?");
        ps.setString(1, updateName);
        ps.executeUpdate();
        //System.out.println(updateName);
    }

    public List<String> searchUser(String searchKeyword) throws SQLException {
        List<String> searchedUser = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connectDB().prepareStatement("SELECT * FROM users WHERE Name LIKE ? ");
            ps.setString(1, "%" + searchKeyword + "%");
            ResultSet rs = ps.executeQuery();
            //System.out.println(ps);

            while (rs.next()){
                searchedUser.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchedUser;
    }

    public ObservableList<BookClub> searchBooks(String searchKeyword) throws SQLException {
        ObservableList<BookClub> searchedBookList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connectDB().prepareStatement("SELECT * FROM books WHERE Name LIKE ? ");
            //PreparedStatement psa = connectDB().prepareStatement("SELECT * FROM books WHERE ISBN = ?");
            ps.setString(1, "%" + searchKeyword + "%");
            //psa.setString(1, searchKeyword);
            ResultSet rs = ps.executeQuery();
            //ResultSet rsa = psa.executeQuery();
            //System.out.println(psa);

            while (rs.next()){
                //searchedBookList.add(rs.getString("Name"));
                BookClub book = new BookClub(rs.getString("name"), rs.getInt("totalamount"), rs.getInt("amountavailable"), rs.getString("level"), rs.getString("CheckedOutBy"), rs.getString("DateCheckedOut"));
                searchedBookList.add(book);
            }

//            while (rsa.next()){
//                //searchedBookList.add(rs.getString("Name"));
//                BookClub book = new BookClub(rsa.getString("name"), rsa.getInt("totalamount"), rsa.getInt("amountavailable"), rsa.getString("level"), rsa.getString("CheckedOutBy"), rs.getString("DateCheckedOut"));
//                searchedBookList.add(book);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchedBookList;
    }

    public ObservableList<BookClub> getBooks() throws SQLException {
        ObservableList<BookClub> bookList = FXCollections.observableArrayList();
        PreparedStatement ps = connectDB().prepareStatement("SELECT * FROM books");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            BookClub book = new BookClub(rs.getString("Name"), rs.getInt("TotalAmount"), rs.getInt("AmountAvailable"), rs.getString("Level"), rs.getString("CheckedOutBy"), rs.getString("DateCheckedOut"));
            bookList.add(book);
        }
        //System.out.println(bookList);
        return bookList;
    }



    public List <String> getCheckedOutBooks(String name){
        List <String> checkedOutBooksList = new ArrayList<>();
        try{
            PreparedStatement ps = connectDB().prepareStatement("SELECT Book1, Book2, Book3 FROM users WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                checkedOutBooksList.add(rs.getString("Book1"));
                checkedOutBooksList.add(rs.getString("Book2"));
                checkedOutBooksList.add(rs.getString("Book3"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(checkedOutBooksList);
        return checkedOutBooksList;
    }

    public List <String> getCheckedOutDates(String name){
        List <String> checkedOutDatesList = new ArrayList<>();
        try{
            PreparedStatement ps = connectDB().prepareStatement("SELECT DateCheckedOutBook1, DateCheckedOutBook2, DateCheckedOutBook3 FROM users WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                checkedOutDatesList.add(rs.getString("DateCheckedOutBook1"));
                checkedOutDatesList.add(rs.getString("DateCheckedOutBook2"));
                checkedOutDatesList.add(rs.getString("DateCheckedOutBook3"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(checkedOutDatesList);
        return checkedOutDatesList;
    }

    public void returnBook(String book, String date, String name, String selectedItem) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("UPDATE users SET " + book + " = ' ' WHERE name = ?");
        PreparedStatement psa = connectDB().prepareStatement("UPDATE users SET " + date + "= ' ' WHERE name = ?");
        PreparedStatement psb = connectDB().prepareStatement("UPDATE users SET BooksCheckedOut = IF (BooksCheckedOut > 0, BooksCheckedOut-1, BooksCheckedOut) WHERE name = ?;");
        PreparedStatement psc = connectDB().prepareStatement("UPDATE books SET AmountAvailable = AmountAvailable+1 WHERE name = ? ");
        PreparedStatement psd = connectDB().prepareStatement("UPDATE books SET CheckedOutBy = REPLACE (CheckedOutBy, ? ' on '? , '') WHERE name = ?");

        ps.setString(1, name);
        psa.setString(1, name);
        psb.setString(1, name);
        psc.setString(1, selectedItem);
        psd.setString(1, name);
        psd.setString(2, date);
        psd.setString(3, selectedItem);
        ps.execute();
        psa.execute();
        psb.execute();
        psc.execute();
        psd.execute();


    }

    public void checkOutBook(String book, String name, String wBook, String wDateX, String wDate) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("UPDATE books SET AmountAvailable = AmountAvailable-1 WHERE name= ?");
        PreparedStatement psa = connectDB().prepareStatement("UPDATE users SET " + wBook + "= ? WHERE name = ?");
        PreparedStatement psb = connectDB().prepareStatement("UPDATE users SET BooksCheckedOut = BooksCheckedOut+1 WHERE name = ?");
        PreparedStatement psc = connectDB().prepareStatement("UPDATE users SET " + wDateX + " = ? WHERE name = ?");
        PreparedStatement psd = connectDB().prepareStatement("UPDATE books SET CheckedOutBy = CONCAT(CheckedOutBy, ? ' on "+wDate+" ') WHERE name = ?");
        PreparedStatement pse = connectDB().prepareStatement("Update books SET DateCheckedOut = ? WHERE name = ?");
        ps.setString(1, book);
        psa.setString(1, book);
        psa.setString(2, name);
        psb.setString(1, name);
        psc.setString(1, wDate);
        psc.setString(2, name);
        psd.setString(1, name);
        //psd.setString(2, wDate);
        psd.setString(2, book);
        pse.setString(1, wDate);
        pse.setString(2, book);
        ps.execute();
        psa.execute();
        psb.execute();
        psc.execute();
        psd.execute();
        pse.execute();
        //System.out.println("yay");
    }

    public ObservableList<BookClub> onLevelSelected(String chosenLevel) throws SQLException {
        ObservableList books = FXCollections.observableArrayList();
        PreparedStatement ps = connectDB().prepareStatement("SELECT * FROM books WHERE level = ?");
        ps.setString(1, chosenLevel);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            BookClub book = new BookClub(rs.getString("name"), rs.getInt("totalamount"), rs.getInt("amountavailable"), rs.getString("level"), rs.getString("CheckedOutBy"), rs.getString("DateCheckedOut"));
            books.add(book);
            //System.out.println(books);
        }
        return books;
    }

    public void onAddBook(String bookName, String level, String quantity) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("INSERT INTO books (Name, level, TotalAmount, AmountAvailable, CheckedOutBy) VALUES (?, ?, ?, ?, '')");
        ps.setString(1, bookName);
        ps.setString(2, level);
        ps.setString(3, quantity);
        ps.setString(4, quantity);
        //ps.setString(6, ISBN);
        ps.execute();
    }

    public void onIncrease(String selectedBookQuantity) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("UPDATE books SET TotalAmount = TotalAmount+1 WHERE name = ? ");
        PreparedStatement psa = connectDB().prepareStatement("UPDATE books SET AmountAvailable = AmountAvailable+1 WHERE name = ? ");

        ps.setString(1, selectedBookQuantity);
        psa.setString(1, selectedBookQuantity);

        ps.execute();
        psa.execute();
    }

    public void onDecrease(String selectedBookName) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("UPDATE books SET TotalAmount = TotalAmount-1 WHERE name = ? ");
        PreparedStatement psa = connectDB().prepareStatement("UPDATE books SET AmountAvailable = AmountAvailable-1 WHERE name = ? ");

        ps.setString(1, selectedBookName);
        psa.setString(1, selectedBookName);

        ps.execute();
        psa.execute();
    }

    public void onEditName(String editBookName, String selectedBook) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("UPDATE books SET Name = ? WHERE name = ?");
        ps.setString(1, editBookName);
        ps.setString(2, selectedBook);

        ps.executeUpdate();
    }

    public void onEditAuthor(String editAuthorName, String selectedBook) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("UPDATE books SET AuthorName = ? WHERE name = ?");
        ps.setString(1, editAuthorName);
        ps.setString(2, selectedBook);

        ps.executeUpdate();
    }

    public void onDeleteBook(String selectedBook) throws SQLException {
        PreparedStatement ps = connectDB().prepareStatement("DELETE FROM books WHERE name = ?");
        ps.setString(1, selectedBook);
        ps.executeUpdate();
    }

    public ArrayList<Inventory> onPrintCheckedOut() throws SQLException {
        ArrayList<Inventory> checkedOutBookInfo = new ArrayList<>();
        try {
            PreparedStatement ps = connectDB().prepareStatement("SELECT * FROM books WHERE CheckedOutBy != ''");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                checkedOutBookInfo.add(new Inventory(rs.getString("Name"), rs.getString("CheckedOutBy"), rs.getString("DateCheckedOut")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkedOutBookInfo;
    }

}