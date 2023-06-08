package com.example.titresapo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Stage extends Drawing{

    private int id;

    private Canvas canvas;
    private GraphicsContext gc;

    private ArrayList<Weapon> weapon;
    ArrayList<Enemy> enemies;

    private Avatar avatar;

    private ArrayList<Projectile> projectiles;
    private Image stage1;
    private Image stage2;
    private Image stage3;

    public Stage(Canvas canvas, GraphicsContext gc, int id,Avatar avatar) {
        this.canvas=canvas;
        projectiles= new ArrayList<>();
        this.gc=gc;
        weapon= new ArrayList<>();
        this.id=id;
        this.avatar=avatar;
        this.pos.setX(100.0);
        this.pos.setY(100.0);
        generateEnemies();

        String uri1;
        uri1 = "file:" + HelloApplication.class.getResource("stage/stage1" + ".png").getPath();
        stage1= new Image(uri1);

        String uri2;
        uri2 = "file:" + HelloApplication.class.getResource("stage/stage2" + ".jpg").getPath();
        stage2= new Image(uri2);

        String uri3;
        uri3 = "file:" + HelloApplication.class.getResource("stage/stage3" + ".png").getPath();
        stage3= new Image(uri3);
    }


    @Override
    public void draw(GraphicsContext gc) {

        if (id==0){
            gc.drawImage(stage3,0 , 0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
        if (id==1){
            gc.drawImage(stage1,0 , 0, canvas.getWidth(), canvas.getHeight());
            avatar.draw(gc);

        }
        if (id==2){
            gc.drawImage(stage2,0 , 0, canvas.getWidth(), canvas.getHeight());
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

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }



}
