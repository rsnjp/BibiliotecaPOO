package biblioteca.visao.telas;

import biblioteca.controle.LivroControle;
import biblioteca.visao.menus.MenuBibliotecaria;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroLivro extends JFrame {

    public TelaCadastroLivro(String idBibliotecaria) {
        setTitle("Cadastro de Livro");
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();

        JLabel lblAutor = new JLabel("Autor:");
        JTextField txtAutor = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> LivroControle.cadastrarLivro(
                txtTitulo.getText().trim(),
                txtAutor.getText().trim(),
                this,
                idBibliotecaria
        ));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuBibliotecaria(idBibliotecaria);
        });

        painel.add(lblTitulo); painel.add(txtTitulo);
        painel.add(lblAutor); painel.add(txtAutor);
        painel.add(btnVoltar); painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
