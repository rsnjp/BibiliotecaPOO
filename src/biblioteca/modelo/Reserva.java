package biblioteca.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reserva {

    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

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

    public void cancelarReserva() {
        this.status = "Cancelada";
    }

    public void concluirReserva() {
        this.status = "Concluída";
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
        return "Reserva #" + idReserva + " - " + livro.getTitulo() + " para " + usuario.getNome();
    }

    // Assim como em Emprestimo, grava apenas o id de usuário/livro para
    // religar as referências na leitura.
    // Formato: idReserva;dataReserva;status;idUsuario;idLivro
    public String toCSV() {
        String dtReserva = dataReserva != null ? FORMATO_DATA.format(dataReserva) : "";
        return idReserva + ";" + dtReserva + ";" + status + ";"
                + usuario.getIdUsuario() + ";" + livro.getIdLivro();
    }
}
