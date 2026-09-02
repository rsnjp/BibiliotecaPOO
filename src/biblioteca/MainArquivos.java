package biblioteca;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;

import java.util.Date;

/**
 * Main de demonstração da persistência em arquivo — tudo com arrays
 * puros, sem List/ArrayList.
 *
 * Fluxo:
 *  1) Cria os arrays e pelo menos 2 objetos de cada classe.
 *  2) Salva cada array em um arquivo .csv, usando ManipuladorArquivos.
 *  3) Lê cada arquivo de volta em um array NOVO (objetos recriados a
 *     partir do texto, não os mesmos objetos criados no passo 1).
 *  4) Exibe os arrays lidos no console.
 */
public class MainArquivos {

    public static void main(String[] args) throws Exception {

        String pastaDados = "dados/";
        new java.io.File(pastaDados).mkdirs();

        // ----------------------------------------------------------
        // 1) Arrays e objetos criados aqui, no main
        // ----------------------------------------------------------
        Bibliotecaria[] bibliotecarias = new Bibliotecaria[2];
        Livro[] livros = new Livro[0];
        Usuario[] usuarios = new Usuario[0];
        Emprestimo[] emprestimos = new Emprestimo[0];
        Reserva[] reservas = new Reserva[0];

        Bibliotecaria bibliotecaria1 = new Bibliotecaria("B001", "Maria Silva", "Manhã");
        Bibliotecaria bibliotecaria2 = new Bibliotecaria("B002", "Carlos Souza", "Tarde");
        bibliotecarias[0] = bibliotecaria1;
        bibliotecarias[1] = bibliotecaria2;

        Livro livro1 = new Livro(1, "Dom Casmurro", "Machado de Assis", "Disponível");
        Livro livro2 = new Livro(2, "O Cortiço", "Aluísio Azevedo", "Disponível");

        Usuario usuario1 = new Usuario(1, "João Pereira", "35999990000", "joao@email.com");
        Usuario usuario2 = new Usuario(2, "Ana Costa", "35988880000", "ana@email.com");

        livros = bibliotecaria1.cadastrarLivro(livros, livro1);
        livros = bibliotecaria1.cadastrarLivro(livros, livro2);
        usuarios = bibliotecaria1.cadastrarUsuario(usuarios, usuario1);
        usuarios = bibliotecaria1.cadastrarUsuario(usuarios, usuario2);

        emprestimos = bibliotecaria1.registrarEmprestimo(emprestimos, usuario1, livro1, new Date());
        emprestimos = bibliotecaria2.registrarEmprestimo(emprestimos, usuario2, livro2, new Date());

        reservas = bibliotecaria1.registrarReserva(reservas, usuario2, livro1);
        reservas = bibliotecaria2.registrarReserva(reservas, usuario1, livro2);

        // ----------------------------------------------------------
        // 2) Gravação em arquivo
        // ----------------------------------------------------------
        ManipuladorArquivos.salvarBibliotecarias(bibliotecarias, pastaDados + "bibliotecarias.csv");
        ManipuladorArquivos.salvarLivros(livros, pastaDados + "livros.csv");
        ManipuladorArquivos.salvarUsuarios(usuarios, pastaDados + "usuarios.csv");
        ManipuladorArquivos.salvarEmprestimos(emprestimos, pastaDados + "emprestimos.csv");
        ManipuladorArquivos.salvarReservas(reservas, pastaDados + "reservas.csv");

        System.out.println("Arquivos salvos em: " + new java.io.File(pastaDados).getAbsolutePath());

        // ----------------------------------------------------------
        // 3) Leitura de volta em arrays NOVOS
        //    (Livros e Usuários primeiro, pois Emprestimo/Reserva
        //    precisam deles para religar as referências pelo id)
        // ----------------------------------------------------------
        Bibliotecaria[] bibliotecariasLidas = ManipuladorArquivos.lerBibliotecarias(pastaDados + "bibliotecarias.csv");
        Livro[] livrosLidos = ManipuladorArquivos.lerLivros(pastaDados + "livros.csv");
        Usuario[] usuariosLidos = ManipuladorArquivos.lerUsuarios(pastaDados + "usuarios.csv");
        Emprestimo[] emprestimosLidos = ManipuladorArquivos.lerEmprestimos(
                pastaDados + "emprestimos.csv", usuariosLidos, livrosLidos);
        Reserva[] reservasLidas = ManipuladorArquivos.lerReservas(
                pastaDados + "reservas.csv", usuariosLidos, livrosLidos);

        // ----------------------------------------------------------
        // 4) Exibição no console
        // ----------------------------------------------------------
        System.out.println("\n=== Bibliotecárias lidas do arquivo ===");
        for (Bibliotecaria b : bibliotecariasLidas) {
            System.out.println(b);
        }

        System.out.println("\n=== Livros lidos do arquivo ===");
        for (Livro l : livrosLidos) {
            System.out.println(l);
        }

        System.out.println("\n=== Usuários lidos do arquivo ===");
        for (Usuario u : usuariosLidos) {
            System.out.println(u);
        }

        System.out.println("\n=== Empréstimos lidos do arquivo ===");
        for (Emprestimo e : emprestimosLidos) {
            System.out.println(e);
        }

        System.out.println("\n=== Reservas lidas do arquivo ===");
        for (Reserva r : reservasLidas) {
            System.out.println(r);
        }
    }
}
