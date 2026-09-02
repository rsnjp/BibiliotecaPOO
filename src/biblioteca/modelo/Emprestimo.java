package biblioteca.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprestimo {

    // Formato usado para gravar/ler datas no arquivo CSV.
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    // Atributos
    private int idEmprestimo;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoEfetiva;
    private String status;

    // Relacionamentos: um empréstimo pertence a 1 Usuario e é para 1 Livro
    private Usuario usuario;
    private Livro livro;

    // Construtor
    public Emprestimo(int idEmprestimo, Date dataEmprestimo, Date dataDevolucaoPrevista,
                       String status, Usuario usuario, Livro livro) {
        this.idEmprestimo = idEmprestimo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.status = status;
        this.usuario = usuario;
        this.livro = livro;
    }

    // Métodos da UML
    public void registrarDevolucao() {
        this.dataDevolucaoEfetiva = new Date();
        this.status = "Concluído";
        if (livro != null) {
            livro.atualizarStatus("Disponível");
        }
    }

    public void renovarEmprestimo() {
        // TODO: implementar regra de renovação (ex.: +7 dias na dataDevolucaoPrevista)
    }

    // Getters e Setters
    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucaoEfetiva() {
        return dataDevolucaoEfetiva;
    }

    public void setDataDevolucaoEfetiva(Date dataDevolucaoEfetiva) {
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Empréstimo #" + idEmprestimo + " - " + livro.getTitulo() + " para " + usuario.getNome();
    }

    // Converte o objeto para uma linha de texto no formato CSV.
    // Datas nulas (ex.: devolução ainda não ocorreu) são gravadas como
    // string vazia, e reconstruídas como null na leitura.
    // Em vez de gravar o objeto Usuario/Livro inteiro, gravamos apenas o
    // seu id: na leitura, o ManipuladorArquivos usa esse id para religar
    // o Emprestimo ao objeto Usuario/Livro já existente em memória.
    // Formato: idEmprestimo;dataEmprestimo;dataDevolucaoPrevista;dataDevolucaoEfetiva;status;idUsuario;idLivro
    public String toCSV() {
        String dtEmprestimo = dataEmprestimo != null ? FORMATO_DATA.format(dataEmprestimo) : "";
        String dtPrevista = dataDevolucaoPrevista != null ? FORMATO_DATA.format(dataDevolucaoPrevista) : "";
        String dtEfetiva = dataDevolucaoEfetiva != null ? FORMATO_DATA.format(dataDevolucaoEfetiva) : "";

        return idEmprestimo + ";" + dtEmprestimo + ";" + dtPrevista + ";" + dtEfetiva + ";"
                + status + ";" + usuario.getIdUsuario() + ";" + livro.getIdLivro();
    }
}
