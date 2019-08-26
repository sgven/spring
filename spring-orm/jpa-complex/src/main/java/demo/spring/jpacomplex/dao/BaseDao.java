package demo.spring.jpacomplex.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
public interface BaseDao<T, Long> extends PagingAndSortingRepository<T, Long> {
    /**
     * 注意：findTop3后面加了一个By
     * findTop3后面是OrderBy，所以findTop3后面没有过滤条件也需要加By
     *
     * 第一个By用作分隔符以指示实际标准的开始
     *
     * 参考文档：https://docs.spring.io/spring-data/data-commons/docs/1.11.0.RELEASE/reference/html/#repositories.query-methods.details
     * @return
     */
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
