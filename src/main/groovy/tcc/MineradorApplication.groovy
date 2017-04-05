package tcc

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.query.Param
import tcc.entity.Foto
import tcc.entity.Pessoa
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository

@SpringBootApplication
class MineradorApplication {

	static void main(String[] args) {
		SpringApplication.run MineradorApplication, args
	}

	@Bean
	public CommandLineRunner demo(PessoaRepository pr, FotoRepository fr) {

			/*def p1 = pr.findById(4)

			Pessoa p = new Pessoa()
			p.setNomeCompleto("Paula")

			Foto f = new Foto()
			f.setPath("teste/teste/teste")
			f.setOwner(p1)
			def list = new ArrayList<Pessoa>()
			list.add(p1)
			list.add(p)
			f.setCompostaPor(list)

			// save a couple of customers
			pr.save(p)
			fr.save(f)*/

		return null
	}
}
