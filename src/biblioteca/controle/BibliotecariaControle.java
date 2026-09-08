package biblioteca.controle;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.util.ManipuladorArquivos;

import java.util.List;

public class BibliotecariaControle {

    public static Bibliotecaria obterBibliotecaria(String idBibliotecaria) {
        for (Bibliotecaria b : ManipuladorArquivos.lerBibliotecarias()) {
            if (b.getIdBibliotecaria().equals(idBibliotecaria)) {
                return b;
            }
        }
        return null;
    }

    public static List<Bibliotecaria> listarBibliotecarias() {
        return ManipuladorArquivos.lerBibliotecarias();
    }
}
