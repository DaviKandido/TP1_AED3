package modelo;
import entidades.Episodio;

import java.util.ArrayList;

import aeds3.*;

//Ideia printar a avaliação da serie, que será a media das avaliações dos seus episodios

public class ArquivoEpisodio extends Arquivo<Episodio> {

  Arquivo<Episodio> arqEpisodio;
  ArvoreBMais <ParIdId> indiceIdEpisodio_IdSerie;
  ArvoreBMais <ParTituloId> indiceNomeEpisodio;
  HashExtensivel<ParIDEndereco> indiceIdEpisodio;

  public ArquivoEpisodio() throws Exception {
    super("episodio", Episodio.class.getConstructor());

    indiceIdEpisodio_IdSerie = new ArvoreBMais<>(
      ParIdId.class.getConstructor(),
      1,
      "./dados/"+nomeEntidade+"/indiceIdEpisodios_IdSerie.d.db"
    );

    indiceNomeEpisodio = new ArvoreBMais<>(
      ParTituloId.class.getConstructor(),
      5,
      "./dados/"+nomeEntidade+"/indiceNomeEpisodios.d.db"
    );

    indiceIdEpisodio = new HashExtensivel<>(
      ParIDEndereco.class.getConstructor(),
      4,
      "./dados/"+nomeEntidade+"/indiceIdEpisodios.d.db",
      "./dados/"+nomeEntidade+"/indiceIdEpisodios.c.db"
    );
  }

  @Override
  public int create(Episodio e) throws Exception{

    // Metodo para verificar se a serie vinculada ao episodio existe 
    // **A ser implementado na classe episodio**
    if(SerieExiste(e.getID_serie()) == false){
      throw new Exception("Episodio nao pode ser criado pois a serie vinculada não existe");
    }

    int id = super.create(e);
    
    indiceIdEpisodio_IdSerie.create(new ParIdId(id, e.getID_serie()));
    indiceNomeEpisodio.create(new ParTituloId(e.getNome(), id));

    return id;

  }
  

}
