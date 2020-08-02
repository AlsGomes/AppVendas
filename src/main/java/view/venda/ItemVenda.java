package view.venda;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Produto;

public class ItemVenda extends VBox {

	private final Produto produto;
	private final IntegerProperty qntd = new SimpleIntegerProperty();
	private final DoubleProperty precoUnitario = new SimpleDoubleProperty();
	private final DoubleProperty precoTotal = new SimpleDoubleProperty();
	private final DoubleProperty valorDesconto = new SimpleDoubleProperty();
	private final DoubleProperty precoCobrado = new SimpleDoubleProperty();

	public ItemVenda(Produto produto, int qntd, double precoUnitario, double precoTotal, double valorDesconto,
			double precoCobrado) {
		super();
		this.produto = produto;
		this.qntd.setValue(qntd);
		this.precoUnitario.setValue(precoUnitario);
		this.precoTotal.setValue(precoTotal);
		this.valorDesconto.setValue(valorDesconto);
		this.precoCobrado.setValue(precoCobrado);
		initListeners();
		configViewer();
	}

	public Produto getProduto() {
		return produto;
	}

	public IntegerProperty getQntdProperty() {
		return qntd;
	}

	public Integer getQntd() {
		return qntd.getValue();
	}

	public DoubleProperty getPrecoUnitarioProperty() {
		return precoUnitario;
	}

	public Double getPrecoUnitario() {
		return precoUnitario.getValue();
	}

	public DoubleProperty getPrecoTotalProperty() {
		return precoTotal;
	}

	public Double getPrecoTotal() {
		return precoTotal.getValue();
	}

	public DoubleProperty getValorDescontoProperty() {
		return valorDesconto;
	}

	public Double getValorDesconto() {
		return valorDesconto.getValue();
	}

	public DoubleProperty getPrecoCobradoProperty() {
		return precoCobrado;
	}

	public Double getPrecoCobrado() {
		return precoCobrado.getValue();
	}

	@Override
	public String toString() {
		return "Produto: " + produto.getNome() + "\n Qntd: " + qntd.getValue() + "\n Preço Cobrado: "
				+ precoCobrado.getValue();
	}

	private void initListeners() {
		getQntdProperty().addListener((observable, oldValue, newValue) -> {
			getPrecoTotalProperty().setValue((int) newValue * getPrecoUnitario());
			getPrecoCobradoProperty().setValue(getPrecoTotal() - getValorDesconto());
		});
	}

	private void configViewer() {
		HBox containerProduto = new HBox();
		Text titleProduto = new Text("Produto: ");
		Text valueProduto = new Text(produto.getNome());
		containerProduto.getChildren().addAll(titleProduto, valueProduto);

		HBox containerQntd = new HBox();
		Text titleQntd = new Text("Qntd: ");
		Text valueQntd = new Text(String.valueOf(getQntd()));
		valueQntd.textProperty().bind(getQntdProperty().asString());
		containerQntd.getChildren().addAll(titleQntd, valueQntd);

		HBox containerPrecoCobrado = new HBox();
		Text titlePrecoCobrado = new Text("Preço Cobrado: ");
		Text valuePrecoCobrado = new Text(String.format("%.2f", getPrecoCobrado()));
		valuePrecoCobrado.textProperty().bind(getPrecoCobradoProperty().asString());
		containerPrecoCobrado.getChildren().addAll(titlePrecoCobrado, valuePrecoCobrado);

		HBox buttons = new HBox();
		JFXButton aumentarQntd = new JFXButton("+");
		JFXButton diminuirQntd = new JFXButton("-");
		aumentarQntd.setOnAction(e -> {
			if (this.qntd.getValue() < this.produto.getQntd()) {
				this.qntd.setValue(this.qntd.getValue() + 1);
			}
		});
		diminuirQntd.setOnAction(e -> {
			if (this.qntd.getValue() > 1) {
				this.qntd.setValue(this.qntd.getValue() - 1);
			}
		});
		buttons.getChildren().addAll(aumentarQntd, diminuirQntd);

		getChildren().clear();
		getChildren().addAll(containerProduto, containerQntd, containerPrecoCobrado, buttons);
	}

}
