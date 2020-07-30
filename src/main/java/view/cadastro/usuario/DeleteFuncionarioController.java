package view.cadastro.usuario;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import control.support.AlertMaker;
import control.support.NotificationsMaker;
import dao.DAO;
import dao.DAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Funcionario;

public class DeleteFuncionarioController implements Initializable {

	private final RequiredFieldValidator reqFieldValidator = new RequiredFieldValidator();
	private final DAO<Funcionario> daoFuncionario = new DAOImpl<Funcionario>();
	private final ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXTextField txt_Buscar;

	@FXML
	private JFXButton btn_Atualizar;

	@FXML
	private JFXComboBox<Funcionario> cbo_Funcionario;

	@FXML
	private JFXButton btn_Deletar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		automate();
	}

	private void automate() {
		reqFieldValidator.setMessage("Campo obrigatório");

		cbo_Funcionario.getValidators().add(reqFieldValidator);

		btn_Deletar.setOnAction(e -> {
			if (validacoes()) {
				if (AlertMaker.popUpSimpleYesNoAlert("Exclusão", null,
						"Deseja realmente deletar este funcionário?") == AlertMaker.NO) {
					NotificationsMaker.popUpSimpleInformationNotification("Exclusão", "Exclusão cancelada");
					return;
				}

				Funcionario funcionario = cbo_Funcionario.getSelectionModel().getSelectedItem();
				if (daoFuncionario.remove(Funcionario.class, funcionario.getCodigo())) {
					NotificationsMaker.popUpSimpleInformationNotification("Exclusão",
							String.format("%s excluído com sucesso!", funcionario.getNome()));
					funcionarios.remove(funcionario);
				} else {
					NotificationsMaker.popUpSimpleErrorNotification("Exclusão",
							String.format("%s não pôde ser excluído!", funcionario.getNome()));
				}
				setFuncionarios();
			}
		});

		txt_Buscar.textProperty().addListener((observable, oldValue, newValue) -> {
			FilteredList<Funcionario> filteredList = new FilteredList<Funcionario>(funcionarios, p -> true);
			filteredList.setPredicate(f -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseValue = newValue.toLowerCase();
				if (f.getNome().toLowerCase().contains(lowerCaseValue)) {
					return true;
				}

				return false;

			});

			SortedList<Funcionario> sortedList = new SortedList(filteredList);

			cbo_Funcionario.setItems(sortedList);

			if (sortedList.size() == 1) {
				cbo_Funcionario.getSelectionModel().selectFirst();
			} else {
				cbo_Funcionario.show();
			}
		});

		btn_Atualizar.setOnAction(e -> {
			setFuncionarios();
		});

		btn_Atualizar.fire();
	}

	private void setFuncionarios() {
		funcionarios.clear();
		funcionarios.addAll(daoFuncionario.select("from Funcionario f order by f.nome", Funcionario.class));
		cbo_Funcionario.setItems(funcionarios);
	}

	private boolean validacoes() {
		if (!cbo_Funcionario.validate()) {
			return false;
		}

		return true;
	}

}
