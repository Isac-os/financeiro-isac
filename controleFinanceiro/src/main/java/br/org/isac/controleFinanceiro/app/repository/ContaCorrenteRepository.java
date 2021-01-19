package br.org.isac.controleFinanceiro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.isac.controleFinanceiro.app.entity.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer> {

}
