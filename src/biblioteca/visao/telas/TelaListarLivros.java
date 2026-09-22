package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.controle.LivroControle;
import biblioteca.controle.ReservaControle;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.util.Datas;
import biblioteca.visao.menus.MenuBibliotecaria;
import biblioteca.visao.menus.MenuUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Lista os livros com filtro: todos, apenas os disponíveis ou apenas os
// que estão com a entrega (devolução) em atraso.
public class TelaListarLivros extends TelaTabela<Livro> {

    private static final String FILTRO_TODOS = "Todos os livros";
    private static final String FILTRO_DISPONIVEIS = "Livros disponíveis";
    private static final String FILTRO_ATRASADOS = "Livros com atraso na entrega";
    private static final String[] COLUNAS = {"ID", "Título", "Autor", "Status", "Situação"};

    private JComboBox<String> cmbFiltro;
    // Empréstimo/reserva ativos de cada livro (chave = idLivro), para a coluna "Situação".
    private final Map<Integer, Emprestimo> emprestimosAtivos = new HashMap<>();
    private final Map<Integer, Reserva> reservasAtivas = new HashMap<>();

    public TelaListarLivros(String idBibliotecaria) {
        super("Livros", COLUNAS, () -> new MenuBibliotecaria(idBibliotecaria));
        montar(FILTRO_TODOS);
    }

    public TelaListarLivros(int idUsuario) {
        super("Livros", COLUNAS, () -> new MenuUsuario(idUsuario));
        montar(FILTRO_DISPONIVEIS);
    }

    private void montar(String filtroInicial) {
        cmbFiltro = new JComboBox<>(new String[]{FILTRO_TODOS, FILTRO_DISPONIVEIS, FILTRO_ATRASADOS});
        cmbFiltro.setSelectedItem(filtroInicial);
        cmbFiltro.addActionListener(e -> recarregar());

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltro.add(new JLabel("Exibir:"));
        painelFiltro.add(cmbFiltro);
        add(painelFiltro, BorderLayout.NORTH);

        exibir();
    }

    @Override
    protected List<Livro> carregarItens() {
        emprestimosAtivos.clear();
        for (Emprestimo e : EmprestimoControle.listarEmprestimosAtivos()) {
            emprestimosAtivos.put(e.getLivro().getIdLivro(), e);
        }
        reservasAtivas.clear();
        for (Reserva r : ReservaControle.listarReservasPendentes()) {
            reservasAtivas.put(r.getLivro().getIdLivro(), r);
        }

        String filtro = (String) cmbFiltro.getSelectedItem();
        if (FILTRO_DISPONIVEIS.equals(filtro)) {
            return LivroControle.listarLivrosDisponiveis();
        }
        if (FILTRO_ATRASADOS.equals(filtro)) {
            List<Livro> atrasados = new ArrayList<>();
            for (Emprestimo e : EmprestimoControle.listarEmprestimosAtrasados()) {
                atrasados.add(e.getLivro());
            }
            return atrasados;
        }
        return LivroControle.listarLivros();
    }

    @Override
    protected Object[] linha(Livro l) {
        return new Object[]{l.getIdLivro(), l.getTitulo(), l.getAutor(), l.getStatus(), situacao(l)};
    }

    private String situacao(Livro l) {
        Emprestimo emprestimo = emprestimosAtivos.get(l.getIdLivro());
        if (emprestimo != null) {
            String texto = "Com " + emprestimo.getUsuario().getNome()
                    + " - devolver até " + Datas.formatar(emprestimo.getDataDevolucaoPrevista());
            if (emprestimo.estaAtrasado()) {
                texto = "ATRASADO " + emprestimo.getDiasAtraso() + " dia(s) - " + texto;
            }
            return texto;
        }

        Reserva reserva = reservasAtivas.get(l.getIdLivro());
        if (reserva != null) {
            return "Reservado para " + reserva.getUsuario().getNome();
        }
        return "-";
    }
}
