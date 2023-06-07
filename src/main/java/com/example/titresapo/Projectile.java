package com.example.titresapo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Projectile extends Drawing {
    private Vector dir;
    private Image[] projectiles;

    private int type;
    public Projectile(Vector pos, Vector dir, int type){
        this.pos = pos;
        this.dir = dir;

        int i;
        String uri;
        this.projectiles= new Image[4];
        if (type==1){
            for(i = 1; i <= 4; ++i) {
                uri = "file:" + HelloApplication.class.getResource("Attack/Projectiles/projectilUno/arrow-" + i + ".png").getPath();
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
    public void draw(GraphicsContext var1) {

    }
}
