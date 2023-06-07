package com.example.titresapo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Stage extends Drawing{

    private int id;

    private Canvas canvas;
    private GraphicsContext gc;


    private Avatar avatar;

    public Stage(Canvas canvas, GraphicsContext gc, int id,Avatar avatar) {
        this.id=id;
        this.avatar=avatar;
        this.canvas=canvas;
        this.gc=gc;
    }


    @Override
    public void draw(GraphicsContext gc) {

        if (id==0){
            gc.setFill(Color.YELLOW);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
        if (id==1){
            gc.setFill(Color.BLUE);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
        if (id==2){
            gc.setFill(Color.VIOLET);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }

    }

}
