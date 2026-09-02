package biblioteca;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<Livro> livros = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Emprestimo> emprestimos = new ArrayList<>();
        List<Reserva> reservas = new ArrayList<>();

        // Duas bibliotecárias diferentes compartilhando as mesmas listas,
        // recebidas por referência a cada chamada de método.
        Bibliotecaria bibliotecariaManha = new Bibliotecaria("B001", "Maria Silva", "Manhã");
        Bibliotecaria bibliotecariaTarde = new Bibliotecaria("B002", "Carlos Souza", "Tarde");

        Livro livro1 = new Livro(1, "Dom Casmurro", "Machado de Assis", "Disponível");
        Livro livro2 = new Livro(2, "O Cortiço", "Aluísio Azevedo", "Disponível");
        Usuario usuario1 = new Usuario(1, "João Pereira", "35999990000", "joao@email.com");

        bibliotecariaManha.cadastrarLivro(livros, livro1);
        bibliotecariaManha.cadastrarLivro(livros, livro2);
        bibliotecariaManha.cadastrarUsuario(usuarios, usuario1);

        System.out.println("=== Livros vistos pela bibliotecária da TARDE ===");
        for (Livro livro : livros) {
            System.out.println(livro);
        }

        Emprestimo emprestimo1 = bibliotecariaTarde.registrarEmprestimo(emprestimos, usuario1, livro1, new Date());
        Reserva reserva1 = bibliotecariaTarde.registrarReserva(reservas, usuario1, livro2);

        System.out.println("\n=== Estado após o empréstimo e a reserva ===");
        System.out.println("Livro 1: " + livro1);
        System.out.println("Livro 2: " + livro2);
        System.out.println(emprestimo1);
        System.out.println(reserva1);

        List<Emprestimo> emprestimosDoUsuario = usuario1.listarEmprestimos(emprestimos);
        List<Reserva> reservasDoUsuario = usuario1.listarReservas(reservas);

        System.out.println("\nEmpréstimos do usuário:");
        for (Emprestimo e : emprestimosDoUsuario) {
            System.out.println(e);
        }

        System.out.println("Reservas do usuário:");
        for (Reserva r : reservasDoUsuario) {
            System.out.println(r);
        }
    }
}
