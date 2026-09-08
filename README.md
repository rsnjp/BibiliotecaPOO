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
        │   └── ManipuladorArquivos.java   (persistência em .csv)
        ├── controle/                (Controller: valida entradas, liga View ao Model)
        │   ├── LivroControle.java
        │   ├── UsuarioControle.java
        │   ├── BibliotecariaControle.java
        │   ├── EmprestimoControle.java
        │   └── ReservaControle.java
        └── visao/                   (View: janelas Swing)
            ├── menus/
            │   ├── MenuInicial.java
            │   ├── MenuBibliotecaria.java
            │   └── MenuUsuario.java
            └── telas/
                ├── TelaCadastroLivro.java
                ├── TelaCadastroUsuario.java
                ├── TelaRegistrarEmprestimo.java
                ├── TelaRegistrarDevolucao.java
                ├── TelaRegistrarReserva.java
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
  monta os objetos do Model (gerando o próximo id via `ManipuladorArquivos.proximoId`)
  e decide qual tela abrir a seguir.

Ao abrir o sistema, o `MenuInicial` pergunta o perfil de acesso:

- **Bibliotecária**: cadastra livros e usuários, registra empréstimos,
  devoluções e reservas.
- **Usuário**: consulta os livros disponíveis e lista/cancela seus próprios
  empréstimos e reservas.

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

## Dados de exemplo

A pasta `dados/` já vem com alguns registros de exemplo (bibliotecárias, livros,
usuários, um empréstimo ativo e uma reserva ativa) para que as telas de login e
listagem não comecem vazias.
