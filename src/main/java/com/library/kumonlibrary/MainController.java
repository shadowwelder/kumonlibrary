/*
Jafar Hashim
AP CS-A | Cloud Computing
April 27 2023
 */

package com.library.kumonlibrary;

import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import javax.xml.catalog.CatalogFeatures;

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

    private String currentStudent;

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
    private TableColumn<BookClub, String> colTotalQuantity;

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

    FileChooser fileChooser = new FileChooser();
    String fileOutput = "";

    //public String selectedUser;
    Stage stage1 = new Stage();

    public MainController() throws SQLException {
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        //MainController mainController = new MainController();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(main.class.getResourceAsStream("fish.png")));
        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.contains("mac")) {

            Taskbar taskbar = Taskbar.getTaskbar();
            URL file = main.class.getResource("fish.png");
            BufferedImage image = ImageIO.read(file);
            taskbar.setIconImage(image);

        }
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage1 = stage;
    }

    @FXML
    public void initialize() throws SQLException {
        onSetStuff();
        onViewUsers();
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
    public void onViewUsers() {
        ObservableList<String> users = FXCollections.observableArrayList(this.databaseManager.userList());
        userListView.setItems(users);
    }

    @FXML
    public void onSelectUser() throws SQLException {
        onReturnHome();
        String selectedItem = userListView.getSelectionModel().getSelectedItem();
        //System.out.println(selectedItem);
        StringBuilder sb = new StringBuilder();
        List<String> userData = this.databaseManager.userSelected(selectedItem);
        sb.append(userData.get(0));
        String sba = sb.toString();
        welcomeLabel.setText("Selected: " + sba);
        sb.delete(0, 30);
        detailBooksCheckedOut.setText(userData.get(1));
        currentStudent = userData.get(0);
        onBrowse();
    }


    @FXML
    public void onCompleteSignUp() throws SQLException {

        String newUserName = signUpFirstName.getText();

        databaseManager.newUserCreation(newUserName);
        onReturnHome();
        onViewUsers();


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
        //System.out.println(updateLevel);

        Notifications.create()
                .title("Edits Made")
                .showInformation();

        databaseManager.updateUserInfo(updateName, updateAddress, updatePhone, updateEmail, updateLevel);
    }

    @FXML
    public void onDelete() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this student?");
        //Optional<ButtonType> result = alert.showAndWait();
        if (alert.showAndWait().get() == ButtonType.OK) {
            databaseManager.deleteUser(currentStudent);
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
    private void onViewCheckedOut() {
        tableStackPane.getChildren().clear();
        tableStackPane.getChildren().add(checkedOutBooks);

        ObservableList<String> checkedOutBooksObservable = FXCollections.observableArrayList(this.databaseManager.getCheckedOutBooks(currentStudent));
        checkedOutBookList.setItems(checkedOutBooksObservable);

        ObservableList<String> checkedOutDatesObservable = FXCollections.observableArrayList(this.databaseManager.getCheckedOutDates(currentStudent));
        checkedOutDateList.setItems(checkedOutDatesObservable);
    }

    @FXML
    private void onReturnBook() throws SQLException {
        String selectedItem = checkedOutBookList.getSelectionModel().getSelectedItem();
        String thing1 = databaseManager.getCheckedOutBooks(currentStudent).get(0);
        String thing2 = databaseManager.getCheckedOutBooks(currentStudent).get(1);
        String thing3 = databaseManager.getCheckedOutBooks(currentStudent).get(2);

        if (Objects.equals(selectedItem, thing1)) {
            databaseManager.returnBook("Book1", "DateCheckedOutBook1", currentStudent, selectedItem);
        }
        if (Objects.equals(selectedItem, thing2)) {
            databaseManager.returnBook("Book2", "DateCheckedOutBook2", currentStudent, selectedItem);
        }
        if (Objects.equals(selectedItem, thing3)) {
            databaseManager.returnBook("Book3", "DateCheckedOutBook3", currentStudent, selectedItem);
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
        colTotalQuantity.setCellValueFactory(new PropertyValueFactory<BookClub, String>("totalAmt"));
        colAmountAvailable.setCellValueFactory(new PropertyValueFactory<BookClub, Integer>("amtAvailable"));
        colLevel.setCellValueFactory(new PropertyValueFactory<BookClub, String>("level"));
        books.setItems(bookList);
    }

    @FXML
    private void onSearchUsers() throws SQLException {
        String searchKeyword = search.getText();
        ObservableList<String> users = FXCollections.observableArrayList(this.databaseManager.searchUser(searchKeyword));
        userListView.setItems(users);
    }

    @FXML
    private void onSearchBook() throws SQLException {
        String searchKeyword = searchBooks.getText();
        ObservableList<BookClub> bookList = databaseManager.searchBooks(searchKeyword);
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        //colAuthorName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("authorName"));
        colAmountAvailable.setCellValueFactory(new PropertyValueFactory<BookClub, Integer>("amtAvailable"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        books.setItems(bookList);
    }

    @FXML
    private void onCheckOut() throws SQLException, IOException {
        //String bco = userData.get(6);
        //System.out.println(detailBooksCheckedOut.getText());
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        String dateFr = dtf.format(localDate);

        String selectedBook = books.getSelectionModel().getSelectedItem().getBookName();
        String wBook;
        String wDateX;
        if (books.getSelectionModel().getSelectedItem().getAmtAvailable() < 0) {
            return;
        }
        if (detailBooksCheckedOut.getText().equals("3")) {
            return;
        }
        if (detailBooksCheckedOut.getText().equals("2")) {
            wBook = "Book3";
            wDateX = "DateCheckedOutBook3";
            databaseManager.checkOutBook(selectedBook, currentStudent, wBook, wDateX, dateFr);
        }
        if (detailBooksCheckedOut.getText().equals("1")) {
            wBook = "Book2";
            wDateX = "DateCheckedOutBook2";
            databaseManager.checkOutBook(selectedBook, currentStudent, wBook, wDateX, dateFr);
        }
        if (detailBooksCheckedOut.getText().equals("0")) {
            wBook = "Book1";
            wDateX = "DateCheckedOutBook1";
            databaseManager.checkOutBook(selectedBook, currentStudent, wBook, wDateX, dateFr);
        }
//        Notifications.create()
//                .title("Checked Out: " + selectedBook)
//                .showInformation();
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
        //detailLevel.setItems(readingLevels);
        onBrowse();
    }

    @FXML
    private void onLevel() throws SQLException {
        String chosenLevel = (String) levels.getValue();
        //System.out.println(chosenLevel);
        //databaseManager.onLevelSelected(chosenLevel);
        ObservableList<BookClub> bookList = databaseManager.onLevelSelected(chosenLevel);
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        //colAuthorName.setCellValueFactory(new PropertyValueFactory<BookClub, String>("authorName"));
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
        databaseManager.onAddBook(addBookName.getText(), (String) addBookLevel.getValue(), addBookQuantity.getText());
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
            //System.out.println(inventory.getName() + inventory.getCheckedOutBy() + inventory.getDateCheckedOut());

            writeIntoCell(detailsRow, inventory.getName(), cellNum++);
            writeIntoCell(detailsRow, inventory.getCheckedOutBy(), cellNum++);
            writeIntoCell(detailsRow, inventory.getDateCheckedOut(), cellNum++);
        }


        try
        {
            // get the file selected
            fileChooser.setInitialFileName("Inventory");
            File file = fileChooser.showSaveDialog(stage1);

            if (file != null) {

                fileOutput = file.getAbsolutePath();
                System.out.println(fileOutput);
            }
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(fileOutput+"-" + LocalDate.now() +".xlsx");
            workbook.write(out);
            out.close();
            //System.out.println("CheckedOutInventory.xlsx written successfully on disk.");
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