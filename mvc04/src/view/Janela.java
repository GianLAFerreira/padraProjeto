package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import controller.ProdutoController;
import observer.Observer;

@SuppressWarnings("serial")
public class Janela extends JFrame implements Observer {

	private JButton jbNovo;

	private CardLayout cardLayout;

	private JButton jbAnterior;

	private JButton jbPosterior;

	private JButton jbAdd;

	private JButton jbConcluir;

	private JPanel jpProdutos;

	private JTable jtItens;

	private ProdutoController produtoController;


	private ItensTableModel itModel;

	@Override
	public void montarNovoProduto() {
		JPanel jp1 = new JPanel();
		jbNovo = new JButton("Novo");

		jp1.add(jbNovo);
		add(jp1, BorderLayout.NORTH);
	}

	@Override
	public void montarListagemProduto() {
		JPanel nav = new JPanel();
		nav.setLayout(new BorderLayout());
		nav.setBorder(BorderFactory.createLineBorder(Color.black));

		jpProdutos = new JPanel();
		for(int i =0; i < produtoController.getQuantosProduto(); i += 1) {
			Icon icon = new ImageIcon("imagens/" + produtoController.getNomeProduto(i) + ".png");
			JLabel jl = new JLabel(icon);
			jpProdutos.add(jl);
		}

		cardLayout = new CardLayout();
		jpProdutos.setLayout(cardLayout);
		jbAnterior = new JButton("<<");
		jbPosterior = new JButton(">>");

		nav.add(jpProdutos, BorderLayout.CENTER);
		JPanel navBotoes = new JPanel();
		navBotoes.add(jbAnterior);
		navBotoes.add(jbPosterior);
		nav.add(navBotoes, BorderLayout.SOUTH);

		add(nav, BorderLayout.WEST);


	}

	@Override
	public void montarGridProdutos() {

		JPanel jp2 = new JPanel();
		jp2.setLayout(new BorderLayout());
		// criar a JTable
		jtItens = new JTable();
		itModel = new ItensTableModel();
		jtItens.setModel(itModel);
		jtItens.setDefaultRenderer(Number.class, new ItensTableRenderer());

		jp2.add(new JScrollPane(jtItens), BorderLayout.CENTER);

		JPanel jpBotoes = new JPanel();

		jbAdd = new JButton("Add ao pedido");
		jbConcluir = new JButton("Concluir o pedido");

		jpBotoes.add(jbAdd);
		jpBotoes.add(jbConcluir);

		jp2.add(jpBotoes, BorderLayout.SOUTH);

		add(jp2, BorderLayout.CENTER);
	}

	class ItensTableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return String.class;
			} else {
				return Number.class;
			}
		}
		
		@Override
		public int getRowCount() {
			return produtoController.getQuantosPedidos() +1;
		}

		@Override
		public Object getValueAt(int rowIndex, int colIndex) {
			if (rowIndex == produtoController.getQuantosPedidos()) {
				if (colIndex == 2) {

					return produtoController.getTotalPedido();

				} else {
					return null;
				}
			} else {
				return produtoController.getValorPedido(rowIndex, colIndex);
			}
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Produto";
			case 1:
				return "Qtdade";
			default:
				return "Total";
			}
		}

	}

	class ItensTableRenderer extends DefaultTableCellRenderer {
		
		public ItensTableRenderer() {
			setHorizontalAlignment(JLabel.RIGHT);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int col) {

			// Cells are by default rendered as a JLabel.
			JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

			if (row == produtoController.getQuantosPedidos()) {
				l.setFont(new Font(l.getFont().getFontName(), Font.BOLD, l.getFont().getSize()));
			}
			return l;

		}
	}

	public Janela() {
		setTitle("Prova 1 55PPR");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		produtoController = new ProdutoController();
		produtoController.addObserver(this);


		initComponents();
		addEventos();
		
		habilitarComponentes(false);

	}

	private void habilitarComponentes(boolean valor) {
		
		jbNovo.setEnabled(!valor);
		jbAnterior.setEnabled(valor);
		jbPosterior.setEnabled(valor);
		jbAdd.setEnabled(valor);
		jbConcluir.setEnabled(valor);
		jtItens.setEnabled(valor);
		
	}

	private void initComponents() {

		montarNovoProduto();
		montarListagemProduto();
		montarGridProdutos();
	}

	private void addEventos() {
		jbNovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				habilitarComponentes(true);
			}
		});

		jbAnterior.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.previous(jpProdutos);

				produtoController.criarProdutoAnterior();
			}
		});

		jbPosterior.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.next(jpProdutos);
				produtoController.criarProdutoPosterior();
			}
		});

		jbAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				produtoController.adicionarProduto();
				itModel.fireTableDataChanged();
			}
		});

		jbConcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			produtoController.concluirPedido();
				itModel.fireTableDataChanged();
				cardLayout.first(jpProdutos);
				habilitarComponentes(false);
			}
		});
	}

	public static void main(String[] args) {
		Janela j = new Janela();
		j.setVisible(true);
	}

}
