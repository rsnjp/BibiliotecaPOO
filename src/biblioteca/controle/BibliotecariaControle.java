package biblioteca.controle;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.List;

public class BibliotecariaControle {

    public static boolean cadastrarBibliotecaria(String nome, String turno, JFrame tela, String idBibliotecaria) {
        if (!Validacoes.camposValidos(tela, nome, turno)) return false;

        String id = ManipuladorArquivos.proximoIdBibliotecaria();
        Bibliotecaria nova = new Bibliotecaria(id, nome, turno);
        obterBibliotecaria(idBibliotecaria).cadastrarBibliotecaria(nova);

        JOptionPane.showMessageDialog(tela, "Bibliotecária cadastrada com sucesso! (ID: " + id + ")");
        return true;
    }

    public static boolean editarBibliotecaria(Bibliotecaria bibliotecaria, String nome, String turno, JFrame tela) {
        if (!Validacoes.camposValidos(tela, nome, turno)) return false;

        bibliotecaria.atualizarDados(nome, turno);
        JOptionPane.showMessageDialog(tela, "Bibliotecária atualizada com sucesso!");
        return true;
    }

    public static boolean excluirBibliotecaria(Bibliotecaria bibliotecaria, JFrame tela, String idBibliotecaria) {
        if (bibliotecaria.getIdBibliotecaria().equals(idBibliotecaria)) {
            JOptionPane.showMessageDialog(tela, "Você não pode excluir a bibliotecária que está logada.");
            return false;
        }
        if (!Validacoes.confirmarExclusao(tela, "a bibliotecária \"" + bibliotecaria.getNome() + "\"")) return false;

        obterBibliotecaria(idBibliotecaria).excluirBibliotecaria(bibliotecaria);
        JOptionPane.showMessageDialog(tela, "Bibliotecária excluída com sucesso!");
        return true;
    }

    public static Bibliotecaria obterBibliotecaria(String idBibliotecaria) {
        for (Bibliotecaria b : ManipuladorArquivos.lerBibliotecarias()) {
            if (b.getIdBibliotecaria().equals(idBibliotecaria)) {
                return b;
            }
        }
        return null;
    }

    public static List<Bibliotecaria> listarBibliotecarias() {
        return ManipuladorArquivos.lerBibliotecarias();
    }
}
