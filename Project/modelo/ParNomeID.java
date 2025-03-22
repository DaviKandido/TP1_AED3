package modelo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import aeds3.*;

public class ParNomeID implements RegistroHashExtensivel {
    
    private String nome;           // chave
    private int    id;             // valor
    private final short TAMANHO = 17;  // tamanho em bytes

    public ParNomeID() throws Exception {
        this.nome = "";
        this.id = -1;
    }

    public ParNomeID(String nome, int id) throws Exception {
        if(nome.length()!=0) {
            this.nome = nome;
            this.id = id;
        } else
            throw new Exception("Nome inválido");
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Math.abs(this.nome.hashCode());
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.nome + ";" + this.id+")";
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        if(nome.length()!=13)
            throw new Exception("Nome inválido");
        dos.writeUTF(nome);
        dos.writeInt(id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        dis.readUTF();
        id = dis.readInt();
    }

    public static int hash(String nome) {
        return Math.abs(nome.hashCode());
    }

}
