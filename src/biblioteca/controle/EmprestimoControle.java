package biblioteca.controle;

import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Usuario;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.Date;

public class EmprestimoControle {

    public static void registrarEmprestimo(Usuario usuario, Livro livro, JFrame tela, String idBibliotecaria) {
        if (livro == null || !livro.consultarDisponibilidade()) {
            JOptionPane.showMessageDialog(tela, "Livro indisponível para empréstimo.");
            return;
        }

        int id = ManipuladorArquivos.proximoId("Emprestimo");
        Emprestimo emprestimo = new Emprestimo(id, usuario, livro, new Date(), null, "Ativo");
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).registrarEmprestimo(emprestimo);
        livro.atualizarStatus("Emprestado");

        JOptionPane.showMessageDialog(tela, "Empréstimo registrado com sucesso!");
        tela.dispose();
        new biblioteca.visao.menus.MenuBibliotecaria(idBibliotecaria);
    }

    public static void registrarDevolucao(int idEmprestimo, JFrame tela, String idBibliotecaria) {
        boolean ok = BibliotecariaControle.obterBibliotecaria(idBibliotecaria).registrarDevolucao(idEmprestimo);

        JOptionPane.showMessageDialog(tela, ok
                ? "Devolução registrada com sucesso!"
                : "Empréstimo não encontrado.");
        tela.dispose();
        new biblioteca.visao.menus.MenuBibliotecaria(idBibliotecaria);
    }
}
