package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.controle.LivroControle;
import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Usuario;
import biblioteca.visao.menus.MenuBibliotecaria;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRegistrarEmprestimo extends JFrame {

    public TelaRegistrarEmprestimo(String idBibliotecaria) {
        setTitle("Registrar Empréstimo");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Usuario> usuarios = UsuarioControle.listarUsuarios();
        List<Livro> livros = LivroControle.listarLivros();

        JComboBox<Usuario> cmbUsuario = new JComboBox<>();
        for (Usuario u : usuarios) cmbUsuario.addItem(u);

        JComboBox<Livro> cmbLivro = new JComboBox<>();
        for (Livro l : livros) cmbLivro.addItem(l);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Usuário:"));
        painel.add(cmbUsuario);

        painel.add(new JLabel("Livro:"));
        painel.add(cmbLivro);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
            Livro livro = (Livro) cmbLivro.getSelectedItem();
            if (usuario == null || livro == null) {
                JOptionPane.showMessageDialog(this, "Cadastre um usuário e um livro antes de continuar.");
                return;
            }
            EmprestimoControle.registrarEmprestimo(usuario.getIdUsuario(), livro.getIdLivro(), this, idBibliotecaria);
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuBibliotecaria(idBibliotecaria);
        });

        painel.add(btnVoltar);
        painel.add(btnRegistrar);

        add(painel);
        setVisible(true);
    }
}
