package biblioteca.visao.menus;

import biblioteca.visao.telas.TelaListarLivros;
import biblioteca.visao.telas.TelaMeusEmprestimos;
import biblioteca.visao.telas.TelaMinhasReservas;

import javax.swing.*;
import java.awt.*;

public class MenuUsuario extends JFrame {

    private final int idUsuario;

    public MenuUsuario(int idUsuario) {
        this.idUsuario = idUsuario;

        setTitle("Menu - Usuário " + idUsuario);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnLivros = new JButton("Ver Livros Disponíveis");
        btnLivros.addActionListener(e -> {
            dispose();
            new TelaListarLivros(idUsuario);
        });

        JButton btnEmprestimos = new JButton("Meus Empréstimos");
        btnEmprestimos.addActionListener(e -> {
            dispose();
            new TelaMeusEmprestimos(idUsuario);
        });

        JButton btnReservas = new JButton("Minhas Reservas");
        btnReservas.addActionListener(e -> {
            dispose();
            new TelaMinhasReservas(idUsuario);
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new MenuInicial();
        });

        painel.add(btnLivros);
        painel.add(btnEmprestimos);
        painel.add(btnReservas);
        painel.add(btnSair);

        add(painel);
        setVisible(true);
    }
}
