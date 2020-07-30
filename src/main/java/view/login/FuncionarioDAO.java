package view.login;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import dao.DAO;
import dao.DAOImpl;
import model.Funcionario;

public class FuncionarioDAO {

	private DAO<Funcionario> daoFuncionario = new DAOImpl<Funcionario>();
	private static Funcionario usuarioLogado = null;

	public boolean logar(String usuario, String senha) {

		String hql = "";
		List<Funcionario> list = daoFuncionario.select(String.format("from Funcionario f where f.nome = '%s'", usuario),
				Funcionario.class);

		if (!list.isEmpty()) {
			Funcionario funcionario = list.get(0);
			if (funcionario.getSenha().equals(DigestUtils.shaHex(senha))) {
				usuarioLogado = funcionario;
			} else {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	public static Funcionario getUsuarioLogado() {
		return usuarioLogado;
	}

}
