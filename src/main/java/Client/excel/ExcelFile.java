package Client.excel;

public class ExcelFile {
    int id,sconto,importo,qta;
    String nome;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }

    public int getImporto() {
        return importo;
    }

    public void setImporto(int importo) {
        this.importo = importo;
    }

    public int getQta() {
        return qta;
    }

    public void setQta(int qta) {
        this.qta = qta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ExcelFile{" +
                "id=" + id +
                ", sconto=" + sconto +
                ", importo=" + importo +
                ", qta=" + qta +
                ", nome='" + nome + '\'' +
                '}';
    }
}
