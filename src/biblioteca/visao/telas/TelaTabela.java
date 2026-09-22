package biblioteca.visao.telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Base das telas que exibem uma lista de registros em tabela, com uma barra
 * de botões embaixo. Cada subclasse diz quais itens carregar e como cada item
 * vira uma linha da tabela.
 *
 * As subclasses devem chamar exibir() no fim do próprio construtor, depois de
 * inicializar seus campos, pois é ali que os itens são carregados.
 */
public abstract class TelaTabela<T> extends JFrame {

    private final DefaultTableModel modeloTabela;
    private final JTable tabela;
    private final JPanel painelBotoes;
    private List<T> itens = new ArrayList<>();

    protected TelaTabela(String titulo, String[] colunas, Runnable acaoVoltar) {
        setTitle(titulo);
        setSize(800, 420);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            acaoVoltar.run();
        });
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    protected abstract List<T> carregarItens();

    protected abstract Object[] linha(T item);

    protected void exibir() {
        recarregar();
        setVisible(true);
    }

    // Relê os dados dos arquivos e redesenha a tabela.
    protected void recarregar() {
        itens = carregarItens();
        modeloTabela.setRowCount(0);
        for (T item : itens) {
            modeloTabela.addRow(linha(item));
        }
    }

    // Botões extras ficam antes do "Voltar".
    protected void adicionarBotao(String texto, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(e -> acao.run());
        painelBotoes.add(botao, painelBotoes.getComponentCount() - 1);
    }

    // Item da linha selecionada, ou null (avisando o usuário) se nada estiver selecionado.
    protected T itemSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela.");
            return null;
        }
        return itens.get(linha);
    }
}
