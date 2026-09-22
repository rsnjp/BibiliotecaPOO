package biblioteca.visao.telas;

import biblioteca.controle.UsuarioControle;
import biblioteca.modelo.Usuario;

import java.util.List;

public class TelaGerenciarUsuarios extends TelaGerenciar<Usuario> {

    public TelaGerenciarUsuarios(String idBibliotecaria) {
        super("Gerenciar Usuários", new String[]{"ID", "Nome", "Telefone", "Email"}, idBibliotecaria);
        exibir();
    }

    @Override
    protected List<Usuario> carregarItens() {
        return UsuarioControle.listarUsuarios();
    }

    @Override
    protected Object[] linha(Usuario u) {
        return new Object[]{u.getIdUsuario(), u.getNome(), u.getTelefone(), u.getEmail()};
    }

    @Override
    protected void abrirCadastro() {
        new TelaCadastroUsuario(idBibliotecaria);
    }

    @Override
    protected void abrirEdicao(Usuario usuario) {
        new TelaCadastroUsuario(idBibliotecaria, usuario);
    }

    @Override
    protected boolean excluir(Usuario usuario) {
        return UsuarioControle.excluirUsuario(usuario, this, idBibliotecaria);
    }
}
