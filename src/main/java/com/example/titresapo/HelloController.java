package com.example.titresapo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    private boolean isAlive = true;

    private boolean Apressed = false;
    private boolean Wpressed = false;
    private boolean Spressed = false;
    private boolean Dpressed = false;

    //Arma aleatoria en el canvas
    private ArrayList<Weapon> weapons = new ArrayList<>();
    //


    private Avatar avatar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gc = this.canvas.getGraphicsContext2D();
        this.canvas.setFocusTraversable(true);
        this.canvas.setOnKeyPressed(this::onKeyPressed);
        this.canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMousePressed(this::onMousePressed);
        this.canvas.setOnMouseReleased(this::onMouseReleased);
        this.avatar = new Avatar();
        (new Thread(this.avatar)).start();
        //Arma aleatoria en el canvas

        Weapon weapon = new Weapon(1);
        Weapon weapon1= new Weapon(2);
        // Configurar la posición aleatoria del arma
        generateRandomPosition(weapon);
        generateRandomPosition(weapon1);
        // Agregar el arma al ArrayList
        weapons.add(weapon);
        weapons.add(weapon1);

        //
        this.draw();
    }

    //Arma aleatoria en el canvas
    private void generateRandomPosition(Weapon weapon) {
        double maxX = canvas.getWidth() - 25;// Ajusta según el tamaño del arma
        double maxY = canvas.getHeight() - 25; // Ajusta según el tamaño del arma

        double x = Math.random() * maxX;
        double y = Math.random() * maxY;

        weapon.pos.setX(x);
        weapon.pos.setY(y);
    }
    public Weapon getNearbyWeapon(double x, double y) {
        for (Weapon weapon : weapons) {
            double distance = Math.sqrt(Math.pow(x - weapon.pos.getX(), 2.0) + Math.pow(y - weapon.pos.getY(), 2.0));
            if (distance < 50.0) {
                return weapon;
            }
        }

        return null;
    }

    //


    public void onKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                Wpressed = false;
                break;
            case A:
                Apressed = false;
                break;
            case S:
                Spressed = false;
                break;
            case D:
                Dpressed = false;
                break;
        }
    }

    public void onKeyPressed(KeyEvent event) {
        System.out.println(event.getCode());
        switch (event.getCode()) {
            case W:
                Wpressed = true;
                break;
            case A:
                Apressed = true;
                break;
            case S:
                Spressed = true;
                break;
            case D:
                Dpressed = true;
                break;
        }
    }

    private void onMousePressed(MouseEvent e) {

        double diffX = e.getX() - avatar.pos.getX();
        double diffY = e.getY() - avatar.pos.getY();

        //Disparar
        Vector diff = new Vector(diffX, diffY);
        diff.normalize();
        diff.setMag(4);

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                avatar.setFacingRight(true);
                avatar.setFacingLeft(false);
                avatar.setFacingUp(false);
                avatar.setFacingDown(false);
            } else {
                avatar.setFacingLeft(true);
                avatar.setFacingRight(false);
                avatar.setFacingUp(false);
                avatar.setFacingDown(false);
            }
        } else {
            if (diffY > 0) {
                avatar.setFacingDown(true);
                avatar.setFacingUp(false);
                avatar.setFacingLeft(false);
                avatar.setFacingRight(false);
            } else {
                avatar.setFacingUp(true);
                avatar.setFacingDown(false);
                avatar.setFacingLeft(false);
                avatar.setFacingRight(false);

            }
        }

        Weapon nearbyWeapon= null;

        //Recoger el arma dando clic en el avatar
        if (avatar.getBoundingBox().contains(e.getX(), e.getY())) {
            // Verificar si hay un arma cercana al hacer clic en el avatar
            nearbyWeapon = getNearbyWeapon(avatar.pos.getX(), avatar.pos.getY());
            if (nearbyWeapon != null) {
                // El avatar recoge el arma
                avatar.pickUpWeapon(nearbyWeapon);
                weapons.remove(nearbyWeapon);
            }
        }

        //Disparar
        if (avatar.getCurrentWeapon() != null){
            avatar.getCurrentWeapon().getProjectiles().add(
                    new Projectile( new Vector(avatar.getCurrentWeapon().pos.getX() , avatar.getCurrentWeapon().pos.getY()), diff, avatar.getCurrentWeapon().getType())
            );
        }
        avatar.setMoving(true);
        avatar.setAttacking(true);

    }

    private void onMouseReleased(MouseEvent e) {
        avatar.setAttacking(false);
    }

    public void draw(){
        //
        Thread ae = new Thread(()->{
            while(isAlive){
                //Dibujar en el lienzo
                Platform.runLater(()->{//Runnable
                    //Lo que hagamos aqui, corre en el main thread
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
                    this.avatar.setMoving(this.Wpressed || this.Spressed || this.Dpressed || this.Apressed);
                    //Arma aleatoria en el canvas
                    for (Weapon weapon : weapons) {
                        weapon.draw(gc);
                        weapon.setFacingRight(true);
                    }
                    //

                    avatar.draw(gc);

                    if (avatar.getCurrentWeapon() != null){
                        for (int i=0; i < avatar.getCurrentWeapon().getProjectiles().size(); i++){
                            avatar.getCurrentWeapon().getProjectiles().get(i).draw(gc);
                        }
                    }

                });

                //Calculos geometricos
                if (this.Wpressed) {
                    this.avatar.setFacingDown(false);
                    this.avatar.setFacingLeft(false);
                    this.avatar.setFacingRight(false);
                    this.avatar.setFacingUp(Wpressed);
                    this.avatar.pos.setY(this.avatar.pos.getY() - 3.0);
                }

                if (this.Apressed) {
                    this.avatar.setFacingDown(false);
                    this.avatar.setFacingUp(false);
                    this.avatar.setFacingRight(false);
                    this.avatar.setFacingLeft(Apressed);
                    this.avatar.pos.setX(this.avatar.pos.getX() - 3.0);
                }

                if (this.Spressed) {
                    this.avatar.setFacingUp(false);
                    this.avatar.setFacingLeft(false);
                    this.avatar.setFacingRight(false);
                    this.avatar.setFacingDown(Spressed);
                    this.avatar.pos.setY(this.avatar.pos.getY() + 3.0);
                }

                if (this.Dpressed) {
                    this.avatar.setFacingDown(false);
                    this.avatar.setFacingLeft(false);
                    this.avatar.setFacingUp(false);
                    this.avatar.setFacingRight(Dpressed);
                    this.avatar.pos.setX(this.avatar.pos.getX() + 3.0);
                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {e.printStackTrace();}
            }
        });
        ae.start();
    }


}