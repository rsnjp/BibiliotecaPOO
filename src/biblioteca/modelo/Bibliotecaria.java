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

    public void cadastrarBibliotecaria(Bibliotecaria bibliotecaria) {
        ManipuladorArquivos.salvarObjeto("Bibliotecaria", bibliotecaria);
    }

    public void atualizarDados(String nome, String turno) {
        this.nome = nome;
        this.turno = turno;
        ManipuladorArquivos.atualizarObjeto("Bibliotecaria", idBibliotecaria, this, 3);
    }

    // Exclusões
    public void excluirLivro(Livro livro) {
        ManipuladorArquivos.excluirObjeto("Livro", livro.getIdLivro(), 4);
    }

    public void excluirUsuario(Usuario usuario) {
        ManipuladorArquivos.excluirObjeto("Usuario", usuario.getIdUsuario(), 4);
    }

    public void excluirBibliotecaria(Bibliotecaria bibliotecaria) {
        ManipuladorArquivos.excluirObjeto("Bibliotecaria", bibliotecaria.getIdBibliotecaria(), 3);
    }

    // Excluir um empréstimo ainda ativo devolve o livro para o acervo.
    public void excluirEmprestimo(Emprestimo emprestimo) {
        ManipuladorArquivos.excluirObjeto("Emprestimo", emprestimo.getIdEmprestimo(), 7);
        if (emprestimo.estaAtivo()) {
            emprestimo.getLivro().atualizarStatus("Disponível");
        }
    }

    // Excluir uma reserva ainda ativa libera o livro reservado.
    public void excluirReserva(Reserva reserva) {
        ManipuladorArquivos.excluirObjeto("Reserva", reserva.getIdReserva(), 5);
        if (reserva.estaAtiva()) {
            reserva.liberarLivro();
        }
    }

    // Empréstimos
    public void registrarEmprestimo(Emprestimo emprestimo) {
        ManipuladorArquivos.salvarObjeto("Emprestimo", emprestimo);
    }

    // Retorna o empréstimo baixado (para a tela informar prazo/atraso) ou null.
    public Emprestimo registrarDevolucao(int idEmprestimo) {
        for (Emprestimo e : ManipuladorArquivos.lerEmprestimos()) {
            if (e.getIdEmprestimo() == idEmprestimo && e.estaAtivo()) {
                e.registrarDevolucao();
                return e;
            }
        }
        return null;
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
