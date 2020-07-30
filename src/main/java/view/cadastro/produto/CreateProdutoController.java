package view.cadastro.produto;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import control.support.CustomFieldValidator;
import control.support.NotificationsMaker;
import dao.DAO;
import dao.DAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Produto;

public class CreateProdutoController implements Initializable {

	private final DAO<Produto> daoProduto = new DAOImpl<Produto>();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXTextField txt_Nome;

	@FXML
	private JFXTextArea txt_Descricao;

	@FXML
	private JFXTextField txt_Preco;

	@FXML
	private JFXTextField txt_Qntd;

	@FXML
	private JFXButton btn_Cadastrar;

	@FXML
	private JFXButton btn_Limpar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		automate();
	}

	private void automate() {
		CustomFieldValidator reqFieldValidator = new CustomFieldValidator(
				new int[] { CustomFieldValidator.REQUIRED_FIELD_VALIDATOR }, 1000);

		CustomFieldValidator reqIntFieldValidator = new CustomFieldValidator(
				new int[] { CustomFieldValidator.REQUIRED_FIELD_VALIDATOR, CustomFieldValidator.INTEGER_VALIDATOR },
				false);

		CustomFieldValidator reqDoubleFieldValidator = new CustomFieldValidator(
				new int[] { CustomFieldValidator.REQUIRED_FIELD_VALIDATOR, CustomFieldValidator.DOUBLE_VALIDATOR },
				false);

		txt_Nome.getValidators().add(reqFieldValidator);
		txt_Descricao.getValidators().add(reqFieldValidator);
		txt_Preco.getValidators().add(reqDoubleFieldValidator);
		txt_Qntd.getValidators().add(reqIntFieldValidator);

		btn_Limpar.setOnAction(e -> {
			txt_Nome.clear();
			txt_Descricao.clear();
			txt_Preco.clear();
			txt_Qntd.clear();
		});

		btn_Cadastrar.setOnAction(e -> {
			if (validacoes()) {
				String nome = txt_Nome.getText();
				String descricao = txt_Descricao.getText();
				double preco = Double.parseDouble(txt_Preco.getText());
				int qntd = Integer.parseInt(txt_Qntd.getText());

				Produto produto = new Produto();
				produto.setNome(nome);
				produto.setDescricao(descricao);
				produto.setPreco(preco);
				produto.setQntd(qntd);

				if (daoProduto.persist(produto)) {
					NotificationsMaker.popUpSimpleInformationNotification("Cadastro Produto",
							String.format("%s cadastrado com sucesso!", nome));
					btn_Limpar.fire();
				} else {
					NotificationsMaker.popUpSimpleErrorNotification("Cadastro Produto", "Erro ao cadastrar");
				}

			}
		});
	}

	private boolean validacoes() {
		if (!txt_Nome.validate()) {
			return false;
		}

		if (!txt_Descricao.validate()) {
			return false;
		}

		if (!txt_Preco.validate()) {
			return false;
		}

		if (!txt_Qntd.validate()) {
			return false;
		}

		return true;
	}

}
