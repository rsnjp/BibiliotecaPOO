package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.modelo.Emprestimo;
import biblioteca.util.Datas;

import javax.swing.*;
import java.util.List;

public class TelaGerenciarEmprestimos extends TelaGerenciar<Emprestimo> {

    public TelaGerenciarEmprestimos(String idBibliotecaria) {
        super("Gerenciar Empréstimos",
                new String[]{"ID", "Livro", "Usuário", "Empréstimo", "Devolução prevista", "Devolvido em", "Status", "Atraso"},
                idBibliotecaria);

        adicionarBotao("Registrar Devolução", () -> {
            Emprestimo selecionado = itemSelecionado();
            if (selecionado == null) return;
            if (!selecionado.estaAtivo()) {
                JOptionPane.showMessageDialog(this, "Este empréstimo já foi devolvido.");
                return;
            }
            if (EmprestimoControle.registrarDevolucao(selecionado.getIdEmprestimo(), this, idBibliotecaria)) {
                recarregar();
            }
        });

        exibir();
    }

    @Override
    protected List<Emprestimo> carregarItens() {
        return EmprestimoControle.listarEmprestimos();
    }

    @Override
    protected Object[] linha(Emprestimo e) {
        long diasAtraso = e.getDiasAtraso();
        String atraso = diasAtraso > 0 ? diasAtraso + " dia(s)" : "-";
        return new Object[]{
                e.getIdEmprestimo(),
                e.getLivro().getTitulo(),
                e.getUsuario().getNome(),
                Datas.formatar(e.getDataEmprestimo()),
                Datas.formatar(e.getDataDevolucaoPrevista()),
                Datas.formatar(e.getDataDevolucaoEfetiva()),
                e.getStatus(),
                atraso
        };
    }

    @Override
    protected void abrirCadastro() {
        new TelaRegistrarEmprestimo(idBibliotecaria);
    }

    @Override
    protected void abrirEdicao(Emprestimo emprestimo) {
        new TelaEditarEmprestimo(idBibliotecaria, emprestimo);
    }

    @Override
    protected boolean excluir(Emprestimo emprestimo) {
        return EmprestimoControle.excluirEmprestimo(emprestimo, this, idBibliotecaria);
    }
}
