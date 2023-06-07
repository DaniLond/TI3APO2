package com.example.titresapo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Stage extends Drawing{

    private int id;

    private Canvas canvas;
    private GraphicsContext gc;

    private ArrayList<Weapon> weapon;
    ArrayList<Enemy> enemies;

    private Avatar avatar;

    public Stage(Canvas canvas, GraphicsContext gc, int id,Avatar avatar) {
        this.canvas=canvas;
        this.gc=gc;
        weapon= new ArrayList<>();
        this.id=id;
        this.avatar=avatar;
        this.pos.setX(100.0);
        this.pos.setY(100.0);
        generateEnemies();
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

    public ArrayList<Weapon> getWeapon() {
        return weapon;
    }

    public void setWeapon(ArrayList<Weapon> weapon) {
        this.weapon = weapon;
    }

    public int getId() {
        return id;
    }

    public void generateEnemies(){
        if (id==0){
            enemies=new ArrayList<>();
            gc.setFill(Color.YELLOW);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
        if (id==1){
            enemies=new ArrayList<>();
            gc.setFill(Color.BLUE);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
        if (id==2){
            enemies=new ArrayList<>();
            gc.setFill(Color.VIOLET);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}
