import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operacao {
    private String tipo; // Ex: "Depósito", "Saque", "Transferência"
    private double valor;
    private LocalDateTime dataHora;
    private String remetente;
    private String destinatario;

    

    public Operacao(String tipo, double valor, String remetente, String destinatario) {
        this.tipo = tipo;
        this.valor = valor;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.dataHora = LocalDateTime.now();
    }

    // Getters e Setters
    public String getRemetente() {
        return remetente;
    }


    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%s\t%s\t%-15s\t%10.2f\t%15s\t%25s", 
            dataHora.format(dateFormatter), 
            dataHora.format(timeFormatter), 
            tipo, 
            valor, 
            remetente, 
            destinatario
        );
    }
    
    
}
