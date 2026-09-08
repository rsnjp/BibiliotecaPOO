package biblioteca.controle;

import biblioteca.modelo.Livro;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.List;

public class LivroControle {

    public static void cadastrarLivro(String titulo, String autor, JFrame tela, String idBibliotecaria) {
        if (titulo.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha todos os campos.");
            return;
        }

        int id = ManipuladorArquivos.proximoId("Livro");
        Livro livro = new Livro(id, titulo, autor, "Disponível");
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).cadastrarLivro(livro);

        JOptionPane.showMessageDialog(tela, "Livro cadastrado com sucesso!");
        tela.dispose();
        new biblioteca.visao.menus.MenuBibliotecaria(idBibliotecaria);
    }

    public static Livro obterLivro(int idLivro) {
        return ManipuladorArquivos.buscarLivroPorId(idLivro);
    }

    public static List<Livro> listarLivros() {
        return ManipuladorArquivos.lerLivros();
    }
}
