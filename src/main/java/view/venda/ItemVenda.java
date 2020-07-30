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
	private final SimpleIntegerProperty qntd = new SimpleIntegerProperty();
	private final IntegerProperty qntdProperty = new SimpleIntegerProperty();
	private final SimpleDoubleProperty precoUnitario = new SimpleDoubleProperty();
	private final DoubleProperty precoUnitarioProperty = new SimpleDoubleProperty();
	private final SimpleDoubleProperty precoTotal = new SimpleDoubleProperty();
	private final DoubleProperty precoTotalProeprty = new SimpleDoubleProperty();
	private final SimpleDoubleProperty valorDesconto = new SimpleDoubleProperty();
	private final DoubleProperty valorDescontoProperty = new SimpleDoubleProperty();
	private final SimpleDoubleProperty precoCobrado = new SimpleDoubleProperty();
	private final DoubleProperty precoCobradoProperty = new SimpleDoubleProperty();

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

	public SimpleIntegerProperty getQntd() {
		return qntd;
	}

	public IntegerProperty getQntdProperty() {
		return qntdProperty;
	}

	public SimpleDoubleProperty getPrecoUnitario() {
		return precoUnitario;
	}

	public DoubleProperty getPrecoUnitarioProperty() {
		return precoUnitarioProperty;
	}

	public SimpleDoubleProperty getPrecoTotal() {
		return precoTotal;
	}

	public DoubleProperty getPrecoTotalProeprty() {
		return precoTotalProeprty;
	}

	public SimpleDoubleProperty getValorDesconto() {
		return valorDesconto;
	}

	public DoubleProperty getValorDescontoProperty() {
		return valorDescontoProperty;
	}

	public SimpleDoubleProperty getPrecoCobrado() {
		return precoCobrado;
	}

	public DoubleProperty getPrecoCobradoProperty() {
		return precoCobradoProperty;
	}

	@Override
	public String toString() {
		return "Produto: " + produto.getNome() + "\n Qntd: " + qntd.getValue() + "\n Preço Cobrado: "
				+ precoCobrado.getValue();
	}

	private void initListeners() {
		this.qntdProperty.addListener((observable, oldValue, newValue) -> {
			System.out.println("Tá alterando");
			this.precoTotal.setValue((int) newValue * this.precoUnitario.getValue());
			this.precoCobrado.setValue(this.precoTotal.getValue() - this.getValorDesconto().getValue());
		});
	}

	private void configViewer() {
		HBox containerProduto = new HBox();
		Text titleProduto = new Text("Produto");		
		Text valueProduto = new Text(produto.getNome());		
		containerProduto.getChildren().addAll(titleProduto, valueProduto);

		HBox containerQntd = new HBox();
		Text titleQntd = new Text("Qntd");
		Text valueQntd = new Text(String.valueOf(this.getQntd().getValue()));
		
//		valueQntd.textProperty().bind(this.getQntdProperty().asString());
		
		containerQntd.getChildren().addAll(titleQntd, valueQntd);

		HBox containerPrecoCobrado = new HBox();
		Text titlePrecoCobrado = new Text("Preço Cobrado");
		Text valuePrecoCobrado = new Text(String.valueOf(this.getPrecoCobrado().getValue()));
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
