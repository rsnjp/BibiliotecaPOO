package biblioteca;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main de demonstração da persistência em arquivo, usando ArrayList em vez
 * de arrays: cadastra os objetos, salva cada lista em .csv, lê de volta em
 * listas novas e exibe o resultado no console.
 */
public class MainArquivos {

    public static void main(String[] args) throws Exception {

        String pastaDados = "dados/";
        new java.io.File(pastaDados).mkdirs();

        // Cadastro em memória
        List<Bibliotecaria> bibliotecarias = new ArrayList<>();
        List<Livro> livros = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Emprestimo> emprestimos = new ArrayList<>();
        List<Reserva> reservas = new ArrayList<>();

        Bibliotecaria bibliotecaria1 = new Bibliotecaria("B001", "Maria Silva", "Manhã");
        Bibliotecaria bibliotecaria2 = new Bibliotecaria("B002", "Carlos Souza", "Tarde");
        bibliotecarias.add(bibliotecaria1);
        bibliotecarias.add(bibliotecaria2);

        Livro livro1 = new Livro(1, "Dom Casmurro", "Machado de Assis", "Disponível");
        Livro livro2 = new Livro(2, "O Cortiço", "Aluísio Azevedo", "Disponível");

        Usuario usuario1 = new Usuario(1, "João Pereira", "35999990000", "joao@email.com");
        Usuario usuario2 = new Usuario(2, "Ana Costa", "35988880000", "ana@email.com");

        bibliotecaria1.cadastrarLivro(livros, livro1);
        bibliotecaria1.cadastrarLivro(livros, livro2);
        bibliotecaria1.cadastrarUsuario(usuarios, usuario1);
        bibliotecaria1.cadastrarUsuario(usuarios, usuario2);

        bibliotecaria1.registrarEmprestimo(emprestimos, usuario1, livro1, new Date());
        bibliotecaria2.registrarEmprestimo(emprestimos, usuario2, livro2, new Date());

        bibliotecaria1.registrarReserva(reservas, usuario2, livro1);
        bibliotecaria2.registrarReserva(reservas, usuario1, livro2);

        // Persistência em arquivo
        ManipuladorArquivos.salvarBibliotecarias(bibliotecarias, pastaDados + "bibliotecarias.csv");
        ManipuladorArquivos.salvarLivros(livros, pastaDados + "livros.csv");
        ManipuladorArquivos.salvarUsuarios(usuarios, pastaDados + "usuarios.csv");
        ManipuladorArquivos.salvarEmprestimos(emprestimos, pastaDados + "emprestimos.csv");
        ManipuladorArquivos.salvarReservas(reservas, pastaDados + "reservas.csv");

        System.out.println("Arquivos salvos em: " + new java.io.File(pastaDados).getAbsolutePath());

        // Leitura de volta em listas novas (livros e usuários primeiro,
        // pois empréstimo/reserva precisam deles para religar pelo id)
        List<Bibliotecaria> bibliotecariasLidas = ManipuladorArquivos.lerBibliotecarias(pastaDados + "bibliotecarias.csv");
        List<Livro> livrosLidos = ManipuladorArquivos.lerLivros(pastaDados + "livros.csv");
        List<Usuario> usuariosLidos = ManipuladorArquivos.lerUsuarios(pastaDados + "usuarios.csv");
        List<Emprestimo> emprestimosLidos = ManipuladorArquivos.lerEmprestimos(
                pastaDados + "emprestimos.csv", usuariosLidos, livrosLidos);
        List<Reserva> reservasLidas = ManipuladorArquivos.lerReservas(
                pastaDados + "reservas.csv", usuariosLidos, livrosLidos);

        // Exibição
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
