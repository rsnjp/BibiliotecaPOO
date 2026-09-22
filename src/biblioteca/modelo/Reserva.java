package biblioteca.modelo;

import biblioteca.util.Datas;
import biblioteca.util.ManipuladorArquivos;

import java.util.Date;

public class Reserva {

    private int idReserva;
    private Date dataReserva;
    private String status;
    private Usuario usuario;
    private Livro livro;

    public Reserva(int idReserva, Date dataReserva, String status, Usuario usuario, Livro livro) {
        this.idReserva = idReserva;
        this.dataReserva = dataReserva;
        this.status = status;
        this.usuario = usuario;
        this.livro = livro;
    }

    // Cancela a reserva e devolve o livro para o acervo disponível.
    public void cancelarReserva() {
        this.status = "Cancelada";
        ManipuladorArquivos.atualizarObjeto("Reserva", idReserva, this, 5);
        liberarLivro();
    }

    // "Baixa" da reserva: chamada quando o empréstimo referente a ela é efetuado.
    public void concluirReserva() {
        this.status = "Concluída";
        ManipuladorArquivos.atualizarObjeto("Reserva", idReserva, this, 5);
    }

    public void atualizarDados(Usuario usuario, Date dataReserva) {
        this.usuario = usuario;
        this.dataReserva = dataReserva;
        ManipuladorArquivos.atualizarObjeto("Reserva", idReserva, this, 5);
    }

    // Só mexe no livro se ele estiver preso por esta reserva.
    public void liberarLivro() {
        if (livro != null && "Reservado".equals(livro.getStatus())) {
            livro.atualizarStatus("Disponível");
        }
    }

    // Reserva ativa = ainda pendente da efetivação do empréstimo.
    public boolean estaAtiva() {
        return "Ativa".equals(status);
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
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
        return "Reserva #" + idReserva + " - Livro " + livro.getTitulo() + " - Usuário " + usuario.getNome()
                + " - " + Datas.formatar(dataReserva) + " (" + status + ")";
    }

    // Assim como em Emprestimo, a composição (usuario/livro completos) vive
    // em memória; no CSV gravamos apenas os ids, pegos dos próprios objetos.
    // Formato: idReserva;dataReserva;status;idUsuario;idLivro
    public String toCSV() {
        return idReserva + ";" + Datas.formatar(dataReserva) + ";" + status + ";" + usuario.getIdUsuario() + ";" + livro.getIdLivro();
    }
}
