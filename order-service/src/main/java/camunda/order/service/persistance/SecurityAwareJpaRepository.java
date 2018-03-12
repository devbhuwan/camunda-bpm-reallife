package camunda.order.service.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface SecurityAwareJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
