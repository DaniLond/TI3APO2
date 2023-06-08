package com.example.titresapo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    private int disparosRecibidos = 0;
    private boolean isGameOver = false;
    @FXML
    private ImageView bala;
    @FXML
    private ImageView coaraon;




    @FXML
    private Label Balas;

    @FXML
    private Label vida;
    private int Vida = 3;

    @FXML
    private ImageView Apuntador;

    private boolean hasWeapon = false;
    private boolean showApuntador = false;

    private boolean isAlive = true;

    private boolean Apressed = false;
    private boolean Wpressed = false;
    private boolean Spressed = false;
    private boolean Dpressed = false;

    private boolean Rpressed= false;

    private int contador=0;
    private int contadorAvatar=0;

    private ArrayList<Stage> stages;

    private int currentStage = 0;

    private Avatar avatar;

    private int bulletsInClip = 10;

    private int count=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Balas.setText("Balas: " + bulletsInClip);
        vida.setText("Vida:  " + Vida);
        this.gc = this.canvas.getGraphicsContext2D();
        this.canvas.setFocusTraversable(true);
        this.canvas.setOnKeyPressed(this::onKeyPressed);
        this.canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMousePressed(this::onMousePressed);
        this.canvas.setOnMouseReleased(this::onMouseReleased);
        stages=new ArrayList<>();

        this.avatar = new Avatar();
        (new Thread(this.avatar)).start();

        Stage s1=new Stage(canvas, gc, 0, avatar);
        s1.getEnemies().add(new Enemy(canvas, gc, avatar));
        s1.getEnemies().add(new Enemy(canvas, gc, avatar));
        s1.getEnemies().add(new Enemy(canvas, gc, avatar));
        s1.draw(gc);


        Stage s2= new Stage(canvas, gc, 1, avatar);
        s2.getEnemies().add(new Enemy(canvas, gc, avatar));
        s2.getEnemies().add(new Enemy(canvas, gc, avatar));
        s2.getEnemies().add(new Enemy(canvas, gc, avatar));
        s2.draw(gc);

        Stage s3=new Stage(canvas, gc, 2, avatar);
        s3.getEnemies().add(new Enemy(canvas, gc, avatar));
        s3.getEnemies().add(new Enemy(canvas, gc, avatar));
        s3.getEnemies().add(new Enemy(canvas, gc, avatar));
        s3.draw(gc);

        stages.add(s1);
        stages.add(s2);
        stages.add(s3);

        //Arma aleatoria en el canvas
        Weapon weapon = new Weapon(1);
        Weapon weapon1= new Weapon(2);
        // Configurar la posición aleatoria del arma
        generateRandomPosition(weapon);
        generateRandomPosition(weapon1);
        // Agregar el arma al ArrayList
        s1.getWeapon().add(weapon);
        s1.getWeapon().add(weapon1);

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
        for (Weapon weapon : stages.get(0).getWeapon()) {
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
            case R:
                Rpressed= false;
                break;
        }
    }

    public void onKeyPressed(KeyEvent event) {
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
            case R:
                Rpressed= true;
                // Recargar balas
                if (Rpressed) {
                    bulletsInClip = 10;
                    Balas.setText("Balas: " + bulletsInClip);
                }
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

        Weapon nearbyWeapon = null;

        //Recoger el arma dando clic en el avatar
        if (avatar.getBoundingBox().contains(e.getX(), e.getY())) {
            // Verificar si hay un arma cercana al hacer clic en el avatar
            nearbyWeapon = getNearbyWeapon(avatar.pos.getX(), avatar.pos.getY());
            if (nearbyWeapon != null) {
                // El avatar recoge el arma
                avatar.pickUpWeapon(nearbyWeapon);
                stages.get(0).getWeapon().remove(nearbyWeapon);
            }
        }

        //Disparar

        if (avatar.getCurrentWeapon() != null) {
            if (bulletsInClip <= avatar.getCurrentWeapon().getMaxBullets() && bulletsInClip > 0) {
                Projectile projectileUno = null;

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (diffX > 0) {
                        projectileUno = new Projectile(new Vector(avatar.pos.getX() + 60, avatar.pos.getY() + 25), diff, avatar.getCurrentWeapon().getType());

                    } else {
                        projectileUno = new Projectile(new Vector(avatar.pos.getX() - 30, avatar.pos.getY() + 25), diff, avatar.getCurrentWeapon().getType());
                    }
                } else {
                    if (diffY > 0) {
                        projectileUno = new Projectile(new Vector(avatar.pos.getX() + 5, avatar.pos.getY() + 60), diff, avatar.getCurrentWeapon().getType());

                    } else {
                        projectileUno = new Projectile(new Vector(avatar.pos.getX(), avatar.pos.getY() - 30), diff, avatar.getCurrentWeapon().getType());

                    }
                }
                avatar.getCurrentWeapon().getProjectiles().add(projectileUno);
                // Iniciar los hilos de los proyectiles del arma actual del avatar
                for (Projectile projectile : avatar.getCurrentWeapon().getProjectiles()) {
                    (new Thread(projectile)).start();
                }
                stages.get(currentStage).getProjectiles().add(projectileUno);


                // Disminuir la cantidad de proyectiles disparados

                System.out.println(bulletsInClip);
            }

        }
        avatar.setMoving(true);
        avatar.setAttacking(true);

        if (avatar.getCurrentWeapon() != null) {
            if (bulletsInClip >= 1) {

                bulletsInClip--;


                Balas.setText("Balas: " + bulletsInClip);
            }
        }


    }



    private void onMouseReleased(MouseEvent e) {
        avatar.setAttacking(false);
    }

    private void moveValid(){

        if(currentStage==0) {

            if (avatar.pos.getX() < 1) {
                avatar.pos.setX(1);
            }
            if(avatar.pos.getX()>canvas.getWidth()-50){
                avatar.pos.setX(canvas.getWidth()-50);
            }
            if (avatar.pos.getY() > canvas.getHeight() - 50) {
                avatar.pos.setY(canvas.getHeight() - 50);
            }
            if (avatar.pos.getY() < 0) {
                currentStage=1;
                avatar.pos.setY(canvas.getHeight());
            }
        }

        if(currentStage==1){
            if (avatar.pos.getX() < 1) {
                avatar.pos.setX(1);
            }
            if(avatar.pos.getX()>canvas.getWidth()-50){
                avatar.pos.setX(canvas.getWidth()-50);
            }
            if (avatar.pos.getY() > canvas.getHeight() - 50) {
                avatar.pos.setY(canvas.getHeight() - 50);
            }
            if (avatar.pos.getY() < 0) {
                currentStage=2;
                avatar.pos.setY(canvas.getHeight());
            }
        }
        if(currentStage==2){
            if (avatar.pos.getX() < 1) {
                avatar.pos.setX(1);
            }
            if(avatar.pos.getX()>canvas.getWidth()-50){
                avatar.pos.setX(canvas.getWidth()-50);
            }
            if (avatar.pos.getY() > canvas.getHeight() - 50) {
                avatar.pos.setY(canvas.getHeight() - 50);
            }
            if (avatar.pos.getY() < 1) {
                avatar.pos.setY(1);
            }
        }

    }

    //Mensaje de recargar
    private void drawReloadMessage(GraphicsContext gc) {
        double x = avatar.pos.getX() + 2;
        double y = avatar.pos.getY() - 10;
        String message = "Recargue (R)";
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 14));
        gc.fillText(message, x, y);
    }

    private void message(GraphicsContext gc) {
        double x = canvas.getWidth()/2;
        double y = canvas.getHeight()/2;
        String message = "GAME OVER";
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 60));
        gc.fillText(message, x, y);
    }

    public void draw() {

        Thread ae = new Thread(() -> {
            while (isAlive) {
                //Dibujar en el lienzo
                Stage stage = stages.get(currentStage);
                moveValid();
                Platform.runLater(() -> {//Runnable
                    //Lo que hagamos aqui, corre en el main thread
                    stages.get(currentStage).draw(gc);
                    //gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
                    this.avatar.setMoving(this.Wpressed || this.Spressed || this.Dpressed || this.Apressed);
                    //Arma aleatoria en el canvas
                    for (Weapon weapon : stages.get(0).getWeapon()) {
                        weapon.draw(gc);
                        weapon.setFacingRight(true);
                    }
                    //

                    avatar.draw(gc);

                    for (int i = 0; i < stage.getEnemies().size(); i++) {
                        stage.getEnemies().get(i).draw(gc);
                    }


                    for (int i = 0; i < stage.getEnemies().size(); i++) {
                        (new Thread(stage.getEnemies().get(i))).start();
                    }

                    if (avatar.getCurrentWeapon() != null) {
                        for (int i = 0; i < avatar.getCurrentWeapon().getProjectiles().size(); i++) {
                            avatar.getCurrentWeapon().getProjectiles().get(i).draw(gc);
                        }
                    }

                    for (int i = 0; i < stage.getProjectiles().size(); i++) {
                        if (isOutside(stage.getProjectiles().get(i).pos.getX(), stage.getProjectiles().get(i).pos.getY())) {
                            stage.getProjectiles().remove(i);
                            avatar.getCurrentWeapon().getProjectiles().remove(i);
                        }
                    }

                    if (avatar.getCurrentWeapon() != null && bulletsInClip == 0) {
                        drawReloadMessage(gc);
                    }

                });


                //Colisiones
                for (int i = 0; i < stage.getProjectiles().size(); i++) {
                    Projectile bn = stage.getProjectiles().get(i);
                    for (int j = 0; j < stage.getEnemies().size(); j++) {
                        Enemy en = stage.getEnemies().get(j);

                        double distance = Math.sqrt(
                                Math.pow(en.pos.getX() - bn.pos.getX(), 2) +
                                        Math.pow(en.pos.getY() - bn.pos.getY(), 2)
                        );

                        if (distance < 30) {
                            contador++;
                            if (avatar.getCurrentWeapon().getProjectiles().size() > 0) {
                                stage.getProjectiles().remove(0);
                            }

                            if (avatar.getCurrentWeapon().getProjectiles().size() > 0) {
                                avatar.getCurrentWeapon().getProjectiles().remove(0);
                            }

                            if (contador == 3) {
                                stage.getEnemies().remove(j);
                                j--;
                                contador = 0;
                            }
                        }

                    }
                }


                // Colisiones
                for (int i = 0; i < stage.getEnemies().size(); i++) {
                    for (int j = 0; j < stage.getEnemies().get(i).getProjectiles().size(); j++) {
                        Projectile projectile = stage.getEnemies().get(i).getProjectiles().get(j);

                        // Comprueba la colisión entre el proyectil y el avatar
                        double distance = Math.sqrt(
                                Math.pow(projectile.pos.getX() - avatar.pos.getX(), 2) +
                                        Math.pow(projectile.pos.getY() - avatar.pos.getY(), 2)
                        );

                        if (distance < 25) {
                            disparosRecibidos++; // Incrementar el contador de disparos recibidos
                            stage.getEnemies().get(i).getProjectiles().remove(j); // Eliminar el proyectil
                            j--;

                            if (disparosRecibidos == 3) {
                                Vida--; // Reducir la vida del jugador
                                disparosRecibidos = 0; // Reiniciar el contador de disparos recibidos
                                vida.setText("vida " + Vida); // Actualizar la etiqueta de vida en la interfaz gráfica

                                if (Vida == 0) {
                                    stages.get(currentStage).draw(gc);
                                    gc.setFill(Color.BLACK);
                                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                                    message(gc);
                                    isAlive = false;
                                }
                            }
                        }


                        if (distance < 25) {
                            count++;
                            System.out.println(count);
                            if (count == 3) {
                                stages.get(currentStage).draw(gc);
                                gc.setFill(Color.BLACK);
                                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                                message(gc);
                                isAlive = false;
                            }

                        }

                    }
                }

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
                if (this.Rpressed) {
                    bulletsInClip = 10;
                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {

                    vida.setText("Vida: " + Vida); // Actualizar etiqueta "vida" con el valor actualizado de Vida
                });

            }
        });
        ae.start();
        // Colisiones
        double distance = 0;
        for (int i = 0; i < Stage.getEnemies().size(); i++) {
            for (int j = 0; j < Stage.getEnemies().get(i).getProjectiles().size(); j++) {
                Projectile projectile = Stage.getEnemies().get(i).getProjectiles().get(j);

                // Comprueba la colisión entre el proyectil y el avatar
                distance = Math.sqrt(
                        Math.pow(projectile.pos.getX() - avatar.pos.getX(), 2) +
                                Math.pow(projectile.pos.getY() - avatar.pos.getY(), 2)
                );

                if (distance < 25) {
                    disparosRecibidos++;
                    if (disparosRecibidos == 3) {
                        isGameOver = true;
                        gc.setFill(Color.RED);
                        gc.setFont(new Font("Arial", 60));
                        gc.fillText("Game Over", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2);
                    }
                }
            }
        }

        // Colisiones
        for (int i = 0; i < Stage.getEnemies().size(); i++) {
            for (int j = 0; j < Stage.getEnemies().get(i).getProjectiles().size(); j++) {
                Projectile projectile = Stage.getEnemies().get(i).getProjectiles().get(j);

                // Comprueba la colisión entre el proyectil y el avatar
                double distances = Math.sqrt(
                        Math.pow(projectile.pos.getX() - avatar.pos.getX(), 2) +
                                Math.pow(projectile.pos.getY() - avatar.pos.getY(), 2)
                );

                if (distances < 25) {
                    count++;
                    System.out.println(count);
                    if (count == 3) {
                        stages.get(currentStage).draw(gc);
                        gc.setFill(Color.BLACK);
                        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        reduceVida();  // Reducir la vida del jugador
                    }
                }
            }
        }



        Platform.runLater(() -> {
            if (isGameOver) {
                message(gc);
                isAlive = false;
            }

        });
        Platform.runLater(() -> {

            vida.setText("Vida: " + Vida); // Actualizar etiqueta "vida" con el valor actualizado de Vida
        });


    }


    public boolean isOutside(double x, double y){
        return x<-10 || y<-10 || x>canvas.getWidth() || y>canvas.getHeight();
    }

    private void reduceVida() {
        Vida --;
        vida.setText("Vidas: " + Vida);
        if (Vida <= 0) {
            isGameOver = true;
            Platform.runLater(() -> message(gc));
            isAlive = false;
        }
    }



}


