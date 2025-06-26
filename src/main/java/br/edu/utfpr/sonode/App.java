package br.edu.utfpr.sonode;

import br.edu.utfpr.sonode.util.JPAUtil;
import br.edu.utfpr.sonode.view.TelaLogin;
import javax.swing.SwingUtilities;


public class App {
    public static void main(String[] args) {
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down application and closing JPA resources.");
            JPAUtil.shutdown();
        }));


        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}