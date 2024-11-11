package dao;

import dto.ProdutosDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
       
        try {
            conn = new conectaDAO().connectDB();

            // Instrução SQL para executar o comando de INSERT
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            PreparedStatement query = conn.prepareStatement(sql);

            query.setString(1, produto.getNome());
            query.setDouble(2, produto.getValor());
            query.setString(3, produto.getStatus());

            // Executar o comando
            query.execute();

        } catch (SQLException e) {
            System.out.println(e);
        }
       
      
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();

        try {
            String sql = "SELECT id, nome, valor, status FROM produtos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return produtos;
    
    }  
    
    public void venderProduto(int id) {
        conn = new conectaDAO().connectDB();
        String sql = "UPDATE produtos SET status=? WHERE id=?";
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            int linhaAfetada = prep.executeUpdate();
            if(linhaAfetada>0) {
                JOptionPane.showMessageDialog(null, "Produto vendido.");
            }
            else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto.");
        }
    }
    
     public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE status LIKE ?";
        ArrayList<ProdutosDTO> listagemVendidos = new ArrayList<>();
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            resultset = prep.executeQuery();
            
            while(resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagemVendidos.add(produto);
            }
            return listagemVendidos;
        }
        catch(Exception e) {
            return null;
        }
    }
    
     public ArrayList<ProdutosDTO> listarProdutosNaoVendidos(){
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE status LIKE ?";
        ArrayList<ProdutosDTO> listagemNaoVendidos = new ArrayList<>();
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1, "A Venda");
            resultset = prep.executeQuery();
            
            while(resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagemNaoVendidos.add(produto);
            }
            return listagemNaoVendidos;
        }
        catch(Exception e) {
            return null;
        }
    }
    
}


