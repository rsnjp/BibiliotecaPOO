package biblioteca.controle;

import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;
import biblioteca.util.Datas;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimoControle {

    // Regras:
    //  - bloqueia livro já emprestado;
    //  - bloqueia livro com reserva ativa de OUTRO usuário;
    //  - se o livro está reservado para o próprio usuário, o empréstimo
    //    "baixa" (conclui) a reserva.
    public static boolean registrarEmprestimo(Usuario usuario, Livro livro, JFrame tela, String idBibliotecaria) {
        if (usuario == null || livro == null) {
            JOptionPane.showMessageDialog(tela, "Selecione o usuário e o livro.");
            return false;
        }

        Emprestimo emprestimoAtivo = livro.buscarEmprestimoAtivo();
        if (emprestimoAtivo != null || "Emprestado".equals(livro.getStatus())) {
            String detalhe = emprestimoAtivo != null
                    ? "\nEmprestado para " + emprestimoAtivo.getUsuario().getNome()
                      + " até " + Datas.formatar(emprestimoAtivo.getDataDevolucaoPrevista()) + "."
                    : "";
            JOptionPane.showMessageDialog(tela, "Empréstimo bloqueado: o livro já está emprestado." + detalhe);
            return false;
        }

        Reserva reserva = livro.buscarReservaAtiva();
        if (reserva != null && reserva.getUsuario().getIdUsuario() != usuario.getIdUsuario()) {
            JOptionPane.showMessageDialog(tela, "Empréstimo bloqueado: o livro está reservado para outro usuário ("
                    + reserva.getUsuario().getNome() + ", reserva de " + Datas.formatar(reserva.getDataReserva()) + ").");
            return false;
        }

        int id = ManipuladorArquivos.proximoId("Emprestimo");
        Date hoje = new Date();
        Emprestimo emprestimo = new Emprestimo(id, usuario, livro, hoje,
                Datas.somarDias(hoje, Emprestimo.PRAZO_DIAS), "Ativo");
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).registrarEmprestimo(emprestimo);
        livro.atualizarStatus("Emprestado");

        String mensagem = "Empréstimo registrado com sucesso!\nDevolução prevista para "
                + Datas.formatar(emprestimo.getDataDevolucaoPrevista()) + ".";
        if (reserva != null) {
            reserva.concluirReserva();
            mensagem += "\nA reserva #" + reserva.getIdReserva() + " foi baixada (Concluída).";
        }
        JOptionPane.showMessageDialog(tela, mensagem);
        return true;
    }

    // Efetiva o empréstimo de uma reserva pendente, para o usuário que reservou.
    public static boolean efetivarReserva(Reserva reserva, JFrame tela, String idBibliotecaria) {
        return registrarEmprestimo(reserva.getUsuario(), reserva.getLivro(), tela, idBibliotecaria);
    }

    // Baixa do empréstimo: informa se a devolução foi dentro do prazo ou
    // atrasada, e quantos dias de atraso.
    public static boolean registrarDevolucao(int idEmprestimo, JFrame tela, String idBibliotecaria) {
        Emprestimo emprestimo = BibliotecariaControle.obterBibliotecaria(idBibliotecaria).registrarDevolucao(idEmprestimo);
        if (emprestimo == null) {
            JOptionPane.showMessageDialog(tela, "Empréstimo ativo não encontrado.");
            return false;
        }

        long diasAtraso = emprestimo.getDiasAtraso();
        String resumo = "Devolução registrada com sucesso!\n\n"
                + "Livro: " + emprestimo.getLivro().getTitulo() + "\n"
                + "Usuário: " + emprestimo.getUsuario().getNome() + "\n"
                + "Devolução prevista: " + Datas.formatar(emprestimo.getDataDevolucaoPrevista()) + "\n"
                + "Devolução efetiva: " + Datas.formatar(emprestimo.getDataDevolucaoEfetiva()) + "\n\n";

        if (diasAtraso > 0) {
            JOptionPane.showMessageDialog(tela, resumo + "Situação: DEVOLUÇÃO ATRASADA\nDias em atraso: " + diasAtraso,
                    "Devolução", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(tela, resumo + "Situação: devolução dentro do prazo.",
                    "Devolução", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }

    public static boolean editarEmprestimo(Emprestimo emprestimo, Usuario usuario, String textoDataEmprestimo,
                                           String textoDataPrevista, JFrame tela) {
        if (usuario == null) {
            JOptionPane.showMessageDialog(tela, "Selecione o usuário.");
            return false;
        }

        Date dataEmprestimo = Datas.converter(textoDataEmprestimo);
        Date dataPrevista = Datas.converter(textoDataPrevista);
        if (dataEmprestimo == null || dataPrevista == null) {
            JOptionPane.showMessageDialog(tela, "Informe as datas no formato dd/MM/aaaa.");
            return false;
        }
        if (dataPrevista.before(dataEmprestimo)) {
            JOptionPane.showMessageDialog(tela, "A devolução prevista não pode ser anterior à data do empréstimo.");
            return false;
        }

        emprestimo.atualizarDados(usuario, dataEmprestimo, dataPrevista);
        JOptionPane.showMessageDialog(tela, "Empréstimo atualizado com sucesso!");
        return true;
    }

    public static boolean excluirEmprestimo(Emprestimo emprestimo, JFrame tela, String idBibliotecaria) {
        String aviso = emprestimo.estaAtivo() ? " (o livro voltará a ficar disponível)" : "";
        if (!Validacoes.confirmarExclusao(tela, "o empréstimo #" + emprestimo.getIdEmprestimo() + aviso)) return false;

        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).excluirEmprestimo(emprestimo);
        JOptionPane.showMessageDialog(tela, "Empréstimo excluído com sucesso!");
        return true;
    }

    public static List<Emprestimo> listarEmprestimos() {
        return ManipuladorArquivos.lerEmprestimos();
    }

    public static List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo e : listarEmprestimos()) {
            if (e.estaAtivo()) ativos.add(e);
        }
        return ativos;
    }

    // Empréstimos ainda não devolvidos cujo prazo já venceu.
    public static List<Emprestimo> listarEmprestimosAtrasados() {
        List<Emprestimo> atrasados = new ArrayList<>();
        for (Emprestimo e : listarEmprestimos()) {
            if (e.estaAtrasado()) atrasados.add(e);
        }
        return atrasados;
    }
}
