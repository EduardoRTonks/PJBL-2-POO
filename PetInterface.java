import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class PetInterface extends JFrame {

    static Scanner leitor = new Scanner(System.in);
    private int largura, altura; // Dimensões da interface
    private JButton button; // Botões das opções
    private static JTextArea logArea = new JTextArea("", 15, 66); // Área de log das ações
    static int sizeLog = 0; // Contador de ações registradas no log
    private static String FILE_NAME = "tutores.dat"; // Nome do arquivo para persistência
    private static ArrayList<Tutor> tutor = new ArrayList<>(); // Lista de tutores cadastrados
    private JTextField nome, dia, mes, ano, ender; // Campos do formulário
    private static final String[] option = { "Cadastrar Tutor/Pets", "Imprimir Cadastro",
            "Buscar Por Código", "Excluir Tutor/Pets", "Encerrar" }; // Opções do menu

    // Carrega dados do arquivo para a lista
    public static void carregarCadastro() {
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
                ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            tutor = (ArrayList<Tutor>) objIn.readObject();
            writeLog("Dados carregados com sucesso!");
        } catch (FileNotFoundException e) {
            writeLog("Arquivo não encontrado. Criando novo.");
            salvarCadastro();
            tutor = new ArrayList<>();
        } catch (EOFException e) {
            writeLog("Leitura do arquivo concluída.");
        } catch (IOException | ClassNotFoundException e) {
            writeLog("Erro ao carregar dados: " + e.getMessage());
        }
    }

    // Salva os dados da lista no arquivo
    public static void salvarCadastro() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(tutor);
        } catch (IOException e) {
            writeLog("Erro ao salvar dados: " + e.getMessage());
        }
    }
    static Font f=new Font("Consolas", Font.PLAIN, 13);  
    // Gera um código único para novos tutores
    public static int geraCodCont() {
        return tutor.isEmpty() ? 1 : tutor.get(tutor.size() - 1).getCod() + 1;
    }

    // Constrói a interface principal
    public PetInterface(int largura, int altura) {
        super("CADASTRO DE TUTORES");
        this.largura = largura;
        this.altura = altura;

        setLayout(new FlowLayout());
        JPanel painel = new JPanel();
        painel.setPreferredSize(new Dimension(largura, altura));
        add(painel);
        pack();

        // Adiciona botões do menu
        ButtonHandler handler = new ButtonHandler();
        for (int k = 0; k < 5; k++) {
            button = new JButton(option[k]);
            painel.add(button);
            if (k != 4)
                button.addActionListener(handler);
        }

        // Adiciona função de encerramento ao último botão
        button.addActionListener(e -> {
            PetInterface.this.dispose();
            System.exit(0);
        });

        // Área de log
        painel.add(new JLabel("        "));
        painel.add(new JLabel("Log de ações realizadas na sessão."));
        Box box = Box.createHorizontalBox();
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        logArea.setEditable(false);
        box.add(new JScrollPane(logArea));
        painel.add(box);
    }

    // Gerencia ações dos botões
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand() == option[0]) {
                cadastrar();
                salvarCadastro();
            } else if (event.getActionCommand() == option[1]) {
                imprimir();
            } else if (event.getActionCommand() == option[2]) {
                buscar();
            } else if (event.getActionCommand() == option[3]) {
                excluir();
                salvarCadastro();
            }
        }
    }

    // Registra mensagens no log
    public static void writeLog(String s) {
        if (sizeLog == 0) {
            logArea.append("LOG CRIADO...");
            logArea.append("\n");
        }
        logArea.append("\n- " + s);
        sizeLog++;
    }

    // Inicia o cadastro de tutor
    private void cadastrar() {
        cadastrarFrame cf = new cadastrarFrame();
        cf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        cf.setSize(largura, 200);
        cf.setLocationRelativeTo(null);
        cf.setVisible(true);
    }

    // Frame para cadastro de tutor
    private class cadastrarFrame extends JFrame {
        private JButton ok;

        public cadastrarFrame() {
            super("Cadastro Tutor");
            setLayout(new FlowLayout(FlowLayout.LEADING));
            add(new JLabel("Nome"));
            nome = new JTextField("", 35);
            add(nome);
            add(new JLabel("Data de nasc.(dd/mm/aaaa)"));
            dia = new JTextField(2);
            add(dia);
            add(new JLabel("/"));
            mes = new JTextField(2);
            add(mes);
            add(new JLabel("/"));
            ano = new JTextField(4);
            add(ano);
            add(new JLabel("                                             "));
            add(new JLabel("Endereço"));
            ender = new JTextField("", 40);
            add(ender);
            ok = new JButton("Cadastrar");
            add(ok);

            // Ação para cadastrar tutor e pets
            ok.addActionListener(e -> {
                String n = nome.getText(), d = dia.getText(), m = mes.getText(), a = ano.getText(), l = ender.getText();
                if(n.length()==0||d.length()==0||m.length()==0||a.length()==0 || l.length()==0){
                    JOptionPane.showMessageDialog(PetInterface.this, "Todos os campos devem ser preenchidos!");
                    return;
                }
                int D, M, A;
                try {
                    D = Integer.parseInt(d);
                    M = Integer.parseInt(m);
                    A = Integer.parseInt(a);
                    if (!Tutor.valiData(D, M, A)) {
                        JOptionPane.showMessageDialog(PetInterface.this, "Data inválida!");
                        return;
                    }
                    LocalDate.of(A, M, D);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PetInterface.this, "Data inválida!");
                    return;
                }
                Tutor novotutor = new Tutor(n, D, M, A, l, geraCodCont());
                cadastrarPetFrame pf = new cadastrarPetFrame(novotutor);
                pf.setTitle("Cadastro de Pet");
                pf.setSize(500, 200);
                pf.setLocationRelativeTo(null);
                pf.setVisible(true);
                tutor.add(novotutor);
                String msg = String.format("Cadastrado: %s (%s/%s/%s) %s.", n, d, m, a, l);
                JOptionPane.showMessageDialog(PetInterface.this, msg);
                writeLog(msg);
                salvarCadastro();
                nome.setText("");
                dia.setText("");
                mes.setText("");
                ano.setText("");
                ender.setText("");
            });
        }
    }

    // Frame para cadastro de pet
    private class cadastrarPetFrame extends JFrame {
        private JButton ok;
        private JTextField nomePet, tipoPet, dia, mes, ano;

        public cadastrarPetFrame(Tutor novotutor) {
            setLayout(new FlowLayout(FlowLayout.LEADING));
            add(new JLabel("Nome do Pet:"));
            nomePet = new JTextField("", 35);
            add(nomePet);
            add(new JLabel("Tipo:"));
            tipoPet = new JTextField("", 20);
            add(tipoPet);
            add(new JLabel("                                      "));
            add(new JLabel("Data de nasc.(dd/mm/aaaa)"));
            dia = new JTextField(2);
            add(dia);
            add(new JLabel("/"));
            mes = new JTextField(2);
            add(mes);
            add(new JLabel("/"));
            ano = new JTextField(4);
            add(ano);
            ok = new JButton("Cadastrar Pet");
            add(ok);
            // Cadastro de pets
            ok.addActionListener(e -> {
                String petName = nomePet.getText(), petTipo = tipoPet.getText(), d = dia.getText(), m = mes.getText(),
                        a = ano.getText();        
 
             // Declaração de variáveis para conversão de texto em inteiros.
                int D = 0, M = 0, A = 0;

                // Valida se todos os campos do formulário foram preenchidos.
                if (petName.length() == 0 || d.length() == 0 || m.length() == 0 || a.length() == 0 || petTipo.length() == 0) {
                    JOptionPane.showMessageDialog(PetInterface.this, "Todos os campos devem ser preenchidos!");
                    return;
                }

                try {
                    // Converte os valores de data para inteiros.
                    D = Integer.parseInt(d);
                    M = Integer.parseInt(m);
                    A = Integer.parseInt(a);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PetInterface.this, "Todos os campos de data devem ser numéricos!");
                    return;
                }

                try {
                    // Valida a data usando um método externo.
                    if (!Tutor.valiData(D, M, A)) {
                        JOptionPane.showMessageDialog(PetInterface.this, "Os campos de data devem conter uma data válida!");
                        return;
                    }

                    // Cria um objeto LocalDate para armazenar a data.
                    LocalDate dataNasc = LocalDate.of(A, M, D);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PetInterface.this, ex.toString());
                    return;
                }

                // Registra o novo pet no tutor.
                novotutor.incluiPet(petName, petTipo, D, M, A);
                salvarCadastro(); // Salva alterações no sistema.
                String msg = String.format("Pet cadastrado: %s - %s.", petTipo, petName);
                JOptionPane.showMessageDialog(this, msg);
                writeLog(msg); // Registra o cadastro no log.

                // Limpa os campos do formulário para o próximo cadastro.
                nomePet.setText("");
                tipoPet.setText("");
                dia.setText("");
                mes.setText("");
                ano.setText("");

    	       });
    	  }
    } 

 // Abre uma janela para exibição dos tutores e pets cadastrados.
    public void imprimir() {
        impTutoresFrame it = new impTutoresFrame();
        it.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Apenas esconde a janela ao fechar.
        it.setSize(largura - 45, 215); // Define tamanho da janela.
        it.setLocationRelativeTo(null); // Centraliza a janela.
        it.setVisible(true); // Torna visível.
    }

    private class impTutoresFrame extends JFrame {
        private static JTextArea cadArea = new JTextArea("", 10, 61);

        public impTutoresFrame() {
            super("CADASTRO DE TUTORES E PETS");
            cadArea.setText(""); // Reseta o conteúdo da área de texto.
            cadArea.setEditable(false); // Impede edição.
            cadArea.setFont(f); // Aplica a fonte padrão.
            setLayout(new FlowLayout(FlowLayout.LEADING)); // Define layout.
            Box box = Box.createHorizontalBox(); // Caixa para adicionar elementos.
            box.add(new JScrollPane(cadArea)); // Adiciona rolagem à área de texto.
            add(box); // Adiciona a caixa à janela.

            if (tutor.size() > 0) { // Verifica se há cadastros.
                cadArea.append("--- TUTORES E PETS CADASTRADOS -----------------------------\n");
                for (Tutor t : tutor)
                    cadArea.append(t.toString() + "\n\n"); // Lista os tutores.
                writeLog("Cadastro impresso.");
            } else {
                cadArea.append("Cadastro vazio.");
                writeLog("Cadastro vazio.");
            }
        }
    }

 // Abre uma janela para buscar informações de um tutor.
    public void buscar() {
        if (tutor.size() == 0) { // Verifica se há cadastros.
            String msg = "Não existem tutores cadastrados.";
            JOptionPane.showMessageDialog(PetInterface.this, msg);
            writeLog(msg);
            return;
        }
        buscarFrame bf = new buscarFrame();
        bf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Apenas esconde a janela.
        bf.setSize(largura, 200); // Define tamanho.
        bf.setLocationRelativeTo(null); // Centraliza.
        bf.setVisible(true); // Torna visível.
    }

    private class buscarFrame extends JFrame {
        private JTextField cod;
        private JTextArea cadArea;
        private JButton ok;

        public buscarFrame() {
            super("Buscar Tutor");
            setLayout(new FlowLayout(FlowLayout.LEADING));

            add(new JLabel("Cód.:"));
            cod = new JTextField(5);
            add(cod);

            ok = new JButton("Buscar");
            add(ok);

            cadArea = new JTextArea(8, 25);
            cadArea.setEditable(false); // Apenas leitura.
            add(new JScrollPane(cadArea));

            ok.addActionListener(e -> {
                cadArea.setText("");
                String c = cod.getText();

                if (c.length() == 0) { // Valida se o campo está vazio.
                    JOptionPane.showMessageDialog(PetInterface.this, "O campo de código deve ser preenchido!");
                    return;
                }

                int C;
                try {
                    C = Integer.parseInt(c); // Converte para número.
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PetInterface.this, "O campo de código deve ser numérico!");
                    return;
                }

                boolean tutorEncontrado = false;
                for (Tutor t : tutor) { // Busca pelo código.
                    if (t.getCod() == C) {
                        cadArea.append("Tutor Encontrado: \n" + t.toString());
                        writeLog("Tutor Encontrado: \n" + t.toString());
                        tutorEncontrado = true;
                    }
                }

                if (!tutorEncontrado) { // Caso não encontre o tutor.
                    JOptionPane.showMessageDialog(PetInterface.this, "Nenhum tutor encontrado com esse código!");
                }
                cod.setText(""); // Limpa campo.
            });

            setSize(600, 400); // Configurações de janela.
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

 // Abre uma janela para exclusão de tutores.
    public void excluir() {
        if (tutor.size() == 0) { // Verifica se há cadastros.
            String msg = "Não existem tutores cadastrados.";
            JOptionPane.showMessageDialog(PetInterface.this, msg);
            writeLog(msg);
            return;
        }
        excluirFrame ef = new excluirFrame();
        ef.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ef.setSize(largura, 200);
        ef.setLocationRelativeTo(null);
        ef.setVisible(true);
    }

    private class excluirFrame extends JFrame {
        private JTextField cod;
        private JTextArea cadArea;
        private JButton ok;

        public excluirFrame() {
            super("Excluir Tutor");
            setLayout(new FlowLayout(FlowLayout.LEADING));

            add(new JLabel("Cód.:"));
            cod = new JTextField(5);
            add(cod);

            ok = new JButton("Excluir");
            add(ok);

            cadArea = new JTextArea(8, 25);
            cadArea.setEditable(false);
            add(new JScrollPane(cadArea));

            ok.addActionListener(e -> {
                cadArea.setText("");
                String c = cod.getText();

                if (c.length() == 0) { // Valida campo vazio.
                    JOptionPane.showMessageDialog(PetInterface.this, "O campo de código deve ser preenchido!");
                    return;
                }

                int C;
                try {
                    C = Integer.parseInt(c); // Converte para número.
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PetInterface.this, "O campo de código deve ser numérico!");
                    return;
                }

                boolean tutorEncontrado = false;
                for (Tutor t : tutor) { // Procura o tutor pelo código.
                    if (t.getCod() == C) {
                        tutor.remove(t); // Remove do cadastro.
                        salvarCadastro();
                        cadArea.append("Tutor removido:\n" + t.toString());
                        JOptionPane.showMessageDialog(PetInterface.this, "Tutor removido!");
                        writeLog("Tutor Removido: \n" + t.toString());
                        tutorEncontrado = true;
                        return;
                    }
                }

                if (!tutorEncontrado) { // Caso tutor não seja encontrado.
                    String msg = "Nenhum tutor encontrado com esse código!";
                    JOptionPane.showMessageDialog(PetInterface.this, msg);
                    writeLog(msg);
                }
            });

            setSize(600, 400); // Configurações da janela.
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}
