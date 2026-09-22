package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Usuario;
import biblioteca.util.Datas;

import javax.swing.*;
import java.awt.*;

public class TelaEditarEmprestimo extends JFrame {

    public TelaEditarEmprestimo(String idBibliotecaria, Emprestimo emprestimo) {
        setTitle("Editar Empréstimo #" + emprestimo.getIdEmprestimo());
        setSize(450, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JComboBox<Usuario> cmbUsuario = new JComboBox<>();
        for (Usuario u : UsuarioControle.listarUsuarios()) {
            cmbUsuario.addItem(u);
            if (u.getIdUsuario() == emprestimo.getUsuario().getIdUsuario()) {
                cmbUsuario.setSelectedItem(u);
            }
        }

        // O livro não é editável: para trocar o livro, exclua e registre outro empréstimo.
        JTextField txtLivro = new JTextField(emprestimo.getLivro().getTitulo());
        txtLivro.setEditable(false);

        JTextField txtDataEmprestimo = new JTextField(Datas.formatar(emprestimo.getDataEmprestimo()));
        JTextField txtDataPrevista = new JTextField(Datas.formatar(emprestimo.getDataDevolucaoPrevista()));

        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Usuário:"));
        painel.add(cmbUsuario);

        painel.add(new JLabel("Livro:"));
        painel.add(txtLivro);

        painel.add(new JLabel("Data do empréstimo (dd/MM/aaaa):"));
        painel.add(txtDataEmprestimo);

        painel.add(new JLabel("Devolução prevista (dd/MM/aaaa):"));
        painel.add(txtDataPrevista);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            boolean ok = EmprestimoControle.editarEmprestimo(emprestimo,
                    (Usuario) cmbUsuario.getSelectedItem(),
                    txtDataEmprestimo.getText(),
                    txtDataPrevista.getText(),
                    this);
            if (ok) {
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
        painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
