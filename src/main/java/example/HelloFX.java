package example;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Function;

public class HelloFX extends Application {

    private final static String DEFAULT_NO_FILE_TEXT = "- Файл не выбран -";

    @Override
    public void start(Stage stage) {
        TextArea textArea = new TextArea();

        Function<File, String> fileHandler = file -> {
            if (file == null) {
                return DEFAULT_NO_FILE_TEXT;
            }
            textArea.setText(getFileContent(file));
            return file.getAbsolutePath();
        };

        Label outputLabel = new Label();
        outputLabel.setText(DEFAULT_NO_FILE_TEXT);
        Button selectionButton = newFileSelectionButton(stage, fileHandler, outputLabel);

        Scene scene = new Scene(newVBox(selectionButton, outputLabel, textArea), 480, 640);

        stage.setScene(scene);
        stage.setTitle("Обработка файла");
        stage.show();
    }

    private static String bytesToString(byte[] bytes) {
        return "Байтов в файле: " + bytes.length;
    }

    private static String getFileContent(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return bytesToString(inputStream.readAllBytes());
        } catch (IOException e) {
            return "Loading content failed";
        }
    }

    private static VBox newVBox(Node... elements) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(elements);
        return vBox;
    }

    private static TilePane newTilePane(Node... elements) {
        TilePane pane = new TilePane();
        pane.getChildren().addAll(elements);
        return pane;
    }

    public static void main(String[] args) {
        launch();
    }

    private Button newFileSelectionButton(Stage stage, Function<File, String> fileHandler, Label outputLabel) {
        Button button = new Button("Выбрать файл");
        button.setOnMousePressed(event -> outputLabel.setText(fileHandler.apply(getFileWithChooser(stage))));
        return button;
    }

    private static File getFileWithChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        return fileChooser.showOpenDialog(stage);
    }

    private Label newAboutLabel() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        return new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    }
}
