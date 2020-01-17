package editor;

import javax.sql.rowset.RowSetWarning;
import java.util.*;

public class PPMImage {
    private Pixel[][] pixels;
    private int height;
    private int width;
    private int maxColor;

    PPMImage(int width, int height){
        this.height = height;
        this.width = width;
        pixels = new Pixel[height][width];
    }

    public void addPixel(int red, int green, int blue, int row, int column) {
        pixels[row][column] = new Pixel(red, green, blue);
    }

    public void invert(){
        for (Pixel[] currRow: pixels) {
            for(Pixel currPixel: currRow) {
                currPixel.invert(maxColor);
            }
        }
    }

    public void grayscale(){
        for (Pixel[] currRow: pixels) {
            for(Pixel currPixel: currRow) {
                currPixel.grayscale();
            }
        }
    }

    public void emboss(){
        for (int i = height - 1; i > 0; i--) {
            for(int j = width - 1; j > 0; j--) {
                pixels[i][j].emboss(pixels[(i - 1)][(j - 1)].getRed(), pixels[(i - 1)][(j - 1)].getGreen(),
                        pixels[(i - 1)][(j - 1)].getBlue(), maxColor);
            }
        }

        int middleColor = (maxColor / 2) + 1;
        for (int i = 0; i < height; i++){
            pixels[i][0].setAll(middleColor);
        }
        for (int i = 0; i < width; i++){
            pixels[0][i].setAll(middleColor);
        }
    }

    public PPMImage motionBlur(int blurNum){
        PPMImage new_ = new PPMImage(width, height);
        new_.setMaxColor(maxColor);
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                int[] avg = {0, 0, 0};
                int run = 0;
                for (int k = j; k < (j + blurNum) && (k < width); k++){
                    avg[0] += pixels[i][k].getRed();
                    avg[1] += pixels[i][k].getGreen();
                    avg[2] += pixels[i][k].getBlue();
                    run++;
                }
                for(int k = 0; k < avg.length; k++){
                    avg[k] = avg[k] / run;
                }
                new_.addPixel(avg[0], avg[1], avg[2], i, j);
            }
        }
        return new_;
    }

    @Override
    public String toString(){
        StringBuilder info = new StringBuilder(width * height * 10);
        info.append("P3 " + width + " " + height + " " + maxColor + " ");
        for(int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                info.append(pixels[row][column].toString() + " ");
            }
            info.append("\n");
        }
        return info.toString();
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getMaxColor() {
        return maxColor;
    }
    public void setMaxColor(int maxColor) {
        this.maxColor = maxColor;
    }
}
