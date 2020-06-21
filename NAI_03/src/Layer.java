import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.List;

public class Layer {

    private Perceptron[] perceptrons;
    private List<Vector> inputVectorList;

    public Layer() {
        this.perceptrons = new Perceptron[Perceptron.getAmmountOfLanguages()];
        for(int i = 0; i < perceptrons.length; i++) {
            perceptrons[i] = new Perceptron();
        }
        inputVectorList = Perceptron.getInputVectorList("Train");
        setDeafultLanguage2();
    }

    public void trainPerceptrons( double alpha) {
        Collections.shuffle(inputVectorList);
        for(int i = 0; i < perceptrons.length; i++) {
            for(int j = 0; j < inputVectorList.size(); j++) {
                perceptrons[i].train(inputVectorList.get(j), alpha);
            }
        }
    }

    public String pickLanguage(Vector inputVector) {
        double[] scalarProducts = new double[Perceptron.getAmmountOfLanguages()];
        for(int i = 0; i < perceptrons.length; i++) {
            scalarProducts[i] = inputVector.countNet(perceptrons[i].getWeightVec());
        }
        int indexOfLargestScalar = getIndexOfLargest(scalarProducts);
        return "Ustalony Jezyk to: " + perceptrons[indexOfLargestScalar].getLanguage() + ", Jezyk wektora to " + inputVector.getLanguage();
    }

    public static int getIndexOfLargest( double[] array ) {
        int largest = 0;
        for ( int i = 1; i < array.length; i++) {
            if ( array[i] > array[largest] )
                largest = i;
        }
        return largest;
    }

    public Perceptron[] getPerceptrons() {
        return perceptrons;
    }

    public List<Vector> getInputVectorList() {
        return inputVectorList;
    }

    public void setDeafultLanguage2() {
        for(int i = 0; i < perceptrons.length; i++) {
            File file = new File("Train");
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory();
                }
            });
            assert files != null;
            perceptrons[i].setLanguage(files[i].getName());
        }

    }
}
