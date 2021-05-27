package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import db.DB;
import entities.Order;
import entities.OrderStatus;
import entities.Product;

public class Program {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = DB.getConnection();
	
		Statement st = conn.createStatement();
			
		ResultSet rs = st.executeQuery("SELECT * FROM tb_order "
				+"INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
				+"INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");

		Map<Long,Order> map = new HashMap<>();
		Map<Long,Product> mapprod = new HashMap<>();
		while (rs.next()) {

			Long orderId = rs.getLong("order_id");
			if(map.get(orderId) == null){
				Order order = instantiateOrder(rs);
				map.put(orderId,order);
			}

			Long productId = rs.getLong("product_id");
			if(mapprod.get(productId) ==null) {
				Product p = instantiateProduct(rs);
				mapprod.put(productId,p);
			}
			map.get(orderId).getProds().add(mapprod.get(productId));

		}
		for(Long orderId :map.keySet()){
			System.out.println(map.get(orderId));
			for(Product p : map.get(orderId).getProds()){
				System.out.println(p);
			}
			System.out.println();
		}
	}
	private static Product instantiateProduct(ResultSet rs) throws SQLException{
		Product prod = new Product();
		prod.setId(rs.getLong("Id"));
		prod.setDescription(rs.getString("description"));
		prod.setName(rs.getString("name"));
		prod.setImageUrl(rs.getString("image_uri"));
		prod.setPrice(rs.getDouble("price"));
		return prod;
	}
	private static Order instantiateOrder(ResultSet rs) throws SQLException{
		Order order = new Order();
		order.setId(rs.getLong("Id"));
		order.setLatitude(rs.getDouble("latitude"));
		order.setLongitude(rs.getDouble("longitude"));
		order.setMoment(rs.getTimestamp("moment").toInstant());
		order.setStatus(OrderStatus.values()[rs.getInt("status")]);
		return order;
	}
}
