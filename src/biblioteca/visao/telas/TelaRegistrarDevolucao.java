package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.modelo.Emprestimo;
import biblioteca.util.ManipuladorArquivos;
import biblioteca.visao.menus.MenuBibliotecaria;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaRegistrarDevolucao extends JFrame {

    public TelaRegistrarDevolucao(String idBibliotecaria) {
        setTitle("Registrar Devolução");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emp : ManipuladorArquivos.lerEmprestimos()) {
            if ("Ativo".equals(emp.getStatus())) {
                ativos.add(emp);
            }
        }

        JComboBox<Emprestimo> cmbEmprestimo = new JComboBox<>();
        for (Emprestimo emp : ativos) cmbEmprestimo.addItem(emp);

        JPanel painel = new JPanel(new GridLayout(2, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Empréstimo ativo:"));
        painel.add(cmbEmprestimo);

        JButton btnRegistrar = new JButton("Registrar Devolução");
        btnRegistrar.addActionListener(e -> {
            Emprestimo emprestimo = (Emprestimo) cmbEmprestimo.getSelectedItem();
            if (emprestimo == null) {
                JOptionPane.showMessageDialog(this, "Não há empréstimos ativos.");
                return;
            }
            EmprestimoControle.registrarDevolucao(emprestimo.getIdEmprestimo(), this, idBibliotecaria);
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
