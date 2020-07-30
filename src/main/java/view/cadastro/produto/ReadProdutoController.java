package view.cadastro.produto;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import dao.DAO;
import dao.DAOImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Produto;

public class ReadProdutoController implements Initializable {

	private final ObservableList<Produto> produtos = FXCollections.observableArrayList();
	private final DAO<Produto> daoProdutos = new DAOImpl<Produto>();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXTextField txt_Buscar;

	@FXML
	private JFXButton btn_Atualizar;

	@FXML
	private JFXListView<Produto> lst_Produtos;

	@FXML
	private VBox vbox_Infos;

	@FXML
	private JFXTextField txt_Codigo;

	@FXML
	private JFXTextField txt_Nome;

	@FXML
	private JFXTextArea txt_Descricao;

	@FXML
	private JFXTextField txt_Preco;

	@FXML
	private JFXTextField txt_Quantidade;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initFields();
		automate();
	}

	private void initFields() {
		lst_Produtos.setDepth(5);
		lst_Produtos.setVerticalGap(5d);
		lst_Produtos.setExpanded(true);
	}

	private void automate() {
		setProdutos();

		txt_Buscar.textProperty().addListener((observable, oldValue, newValue) -> {
			FilteredList<Produto> filteredList = new FilteredList<Produto>(produtos, p -> true);
			filteredList.setPredicate(p -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String newValueToLowerCase = newValue.toLowerCase();
				if (p.getNome().toLowerCase().contains(newValueToLowerCase)) {
					return true;
				}

				if (String.valueOf(p.getCodigo()).contains(newValueToLowerCase)) {
					return true;
				}

				return false;
			});

			SortedList<Produto> sortedList = new SortedList<Produto>(filteredList);
			lst_Produtos.setItems(sortedList);
			if (sortedList.size() == 1) {
				lst_Produtos.getSelectionModel().selectFirst();
			}
		});

		lst_Produtos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				txt_Codigo.setText(String.valueOf(newValue.getCodigo()));
				txt_Nome.setText(newValue.getNome());
				txt_Descricao.setText(newValue.getDescricao());
				txt_Preco.setText(String.valueOf(newValue.getPreco()));
				txt_Quantidade.setText(String.valueOf(newValue.getQntd()));
			} else {
				txt_Codigo.clear();
				txt_Nome.clear();
				txt_Descricao.clear();
				txt_Preco.clear();
				txt_Quantidade.clear();
			}
		});

		btn_Atualizar.setOnAction(e -> {
			setProdutos();
		});

//		lst_Produtos.setCellFactory(lv -> {
//			ListCell<Produto> cell = new ListCell<Produto>() {
//				@Override
//				protected void updateItem(Produto item, boolean empty) {
//					super.updateItem(item, empty);
//					setItem(item);
//					if (!empty) {
//						setText(item.toString());
//					} else {
//						setText(null);
//					}
//				}
//			};
//
//			cell.setOnMouseClicked(e -> {
//				if (!cell.isEmpty()) {
//					txt_Codigo.setText(String.valueOf(cell.getItem().getCodigo()));
//					txt_Nome.setText(cell.getItem().getNome());
//					txt_Descricao.setText(cell.getItem().getDescricao());
//					txt_Preco.setText(String.valueOf(cell.getItem().getPreco()));
//					txt_Quantidade.setText(String.valueOf(cell.getItem().getQntd()));
//				} else {
//					txt_Codigo.clear();
//					txt_Nome.clear();
//					txt_Descricao.clear();
//					txt_Preco.clear();
//					txt_Quantidade.clear();
//				}
//				e.consume();
//			});
//
//			return cell;
//		});
	}

	private void setProdutos() {
		Platform.runLater(() -> {
			produtos.clear();
			produtos.addAll(daoProdutos.select("from Produto p order by p.nome", Produto.class));
			lst_Produtos.setItems(produtos);
		});
	}

}
