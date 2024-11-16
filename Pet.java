import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Pet implements Serializable {
	// Atributos da classe
	private String nomePet;
	private String tipoPet;
	private LocalDate dataNascimento;

	// Método construtor
	public Pet(String nomePet, String tipoPet, int dia, int mes, int ano) {
		this.nomePet = nomePet;
		this.tipoPet = tipoPet;
		this.dataNascimento = LocalDate.of(ano,mes,dia);
	}

	// Retorna o nome do pet
	public String getNomePet() {
		return nomePet;
	}

	// Retorna o tipo do pet
	public String getTipoPet() {
		return tipoPet;
	}
    public String getDataNascPet() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(fmt);
    }


	// Calcula a idade do pet
	public int getIdade() {
		return dataNascimento != null ? Period.between(dataNascimento, LocalDate.now()).getYears() : 0;
	}

	// Método para imprimir os detalhes do pet, incluindo a idade
	public String toString() {
		return "    Nome do Pet: " + getNomePet()+ "; " +
				"Tipo do Pet: " + getTipoPet() + "; " +
				"Data de Nascimento: " + getDataNascPet()+
				" ("+ getIdade() + " anos). \n";
	}
}
