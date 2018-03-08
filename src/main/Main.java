package main;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GregorianCalendar birthday = new GregorianCalendar();
        birthday.set(2000, Calendar.MARCH, 4);
        User user = new User("AAA", "BBB", "CCC", "password", birthday);
        System.out.println(user.toString());

        System.out.println(user.isOfAge() ? "Maggiorenne" : "Non maggiorenne");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
