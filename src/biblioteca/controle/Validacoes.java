package biblioteca.controle;

import javax.swing.*;

// Validações comuns aos formulários de cadastro/edição.
class Validacoes {

    // Todos os campos preenchidos e sem ";" (o separador usado nos arquivos .csv).
    static boolean camposValidos(JFrame tela, String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.isEmpty()) {
                JOptionPane.showMessageDialog(tela, "Preencha todos os campos.");
                return false;
            }
            if (campo.contains(";")) {
                JOptionPane.showMessageDialog(tela, "Os campos não podem conter o caractere \";\".");
                return false;
            }
        }
        return true;
    }

    // Pede confirmação antes de qualquer exclusão.
    static boolean confirmarExclusao(JFrame tela, String descricao) {
        int resposta = JOptionPane.showConfirmDialog(tela,
                "Deseja realmente excluir " + descricao + "?", "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);
        return resposta == JOptionPane.YES_OPTION;
    }
}
