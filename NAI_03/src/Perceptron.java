import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    private String language;
    private Vector weightVec;
    private double t;

    //przypisanie jezyka perceptronowi w momencie najwiekszego net
    public Perceptron() {
        this.weightVec = new Vector();
        this.t = 0;
    }

    public double finalNet(Vector inputVec) {
        return this.weightVec.countNet(inputVec) - t;
    }

    public static List<Vector> getInputVectorList(String path) {
        List<Vector> inputVectorList = new ArrayList<>();

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles((dir, name) -> !name.endsWith(".DS_Store"));
        int i = 0;
        assert listOfFiles != null;
        for (File subFolder : listOfFiles) {
            File[] listOfTexts = subFolder.listFiles();
            for(File textFile : listOfTexts) {
                if (textFile.isFile()) {
                    inputVectorList.add(Vector.loadVectorFromFile(textFile.getPath()));
                    inputVectorList.get(i).setLanguageFromPath(textFile.getPath());
                    //System.out.println(inputVectorList.get(i));
                    i++;
                }
            }
        }

        return inputVectorList;
    }

    public static int getAmmountOfLanguages() {
        File file = new File("Train");
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        });
        assert files != null;
        return files.length;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    public String getLanguage() {
        return this.language;
    }

    public int predict(Vector inputVec) {
       return finalNet(inputVec) > 0 ? 1 : 0;
    }

    public void train(Vector inputVector, double alpha) {
        int expectedClass = 0;
        if(inputVector.getLanguage().equals(this.getLanguage()))
             expectedClass = 1;
        this.adjustWeightVec(expectedClass, predict(inputVector), alpha, inputVector);
    }

    public void adjustWeightVec(int expectedClass, int actualClass, double alpha, Vector inputVec) {
        this.weightVec = this.weightVec.addVector(inputVec.multiply((expectedClass - actualClass) * alpha));
        this.t = this.t + (expectedClass - actualClass) * alpha * -1;
    }

    public Vector getWeightVec() {
        return weightVec;
    }
}
