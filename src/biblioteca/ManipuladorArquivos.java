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
import java.util.Date;

/**
 * Responsável por gravar e ler, em arquivos de texto (.csv), os objetos
 * das classes Livro, Usuario, Bibliotecaria, Emprestimo e Reserva.
 *
 * Não usa nenhuma classe de Collections (List, ArrayList, etc.) — tudo é
 * feito com arrays comuns. Como um array não sabe o próprio "tamanho
 * final" antes de ser criado, cada método de leitura primeiro CONTA
 * quantas linhas válidas existem no arquivo, cria o array já do tamanho
 * certo, e só depois lê o arquivo de novo para preencher o array.
 *
 * Para Emprestimo e Reserva, como eles guardam uma referência a um
 * Usuario e a um Livro (não apenas o id), a leitura precisa receber os
 * arrays de usuários e livros já carregados, para "religar" cada
 * empréstimo/reserva ao objeto correto em memória.
 */
public class ManipuladorArquivos {

    private static final String SEP = ";";
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    // ---------------------------------------------------------------
    // Auxiliar: conta quantas linhas não vazias existem no arquivo,
    // para sabermos o tamanho exato do array antes de criá-lo.
    // ---------------------------------------------------------------
    private static int contarLinhas(String caminhoArquivo) throws IOException {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.isBlank()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // ---------------------------------------------------------------
    // LIVRO
    // ---------------------------------------------------------------

    public static void salvarLivros(Livro[] livros, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Livro livro : livros) {
                bw.write(livro.toCSV());
                bw.newLine();
            }
        }
    }

    public static Livro[] lerLivros(String caminhoArquivo) throws IOException {
        int total = contarLinhas(caminhoArquivo);
        Livro[] livros = new Livro[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] c = linha.split(SEP, -1);
                int idLivro = Integer.parseInt(c[0]);
                String titulo = c[1];
                String autor = c[2];
                String status = c[3];
                livros[i] = new Livro(idLivro, titulo, autor, status);
                i++;
            }
        }
        return livros;
    }

    // ---------------------------------------------------------------
    // USUARIO
    // ---------------------------------------------------------------

    public static void salvarUsuarios(Usuario[] usuarios, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.toCSV());
                bw.newLine();
            }
        }
    }

    public static Usuario[] lerUsuarios(String caminhoArquivo) throws IOException {
        int total = contarLinhas(caminhoArquivo);
        Usuario[] usuarios = new Usuario[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] c = linha.split(SEP, -1);
                int idUsuario = Integer.parseInt(c[0]);
                String nome = c[1];
                String telefone = c[2];
                String email = c[3];
                usuarios[i] = new Usuario(idUsuario, nome, telefone, email);
                i++;
            }
        }
        return usuarios;
    }

    // ---------------------------------------------------------------
    // BIBLIOTECARIA
    // ---------------------------------------------------------------

    public static void salvarBibliotecarias(Bibliotecaria[] bibliotecarias, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Bibliotecaria bibliotecaria : bibliotecarias) {
                bw.write(bibliotecaria.toCSV());
                bw.newLine();
            }
        }
    }

    public static Bibliotecaria[] lerBibliotecarias(String caminhoArquivo) throws IOException {
        int total = contarLinhas(caminhoArquivo);
        Bibliotecaria[] bibliotecarias = new Bibliotecaria[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] c = linha.split(SEP, -1);
                String idBibliotecaria = c[0];
                String nome = c[1];
                String turno = c[2];
                bibliotecarias[i] = new Bibliotecaria(idBibliotecaria, nome, turno);
                i++;
            }
        }
        return bibliotecarias;
    }

    // ---------------------------------------------------------------
    // EMPRESTIMO
    // (depende dos arrays de usuários e livros já carregados, para
    // religar as referências a partir dos ids gravados no arquivo)
    // ---------------------------------------------------------------

    public static void salvarEmprestimos(Emprestimo[] emprestimos, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Emprestimo emprestimo : emprestimos) {
                bw.write(emprestimo.toCSV());
                bw.newLine();
            }
        }
    }

    public static Emprestimo[] lerEmprestimos(String caminhoArquivo, Usuario[] usuarios, Livro[] livros)
            throws IOException, ParseException {
        int total = contarLinhas(caminhoArquivo);
        Emprestimo[] emprestimos = new Emprestimo[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
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
                emprestimos[i] = emprestimo;
                i++;
            }
        }
        return emprestimos;
    }

    // ---------------------------------------------------------------
    // RESERVA
    // (mesma lógica de religar por id usada em Emprestimo)
    // ---------------------------------------------------------------

    public static void salvarReservas(Reserva[] reservas, String caminhoArquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Reserva reserva : reservas) {
                bw.write(reserva.toCSV());
                bw.newLine();
            }
        }
    }

    public static Reserva[] lerReservas(String caminhoArquivo, Usuario[] usuarios, Livro[] livros)
            throws IOException, ParseException {
        int total = contarLinhas(caminhoArquivo);
        Reserva[] reservas = new Reserva[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] c = linha.split(SEP, -1);
                int idReserva = Integer.parseInt(c[0]);
                Date dataReserva = c[1].isEmpty() ? null : FORMATO_DATA.parse(c[1]);
                String status = c[2];
                int idUsuario = Integer.parseInt(c[3]);
                int idLivro = Integer.parseInt(c[4]);

                Usuario usuario = buscarUsuarioPorId(usuarios, idUsuario);
                Livro livro = buscarLivroPorId(livros, idLivro);

                reservas[i] = new Reserva(idReserva, dataReserva, status, usuario, livro);
                i++;
            }
        }
        return reservas;
    }

    // ---------------------------------------------------------------
    // Auxiliares privados: religam Emprestimo/Reserva ao Usuario/Livro
    // correto, procurando pelo id dentro do array já carregado.
    // ---------------------------------------------------------------

    private static Usuario buscarUsuarioPorId(Usuario[] usuarios, int id) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == id) {
                return u;
            }
        }
        return null;
    }

    private static Livro buscarLivroPorId(Livro[] livros, int id) {
        for (Livro l : livros) {
            if (l.getIdLivro() == id) {
                return l;
            }
        }
        return null;
    }
}
