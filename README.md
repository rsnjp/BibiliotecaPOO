# Sistema de Biblioteca — Java (MVC + Swing)

Projeto criado a partir da UML da atividade "Sistema de Biblioteca", agora com
interface gráfica em Java Swing organizada no padrão **MVC** (Model-View-Controller).

## Estrutura

```
BibiliotecaPOO/
├── dados/                          (arquivos .csv com os dados persistidos)
└── src/
    └── biblioteca/
        ├── Main.java                (ponto de entrada: abre o MenuInicial)
        ├── modelo/                  (Model: dados e regras de negócio)
        │   ├── Livro.java
        │   ├── Usuario.java
        │   ├── Bibliotecaria.java
        │   ├── Emprestimo.java
        │   └── Reserva.java
        ├── util/
        │   ├── ManipuladorArquivos.java   (persistência em .csv)
        │   └── Datas.java                 (formatação e cálculo de prazos/atrasos)
        ├── controle/                (Controller: valida entradas, liga View ao Model)
        │   ├── LivroControle.java
        │   ├── UsuarioControle.java
        │   ├── BibliotecariaControle.java
        │   ├── EmprestimoControle.java
        │   ├── ReservaControle.java
        │   └── Validacoes.java
        └── visao/                   (View: janelas Swing)
            ├── menus/
            │   ├── MenuInicial.java
            │   ├── MenuBibliotecaria.java
            │   └── MenuUsuario.java
            └── telas/
                ├── TelaTabela.java                (base: lista em tabela + botões)
                ├── TelaGerenciar.java             (base do CRUD: Novo/Editar/Excluir)
                ├── TelaGerenciarLivros.java
                ├── TelaGerenciarUsuarios.java
                ├── TelaGerenciarBibliotecarias.java
                ├── TelaGerenciarEmprestimos.java
                ├── TelaGerenciarReservas.java
                ├── TelaCadastroLivro.java         (cadastro e edição)
                ├── TelaCadastroUsuario.java       (cadastro e edição)
                ├── TelaCadastroBibliotecaria.java (cadastro e edição)
                ├── TelaRegistrarEmprestimo.java
                ├── TelaEditarEmprestimo.java
                ├── TelaRegistrarDevolucao.java
                ├── TelaRegistrarReserva.java
                ├── TelaEditarReserva.java
                ├── TelaReservasPendentes.java
                ├── TelaListarLivros.java
                ├── TelaMeusEmprestimos.java
                └── TelaMinhasReservas.java
```

## Como funciona

- **Model** (`modelo`): cada classe representa uma entidade do domínio e sabe se
  converter para/de uma linha CSV. Ações de negócio (cadastrar livro, registrar
  empréstimo, cancelar reserva, etc.) persistem imediatamente através de
  `util.ManipuladorArquivos` — não há listas em memória compartilhadas entre telas.
- **View** (`visao`): `menus` são as telas de seleção de perfil e de navegação
  principal; `telas` são os formulários de cadastro e as ações específicas.
- **Controller** (`controle`): recebe os dados digitados na View, valida,
  aplica as regras de negócio, monta os objetos do Model (gerando o próximo id via
  `ManipuladorArquivos.proximoId`) e devolve `true`/`false` para a View decidir
  qual tela abrir a seguir.

Ao abrir o sistema, o `MenuInicial` pergunta o perfil de acesso:

- **Bibliotecária**: cadastra, edita, exclui e lista livros, usuários,
  bibliotecárias, empréstimos e reservas; registra devoluções; efetiva as
  reservas pendentes; consulta livros disponíveis e com atraso na entrega.
- **Usuário**: consulta os livros disponíveis e lista/cancela seus próprios
  empréstimos e reservas.

## Regras de negócio

- **Prazo**: todo empréstimo tem devolução prevista para
  `Emprestimo.PRAZO_DIAS` (7) dias após a data do empréstimo. Registros antigos
  sem data prevista têm o prazo calculado a partir da data do empréstimo.
- **Empréstimo bloqueado** se o livro já está emprestado ou se tem uma reserva
  ativa de **outro** usuário.
- **Baixa da reserva**: ao emprestar um livro reservado para o próprio usuário
  (pela tela de empréstimo ou pelo botão "Efetivar Empréstimo" em Reservas
  Pendentes), a reserva passa para "Concluída".
- **Reserva bloqueada** se o livro está emprestado ou já possui reserva ativa.
  Enquanto reservado, o livro fica com status "Reservado"; cancelar/excluir a
  reserva o devolve para "Disponível".
- **Reservas pendentes**: reservas com status "Ativa", ainda aguardando a
  efetivação do empréstimo.
- **Devolução**: ao dar baixa no empréstimo, o sistema informa se a devolução
  está dentro do prazo ou atrasada, e quantos dias de atraso.
- **Livros com atraso na entrega**: empréstimos ativos cuja data prevista já
  passou (filtro na tela "Livros Disponíveis / Com Atraso").
- **Exclusões**: livros e usuários só podem ser excluídos se não tiverem
  empréstimos/reservas registrados; a bibliotecária logada não pode se excluir;
  excluir um empréstimo/reserva ativo libera o livro.

## Como abrir no VS Code

1. Abra o VS Code em **File > Open Folder** e selecione esta pasta
   (a que contém a subpasta `src`).
2. Instale a extensão **"Extension Pack for Java"** (da Microsoft), se ainda não tiver.
3. Abra `src/biblioteca/Main.java`.
4. Clique no botão **Run** que aparece acima do método `main`, ou pressione **F5**.

## Pré-requisito

Você precisa ter o **JDK** (Java Development Kit) instalado, versão 17 ou superior.
Para verificar, abra um terminal e digite:

```
java -version
```

Se não estiver instalado, baixe em: https://adoptium.net

O arquivo `.vscode/settings.json` já aponta para uma instalação de JDK 24 detectada
neste ambiente; ajuste o caminho se a sua máquina tiver o JDK em outro lugar.
## Observação

## Dados de exemplo

A pasta `dados/` já vem com alguns registros de exemplo (bibliotecárias, livros,
usuários, um empréstimo ativo e uma reserva ativa) para que as telas de login e
listagem não comecem vazias.
