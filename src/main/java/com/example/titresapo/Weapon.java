package com.example.titresapo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Weapon extends Drawing {
    private ArrayList<Projectile> projectiles;
    private int type;

    private Image[] weapon;

    private boolean isFacingRight = false;
    private boolean isFacingDown = false;
    boolean isFacingUp = false;
    boolean isFacingLeft= false;


    public Weapon(int x , int y) {
        int i;
        String uri;
        if (type==1){
            for(i = 1; i <= 4; ++i) {
                uri = "file:" + HelloApplication.class.getResource("Attack/Weapon/weaponUno/weapon-" + i + ".png").getPath();
                this.weapon[i - 1] = new Image(uri);
            }
        }else {
            for(i = 1; i <= 4; ++i) {
                uri = "file:" + HelloApplication.class.getResource("Attack/Weapon/weaponDos/torch-" + i + ".png").getPath();
                this.weapon[i - 1] = new Image(uri);
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isFacingRight){
            gc.drawImage(this.weapon[0] , this.pos.getX(), this.pos.getY(), 25, 25 );
        } else if (isFacingDown) {
            gc.drawImage(this.weapon[3] , this.pos.getX(), this.pos.getY(), 25, 25 );
        } else if (isFacingUp) {
            gc.drawImage(this.weapon[2] , this.pos.getX(), this.pos.getY(), 25, 25 );
        } else if (isFacingLeft) {
            gc.drawImage(this.weapon[1] , this.pos.getX(), this.pos.getY(), 25, 25 );
        }
    }
}
