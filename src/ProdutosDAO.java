/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public int cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();
         int status = 0;

        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)");

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

           status = prep.executeUpdate();
            if (status > 0) {
                System.out.println("Produto cadastrado com sucesso.");
            } else {
                System.out.println("Falha ao cadastrar o produto.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar o produto: " + ex.getMessage());
        } finally {
           
            try {
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar o PreparedStatement: " + ex.getMessage());
            }
        }
          return status;
    }
    

    public ArrayList<ProdutosDTO> listarProdutos() {
       
        listagem.clear();

        try {
            conn = new conectaDAO().connectDB(); // Obtém a conexão com o banco de dados
            prep = conn.prepareStatement("SELECT * FROM produtos");
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar os produtos: " + ex.getMessage());
        } finally {
            // Fechar recursos (ResultSet e PreparedStatement)
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar os recursos: " + ex.getMessage());
            }
        }

        return listagem;
    }
}
