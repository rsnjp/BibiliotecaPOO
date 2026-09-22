package biblioteca.controle;

import biblioteca.modelo.Usuario;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.List;

public class UsuarioControle {

    public static boolean cadastrarUsuario(String nome, String telefone, String email, JFrame tela, String idBibliotecaria) {
        if (!dadosValidos(nome, telefone, email, tela)) return false;

        int id = ManipuladorArquivos.proximoId("Usuario");
        Usuario usuario = new Usuario(id, nome, telefone, email);
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).cadastrarUsuario(usuario);

        JOptionPane.showMessageDialog(tela, "Usuário cadastrado com sucesso!");
        return true;
    }

    public static boolean editarUsuario(Usuario usuario, String nome, String telefone, String email, JFrame tela) {
        if (!dadosValidos(nome, telefone, email, tela)) return false;

        usuario.atualizarDados(nome, telefone, email);
        JOptionPane.showMessageDialog(tela, "Usuário atualizado com sucesso!");
        return true;
    }

    public static boolean excluirUsuario(Usuario usuario, JFrame tela, String idBibliotecaria) {
        if (usuario.possuiMovimentacoes()) {
            JOptionPane.showMessageDialog(tela, "Não é possível excluir: o usuário possui empréstimos ou reservas registrados.\n"
                    + "Exclua esses registros antes de excluir o usuário.");
            return false;
        }
        if (!Validacoes.confirmarExclusao(tela, "o usuário \"" + usuario.getNome() + "\"")) return false;

        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).excluirUsuario(usuario);
        JOptionPane.showMessageDialog(tela, "Usuário excluído com sucesso!");
        return true;
    }

    private static boolean dadosValidos(String nome, String telefone, String email, JFrame tela) {
        if (!Validacoes.camposValidos(tela, nome, telefone, email)) return false;

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(tela, "Email inválido.");
            return false;
        }
        return true;
    }

    public static Usuario obterUsuario(int idUsuario) {
        return ManipuladorArquivos.buscarUsuarioPorId(idUsuario);
    }

    public static List<Usuario> listarUsuarios() {
        return ManipuladorArquivos.lerUsuarios();
    }
}
