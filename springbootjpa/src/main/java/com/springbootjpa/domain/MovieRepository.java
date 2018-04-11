package com.springbootjpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 持久层: 由 jpa 管理
 *
 * @author SONG
 */
public interface MovieRepository extends JpaRepository<Movie, Integer> {
  // 包含了一般的增删改查

  // hql: Hiberante Query Language:Hiberante 查询语言 - 类名 + 属性名
  @Query(value = "from Movie m where m.name=:name")
  List<Movie> findByMovieName(@Param("name")String name);
}
