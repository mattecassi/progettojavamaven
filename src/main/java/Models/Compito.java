package Models;

public class Compito extends Model {

    private String desc;
    private String dataCompito;


    public Compito() {
        config("compito");
    }

    public Compito(String desc) {
        config("compito");
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "{" +
                "desc:'" + desc + '\'' +
                ", dataCompito:'" + dataCompito + '\'' +
                ", ID:" + ID +
                '}';
    }

    public String getDataCompito() {
        return dataCompito;
    }

    public void setDataCompito(String dataCompito) {
        this.dataCompito = dataCompito;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
