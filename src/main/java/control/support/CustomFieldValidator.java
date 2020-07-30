package control.support;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jfoenix.validation.base.ValidatorBase;

import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;

public class CustomFieldValidator extends ValidatorBase {

	public static final int REQUIRED_FIELD_VALIDATOR = 0;
	public static final int DOUBLE_VALIDATOR = 1;
	public static final int INTEGER_VALIDATOR = 2;

	private final int validatorType[];
	private boolean isAllowedNumbersLowerThanZero;
	private int maxCharacterLength;

	public CustomFieldValidator(int validatorType[]) {
		super();
		this.validatorType = validatorType;
		this.isAllowedNumbersLowerThanZero = true;
		this.maxCharacterLength = 255;
	}

	public CustomFieldValidator(int validatorType[], boolean isAllowedNumbersLowerThanZero) {
		super();
		this.validatorType = validatorType;
		this.isAllowedNumbersLowerThanZero = isAllowedNumbersLowerThanZero;
		this.maxCharacterLength = 255;
	}

	public CustomFieldValidator(int validatorType[], int maxCharacterLength) {
		super();
		this.validatorType = validatorType;
		this.isAllowedNumbersLowerThanZero = true;
		this.maxCharacterLength = (maxCharacterLength > 0 ? maxCharacterLength : 255);
	}

	@Override
	protected void eval() {
		List<Boolean> errors = new ArrayList<Boolean>();

		for (int validator : validatorType) {
			switch (validator) {

			case REQUIRED_FIELD_VALIDATOR:
				if (srcControl.get() instanceof TextInputControl) {
					evalRequiredFieldTextInputField();
				}
				if (srcControl.get() instanceof ComboBoxBase) {
					evalRequiredFieldComboBoxField();
				}
				errors.add(hasErrors.get());
				break;

			case INTEGER_VALIDATOR:
				setMessage(isAllowedNumbersLowerThanZero ? "Somente números inteiros"
						: "Somente números inteiros maiores que zero");
				if (srcControl.get() instanceof TextInputControl) {
					evalIntegerTextInputField();
				}
				errors.add(hasErrors.get());
				break;

			case DOUBLE_VALIDATOR:
				setMessage(isAllowedNumbersLowerThanZero ? "Somente números reais"
						: "Somente números reais maiores que zero");
				if (srcControl.get() instanceof TextInputControl) {
					evalDoubleTextInputField();
				}
				errors.add(hasErrors.get());
				break;

			default:
				break;
			}
		}

		List<Boolean> errorsFound = errors.stream().filter(e -> e == true).collect(Collectors.toList());
		if (!errorsFound.isEmpty()) {
			hasErrors.set(true);
		}
	}

	private void evalRequiredFieldTextInputField() {
		TextInputControl textField = (TextInputControl) srcControl.get();
		if (textField.getText() == null || textField.getText().trim().isEmpty()
				|| textField.getText().trim().length() > maxCharacterLength) {
			if (textField.getText().trim().length() > maxCharacterLength) {
				setMessage(String.format("Texto muito longo. Número máximo de caracteres %s", maxCharacterLength));
			} else {
				setMessage("Campo obrigatório");
			}
			hasErrors.set(true);
		} else {
			hasErrors.set(false);
		}
	}

	private void evalRequiredFieldComboBoxField() {
		ComboBoxBase comboField = (ComboBoxBase) srcControl.get();
		Object value = comboField.getValue();
		hasErrors.set(value == null || value.toString().trim().isEmpty()
				|| value.toString().trim().length() > maxCharacterLength);
		if (value.toString().trim().length() > maxCharacterLength) {
			setMessage(String.format("Texto muito longo. Número máximo de caracteres %s", maxCharacterLength));
		} else {
			setMessage("Campo obrigatório");
		}
	}

	private void evalIntegerTextInputField() {
		TextInputControl textField = (TextInputControl) srcControl.get();
		String text = textField.getText();
		try {
			if (!text.isEmpty()) {
				int value = Integer.parseInt(text);
				if (isAllowedNumbersLowerThanZero) {
					hasErrors.set(false);
				} else {
					if (value > 0) {
						hasErrors.set(false);
					} else {
						hasErrors.set(true);
					}
				}
			}
		} catch (Exception e) {
			hasErrors.set(true);
		}
	}

	private void evalDoubleTextInputField() {
		TextInputControl textField = (TextInputControl) srcControl.get();
		try {
			double value = Double.parseDouble(textField.getText());
			if (isAllowedNumbersLowerThanZero) {
				hasErrors.set(false);
			} else {
				if (value > 0d) {
					hasErrors.set(false);
				} else {
					hasErrors.set(true);
				}
			}
		} catch (Exception e) {
			hasErrors.set(true);
		}
	}

	/**
	 * Accepts double/float values using dot(.).
	 */
	public static boolean isNumber(String value, boolean acceptDot, boolean acceptNullOrEmpty) {
		if (value != null && !value.isEmpty()) {
			String ultimaLetra = value.substring(value.length() - 1);
			if (acceptDot) {
				if (!ultimaLetra.matches("[0-9]") && !ultimaLetra.equals(".")) {
					return false;
				} else {
					return true;
				}
			} else {
				if (!ultimaLetra.matches("[0-9]")) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			return acceptNullOrEmpty;
		}
	}

	public static <T> boolean validate(CustomValidation<T> customValidation, T object) {
		return customValidation.validate(object);
	}
}
