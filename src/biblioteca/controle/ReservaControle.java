package biblioteca.controle;

import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;
import biblioteca.util.Datas;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaControle {

    // Regras: bloqueia reserva de livro emprestado ou que já possui reserva ativa.
    public static boolean registrarReserva(Usuario usuario, Livro livro, JFrame tela, String idBibliotecaria) {
        if (usuario == null || livro == null) {
            JOptionPane.showMessageDialog(tela, "Selecione o usuário e o livro.");
            return false;
        }

        if (livro.buscarEmprestimoAtivo() != null || "Emprestado".equals(livro.getStatus())) {
            JOptionPane.showMessageDialog(tela, "Reserva bloqueada: o livro está emprestado.");
            return false;
        }

        Reserva reservaExistente = livro.buscarReservaAtiva();
        if (reservaExistente != null) {
            JOptionPane.showMessageDialog(tela, "Reserva bloqueada: o livro já está reservado para "
                    + reservaExistente.getUsuario().getNome()
                    + " (reserva de " + Datas.formatar(reservaExistente.getDataReserva()) + ").");
            return false;
        }

        int id = ManipuladorArquivos.proximoId("Reserva");
        Reserva reserva = new Reserva(id, new Date(), "Ativa", usuario, livro);
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).registrarReserva(reserva);
        livro.atualizarStatus("Reservado");

        JOptionPane.showMessageDialog(tela, "Reserva registrada com sucesso!");
        return true;
    }

    // Cancelamento feito pelo próprio usuário (só as reservas dele).
    public static boolean cancelarReserva(int idReserva, JFrame tela, int idUsuario) {
        Usuario usuario = UsuarioControle.obterUsuario(idUsuario);
        boolean ok = usuario.cancelarReserva(idReserva);

        JOptionPane.showMessageDialog(tela, ok
                ? "Reserva cancelada com sucesso!"
                : "Somente reservas ativas podem ser canceladas.");
        return ok;
    }

    // Cancelamento feito pela bibliotecária (ex.: na lista de pendentes).
    public static boolean cancelarReserva(Reserva reserva, JFrame tela) {
        if (!reserva.estaAtiva()) {
            JOptionPane.showMessageDialog(tela, "Somente reservas ativas podem ser canceladas.");
            return false;
        }
        int resposta = JOptionPane.showConfirmDialog(tela, "Cancelar a reserva #" + reserva.getIdReserva() + "?",
                "Cancelar reserva", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) return false;

        reserva.cancelarReserva();
        JOptionPane.showMessageDialog(tela, "Reserva cancelada com sucesso!");
        return true;
    }

    public static boolean editarReserva(Reserva reserva, Usuario usuario, String textoDataReserva, JFrame tela) {
        if (usuario == null) {
            JOptionPane.showMessageDialog(tela, "Selecione o usuário.");
            return false;
        }

        Date dataReserva = Datas.converter(textoDataReserva);
        if (dataReserva == null) {
            JOptionPane.showMessageDialog(tela, "Informe a data no formato dd/MM/aaaa.");
            return false;
        }

        reserva.atualizarDados(usuario, dataReserva);
        JOptionPane.showMessageDialog(tela, "Reserva atualizada com sucesso!");
        return true;
    }

    public static boolean excluirReserva(Reserva reserva, JFrame tela, String idBibliotecaria) {
        String aviso = reserva.estaAtiva() ? " (o livro voltará a ficar disponível)" : "";
        if (!Validacoes.confirmarExclusao(tela, "a reserva #" + reserva.getIdReserva() + aviso)) return false;

        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).excluirReserva(reserva);
        JOptionPane.showMessageDialog(tela, "Reserva excluída com sucesso!");
        return true;
    }

    public static List<Reserva> listarReservas() {
        return ManipuladorArquivos.lerReservas();
    }

    // Reservas ativas, ou seja, ainda pendentes da efetivação do empréstimo.
    public static List<Reserva> listarReservasPendentes() {
        List<Reserva> pendentes = new ArrayList<>();
        for (Reserva r : listarReservas()) {
            if (r.estaAtiva()) pendentes.add(r);
        }
        return pendentes;
    }
}
