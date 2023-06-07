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


    private Avatar avatar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gc = this.canvas.getGraphicsContext2D();
        this.canvas.setFocusTraversable(true);
        this.canvas.setOnKeyPressed(this::onKeyPressed);
        this.canvas.setOnKeyReleased(this::onKeyReleased);
        this.avatar = new Avatar();
        (new Thread(this.avatar)).start();
        this.draw();
    }


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
                    avatar.draw(gc);
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