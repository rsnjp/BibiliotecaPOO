package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.controle.LivroControle;
import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRegistrarEmprestimo extends JFrame {

    public TelaRegistrarEmprestimo(String idBibliotecaria) {
        setTitle("Registrar Empréstimo");
        setSize(450, 270);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Usuario> usuarios = UsuarioControle.listarUsuarios();
        List<Livro> livros = LivroControle.listarLivros();

        JComboBox<Usuario> cmbUsuario = new JComboBox<>();
        for (Usuario u : usuarios) cmbUsuario.addItem(u);

        JComboBox<Livro> cmbLivro = new JComboBox<>();
        for (Livro l : livros) cmbLivro.addItem(l);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Usuário:"));
        painel.add(cmbUsuario);

        painel.add(new JLabel("Livro:"));
        painel.add(cmbLivro);

        painel.add(new JLabel("Prazo de devolução:"));
        painel.add(new JLabel(Emprestimo.PRAZO_DIAS + " dias a partir de hoje"));

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
            Livro livro = (Livro) cmbLivro.getSelectedItem();
            if (usuario == null || livro == null) {
                JOptionPane.showMessageDialog(this, "Cadastre um usuário e um livro antes de continuar.");
                return;
            }
            if (EmprestimoControle.registrarEmprestimo(usuario, livro, this, idBibliotecaria)) {
                dispose();
                new TelaGerenciarEmprestimos(idBibliotecaria);
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGerenciarEmprestimos(idBibliotecaria);
        });

        painel.add(btnVoltar);
        painel.add(btnRegistrar);

        add(painel);
        setVisible(true);
    }
}
