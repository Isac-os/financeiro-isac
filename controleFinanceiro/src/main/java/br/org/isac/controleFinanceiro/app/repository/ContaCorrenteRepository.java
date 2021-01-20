package br.org.isac.controleFinanceiro.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.org.isac.controleFinanceiro.app.entity.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer> {
	
	@Query("select u from ContaCorrente u where u.origem = :origem")
	public Optional<List<ContaCorrente>> findAllPorTipo(@Param("origem") String origem);

}
