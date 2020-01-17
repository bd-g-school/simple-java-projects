package editor;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.ceil;

public class ImageEditor {
    private static final String BASE_DIR = "C:/Users/Brent/IdeaProjects/ImageEditor/";
    private static final String SOURCE_IMAGE = "source_images/";
    private static final String OUT = "out_images/";
    public static void main(String args[]){

        //System.out.println(args.length == 3 ? args[0] + args[1] + args[2]: args[0] + args[1] + args[2] + args[3]);
        File outputFile = new File(args[1]);

        PPMImage raw_input = null;
        try {
            raw_input = processFile(new File(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(raw_input);

        switch (args[2]){
            case "invert":
                raw_input.invert();
                break;
            case "grayscale":
                raw_input.grayscale();
                break;
            case "emboss":
                raw_input.emboss();
                break;
            case "motionblur":
                int blurNum = Integer.parseInt(args[3]);
                PPMImage tmp = raw_input.motionBlur(blurNum);
                raw_input = tmp;
                break;
            default:
                throw new IllegalArgumentException();
        }

        //System.out.println(raw_input);

        writeToFile(raw_input, outputFile);

    }

    public static PPMImage processFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        String returnString = "";
        PPMImage raw_image = null;

        var title = scanner.next();
        if (title.equals("P3")){
            raw_image = new PPMImage(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
            raw_image.setMaxColor(scanner.nextInt());
        }
        else {
            throw new IOException();
        }

        for(int row = 0; row < raw_image.getHeight(); row++){
            for(int column = 0; column < raw_image.getWidth(); column++){
                raw_image.addPixel(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), row, column);
            }
        }

        return raw_image;
    }

    public static void writeToFile(PPMImage image, File outputFile) {
        FileWriter fr = null;
        try {
            fr = new FileWriter(outputFile);
            fr.write(image.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
