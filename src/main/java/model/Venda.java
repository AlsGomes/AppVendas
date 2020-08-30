package model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_vendas")
public class Venda implements Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	@OneToMany(mappedBy = "codigo.venda")
	private Set<ItemVenda> items = new HashSet<ItemVenda>();

	@ManyToOne(cascade = { CascadeType.MERGE })
	// @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable = false, name = "id_funcionario")
	private Funcionario funcionario;

	@Column(nullable = false)
	private double valorTotal;

	public Venda() {
	}

	public Venda(long codigo, Date dataHora, Set<ItemVenda> items, Funcionario funcionario, double valorTotal) {
		super();
		this.codigo = codigo;
		this.dataHora = dataHora;
		this.items = items;
		this.funcionario = funcionario;
		this.valorTotal = valorTotal;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Set<ItemVenda> getItems() {
		return items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codigo ^ (codigo >>> 32));
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
		Venda other = (Venda) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}

}
