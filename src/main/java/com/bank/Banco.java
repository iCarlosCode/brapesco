import java.util.*;

public class Banco {
    private static List<Conta> contas = new ArrayList<>();
    private static List<Agencia> agencias = new ArrayList<>();
    private static Queue<Transferencia> filaTransferencias = new LinkedList<>();
    private static int sizeTracking = 0; 
    private static int sizeAgenciasTracking = 0; 
    private static final int QTD_ACCOUNTS = 10;

    public static void adicionarTransferenciaNaFila(int numeroOrigem, Scanner scanner) {
        System.out.print("Digite o número da conta de destino: ");
        int numeroDestino = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        filaTransferencias.add(new Transferencia(numeroOrigem, numeroDestino, valor));
        System.out.println("Processando transferência.....");
        processarTransferencias();
    }

    public static void processarTransferencias() {
        System.out.println("Iniciando processamento de transferências.....");

        while (!filaTransferencias.isEmpty()) {
            Transferencia transferencia = filaTransferencias.poll(); //puxando o primeiro elemento da fila
            Conta origem = Banco.buscarContaBinariamente(transferencia.getNumeroOrigem());
            Conta destino = buscarContaBinariamente(transferencia.getNumeroDestino());

            if (origem == null || destino == null) {
                System.out.println("Transferência inválida: Conta origem ou destino não encontrada.");
                continue;
            }
            if(origem.getNumero() == destino.getNumero()){
                System.out.println("Transferência inválida: Conta de origem e destino são iguais.");
                continue;
            }

            // if (!origem.sacar(transferencia.getValor())) {
            //     System.out.println("Transferência inválida: Não foi possível realizar a transferencia");
            //     continue;
            // }
            if (origem.getSaldo() < transferencia.getValor()) {
                System.out.println("Transferência inválida: Não foi possível realizar a transferencia");
                continue;
            } else if(transferencia.getValor() <= 0){
                System.out.println("Transferência inválida");
                continue;
            }

            //destino.depositar(transferencia.getValor()); 
            
            destino.setSaldo(destino.getSaldo() + transferencia.getValor());//dando dinheiro pro destinatario

            //tirando do remetente
            origem.setSaldo(origem.getSaldo() - transferencia.getValor());

            origem.adicionarOperacaoExtrato("Tran. Env", transferencia.getValor(), "Você", destino.getTitular());
            destino.adicionarOperacaoExtrato("Tran. Rec", transferencia.getValor(), origem.getTitular(), "Você");

            System.out.println("Transferência concluída: " + transferencia);
            origem.mostrarExtrato();
        }
    }
        
    protected static void criarContasIniciais() { //ok
        agencias.add(new Agencia(sizeAgenciasTracking++));
        
        for (int i = 1; i <= QTD_ACCOUNTS; i++) {
            Conta conta = new Conta(i, "123", 100,"Fulano");
            contas.add(conta);

            agencias.get(0).getContas().add(conta);
        }
    }
    protected static void criarAgencias() { 
        agencias.add(new Agencia(sizeAgenciasTracking++));
        System.out.println("Agencia " + agencias.get(sizeAgenciasTracking-1).getCode() + " foi criada com sucesso!");
    }
    
    protected static void listarAgencias() { 
        System.out.println("Banco");
        for (Agencia agencia : agencias) {
            System.out.println("    " + agencia.getCode());
        }
    }

    protected static void criarConta(Scanner scanner) { //ok
        int numero = 20240 + sizeTracking + 1;
        sizeTracking++;
        int agencia;
        System.out.print("Digite o nome do Titular da conta: ");
        String titular = scanner.nextLine();
        
        System.out.print("Digite a senha da conta: ");
        String senha = scanner.nextLine();
        Conta conta = new Conta(numero, senha, titular);
        contas.add(conta);

        while (true) {
            System.out.print("Digite o número da agência: ");
            agencia = scanner.nextInt();
        
            if (agencia >= 0 && agencia <= sizeAgenciasTracking) {
                agencias.get(agencia).getContas().add(conta);
                break;
            }
            else {
                System.out.print("Nenhuma agência com esse número foi encontrada!");
            }
        }

        System.out.println("Conta criada com sucesso! Número da conta: " + numero);
    }
    
