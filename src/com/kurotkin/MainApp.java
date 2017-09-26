package com.kurotkin;
/**
 * Created by Vitaly Kurotkin on 25.09.2017.
 */

import com.kurotkin.model.Person;
import com.kurotkin.view.PersonEditDialog;
import com.kurotkin.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp(){
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        try {
            initRootLayout();
            showPersonOverview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public void initRootLayout() throws IOException {
        // Загружаем корневой макет из fxml файла.
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Отображаем сцену, содержащую корневой макет.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showPersonOverview() throws IOException {
        // Загружаем сведения об адресатах.
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/PersonOverview.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();

        // Помещаем сведения об адресатах в центр корневого макета.
        rootLayout.setCenter(personOverview);

        // Даём контроллеру доступ к главному приложению.
        PersonOverviewController controller = loader.getController();
        controller.setMainApp(this);
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            PersonEditDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
