package com.example.titresapo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Enemy extends Drawing implements Runnable{

    private Canvas canvas;
    private GraphicsContext gc;
    private Avatar avatar;

    private Image[] run;
    private Image[] attack;
    private int frame=0;

    private boolean isNear=false;
    private ArrayList<Projectile> projectiles;

    private int shootTimer;
    private int shootInterval;

    public Enemy(Canvas canvas, GraphicsContext gc, Avatar avatar) {
        this.shootTimer=0;
        this.shootInterval=60;
        this.canvas=canvas;
        this.gc=gc;
        this.avatar = avatar;
        generateRandomPosition();

        run = new Image[8];

        for (int i = 1; i <= 5; i++) {

            String uri = "file:" + HelloApplication.class.getResource("Enemy/run/run-" + i + ".png").getPath();
            run[i-1] = new Image(uri);
        }

        attack = new Image[8];

        for (int i = 1; i <= 8; i++) {

            String uri = "file:" + HelloApplication.class.getResource("Enemy/attack/attack-" + i + ".png").getPath();
            attack[i-1] = new Image(uri);
        }
        projectiles=new ArrayList<>();
        autoShoot();


    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isNear){
            gc.drawImage(this.attack[frame], this.pos.getX(), this.pos.getY(),50, 50);
        }else{
            gc.drawImage(this.run[frame], this.pos.getX(), this.pos.getY(),50, 50);
        }
        for (Projectile projectile : projectiles){
            projectile.draw(gc);
        }


    }

    @Override
    public void run() {
        while (true) {
            this.frame = (this.frame + 1) % 5;
            // Calcula la dirección hacia el avatar
            Vector direction = calculateDirection();

            // Actualiza la posición del enemigo en función de la dirección y la velocidad
            pos.setX(pos.getX() + direction.getX());
            pos.setY(pos.getY() + direction.getY());

            autoShoot();

            try {
                Thread.sleep(100000); // Espera un tiempo antes de la siguiente actualización
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void generateRandomPosition() {
        double maxX = canvas.getWidth();
        double maxY = canvas.getHeight();

        double x = Math.random() * maxX;
        double y = Math.random() * maxY;

        pos.setX(x);
        pos.setY(y);
    }

    private Vector calculateDirection() {
        Vector enemyPosition = pos;
        Vector avatarPosition = avatar.pos;

        // Calcula la diferencia de posición entre el enemigo y el avatar
        Vector direction = avatarPosition.subtract(enemyPosition);

        // Normaliza el vector de dirección para obtener una longitud de 1
        direction.normalize();

        // Ajusta la velocidad del enemigo multiplicando el vector de dirección por una velocidad deseada
        direction.setMag(2); // Ajusta la velocidad según tus necesidades

        return direction;
    }

    public void autoShoot() {
        shootTimer++;
        if (shootTimer >= shootInterval) {
            shootTimer = 0;

            // Calcular dirección hacia el avatar
            double direction = Math.atan2(avatar.pos.getY() - pos.getY(), avatar.pos.getX() - pos.getX());

            // Calcular velocidad del proyectil
            double speed = 5;  // Ajusta la velocidad según tus necesidades

            // Crear objeto de proyectil y agregarlo a la lista de proyectiles activos
            Projectile projectile = new Projectile(new Vector(pos.getX(), pos.getY()), calculateDirection(), 1);
            projectiles.add(projectile);
        }
    }


    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
