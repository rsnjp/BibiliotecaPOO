package biblioteca.visao.menus;

import biblioteca.visao.telas.TelaCadastroLivro;
import biblioteca.visao.telas.TelaCadastroUsuario;
import biblioteca.visao.telas.TelaListarLivros;
import biblioteca.visao.telas.TelaRegistrarDevolucao;
import biblioteca.visao.telas.TelaRegistrarEmprestimo;
import biblioteca.visao.telas.TelaRegistrarReserva;

import javax.swing.*;
import java.awt.*;

public class MenuBibliotecaria extends JFrame {

    public MenuBibliotecaria(String idBibliotecaria) {
        setTitle("Menu - Bibliotecária " + idBibliotecaria);
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(7, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnCadastrarLivro = new JButton("Cadastrar Livro");
        btnCadastrarLivro.addActionListener(e -> {
            dispose();
            new TelaCadastroLivro(idBibliotecaria);
        });

        JButton btnCadastrarUsuario = new JButton("Cadastrar Usuário");
        btnCadastrarUsuario.addActionListener(e -> {
            dispose();
            new TelaCadastroUsuario(idBibliotecaria);
        });

        JButton btnEmprestimo = new JButton("Registrar Empréstimo");
        btnEmprestimo.addActionListener(e -> {
            dispose();
            new TelaRegistrarEmprestimo(idBibliotecaria);
        });

        JButton btnDevolucao = new JButton("Registrar Devolução");
        btnDevolucao.addActionListener(e -> {
            dispose();
            new TelaRegistrarDevolucao(idBibliotecaria);
        });

        JButton btnReserva = new JButton("Registrar Reserva");
        btnReserva.addActionListener(e -> {
            dispose();
            new TelaRegistrarReserva(idBibliotecaria);
        });

        JButton btnLivros = new JButton("Listar Livros");
        btnLivros.addActionListener(e -> {
            dispose();
            new TelaListarLivros(idBibliotecaria);
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new MenuInicial();
        });

        painel.add(btnCadastrarLivro);
        painel.add(btnCadastrarUsuario);
        painel.add(btnEmprestimo);
        painel.add(btnDevolucao);
        painel.add(btnReserva);
        painel.add(btnLivros);
        painel.add(btnSair);

        add(painel);
        setVisible(true);
    }
}
