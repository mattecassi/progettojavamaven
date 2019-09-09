package Models;

public class TipoVino extends Model {

    private String tipo;


    public TipoVino() {
        config("tipo_vino");

    }

    public TipoVino(String tipo) {
        config("tipo_vino");
        this.tipo = tipo;
    }

    public TipoVino(Integer ID, String tipo) {
        config("tipo_vino");
        this.tipo = tipo;
        this.setID(ID);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "{" +
                "tipo:\"" + tipo + '"' +
                '}';
    }
}
