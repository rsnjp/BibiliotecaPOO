package biblioteca.util;

import biblioteca.modelo.*;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Camada de persistência: grava e lê os objetos do Model em arquivos .csv
 * dentro da pasta "dados". Cada classe tem seu próprio arquivo
 * (dados/Livro.csv, dados/Usuario.csv, ...), identificado pelo nome passado
 * em nomeClasse.
 */
public class ManipuladorArquivos {

    private static final String DIRETORIO = "dados";
    private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    public static void salvarObjeto(String nomeClasse, Object objeto) {
        salvar(nomeClasse, toCSV(objeto));
    }

    // Reescreve, no arquivo, a linha cujo primeiro campo (id) bate com o id
    // informado, substituindo-a pelo CSV atual de novoObjeto.
    public static void atualizarObjeto(String nomeClasse, Object id, Object novoObjeto, int camposEsperados) {
        List<String[]> linhas = ler(nomeClasse, camposEsperados);

        for (int i = 0; i < linhas.size(); i++) {
            if (linhas.get(i)[0].equals(String.valueOf(id))) {
                linhas.set(i, toCSV(novoObjeto).split(";", -1));
                break;
            }
        }

        salvarLista(nomeClasse, linhas);
    }

    // Remove do arquivo a linha cujo primeiro campo (id) bate com o id informado.
    public static void excluirObjeto(String nomeClasse, Object id, int camposEsperados) {
        List<String[]> linhas = ler(nomeClasse, camposEsperados);
        linhas.removeIf(campos -> campos[0].equals(String.valueOf(id)));
        salvarLista(nomeClasse, linhas);
    }

    private static String toCSV(Object objeto) {
        if (objeto instanceof Livro) return ((Livro) objeto).toCSV();
        if (objeto instanceof Usuario) return ((Usuario) objeto).toCSV();
        if (objeto instanceof Bibliotecaria) return ((Bibliotecaria) objeto).toCSV();
        if (objeto instanceof Emprestimo) return ((Emprestimo) objeto).toCSV();
        if (objeto instanceof Reserva) return ((Reserva) objeto).toCSV();
        throw new IllegalArgumentException("Tipo não suportado: " + objeto.getClass());
    }

