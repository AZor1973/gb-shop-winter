package ru.gb.external.api.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.external.api.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
