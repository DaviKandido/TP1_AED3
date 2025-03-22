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

    public void povoar() throws Exception {

        // Serie de id 1
        arqEpisodios.create(new Episodio( "Piloto",  1, LocalDate.of(2008, 1, 20), 58, 9.5f, false, "Walter White toma uma decisão que muda sua vida.", 1));
        arqEpisodios.create(new Episodio( "Gato Sai do Saco", (short) 1, LocalDate.of(2008, 1, 27), 48, 8.7f, false, "As consequências da primeira venda aparecem.", 1));

        // Serie de id 2
        arqEpisodios.create(new Episodio( "Desaparecimento de Will", 1, LocalDate.of(2016, 7, 15), 47, 8.9f, false, "Will desaparece misteriosamente.", 2));
        arqEpisodios.create(new Episodio( "Estranha da Maple Street", 1, LocalDate.of(2016, 7, 15), 55, 9.1f, false, "Mike e seus amigos encontram uma garota misteriosa.", 2));

        // Serie de id 3
        arqEpisodios.create(new Episodio( "Winter is Coming", 1, LocalDate.of(2011, 4, 17), 61, 9.0f, false, "Os Stark recebem o rei Robert Baratheon.", 3));

        // Serie de id 4
        arqEpisodios.create(new Episodio( "Princípio do Fim", 1, LocalDate.of(2019, 12, 20), 60, 8.5f, false, "Geralt enfrenta sua primeira batalha.", 4));
        arqEpisodios.create(new Episodio( "Quatro Marcas", 1, LocalDate.of(2019, 12, 20), 59, 8.7f, false, "Yennefer descobre seu verdadeiro poder.", 4));

        // Serie de id 5
        arqEpisodios.create(new Episodio( "A Chegada", 1, LocalDate.of(2019, 7, 26), 60, 9.0f, false, "Billy Butcher apresenta Hughie aos The Boys.", 5));

        // Serie de id 6
        arqEpisodios.create(new Episodio( "Segredos", 1, LocalDate.of(2017, 12, 1), 53, 8.9f, false, "Jonas começa a desvendar os mistérios de Winden.", 6));

        // Serie de id 7
        arqEpisodios.create(new Episodio( "Apostando Alto", 1, LocalDate.of(2013, 9, 12), 55, 9.2f, false, "Tommy Shelby expande seu império.", 7));

        // Serie de id 8
        arqEpisodios.create(new Episodio( "O Legado Targaryen", 1, LocalDate.of(2022, 8, 21), 66, 8.7f, false, "Rei Viserys enfrenta um dilema sucessório.", 8));

        // Serie de id 9
        arqEpisodios.create(new Episodio( "Renascimento", 1, LocalDate.of(2016, 10, 2), 57, 9.0f, false, "Os anfitriões começam a questionar sua realidade.", 9));
        
        // Serie de id 10
        arqEpisodios.create(new Episodio( "Volto Já", 1, LocalDate.of(2011, 12, 4), 44, 8.6f, false, "Uma mulher tenta reviver seu namorado morto usando IA.", 10));
    }
}
