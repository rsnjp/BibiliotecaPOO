package biblioteca.controle;

import biblioteca.modelo.Usuario;
import biblioteca.util.ManipuladorArquivos;

import javax.swing.*;
import java.util.List;

public class UsuarioControle {

    public static void cadastrarUsuario(String nome, String telefone, String email, JFrame tela, String idBibliotecaria) {
        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Preencha todos os campos.");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(tela, "Email inválido.");
            return;
        }

        int id = ManipuladorArquivos.proximoId("Usuario");
        Usuario usuario = new Usuario(id, nome, telefone, email);
        BibliotecariaControle.obterBibliotecaria(idBibliotecaria).cadastrarUsuario(usuario);

        JOptionPane.showMessageDialog(tela, "Usuário cadastrado com sucesso!");
        tela.dispose();
        new biblioteca.visao.menus.MenuBibliotecaria(idBibliotecaria);
    }

    public static Usuario obterUsuario(int idUsuario) {
        return ManipuladorArquivos.buscarUsuarioPorId(idUsuario);
    }

    public static List<Usuario> listarUsuarios() {
        return ManipuladorArquivos.lerUsuarios();
    }
}
