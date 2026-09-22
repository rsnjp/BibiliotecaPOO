package biblioteca.modelo;

import biblioteca.util.ManipuladorArquivos;

public class Livro {

    private int idLivro;
    private String titulo;
    private String autor;
    private String status;

    public Livro(int idLivro, String titulo, String autor, String status) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.status = status;
    }

    // Muda o status e já regrava a linha correspondente no arquivo.
    public void atualizarStatus(String status) {
        this.status = status;
        ManipuladorArquivos.atualizarObjeto("Livro", idLivro, this, 4);
    }

    public void atualizarDados(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        ManipuladorArquivos.atualizarObjeto("Livro", idLivro, this, 4);
    }

    public boolean consultarDisponibilidade() {
        return "Disponível".equalsIgnoreCase(this.status);
    }

    // Empréstimo ainda não devolvido deste livro (null se não houver).
    public Emprestimo buscarEmprestimoAtivo() {
        for (Emprestimo e : ManipuladorArquivos.lerEmprestimos()) {
            if (e.getLivro().getIdLivro() == idLivro && e.estaAtivo()) {
                return e;
            }
        }
        return null;
    }

    // Reserva pendente deste livro (null se não houver).
    public Reserva buscarReservaAtiva() {
        for (Reserva r : ManipuladorArquivos.lerReservas()) {
            if (r.getLivro().getIdLivro() == idLivro && r.estaAtiva()) {
                return r;
            }
        }
        return null;
    }

    // Indica se algum empréstimo ou reserva (de qualquer status) aponta para este livro.
    public boolean possuiMovimentacoes() {
        for (Emprestimo e : ManipuladorArquivos.lerEmprestimos()) {
            if (e.getLivro().getIdLivro() == idLivro) return true;
        }
        for (Reserva r : ManipuladorArquivos.lerReservas()) {
            if (r.getLivro().getIdLivro() == idLivro) return true;
        }
        return false;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return idLivro + " - " + titulo + " (" + status + ")";
    }

    // Formato: idLivro;titulo;autor;status
    public String toCSV() {
        return idLivro + ";" + titulo + ";" + autor + ";" + status;
    }
}
