package biblioteca.visao.telas;

import biblioteca.controle.ReservaControle;
import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Reserva;
import biblioteca.visao.menus.MenuUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMinhasReservas extends JFrame {

    public TelaMinhasReservas(int idUsuario) {
        setTitle("Minhas Reservas");
        setSize(550, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Reserva> reservas = UsuarioControle.obterUsuario(idUsuario).listarReservas();
        DefaultListModel<Reserva> modelo = new DefaultListModel<>();
        for (Reserva r : reservas) modelo.addElement(r);

        JList<Reserva> lista = new JList<>(modelo);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton btnCancelar = new JButton("Cancelar Reserva Selecionada");
        btnCancelar.addActionListener(e -> {
            Reserva selecionada = lista.getSelectedValue();
            if (selecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma reserva.");
                return;
            }
            if (ReservaControle.cancelarReserva(selecionada.getIdReserva(), this, idUsuario)) {
                dispose();
                new TelaMinhasReservas(idUsuario);
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuUsuario(idUsuario);
        });

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }
}
