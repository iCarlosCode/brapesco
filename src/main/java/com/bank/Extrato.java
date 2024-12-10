import java.util.Stack;

public class Extrato {
    private Stack<Operacao> operacoes;

    public Extrato() {
        this.operacoes = new Stack<>();
    }

    public void adicionarOperacao(Operacao operacao) {
        operacoes.push(operacao);
    }

    public void mostrarExtrato() {
        if (operacoes.isEmpty()) {
            System.out.println("Extrato vazio.");
            return;
        }
        
        System.out.println("Extrato Bancário");
        System.out.println("Data\t\tHora\t\tTipo\t\tValor(R$)\t\tRemetente\t\tDestinatário");
        Stack<Operacao> tempStack = new Stack<>();
        while (!operacoes.isEmpty()) { //desemplilhando as operações 
            Operacao operacao = operacoes.pop();
            System.out.println(operacao);
            tempStack.push(operacao); //empilhando as operações na pilha temporária
        }
        while (!tempStack.isEmpty()) { //reempilhando as operações
            operacoes.push(tempStack.pop());
        }
    }
}