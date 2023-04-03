import GA.GenAlgorithm;
import readYml.InputFile;
import util.WorkStation;

public class Application {
    public static void main(String[] args) {
        GenAlgorithm genAlgorithm = new GenAlgorithm();
        genAlgorithm.genStart(InputFile.load());
    }
}
