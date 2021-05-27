package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import entities.Product;

public class Program {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = DB.getConnection();
	
		Statement st = conn.createStatement();
			
		ResultSet rs = st.executeQuery("select * from tb_product");
			
		while (rs.next()) {
			Product prod = new Product();
			prod.setId(rs.getLong("Id"));
			prod.setDescription(rs.getString("description"));
			prod.setName(rs.getString("name"));
			prod.setImageUrl(rs.getString("image_uri"));
			prod.setPrice(rs.getDouble("price"));
			System.out.println(prod);
		}
	}
}
