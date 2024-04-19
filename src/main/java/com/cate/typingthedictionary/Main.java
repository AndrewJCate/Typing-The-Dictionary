package com.cate.typingthedictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.cate.typingthedictionary.constants.Constants.*;

public class Main extends Application {

    private static Stage stg;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        stg = stage;

        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(SUMMARY_VIEW))));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLES_FILE)).toExternalForm());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(ICON_FILE)).toString()));

        stage.show();

    }

    public void changeScene(String fxml) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));

        stg.getScene().setRoot(root);
    }
}