package Classes;

import Models.Funcionario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/funcionarios";
    private static final String USER = "root";
    private static final String PASS = "1234";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; //"com.mysql.jdbc.Driver";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro na conexão com o Banco de Dados: " + e);
        }
    }
    
    public static boolean login(Funcionario funcionario) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstm = conn.prepareStatement("Select * from cad_funcionarios where matricula = ? and senha = ?");
            pstm.setInt(1, funcionario.getMatricula());
            pstm.setString(2, funcionario.getSenha());
            
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next())
            {
                int permiteLogin = rs.getInt("permite_login");
                
                if (permiteLogin == 1) {
                    return true;
                }
                
                System.out.println("O usuário: " + rs.getString("nome") + " Não tem permissão para logar");
                JOptionPane.showMessageDialog(null, "Você não tem permissão para logar");
            }
        } catch(Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
        
        return false;
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados: " + e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados: " + e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados: " + e);
        }
    }
}
