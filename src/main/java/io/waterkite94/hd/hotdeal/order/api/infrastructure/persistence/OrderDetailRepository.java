package io.waterkite94.hd.hotdeal.order.api.infrastructure.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import io.waterkite94.hd.hotdeal.order.api.domain.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderDetailRepository {

	private final JdbcTemplate jdbcTemplate;

	public void saveAll(List<OrderDetailDto> orderDetails, String orderId) {
		String sql = "INSERT INTO hotdeal.order_detail "
			+ "(quantity, total_discount, total_price, item_id, order_id, created_at, updated_at) "
			+ "VALUES (?, ?, ?, ?, ?, now(), now())";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderDetailDto orderDetail = orderDetails.get(i);

				ps.setInt(1, orderDetail.getQuantity());
				ps.setInt(2, orderDetail.getTotalDiscount());
				ps.setInt(3, orderDetail.getTotalPrice());
				ps.setLong(4, orderDetail.getItemId());
				ps.setString(5, orderId);
			}

			@Override
			public int getBatchSize() {
				return orderDetails.size();
			}
		});
	}
}
