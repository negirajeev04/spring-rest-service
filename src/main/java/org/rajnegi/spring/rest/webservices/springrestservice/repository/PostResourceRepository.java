package org.rajnegi.spring.rest.webservices.springrestservice.repository;

import org.rajnegi.spring.rest.webservices.springrestservice.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostResourceRepository extends JpaRepository<Post, Integer> {

}
