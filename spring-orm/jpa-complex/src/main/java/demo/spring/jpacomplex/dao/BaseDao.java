package demo.spring.jpacomplex.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
public interface BaseDao<T, Long> extends PagingAndSortingRepository<T, Long> {
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
