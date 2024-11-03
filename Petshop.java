	import java.util.ArrayList;
	import java.util.Scanner;
	import java.io.*;


	public class Petshop implements Serializable {

		static Scanner leitor = new Scanner (System.in);
		private static String FILE_NAME = "tutores.dat"; // Nome do arquivo para armazenar os dados
		private static ArrayList<Tutor> tutor = new ArrayList<Tutor>(); // Lista de tutores

		public static void carregarCadastro() {
			FileInputStream fileIn = null;
			ObjectInputStream objIn = null;

			try {
				fileIn = new FileInputStream(FILE_NAME); // Abre o arquivo
				objIn = new ObjectInputStream(fileIn); // Converte para objeto
				tutor = (ArrayList<Tutor>) objIn.readObject(); // Lê o ArrayList de tutores
				System.out.println("Dados carregados com sucesso!");
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo de dados não encontrado. Um novo arquivo será criado na primeira gravação.");
				tutor = new ArrayList<>(); // Inicializa uma nova lista vazia de tutores
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Erro ao carregar dados: " + e.getMessage());
			} finally {
				try {
					if (objIn != null) objIn.close(); // Fecha o ObjectInputStream
					if (fileIn != null) fileIn.close(); // Fecha o FileInputStream
				} catch (IOException e) {
					System.out.println("Erro ao fechar o arquivo de leitura: " + e.getMessage());
				}
			}
		}

		public static void salvarCadastro() {
			FileOutputStream fileOut = null;
			ObjectOutputStream objOut = null;

			try {
				fileOut = new FileOutputStream(FILE_NAME); // Abre ou cria o arquivo
				objOut = new ObjectOutputStream(fileOut); // Converte para objeto
				objOut.writeObject(tutor); // Grava o ArrayList de tutores
				System.out.println("Dados salvos com sucesso!");
			} catch (IOException e) {
				System.out.println("Erro ao salvar dados: " + e.getMessage());
			} finally {
				try {
					if (objOut != null) objOut.close(); // Fecha o ObjectOutputStream
					if (fileOut != null) fileOut.close(); // Fecha o FileOutputStream
				} catch (IOException e) {
					System.out.println("Erro ao fechar o arquivo de gravação: " + e.getMessage());
				}
			}
		}

		public static int geraCodCont(){ // Gera código p/ contribuinte. // CREIO EU QUE DE PRA REUTILIZAR TUDO ISSO AI PRO CONTADOR :P
			 if (tutor.size()==0)
				 return 1;
			 else // Incrementa o código do contribuinte no final da lista.
				 return tutor.get(tutor.size()-1).getCod()+1;
		}

		public static void cadTutorPets() {
			String nomeTutor;

			do {
				nomeTutor = obterNomeTutor();
				if (nomeTutor.equals("")) break;

				int[] dataNascimento = obterDataNascimento("tutor");
				String endereco = obterEndereco();

				int codTutor = geraCodCont(); // Geração de código do tutor
				Tutor novotutor = new Tutor(nomeTutor, dataNascimento[0], dataNascimento[1], dataNascimento[2], endereco, codTutor);

				cadastrarPetsParaTutor(novotutor);

				// Adiciona o tutor completo com pets à lista de tutores
				tutor.add(novotutor);
				System.out.println("--- Tutor cadastrado ---");
			} while (!nomeTutor.equals(""));
		}

		// Método para obter o nome do tutor
		private static String obterNomeTutor() {
			System.out.println("Digite o nome do Tutor (vazio encerra cadastro tutor): ");
			return leitor.nextLine();
		}


		// Método para obter o endereço do tutor
		private static String obterEndereco() {
			String endereco;
			do {
				System.out.println("Digite endereço do tutor/pet:");
				endereco = leitor.nextLine();
				if (endereco.equals("")) {
					System.out.println("Campo de endereço vazio!");
				}
			} while (endereco.equals(""));
			return endereco;
		}

		// Método para cadastrar pets para o tutor
		private static void cadastrarPetsParaTutor(Tutor tutor) {
			String nomePet;
			do {
				nomePet = obterNomePet();
				if (nomePet.equals("")) break;

				String tipoPet = obterTipoPet();
				int[] dataNascimento = obterDataNascimento("pet");
				tutor.incluiPet(nomePet, tipoPet, dataNascimento[0], dataNascimento[1], dataNascimento[2]);  // Adiciona o pet ao tutor
				System.out.println("--- Pet cadastrado ---");
			} while (!nomePet.equals(""));
		}

		// Método para obter a data de nascimento do tutor
		private static int[] obterDataNascimento(String tipo) {
			int dia = 1, mes = 1, ano = 1;
			boolean dataValida = false;

			do {
				try {
					System.out.println("Digite dia (dd), mês (mm) e ano (aaaa) de nascimento do " + tipo + " (separados por espaços):");
					String dataNascimento = leitor.nextLine();
					String[] partesData = dataNascimento.split(" ");

					if (partesData.length != 3) {
						System.out.println("Entrada inválida: por favor, insira o dia, mês e ano separados por espaços.");
						continue;
					}

					dia = Integer.parseInt(partesData[0]);
					mes = Integer.parseInt(partesData[1]);
					ano = Integer.parseInt(partesData[2]);

					dataValida = Tutor.valiData(dia, mes, ano);

				} catch (NumberFormatException e) {
					System.out.println("Erro de formato: por favor, insira números válidos.");
				}
			} while (!dataValida);

			return new int[]{dia, mes, ano};
		}



		// Método para obter o nome do pet
		private static String obterNomePet() {
			System.out.println("Digite nome do pet (vazio encerra cadastro pet): ");
			return leitor.nextLine();
		}

		// Método para obter o tipo do pet
		private static String obterTipoPet() {
			System.out.println("Digite tipo do pet:");
			return leitor.nextLine();
		}





		public static void imprimiCad() {
			System.out.println("\n--- CADASTRO DE TUTORES E PETS ------------------------------------------------------\n");
			if (tutor.size()==0) {
				System.out.println("Não existem tutores cadastrados.");
			}
			for (Tutor t:tutor)
				System.out.println(t.toString()+"\n"); // precisar criar um toString no Tutor, creio que vai ter que criar um toString no Pets também pra formatar
			System.out.println("------------------------------------------------------------------------------\n");
		}

		public static void buscarPets() {

			if(tutor.size()==0) {
				System.out.println("Não existem tutores cadastrados.");
			}else{
				double numLeitor=0;
				while (true) {
					System.out.println("Digite o código do tutor a ser localizado: ");
					//verifica se a proxima entrada esta no formato valido
					if (leitor.hasNextDouble()) {
						numLeitor = leitor.nextDouble(); clearBuffer(leitor); // Lê o código do tutor se for um número válido
						break; // Se a entrada for válida, sai do loop
					} else {
						System.out.println("Erro: Entrada inválida. Por favor, insira um número válido.");
						leitor.next(); // Limpa a entrada inválida do buffer
					}
				}
				boolean tutorEncontrado = false;
				for (Tutor t : tutor) {
					if (t.getCod() == numLeitor) {
						System.out.println(t.toString());
						tutorEncontrado = true;
						break; // Interrompe o loop após encontrar o tutor
					}
				}

				if (!tutorEncontrado) {
					System.out.println("Nenhum tutor com o código " + numLeitor + " encontrado.");
				}

			}	
		}

		public static void excluiPets(){
			if(tutor.size()==0) {
				System.out.println("Não existem tutores cadastrados.");
			}else{
				double numLeitor=0;

				while (true) {
					System.out.println("Digite o código do tutor a ser localizado: ");
					//verifica se a proxima entrada esta no formato valido
					if (leitor.hasNextDouble()) {
						numLeitor = leitor.nextDouble(); clearBuffer(leitor); // Lê o código do tutor se for um número válido
						break; // Se a entrada for válida, sai do loop
					} else {
						System.out.println("Erro: Entrada inválida. Por favor, insira um número válido.");
						leitor.next(); // Limpa a entrada inválida do buffer
					}
				}
				boolean tutorEncontrado = false;
				for (Tutor t : tutor) {
					if (t.getCod() == numLeitor) {
						tutor.remove(t);
						System.out.println("--- Tutor (+pets) com código " + numLeitor + " excluído com sucesso! ---");
						tutorEncontrado = true;
						break; // Interrompe o loop após encontrar e remover o tutor
					}
				}

				if (!tutorEncontrado) {
					System.out.println("Nenhum tutor com o código " + numLeitor + " encontrado.");
				}

			}		
		}

		public static void main(String[] args){
			carregarCadastro();
		    String opcao = "y";
			while (!opcao.equals("x")) {
		          System.out.println("\n--- Menu ---");
		          System.out.println("c. Cadastrar tutor + pet(s)");
		          System.out.println("i. imprimir cadastro");
		          System.out.println("b. buscar pets por codigo tutor");
		          System.out.println("e. excluir pets ");
		          System.out.println("x. encerrar");
		          System.out.print("Opção escolhida: ");
		          opcao = leitor.nextLine();
		          
		          switch (opcao) {
		             case "c":
		                cadTutorPets();
						 salvarCadastro();
						 break;
		             case "i":
		                imprimiCad();
		                break;
		             case "b":
		                buscarPets();
		                break;
		             case "e":
		                excluiPets();
						 salvarCadastro();
						 break;
		             case "x":
		                System.out.println("\n--- Programa de cadastro encerrado ---");
		                break;
		             default:
		                System.out.println("Opção inválida! Tente novamente.");
		          }
			}
		}
		private static void clearBuffer(Scanner scanner) {
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		}
	}
	

