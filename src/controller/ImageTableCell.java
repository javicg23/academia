/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<Alumno> extends TableCell<Alumno, Image> {

    private ImageView imageView;

    public ImageTableCell() {
        imageView = new ImageView();
        imageView.setFitHeight(75);
        imageView.setFitWidth(75);
        imageView.setPreserveRatio(true);
    }

    @Override
    protected void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        setGraphic(null);
        if ((!empty) && (null != item)) {
            imageView.setImage(item);
            setGraphic(imageView);
        }
    }
}
