import java.util.ArrayList;
import java.util.List;

public class Agencia {
    private List<Conta> contas = new ArrayList<>();
    private String code;

    public Agencia(int n) {
        this.code = String.format("A%03d", n);
    }

    public String getCode() {
        return code;
    }
    
    public List<Conta> getContas() {
        return contas;
    }
}
