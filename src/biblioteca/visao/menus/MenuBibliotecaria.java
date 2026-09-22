package biblioteca.visao.menus;

import biblioteca.visao.telas.TelaGerenciarBibliotecarias;
import biblioteca.visao.telas.TelaGerenciarEmprestimos;
import biblioteca.visao.telas.TelaGerenciarLivros;
import biblioteca.visao.telas.TelaGerenciarReservas;
import biblioteca.visao.telas.TelaGerenciarUsuarios;
import biblioteca.visao.telas.TelaListarLivros;
import biblioteca.visao.telas.TelaRegistrarDevolucao;
import biblioteca.visao.telas.TelaReservasPendentes;

import javax.swing.*;
import java.awt.*;

public class MenuBibliotecaria extends JFrame {

    public MenuBibliotecaria(String idBibliotecaria) {
        setTitle("Menu - Bibliotecária " + idBibliotecaria);
        setSize(400, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(9, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Cadastrar / Editar / Excluir / Listar de cada entidade
        JButton btnLivros = new JButton("Gerenciar Livros");
        btnLivros.addActionListener(e -> {
            dispose();
            new TelaGerenciarLivros(idBibliotecaria);
        });

        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        btnUsuarios.addActionListener(e -> {
            dispose();
            new TelaGerenciarUsuarios(idBibliotecaria);
        });

        JButton btnBibliotecarias = new JButton("Gerenciar Bibliotecárias");
        btnBibliotecarias.addActionListener(e -> {
            dispose();
            new TelaGerenciarBibliotecarias(idBibliotecaria);
        });

        JButton btnEmprestimos = new JButton("Gerenciar Empréstimos");
        btnEmprestimos.addActionListener(e -> {
            dispose();
            new TelaGerenciarEmprestimos(idBibliotecaria);
        });

        JButton btnReservas = new JButton("Gerenciar Reservas");
        btnReservas.addActionListener(e -> {
            dispose();
            new TelaGerenciarReservas(idBibliotecaria);
        });

        JButton btnDevolucao = new JButton("Registrar Devolução");
        btnDevolucao.addActionListener(e -> {
            dispose();
            new TelaRegistrarDevolucao(idBibliotecaria);
        });

        JButton btnPendentes = new JButton("Reservas Pendentes de Empréstimo");
        btnPendentes.addActionListener(e -> {
            dispose();
            new TelaReservasPendentes(idBibliotecaria);
        });

        JButton btnListarLivros = new JButton("Livros Disponíveis / Com Atraso");
        btnListarLivros.addActionListener(e -> {
            dispose();
            new TelaListarLivros(idBibliotecaria);
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new MenuInicial();
        });

        painel.add(btnLivros);
        painel.add(btnUsuarios);
        painel.add(btnBibliotecarias);
        painel.add(btnEmprestimos);
        painel.add(btnReservas);
        painel.add(btnDevolucao);
        painel.add(btnPendentes);
        painel.add(btnListarLivros);
        painel.add(btnSair);

        add(painel);
        setVisible(true);
    }
}
