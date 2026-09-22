package biblioteca.controle;

import biblioteca.modelo.Livro;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LivroControle {

    public static boolean cadastrarLivro(String titulo, String autor, JFrame tela, String idBibliotecaria) {
        if (!Validacoes.camposValidos(tela, titulo, autor)) return false;

        int id = ManipuladorArquivos.proximoId("Livro");
        Livro livro = new Livro(id, titulo, autor, "Disponível");
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).cadastrarLivro(livro);

        JOptionPane.showMessageDialog(tela, "Livro cadastrado com sucesso!");
        return true;
    }

    public static boolean editarLivro(Livro livro, String titulo, String autor, JFrame tela) {
        if (!Validacoes.camposValidos(tela, titulo, autor)) return false;

        livro.atualizarDados(titulo, autor);
        JOptionPane.showMessageDialog(tela, "Livro atualizado com sucesso!");
        return true;
    }

    public static boolean excluirLivro(Livro livro, JFrame tela, String idBibliotecaria) {
        if (livro.possuiMovimentacoes()) {
            JOptionPane.showMessageDialog(tela, "Não é possível excluir: o livro possui empréstimos ou reservas registrados.\n"
                    + "Exclua esses registros antes de excluir o livro.");
            return false;
        }
        if (!Validacoes.confirmarExclusao(tela, "o livro \"" + livro.getTitulo() + "\"")) return false;

        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).excluirLivro(livro);
        JOptionPane.showMessageDialog(tela, "Livro excluído com sucesso!");
        return true;
    }

    public static Livro obterLivro(int idLivro) {
        return ManipuladorArquivos.buscarLivroPorId(idLivro);
    }

    public static List<Livro> listarLivros() {
        return ManipuladorArquivos.lerLivros();
    }

    public static List<Livro> listarLivrosDisponiveis() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro l : listarLivros()) {
            if (l.consultarDisponibilidade()) {
                disponiveis.add(l);
            }
        }
        return disponiveis;
    }
}
