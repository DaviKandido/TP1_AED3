
package visao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entidades.Serie;
import entidades.Episodio;
import modelo.ArquivoSeries;
import modelo.ArquivoEpisodios;

public class MenuSeries {
    ArquivoSeries arqSeries = new ArquivoSeries();
    ArquivoEpisodios arqEpisodios = new ArquivoEpisodios();
    private static Scanner console = new Scanner(System.in);

    public MenuSeries() throws Exception {
        arqSeries = new ArquivoSeries();
        arqEpisodios = new ArquivoEpisodios();
    }

    public void menu() throws Exception{
        int opcao;
        do {
            System.out.println("\n\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início > Séries");
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
                    incluirSerie();
                    break;
                case 2:
                    buscarSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirSerie() {
    System.out.println("\nInclusão de Série");
    
    String nome = "";
    LocalDate ano = LocalDate.now();
    String sinopse = "";
    String streaming = "";
    String genero = " ";
    String classind = " ";

    boolean dadosCorretos = false;
    int anoAtual = LocalDate.now().getYear();

    // Validação do nome
    do {
        System.out.print("Nome da série (min. 4 letras): ");
        nome = console.nextLine();
        if (nome.length() >= 4) {
            dadosCorretos = true;
        } else {
            System.err.println("O nome da série deve ter no mínimo 4 caracteres.");
        }
    } while (!dadosCorretos);


     do {
        System.out.print("genero da série (min. 4 letras): ");
        genero = console.nextLine();
        if (nome.length() >= 4) {
            dadosCorretos = true;
        } else {
            System.err.println("O genero da série deve ter no mínimo 4 caracteres.");
        }
    } while (!dadosCorretos);

    do {
        System.out.print("classificação indicada da série (min. 2 letras): ");
        genero = console.nextLine();
        if (classind.length() >= 2) {
            dadosCorretos = true;
        } else {
            System.err.println("A classificação indicada da série deve ter no mínimo 2 caracteres.");
        }
    } while (!dadosCorretos);


    // Validação do ano de lançamento
    dadosCorretos = false;
    do {
        System.out.print("Ano de lançamento (entre 1900 e " + anoAtual + "): ");
        if (console.hasNextInt()) {
            ano = LocalDate.parse(console.nextLine() + "-01-01" );

            int anoDigitado = ano.getYear(); // Extrai o ano
            if (anoDigitado >= 1900 && anoDigitado <= anoAtual)
            {
                dadosCorretos = true;
            } else {
                System.err.println("Ano inválido! Insira um ano entre 1900 e " + anoAtual + ".");
            }
        } else {
            System.err.println("Entrada inválida! Insira um ano numérico.");
            console.next(); // Descartar entrada inválida
        }
    } while (!dadosCorretos);
    console.nextLine(); // Limpar o buffer

    // Validação da sinopse
    dadosCorretos = false;
    do {
        System.out.print("Sinopse (min. 10 letras): ");
        sinopse = console.nextLine();
        if (sinopse.length() >= 10) {
            dadosCorretos = true;
        } else {
            System.err.println("A sinopse deve ter no mínimo 10 caracteres.");
        }
    } while (!dadosCorretos);

    // Validação do streaming
    dadosCorretos = false;
    do {
        System.out.print("Streaming (min. 3 letras): ");
        streaming = console.nextLine();
        if (streaming.length() >= 3) {
            dadosCorretos = true;
        } else {
            System.err.println("O nome do streaming deve ter no mínimo 3 caracteres.");
        }
    } while (!dadosCorretos);

    System.out.print("\nConfirma a inclusão da série? (S/N) ");
    char resp = console.nextLine().charAt(0);
    if (resp == 'S' || resp == 's') {
        try {
            Serie s = new Serie(-1, nome, ano, sinopse, streaming, genero, classind);
            arqSeries.create(s);
            System.out.println("Série incluída com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao incluir série.");
        }
    }
}



    public void buscarSerie() {
        System.out.println("\nBusca de Série por ID");
        System.out.print("ID: ");
        int id = console.nextInt();

        try {
            Serie serie = arqSeries.read(id);
            if (serie != null) {
                mostraSerie(serie);
            } else {
                System.out.println("Série não encontrada.");
            }
        } catch(Exception e) {
            System.out.println("Erro ao buscar série.");
        }
    }

 

    public void alterarSerie() throws Exception {
        System.out.println("\nAlteração de Série");
        System.out.print("ID da série: ");
        int id = console.nextInt();

        try {
            Serie serie = arqSeries.read(id); 
            if (serie != null)
             {
                mostraSerie(serie);
                
                System.out.print("Novo nome (ou Enter para manter): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) {
                    serie.setNome(novoNome);
                }
                
                System.out.print("Novo ano de lançamento (ou Enter para manter): ");
                LocalDate ano = LocalDate.parse(console.nextLine());
                if (ano.lengthOfYear() == 0) {
                    serie.setAnoLancamento(ano);
                }

                System.out.print("Nova sinopse (ou Enter para manter): ");
                String novaSinopse = console.nextLine();
                if (!novaSinopse.isEmpty()) {
                    serie.setSinopse(novaSinopse);
                }
                
                System.out.print("Novo streaming (ou Enter para manter): ");
                String novoStreaming = console.nextLine();
                if (!novoStreaming.isEmpty()) {
                    serie.setStreaming(novoStreaming);
                }

                System.out.print("Novo genero (ou Enter para manter): ");
                String novogenero = console.nextLine();
                if (!novoStreaming.isEmpty()) {
                    serie.setGenero(novogenero);
                }

                System.out.print("Nova classificação indicada (ou Enter para manter): ");
                String novoclassind = console.nextLine();
                if (!novoclassind.isEmpty()) {
                    serie.setClassIndicativa(novoclassind);
                }
                
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.nextLine().charAt(0);
                
                if (resp == 'S' || resp == 's') {
                    boolean alterado = arqSeries.update(serie);
                    if (alterado) {
                        System.out.println("Série alterada com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar a série.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Série não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar série.");
        }
    }
   
    public void excluirSerie() throws Exception{
        System.out.println("\nExclusão de Série");
        System.out.print("ID: ");
        int id = console.nextInt();
        System.out.print("Nome: ");
        String nome = console.nextLine();

        try {
            Episodio[] episodios = arqEpisodios.readEpisodiosSerie(id);
            if (episodios != null)
            {
                System.out.print("Essa série possui episódios vinculados, você deseja excluir mesmo assim? (S/N)");
                char resposta = console.nextLine().charAt(0);

                if(resposta == 's' || resposta == 'S')
                {
                    boolean excluido = arqSeries.delete(nome,id);
                    if (excluido) {
                        System.out.println("Série excluída com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir a série.");
                    }

                }
                else
                {
                    System.out.println("A série nao foi excluída");
                }
               
            }
            else
            {
            
                boolean excluido = arqSeries.delete(id);
                if (excluido) {
                    System.out.println("Série excluída com sucesso.");
                } else {
                    System.out.println("Erro ao excluir a série.");
                }

            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir série.");
        }
    }

    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println("----------------------");
            System.out.printf("Nome....: %s%n", serie.getNome());
            System.out.printf("Ano lançamento: %s%n", serie.getAnoLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Sinopse....: %s%n", serie.getSinopse());
            System.out.printf("Streaming.....: %s%n", serie.getStreaming());
            System.out.printf("Gênero.....: %s%n", serie.getGenero());
            System.out.printf("Classificação indicada.....: %s%n", serie.getClassIndicativa());
            System.out.println("----------------------");
        }
    }

}
