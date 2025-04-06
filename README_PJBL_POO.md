# PJBL 2 - Programação Orientada a Objetos 🐾

Este projeto é uma aplicação simples de gerenciamento de pet shop desenvolvida como parte do Projeto Integrador PJBL 2 na disciplina de Programação Orientada a Objetos (POO).

## 📁 Estrutura de Arquivos

- `Pet.java`: Classe que representa um animal de estimação.
- `PetInterface.java`: Interface com métodos obrigatórios para o comportamento dos pets.
- `Petshop.java`: Classe principal que simula a lógica do pet shop (cadastro, exibição de dados etc).
- `Tutor.java`: Classe que representa o tutor (dono) do pet.
- `tutores.dat`: Arquivo de dados binário usado para armazenar os tutores cadastrados.
- `cat.jpg`: Imagem decorativa/ilustrativa utilizada na aplicação.

## 🚀 Como Executar

1. Compile os arquivos `.java`:

```bash
javac *.java
```

2. Execute a classe principa `Petshop` :

```bash
java Petshop
```

3. Acompanhe a execução via terminal/interação CLI conforme definido nos métodos da classe `Petshop`.

## 🧰 Tecnologias

- Linguagem: Java
- Paradigma: Programação Orientada a Objetos (POO)
- Armazenamento: Arquivo binário (`tutores.dat`)

## 💡 Possíveis Melhorias

- Implementar interface gráfica com JavaFX ou Swing
- Persistência com banco de dados (ex: SQLite)
- Serialização mais robusta com tratamento de exceções
- Suporte a múltiplos tipos de pets com herança

## 📄 Licença

Uso livre para fins educacionais. Sinta-se à vontade para estudar e modificar!

---

Desenvolvido para o projeto PJBL 2.
