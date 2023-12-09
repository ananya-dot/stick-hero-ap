package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public abstract class AbstractGameObject {

    protected ImageView imageView;

    public AbstractGameObject(ImageView imageView) {
        this.imageView=imageView;
    }



    // Abstract method for updating the game object.
    public abstract void update();

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
