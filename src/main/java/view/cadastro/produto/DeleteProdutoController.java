package view.cadastro.produto;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import control.support.AlertMaker;
import control.support.CustomFieldValidator;
import control.support.NotificationsMaker;
import dao.DAO;
import dao.DAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Produto;

public class DeleteProdutoController implements Initializable {
	private final DAO<Produto> daoProduto = new DAOImpl<Produto>();
	private final ObservableList<Produto> produtos = FXCollections.observableArrayList();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXTextField txt_Buscar;

	@FXML
	private JFXButton btn_Atualizar;

	@FXML
	private JFXComboBox<Produto> cbo_Produto;

	@FXML
	private JFXTextArea txt_Descricao;

	@FXML
	private JFXTextField txt_Preco;

	@FXML
	private JFXTextField txt_Codigo;

	@FXML
	private JFXTextField txt_Qntd;

	@FXML
	private JFXButton btn_Deletar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		automate();
	}

	private void automate() {
		CustomFieldValidator reqFieldValidator = new CustomFieldValidator(
				new int[] { CustomFieldValidator.REQUIRED_FIELD_VALIDATOR });

		cbo_Produto.getValidators().add(reqFieldValidator);

		btn_Deletar.setOnAction(e -> {
			if (validacoes()) {
				if (AlertMaker.popUpSimpleYesNoAlert("Exclusão", null,
						"Deseja realmente deletar este produto?") == AlertMaker.NO) {
					NotificationsMaker.popUpSimpleInformationNotification("Exclusão", "Exclusão cancelada");
					return;
				}

				Produto produto = cbo_Produto.getSelectionModel().getSelectedItem();
				if (daoProduto.remove(Produto.class, produto.getCodigo())) {
					NotificationsMaker.popUpSimpleInformationNotification("Exclusão",
							String.format("%s excluído com sucesso!", produto.getNome()));
					produtos.remove(produto);
				} else {
					NotificationsMaker.popUpSimpleErrorNotification("Exclusão",
							String.format("%s não pôde ser excluído!", produto.getNome()));
				}
			}
		});

		cbo_Produto.setOnAction(e -> {
			Produto produto = cbo_Produto.getSelectionModel().getSelectedItem();
			if (produto != null) {
				txt_Codigo.setText(String.valueOf(produto.getCodigo()));
				txt_Descricao.setText(produto.getDescricao());
				txt_Preco.setText(String.valueOf(produto.getPreco()));
				txt_Qntd.setText(String.valueOf(produto.getQntd()));
			} else {
				txt_Codigo.clear();
				txt_Descricao.clear();
				txt_Preco.clear();
				txt_Qntd.clear();
			}
		});

		btn_Atualizar.setOnAction(e -> {
			setProdutos(txt_Buscar.getText());
		});
	}

	private void setProdutos(String nome) {
		produtos.clear();
		if (nome.isEmpty()) {
			produtos.addAll(daoProduto.select("from Produto f order by f.nome", Produto.class));
		} else {
			produtos.addAll(daoProduto.select("from Produto f where f.nome like '%" + nome + "%' order by f.nome",
					Produto.class));
		}
		cbo_Produto.setItems(produtos);
		if (produtos.size() == 1) {
			cbo_Produto.getSelectionModel().selectFirst();
		} else {
			cbo_Produto.show();
		}
	}

	private boolean validacoes() {
		if (!cbo_Produto.validate()) {
			return false;
		}

		return true;
	}

}
