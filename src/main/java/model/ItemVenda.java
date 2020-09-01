package model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import model.pk.ItemVendaPK;

@Entity
@Table(name = "tbl_vendas_x_produtos")
public class ItemVenda implements Model, Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemVendaPK codigo = new ItemVendaPK();

	private Integer qntd;
	private Double precoUnitario;
	private Double precoTotal;
	private Double valorDesconto;
	private Double precoCobrado;

	public ItemVenda() {
	}

	public ItemVenda(Produto produto, Venda venda, Integer qntd, Double precoUnitario, Double precoTotal,
			Double valorDesconto, Double precoCobrado) {
		this.qntd = qntd;
		this.precoUnitario = precoUnitario;
		this.precoTotal = precoTotal;
		this.valorDesconto = valorDesconto;
		this.precoCobrado = precoCobrado;
		this.codigo.setProduto(produto);
		this.codigo.setVenda(venda);
	}

	public Integer getQntd() {
		return qntd;
	}

	public void setQntd(Integer qntd) {
		this.qntd = qntd;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Double getPrecoCobrado() {
		return precoCobrado;
	}

	public void setPrecoCobrado(Double precoCobrado) {
		this.precoCobrado = precoCobrado;
	}

	public Produto getProduto() {
		return this.codigo.getProduto();
	}

	public void setProduto(Produto produto) {
		this.codigo.setProduto(produto);
	}

	public Venda getVenda() {
		return this.codigo.getVenda();
	}

	public void setVenda(Venda venda) {
		this.codigo.setVenda(venda);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {	
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVenda other = (ItemVenda) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
