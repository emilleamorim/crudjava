package DAO;

import Classes.ConnectionFactory;
import beans.Cad_funcionarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cad_funcionariosDAO {
    private ConnectionFactory connectionfactory;
    private Connection conn;
    
    public cad_funcionariosDAO(){
        this.connectionfactory = new ConnectionFactory();
        this.conn = ConnectionFactory.getConnection();
    }
    
    public void insert(Cad_funcionarios cad_funcionarios){
        String sql = "INSERT INTO cad_funcionarios(matricula, cpf, nome, cargo, salario, senha, permite_login) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, cad_funcionarios.getMatricula());
            stmt.setString(2, cad_funcionarios.getCpf());
            stmt.setString(3, cad_funcionarios.getNome());
            stmt.setString(4, cad_funcionarios.getCargo());
            stmt.setDouble(5, cad_funcionarios.getSalario());
            stmt.setString(6, cad_funcionarios.getSenha());
            stmt.setInt(7, cad_funcionarios.getPermite_login());
            stmt.execute();
        }
        catch(SQLException e){
            System.out.println("Erro ao inserir funcionário: "+ e.getMessage());
            
        }
    }
    
    public void update(Cad_funcionarios cad_funcionarios){
        String sql = "UPDATE cad_funcionarios SET matricula=?, cpf=?, nome=?, cargo=?, salario=?, senha=?, permite_login=? WHERE id=?";
        try{
            Cad_funcionarios funcionarioAtual = getFuncById(String.valueOf(cad_funcionarios.getId()));

            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, cad_funcionarios.getMatricula());
            stmt.setString(2, cad_funcionarios.getCpf());
            stmt.setString(3, cad_funcionarios.getNome());
            stmt.setString(4, cad_funcionarios.getCargo());
            stmt.setDouble(5, cad_funcionarios.getSalario());
            String senha = cad_funcionarios.getSenha().isEmpty() ? funcionarioAtual.getSenha() : cad_funcionarios.getSenha();
            stmt.setString(6, senha);
            stmt.setInt(7, cad_funcionarios.getPermite_login());
            stmt.setInt(8, cad_funcionarios.getId());
            stmt.execute();
        } catch (SQLException e){
            System.out.println("Erro ao editar o dado: "+ e.getMessage());
        }
    }
    
    public void delete(String nome){
        String sql = "DELETE FROM cad_funcionarios WHERE nome=?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.execute();
        } catch (SQLException e){
            System.out.println("Erro ao excluir cadastro: "+ e.getMessage());
        }
    }
    
    public Cad_funcionarios getFunc(String nome){
        String sql = "SELECT * FROM cad_funcionarios WHERE nome = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Cad_funcionarios funcionarios = new Cad_funcionarios();
            rs.next();
            funcionarios.setNome(nome);
            funcionarios.setMatricula(rs.getInt("matricula"));
            funcionarios.setCargo(rs.getString("cargo"));
            funcionarios.setCpf(rs.getString("cpf"));
            funcionarios.setPermite_login(rs.getInt("permite_login"));
            funcionarios.setSalario(rs.getDouble("salario"));
            funcionarios.setSenha(rs.getString("senha"));
            funcionarios.setId(rs.getInt("id"));
            return funcionarios;
        } catch (SQLException e){
        System.out.println("Erro ao inserir funcionário: "+ e.getMessage());
        return null;
        }
    }
    
    public Cad_funcionarios getFuncById(String id){
        String sql = "SELECT * FROM cad_funcionarios WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            Cad_funcionarios funcionarios = new Cad_funcionarios();
            rs.next();
            funcionarios.setId(rs.getInt("id"));
            funcionarios.setNome(rs.getString("nome"));
            funcionarios.setMatricula(rs.getInt("matricula"));
            funcionarios.setCargo(rs.getString("cargo"));
            funcionarios.setCpf(rs.getString("cpf"));
            funcionarios.setPermite_login(rs.getInt("permite_login"));
            funcionarios.setSalario(rs.getDouble("salario"));
            funcionarios.setSenha(rs.getString("senha"));
            return funcionarios;
        } catch (SQLException e){
        System.out.println("Erro ao inserir funcionário: "+ e.getMessage());
        return null;
        }
    }
    
    public List<Cad_funcionarios> getFunc(){
        String sql = "SELECT * FROM cad_funcionarios";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<Cad_funcionarios> listaFunc = new ArrayList<>();
            while(rs.next()){
                Cad_funcionarios funcionarios = new Cad_funcionarios();
                funcionarios.setNome(rs.getString("nome"));
                funcionarios.setMatricula(rs.getInt("matricula"));
                funcionarios.setCargo(rs.getString("cargo"));
                funcionarios.setCpf(rs.getString("cpf"));
                funcionarios.setPermite_login(rs.getInt("permite_login"));
                funcionarios.setSalario(rs.getDouble("salario"));
                funcionarios.setSenha(rs.getString("senha"));
                funcionarios.setId(rs.getInt("id"));
                listaFunc.add(funcionarios);
            }
            return listaFunc;
        } catch (SQLException e){
            System.out.println("Erro ao carregar dados: "+ e.getMessage());
            return null;
        }
    }
    
    public List<Cad_funcionarios> getFuncByName(String nome){
        String sql = "SELECT * FROM cad_funcionarios where nome like ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            List<Cad_funcionarios> listaFunc = new ArrayList<>();
            while(rs.next()){
                Cad_funcionarios funcionarios = new Cad_funcionarios();
                funcionarios.setNome(rs.getString("nome"));
                funcionarios.setMatricula(rs.getInt("matricula"));
                funcionarios.setCargo(rs.getString("cargo"));
                funcionarios.setCpf(rs.getString("cpf"));
                funcionarios.setPermite_login(rs.getInt("permite_login"));
                funcionarios.setSalario(rs.getDouble("salario"));
                funcionarios.setSenha(rs.getString("senha"));
                funcionarios.setId(rs.getInt("id"));
                listaFunc.add(funcionarios);
            }
            return listaFunc;
        } catch (SQLException e){
            System.out.println("Erro ao carregar dados: "+ e.getMessage());
            return null;
        }
    }
            
}