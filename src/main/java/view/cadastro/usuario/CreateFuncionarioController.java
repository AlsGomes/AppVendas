package view.cadastro.usuario;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import control.support.NotificationsMaker;
import dao.DAO;
import dao.DAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Funcionario;

public class CreateFuncionarioController implements Initializable {

	private final RequiredFieldValidator reqFieldValidator = new RequiredFieldValidator();
	private final DAO<Funcionario> daoFuncionario = new DAOImpl<Funcionario>();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXTextField txt_Nome;

	@FXML
	private JFXTextField txt_Cargo;

	@FXML
	private JFXPasswordField txt_Senha;

	@FXML
	private JFXPasswordField txt_SenhaConfirm;

	@FXML
	private JFXButton btn_Cadastrar;

	@FXML
	private JFXButton btn_Limpar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		automate();
	}

	private void automate() {
		reqFieldValidator.setMessage("Campo obrigatório");

		txt_Nome.getValidators().add(reqFieldValidator);
		txt_Cargo.getValidators().add(reqFieldValidator);
		txt_Senha.getValidators().add(reqFieldValidator);
		txt_SenhaConfirm.getValidators().add(reqFieldValidator);

		btn_Limpar.setOnAction(e -> {
			txt_Nome.clear();
			txt_Cargo.clear();
			txt_Senha.clear();
			txt_SenhaConfirm.clear();
		});

		btn_Cadastrar.setOnAction(e -> {
			if (validacoes()) {
				String nome = txt_Nome.getText().trim();
				String cargo = txt_Cargo.getText().trim();
				String senha = txt_Senha.getText().trim();

				Funcionario funcionario = new Funcionario();
				funcionario.setNome(nome);
				funcionario.setCargo(cargo);
				funcionario.setSenha(senha);

				if (daoFuncionario.persist(funcionario)) {
					NotificationsMaker.popUpSimpleInformationNotification("Cadastro Funcionário",
							String.format("%s cadastrado com sucesso!", nome));
					btn_Limpar.fire();
				} else {
					NotificationsMaker.popUpSimpleErrorNotification("Cadastro Funcionário", "Erro ao cadastrar");
				}

			}
		});
	}

	private boolean validacoes() {

		if (!txt_Nome.validate()) {
			return false;
		}

		if (!txt_Cargo.validate()) {
			return false;
		}

		if (!txt_Senha.validate()) {
			return false;
		}

		if (!txt_SenhaConfirm.validate()) {
			return false;
		}

		if (!txt_Senha.getText().trim().equals(txt_SenhaConfirm.getText().trim())) {
			NotificationsMaker.popUpSimpleWarningNotification("Atenção", "Senhas não coincidem. Reescreva as senhas");
			return false;
		}

		String nome = txt_Nome.getText().trim();

		List<Funcionario> funcionarios = daoFuncionario
				.select(String.format("from Funcionario f where f.nome = '%s'", nome), Funcionario.class);
		if (!funcionarios.isEmpty()) {
			NotificationsMaker.popUpSimpleWarningNotification("Atenção", "Usuário já existe. Escolha outro nome");
			return false;
		}

		return true;
	}

}
