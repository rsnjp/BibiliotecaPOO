package biblioteca.modelo;

import biblioteca.util.ManipuladorArquivos;

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
    public void cadastrarLivro(Livro livro) {
        ManipuladorArquivos.salvarObjeto("Livro", livro);
    }

    public void cadastrarUsuario(Usuario usuario) {
        ManipuladorArquivos.salvarObjeto("Usuario", usuario);
    }

    // Empréstimos
    public void registrarEmprestimo(Emprestimo emprestimo) {
        ManipuladorArquivos.salvarObjeto("Emprestimo", emprestimo);
    }

    public boolean registrarDevolucao(int idEmprestimo) {
        for (Emprestimo e : ManipuladorArquivos.lerEmprestimos()) {
            if (e.getIdEmprestimo() == idEmprestimo) {
                e.registrarDevolucao();
                return true;
            }
        }
        return false;
    }

    // Reservas
    public void registrarReserva(Reserva reserva) {
        ManipuladorArquivos.salvarObjeto("Reserva", reserva);
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
        return idBibliotecaria + " - " + nome;
    }

    // Formato: idBibliotecaria;nome;turno
    public String toCSV() {
        return idBibliotecaria + ";" + nome + ";" + turno;
    }
}
