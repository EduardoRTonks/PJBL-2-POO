import javax.swing.JFrame;
public class Petshop{
   public static void main(String[] args){
      // Cria a interface/frame do sistema de cadastro.
      // Os dados de entrada: (largura-pixels,altura-pixels) da �rea �til do jFrame.
      PetInterface pi=new PetInterface(500,350);
      PetInterface.carregarCadastro();
      // Define que o programa � encerrado ao se fechar a janela.
      pi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Define que a janela deve ser centralizada na tela.
      pi.setLocationRelativeTo(null); 
      // Torna a interface vis�vel.
      pi.setVisible(true);
   }
}
