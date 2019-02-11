package com.example.bearlake;

public class Speeds {
    private static final Speeds ourInstance = new Speeds();

    public static Speeds getInstance() {
        return ourInstance;
    }

    public int getHumanSpeed() {
        return humanSpeed;
    }

    public void setHumanSpeed(int humanSpeed) {
        this.humanSpeed = humanSpeed;
    }

    public int getBearSpeed() {
        return bearSpeed;
    }

    public void setBearSpeed(int bearSpeed) {
        this.bearSpeed = bearSpeed;
    }

    private int humanSpeed;
    private int bearSpeed;

    public float getHumanX() {
        return humanX;
    }

    public void setHumanX(float humanX) {
        this.humanX = humanX;
    }

    public float getHumanY() {
        return humanY;
    }

    public void setHumanY(float humanY) {
        this.humanY = humanY;
    }

    public float getBearX() {
        return bearX;
    }

    public void setBearX(float bearX) {
        this.bearX = bearX;
    }

    public float getBearY() {
        return bearY;
    }

    public void setBearY(float bearY) {
        this.bearY = bearY;
    }

    private float humanX = 0f;
    private float humanY = 0f;

    public double getHumanAngle() {
        return humanAngle;
    }

    public void setHumanAngle(double humanAngle) {
        this.humanAngle = humanAngle;
    }

    private double humanAngle = -Math.PI / 2f;
    private float bearX = 0f;
    private float bearY = 0f;

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    private boolean isFinal = false;

    private Speeds() {

    }
}
