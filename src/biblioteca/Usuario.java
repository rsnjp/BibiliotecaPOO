package biblioteca;

public class Usuario {

    // Atributos
    private int idUsuario;
    private String nome;
    private String telefone;
    private String email;

    // OBS: Não guardamos array de Emprestimo nem de Reserva aqui dentro.
    // A Bibliotecaria (ou o main) já é a "fonte única da verdade" para
    // esses dados. Manter uma segunda cópia aqui criaria dois arrays que
    // precisariam ser sincronizados manualmente a cada empréstimo,
    // devolução ou reserva — risco real de bug, sem ganho nenhum. Em vez
    // disso, o Usuario recebe o array mestre como parâmetro e filtra.

    // Construtor
    public Usuario(int idUsuario, String nome, String telefone, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Métodos da UML (assinaturas declaradas, sem implementação completa)
    public void atualizarContato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    // Recebe o array mestre (vindo do main) e devolve um array novo,
    // só com os empréstimos deste usuário. Não duplica dados nem usa
    // Collections: primeiro conta quantos batem, cria um array desse
    // tamanho exato, depois preenche.
    public Emprestimo[] listarEmprestimos(Emprestimo[] todosEmprestimos) {
        int quantidade = 0;
        for (Emprestimo e : todosEmprestimos) {
            if (e.getUsuario() == this) {
                quantidade++;
            }
        }

        Emprestimo[] resultado = new Emprestimo[quantidade];
        int posicao = 0;
        for (Emprestimo e : todosEmprestimos) {
            if (e.getUsuario() == this) {
                resultado[posicao] = e;
                posicao++;
            }
        }
        return resultado;
    }

    // Mesma lógica para reservas.
    public Reserva[] listarReservas(Reserva[] todasReservas) {
        int quantidade = 0;
        for (Reserva r : todasReservas) {
            if (r.getUsuario() == this) {
                quantidade++;
            }
        }

        Reserva[] resultado = new Reserva[quantidade];
        int posicao = 0;
        for (Reserva r : todasReservas) {
            if (r.getUsuario() == this) {
                resultado[posicao] = r;
                posicao++;
            }
        }
        return resultado;
    }

    // Getters e Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Converte o objeto para uma linha de texto no formato CSV.
    // Formato: idUsuario;nome;telefone;email
    public String toCSV() {
        return idUsuario + ";" + nome + ";" + telefone + ";" + email;
    }
}
