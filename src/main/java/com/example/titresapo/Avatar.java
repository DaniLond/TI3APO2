package com.example.titresapo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Avatar extends Drawing implements Runnable {
    private Image idle;
    private Image[] runRight;

    private Image[] runLeft;

    private Image[] runDown;
    private Image[] runUp;

    private Image[] attack;
    private int frame = 0;
    private boolean isMoving;
    private boolean isFacingRight = false;
    private boolean isFacingDown = false;
    boolean isFacingUp = false;
    boolean isFacingLeft= false;
    
    boolean isAttack= false;

    private ArrayList<Weapon> weapons;

    public Avatar(){
        this.pos.setX(100.0);
        this.pos.setY(100.0);


        int i;
        String uri;
        uri = "file:" + HelloApplication.class.getResource("idle/Avatar/player-idle1" + ".png").getPath();
        idle= new Image(uri);

        this.runDown = new Image[4];

        for(i = 1; i <= 4; ++i) {
            uri = "file:" + HelloApplication.class.getResource("walk/player/walkDown/walk-player" + i + ".png").getPath();
            this.runDown[i - 1] = new Image(uri);
        }

        this.runUp = new Image[4];

        for(i = 1; i <= 4; ++i) {
            uri = "file:" + HelloApplication.class.getResource("walk/player/walkUp/walk-player" + i + ".png").getPath();
            this.runUp[i - 1] = new Image(uri);
        }

        this.runLeft = new Image[4];

        for(i = 1; i <= 4; ++i) {
            uri = "file:" + HelloApplication.class.getResource("walk/player/walkLeft/walk-player" + i + ".png").getPath();
            this.runLeft[i - 1] = new Image(uri);
        }

        this.runRight = new Image[4];

        for(i = 1; i <= 4; ++i) {
            uri = "file:" + HelloApplication.class.getResource("walk/player/walkRight/walk-player" + i + ".png").getPath();
            this.runRight[i - 1] = new Image(uri);
        }

        this.attack= new Image[4];

        for(i = 1; i <= 4; ++i) {
            uri = "file:" + HelloApplication.class.getResource("Attack/Player/attack-" + i + ".png").getPath();
            this.attack[i - 1] = new Image(uri);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isMoving){
            if (isFacingRight) {
                gc.drawImage(this.runRight[this.frame], this.pos.getX(), this.pos.getY(), 50, 50);
            } else if (isFacingDown) {
                gc.drawImage(this.runDown[this.frame], this.pos.getX(), this.pos.getY(), 50, 50);
            } else if (isFacingUp) {
                gc.drawImage(this.runUp[this.frame], this.pos.getX(), this.pos.getY(), 50, 50);
            } else if (isFacingLeft) {
                gc.drawImage(this.runLeft[this.frame], this.pos.getX(), this.pos.getY(), 50, 50);
            }
        }else {
            gc.drawImage(this.idle, this.pos.getX() , this.pos.getY() , 50 , 50 );
        }
    }

    @Override
    public void run() {
        while(true) {
            if (isMoving){
                this.frame = (this.frame + 1) % 4;
                System.out.println(frame);
            }else {
                frame=0;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public void attack(){

    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }

    public boolean isFacingDown() {
        return isFacingDown;
    }

    public void setFacingDown(boolean facingDown) {
        isFacingDown = facingDown;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setFacingUp(boolean facingUp) {
        isFacingUp = facingUp;
    }

    public void setFacingLeft(boolean facingLeft) {
        isFacingLeft = facingLeft;
    }
}
