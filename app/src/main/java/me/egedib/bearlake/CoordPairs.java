package me.egedib.bearlake;

// TODO: Ez így csúnya -> lehetne így tárolni a medve és embert, ahelyett, hogy get/set lenne koordinátánként.
public class CoordPairs {

    private final float humanX;
    private final float humanY;
    private final float bearX;
    private final float bearY;

    public float getHumanX() {
        return humanX;
    }

    public float getHumanY() {
        return humanY;
    }

    public float getBearX() {
        return bearX;
    }

    public float getBearY() {
        return bearY;
    }


    public CoordPairs(float humanX, float humanY, float bearX, float bearY) {
        this.humanX = humanX;
        this.humanY = humanY;
        this.bearX = bearX;
        this.bearY = bearY;
    }
}
