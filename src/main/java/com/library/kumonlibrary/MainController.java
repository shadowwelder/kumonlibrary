/*
Jafar Hashim
CS50 AP
May 18 2022
 */

package com.library.kumonlibrary;

import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;


import org.apache.poi.xssf.usermodel.*;

public class MainController extends Application {

    @FXML
    private VBox userViewVbox;

    @FXML
    private VBox signUpVbox;

    // @FXML
    //private Material randoButton;

    @FXML
    private Button signUpButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextArea addressUpdate;

    @FXML
    private MenuItem connectDB;

    @FXML
    private Text welcomeLabel;

    @FXML
    private Label detailName;

    @FXML
    private Label detailMemberSince;

    @FXML
    private Label detailDOB;

    @FXML
    private TextField detailAddress;

    @FXML
    private TextField detailEmail;

    @FXML
    private TextField detailPhoneNumber;

    @FXML
    private Text detailBooksCheckedOut;

    @FXML
    private JFXComboBox detailLevel;

    @FXML
    private Label detailID;

    @FXML
    private JFXListView<String> userListView;

    @FXML
    private MenuItem testSelect;

    @FXML
    private JFXButton selectUser;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField brandNewSignUpDOB;

    @FXML
    private TextField signUpAddress;

    @FXML
    private TextField signUpPhone;

    @FXML
    private TextField signUpEmail;

    @FXML
    private JFXComboBox signUpLevel = new JFXComboBox<>();

    @FXML
    private TextField signUpID;

    @FXML
    private TableView<BookClub> books;

    @FXML
    private TableColumn<BookClub, String> colBookName;

    @FXML
    private TableColumn<BookClub, String> colAuthorName;

    @FXML
    private TableColumn<BookClub, Integer> colAmountAvailable;

    @FXML
    private TableColumn<BookClub, String> colLevel;

    @FXML
    private StackPane tableStackPane;

    @FXML
    private VBox checkedOutBooks;

    @FXML
    private JFXListView<String> checkedOutBookList;

    @FXML
    private JFXListView<String> checkedOutDateList;

    @FXML
    private JFXButton viewCheckedOut;

    @FXML
    private TextField search;

    @FXML
    private TextField searchBooks;

    @FXML
    private JFXButton searchBooksButton;

    @FXML
    private MenuItem addBookMenu;

    @FXML
    private VBox addBook;

    @FXML
    private TextField addBookName;

    @FXML
    private TextField addBookAuthor;

    @FXML
    private JFXComboBox addBookLevel;

    @FXML
    private TextField addBookQuantity;

    @FXML
    private TextField addBookISBN;

    @FXML
    private TextField editBookTextField;


    private DatabaseManager databaseManager = new DatabaseManager();

    String updateName;

