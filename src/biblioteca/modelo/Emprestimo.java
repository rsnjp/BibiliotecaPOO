package biblioteca.modelo;

import biblioteca.util.ManipuladorArquivos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprestimo {

    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    private int idEmprestimo;
    private int idUsuario;
    private int idLivro;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoEfetiva;
    private String status;

    public Emprestimo(int idEmprestimo, int idUsuario, int idLivro, Date dataEmprestimo,
                       Date dataDevolucaoPrevista, String status) {
        this.idEmprestimo = idEmprestimo;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.status = status;
    }

    // Encerra o empréstimo, grava a mudança e libera o livro automaticamente.
    public void registrarDevolucao() {
        this.dataDevolucaoEfetiva = new Date();
        this.status = "Concluído";
        ManipuladorArquivos.atualizarObjeto("Emprestimo", idEmprestimo, this, 7);

        Livro livro = ManipuladorArquivos.buscarLivroPorId(idLivro);
        if (livro != null) {
            livro.atualizarStatus("Disponível");
        }
    }

    public void renovarEmprestimo() {
        // TODO: implementar regra de renovação (ex.: +7 dias na dataDevolucaoPrevista)
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
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

    @Override
    public String toString() {
        return "Empréstimo #" + idEmprestimo + " - Livro " + idLivro + " - Usuário " + idUsuario + " (" + status + ")";
    }

    // Grava só os ids de usuário/livro (não o objeto inteiro); a leitura
    // resolve esses ids para o Livro/Usuario correspondente quando precisa.
    // Formato: idEmprestimo;dataEmprestimo;dataDevolucaoPrevista;dataDevolucaoEfetiva;status;idUsuario;idLivro
    public String toCSV() {
        String dtEmprestimo = dataEmprestimo != null ? FORMATO_DATA.format(dataEmprestimo) : "";
        String dtPrevista = dataDevolucaoPrevista != null ? FORMATO_DATA.format(dataDevolucaoPrevista) : "";
        String dtEfetiva = dataDevolucaoEfetiva != null ? FORMATO_DATA.format(dataDevolucaoEfetiva) : "";

        return idEmprestimo + ";" + dtEmprestimo + ";" + dtPrevista + ";" + dtEfetiva + ";"
                + status + ";" + idUsuario + ";" + idLivro;
    }
}
