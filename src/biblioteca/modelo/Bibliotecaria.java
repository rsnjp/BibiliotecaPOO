package biblioteca.modelo;

import java.util.Date;
import java.util.List;

public class Bibliotecaria {

    private String idBibliotecaria;
    private String nome;
    private String turno;

    public Bibliotecaria(String idBibliotecaria, String nome, String turno) {
        this.idBibliotecaria = idBibliotecaria;
        this.nome = nome;
        this.turno = turno;
    }

    // Cadastro de livros e usuários
    public void cadastrarLivro(List<Livro> livros, Livro livro) {
        livros.add(livro);
    }

    public void cadastrarUsuario(List<Usuario> usuarios, Usuario usuario) {
        usuarios.add(usuario);
    }

    // Empréstimos
    public Emprestimo registrarEmprestimo(List<Emprestimo> emprestimos, Usuario usuario, Livro livro, Date dataEmprestimo) {
        Emprestimo emprestimo = new Emprestimo(emprestimos.size() + 1, dataEmprestimo, null, "Ativo", usuario, livro);
        emprestimos.add(emprestimo);
        livro.atualizarStatus("Emprestado");
        return emprestimo;
    }

    public void registrarDevolucao(List<Emprestimo> emprestimos, int idEmprestimo) {
        for (Emprestimo e : emprestimos) {
            if (e.getIdEmprestimo() == idEmprestimo) {
                e.registrarDevolucao();
                break;
            }
        }
    }

    // Reservas
    public Reserva registrarReserva(List<Reserva> reservas, Usuario usuario, Livro livro) {
        Reserva reserva = new Reserva(reservas.size() + 1, new Date(), "Ativa", usuario, livro);
        reservas.add(reserva);
        return reserva;
    }

    public String getIdBibliotecaria() {
        return idBibliotecaria;
    }

    public void setIdBibliotecaria(String idBibliotecaria) {
        this.idBibliotecaria = idBibliotecaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Bibliotecaria{" +
                "idBibliotecaria='" + idBibliotecaria + '\'' +
                ", nome='" + nome + '\'' +
                ", turno='" + turno + '\'' +
                '}';
    }

    // Formato: idBibliotecaria;nome;turno
    public String toCSV() {
        return idBibliotecaria + ";" + nome + ";" + turno;
    }
}