    //public String selectedUser;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        //MainController mainController = new MainController();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("file:fish.png"));
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            initialize(event);
        });
    }

    public void initialize(KeyEvent event){


            long now = Instant.now().toEpochMilli();

            // events must come fast enough to separate from manual input
            if (now - this.lastEventTimeStamp > this.threshold) {
                barcode.delete(0, barcode.length());
            }
            this.lastEventTimeStamp = now;

            if (event.getCode() == KeyCode.ENTER) {
                if (barcode.length() == 10 || barcode.length() == 13) {
                    System.out.println("barcode: " + barcode);
                    System.out.println(detailAddress.getText());
                }
                if (barcode.length() >= 15) {
                    System.out.println("Student ID: " + barcode);
                }
                barcode.delete(0, barcode.length());
            } else {
                barcode.append(event.getText());
            }
            event.consume();
            System.out.println("Key pressed" + event.getCode());
            System.out.println(detailAddress.getText());

    }

    @FXML
    private void onNewUser() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(signUpVbox);
    }

    @FXML
    private void onReturnHome() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(userViewVbox);
    }

    @FXML
    public void onViewUsers() throws IOException {
        ObservableList<String> users = FXCollections.observableArrayList(this.databaseManager.userList());
        userListView.setItems(users);
    }

    @FXML
    public void onSelectUser() throws IOException, SQLException {
        onReturnHome();
        String selectedItem = userListView.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        StringBuilder sb = new StringBuilder();
        List<String> userData = this.databaseManager.userSelected(selectedItem);
        sb.append(userData.get(0));
        String sba = sb.toString();
        welcomeLabel.setText("Welcome " + sba);
        sb.delete(0, 30);
        System.out.println(sb);
        sb.append(userData.get(0));
        detailName.setText(String.valueOf(sb));
        updateName = (String.valueOf(sb));
        sb.delete(0, 30);
        detailMemberSince.setText(String.valueOf(sb.append("Member Since: ").append(userData.get(1))));
        sb.delete(0, 30);
        detailDOB.setText(String.valueOf(sb.append("Date of Birth:  ").append(userData.get(2))));
        sb.delete(0, 30);
        detailAddress.setText(String.valueOf(sb.append(userData.get(3))));
        sb.delete(0, 30);
        detailEmail.setText(String.valueOf(sb.append(userData.get(5))));
        sb.delete(0, 30);
        detailPhoneNumber.setText(String.valueOf(sb.append(userData.get(4))));
        sb.delete(0, 30);
        detailBooksCheckedOut.setText(String.valueOf(sb.append(userData.get(8))));
        sb.delete(0, 30);
        detailID.setText(String.valueOf(sb.append("Student ID:").append(userData.get(6))));

        detailLevel.setValue(String.valueOf(userData.get(7)));
        onBrowse();
    }


    @FXML
    public void onCompleteSignUp() throws SQLException, IOException {

        String newUserName = signUpFirstName.getText();
        String newSignUpDOB = brandNewSignUpDOB.getText();
        String newUserSince = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        String newUserAddress = signUpAddress.getText();
        String newUserPhone = signUpPhone.getText();
        String newUserEmail = signUpEmail.getText();
        String newUserLevel = (String) signUpLevel.getValue();
        String newUserID = signUpID.getText();

        databaseManager.newUserCreation(newUserName, newUserSince, newSignUpDOB, newUserAddress, newUserPhone, newUserEmail, newUserID, newUserLevel);
        onReturnHome();
        onViewUsers();

        signUpFirstName.setText("");
        brandNewSignUpDOB.setText("");
        signUpAddress.setText("");
        signUpPhone.setText("");
        signUpEmail.setText("");

        Notifications.create()
                .title("Account Created")
                .showInformation();

    }

    @FXML
    public void onConfirm() {

        String updateAddress = detailAddress.getText();
        String updateEmail = detailEmail.getText();
        String updatePhone = detailPhoneNumber.getText();
        String updateLevel = (String) detailLevel.getValue();
        System.out.println(updateLevel);

        Notifications.create()
                .title("Edits Made")
                .showInformation();

        databaseManager.updateUserInfo(updateName, updateAddress, updatePhone, updateEmail, updateLevel);
    }

    @FXML
    public void onDelete() throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete your account?");
        //Optional<ButtonType> result = alert.showAndWait();
        if (alert.showAndWait().get() == ButtonType.OK) {
            databaseManager.deleteUser(updateName);
            databaseManager.connectDB();
            Notifications.create()
                    .title("Account Deleted :(")
                    .showInformation();
            onReturnHome();
            onViewUsers();
        } else {
            return;
        }
    }

    @FXML
    private void onViewCheckedOut() throws SQLException {
        tableStackPane.getChildren().clear();
        tableStackPane.getChildren().add(checkedOutBooks);

        ObservableList<String> checkedOutBooksObservable = FXCollections.observableArrayList(this.databaseManager.getCheckedOutBooks(detailName.getText()));
        checkedOutBookList.setItems(checkedOutBooksObservable);

        ObservableList<String> checkedOutDatesObservable = FXCollections.observableArrayList(this.databaseManager.getCheckedOutDates(detailName.getText()));
        checkedOutDateList.setItems(checkedOutDatesObservable);
    }

    @FXML
    private void onReturnBook() throws SQLException, IOException {
        String selectedItem = checkedOutBookList.getSelectionModel().getSelectedItem();
        String thing1 = databaseManager.getCheckedOutBooks(detailName.getText()).get(0);
        String thing2 = databaseManager.getCheckedOutBooks(detailName.getText()).get(1);
        String thing3 = databaseManager.getCheckedOutBooks(detailName.getText()).get(2);

        if (Objects.equals(selectedItem, thing1)) {
            databaseManager.returnBook("Book1", "DateCheckedOutBook1", detailName.getText(), selectedItem);
        }
        if (Objects.equals(selectedItem, thing2)) {
            databaseManager.returnBook("Book2", "DateCheckedOutBook2", detailName.getText(), selectedItem);
        }
        if (Objects.equals(selectedItem, thing3)) {
            databaseManager.returnBook("Book3", "DateCheckedOutBook3", detailName.getText(), selectedItem);
        }
        Notifications.create()
                .title("Book Returned!")
                .showInformation();

        onViewCheckedOut();
        onSelectUser();
    }


    @FXML
    private void onBrowse() throws SQLException {
        tableStackPane.getChildren().clear();
        tableStackPane.getChildren().add(books);

        ObservableList<BookClub> bookList = databaseManager.getBooks();
        colBookName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("bookName"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("authorName"));
        colAmountAvailable.setCellValueFactory(new PropertyValueFactory<BookClub, Integer>("amtAvailable"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        books.setItems(bookList);
    }

    @FXML
    private void onSearchUsers() throws SQLException {
        String searchKeyword = search.getText();
        //databaseManager.searchUser(searchKeyword);
        ObservableList<String> users = FXCollections.observableArrayList(this.databaseManager.searchUser(searchKeyword));
        userListView.setItems(users);
    }

    @FXML
    private void onSearchBook() throws SQLException {
        String searchKeyword = searchBooks.getText();
        ObservableList<BookClub> bookList = databaseManager.searchBooks(searchKeyword);
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("authorName"));
        colAmountAvailable.setCellValueFactory(new PropertyValueFactory<BookClub, Integer>("amtAvailable"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        books.setItems(bookList);
    }

    @FXML
    private void onCheckOut() throws SQLException, IOException {
        //String bco = userData.get(6);
        System.out.println(detailBooksCheckedOut.getText());
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        String dateFr = dtf.format(localDate);

        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();
        String wBook;
        String wDateX;
        if (books.getSelectionModel().getSelectedItem().getAmtAvailable() <= 0) {
            return;
        }
        if (detailBooksCheckedOut.getText().equals("3")) {
            return;
        }
        if (detailBooksCheckedOut.getText().equals("2")) {
            wBook = "Book3";
            wDateX = "DateCheckedOutBook3";
            databaseManager.checkOutBook(selectedBook, detailName.getText(), wBook, wDateX, dateFr);
        }
        if (detailBooksCheckedOut.getText().equals("1")) {
            wBook = "Book2";
            wDateX = "DateCheckedOutBook2";
            databaseManager.checkOutBook(selectedBook, detailName.getText(), wBook, wDateX, dateFr);
        }
        if (detailBooksCheckedOut.getText().equals("0")) {
            wBook = "Book1";
            wDateX = "DateCheckedOutBook1";
            databaseManager.checkOutBook(selectedBook, detailName.getText(), wBook, wDateX, dateFr);
        }
        Notifications.create()
                .title("Checked Out: " + selectedBook)
                .showInformation();
        onSelectUser();
        onBrowse();
    }

    @FXML
    public void onExit(javafx.event.ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Press Ok to exit");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    private JFXComboBox levels = new JFXComboBox();

    @FXML
    public void onSetStuff() throws SQLException {
        //ObservableList levelss = FXCollections.observableArrayList("7A", "6A", "5A");
        ObservableList readingLevels = FXCollections.observableArrayList("7A", "6A", "5A", "4A", "3A", "2A", "AI", "AII", "BI", "BII", "CI", "CII", "DI", "DII", "EI", "EII", "FI", "FII", "G", "H", "I", "J", "K", "L");

        levels.setItems(readingLevels);
        addBookLevel.setItems(readingLevels);
        signUpLevel.setItems(readingLevels);
        detailLevel.setItems(readingLevels);
        onBrowse();
    }

    @FXML
    private void onLevel() throws SQLException {
        String chosenLevel = (String) levels.getValue();
        System.out.println(chosenLevel);
        //databaseManager.onLevelSelected(chosenLevel);
        ObservableList<BookClub> bookList = databaseManager.onLevelSelected(chosenLevel);
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("authorName"));
        colAmountAvailable.setCellValueFactory(new PropertyValueFactory<BookClub, Integer>("amtAvailable"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        books.setItems(bookList);
    }

    @FXML
    private void onAddBookMenu() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(addBook);
    }

    @FXML
    private void onAddBooksButton() throws SQLException {
        databaseManager.onAddBook(addBookName.getText(), addBookAuthor.getText(), (String) addBookLevel.getValue(), addBookQuantity.getText(), addBookISBN.getText());
        Notifications.create()
                .title("Book Added: " + addBookName.getText())
                .showInformation();
    }

    @FXML
    private void onIncrease() throws SQLException {
        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();
        int selectedBookQuantity = books.getSelectionModel().getSelectedItem().getAmtAvailable();
        databaseManager.onIncrease(selectedBook);
        onBrowse();
        Notifications.create()
                .title("Increased Quantity of " + selectedBook + " to " + (selectedBookQuantity + 1))
                .showInformation();
    }

    @FXML
    private void onDecrease() throws SQLException {
        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();
        int selectedBookQuantity = books.getSelectionModel().getSelectedItem().getAmtAvailable();
        databaseManager.onDecrease(selectedBook);
        onBrowse();
        Notifications.create()
                .title("Decreased Quantity of " + selectedBook + " to " + (selectedBookQuantity - 1))
                .showInformation();
    }

    @FXML
    private void onEditBookName() throws SQLException {
        String editBookName = editBookTextField.getText();
        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();
        databaseManager.onEditName(editBookName, selectedBook);
        onBrowse();
        Notifications.create()
                .title(selectedBook + "changed to " + editBookName)
                .showInformation();
        editBookTextField.clear();
    }

    @FXML
    private void onEditAuthorName() throws SQLException {
        String editAuthorName = editBookTextField.getText();
        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();
        databaseManager.onEditAuthor(editAuthorName, selectedBook);
        onBrowse();
        Notifications.create()
                .title(selectedBook + "changed to ")
                .showInformation();
        editBookTextField.clear();
    }

    @FXML
    private void onDeleteBook() throws SQLException {
        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText("Are you sure you want to delete this book?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            databaseManager.onDeleteBook(selectedBook);
            Notifications.create()
                    .title("Deleted " + selectedBook)
                    .showInformation();
            onBrowse();
        }
    }

    final StringBuffer barcode = new StringBuffer();
    long lastEventTimeStamp = 0L;
    long threshold = 100;
    final int minBarcodeLength = 8;

//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//        Scene scene = this.welcomeLabel.getScene();
//        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
//            long now = Instant.now().toEpochMilli();
//
//            // events must come fast enough to separate from manual input
//            if (now - this.lastEventTimeStamp > this.threshold) {
//                barcode.delete(0, barcode.length());
//            }
//            this.lastEventTimeStamp = now;
//
//            if (event.getCode() == KeyCode.ENTER) {
//                if (barcode.length() == 10 || barcode.length() == 13) {
//                    System.out.println("barcode: " + barcode);
//                    System.out.println(detailAddress.getText());
//                }
//                if (barcode.length() >= 15) {
//                    System.out.println("Student ID: " + barcode);
//                }
//                barcode.delete(0, barcode.length());
//            } else {
//                barcode.append(event.getText());
//            }
//            event.consume();
//            System.out.println("Key pressed" + event.getCode());
//            System.out.println(detailAddress.getText());
//        });
//    }

    @FXML
    private void onSearchBook1() throws SQLException {
        String searchKeyword = searchBooks.getText();
        ObservableList<BookClub> bookList = databaseManager.searchBooks(searchKeyword);
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("authorName"));
        colAmountAvailable.setCellValueFactory(new PropertyValueFactory<BookClub, Integer>("amtAvailable"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        books.setItems(bookList);
    }

    @FXML
    private void onPrintCheckedOut() throws SQLException{
//Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet( "Checked Out Inventory Info");

        //Create row object
        XSSFRow row = spreadsheet.createRow(0);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Book Name");

        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("Checked Out By");

        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("Date Checked Out");

        //databaseManager.onPrintCheckedOut();
        // Get Data and Write into the Excel
        int rowNum = 1;
        ArrayList<Inventory> checkedOutBooks = databaseManager.onPrintCheckedOut();
        for (Inventory inventory : checkedOutBooks) {
            XSSFRow detailsRow = spreadsheet.createRow(rowNum++);
            int cellNum = 0;
            System.out.println(inventory.getName() + inventory.getCheckedOutBy() + inventory.getDateCheckedOut());

            writeIntoCell(detailsRow, inventory.getName(), cellNum++);
            writeIntoCell(detailsRow, inventory.getCheckedOutBy(), cellNum++);
            writeIntoCell(detailsRow, inventory.getDateCheckedOut(), cellNum++);
        }


        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream("CheckedOutInventory.xlsx");
            workbook.write(out);
            out.close();
            System.out.println("CheckedOutInventory.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    private void writeIntoCell(XSSFRow row, Object value, int cellNum) {
        XSSFCell cell = row.createCell(cellNum);

        if (value instanceof String) {
            //cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue((String) value);
        } else if (value instanceof Long) {
            //cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            //cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Integer) value);
        }

    }
}