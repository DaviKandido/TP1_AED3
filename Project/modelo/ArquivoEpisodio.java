package modelo;
import entidades.Episodio;

import java.util.ArrayList;

import aeds3.*;

//Ideia printar a avaliação da serie, que será a media das avaliações dos seus episodios

public class ArquivoEpisodio extends Arquivo<Episodio> {

  Arquivo<Episodio> arqEpisodio;
  ArvoreBMais <ParIdId> indiceIdEpisodio_IdSerie;
  ArvoreBMais <ParTituloId> indiceNomeEpisodio;

  public ArquivoEpisodio() throws Exception {
    super("episodio", Episodio.class.getConstructor());

    indiceIdEpisodio_IdSerie = new ArvoreBMais<>(
      ParIdId.class.getConstructor(),
      1,
      "./dados/"+nomeEntidade+"/indiceIdEpisodios_IdSerie.db"
    );

    indiceNomeEpisodio = new ArvoreBMais<>(
      ParTituloId.class.getConstructor(),
      5,
      "./dados/"+nomeEntidade+"/indiceNomeEpisodios.db"
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

  public Episodio[] readNomeEpisodio(String nome) throws Exception{
    if(nome.length() == 0)
      return null;

    ArrayList<ParTituloId> ptis = indiceNomeEpisodio.read(new ParTituloId(nome, -1));
    if(ptis.size() > 0){
      Episodio[] episodios = new Episodio[ptis.size()];
      int i = 0;
      for(ParTituloId pti: ptis)
        episodios[i++] = read(pti.getId());
      return episodios;

    }else
      return null;
  }
  
  public Episodio[] readEpisodiosSerie(int id_serie) throws Exception{
    
    // Metodo para verificar se a serie vinculada ao episodio existe ordem id_serie -1 é ao contrario?
    ArrayList<ParIdId> pIds = indiceIdEpisodio_IdSerie.read(new ParIdId(-1, id_serie));
    if(pIds.size() > 0){
      Episodio[] episodios = new Episodio[pIds.size()];
      int i = 0;

      // Tive que criar um metodo para pegar o id do episodio
      // pois o metodo read() da superclasse Arquivo não aceita perguntar kutova
      for(ParIdId pID : pIds)
        episodios[i++] = read(pID.getId1());
      return episodios;
    }else
      return null;
  }

  @Override
  public boolean delete(int id) throws Exception{
    Episodio e = read(id);
    if(e != null){
      if(super.delete(id))
        return indiceIdEpisodio_IdSerie.delete(new ParIdId(id, e.getID_serie())) 
            && indiceNomeEpisodio.delete(new ParTituloId(e.getNome(), id));
    }
    return false;
  }

  public boolean deleteEpisodioSerie(int id_serie) throws Exception{

    // Metodo para verificar se a serie vinculada ao episodio existe ordem id_serie -1 é ao contrario?
    ArrayList<ParIdId> pIds = indiceIdEpisodio_IdSerie.read(new ParIdId(id_serie, -1));
    if(pIds.size() > 0){
      for(ParIdId pID : pIds)
        delete(pID.getId1());
      return true;
    } 
    return false;
  }

  @Override
  public boolean update(Episodio novoEpisodio) throws Exception{
    Episodio e = read(novoEpisodio.getID());
    if(e != null){
      if(super.update(novoEpisodio)){
        if(!e.getNome().equals(novoEpisodio.getNome())){
          indiceNomeEpisodio.delete(new ParTituloId(e.getNome(), e.getID()));
          indiceNomeEpisodio.create(new ParTituloId(novoEpisodio.getNome(), e.getID()));
        } else {
          throw new Exception("Episodio não pode ser atualizado pois ouve erro no cadastro do novo nome");
        }

        if(e.getID_serie() != novoEpisodio.getID_serie()){
          indiceIdEpisodio_IdSerie.delete(new ParIdId(e.getID(), e.getID_serie()));
          indiceIdEpisodio_IdSerie.create(new ParIdId(e.getID(), novoEpisodio.getID_serie()));
        }else {
          throw new Exception("Episodio não pode ser atualizado pois a serie vinculada não existe");
        }

        return true;
      }
    }
    return false;
  }





}


// Duvidas:

// Verificar se o id serie deve realmente ser em uma arvore?
// Verificar se o nome do episodio deve realmente ser em uma arvore?
// Metodo para verificar se a serie vinculada ao episodio existe ordem id_serie -1 é ao contrario?

// Tive que criar um metodo para pegar o id do episodio na class parIdId
// pois o metodo read() da superclasse Arquivo não aceita perguntar kutova

//Ideia printar a avaliação da serie, que será a media das avaliações dos seus episodios
