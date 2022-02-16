package ru.gb.external.api.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.external.api.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
    AccountRole findByName(String name);
}
