package biblioteca.visao.telas;

import biblioteca.controle.LivroControle;
import biblioteca.modelo.Livro;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroLivro extends JFrame {

    // Cadastro de um novo livro.
    public TelaCadastroLivro(String idBibliotecaria) {
        this(idBibliotecaria, null);
    }

    // Edição: com um livro informado, os campos já vêm preenchidos.
    public TelaCadastroLivro(String idBibliotecaria, Livro livro) {
        setTitle(livro == null ? "Cadastro de Livro" : "Editar Livro #" + livro.getIdLivro());
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();

        JLabel lblAutor = new JLabel("Autor:");
        JTextField txtAutor = new JTextField();

        if (livro != null) {
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
        }

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            boolean ok = livro == null
                    ? LivroControle.cadastrarLivro(titulo, autor, this, idBibliotecaria)
                    : LivroControle.editarLivro(livro, titulo, autor, this);
            if (ok) {
                dispose();
                new TelaGerenciarLivros(idBibliotecaria);
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGerenciarLivros(idBibliotecaria);
        });

        painel.add(lblTitulo); painel.add(txtTitulo);
        painel.add(lblAutor); painel.add(txtAutor);
        painel.add(btnVoltar); painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
