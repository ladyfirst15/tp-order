package myProject_LSP;

import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{


}