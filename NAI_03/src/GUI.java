import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class GUI extends Stage {

    public GUI() {
        setTitle("Klasyfikacja jezyka");
        setHeight(500);
        setWidth(800);

        Layer layer = new Layer();
        for(int i = 0; i < 5000; i++) {
            layer.trainPerceptrons(0.2);
        }

        for (Perceptron perceptron : layer.getPerceptrons()) {
            System.out.println("Jezyk-" + perceptron.getLanguage() + " -> " + perceptron.getWeightVec());
        }

        List<Vector> testVectors = Perceptron.getInputVectorList("Test");
        for(Vector testVector : testVectors) {
            System.out.println(layer.pickLanguage(testVector));
        }

        BorderPane mainPane = new BorderPane();

        Button startBUtton = new Button("Wykryj jezyk");
        TextArea textArea = new TextArea("Wprowadz text tutaj");

        Label resultLabel = new Label("Tu pokaze sie wynik");

        mainPane.setTop(startBUtton);
        mainPane.setCenter(textArea);
        mainPane.setBottom(resultLabel);

        startBUtton.setOnAction(e -> {
                    double[] inputTextCharArray = Vector.countCharacters(textArea.getText());
                    resultLabel.setText(layer.pickLanguage(new Vector(inputTextCharArray)));
                }
        );

        setScene(new Scene(mainPane));

        textArea.selectAll();
        textArea.requestFocus();
    }
}
