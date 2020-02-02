package wang.ismy.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.sell.pojo.entity.OrderDetail;

import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 16:14
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
