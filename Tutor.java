import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.io.*;

public class Tutor implements Serializable {

    // ATRIBUTOS DA CLASSE
    private String nomeTutor;
    private String ender;
    private int cod;
    private LocalDate dataNasc;

    // LISTA DINÂMICA
    private ArrayList<Pet> pet = new ArrayList<Pet>();

    // MÉTODO CONSTRUTOR
    public Tutor(String nomeTutor, int dia, int mes, int ano, String ender, int cod) {
        this.nomeTutor = nomeTutor;
        this.ender = ender;
        this.cod = cod;
        this.dataNasc = LocalDate.of(ano, mes, dia);
    }

    // RETORNA ANO ATUAL
    public static int getAnoAtual() {
        LocalDate dataSistema = LocalDate.now();
        return dataSistema.getYear();
    }

    // RETORNA MÊS ATUAL
    public static int getMesAtual() {
        LocalDate dataSistema = LocalDate.now();
        return dataSistema.getMonth().getValue();
    }

    // RETORNA DIA ATUAL
    public static int getDiaAtual() {
        LocalDate dataSistema = LocalDate.now();
        return dataSistema.getDayOfMonth();
    }

    // VERIFICA SE É ANO BISSEXTO
    public static boolean bissexto(int ano) {
        return (ano % 4 == 0);
    }

    public static boolean valiData(int dia, int mes, int ano) {
        // Verifica se o ano é válido
        if (ano > getAnoAtual() || ano < 1) {
            return false;
        }

        // Verifica se o mês é válido
        if (mes < 1 || mes > 12) {
            return false;
        }

        // Verifica se o mês e o dia são válidos em relação ao mês e ano atuais
        if (ano == getAnoAtual() && (mes > getMesAtual() || (mes == getMesAtual() && dia > getDiaAtual()))) {
            return false;
        }

        // Verifica o número máximo de dias do mês
        int maxDia;
        switch (mes) {
            case 2:
                maxDia = bissexto(ano) ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDia = 30;
                break;
            default:
                maxDia = 31;
        }

        // Verifica se o dia é válido
        if (dia < 1 || dia > maxDia) {        
            return false;
        }

        return true; // Data válida
    }

    // Método para incluir pet
    public void incluiPet(String nomePet, String tipoPet, int dia, int mes, int ano) {
        Pet pt = new Pet(nomePet, tipoPet, dia, mes, ano);
        pet.add(pt);
    }

    // RETORNA NOME DO TUTOR
    public String getnomeTutor() {
        return nomeTutor;
    }

    // RETORNA CÓDIGO DO TUTOR
    public int getCod() {
        return cod;
    }

    // RETORNA DATA DE NASCIMENTO DO TUTOR FORMATADO
    public String getDataNasc() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNasc.format(fmt);
    }

    // CALCULA E RETORNA A IDADE DO TUTOR
    public int getIdade() {
        LocalDate dataSistema = LocalDate.now();
        int idade = Period.between(dataNasc, dataSistema).getYears(); // Calcula a idade
        return idade;
    }
    public String getEnder() {
    	return ender;
    }

    // Método para retornar a lista de pets cadastrados no tutor
    public ArrayList<Pet> getPets() {
        return pet;
    }

    // GERA E RETORNA UMA STRING COM O CADASTRO DO TUTOR
    public String toString() {
        // *** CADASTRO DE CONTRIBUINTE *** << tem disso, mas ta fora do toString
        String s = "Cod. do tutor: " + getCod() + "\n";
        s += "  Nome...........: " + getnomeTutor() + "\n";
        s += "  Data de nascimento: " + getDataNasc() + " (" + getIdade() + " anos)\n";
        s += "  Endereço.......: " + getEnder() + "\n";
        s += "  Relação de Pets:\n";
        if (pet.size() == 0) {
            s += "  - Pets de " + getnomeTutor() + ": nenhum cadastrado.";
        } else {
            for (Pet p : pet)
                s += p.toString();
        }
        return s;
    }
}
