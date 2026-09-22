package biblioteca.visao.telas;

import biblioteca.controle.BibliotecariaControle;
import biblioteca.modelo.Bibliotecaria;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroBibliotecaria extends JFrame {

    private static final String[] TURNOS = {"Manhã", "Tarde", "Noite"};

    // Cadastro de uma nova bibliotecária.
    public TelaCadastroBibliotecaria(String idBibliotecaria) {
        this(idBibliotecaria, null);
    }

    // Edição: com uma bibliotecária informada, os campos já vêm preenchidos.
    public TelaCadastroBibliotecaria(String idBibliotecaria, Bibliotecaria bibliotecaria) {
        setTitle(bibliotecaria == null ? "Cadastro de Bibliotecária"
                : "Editar Bibliotecária " + bibliotecaria.getIdBibliotecaria());
        setSize(350, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

        JLabel lblTurno = new JLabel("Turno:");
        JComboBox<String> cmbTurno = new JComboBox<>(TURNOS);
        cmbTurno.setEditable(true); // permite digitar outro turno

        if (bibliotecaria != null) {
            txtNome.setText(bibliotecaria.getNome());
            cmbTurno.setSelectedItem(bibliotecaria.getTurno());
        }

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            Object turnoSelecionado = cmbTurno.getSelectedItem();
            String turno = turnoSelecionado != null ? turnoSelecionado.toString().trim() : "";
            boolean ok = bibliotecaria == null
                    ? BibliotecariaControle.cadastrarBibliotecaria(nome, turno, this, idBibliotecaria)
                    : BibliotecariaControle.editarBibliotecaria(bibliotecaria, nome, turno, this);
            if (ok) {
                dispose();
                new TelaGerenciarBibliotecarias(idBibliotecaria);
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGerenciarBibliotecarias(idBibliotecaria);
        });

        painel.add(lblNome); painel.add(txtNome);
        painel.add(lblTurno); painel.add(cmbTurno);
        painel.add(btnVoltar); painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
