package biblioteca;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;

import java.util.Date;

public class App {

    public static void main(String[] args) {

        // Os arrays começam vazios (tamanho 0) e vão crescendo conforme
        // a bibliotecária cadastra/registra coisas. Cada método devolve
        // o array NOVO, maior, e por isso reatribuímos a variável.
        Livro[] livros = new Livro[0];
        Usuario[] usuarios = new Usuario[0];
        Emprestimo[] emprestimos = new Emprestimo[0];
        Reserva[] reservas = new Reserva[0];

        // Duas bibliotecárias DIFERENTES, para provar que, mesmo sem
        // nenhum atributo "static" ou Collection dentro da classe, elas
        // continuam enxergando os mesmos dados — desde que recebam o
        // MESMO array por parâmetro. Isso acontece porque, em Java, um
        // array é passado por referência: "livros" aqui e o parâmetro
        // "livros" lá dentro do método apontam para o mesmo array na
        // memória (até o momento em que um novo array é criado e
        // devolvido).
        Bibliotecaria bibliotecariaManha = new Bibliotecaria("B001", "Maria Silva", "Manhã");
        Bibliotecaria bibliotecariaTarde = new Bibliotecaria("B002", "Carlos Souza", "Tarde");

        // Criando livros e usuário
        Livro livro1 = new Livro(1, "Dom Casmurro", "Machado de Assis", "Disponível");
        Livro livro2 = new Livro(2, "O Cortiço", "Aluísio Azevedo", "Disponível");
        Usuario usuario1 = new Usuario(1, "João Pereira", "35999990000", "joao@email.com");

        // A bibliotecária da MANHÃ cadastra os livros e o usuário.
        // Repare que cada chamada devolve um array novo, maior, que
        // guardamos de volta na mesma variável.
        livros = bibliotecariaManha.cadastrarLivro(livros, livro1);
        livros = bibliotecariaManha.cadastrarLivro(livros, livro2);
        usuarios = bibliotecariaManha.cadastrarUsuario(usuarios, usuario1);

        // A bibliotecária da TARDE recebe o mesmo array "livros" já
        // atualizado, então já enxerga os livros cadastrados pela manhã.
        System.out.println("=== Livros vistos pela bibliotecária da TARDE ===");
        for (Livro livro : livros) {
            System.out.println(livro);
        }

        // A bibliotecária da TARDE registra o empréstimo e a reserva.
        // O objeto recém-criado sempre fica na última posição do array
        // devolvido.
        emprestimos = bibliotecariaTarde.registrarEmprestimo(emprestimos, usuario1, livro1, new Date());
        Emprestimo emprestimo1 = emprestimos[emprestimos.length - 1];

        reservas = bibliotecariaTarde.registrarReserva(reservas, usuario1, livro2);
        Reserva reserva1 = reservas[reservas.length - 1];

        System.out.println("\n=== Estado após o empréstimo e a reserva ===");
        System.out.println("Livro 1: " + livro1);
        System.out.println("Livro 2: " + livro2);
        System.out.println(emprestimo1);
        System.out.println(reserva1);

        // O Usuario filtra o array mestre recebido por parâmetro,
        // sem manter cópia própria dentro dele.
        Emprestimo[] emprestimosDoUsuario = usuario1.listarEmprestimos(emprestimos);
        Reserva[] reservasDoUsuario = usuario1.listarReservas(reservas);

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
