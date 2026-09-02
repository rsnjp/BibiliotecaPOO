# Sistema de Biblioteca — Java

Projeto criado a partir da UML da atividade "Sistema de Biblioteca".

## Estrutura

```
SistemaBiblioteca/
└── src/
    └── biblioteca/
        ├── App.java          (classe principal, com o método main)
        ├── Bibliotecaria.java
        ├── Livro.java
        ├── Usuario.java
        ├── Emprestimo.java
        └── Reserva.java
```

## Como abrir no VS Code

1. Extraia este .zip em uma pasta no seu computador.
2. Abra o VS Code.
3. Vá em **File > Open Folder** e selecione a pasta `SistemaBiblioteca` (a pasta que contém a subpasta `src`).
4. Instale a extensão **"Extension Pack for Java"** (da Microsoft), se ainda não tiver.
5. Abra `src/biblioteca/App.java`.
6. Clique no botão **Run** que aparece acima do método `main`, ou pressione **F5**.

## Observação

Os métodos de negócio (`cadastrarLivro`, `registrarEmprestimo`, `cancelarReserva`, etc.)
estão **apenas declarados**, sem implementação completa — conforme pedido na atividade.
Por isso, ao rodar `App.java`, alguns valores podem aparecer como `null`. Isso é esperado
neste momento do exercício.
