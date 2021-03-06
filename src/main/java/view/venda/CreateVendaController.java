package view.venda;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import control.support.AlertMaker;
import control.support.CustomFieldValidator;
import control.support.CustomValidation;
import control.support.NotificationsMaker;
import dao.DAO;
import dao.DAOImpl;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Funcionario;
import model.Produto;
import model.Venda;

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
	private final DAO<Funcionario> daoFuncionario = new DAOImpl<Funcionario>();
	private Funcionario funcionarioCompra = null;
	private final DoubleProperty valorTotal = new SimpleDoubleProperty();

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
	private Text txt_ValorTotal;

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
		txt_Preco.getValidators().add(doubleFieldValidator);
		txt_Total.getValidators().add(doubleFieldValidator);
		txt_Funcionario.getValidators().add(reqFieldValidator);
		cbo_Produto.getValidators().add(reqFieldValidator);

		txt_ValorTotal.textProperty().bind(valorTotal.asString());

		btn_Buscar.setOnAction(e -> {
			if (txt_Buscar.validate()) {
				produtos.clear();
				String sql;
				String buscar = txt_Buscar.getText();
				if (CustomFieldValidator.isNumber(txt_Buscar.getText(), false, false)) {
					sql = "from Produto p where (p.nome like '%" + buscar + "%' or p.codigo = " + buscar
							+ ") order by p.nome";
				} else {
					sql = "from Produto p where p.nome like '%" + buscar + "%' order by p.nome";
				}
				System.out.println(sql);
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

		carrinho.addListener(new ListChangeListener<VBox>() {
			@Override
			public void onChanged(Change<? extends VBox> c) {
				if (c.next()) {
					btn_Finalizar.setDisable(carrinho.isEmpty());
				}
			}
		});
		btn_Finalizar.setDisable(carrinho.isEmpty());

//		lst_Carrinho.setExpanded(true);
//		lst_Carrinho.setVerticalGap(5d);
//		lst_Carrinho.setDepth(5);
		lst_Carrinho.setItems(carrinho);

		carrinho.addListener(new ListChangeListener<ItemVenda>() {
			@Override
			public void onChanged(Change<? extends ItemVenda> c) {
				if (c.next()) {
					if (c.wasAdded()) {
						c.getAddedSubList().get(0).getPrecoCobradoProperty()
								.addListener((observable, oldValue, newValue) -> {
									valorTotal.setValue(carrinho.stream().mapToDouble(v -> v.getPrecoCobrado()).sum());
								});

						valorTotal.setValue(carrinho.stream().mapToDouble(v -> v.getPrecoCobrado()).sum());
					}

					if (c.wasRemoved()) {
						valorTotal.setValue(carrinho.stream().mapToDouble(v -> v.getPrecoCobrado()).sum());
					}
				}
			}
		});

		Callback<ListView<ItemVenda>, ListCell<ItemVenda>> cb = new Callback<ListView<ItemVenda>, ListCell<ItemVenda>>() {
			@Override
			public ListCell<ItemVenda> call(ListView<ItemVenda> param) {
				ListCell<ItemVenda> cell = new ListCell<ItemVenda>() {
					@Override
					protected void updateItem(ItemVenda item, boolean empty) {
						super.updateItem(item, empty);
						setItem(item);
						if (!empty) {
							setGraphic(item);
						} else {
							setGraphic(null);
						}
					}
				};

				cell.setOnMouseClicked(e -> {
					if (!cell.isEmpty() && e.getButton().equals(MouseButton.SECONDARY)) {
						ContextMenu menu = new ContextMenu();
						MenuItem itemRemover = new MenuItem("Remover");
						itemRemover.setOnAction(ev -> {
							carrinho.remove(cell.getItem());
							menu.hide();
						});

						menu.getItems().add(itemRemover);
						menu.show(cell, e.getScreenX(), e.getScreenY());
					}
				});
				return cell;
			}
		};

		lst_Carrinho.setCellFactory(cb);

//		lst_Carrinho.setCellFactory(lv -> {
//			ListCell<ItemVenda> cell = new ListCell<ItemVenda>() {
//				@Override
//				protected void updateItem(ItemVenda item, boolean empty) {
//					super.updateItem(item, empty);
//					setItem(item);
//					if (!empty) {
//						setGraphic(item);
//					} else {
//						setGraphic(null);
//					}
//				}
//			};
//
//			cell.setOnMouseClicked(e -> {
//				if (!cell.isEmpty() && e.getButton().equals(MouseButton.SECONDARY)) {
//					ContextMenu menu = new ContextMenu();
//					MenuItem itemRemover = new MenuItem("Remover");
//					itemRemover.setOnAction(ev -> {
//						carrinho.remove(cell.getItem());
//						menu.hide();
//					});
//
//					menu.getItems().add(itemRemover);
//					menu.show(cell, e.getScreenX(), e.getScreenY());
//				}
//			});
//
//			return cell;
//		});

		btn_InserirCarrinho.setOnAction(e -> {
			if (itemValidado()) {
				Produto produto = cbo_Produto.getSelectionModel().getSelectedItem();

				List<ItemVenda> produtoIgual = carrinho.stream()
						.filter(p -> produto.getCodigo() == p.getProduto().getCodigo()).collect(Collectors.toList());
				if (!produtoIgual.isEmpty()) {
					NotificationsMaker.popUpSimpleWarningNotification("Aten��o",
							"O produto j� est� no carrinho. Aumente a quantidade, se desejar.");
					return;
				}

				int qntd = spn_Qntd.getValue();
				double precoUnitario = produto.getPreco();
				double precoTotal = precoUnitario * qntd;
				double valorDesconto = (txt_Desconto.getText() == null || txt_Desconto.getText().isEmpty()) ? 0
						: Double.parseDouble(txt_Desconto.getText());
				double precoCobrado = Double.parseDouble(txt_Total.getText());
				carrinho.add(new ItemVenda(produto, qntd, precoUnitario, precoTotal, valorDesconto, precoCobrado));
			} else {
				NotificationsMaker.popUpSimpleInformationNotification("Aten��o", "Item Inv�lido");
			}
		});

		btn_Descartar.setOnAction(e -> {
			if (AlertMaker.popUpSimpleYesNoAlert("Compra de Produtos", null,
					"Deseja descartar essa compra?") == AlertMaker.YES) {
				btn_Descartar.getScene().getWindow().hide();
			}
		});

		btn_Finalizar.setOnAction(e -> {
			if (compraValidada()) {
				if (AlertMaker.popUpSimpleYesNoAlert("Compra de Produtos", null, String.format(
						"Deseja finalizar a compra no valor total de %.2f?", valorTotal.getValue())) == AlertMaker.NO) {
					return;
				}

				Venda venda = new Venda(null, Instant.now(), funcionarioCompra);
				DAO<Venda> daoVenda = new DAOImpl<Venda>();
				daoVenda.persist(venda);

				List<model.ItemVenda> items = new ArrayList<model.ItemVenda>();
				carrinho.forEach(i -> {
					items.add(new model.ItemVenda(i.getProduto(), venda, i.getQntd(), i.getPrecoUnitario(),
							i.getPrecoTotal(), i.getValorDesconto(), i.getPrecoCobrado()));
				});
				DAO<model.ItemVenda> daoItemVenda = new DAOImpl<model.ItemVenda>();
				daoItemVenda.persistAll(items);

				NotificationsMaker.popUpSimpleInformationNotification("Venda", "Venda efetuada com sucesso!");
			}
		});
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

	private boolean compraValidada() {
		if (!txt_Funcionario.validate()) {
			return false;
		}

		String sql;
		String buscar = txt_Funcionario.getText();
		if (CustomFieldValidator.isNumber(buscar, false, false)) {
			sql = "from Funcionario f where (f.nome like '" + buscar + "' or f.codigo = " + buscar + ")";
		} else {
			sql = "from Funcionario f where f.nome like '" + buscar + "'";
		}

		funcionarioCompra = null;
		List<Funcionario> list = daoFuncionario.select(sql, Funcionario.class);
		if (list.isEmpty()) {
			NotificationsMaker.popUpSimpleErrorNotification("Compra de Produtos", "Funcion�rio n�o encontrado");
			return false;
		}
		funcionarioCompra = list.get(0);

		return true;
	}
}
