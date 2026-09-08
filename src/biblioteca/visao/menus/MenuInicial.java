package biblioteca.visao.menus;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.modelo.Usuario;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuInicial extends JFrame {

    public MenuInicial() {
        setTitle("Sistema Biblioteca - Seleção de Perfil");
        setSize(400, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] perfis = {"Bibliotecária", "Usuário"};

        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lbl = new JLabel("Selecione o perfil de acesso:");
        JComboBox<String> comboPerfil = new JComboBox<>(perfis);
        JButton btnEntrar = new JButton("Entrar");

        btnEntrar.addActionListener(e -> {
            String perfil = (String) comboPerfil.getSelectedItem();
            dispose();
            selecionarUsuario(perfil);
        });

        painel.add(lbl);
        painel.add(comboPerfil);
        painel.add(btnEntrar);

        add(painel);
        setVisible(true);
    }

    private void selecionarUsuario(String perfil) {
        if ("Bibliotecária".equals(perfil)) {
            selecionarBibliotecaria();
        } else {
            selecionarUsuarioComum();
        }
    }

    private void selecionarBibliotecaria() {
        List<Bibliotecaria> lista = ManipuladorArquivos.lerBibliotecarias();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma bibliotecária cadastrada.");
            new MenuInicial();
            return;
        }

        String[] opcoes = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Bibliotecaria b = lista.get(i);
            opcoes[i] = b.getNome() + " (ID: " + b.getIdBibliotecaria() + ")";
        }

        String escolha = (String) JOptionPane.showInputDialog(null,
                "Selecione a bibliotecária:", "Login", JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]);
        if (escolha == null) {
            new MenuInicial();
            return;
        }

        String id = escolha.split("ID: ")[1].replace(")", "");
        new MenuBibliotecaria(id);
    }

    private void selecionarUsuarioComum() {
        List<Usuario> lista = ManipuladorArquivos.lerUsuarios();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
            new MenuInicial();
            return;
        }

        String[] opcoes = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Usuario u = lista.get(i);
            opcoes[i] = u.getNome() + " (ID: " + u.getIdUsuario() + ")";
        }

        String escolha = (String) JOptionPane.showInputDialog(null,
                "Selecione o usuário:", "Login", JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]);
        if (escolha == null) {
            new MenuInicial();
            return;
        }

        int id = Integer.parseInt(escolha.split("ID: ")[1].replace(")", ""));
        new MenuUsuario(id);
    }
}
