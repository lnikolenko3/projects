import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
*MailReader application
*@author Liubov Nikolenko
*@version 1
*/
public class MailReader extends Application {
    @Override public void start(Stage stage) {
        Mailbox inb = new Mailbox("Inbox");
        Mailbox imp = new Mailbox("Important");
        Mailbox tr = new Mailbox("Trash");
        Server s = new Server();
        //Create UI controls
        ObservableList<Message> d = FXCollections.observableArrayList();
        Button refresh = new Button("Refresh");
        ListView<Message> listView = new ListView<Message>(d);
        Button sortBySender = new Button("Sort by Sender");
        sortBySender.setOnAction(e ->
            {
                d.setAll(d.sorted((s1, s2) ->
                    s1.getSender().compareTo(s2.getSender())));
                imp.sortBySender();
                inb.sortBySender();
                tr.sortBySender();
            });
        Button sortByDate = new Button("Sort by Date");
        sortByDate.setOnAction(e ->
            {
                d.setAll(d.sorted((s1, s2) ->
                    s2.getDate().compareTo(s1.getDate())));
                imp.sortByDate();
                inb.sortByDate();
                tr.sortByDate();
            });
        Button sortBySubject = new Button("Sort by Subject");
        sortBySubject.setOnAction(e ->
            {
                d.setAll(d.sorted((s1, s2) ->
                    s1.getSubject().compareTo(s2.getSubject())));
                imp.sortBySubject();
                inb.sortBySubject();
                tr.sortBySubject();

            });
        Button toTrash = new Button("Trash");
        toTrash.setOnAction(e ->
            {
                if (listView.getSelectionModel().getSelectedItem() != null) {
                    Message m = listView.getSelectionModel().getSelectedItem();
                    if (inb.contains(m)) {
                        inb.moveTo(m, tr);
                        d.remove(m);
                    }
                    if (imp.contains(m)) {
                        imp.moveTo(m, tr);
                        d.remove(m);
                    }
                }
            });
        Button flag = new Button("Flag");
        flag.setOnAction(e ->
            {
                if (listView.getSelectionModel().getSelectedItem() != null) {
                    Message m = listView.getSelectionModel().getSelectedItem();
                    if (inb.contains(m)) {
                        inb.moveTo(m, imp);
                        d.remove(m);
                    }
                    if (tr.contains(m)) {
                        tr.moveTo(m, imp);
                        d.remove(m);
                    }
                }
            });
        ObservableList<String> stuff = FXCollections.observableArrayList();
        stuff.addAll("Inbox", "Important", "Trash");
        ListView<String> listView2 = new ListView<String>(stuff);
        Label mess = new Label("Messages");
        Label em = new Label("Display an email");
        Label p = new Label("PERSON:");
        Label d1 = new Label("DATE:");
        Label sub = new Label("SUBJECT:");
        ObservableList<String> m11 = FXCollections.observableArrayList();
        m11.add("");
        ListView<String> m1 = new ListView<>(m11);
        listView2.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov,
                    String oldval, String newval) {
                    if (newval.equals("Important")) {
                        d.setAll(imp.sort());
                        reset(m11, sub, d1, p);
                    }
                    if (newval.equals("Trash")) {
                        d.setAll(tr.sort());
                        reset(m11, sub, d1, p);
                    }
                    if (newval.equals("Inbox")) {
                        d.setAll(inb.sort());
                        reset(m11, sub, d1, p);
                    }
                    if (newval != oldval) {
                        listView.getSelectionModel().selectFirst();
                    }

                }
            });
        refresh.setOnAction(e ->
            {
                s.getMessage(inb);
                inb.sort();
                d.setAll(inb.getMessages());
                listView2.getSelectionModel().selectFirst();
                listView.getSelectionModel().selectFirst();
            });
        m1.setPrefHeight(700);
        m1.setPrefWidth(1000);
        listView.setPrefHeight(700);
        listView.setPrefWidth(400);
        listView.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Message>() {
                public void changed(ObservableValue<? extends Message> ov,
                    Message oldval, Message newval) {
                    if (newval != null) {
                        Message m = newval;
                        m11.setAll(m.getBody());
                        sub.setText("SUBJECT: " + m.getSubject());
                        p.setText("PERSON: " + m.getSender());
                        d1.setText("DATE: " + m.getDate().toLocalDate() + "  "
                            + m.getDate().toLocalTime());
                    }
                }
            });
        // Add UI controls to the scene
        HBox buttons = new HBox(70);
        buttons.getChildren().addAll(refresh, sortBySender, sortByDate,
            sortBySubject, toTrash, flag);
        VBox navigation = new VBox();
        navigation.getChildren().addAll(listView2);
        VBox listOfEmails = new VBox();
        listOfEmails.getChildren().addAll(mess, listView);
        VBox attr = new VBox(30);
        attr.getChildren().addAll(p, d1);
        VBox display = new VBox(30);
        display.getChildren().addAll(em, attr, sub, m1);
        HBox combine = new HBox();
        combine.getChildren().addAll(navigation, listOfEmails, display);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(buttons, combine);
        BorderPane bor = new BorderPane();
        bor.setCenter(vbox);
        bor.setPrefSize(1800, 10000);
        Scene scene = new Scene(bor);
        stage.setScene(scene);
        stage.setTitle("Tech mail");
        stage.show();
    }
    private static void reset(ObservableList<String> m11, Label sub,
        Label d1, Label p) {
        sub.setText("SUBJECT:");
        m11.setAll("");
        d1.setText("DATE:");
        p.setText("PERSON:");

    }
}
