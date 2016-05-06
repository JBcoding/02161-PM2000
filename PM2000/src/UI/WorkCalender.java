import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by madsbjoern on 02/05/16.
 */
public class WorkCalender extends Stage {
    protected Scene scene;
    protected AnchorPane root;
    protected VBox vbox;
    protected DatePicker datePicker;
    protected ScrollPane calendar;
    protected User thisUser;

    public WorkCalender(User user) {
        thisUser = user;
        root = new AnchorPane();
        scene = new Scene(root, 450, 800);
        this.setMaxWidth(450);
        this.setMaxHeight(800);
        this.setMinWidth(450);
        this.setMinHeight(800);
        setTitle("Work Calendar");
        vbox = new VBox();
        root.getChildren().add(vbox);

        calendar = new ScrollPane();
        calendar.setPrefWidth(448);
        calendar.setPrefHeight(700);

        datePicker = new DatePicker();
        datePicker.setPrefWidth(225);
        datePicker.setShowWeekNumbers(true);
        datePicker.setEditable(false);
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateCalendar();
            }
        });

        vbox.getChildren().addAll(datePicker, calendar);

        setScene(scene);
        this.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                close();
            }
        });
        scene.getStylesheets().add("styleWork.css");


        updateCalendar();
    }

    public void updateCalendar() {
        AnchorPane anchorPane = new AnchorPane();
        Date date = Date.from(Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
        for (int i = 0; i < 96; i ++) {
            String mL = "" + ((i * 15) % 60);
            String mH = "" + (i * 15 + 15) % 60;
            String hL = "" + (i / 4);
            String hH = "" + ((i + 1) / 4);
            if (mL.length() == 1) {mL = "0" + mL;}
            if (mH.length() == 1) {mH = "0" + mH;}
            if (hL.length() == 1) {hL = "0" + hL;}
            if (hH.length() == 1) {hH = "0" + hH;}
            String textLow = hL + ":" + mL;
            String textHigh = hH + ":" + mH;
            Quarter quarter = thisUser.getSimpleCalendar().getQuarter(date, i);
            Label info = new Label(" " + textLow + " - " + textHigh);
            if (quarter != null && quarter.getActivity() != null) {
                info.setText(info.getText() + "  " + quarter.getActivity().toString());
                int hash = quarter.getActivity().toString().hashCode();
                info.setStyle("-fx-background-color: rgb(" + (190 + hash % 40) + ", " + (190 + (hash * 7) % 40) + ", " + (190 + (hash * 27) % 40) + ");");
                info.setPrefHeight(20.0);
                info.setPrefWidth(430);
            }
            info.setMinWidth(350);
            info.setFont(Font.font("Monospaced", info.getFont().getSize()));
            anchorPane.getChildren().add(info);
            anchorPane.setTopAnchor(info, i * 20.0);
            anchorPane.setLeftAnchor(info, 0.0);
        }
        anchorPane.setPrefHeight(350);
        HBox hBox = new HBox();
        hBox.getChildren().add(anchorPane);
        hBox.setMaxHeight(20.0 * 96.0 + 5);
        hBox.setPrefHeight(20.0 * 96.0 + 5);
        hBox.setMinHeight(20.0 * 96.0 + 5);
        calendar.setContent(hBox);
    }
}
