package io.starkindustries.my_app.repos;

import io.starkindustries.my_app.domain.UsuarioSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioSupervisorRepository extends JpaRepository<UsuarioSupervisor, Long> {
    UsuarioSupervisor findByUsername(String username);
}