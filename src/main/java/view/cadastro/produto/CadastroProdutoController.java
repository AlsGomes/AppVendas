package view.cadastro.produto;

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

public class CadastroProdutoController implements Initializable {

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
		sceneLoader.addScreen("DeleteProduto", "/view/cadastro/produto/DeleteProdutoUI.fxml");
		sceneLoader.addScreen("CreateProduto", "/view/cadastro/produto/CreateProdutoUI.fxml");
		sceneLoader.addScreen("UpdateProduto", "/view/cadastro/produto/UpdateProdutoUI.fxml");
		sceneLoader.addScreen("ReadProduto", "/view/cadastro/produto/ReadProdutoUI.fxml");

		JFXButton btn_Cadastrar = new JFXButton("Cadastrar");
		btn_Cadastrar.setOnAction(e -> {
			sceneLoader.activate("CreateProduto");
			SceneLoader.anchor(sceneLoader.getScreen("CreateProduto"), 0d);
		});

		JFXButton btn_Excluir = new JFXButton("Excluir");
		btn_Excluir.setOnAction(e -> {
			sceneLoader.activate("DeleteProduto");
			SceneLoader.anchor(sceneLoader.getScreen("DeleteProduto"), 0d);
		});

		JFXButton btn_Alterar = new JFXButton("Alterar");
		btn_Alterar.setOnAction(e -> {
			sceneLoader.activate("UpdateProduto");
			SceneLoader.anchor(sceneLoader.getScreen("UpdateProduto"), 0d);
		});

		List<JFXButton> listCadastros = new ArrayList<JFXButton>();
		listCadastros.add(btn_Cadastrar);
		listCadastros.add(btn_Alterar);
		listCadastros.add(btn_Excluir);

		JFXButton btn_Listar = new JFXButton("Listar");
		btn_Listar.setOnAction(e -> {
			sceneLoader.activate("ReadProduto");
			SceneLoader.anchor(sceneLoader.getScreen("ReadProduto"), 0d);
		});

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
