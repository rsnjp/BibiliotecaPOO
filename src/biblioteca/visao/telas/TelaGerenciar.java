package biblioteca.visao.telas;

import biblioteca.visao.menus.MenuBibliotecaria;

/**
 * Tela de CRUD da bibliotecária: lista os registros (Listar) e oferece os
 * botões Novo (Cadastrar), Editar e Excluir.
 */
public abstract class TelaGerenciar<T> extends TelaTabela<T> {

    protected final String idBibliotecaria;

    protected TelaGerenciar(String titulo, String[] colunas, String idBibliotecaria) {
        super(titulo, colunas, () -> new MenuBibliotecaria(idBibliotecaria));
        this.idBibliotecaria = idBibliotecaria;

        adicionarBotao("Novo", () -> {
            dispose();
            abrirCadastro();
        });

        adicionarBotao("Editar", () -> {
            T selecionado = itemSelecionado();
            if (selecionado != null) {
                dispose();
                abrirEdicao(selecionado);
            }
        });

        adicionarBotao("Excluir", () -> {
            T selecionado = itemSelecionado();
            if (selecionado != null && excluir(selecionado)) {
                recarregar();
            }
        });
    }

    protected abstract void abrirCadastro();

    protected abstract void abrirEdicao(T item);

    // Retorna true se o item foi realmente excluído.
    protected abstract boolean excluir(T item);
}
