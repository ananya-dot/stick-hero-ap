package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Pillar extends AbstractGameObject {
    private double width;
    private double height;
    private double x;
    private double y;

    private ImageView imageView;


    public Pillar(double width, double height, double x, double y,ImageView imageView) {
        super(imageView);
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {

    }

    void isPassed(){

    }
//
//    public double getWidth() {
//        return width;
//    }
//
//    public void setWidth(double width) {
//        this.width = width;
//    }
//
//    public double getHeight() {
//        return height;
//    }
//
//    public void setHeight(double height) {
//        this.height = height;
//    }
//
//    @Override
//    public double getX() {
//        return x;
//    }
//
//    @Override
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    @Override
//    public double getY() {
//        return y;
//    }
//
//    @Override
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    @Override
//    public ImageView getImageView() {
//        return imageView;
//    }
//
//    @Override
//    public void setImageView(ImageView imageView) {
//        this.imageView = imageView;
//    }

    public double getMidPoint(){
        return this.width / 2;
    }




}
