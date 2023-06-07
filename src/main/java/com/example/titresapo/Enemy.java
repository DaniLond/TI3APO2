package com.example.titresapo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends Drawing implements Runnable{

    private Canvas canvas;
    private GraphicsContext gc;

    public Enemy(Canvas canvas, GraphicsContext gc) {
        this.canvas=canvas;
        this.gc=gc;
        generateRandomPosition();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.RED);
        gc.fillRect(pos.getX(), pos.getY(), 50,50);
        gc.strokeRect(pos.getX(), pos.getY(), 50,50);

    }

    @Override
    public void run() {


    }

    private void generateRandomPosition() {
        double maxX = canvas.getWidth();
        double maxY = canvas.getHeight();

        double x = Math.random() * maxX;
        double y = Math.random() * maxY;

        pos.setX(x);
        pos.setY(y);
    }


}
