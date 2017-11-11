package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller implements Initializable {

    private Connection conn;
    private Statement myStmt;
    ResultSet resultSet;
    private static int count = 1;

    @FXML
    Button addButton;
    @FXML
    TextField descriptionTextField;
    @FXML
    DatePicker datePicker;
    @FXML
    ListView<LocalEvent> eventList;

    ObservableList<LocalEvent> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setValue(LocalDate.now());

        try {
            conn = DBConnection.getConnection();
            myStmt = conn.createStatement();

            String sql = "select * from events";
            resultSet = myStmt.executeQuery(sql);
            while (resultSet.next()) {
                count++;
                Date date = resultSet.getDate(2);
                LocalDate ld = date.toLocalDate();
                String description = resultSet.getString(3);
                list.add(new LocalEvent(ld, description));

            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        eventList.setItems(list);
    }

    @FXML
    private void addEvent(Event e){

        LocalDate ld = datePicker.getValue();
        String description = descriptionTextField.getText();

        try {
            String sql = "INSERT INTO events (id, date, description) VALUES ('" + count +
                    "', '" + ld + "', '" + description + "')";
            myStmt.executeUpdate(sql);
            list.add(new LocalEvent(ld, description));
            count++;

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        eventList.setItems(list);
        refresh();
    }
    private void refresh() {
        datePicker.setValue(LocalDate.now());
        descriptionTextField.setText(null);
    }
}
