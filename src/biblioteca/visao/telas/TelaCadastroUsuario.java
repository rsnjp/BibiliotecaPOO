package biblioteca.visao.telas;

import biblioteca.controle.UsuarioControle;
import biblioteca.visao.menus.MenuBibliotecaria;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {

    public TelaCadastroUsuario(String idBibliotecaria) {
        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> UsuarioControle.cadastrarUsuario(
                txtNome.getText().trim(),
                txtTelefone.getText().trim(),
                txtEmail.getText().trim(),
                this,
                idBibliotecaria
        ));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new MenuBibliotecaria(idBibliotecaria);
        });

        painel.add(lblNome); painel.add(txtNome);
        painel.add(lblTelefone); painel.add(txtTelefone);
        painel.add(lblEmail); painel.add(txtEmail);
        painel.add(btnVoltar); painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
