package Models;

public class Users extends Model {


    private String nome;
    private String cognome;
    private String email;


    public Users() {
        config("users");
    }

    public Users(String nome, String cognome, String email) {
        config("users");
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }


    @Override
    public String toString() {
        return "{" +
                "nome:\"" + nome + '"' +
                ", cognome:\"" + cognome + '"' +
                ", email:\"" + email + '"' +
                ", ID:" + ID +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
