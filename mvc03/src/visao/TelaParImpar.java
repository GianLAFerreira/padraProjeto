package visao;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import controller.ParImparController;
import modelo.Mao;
import observer.Observer;

public class TelaParImpar extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JButton jbNovo;
	private CardLayout cardLayout1;
	private CardLayout cardLayout2;
	private JPanel jpMaosPC;
	private JPanel jpMinhaMao;
	private JRadioButton jrbPar;
	private JRadioButton jrbImpar;
	private JButton jbPosterior;
	private JButton jbJogar;
	private JList<Mao> listSorteios;
	private MaoModel maoModel;
	private ParImparController parImparController;

	@Override
	public void novoJogo() {
		maoModel.repaint();
		habilitarComponentes(false);
	}

	@Override
	public void proximaMao() {
		cardLayout2.next(jpMinhaMao);
	}

	@Override
	public void atualizar(int maoPC) {
		maoModel.repaint();
		cardLayout1.show(jpMaosPC, maoPC + "");
	}

	@Override
	public void notificarVitoria() {
		JOptionPane.showMessageDialog(null, "Parab\u00e9ns, voc\u00ea venceu");
		habilitarComponentes(true);
	}

	@Override
	public void notificarDerrota() {
		JOptionPane.showMessageDialog(null, "Naba, o PC venceu");
		habilitarComponentes(true);
	}

	class MaoModel extends AbstractListModel<Mao> {

		@Override
		public Mao getElementAt(int index) {
			return parImparController.getMao(index);
		}

		@Override
		public int getSize() {
			return parImparController.getSize();
		}
		
		public void repaint() {
			fireContentsChanged(this, 0, getSize());
		}
		
	}


	public TelaParImpar() {
		setTitle("Prova 1 55PPR");

		parImparController = new ParImparController();
		parImparController.addObserver(this);


		setSize(300, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponents();
		addEventos();
	}
	
	private void initComponents() {
	    JPanel jp0 = new JPanel();
		jp0.setLayout(new BoxLayout(jp0, BoxLayout.Y_AXIS));
	    
		jbNovo = new JButton("Novo");
		jbNovo.setAlignmentX(Component.CENTER_ALIGNMENT);
		jp0.add(jbNovo);
		
		JPanel jp2 = new JPanel();
		
		ButtonGroup bg = new ButtonGroup();
		
		jrbPar = new JRadioButton("Par");
		jrbPar.setEnabled(false);
		jrbPar.setSelected(true);
		jrbImpar = new JRadioButton("Impar");
		jrbImpar.setEnabled(false);
		
		bg.add(jrbPar);
		bg.add(jrbImpar);
		
		jp2.add(jrbPar);
		jp2.add(jrbImpar);

		jp0.add(jp2);
		
		JPanel meio = new JPanel();
		jpMaosPC = new JPanel(); cardLayout1 = new CardLayout();

		Icon icon = new ImageIcon("imagens/branco.png");
		JLabel jl = new JLabel(icon);
		cardLayout1.addLayoutComponent(jl, "branco");
		jpMaosPC.add(jl);
		
		JPanel jp4 = new JPanel(new BorderLayout());
		jp4.add(new JLabel("PC"), BorderLayout.NORTH);
		jp4.add(criarUmaMao(jpMaosPC, cardLayout1), BorderLayout.CENTER);
		
		meio.add(jp4);
		
		JPanel jp5 = new JPanel(new BorderLayout());
		jp5.add(new JLabel("Eu"), BorderLayout.NORTH);
		jpMinhaMao = new JPanel(); cardLayout2 = new CardLayout();
		jp5.add(criarUmaMao(jpMinhaMao, cardLayout2), BorderLayout.CENTER);
		
		meio.add(jp5);

		jp0.add(meio);
		
		JPanel jp3 = new JPanel();
		jp3.add(new JLabel("A sua m\u00E3o pretende jogar: "));
		jbPosterior = new JButton(">>"); jbPosterior.setEnabled(false);
		jp3.add(jbPosterior);
		
		jp0.add(jp3);
		
		JLabel jlx = new JLabel("Vit\u00f3ria ao primeiro que vencer 3 partidas");
		jlx.setAlignmentX(Component.CENTER_ALIGNMENT);
		jp0.add(jlx);

		jbJogar = new JButton("Jogar");
		jbJogar.setEnabled(false);
		jbJogar.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		jp0.add(jbJogar);
		
		add(jp0, BorderLayout.NORTH);

		maoModel = new MaoModel();
		listSorteios = new JList<Mao>(maoModel);
		
		add(new JScrollPane(this.listSorteios), BorderLayout.CENTER);
		 
	}
	
	private JPanel criarUmaMao(JPanel umaMao, CardLayout cardLayout) {
		JPanel nav = new JPanel();
		nav.setLayout(new BorderLayout());
		nav.setBorder(BorderFactory.createLineBorder(Color.black));

		for (int i=0; i<=5; i++) {
			Icon icon = new ImageIcon("imagens/" + i + ".png");
			JLabel jl = new JLabel(icon);
			cardLayout.addLayoutComponent(jl, i+"");
			umaMao.add(jl);	
		}

		umaMao.setLayout(cardLayout);
		nav.add(umaMao, BorderLayout.CENTER);
		return nav;
	}

	private void addEventos() {
		jbNovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parImparController.novo();
				novoJogo();
			}
		});
		
		jbPosterior.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parImparController.posterior();
				proximaMao();
			}
			
		});
		
		jbJogar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parImparController.jogar(jrbPar.isSelected());
			}
		});
		
	}
	
	private void habilitarComponentes(boolean valor) {
		jbNovo.setEnabled(valor);
		jrbPar.setEnabled(!valor);
		jrbImpar.setEnabled(!valor);
		
		jbPosterior.setEnabled(!valor);
		jbJogar.setEnabled(!valor);
	}

	public static void main(String[] args) {
		TelaParImpar j = new TelaParImpar();
		j.setVisible(true);
	}

}
