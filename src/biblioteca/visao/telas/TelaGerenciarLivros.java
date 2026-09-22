package biblioteca.visao.telas;

import biblioteca.controle.LivroControle;
import biblioteca.modelo.Livro;

import java.util.List;

public class TelaGerenciarLivros extends TelaGerenciar<Livro> {

    public TelaGerenciarLivros(String idBibliotecaria) {
        super("Gerenciar Livros", new String[]{"ID", "Título", "Autor", "Status"}, idBibliotecaria);
        exibir();
    }

    @Override
    protected List<Livro> carregarItens() {
        return LivroControle.listarLivros();
    }

    @Override
    protected Object[] linha(Livro l) {
        return new Object[]{l.getIdLivro(), l.getTitulo(), l.getAutor(), l.getStatus()};
    }

    @Override
    protected void abrirCadastro() {
        new TelaCadastroLivro(idBibliotecaria);
    }

    @Override
    protected void abrirEdicao(Livro livro) {
        new TelaCadastroLivro(idBibliotecaria, livro);
    }

    @Override
    protected boolean excluir(Livro livro) {
        return LivroControle.excluirLivro(livro, this, idBibliotecaria);
    }
}
