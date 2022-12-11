package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private Image img;
    private ImageView iview;
    private Label label;
    private VBox vbox;

    public GuiElementBox(String img, String text) throws FileNotFoundException {
        this.img = new Image(new FileInputStream(img));
        this.iview = new ImageView(this.img);
        this.iview.setFitHeight(40);
        this.iview.setFitWidth(40);
        this.label = new Label(text);
        this.vbox = new VBox();
        label.setAlignment(Pos.CENTER);
        vbox.setPrefSize(60, 60);
        vbox.getChildren().addAll(iview, label);
        vbox.setAlignment(Pos.CENTER);
    }

    public VBox getVBox(){
        return vbox;
    }
}
