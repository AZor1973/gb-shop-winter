package ru.gb.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
