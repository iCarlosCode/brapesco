class Transferencia {
    private int numeroOrigem;
    private int numeroDestino;
    private double valor;

    public Transferencia(int numeroOrigem, int numeroDestino, double valor) {
        this.numeroOrigem = numeroOrigem;
        this.numeroDestino = numeroDestino;
        this.valor = valor;
    }

    public int getNumeroOrigem() {
        return numeroOrigem;
    }

    public int getNumeroDestino() {
        return numeroDestino;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("Origem: %d, Destino: %d, Valor: %.2f", numeroOrigem, numeroDestino, valor);
    }
}