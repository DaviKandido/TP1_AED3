package visao;

import entidades.Episodio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import modelo.ArquivoEpisodios;

public class MenuEpisodios {
    
    ArquivoEpisodios arqEpisodios;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodios() throws Exception {
        arqEpisodios = new ArquivoEpisodios();
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início > Episódios");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("0) Retornar ao menu anterior");
            
            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirEpisodio() {
        System.out.println("\nInclusão de Episódio");
        String nome = "";
        int temporada = 0;
        LocalDate dataLancamento = LocalDate.now();
        int duracaoMinutos = 0;
        float avaliacao = 0F;
        boolean especial = false;
        String descricao = "";
        int id_serie = 0;

        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        dadosCorretos = false;
        do {
            System.out.print("Nome do episodio (min. de 4 letras): ");
            nome = console.nextLine();
            if(nome.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O Nome do episódio deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Temporada: ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Data de lançamento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            try {
                dataLancamento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        
        dadosCorretos = false;
        do {
            System.out.print("Duração em minutos (0-999): ");
            if (console.hasNextInt()) {
                duracaoMinutos = console.nextInt();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Avaliação do episodio (0-10): ");
            if (console.hasNextFloat()) {
                avaliacao = console.nextFloat();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("O episodio é especial? (S/N) ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's' || resp == 'N' || resp == 'n') {
                especial = (resp == 'S' || resp == 's');
                avaliacao = console.nextFloat();
                dadosCorretos = true;
            }else{
                System.err.println("Resposta inválida! Use S ou N.");
            }
            console.nextLine();
        } while(!dadosCorretos);

        do {
            System.out.print("Descrição do espisodio: ");
            descricao = console.nextLine();
            if(descricao.length()>=10)
                dadosCorretos = true;
            else
                System.err.println("A descricao do episódio deve ter no mínimo 10 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("A qual serie esta vinculada este episodio? (ID):");
            if (console.hasNextInt()) {
                id_serie = console.nextInt();
                dadosCorretos = true;
            }
            console.nextLine();
        } while(!dadosCorretos);

        System.out.print("\nConfirma a inclusão do episódio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Episodio e = new Episodio(nome, temporada, dataLancamento, duracaoMinutos, avaliacao, especial, descricao, id_serie);
                arqEpisodios.create(e);
                System.out.println("Episódio incluído com sucesso.");
            } catch(Exception ex) {
                System.out.println("Erro do sistema. Não foi possível incluir o episódio!");
            }
        }
    }

    public void buscarEpisodio() {
        System.out.println("\nBusca de episódio por ID");
        System.out.print("\nID: ");
        int id = console.nextInt();

        try {
            Episodio episodio = arqEpisodios.read(id);
            if (episodio != null) {
                mostraEpisodio(episodio);
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o episódio!");
        }
    }

    public void excluirEpisodio() {
        System.out.println("\nExclusão de episódio");
        System.out.print("\nID: ");
        int id = console.nextInt();

        try {
            Episodio episodio = arqEpisodios.read(id);
            if (episodio != null) {
                System.out.print("\nConfirma a exclusão do episódio? (S/N) ");
                char resp = console.next().charAt(0);
                console.nextLine();

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodios.delete(id);
                    if (excluido) {
                        System.out.println("Episódio excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o episódio.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o episódio!");
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de episódio");
        System.out.print("ID do episódio: ");
        int id = console.nextInt();

        try {
            Episodio episodio = arqEpisodios.read(id);
            if (episodio != null) {
                System.out.println("Episódio encontrado:");
                mostraEpisodio(episodio);

                System.out.print("Novo título (deixe em branco para manter o anterior): ");
                String novoTitulo = console.nextLine();
                if (!novoTitulo.isEmpty()) {
                    episodio.setNome(novoTitulo);
                }

                System.out.print("Nova duração em minutos (deixe em branco para manter o anterior): ");
                String novaDuracao = console.nextLine();
                if (!novaDuracao.isEmpty()) {
                    episodio.setDuracaoMinutos(Integer.parseInt(novaDuracao));
                }

                System.out.print("Nova data de lançamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaData = console.nextLine();
                if (!novaData.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    episodio.setDataLancamento(LocalDate.parse(novaData, formatter));
                }

                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                console.nextLine(); // Limpar buffer
                if (resp == 'S' || resp == 's') {
                    boolean alterado = arqEpisodios.update(episodio);
                    if (alterado) {
                        System.out.println("Episódio alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o episódio.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o episódio!");
            e.printStackTrace();
        }
    }
    
    public void mostraEpisodio(Episodio episodio) {
        if (episodio != null) {
            System.out.println("----------------------");
            System.out.printf("ID.........: $s%n" + episodio.getID());
            System.out.printf("Título.....: %s%n", episodio.getNome());
            System.out.printf("Temporada..: %d%n", episodio.getTemporada());
            System.out.printf("Número da Serie.....: %d%n", episodio.getID_serie());
            System.out.printf("Lançamento.: %s%n", episodio.getDataLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("----------------------");
        }
    }
}
