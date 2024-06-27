package kevcold.med.voll.api.repository;

import kevcold.med.voll.api.domain.users.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String username);
}
