package biblioteca.modelo;

import biblioteca.util.Datas;
import biblioteca.util.ManipuladorArquivos;

import java.util.Date;

public class Emprestimo {

    // Prazo padrão, em dias, para a devolução do livro.
    public static final int PRAZO_DIAS = 7;

    private int idEmprestimo;
    private Usuario usuario;
    private Livro livro;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoEfetiva;
    private String status;

    public Emprestimo(int idEmprestimo, Usuario usuario, Livro livro, Date dataEmprestimo,
                       Date dataDevolucaoPrevista, String status) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.status = status;

        // Sem data prevista informada (ex.: registros antigos do CSV), o prazo
        // é calculado a partir da data do empréstimo.
        if (dataDevolucaoPrevista == null && dataEmprestimo != null) {
            dataDevolucaoPrevista = Datas.somarDias(dataEmprestimo, PRAZO_DIAS);
        }
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    // Encerra o empréstimo, grava a mudança e libera o livro automaticamente.
    public void registrarDevolucao() {
        this.dataDevolucaoEfetiva = new Date();
        this.status = "Concluído";
        ManipuladorArquivos.atualizarObjeto("Emprestimo", idEmprestimo, this, 7);

        if (livro != null) {
            livro.atualizarStatus("Disponível");
        }
    }

    public void atualizarDados(Usuario usuario, Date dataEmprestimo, Date dataDevolucaoPrevista) {
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        ManipuladorArquivos.atualizarObjeto("Emprestimo", idEmprestimo, this, 7);
    }

    public void renovarEmprestimo() {
        // TODO: implementar regra de renovação (ex.: +7 dias na dataDevolucaoPrevista)
    }

    public boolean estaAtivo() {
        return "Ativo".equals(status);
    }

    // Dias de atraso considerando a devolução na data de referência (0 se dentro do prazo).
    public long calcularDiasAtraso(Date dataReferencia) {
        if (dataDevolucaoPrevista == null) return 0;
        return Math.max(0, Datas.diasEntre(dataDevolucaoPrevista, dataReferencia));
    }

    // Dias de atraso do empréstimo: até hoje se ainda está ativo, ou até a
    // data em que foi devolvido.
    public long getDiasAtraso() {
        Date referencia = dataDevolucaoEfetiva != null ? dataDevolucaoEfetiva : new Date();
        return calcularDiasAtraso(referencia);
    }

    public boolean estaAtrasado() {
        return estaAtivo() && getDiasAtraso() > 0;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
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
        String texto = "Empréstimo #" + idEmprestimo + " - Livro " + livro.getTitulo() + " - Usuário " + usuario.getNome()
                + " - Devolver até " + Datas.formatar(dataDevolucaoPrevista) + " (" + status + ")";
        if (estaAtrasado()) {
            texto += " - ATRASADO " + getDiasAtraso() + " dia(s)";
        }
        return texto;
    }

    // A composição (usuario/livro completos) vive só em memória; no CSV
    // continuamos gravando apenas os ids, pegos dos próprios objetos.
    // Formato: idEmprestimo;dataEmprestimo;dataDevolucaoPrevista;dataDevolucaoEfetiva;status;idUsuario;idLivro
    public String toCSV() {
        return idEmprestimo + ";" + Datas.formatar(dataEmprestimo) + ";" + Datas.formatar(dataDevolucaoPrevista) + ";"
                + Datas.formatar(dataDevolucaoEfetiva) + ";" + status + ";" + usuario.getIdUsuario() + ";" + livro.getIdLivro();
    }
}
