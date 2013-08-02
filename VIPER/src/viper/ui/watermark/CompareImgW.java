package viper.ui.watermark;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CompareImgW extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabelOriginalImg = null;
	private JLabel jLabelWatermarkedImg = null;
	private JLabel jLabelOrigImg = null;
	private JLabel jLabelWMImg = null;
	private JTextField jTextFieldS1 = null;
	private JLabel jLabelS1 = null;
	private JLabel jLabelS2 = null;
	private JTextField jTextFieldS2 = null;

	/**
	 * This is the default constructor
	 */
	public CompareImgW() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabelS2 = new JLabel();
		jLabelS2.setText("Search:");
		jLabelS2.setLocation(new Point(556, 110));
		jLabelS2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelS2.setSize(new Dimension(80, 50));
		jLabelS1 = new JLabel();
		jLabelS1.setText("Search:");
		jLabelS1.setSize(new Dimension(80, 50));
		jLabelS1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelS1.setLocation(new Point(60, 110));
		jLabelWMImg = new JLabel();
		jLabelWMImg.setText("JLabel");
		jLabelWMImg.setSize(new Dimension(450, 350));
		jLabelWMImg.setLocation(new Point(520, 200));
		jLabelOrigImg = new JLabel();
		jLabelOrigImg.setText("JLabel");
		jLabelOrigImg.setSize(new Dimension(450, 350));
		jLabelOrigImg.setLocation(new Point(30, 200));
		jLabelWatermarkedImg = new JLabel();
		jLabelWatermarkedImg.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
		jLabelWatermarkedImg.setSize(new Dimension(230, 26));
		jLabelWatermarkedImg.setLocation(new Point(650, 48));
		jLabelWatermarkedImg.setText("Watermarked Image");
		jLabelOriginalImg = new JLabel();
		jLabelOriginalImg.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
		jLabelOriginalImg.setLocation(new Point(150, 48));
		jLabelOriginalImg.setSize(new Dimension(230, 26));
		jLabelOriginalImg.setText("Original Image");
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
		this.add(jLabelOriginalImg, null);
		this.add(jLabelWatermarkedImg, null);
		this.add(jLabelOrigImg, null);
		this.add(jLabelWMImg, null);
		this.add(getJTextFieldS1(), null);
		this.add(jLabelS1, null);
		this.add(jLabelS2, null);
		this.add(getJTextFieldS2(), null);
	}

	/**
	 * This method initializes jTextFieldS1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldS1() {
		if (jTextFieldS1 == null) {
			jTextFieldS1 = new JTextField();
			jTextFieldS1.setSize(new Dimension(289, 50));
			jTextFieldS1.setLocation(new Point(170, 110));
			jTextFieldS1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
				}
			});
		}
		return jTextFieldS1;
	}

	/**
	 * This method initializes jTextFieldS2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldS2() {
		if (jTextFieldS2 == null) {
			jTextFieldS2 = new JTextField();
			jTextFieldS2.setSize(new Dimension(289, 50));
			jTextFieldS2.setLocation(new Point(660, 110));
			jTextFieldS2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jTextFieldS2;
	}

}
