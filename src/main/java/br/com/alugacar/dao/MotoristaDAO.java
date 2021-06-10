package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.EmailMotorista;
import br.com.alugacar.entidades.EnderecoMotorista;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.TelefoneMotorista;

public interface MotoristaDAO {
	
	List<Motorista> buscarTodos();
	
	List<Motorista> buscarCpf(String cpf);
	
	Motorista buscarLocacao(Locacao locacao);
	
	void adicionarEmail(Locacao locacao, EmailMotorista email);

	void removerEmail(Locacao locacao, String email);

	void adicionarEndereco(Locacao locacao, EnderecoMotorista endereco);

	void removerEndereco(Locacao locacao, EnderecoMotorista endereco);

	void adicionarTelefone(Locacao locacao, TelefoneMotorista telefone);

	void removerTelefone(Locacao locacao, TelefoneMotorista telefone);
}