    protected static void editarConta(Scanner scanner) { //método ok
        System.out.println("\n=== Editar Conta ===");
        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // evitar o maldito do bug de pular linha

        Conta conta = buscarContaLinearmente(numeroConta);

        if (conta != null) {
            System.out.print("Digite a senha da conta: ");
            String senha = scanner.nextLine();

            if(conta.getSenha().equals(senha)){
                System.out.println("Conta encontrada. Digite os novos dados.");
                System.out.print("Digite o novo nome do Titular da conta: ");
                String novoTitular = scanner.nextLine();

                System.out.print("Digite a nova senha da conta: ");
                String novaSenha = scanner.nextLine();

                conta.setSenha(novaSenha);
                conta.setTitular(novoTitular);
                System.out.println("Dados da conta atualizados com sucesso!");

            } else {
                System.out.println("Conta ou senha inválidos.");
            }

        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    protected static void deletarConta(Scanner scanner) { //OK
        System.out.println("\n=== Deletar Conta ===");
        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();

        Conta conta = buscarContaLinearmente(numeroConta);

        if (conta == null) {
            System.out.println("Conta não encontrada.");
        
        } else if(conta.getSaldo() <= 0){
            for (int i = 0; i < sizeAgenciasTracking; i++) {
                Agencia agencia = agencias.get(i);
                for (int j = 0; j < agencia.getContas().size(); j++) {
                    Conta c = agencia.getContas().get(j);
                    if (c.getNumero() == numeroConta) {
                        agencia.getContas().remove(j);
                    }
                }
            }

            contas.remove(conta);
            System.out.println("Conta deletada com sucesso!");
        } else {
            System.out.println("Para deletar a conta, saque os R$" + conta.getSaldo() + " restantes.");
        }
    }

    protected static void listarContas() { // ok
        System.out.println("\n=== Lista de Contas ===");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            System.out.printf("%-20s %-10s %-10s%n", "Titular", "Conta", "Saldo");
            System.out.println("----------------------------------------------------");
            for (Conta conta : contas) {
                System.out.printf("%-20s %-10d %-10.2f%n", conta.getTitular(), conta.getNumero(), conta.getSaldo());
            }
        }
    }
    
    protected static void listarAgenciasEContas() { // ok
        System.out.println("\n=== Lista de Agencias e Contas ===");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            System.out.println("Banco");
            for (Agencia agencia: agencias) {
                System.out.println("    Agência: " + agencia.getCode());
                
                for (Conta conta: agencia.getContas()) {
                    System.out.println("        Conta: " + conta.getNumero());
                }
            }
        }
    }

    protected static Conta buscarContaLinearmente(int numeroConta) { // ok método de busca linear
        for (Conta conta : contas) {
            if (conta.getNumero() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    protected static Conta buscarContaBinariamente(int numeroConta) { //ok método de busca binária
        //contas.sort(Comparator.comparingInt(Conta::getNumero));
        int esquerda = 0;
        int direita = contas.size() - 1;

        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;
            Conta conta = contas.get(meio);

            if (conta.getNumero() == numeroConta) {
                return conta;
            } else if (conta.getNumero() < numeroConta) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return null;
    }

    protected static Conta detalharContaBuscaLinear(Scanner scanner) { //ok
        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();
    
        // Captura o tempo antes da execução
        long inicio = System.nanoTime();
    
        Conta conta = buscarContaLinearmente(numeroConta);
    
        // Captura o tempo após a execução
        long fim = System.nanoTime();
    
        // Calcula o tempo de execução em milissegundos
        long tempoExecucao = (fim - inicio) / 1_000_000; // Convertendo de nanosegundos para milissegundos
    
        if (conta != null) {
            System.out.printf("%-20s %-10s %-10s%n", "Titular", "Conta", "Saldo");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-20s %-10d %-10.2f%n", conta.getTitular(), conta.getNumero(), conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    
        System.out.println("Tempo de execução (ms): " + tempoExecucao + " ms");
        System.out.println("Tempo de execução (ns): " + (fim - inicio) + " ns");
        return conta;
    }
    
    protected static Conta detalharContaBuscaBinaria(Scanner scanner) { //ok
        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();
    
        // Captura o tempo antes da execução
        long inicio = System.nanoTime();
    
        Conta conta = buscarContaBinariamente(numeroConta);
    
        // Captura o tempo após a execução
        long fim = System.nanoTime();
    
        // Calcula o tempo de execução em milissegundos
        long tempoExecucao = (fim - inicio) / 1_000_000; // Convertendo de nanosegundos para milissegundos
    
        if (conta != null) {
            System.out.printf("%-20s %-10s %-10s%n", "Titular", "Conta", "Saldo");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-20s %-10d %-10.2f%n", conta.getTitular(), conta.getNumero(), conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    
        // Exibe o tempo de execução
        System.out.println("Tempo de execução (ms): " + tempoExecucao + " ms");
        System.out.println("Tempo de execução (ns): " + (fim - inicio) + " ns");

        return conta;
    }
}
