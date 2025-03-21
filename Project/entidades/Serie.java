package entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

import aeds3.EntidadeArquivo;

public class Serie implements EntidadeArquivo {

    private int id;
    private String nome;
    private LocalDate anoLancamento;
    private String sinopse;
    private String streaming;
    private String genero;
    private String classIndicativa;

    public Serie() throws Exception  {
        this(-1, "", LocalDate.now(),"","", "", "");
    }

    public Serie(String nome, LocalDate anoLancamento, String sinopse, String streaming, String genero, String classIndicativa) throws Exception {
        this(-1, nome, anoLancamento, sinopse, streaming, genero, classIndicativa);
    }

    public Serie(int id, String nome, LocalDate anoLancamento, String sinopse, String streaming, String genero, String classIndicativa) throws Exception {
        this.id = id;
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
        this.genero = genero;
        this.classIndicativa = classIndicativa;

        if(id < 0)
            throw new Exception("ID inválido");
        if(nome.equals(""))
            throw new Exception("Nome inválido");
        if(anoLancamento == null)
            throw new Exception("Ano de lançamento inválida");
        if(sinopse.equals(""))
            throw new Exception("Sinopse inválida");
        if(sinopse.equals(""))
            throw new Exception("Sinopse inválida");
        if(streaming.equals(""))
            throw new Exception("Streaming inválida");
        if(genero.equals(""))
            throw new Exception("Genero inválida");
        if(classIndicativa.equals(""))
        throw new Exception("Classificação Indicativa inválida");

    } 

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(LocalDate anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getclassIndicativa() {
        return classIndicativa;
    }

    public void setclassIndicativa(String classIndicativa) {
        this.classIndicativa = classIndicativa;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt((int)anoLancamento.toEpochDay());
        dos.writeUTF(sinopse);
        dos.writeUTF(streaming);
        dos.writeUTF(genero);
        dos.writeUTF(classIndicativa);
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
    
        id = dis.readInt();
        nome = dis.readUTF();
        anoLancamento = LocalDate.ofEpochDay(dis.readInt());
        sinopse = dis.readUTF();
        streaming = dis.readUTF();
        genero = dis.readUTF();
        classIndicativa = dis.readUTF();
    }

    public String toString(){
        return "Série = [ID: " + id +
                "\nNome: " + nome +
                "\nAno de lançamento: " + anoLancamento +
                "\nSinopse " + sinopse + 
                "\nStreaming: " + streaming + 
                "\nGênero: " + genero + 
                "\nClassificação Indicativa: " + classIndicativa + "]";
    }

    @Override
 	public boolean equals(Object obj){
 		return (this.getID() == ((Serie) obj).getID());
 	}
}