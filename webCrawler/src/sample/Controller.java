package sample;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static sample.CrawlService.totalEmails;

public class Controller {
    @FXML
    private JFXTextField webAddress;
    @FXML
    private JFXComboBox<Integer> times;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private JFXListView<Label> listView;
    @FXML
    private JFXButton exButton;
    @FXML
    private JFXButton startButton;
    @FXML
    private Label message;

    public void initialize(){
        List<Integer> list =new ArrayList<>();
        for (int i=1; i<6; i++){
            list.add(i);
        }
        times.getItems().setAll(list);


    }
    public void onStartClicked(){
        listView.getItems().clear();
        message.setText("");
        exButton.setDisable(true);
        startButton.setDisable(true);
        webAddress.setDisable(true);
        times.setDisable(true);
        CrawlService service = new CrawlService(webAddress.getText(),times.getValue());
        progressBar.progressProperty().bind(service.progressProperty());
        service.start();
        service.setOnSucceeded(event ->{
            message.setText("عملیات با موفقیت انجام شد");

            for (String email: totalEmails){
                try {
                    Label label = new Label(email);
                    label.setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Mahdi\\Desktop\\ne\\webCrawler\\email.png"))));
                    listView.getItems().add(label);
                    exButton.setDisable(false);
                    startButton.setDisable(false);
                    webAddress.setDisable(false);
                    times.setDisable(false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setExButtonClicked(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            FileWriter myWriter = new FileWriter(selectedDirectory+"\\emails.txt");
            for (String email:totalEmails){
                myWriter.write(email+"\n");
            }
            myWriter.close();
            message.setText("  فایل در :  "+selectedDirectory.getAbsolutePath()+"قرار گرفت ");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
