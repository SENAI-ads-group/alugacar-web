package br.com.alugacar.services;

import javax.inject.Inject;

import br.com.alugacar.dao.LocacaoDAO;
import br.com.alugacar.dao.MotoristaDAO;
import br.com.alugacar.entidades.Email;
import br.com.alugacar.entidades.EmailMotorista;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.EnderecoMotorista;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.TelefoneMotorista;
import br.com.alugacar.services.exceptions.ServiceException;

public class MotoristaService {

	@Inject
	private MotoristaDAO dao;

	@Inject
	private LocacaoDAO locacaoDao;

	public Motorista getByLocacaoId(Integer locacaoId) {
		Locacao locacao = locacaoDao.buscarId(locacaoId);
		return dao.buscarLocacao(locacao);
	}

	public void cadastrarEndereco(Motorista motorista, EnderecoMotorista endereco) {
		dao.enderecoDAO().adicionarEndereco(motorista, endereco);
	}

	public void cadastrarTelefone(Motorista motorista, TelefoneMotorista telefone) {
		dao.telefoneDAO().adicionarTelefone(motorista, telefone);
	}

	public void cadastrarEmail(Motorista motorista, EmailMotorista email) {
		dao.emailDAO().adicionarEmail(motorista, email);
	}

	public EnderecoMotorista atualizarEndereco(Locacao locacao, Endereco endereco) {
		EnderecoMotorista e = (EnderecoMotorista) dao.enderecoDAO().atualizarEndereco(locacao.getId(), endereco.getId(),
				endereco);
		if (e == null)
			throw new ServiceException("Não foi possível atualizar o endereco");
		return e;
	}

	public TelefoneMotorista atualizarTelefone(Locacao locacao, String strTelefone, Telefone telefone) {
		telefone.setNumero(telefone.getNumero().trim());
		TelefoneMotorista t = (TelefoneMotorista) dao.telefoneDAO().atualizarTelefone(locacao.getId(), strTelefone,
				telefone);
		if (t == null)
			throw new ServiceException("Não foi possível atualizar o telefone");
		return t;
	}

	public EmailMotorista atualizarEmail(Locacao locacao, String strEmail, Email email) {
		strEmail = strEmail.trim().toLowerCase();
		email.setEmail(email.getEmail().trim().toLowerCase());
		EmailMotorista e = (EmailMotorista) dao.emailDAO().atualizarEmail(locacao.getId(), strEmail, email);
		if (e == null)
			throw new ServiceException("Não foi possível atualizar o endereco");
		return (EmailMotorista) email;
	}

	public Telefone getTelefone(Motorista mot, String numero) {
		TelefoneMotorista tel = (TelefoneMotorista) dao.telefoneDAO().buscarNumero(mot, numero);
		if (tel == null)
			throw new ServiceException(
					"Telefone com o número " + numero + " não foi encontado para o motorista " + mot.getNome());
		return tel;
	}

	public void excluirEndereco(Motorista mot, EnderecoCliente endereco) {
		dao.enderecoDAO().removerEndereco(mot.getLocacao().getId(), endereco.getId());
	}

	public void excluirTelefone(Motorista mot, TelefoneCliente telefone) {
		dao.telefoneDAO().removerTelefone(mot.getLocacao().getId(), telefone.getNumero());
	}

	public void excluirEmail(Motorista mot, EmailMotorista email) {
		dao.emailDAO().removerEmail(mot.getLocacao().getId(), email.getEmail());
	}
}
