import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Vector {

    private double[] letterCountArray;
    private String language;

    public Vector() {
        this.letterCountArray = new double[26];
    }

    public Vector(double[] letterCountArray) {
        this.letterCountArray = letterCountArray.clone();
        this.normalize();
        this.language = "";
    }

    //matematyczne metody
    public void setIArg(int index, double arg) {
        this.letterCountArray[index] = arg;
    }

    public double getIArg(int index) {
        return letterCountArray[index];
    }

    public double getLength() {
        double length = 0;
        for(int i = 0; i < this.letterCountArray.length; i++) {
            length += this.letterCountArray[i] * this.letterCountArray[i];
        }

        return Math.sqrt(length);
    }

    public Vector multiply(double arg) {
        Vector result = new Vector(this.letterCountArray);
        for(int i = 0; i < this.letterCountArray.length; i++) {
            result.setIArg(i, this.letterCountArray[i] * arg);
        }
        return result;
    }

    public Vector addVector(Vector vector) {
        Vector result = new Vector();
        for(int i = 0; i < this.letterCountArray.length; i++) {
            result.setIArg(i, this.getIArg(i) + vector.getIArg(i));
        }
        return result;
    }

    public double countNet(Vector vector) {
        double netProduct = 0;
        for(int i = 0; i < this.letterCountArray.length; i++) {
            netProduct += this.letterCountArray[i] * vector.letterCountArray[i];
        }
        return netProduct;
    }

    public void normalize() {
        double length = getLength();
        length = length > 0 ? length : 1;
        for(int i = 0; i < this.letterCountArray.length; i++) {
            this.setIArg(i,this.getIArg(i)/length);
        }
    }

    //metody zwiazane z jezykiem
    public String getLanguage() {
        return this.language;
    }

    protected static String getTextFromFile(String path) {
        String text = "";
        try {
            text = Files.readString(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    protected static double[] countCharacters(String text) {
        double[] repeats = new double['z' - 'a' + 1];
        int index = 0;
        for(char c = 'a'; c <= 'z'; c++) {
            char chara = c;
            long countChar = text.chars().filter(tmp -> tmp == chara).count();
            repeats[index] =  countChar;
            index++;
        }
        return repeats;
    }

    public static Vector loadVectorFromFile(String path) {
        double[] countChars = new double[26];
        countChars = countCharacters(getTextFromFile(path));

        return new Vector(countChars);
    }

    public void setLanguageFromPath(String path) {
        File f = new File(path);
        this.language = f.getParentFile().getName();
    }

    @Override
    public String toString() {
        return "Vector{" +
                "letterCountArray=" + Arrays.toString(letterCountArray) +
                ", language='" + language + '\'' +
                '}';
    }
}
