package editor;

import static java.lang.Math.*;

public class Pixel {
    private int red;
    private int green;
    private int blue;

    Pixel(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    public void setRed(char red) {
        this.red = red;
    }

    public void invert(int maxColor){
        red = maxColor - red;
        green = maxColor - green;
        blue = maxColor - blue;
    }

    public void grayscale(){
        int average = (red + green + blue) / 3;
        red = average;
        green = average;
        blue = average;
    }

    public void emboss(int altRed, int altGreen, int altBlue, int maxColor){
        int redDiff = red - altRed;
        int greenDiff = green - altGreen;
        int blueDiff = blue - altBlue;
        int greatestDiff = getGreatestDiff(redDiff, greenDiff, blueDiff);
        int middleColor = (maxColor / 2) + 1;

        int finalColor = middleColor + greatestDiff;
        if (finalColor < 0) finalColor = 0;
        else if (finalColor > maxColor) finalColor = maxColor;

        red = finalColor;
        green = finalColor;
        blue = finalColor;
    }

    public void setAll(int color){
        red = color;
        green = color;
        blue = color;
    }

    public static int getGreatestDiff(int... ints){
        int greatestDiff = 0;
        for(int num : ints){
            if (abs(num) > abs(greatestDiff)){
                greatestDiff = num;
            }
        }
        return greatestDiff;
    }

    @Override
    public String toString(){
        return red + " " + green + " " + blue;
    }

    public int getRed() {
        return red;
    }

    public void setBlue(char blue) {
        this.blue = blue;
    }

    public int getBlue() {
        return blue;
    }

    public void setGreen(char green) {
        this.green = green;
    }

    public int getGreen() {
        return green;
    }
}
