package com.example.titresapo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Projectile extends Drawing implements Runnable{
    private Vector dir;
    private Image[] projectiles;

    private int frame = 0;

    private int type;
    public Projectile(Vector pos, Vector dir, int type){
        this.pos = pos;
        this.dir = dir;

        int i;
        String uri;
        this.projectiles= new Image[4];
        if (type==1){
            for(i = 1; i <= 4; ++i) {
                uri = "file:" + HelloApplication.class.getResource("Attack/Projectiles/projectilUno/BigEnergyBall-" + i + ".png").getPath();
                this.projectiles[i - 1] = new Image(uri);
            }
        }else {
            for(i = 1; i <= 4; ++i) {
                uri = "file:" + HelloApplication.class.getResource("Attack/Projectiles/projectilDos/BigShuriken-" + i + ".png").getPath();
                this.projectiles[i - 1] = new Image(uri);
            }
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.projectiles[this.frame], this.pos.getX(), this.pos.getY(), 25, 25);
        pos.setX( pos.getX() + dir.getX() );
        pos.setY( pos.getY() + dir.getY() );
    }

    @Override
    public void run() {
        while(true) {
             this.frame = (this.frame + 1) % 4;
            try {
                Thread.sleep(80);
            } catch (InterruptedException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
