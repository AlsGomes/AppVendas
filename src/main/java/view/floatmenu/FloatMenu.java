package view.floatmenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FloatMenu extends VBox {

	private final Map<String, List<? extends Button>> menuMap = new HashMap<String, List<? extends Button>>();

	public FloatMenu() {
		super();
		setDefaultConfig();
	}

	private void setDefaultConfig() {
		setPrefSize(150d, 500d);
	}

	public Map<String, List<? extends Button>> getMenuMap() {
		return menuMap;
	}

	public void refresh() {
		getChildren().clear();
		Accordion accordion = new Accordion();
		setVgrow(accordion, Priority.ALWAYS);

		menuMap.keySet().forEach(k -> {
			TitledPane titledPane = new TitledPane();
			titledPane.setText(k);
			titledPane.setAnimated(true);

			VBox vbox = new VBox();
			vbox.setSpacing(5d);
			vbox.setAlignment(Pos.TOP_CENTER);

			AnchorPane anchorPane = new AnchorPane();
			anchorPane.getChildren().add(vbox);
			anchorPane.setBottomAnchor(vbox, 0d);
			anchorPane.setLeftAnchor(vbox, 0d);
			anchorPane.setRightAnchor(vbox, 0d);
			anchorPane.setTopAnchor(vbox, 0d);

			if (!menuMap.get(k).isEmpty()) {
				vbox.getChildren().addAll(menuMap.get(k));
			}

			titledPane.setContent(anchorPane);
			accordion.getPanes().add(titledPane);			
		});
		
		getChildren().add(accordion);
	}

}
