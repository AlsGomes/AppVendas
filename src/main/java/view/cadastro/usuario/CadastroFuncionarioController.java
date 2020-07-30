package view.cadastro.usuario;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import control.support.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.layout.AnchorPane;
import view.floatmenu.FloatMenu;

public class CadastroFuncionarioController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private AnchorPane palco;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	public void init() {
		SceneLoader sceneLoader = new SceneLoader(palco);
		sceneLoader.addScreen("DeleteFuncionario", "/view/cadastro/usuario/DeleteFuncionarioUI.fxml");
		sceneLoader.addScreen("CreateFuncionario", "/view/cadastro/usuario/CreateFuncionarioUI.fxml");

		JFXButton btn_Cadastrar = new JFXButton("Cadastrar");
		btn_Cadastrar.setOnAction(e -> {
			sceneLoader.activate("CreateFuncionario");
			SceneLoader.anchor(sceneLoader.getScreen("CreateFuncionario"), 0d);
		});

		JFXButton btn_Excluir = new JFXButton("Excluir");
		btn_Excluir.setOnAction(e -> {
			sceneLoader.activate("DeleteFuncionario");
			SceneLoader.anchor(sceneLoader.getScreen("DeleteFuncionario"), 0d);
		});

		JFXButton btn_Alterar = new JFXButton("Alterar");

		List<JFXButton> listCadastros = new ArrayList<JFXButton>();
		listCadastros.add(btn_Cadastrar);
		listCadastros.add(btn_Alterar);
		listCadastros.add(btn_Excluir);

		JFXButton btn_Listar = new JFXButton("Listar");
		List<JFXButton> listLista = new ArrayList<JFXButton>();
		listLista.add(btn_Listar);

		FloatMenu menu = new FloatMenu();
		menu.getMenuMap().put("Cadastros", listCadastros);
		menu.getMenuMap().put("Consulta", listLista);
		menu.refresh();

		root.getChildren().add(menu);
		SceneLoader.anchor(menu, 0d);
	}

}
