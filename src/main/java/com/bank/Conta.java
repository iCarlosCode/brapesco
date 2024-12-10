// Classe Conta
class Conta {
    private int numero;
    private String senha;
    private double saldo;
    private String titular;
    private Extrato extrato;

    public Conta(int numero, String senha, String titular) {
        this.titular = titular;
        this.numero = numero;
        this.senha = senha;
        this.saldo = 0;
        this.extrato = new Extrato();
    }


    public Conta(int numero, String senha, double saldo, String titular) {
        this.titular = titular;
        this.numero = numero;
        this.senha = senha;
        this.saldo = saldo;
        this.extrato = new Extrato();
    }


    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Insira um valor positivo para depositar!");
            return;
        }
        saldo += valor;

        //Registro de operações
        adicionarOperacaoExtrato("Depósito", valor, "", "");

        System.out.println("Depósito realizado com sucesso!");
    }

    public boolean sacar(double valor) {
        if(valor < 0){
            System.out.println("");
            System.out.println("Insira apenas valores positivos!");
            return false;
        } else if(valor == 0){
            System.out.println("");
            System.out.println("Insira um valor maior que zero!");
            return false;
        }else if(valor > saldo){
            System.out.println("");
            System.out.printf("Saldo insuficiente! Você só pode sacar até R$%.2f%n", saldo);
            return false;

        }else if(saldo > valor) {
            saldo -= valor;
            //Registro de operações

            adicionarOperacaoExtrato("Saque", valor, "", "");
            return true;
        }
        return false;
    }
    public void adicionarOperacaoExtrato(String tipo, double valor, String remetente, String destinatario) {
        extrato.adicionarOperacao(new Operacao(tipo, valor, remetente, destinatario));
    }

    public void mostrarExtrato() {
        System.out.println("");
        extrato.mostrarExtrato();
        System.out.printf("\nSaldo atual: R$%.2f%n", saldo);
    }
}