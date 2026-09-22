package biblioteca.visao.telas;

import biblioteca.controle.EmprestimoControle;
import biblioteca.controle.ReservaControle;
import biblioteca.modelo.Reserva;
import biblioteca.util.Datas;
import biblioteca.visao.menus.MenuBibliotecaria;

import java.util.Date;
import java.util.List;

// Reservas ativas, ou seja, ainda pendentes da efetivação do empréstimo.
public class TelaReservasPendentes extends TelaTabela<Reserva> {

    public TelaReservasPendentes(String idBibliotecaria) {
        super("Reservas Pendentes de Empréstimo",
                new String[]{"ID", "Livro", "Usuário", "Data da reserva", "Dias aguardando"},
                () -> new MenuBibliotecaria(idBibliotecaria));

        // Efetiva o empréstimo para quem reservou; a reserva é baixada automaticamente.
        adicionarBotao("Efetivar Empréstimo", () -> {
            Reserva selecionada = itemSelecionado();
            if (selecionada != null && EmprestimoControle.efetivarReserva(selecionada, this, idBibliotecaria)) {
                recarregar();
            }
        });

        adicionarBotao("Cancelar Reserva", () -> {
            Reserva selecionada = itemSelecionado();
            if (selecionada != null && ReservaControle.cancelarReserva(selecionada, this)) {
                recarregar();
            }
        });

        exibir();
    }

    @Override
    protected List<Reserva> carregarItens() {
        return ReservaControle.listarReservasPendentes();
    }

    @Override
    protected Object[] linha(Reserva r) {
        long diasAguardando = r.getDataReserva() != null
                ? Math.max(0, Datas.diasEntre(r.getDataReserva(), new Date()))
                : 0;
        return new Object[]{
                r.getIdReserva(),
                r.getLivro().getTitulo(),
                r.getUsuario().getNome(),
                Datas.formatar(r.getDataReserva()),
                diasAguardando
        };
    }
}
