package view.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DAO;
import dao.DAOImpl;
import model.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main {

	public static void main(String[] args) {
		verDetalhesVenda();
	}

	private static void removerFuncionario() {
		DAO<Funcionario> dao = new DAOImpl<Funcionario>();
		dao.remove(Funcionario.class, 2);
	}

	private static void removerVenda() {
		DAO<Venda> dao = new DAOImpl<Venda>();
		dao.remove(Venda.class, 6);
	}

	private static void efetuarVenda() {
		DAO<Funcionario> daoFuncionario = new DAOImpl<Funcionario>();
		Funcionario funcionario = daoFuncionario.get(2L, Funcionario.class, false);

		DAO<Produto> daoProduto = new DAOImpl<Produto>();
		List<Produto> produtos = new ArrayList<Produto>();
		Produto produto1 = daoProduto.get(1L, Produto.class, false);
		Produto produto2 = daoProduto.get(2L, Produto.class, false);
		produtos.add(produto1);
		produtos.add(produto2);

		DAO<Venda> daoVenda = new DAOImpl<Venda>();
		Venda venda = new Venda();
		venda.setFuncionario(funcionario);
		venda.setProdutos(produtos);

		venda.setDataHora(new Date());

		daoVenda.persist(venda);
	}

	private static void verDetalhesVenda() {
		DAO<Venda> dao = new DAOImpl<Venda>();
		Venda venda = dao.get(7, Venda.class, true);

		System.out.println(venda.getCodigo());
		System.out.println(venda.getFuncionario().getNome());
		System.out.println(venda.getDataHora());
		venda.getProdutos().forEach(t -> {
			System.out.println(t.getNome());
		});

	}
}
