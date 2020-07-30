package view.venda;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Produto;

public class ItemVenda {

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

}
