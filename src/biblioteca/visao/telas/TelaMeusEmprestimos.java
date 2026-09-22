package biblioteca.visao.telas;

import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Emprestimo;
import biblioteca.visao.menus.MenuUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMeusEmprestimos extends JFrame {

    public TelaMeusEmprestimos(int idUsuario) {
        setTitle("Meus Empréstimos");
        setSize(650, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Emprestimo> emprestimos = UsuarioControle.obterUsuario(idUsuario).listarEmprestimos();
        DefaultListModel<Emprestimo> modelo = new DefaultListModel<>();
        for (Emprestimo e : emprestimos) modelo.addElement(e);

        JList<Emprestimo> lista = new JList<>(modelo);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuUsuario(idUsuario);
        });
        add(btnVoltar, BorderLayout.SOUTH);

        setVisible(true);
    }
}
