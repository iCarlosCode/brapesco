import java.util.*;


public class SistemaBancario {

    private static final String GERENTE_PIN = "2020107581";
    //Banco banco = new Banco();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executar = true;
        Banco.criarContasIniciais();
        clearConsole();

        while (executar) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Login como Gerente");
            System.out.println("2. Login como Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:

                    loginGerente(scanner);
                    break;
                case 2:

                    loginCliente(scanner);
                    break;
                case 0:
                    executar = false;
                    System.out.println("Encerrando o sistema...");
                    break;
                default:

                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }



    private static void loginGerente(Scanner scanner) { // ok
        System.out.print("Digite o PIN do gerente: ");
        String pin = scanner.nextLine();

        if (GERENTE_PIN.equals(pin)) {
            System.out.println("Bem-vindo, Gerente!");
            menuGerente(scanner);
        } else {
            System.out.println("PIN inválido.");
        }
    }

    private static void loginCliente(Scanner scanner) { //ok
        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a senha da conta: ");
        String senha = scanner.nextLine();

        Conta conta = Banco.buscarContaLinearmente(numeroConta);

        if (conta != null && conta.getSenha().equals(senha)) {
            System.out.println("Login realizado com sucesso!");
            menuCliente(scanner, conta);
        } else {
            System.out.println("Número da conta ou senha inválidos.");
        }
    }


    private static void menuGerente(Scanner scanner) {
        boolean voltar = false;
        clearConsole();
        while (!voltar) {
            System.out.println("\n=== Menu do Gerente ===");
            System.out.println("1. Criar Conta");
            System.out.println("2. Listar Contas");
            System.out.println("3. Editar Conta");
            System.out.println("4. Deletar Conta");
            System.out.println("5. Detalhar conta (Busca Linear)");
            System.out.println("6. Detalhar conta (Busca Binária)");
            System.out.println("7. Listar árvore agência-contas");
            System.out.println("8. Criar agência");
            System.out.println("9. Listar agências");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:

                    Banco.criarConta(scanner);
                    break;
                case 2:

                    Banco.listarContas();
                    break;
                case 3:
                    Banco.editarConta(scanner);
                    break;
                case 4:
                    Banco.deletarConta(scanner);
                    break;
                case 5:
                    Banco.detalharContaBuscaLinear(scanner);
                    break;
                case 6:
                    Banco.detalharContaBuscaBinaria(scanner);
                    break;
                case 7:
                    Banco.listarAgenciasEContas();
                    break;
                case 8:
                    Banco.criarAgencias();
                    break;
                case 9:
                    Banco.listarAgencias();
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    



    private static void menuCliente(Scanner scanner, Conta conta) {
        boolean voltar = false;
        clearConsole();

        while (!voltar) {
            System.out.println("\n=== Menu do Cliente ===");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Consultar Saldo");
            System.out.println("4. Extrato");
            System.out.println("5. Transferir");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor para depósito: ");
                    double deposito = scanner.nextDouble();
                    conta.depositar(deposito);
                    break;
                case 2:
                    System.out.print("Digite o valor para saque: ");
                    double saque = scanner.nextDouble();
                    if (conta.sacar(saque)) {
                        System.out.println("Saque realizado com sucesso!");
                    } else {
                        System.out.println("Não foi possível realizar essa operação.");
                    }
                    break;
                case 3:
                    System.out.printf("Saldo atual: R$%.2f%n", conta.getSaldo());
                    break;
                case 4:
                    conta.mostrarExtrato();
                    break;
                case 5:
                    Banco.adicionarTransferenciaNaFila(conta.getNumero(), scanner);
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    public final static void clearConsole()
    {
        try
        {
            if (System.getProperty("os.name").contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
