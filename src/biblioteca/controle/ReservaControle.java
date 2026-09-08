package biblioteca.controle;

import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.Date;

public class ReservaControle {

    public static void registrarReserva(int idUsuario, int idLivro, JFrame tela, String idBibliotecaria) {
        Livro livro = LivroControle.obterLivro(idLivro);
        if (livro == null) {
            JOptionPane.showMessageDialog(tela, "Livro não encontrado.");
            return;
        }

        int id = ManipuladorArquivos.proximoId("Reserva");
        Reserva reserva = new Reserva(id, new Date(), "Ativa", idUsuario, idLivro);
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).registrarReserva(reserva);

        JOptionPane.showMessageDialog(tela, "Reserva registrada com sucesso!");
        tela.dispose();
        new biblioteca.visao.menus.MenuBibliotecaria(idBibliotecaria);
    }

    public static void cancelarReserva(int idReserva, JFrame tela, int idUsuario) {
        Usuario usuario = UsuarioControle.obterUsuario(idUsuario);
        boolean ok = usuario.cancelarReserva(idReserva);

        JOptionPane.showMessageDialog(tela, ok
                ? "Reserva cancelada com sucesso!"
                : "Reserva não encontrada.");
        tela.dispose();
        new biblioteca.visao.menus.MenuUsuario(idUsuario);
    }
}
