import QEA.Qall;
import readYml.InputFile;

public class Application_Qbit {
    public static void main(String[] args) {
        Qall qall=new Qall();
        qall.q_bitStart(InputFile.load());
    }
}
