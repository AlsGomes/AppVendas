package view.main;

import java.time.Instant;

import dao.DAO;
import dao.DAOImpl;
import model.Funcionario;
import model.ItemVenda;
import model.Produto;
import model.Venda;

public class Main {

	public static void main(String[] args) {
		seedDatabase();
	}

	private static void seedDatabase() {
		Funcionario f1 = new Funcionario(null, "Alisson", "Vendedor", "123");
		Funcionario f2 = new Funcionario(null, "Cristina", "Vendedor", "456");
		Funcionario f3 = new Funcionario(null, "Sky", "Vendedor", "789");

		DAO<Funcionario> daoFuncionario = new DAOImpl<Funcionario>();
		daoFuncionario.persist(f1);
		daoFuncionario.persist(f2);
		daoFuncionario.persist(f3);

		Produto p1 = new Produto(null, "Livro 1",
				"Donec vel mollis mi, at dignissim nisl. Fusce at ex nisi. Ut at neque nunc."
						+ " Ut vitae pretium felis, at varius enim. Duis elementum, risus id scelerisque porta, "
						+ "leo nisi tempor felis, vel varius libero massa in velit. Pellentesque efficitur massa elit, "
						+ "eu suscipit quam varius semper. Donec mollis dignissim maximus. Maecenas iaculis molestie "
						+ "sollicitudin.",
				50.0, 10);

		Produto p2 = new Produto(null, "Livro 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Vivamus eu ornare neque. Donec elementum diam id arcu tempor,"
				+ " ut tempor libero pulvinar. Mauris nec commodo lectus. Aliquam erat volutpat. Nam aliquet magna et sem vulputate mattis. "
				+ "Ut in vulputate felis, nec cursus nisi. Aliquam consequat, felis a semper gravida, neque elit pharetra tortor, ac eleifend"
				+ " erat odio sit amet ex. Aenean velit nisl, accumsan quis consectetur et, bibendum non nibh. "
				+ "In hac habitasse platea dictumst. Etiam sollicitudin metus a bibendum laoreet.", 100.5, 5);

		Produto p3 = new Produto(null, "Livro 3",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus a ex porttitor,"
						+ " tristique sapien in, tincidunt magna. Mauris erat lectus, placerat tristique eros eu, vehicula "
						+ "condimentum sapien. Duis vitae neque aliquet neque interdum commodo vel a orci. Duis "
						+ "pulvinar, tellus nec eleifend vestibulum, risus dolor suscipit massa, tristique fermentum mauris lorem "
						+ "non turpis. Proin venenatis aliquam justo, quis iaculis magna porttitor vehicula. "
						+ "In consequat rhoncus elit, suscipit facilisis leo accumsan nec. Maecenas odio "
						+ "nunc, vestibulum eget lacus nec, finibus varius mauris.",
				150.0, 50);

		DAO<Produto> daoProduto = new DAOImpl<Produto>();
		daoProduto.persist(p1);
		daoProduto.persist(p2);
		daoProduto.persist(p3);

		Venda v1 = new Venda(null, Instant.parse("2019-06-20T19:53:07Z"), f1);
		Venda v2 = new Venda(null, Instant.parse("2020-08-31T20:24:35Z"), f1);
		Venda v3 = new Venda(null, Instant.parse("2020-07-15T15:44:03Z"), f2);
		Venda v4 = new Venda(null, Instant.parse("2020-11-08T21:55:12Z"), f3);

		DAO<Venda> daoVenda = new DAOImpl<Venda>();
		daoVenda.persist(v1);
		daoVenda.persist(v2);
		daoVenda.persist(v3);
		daoVenda.persist(v4);

		ItemVenda iv1 = new ItemVenda(p1, v1, 3, p1.getPreco(), 3 * p1.getPreco(), 0.0, 3 * p1.getPreco());
		ItemVenda iv2 = new ItemVenda(p2, v1, 2, p2.getPreco(), 2 * p2.getPreco(), 0.0, 2 * p2.getPreco());
		ItemVenda iv3 = new ItemVenda(p3, v3, 1, p3.getPreco(), 1 * p3.getPreco(), 10.0, (1 * p3.getPreco()) - 10.0);
		ItemVenda iv4 = new ItemVenda(p3, v2, 1, p3.getPreco(), 1 * p3.getPreco(), 5.0, (1 * p3.getPreco()) - 5.0);

		DAO<ItemVenda> daoItemVenda = new DAOImpl<ItemVenda>();
		daoItemVenda.persist(iv1);
		daoItemVenda.persist(iv2);
		daoItemVenda.persist(iv3);
		daoItemVenda.persist(iv4);

	}
}