    public static void salvar(String nomeClasse, String linhaCSV) {
        try {
            File dir = new File(DIRETORIO);
            if (!dir.exists()) dir.mkdirs();
            try (FileWriter fw = new FileWriter(new File(dir, nomeClasse + ".csv"), true)) {
                fw.write(linhaCSV + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }
    }

    // Só aceita linhas com exatamente camposEsperados colunas, descartando
    // linhas corrompidas/incompletas.
    public static List<String[]> ler(String nomeClasse, int camposEsperados) {
        List<String[]> linhas = new ArrayList<>();
        File arquivo = new File(DIRETORIO, nomeClasse + ".csv");
        if (!arquivo.exists()) return linhas;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(";", -1);
                if (campos.length == camposEsperados) {
                    linhas.add(campos);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler " + nomeClasse + ": " + e.getMessage());
        }
        return linhas;
    }

    private static void salvarLista(String nomeClasse, List<String[]> linhas) {
        try (FileWriter fw = new FileWriter(new File(DIRETORIO, nomeClasse + ".csv"), false)) {
            for (String[] campos : linhas) {
                fw.write(String.join(";", campos) + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao regravar " + nomeClasse + ": " + e.getMessage());
        }
    }

    // Maior id já usado + 1. Só serve para classes com id numérico.
    public static int proximoId(String nomeClasse) {
        int maiorId = 0;
        for (String[] campos : ler(nomeClasse, camposDe(nomeClasse))) {
            try {
                maiorId = Math.max(maiorId, Integer.parseInt(campos[0]));
            } catch (NumberFormatException ignored) {
                // linha com id não numérico é ignorada no cálculo
            }
        }
        return maiorId + 1;
    }

    // Bibliotecárias usam id no formato "B001", "B002"...
    public static String proximoIdBibliotecaria() {
        int maiorNumero = 0;
        for (String[] campos : ler("Bibliotecaria", 3)) {
            try {
                maiorNumero = Math.max(maiorNumero, Integer.parseInt(campos[0].replaceAll("\\D", "")));
            } catch (NumberFormatException ignored) {
                // id sem parte numérica é ignorado no cálculo
            }
        }
        return String.format("B%03d", maiorNumero + 1);
    }

    private static int camposDe(String nomeClasse) {
        if (nomeClasse.equals("Livro") || nomeClasse.equals("Usuario")) return 4;
        if (nomeClasse.equals("Bibliotecaria")) return 3;
        if (nomeClasse.equals("Reserva")) return 5;
        if (nomeClasse.equals("Emprestimo")) return 7;
        return -1;
    }

    public static List<Livro> lerLivros() {
        List<Livro> lista = new ArrayList<>();
        for (String[] c : ler("Livro", 4)) {
            try {
                lista.add(new Livro(Integer.parseInt(c[0]), c[1], c[2], c[3]));
            } catch (Exception e) {
                System.err.println("Erro ao ler livro: " + Arrays.toString(c));
            }
        }
        return lista;
    }

    public static List<Usuario> lerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        for (String[] c : ler("Usuario", 4)) {
            try {
                lista.add(new Usuario(Integer.parseInt(c[0]), c[1], c[2], c[3]));
            } catch (Exception e) {
                System.err.println("Erro ao ler usuário: " + Arrays.toString(c));
            }
        }
        return lista;
    }

    public static List<Bibliotecaria> lerBibliotecarias() {
        List<Bibliotecaria> lista = new ArrayList<>();
        for (String[] c : ler("Bibliotecaria", 3)) {
            lista.add(new Bibliotecaria(c[0], c[1], c[2]));
        }
        return lista;
    }

    public static List<Emprestimo> lerEmprestimos() {
        List<Emprestimo> lista = new ArrayList<>();
        for (String[] c : ler("Emprestimo", 7)) {
            try {
                int idEmprestimo = Integer.parseInt(c[0]);
                Date dataEmprestimo = c[1].isEmpty() ? null : FORMATO_DATA.parse(c[1]);
                Date dataDevolucaoPrevista = c[2].isEmpty() ? null : FORMATO_DATA.parse(c[2]);
                Date dataDevolucaoEfetiva = c[3].isEmpty() ? null : FORMATO_DATA.parse(c[3]);
                String status = c[4];
                int idUsuario = Integer.parseInt(c[5]);
                int idLivro = Integer.parseInt(c[6]);

                Usuario usuario = buscarUsuarioPorId(idUsuario);
                Livro livro = buscarLivroPorId(idLivro);
                if (usuario == null || livro == null) {
                    System.err.println("Erro ao ler empréstimo: " + Arrays.toString(c));
                    continue;
                }

                Emprestimo emprestimo = new Emprestimo(idEmprestimo, usuario, livro,
                        dataEmprestimo, dataDevolucaoPrevista, status);
                emprestimo.setDataDevolucaoEfetiva(dataDevolucaoEfetiva);
                lista.add(emprestimo);
            } catch (Exception e) {
                System.err.println("Erro ao ler empréstimo: " + Arrays.toString(c));
            }
        }
        return lista;
    }

    public static List<Reserva> lerReservas() {
        List<Reserva> lista = new ArrayList<>();
        for (String[] c : ler("Reserva", 5)) {
            try {
                int idReserva = Integer.parseInt(c[0]);
                Date dataReserva = c[1].isEmpty() ? null : FORMATO_DATA.parse(c[1]);
                String status = c[2];
                int idUsuario = Integer.parseInt(c[3]);
                int idLivro = Integer.parseInt(c[4]);

                Usuario usuario = buscarUsuarioPorId(idUsuario);
                Livro livro = buscarLivroPorId(idLivro);
                if (usuario == null || livro == null) {
                    System.err.println("Erro ao ler reserva: " + Arrays.toString(c));
                    continue;
                }

                lista.add(new Reserva(idReserva, dataReserva, status, usuario, livro));
            } catch (Exception e) {
                System.err.println("Erro ao ler reserva: " + Arrays.toString(c));
            }
        }
        return lista;
    }

    public static Livro buscarLivroPorId(int idLivro) {
        for (Livro l : lerLivros()) {
            if (l.getIdLivro() == idLivro) return l;
        }
        return null;
    }

    public static Usuario buscarUsuarioPorId(int idUsuario) {
        for (Usuario u : lerUsuarios()) {
            if (u.getIdUsuario() == idUsuario) return u;
        }
        return null;
    }
}
