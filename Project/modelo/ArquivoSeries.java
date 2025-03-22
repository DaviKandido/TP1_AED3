package modelo;
import entidades.Serie;

import java.util.ArrayList;

import aeds3.*;

public class ArquivoSeries extends Arquivo<Serie> {
    
    Arquivo<Serie> arqSeries;
    HashExtensivel<ParNomeID> indiceNome;
    ArvoreBMais<ParNomeSerieId> indiceNomeSerie;
    ArvoreBMais <ParIdId> indices;

    public ArquivoSeries() throws Exception {
        super("series", Serie.class.getConstructor());
        
        indiceNome = new HashExtensivel<>(
            ParNomeID.class.getConstructor(),
            4,
            "./dados/"+nomeEntidade+"/indiceNome.d.db",
            "./dados/"+nomeEntidade+"/indiceNome.c.db"
            );
        
        indiceNomeSerie = new ArvoreBMais<>(
            ParNomeSerieId.class.getConstructor(), 
            5, 
            "./dados/"+nomeEntidade+"/indiceNomeSerie.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeID(s.getNome(), id));
        indiceNomeSerie.create(new ParNomeSerieId(s.getNome(), id));
        return id;
    }
 
    public Serie readNome(String nome) throws Exception {

        if(nome.length()==0)
            return null;

        ParNomeID pii = indiceNome.read(ParNomeID.hash(nome));
        if(pii != null)
            return read(pii.getId());    // na superclasse
        else 
            return null;
    }

    public Serie[] readNomeSerie(String nome) throws Exception {
        if(nome.length()==0)
            return null;
        ArrayList<ParNomeSerieId> ptis = indiceNomeSerie.read(new ParNomeSerieId(nome, -1));
        if(ptis.size()>0) {
            Serie[] series = new Serie[ptis.size()];
            int i=0;
            
            for(ParNomeSerieId pti: ptis) 
                series[i++] = read(pti.getId());
            return series;
        }
        else 
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie s = read(id);   // na superclasse
        if(s!=null) {
            if(super.delete(id))
                return indiceNome.delete(ParNomeID.hash(s.getNome()))
                    && indiceNomeSerie.delete(new ParNomeSerieId(s.getNome(), id));
        }
        return false;
    }

    public boolean delete(String nome, int id) throws Exception {
        ArquivoEpisodio arqEpisodios = new ArquivoEpisodio();
        ParNomeID pii =  indiceNome.read(ParNomeID.hash(nome));
        
        if (pii == null) {
            return false; // Serie nao encontrada
        }

        //verificar episodios vinculados a serie
        ArrayList<ParIdId> ids = indices.read(new ParIdId(id, -1));
        if(ids!= null){
            //excluir todos os episodios
            boolean deletados = arqEpisodios.deleteEpisodio(pii.getId());
            //deletar a serie
            if(deletados){
                return delete(pii.getId());
            }
        } 
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getID());    // na superclasse
        if(s!=null) {
            if(super.update(novaSerie)) {
                if(!s.getNome().equals(novaSerie.getNome())) {
                    indiceNome.delete(ParNomeID.hash(s.getNome()));
                    indiceNome.create(new ParNomeID(novaSerie.getNome(), s.getID()));
                }
                return true;
            }
        }
        return false;
    }

    // metodo para atualizar pelo id
    public boolean update(int id) throws Exception {
        if (!serieExiste(id))
            throw new Exception("Erro: Não há série vinculada a esse ID");
        
        Serie serie = read(id); // na superclasse
        
        if (serie != null) {
            return update(serie); 
        }
        
        return false;
    }

    //metodo para testar se ha serie vinculada ao id buscado
    public static boolean serieExiste(int id) throws Exception{
        ArquivoSeries arqSeries = new ArquivoSeries();
        Serie s = arqSeries.read(id);   // na superclasse
        if(s != null) {
            return true;
        }
        return false;
    }
}
