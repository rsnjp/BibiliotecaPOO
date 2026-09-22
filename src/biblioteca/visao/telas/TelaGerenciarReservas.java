package biblioteca.visao.telas;

import biblioteca.controle.ReservaControle;
import biblioteca.modelo.Reserva;
import biblioteca.util.Datas;

import java.util.List;

public class TelaGerenciarReservas extends TelaGerenciar<Reserva> {

    public TelaGerenciarReservas(String idBibliotecaria) {
        super("Gerenciar Reservas", new String[]{"ID", "Livro", "Usuário", "Data da reserva", "Status"}, idBibliotecaria);

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
        return ReservaControle.listarReservas();
    }

    @Override
    protected Object[] linha(Reserva r) {
        return new Object[]{
                r.getIdReserva(),
                r.getLivro().getTitulo(),
                r.getUsuario().getNome(),
                Datas.formatar(r.getDataReserva()),
                r.getStatus()
        };
    }

    @Override
    protected void abrirCadastro() {
        new TelaRegistrarReserva(idBibliotecaria);
    }

    @Override
    protected void abrirEdicao(Reserva reserva) {
        new TelaEditarReserva(idBibliotecaria, reserva);
    }

    @Override
    protected boolean excluir(Reserva reserva) {
        return ReservaControle.excluirReserva(reserva, this, idBibliotecaria);
    }
}
