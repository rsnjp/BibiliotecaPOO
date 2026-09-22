package biblioteca.visao.telas;

import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {

    // Cadastro de um novo usuário.
    public TelaCadastroUsuario(String idBibliotecaria) {
        this(idBibliotecaria, null);
    }

    // Edição: com um usuário informado, os campos já vêm preenchidos.
    public TelaCadastroUsuario(String idBibliotecaria, Usuario usuario) {
        setTitle(usuario == null ? "Cadastro de Usuário" : "Editar Usuário #" + usuario.getIdUsuario());
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

        if (usuario != null) {
            txtNome.setText(usuario.getNome());
            txtTelefone.setText(usuario.getTelefone());
            txtEmail.setText(usuario.getEmail());
        }

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            boolean ok = usuario == null
                    ? UsuarioControle.cadastrarUsuario(nome, telefone, email, this, idBibliotecaria)
                    : UsuarioControle.editarUsuario(usuario, nome, telefone, email, this);
            if (ok) {
                dispose();
                new TelaGerenciarUsuarios(idBibliotecaria);
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGerenciarUsuarios(idBibliotecaria);
        });

        painel.add(lblNome); painel.add(txtNome);
        painel.add(lblTelefone); painel.add(txtTelefone);
        painel.add(lblEmail); painel.add(txtEmail);
        painel.add(btnVoltar); painel.add(btnSalvar);

        add(painel);
        setVisible(true);
    }
}
