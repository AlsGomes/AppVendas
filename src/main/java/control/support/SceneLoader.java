package control.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * This class is used to load either a new stage or just activate a root Parent
 * in the same stage from which it was called.
 * 
 */
public class SceneLoader {

	private HashMap<String, SceneInformation> screenMap = new HashMap<String, SceneInformation>();
	private Pane main;

	/**
	 * 
	 * This constructor is used if you want to just activate a Parent root in the
	 * same stage from which it was called. If you want to load the screen on a new
	 * stage, use the empty constructor.
	 * 
	 * @param main, the main node which is going to have the children cleared and
	 *              the new Parent root loaded.
	 */
	public SceneLoader(Pane main) {
		this.main = main;
	}

	/**
	 * 
	 * This constructor is used if you want to load the screen on a new stage.
	 * 
	 */
	public SceneLoader() {
	}

	/**
	 * 
	 * Adds the given fxml file as a screen to the SceneLoader class. Give a name to
	 * identify your screen and get it back if you need.
	 * 
	 * @param name, a given name to identify the screen. It is going to be used for
	 *              all other functions.
	 * @param fxml, the String path to the .fxml file. The class will take care of
	 *              load the fxml with the FXMLLoader class.
	 */
	public void addScreen(String name, String fxml) {
		screenMap.put(name, new SceneInformation(false, fxml));
	}

	/**
	 * Removes a screen named by the given name from this class.
	 * 
	 * @param name, the name of the screen that you want to remove.
	 */
	public boolean removeScreen(String name) {
		if (screenMap.containsKey(name)) {
			screenMap.remove(name);
			return true;
		}

		return false;
	}

	/**
	 * Activates the given screen, by its name, in the node given in the constructor
	 * of this class. If it was not constructed with a pane, it has no effect and
	 * return false.
	 * 
	 * @param name, a created name to identify the screen you want to load.
	 * 
	 * @return true, if the screen was successfully activated, false otherwise.
	 */
	public boolean activate(String name) {
		try {
			if (main == null) {
				return false;
			}

			if (screenMap.containsKey(name)) {
				if (!screenMap.get(name).isLoaded()) {
					screenMap.get(name).getLoader().load();
					screenMap.entrySet().stream().filter(entry -> entry.getKey().equals(name))
							.collect(Collectors.toList()).get(0).getValue().setLoaded(true);
				}
				main.getChildren().clear();
				main.getChildren().add(screenMap.get(name).getLoader().getRoot());
				return true;
			}

			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Used to load a scene on a new stage named by the given name in the @param
	 * name.
	 * 
	 * @return Stage, if everything was successfully made, null otherwise.
	 */
	public Stage loadUI(String name, boolean stageStyleTransparent, boolean stageMaximized) {
		try {
			if (screenMap.containsKey(name)) {
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource(screenMap.get(name).getFxmlURL()));
				Scene scene = new Scene(root);
				if (stageStyleTransparent) {
					stage.initStyle(StageStyle.TRANSPARENT);
				}
				stage.setMaximized(stageMaximized);
				stage.setScene(scene);

				screenMap.entrySet().stream().filter(e -> e.getKey().equals(name)).collect(Collectors.toList()).get(0)
						.getValue().setLoaded(true);

				stage.show();
				return stage;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the screen, named by the given name parameter, as a Parent root.
	 * 
	 */
	public Pane getScreen(String name) {
		return screenMap.get(name).getLoader().getRoot();
	}

	/**
	 * Used to anchor the @param child to its father. There's a static method in the
	 * AnchorPane class that sets the anchor @param value, but you need to do it in
	 * four lines, setting the top, the bottom, the left and the right anchor. With
	 * this, the value will be equal in all sides.
	 * 
	 */
	public static void anchor(Node child, double value) {
		AnchorPane.setBottomAnchor(child, value);
		AnchorPane.setLeftAnchor(child, value);
		AnchorPane.setRightAnchor(child, value);
		AnchorPane.setTopAnchor(child, value);
	}

	private final class SceneInformation {
		private boolean isLoaded;
		private FXMLLoader loader;
		private String fxmlURL;

		public SceneInformation(boolean isLoaded, String fxmlURL) {
			super();
			this.isLoaded = isLoaded;
			this.fxmlURL = fxmlURL;
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlURL));
			this.loader = loader;
		}

		public boolean isLoaded() {
			return isLoaded;
		}

		public void setLoaded(boolean isLoaded) {
			this.isLoaded = isLoaded;
		}

		public FXMLLoader getLoader() {
			return loader;
		}

		public void setLoader(FXMLLoader loader) {
			this.loader = loader;
		}

		public String getFxmlURL() {
			return fxmlURL;
		}

		public void setFxmlURL(String fxmlURL) {
			this.fxmlURL = fxmlURL;
		}

	}
}