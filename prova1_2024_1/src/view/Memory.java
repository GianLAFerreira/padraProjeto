package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.CartaController;
import observer.Observer;

/**
 * The Memory class creates the GUI for the MemoryUI House application.
 */

// Adaptado de https://github.com/moon-tea/Memory/

public class Memory extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	// constantes: nao tem problema mante-las publicas
	public static final int ROWS = 2;
	public static final int COLUMNS = 3;

	private MemoryButton[][] memButtons = new MemoryButton[ROWS][COLUMNS];
	private JLabel[][] imgLabel = new JLabel[ROWS][COLUMNS];
	private JPanel[][] memPanel = new JPanel[ROWS][COLUMNS];

	private CartaController cartaController;

	public Memory() {
		cartaController = new CartaController();
		cartaController.addObserver(this);

		cartaController.setVersoCarta(new ImageIcon("imagens/Card0.jpg"));

		cartaController.embaralhar();

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				buildMemPanel(i, j);
				add(memPanel[i][j]);
			}
		}

		setTitle("Prova 1 - 2024/1");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(ROWS, COLUMNS));

		// Empacotar (organizar) a janela
		pack();

		setLocationRelativeTo(null);
		setResizable(false);

	}

	// criar o visual do jogo de memoria
	private void buildMemPanel(int _i, int _j) {
		memPanel[_i][_j] = new JPanel();

		memPanel[_i][_j].setLayout(new GridLayout(2, 1));

		memButtons[_i][_j] = new MemoryButton(_i, _j);

		memButtons[_i][_j].addActionListener(new FlipListener());
		memButtons[_i][_j].setText("Virar!");

		imgLabel[_i][_j] = new JLabel(cartaController.getVersoCarta());

		memPanel[_i][_j].setBorder(BorderFactory.createTitledBorder(" "));

		memPanel[_i][_j].add(imgLabel[_i][_j]);
		memPanel[_i][_j].add(memButtons[_i][_j]);
	}

	@Override
	public void exibirImagemCarta(int index, int r, int c) {
		ImageIcon tempImg = new ImageIcon("imagens/Card" + cartaController.getCards()[index].getImgNum() + ".jpg");
		imgLabel[r][c].setIcon(tempImg);
	}

	@Override
	public void tratarPrimeiraCarta(int index, int r, int c) {
		cartaController.alterarVariaveis(cartaController.getCards()[index].getImgNum(), r, c);
		memButtons[cartaController.getHoldCardRow()][cartaController.getHoldCardColumn()].setEnabled(false);
	}

	@Override
	public void tratarSegundaCarta(int index, int r, int c) {
		if (cartasSaoIguais(index)) {
			processarCartasIguais(index, r, c);
		} else {
			processarCartasDiferentes(r, c);
		}
		cartaController.setFirst(true);
	}

	@Override
	public void exibirMensagemVitoria() {
		JOptionPane.showMessageDialog(null, "Você venceu!");
		dispose();
	}

	@Override
	public void exibirMensagemParEncontrado() {
		JOptionPane.showMessageDialog(null, "Você encontrou um par!");
	}

	@Override
	public void exibirMensagemCartasDiferentes() {
		JOptionPane.showMessageDialog(null, "Gah! Erradas!");
	}

	public void processarCartasDiferentes(int r, int c) {
		exibirMensagemCartasDiferentes();
		memButtons[cartaController.getHoldCardRow()][cartaController.getHoldCardColumn()].setEnabled(true);
		ImageIcon tempImg = cartaController.getVersoCarta();
		imgLabel[r][c].setIcon(tempImg);
		imgLabel[cartaController.getHoldCardRow()][cartaController.getHoldCardColumn()].setIcon(tempImg);
	}

	private boolean cartasSaoIguais(int index) {
		return cartaController.getCompareCards() == cartaController.getCards()[index].getImgNum();
	}

	private void processarCartasIguais(int index, int r, int c) {
		cartaController.modificaContador();
		if (cartaController.getWin() == COLUMNS) {
			exibirMensagemVitoria();
		} else {
			exibirMensagemParEncontrado();
			parEncontrado(index, r, c);
		}
	}

	private void parEncontrado(int index, int r, int c) {
		cartaController.getCards()[(cartaController.getHoldCardRow() * COLUMNS) + cartaController.getHoldCardColumn()].setCorrect(true);
		cartaController.getCards()[index].setCorrect(true);
		memButtons[r][c].setEnabled(false);
	}

	private class FlipListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MemoryButton clicked = (MemoryButton) e.getSource();
			int r = clicked.getRow();
			int c = clicked.getColumn();

			int index = (r * COLUMNS) + c ;

			// Exibir imagem da carta
			exibirImagemCarta(index,r, c);

			// guarda as variaveis da primeira carta a ser virada
			if (cartaController.isFirst()) {
				tratarPrimeiraCarta(index, r, c);
			} else {
				tratarSegundaCarta(index, r, c);
			}
		}
	}

	public class MemoryButton extends JButton {

		private static final long serialVersionUID = 1L;

		private int row;
		private int column;

		public MemoryButton(int _i, int _j) {
			this.row = _i;
			this.column = _j;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
	}

	public static void main(String[] args) {
		Memory m = new Memory();
		m.setVisible(true);
	}
}