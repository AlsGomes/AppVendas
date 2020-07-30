package control.support;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class AlertMaker {

	public static final int YES = 1;
	public static final int NO = 0;
	public static final int CANCEL = -1;

	public static int popUpSimpleYesNoAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		ButtonType buttonYes = new ButtonType("Sim", ButtonData.YES);
		ButtonType buttonNo = new ButtonType("Não", ButtonData.NO);

		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonYes) {
			return YES;
		} else {
			return NO;
		}
	}
}
