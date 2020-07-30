package view.venda;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import control.support.CustomFieldValidator;
import control.support.CustomValidation;
import control.support.NotificationsMaker;
import dao.DAO;
import dao.DAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import model.Produto;

public class CreateVendaController implements Initializable {

	private final CustomFieldValidator reqFieldValidator = new CustomFieldValidator(
			new int[] { CustomFieldValidator.REQUIRED_FIELD_VALIDATOR });

	private final CustomFieldValidator doubleFieldValidator = new CustomFieldValidator(
			new int[] { CustomFieldValidator.DOUBLE_VALIDATOR, CustomFieldValidator.REQUIRED_FIELD_VALIDATOR }, false);

	private final CustomValidation<String> customValidation = new CustomValidation<String>() {
		@Override
		public boolean validate(String toValidate) {
			return CustomFieldValidator.isNumber(toValidate, true, true);
		}
	};

	private final ObservableList<ItemVenda> carrinho = FXCollections.observableArrayList();
	private final ObservableList<Produto> produtos = FXCollections.observableArrayList();
	private final DAO<Produto> daoProduto = new DAOImpl<Produto>();

	@FXML
	private JFXListView<ItemVenda> lst_Carrinho;

	@FXML
	private JFXButton btn_Finalizar;

	@FXML
	private JFXButton btn_Descartar;

	@FXML
	private JFXTextField txt_Funcionario;

	@FXML
	private JFXTextField txt_Buscar;

	@FXML
	private JFXButton btn_Buscar;

	@FXML
	private JFXComboBox<Produto> cbo_Produto;

	@FXML
	private JFXTextField txt_Codigo;

	@FXML
	private JFXTextField txt_Nome;

	@FXML
	private JFXTextArea txt_Descricao;

	@FXML
	private JFXTextField txt_Estoque;

	@FXML
	private JFXTextField txt_Preco;

	@FXML
	private Spinner<Integer> spn_Qntd;

	@FXML
	private JFXButton btn_InserirCarrinho;

	@FXML
	private JFXTextField txt_Desconto;

	@FXML
	private JFXTextField txt_Total;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		automate();
	}

	private void automate() {
		txt_Buscar.getValidators().add(reqFieldValidator);
		txt_Funcionario.getValidators().add(reqFieldValidator);		
		txt_Preco.getValidators().add(doubleFieldValidator);
		txt_Total.getValidators().add(doubleFieldValidator);
		cbo_Produto.getValidators().add(reqFieldValidator);

		btn_Buscar.setOnAction(e -> {
			if (txt_Buscar.validate()) {
				produtos.clear();
				String sql;
				String buscar = txt_Buscar.getText();
				if (CustomFieldValidator.isNumber(txt_Buscar.getText(), true, false)) {
					sql = "from Produto p where (p.nome like '%" + buscar + "%' or p.codigo = " + buscar
							+ ") order by p.nome";
				} else {
					sql = "from Produto p where p.nome like '%" + buscar + "%' order by p.nome";
				}
				produtos.addAll(daoProduto.select(sql, Produto.class));
				cbo_Produto.setItems(produtos);
				if (produtos.size() == 1) {
					cbo_Produto.getSelectionModel().selectFirst();
				} else {
					cbo_Produto.show();
				}
			}
		});

		cbo_Produto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				txt_Codigo.setText(String.valueOf(newValue.getCodigo()));
				txt_Nome.setText(newValue.getNome());
				txt_Descricao.setText(newValue.getDescricao());
				txt_Estoque.setText(String.valueOf(newValue.getQntd()));
				txt_Preco.setText(String.valueOf(newValue.getPreco()));
				if (newValue.getQntd() > 0) {
					spn_Qntd.setDisable(false);
					spn_Qntd.setValueFactory(
							new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newValue.getQntd(), 1, 1));
				}
			} else {
				spn_Qntd.setDisable(true);
				txt_Codigo.clear();
				txt_Nome.clear();
				txt_Descricao.clear();
				txt_Estoque.clear();
				txt_Preco.clear();
			}
		});

		spn_Qntd.valueProperty().addListener((observable, oldValue, newValue) -> {
			Produto produto = cbo_Produto.getSelectionModel().getSelectedItem();
			if (produto != null) {
				double total = (double) newValue * produto.getPreco();
				if (CustomFieldValidator.isNumber(txt_Desconto.getText(), true, false)) {
					total -= Double.parseDouble(txt_Desconto.getText());
				}
				total = total >= 0 ? total : 0d;
				String totalFormatado = String.format("%.2f", total);
				totalFormatado = totalFormatado.replace(",", ".");
				txt_Total.setText(totalFormatado);
			}
		});

		txt_Desconto.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!CustomFieldValidator.isNumber(newValue, true, false)) {
				if (newValue.isEmpty() || newValue == null) {
					txt_Desconto.clear();
				} else {
					txt_Desconto.setText(oldValue);
				}
			}
		});

		txt_Desconto.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				Produto produto = cbo_Produto.getSelectionModel().getSelectedItem();
				if (produto != null) {
					double total = spn_Qntd.getValue() * produto.getPreco();
					if (CustomFieldValidator.isNumber(txt_Desconto.getText(), true, false)) {
						total -= Double.parseDouble(txt_Desconto.getText());
					}
					total = total >= 0 ? total : 0d;
					String totalFormatado = String.format("%.2f", total);
					totalFormatado = totalFormatado.replace(",", ".");
					txt_Total.setText(totalFormatado);
				}
				if (CustomFieldValidator.isNumber(txt_Desconto.getText(), true, false)) {
					String descontoFormatado = String.format("%.2f", Double.parseDouble(txt_Desconto.getText()));
					descontoFormatado = descontoFormatado.replace(",", ".");
					txt_Desconto.setText(descontoFormatado);
				}
			}
		});

		carrinho.addListener(new ListChangeListener<ItemVenda>() {
			@Override
			public void onChanged(Change<? extends ItemVenda> c) {
				if (c.next()) {
					btn_Finalizar.setDisable(carrinho.isEmpty());
				}
			}
		});

		lst_Carrinho.setExpanded(true);
		lst_Carrinho.setVerticalGap(5d);
		lst_Carrinho.setDepth(5);
		lst_Carrinho.setItems(carrinho);

		btn_InserirCarrinho.setOnAction(e -> {
			if (itemValidado()) {
				Produto produto = cbo_Produto.getSelectionModel().getSelectedItem();
				int qntd = spn_Qntd.getValue();
				double precoUnitario = produto.getPreco();
				double precoTotal = precoUnitario * qntd;
				double valorDesconto = (txt_Desconto.getText() == null || txt_Desconto.getText().isEmpty()) ? 0
						: Double.parseDouble(txt_Desconto.getText());
				double precoCobrado = Double.parseDouble(txt_Total.getText());
				carrinho.add(new ItemVenda(produto, qntd, precoUnitario, precoTotal, valorDesconto, precoCobrado));
			} else {
				NotificationsMaker.popUpSimpleInformationNotification("Atenção", "Item Inválido");
			}
		});

		btn_Finalizar.setDisable(carrinho.isEmpty());
	}

	private boolean itemValidado() {
		if (spn_Qntd.getValue() == null || spn_Qntd.getValue() == 0) {
			return false;
		}

		if (!cbo_Produto.validate()) {
			return false;
		}

		if (!CustomFieldValidator.validate(customValidation, txt_Desconto.getText())) {
			return false;
		}

		if (!txt_Total.validate()) {
			return false;
		}

		return true;
	}
}
