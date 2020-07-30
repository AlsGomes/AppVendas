package view.login;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import control.support.NotificationsMaker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoginController implements Initializable {

	private final RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
	private final FuncionarioDAO usuarioDAO = new FuncionarioDAO();

	@FXML
	private JFXTextField txtUser;

	@FXML
	private JFXPasswordField txtSenha;

	@FXML
	private JFXButton btnOk;

	@FXML
	private JFXButton btnSair;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		automate();
	}

	private void automate() {
		requiredFieldValidator.setMessage("Campo obrigatório");

		txtSenha.getValidators().add(requiredFieldValidator);
		txtUser.getValidators().add(requiredFieldValidator);

		btnSair.setOnAction((ev) -> {
			System.exit(0);
		});

		btnOk.setOnAction((ev) -> {
			if (verificacoes()) {
				if (usuarioDAO.logar(txtUser.getText().trim(), txtSenha.getText().trim())) {
					NotificationsMaker.popUpSimpleInformationNotification("Login", "Bem-vindo!");					
				} else {
					NotificationsMaker.popUpSimpleWarningNotification("Login", "Usuário ou senha inválido");
				}
			}
		});
	}

	private boolean verificacoes() {
		if (!txtUser.validate()) {
			return false;
		}

		if (!txtSenha.validate()) {
			return false;
		}

		return true;
	}

}
