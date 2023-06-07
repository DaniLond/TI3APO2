package com.example.titresapo;

import javafx.scene.canvas.GraphicsContext;


public abstract class Drawing {
    protected Vector pos = new Vector(0.0, 0.0);

    public Drawing() {
    }

    public abstract void draw(GraphicsContext var1);
}
