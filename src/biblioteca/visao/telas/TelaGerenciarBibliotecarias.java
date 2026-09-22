package biblioteca.visao.telas;

import biblioteca.controle.BibliotecariaControle;
import biblioteca.modelo.Bibliotecaria;

import java.util.List;

public class TelaGerenciarBibliotecarias extends TelaGerenciar<Bibliotecaria> {

    public TelaGerenciarBibliotecarias(String idBibliotecaria) {
        super("Gerenciar Bibliotecárias", new String[]{"ID", "Nome", "Turno"}, idBibliotecaria);
        exibir();
    }

    @Override
    protected List<Bibliotecaria> carregarItens() {
        return BibliotecariaControle.listarBibliotecarias();
    }

    @Override
    protected Object[] linha(Bibliotecaria b) {
        return new Object[]{b.getIdBibliotecaria(), b.getNome(), b.getTurno()};
    }

    @Override
    protected void abrirCadastro() {
        new TelaCadastroBibliotecaria(idBibliotecaria);
    }

    @Override
    protected void abrirEdicao(Bibliotecaria bibliotecaria) {
        new TelaCadastroBibliotecaria(idBibliotecaria, bibliotecaria);
    }

    @Override
    protected boolean excluir(Bibliotecaria bibliotecaria) {
        return BibliotecariaControle.excluirBibliotecaria(bibliotecaria, this, idBibliotecaria);
    }
}
