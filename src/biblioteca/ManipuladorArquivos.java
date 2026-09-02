package biblioteca;

import biblioteca.modelo.Bibliotecaria;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Reserva;
import biblioteca.modelo.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Grava e lê, em arquivos .csv, os objetos de Livro, Usuario,
 * Bibliotecaria, Emprestimo e Reserva.
 *
 * Emprestimo e Reserva guardam referência a um Usuario e a um Livro, então
 * a leitura precisa receber as listas de usuários e livros já carregadas,
 * para religar cada empréstimo/reserva ao objeto correto em memória.
 */
public class ManipuladorArquivos {

    private static final String SEP = ";";
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    // ---------------------------------------------------------------
    // LIVRO
    // ---------------------------------------------------------------

    public static void salvarLivros(List<Livro> livros, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Livro livro : livros) {
                bw.write(livro.toCSV());
                bw.newLine();
            }
        }
    }

    public static List<Livro> lerLivros(String caminhoArquivo) throws IOException {
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] c = linha.split(SEP, -1);
                int idLivro = Integer.parseInt(c[0]);
                String titulo = c[1];
                String autor = c[2];
                String status = c[3];
                livros.add(new Livro(idLivro, titulo, autor, status));
            }
        }
        return livros;
    }

    // ---------------------------------------------------------------
    // USUARIO
    // ---------------------------------------------------------------

    public static void salvarUsuarios(List<Usuario> usuarios, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.toCSV());
                bw.newLine();
            }
        }
    }

    public static List<Usuario> lerUsuarios(String caminhoArquivo) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] c = linha.split(SEP, -1);
                int idUsuario = Integer.parseInt(c[0]);
                String nome = c[1];
                String telefone = c[2];
                String email = c[3];
                usuarios.add(new Usuario(idUsuario, nome, telefone, email));
            }
        }
        return usuarios;
    }

    // ---------------------------------------------------------------
    // BIBLIOTECARIA
    // ---------------------------------------------------------------

    public static void salvarBibliotecarias(List<Bibliotecaria> bibliotecarias, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Bibliotecaria bibliotecaria : bibliotecarias) {
                bw.write(bibliotecaria.toCSV());
                bw.newLine();
            }
        }
    }

    public static List<Bibliotecaria> lerBibliotecarias(String caminhoArquivo) throws IOException {
        List<Bibliotecaria> bibliotecarias = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] c = linha.split(SEP, -1);
                String idBibliotecaria = c[0];
                String nome = c[1];
                String turno = c[2];
                bibliotecarias.add(new Bibliotecaria(idBibliotecaria, nome, turno));
            }
        }
        return bibliotecarias;
    }

    // ---------------------------------------------------------------
    // EMPRESTIMO (depende das listas de usuários e livros já carregadas,
    // para religar as referências a partir dos ids gravados no arquivo)
    // ---------------------------------------------------------------

    public static void salvarEmprestimos(List<Emprestimo> emprestimos, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Emprestimo emprestimo : emprestimos) {
                bw.write(emprestimo.toCSV());
                bw.newLine();
            }
        }
    }

    public static List<Emprestimo> lerEmprestimos(String caminhoArquivo, List<Usuario> usuarios, List<Livro> livros)
            throws IOException, ParseException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] c = linha.split(SEP, -1);
                int idEmprestimo = Integer.parseInt(c[0]);
                Date dataEmprestimo = c[1].isEmpty() ? null : FORMATO_DATA.parse(c[1]);
                Date dataDevolucaoPrevista = c[2].isEmpty() ? null : FORMATO_DATA.parse(c[2]);
                Date dataDevolucaoEfetiva = c[3].isEmpty() ? null : FORMATO_DATA.parse(c[3]);
                String status = c[4];
                int idUsuario = Integer.parseInt(c[5]);
                int idLivro = Integer.parseInt(c[6]);

                Usuario usuario = buscarUsuarioPorId(usuarios, idUsuario);
                Livro livro = buscarLivroPorId(livros, idLivro);

                Emprestimo emprestimo = new Emprestimo(idEmprestimo, dataEmprestimo, dataDevolucaoPrevista,
                        status, usuario, livro);
                emprestimo.setDataDevolucaoEfetiva(dataDevolucaoEfetiva);
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }

    // ---------------------------------------------------------------
    // RESERVA (mesma lógica de religar por id usada em Emprestimo)
    // ---------------------------------------------------------------

    public static void salvarReservas(List<Reserva> reservas, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Reserva reserva : reservas) {
                bw.write(reserva.toCSV());
                bw.newLine();
            }
        }
    }

    public static List<Reserva> lerReservas(String caminhoArquivo, List<Usuario> usuarios, List<Livro> livros)
            throws IOException, ParseException {
        List<Reserva> reservas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] c = linha.split(SEP, -1);
                int idReserva = Integer.parseInt(c[0]);
                Date dataReserva = c[1].isEmpty() ? null : FORMATO_DATA.parse(c[1]);
                String status = c[2];
                int idUsuario = Integer.parseInt(c[3]);
                int idLivro = Integer.parseInt(c[4]);

                Usuario usuario = buscarUsuarioPorId(usuarios, idUsuario);
                Livro livro = buscarLivroPorId(livros, idLivro);

                reservas.add(new Reserva(idReserva, dataReserva, status, usuario, livro));
            }
        }
        return reservas;
    }

    private static Usuario buscarUsuarioPorId(List<Usuario> usuarios, int id) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == id) {
                return u;
            }
        }
        return null;
    }

    private static Livro buscarLivroPorId(List<Livro> livros, int id) {
        for (Livro l : livros) {
            if (l.getIdLivro() == id) {
                return l;
            }
        }
        return null;
    }
}
