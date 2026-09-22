package biblioteca.visao.telas;

import biblioteca.controle.ReservaControle;
import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;
import biblioteca.util.Datas;

import javax.swing.*;
import java.awt.*;

public class TelaEditarReserva extends JFrame {

    public TelaEditarReserva(String idBibliotecaria, Reserva reserva) {
        setTitle("Editar Reserva #" + reserva.getIdReserva());
        setSize(450, 260);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JComboBox<Usuario> cmbUsuario = new JComboBox<>();
        for (Usuario u : UsuarioControle.listarUsuarios()) {
            cmbUsuario.addItem(u);
            if (u.getIdUsuario() == reserva.getUsuario().getIdUsuario()) {
                cmbUsuario.setSelectedItem(u);
            }
        }

        // O livro não é editável: para trocar o livro, exclua e registre outra reserva.
        JTextField txtLivro = new JTextField(reserva.getLivro().getTitulo());
        txtLivro.setEditable(false);

        JTextField txtDataReserva = new JTextField(Datas.formatar(reserva.getDataReserva()));

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Usuário:"));
        painel.add(cmbUsuario);

        painel.add(new JLabel("Livro:"));
        painel.add(txtLivro);

        painel.add(new JLabel("Data da reserva (dd/MM/aaaa):"));
        painel.add(txtDataReserva);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            boolean ok = ReservaControle.editarReserva(reserva,
                    (Usuario) cmbUsuario.getSelectedItem(),
                    txtDataReserva.getText(),
                    this);
            if (ok) {
                dispose();
                new TelaGerenciarReservas(idBibliotecaria);
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGerenciarReservas(idBibliotecaria);
        });

        painel.add(btnVoltar);
        painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
