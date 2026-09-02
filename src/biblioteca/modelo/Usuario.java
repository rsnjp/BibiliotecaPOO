package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String telefone;
    private String email;

    // Não guarda a própria lista de empréstimos/reservas: filtra a lista
    // mestre recebida por parâmetro, evitando duas cópias desincronizadas.

    public Usuario(int idUsuario, String nome, String telefone, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public void atualizarContato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    public List<Emprestimo> listarEmprestimos(List<Emprestimo> todosEmprestimos) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : todosEmprestimos) {
            if (e.getUsuario() == this) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public List<Reserva> listarReservas(List<Reserva> todasReservas) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : todasReservas) {
            if (r.getUsuario() == this) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Formato: idUsuario;nome;telefone;email
    public String toCSV() {
        return idUsuario + ";" + nome + ";" + telefone + ";" + email;
    }
}
