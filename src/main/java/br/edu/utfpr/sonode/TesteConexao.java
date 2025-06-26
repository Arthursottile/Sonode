package br.edu.utfpr.sonode;

import br.edu.utfpr.sonode.util.JPAUtil;
import org.hibernate.Session;

public class TesteConexao {
    public static void main(String[] args) {
        // abre e fecha uma sessão só para testar a conexão
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            System.out.println("Conexão com MySQL via Hibernate estabelecida com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JPAUtil.getSessionFactory().close();
        }
    }
}
