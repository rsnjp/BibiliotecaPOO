package biblioteca.modelo;

import java.util.Date;

public class Bibliotecaria {

    // Atributos de instância (cada Bibliotecaria tem os seus)
    private String idBibliotecaria;
    private String nome;
    private String turno;

    // A classe não guarda nenhuma coleção interna. Livros, usuários,
    // empréstimos e reservas são recebidos como ARRAY por parâmetro em
    // cada método. Como array em Java tem tamanho fixo, "adicionar um
    // item" na prática significa: criar um array novo, um espaço maior
    // que o antigo, copiar manualmente (com um for) todo mundo que já
    // estava lá, colocar o item novo na última posição, e devolver esse
    // array novo para quem chamou o método guardar na variável.

    // Construtor
    public Bibliotecaria(String idBibliotecaria, String nome, String turno) {
        this.idBibliotecaria = idBibliotecaria;
        this.nome = nome;
        this.turno = turno;
    }

    // Métodos da UML — agora recebem e devolvem o array em que devem atuar

    public Livro[] cadastrarLivro(Livro[] livros, Livro livro) {
        Livro[] novoArray = new Livro[livros.length + 1];
        for (int i = 0; i < livros.length; i++) {
            novoArray[i] = livros[i];
        }
        novoArray[livros.length] = livro;
        return novoArray;
    }

    public Usuario[] cadastrarUsuario(Usuario[] usuarios, Usuario usuario) {
        Usuario[] novoArray = new Usuario[usuarios.length + 1];
        for (int i = 0; i < usuarios.length; i++) {
            novoArray[i] = usuarios[i];
        }
        novoArray[usuarios.length] = usuario;
        return novoArray;
    }

    // Registra um novo empréstimo: cria o objeto Emprestimo, coloca-o
    // dentro de um array maior que o recebido e devolve esse array novo.
    // O empréstimo recém-criado fica sempre na ÚLTIMA posição do array
    // devolvido (emprestimos[emprestimos.length - 1]).
    public Emprestimo[] registrarEmprestimo(Emprestimo[] emprestimos, Usuario usuario, Livro livro, Date dataEmprestimo) {
        int novoId = emprestimos.length + 1;
        Emprestimo emprestimo = new Emprestimo(novoId, dataEmprestimo, null, "Ativo", usuario, livro);

        Emprestimo[] novoArray = new Emprestimo[emprestimos.length + 1];
        for (int i = 0; i < emprestimos.length; i++) {
            novoArray[i] = emprestimos[i];
        }
        novoArray[emprestimos.length] = emprestimo;

        livro.atualizarStatus("Emprestado");
        return novoArray;
    }

    // Não precisa criar array novo aqui: devolução não adiciona nem
    // remove elemento, só altera o estado de um Emprestimo já existente.
    public void registrarDevolucao(Emprestimo[] emprestimos, int idEmprestimo) {
        for (Emprestimo e : emprestimos) {
            if (e.getIdEmprestimo() == idEmprestimo) {
                e.registrarDevolucao();
                break;
            }
        }
    }

    // Mesma lógica de registrarEmprestimo: a reserva nova fica na
    // última posição do array devolvido.
    public Reserva[] registrarReserva(Reserva[] reservas, Usuario usuario, Livro livro) {
        int novoId = reservas.length + 1;
        Reserva reserva = new Reserva(novoId, new Date(), "Ativa", usuario, livro);

        Reserva[] novoArray = new Reserva[reservas.length + 1];
        for (int i = 0; i < reservas.length; i++) {
            novoArray[i] = reservas[i];
        }
        novoArray[reservas.length] = reserva;

        return novoArray;
    }

    // Getters e Setters
    public String getIdBibliotecaria() {
        return idBibliotecaria;
    }

    public void setIdBibliotecaria(String idBibliotecaria) {
        this.idBibliotecaria = idBibliotecaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Bibliotecaria{" +
                "idBibliotecaria='" + idBibliotecaria + '\'' +
                ", nome='" + nome + '\'' +
                ", turno='" + turno + '\'' +
                '}';
    }

    // Converte o objeto para uma linha de texto no formato CSV.
    // Formato: idBibliotecaria;nome;turno
    public String toCSV() {
        return idBibliotecaria + ";" + nome + ";" + turno;
    }
}
