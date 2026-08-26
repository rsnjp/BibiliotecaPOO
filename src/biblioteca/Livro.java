package biblioteca;

public class Livro {

    // Atributos
    private int idLivro;
    private String titulo;
    private String autor;
    private String status;

    // Construtor
    public Livro(int idLivro, String titulo, String autor, String status) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.status = status;
    }

    // Métodos da UML (assinaturas declaradas, sem implementação completa)
    public void atualizarStatus(String status) {
        this.status = status;
    }

    public boolean consultarDisponibilidade() {
        return "Disponível".equalsIgnoreCase(this.status);
    }

    // Getters e Setters
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
        return titulo + " (" + status + ")";
    }

    // Converte o objeto para uma linha de texto no formato CSV,
    // usando ";" como separador (evita conflito com vírgulas que
    // possam existir no título ou no nome do autor).
    // Formato: idLivro;titulo;autor;status
    public String toCSV() {
        return idLivro + ";" + titulo + ";" + autor + ";" + status;
    }
}
