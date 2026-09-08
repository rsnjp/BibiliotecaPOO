package biblioteca.visao.telas;

import biblioteca.controle.LivroControle;
import biblioteca.modelo.Livro;
import biblioteca.visao.menus.MenuBibliotecaria;
import biblioteca.visao.menus.MenuUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarLivros extends JFrame {

    public TelaListarLivros(String idBibliotecaria) {
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuBibliotecaria(idBibliotecaria);
        });
        montar(btnVoltar);
    }

    public TelaListarLivros(int idUsuario) {
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuUsuario(idUsuario);
        });
        montar(btnVoltar);
    }

    private void montar(JButton btnVoltar) {
        setTitle("Livros");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Livro> livros = LivroControle.listarLivros();
        DefaultListModel<Livro> modeloLista = new DefaultListModel<>();
        for (Livro l : livros) modeloLista.addElement(l);

        JList<Livro> lista = new JList<>(modeloLista);
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(btnVoltar, BorderLayout.SOUTH);

        setVisible(true);
    }
}
