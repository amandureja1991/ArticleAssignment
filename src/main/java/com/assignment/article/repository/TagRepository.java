package com.assignment.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.article.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "Select tag,count(*) from Tag group by tag", nativeQuery = true)
    public List<Object[]> findTagOccurence();
}
