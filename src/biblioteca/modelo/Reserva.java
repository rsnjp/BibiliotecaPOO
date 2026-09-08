package biblioteca.modelo;

import biblioteca.util.ManipuladorArquivos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reserva {

    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    private int idReserva;
    private Date dataReserva;
    private String status;
    private int idUsuario;
    private int idLivro;

    public Reserva(int idReserva, Date dataReserva, String status, int idUsuario, int idLivro) {
        this.idReserva = idReserva;
        this.dataReserva = dataReserva;
        this.status = status;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
    }

    public void cancelarReserva() {
        this.status = "Cancelada";
        ManipuladorArquivos.atualizarObjeto("Reserva", idReserva, this, 5);
    }

    public void concluirReserva() {
        this.status = "Concluída";
        ManipuladorArquivos.atualizarObjeto("Reserva", idReserva, this, 5);
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

    @Override
    public String toString() {
        return "Reserva #" + idReserva + " - Livro " + idLivro + " - Usuário " + idUsuario + " (" + status + ")";
    }

    // Assim como em Emprestimo, grava apenas o id de usuário/livro.
    // Formato: idReserva;dataReserva;status;idUsuario;idLivro
    public String toCSV() {
        String dtReserva = dataReserva != null ? FORMATO_DATA.format(dataReserva) : "";
        return idReserva + ";" + dtReserva + ";" + status + ";" + idUsuario + ";" + idLivro;
    }
}
