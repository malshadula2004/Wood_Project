package org.example.ggg.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class Page1Controller {

    public Text woodtext;
    public ImageView Imageview3;
    public ImageView imageview4; // Add reference for imageview4
    public Text text2;  // Add reference for text2
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    private boolean isImage1Visible = false;
    private boolean isImage2Visible = true;  // Start with imageView2 visible
    private boolean isImage3Visible = false;
    private boolean isImage4Visible = false;

    @FXML
    private AnchorPane Page11;
    @FXML
    private Text woodlot; // Animated Text

    @FXML
    private void initialize() {
        // Resource loading and image set
        setImageView(imageView1, "/image/pexels-lum3n-44775-235309.jpg");
        setImageView(imageView2, "/image/pexels-photo-122588.jpeg");
        setImageView(Imageview3, "/image/pexels-daniel-reche-718241-7109997.jpg");
        setImageView(imageview4, "/image/pexels-daniel-reche-718241-7109998.jpg"); // Load image for imageview4

        // Play Text Animation
        playTextAnimation();

        // Start sequential fade transitions
        startSequentialTransition();
    }

    // Helper method to set images for ImageView
    private void setImageView(ImageView imageView, String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            imageView.setImage(new Image(imageUrl.toExternalForm()));
            imageView.setOpacity(0.0); // Initially set opacity to 0
        } else {
            System.out.println("Image not found: " + imagePath);
        }
    }

    private void playTextAnimation() {
        // Simple Fade-in animation for woodtext
        FadeTransition fadeWoodText = new FadeTransition(Duration.seconds(3), woodtext);
        fadeWoodText.setFromValue(0.0);
        fadeWoodText.setToValue(1.0);
        fadeWoodText.play();

        // Fade-in animation for text2 at the same time as woodtext
        FadeTransition fadeText2 = new FadeTransition(Duration.seconds(3), text2);
        fadeText2.setFromValue(0.0);
        fadeText2.setToValue(1.0);
        fadeText2.play();
    }

    // Sequential fade animation for ImageViews
    private void startSequentialTransition() {
        imageView1.setOpacity(0.0);
        imageView2.setOpacity(1.0);  // Start with imageView2 visible
        Imageview3.setOpacity(0.0);
        imageview4.setOpacity(0.0);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(6000); // Delay between transitions
                    javafx.application.Platform.runLater(this::sequentialFadeImages);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sequentialFadeImages() {
        FadeTransition fadeOut = null;
        FadeTransition fadeIn = null;

        if (isImage2Visible) {
            fadeOut = new FadeTransition(Duration.seconds(2), imageView2);
            fadeIn = new FadeTransition(Duration.seconds(2), Imageview3);
        } else if (isImage3Visible) {
            fadeOut = new FadeTransition(Duration.seconds(2), Imageview3);
            fadeIn = new FadeTransition(Duration.seconds(2), imageview4);
        } else if (isImage4Visible) {
            fadeOut = new FadeTransition(Duration.seconds(2), imageview4);
            fadeIn = new FadeTransition(Duration.seconds(2), imageView1);
        } else if (isImage1Visible) {
            fadeOut = new FadeTransition(Duration.seconds(2), imageView1);
            fadeIn = new FadeTransition(Duration.seconds(2), imageView2);
        }

        if (fadeOut != null && fadeIn != null) {
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            fadeOut.play();
            fadeIn.play();
        }

        if (isImage2Visible) {
            isImage2Visible = false;
            isImage3Visible = true;
        } else if (isImage3Visible) {
            isImage3Visible = false;
            isImage4Visible = true;
        } else if (isImage4Visible) {
            isImage4Visible = false;
            isImage1Visible = true;
        } else if (isImage1Visible) {
            isImage1Visible = false;
            isImage2Visible = true;
        }
    }

    public void gotopage11acttion(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane page = FXMLLoader.load(getClass().getResource("/org/example/ggg/view/page3.fxml"));
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setScene(new Scene(page));
        newStage.setResizable(false);
        newStage.setWidth(790);
        newStage.setHeight(550);
        newStage.show();
    }

    public void goToPage1Acttion(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane page = FXMLLoader.load(getClass().getResource("/org/example/ggg/view/user.fxml"));
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setScene(new Scene(page));
        newStage.setResizable(false);
        newStage.setWidth(790);
        newStage.setHeight(550);
        newStage.show();
    }
}
