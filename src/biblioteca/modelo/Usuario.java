package biblioteca.modelo;

import biblioteca.util.ManipuladorArquivos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String telefone;
    private String email;

    public Usuario(int idUsuario, String nome, String telefone, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public void atualizarContato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
        ManipuladorArquivos.atualizarObjeto("Usuario", idUsuario, this, 4);
    }

    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> meus = new ArrayList<>();
        for (Emprestimo e : ManipuladorArquivos.lerEmprestimos()) {
            if (e.getUsuario().getIdUsuario() == idUsuario) {
                meus.add(e);
            }
        }
        return meus;
    }

    public List<Reserva> listarReservas() {
        List<Reserva> minhas = new ArrayList<>();
        for (Reserva r : ManipuladorArquivos.lerReservas()) {
            if (r.getUsuario().getIdUsuario() == idUsuario) {
                minhas.add(r);
            }
        }
        return minhas;
    }

    // Só cancela reservas do próprio usuário.
    public boolean cancelarReserva(int idReserva) {
        for (Reserva r : ManipuladorArquivos.lerReservas()) {
            if (r.getIdReserva() == idReserva && r.getUsuario().getIdUsuario() == idUsuario) {
                r.cancelarReserva();
                return true;
            }
        }
        return false;
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
        return idUsuario + " - " + nome;
    }

    // Formato: idUsuario;nome;telefone;email
    public String toCSV() {
        return idUsuario + ";" + nome + ";" + telefone + ";" + email;
    }
}
