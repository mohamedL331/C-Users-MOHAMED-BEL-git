
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class analyseur {

	private JFrame frmMonAnalyseur;
	JTextArea textArea;

	static JFileChooser file_chooser = new JFileChooser("C:\\Users\\sol\\Desktop");
	static FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers text", "compila");
	static ArrayList<String> mots = new ArrayList<String>();
	static ArrayList<String> lignes = new ArrayList<String>();
	static ArrayList<String> sortie_lexic = new ArrayList<String>();
	static String[] mot;

/*********************************************************************************************************************/
	public static void charger() throws FileNotFoundException {
		file_chooser.addChoosableFileFilter(filter);
		if(file_chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File file = file_chooser.getSelectedFile();
			Scanner sc_lignes = new Scanner(file);
			Scanner sc_mots = new Scanner(file);
			mots.clear();
			lignes.clear();
				while(sc_lignes.hasNextLine()){
					lignes.add(sc_lignes.nextLine());
				}
				while(sc_mots.hasNext()){
					mots.add(sc_mots.next());
					}

			sc_mots.close();
			sc_lignes.close();
			}
	}

/*********************************************************************************************************************/

	public boolean isNum(String chaine, int i) {
		char[] nombre = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		int j = 0;
		while (j < nombre.length) {
			if (chaine.charAt(i) == nombre[j]) {
				return true;
			}
			j++;
		}

		return false;
	}

	public String num(String chaine) {
		int i = 0;
		int token_pos = 0;
		boolean point_unique = true;
		while (i < chaine.length()) {
			if (isNum(chaine, i)) token_pos++;
			else if(point_unique & chaine.charAt(token_pos)==',') {
				token_pos++;
				point_unique = false;
			}
			i++;
		}

		if (token_pos == chaine.length() && !chaine.contains(",")) return "Nombre entier";
		else if (token_pos == chaine.length() && !point_unique) return "Nombre reel";
		return null;

	}

/*********************************************************************************************************************/

	public boolean isChar(String chaine, int i) {
		char[] alphabet = { 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i',
				'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T',
				't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z' };
		int k = 0;
		while (k < alphabet.length) {
			if (chaine.charAt(i) == alphabet[k]) {
				return true;
			}
			k++;
		}
		return false;

	}

	public String id(String chaine) {
		boolean verifier_Premier = false;
		boolean tiret_unique = true;
		int token_pos = 0;
		int i = 0;
		if (isChar(chaine, 0)) {
			token_pos++;
			verifier_Premier = true;
		}
		if (verifier_Premier == true && chaine.length() == 1)
			return "identificateur";

		else if (chaine.length() > 1) {
			i = 1;
			while (i < chaine.length()) {

				if (isChar(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (isNum(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chaine.charAt(i) == '_' && tiret_unique) {
					tiret_unique=true;
					token_pos++;
				}
				i++;
			}
			if (token_pos == chaine.length())
				return "Identificateur";
		}
		return null;
	}

/*********************************************************************************************************************/

	public String UL_reserve(String chaine) {
		
String[] mot_reserve = {"Start_Program", "Int_Number",":","i",",","j",",","Aft_5",";;","Give","i",":","23",";;", "Real_Number",":","Aft34_2",";;", "If",
		"--","i","<","j","--","Give","Aft_5",":","10",";;", "Else",
"Start", "Affect","i","to","j",";;","Give"," Af34_2",":","123.54",";;", "Finish", "ShowMes","",";;","ShowVal", "//.", "End_Program" };

String[] Affichage = {"Mot reserve debut du programme", " Mot reserve  declaration d'un entier","caractere reserv�","identificateur","caractere reserve","identificateur","caractere reserv�","identificateur","mot reserv�",
		"mot reserv�",
		
		"Mot reserv�","identificateur","caractere reserv�","valeur entiere",
"D�claration d'un reel","caractere reserv�","identificateur","mot reserv�",

" Mot reserve pour condition SI", "Mot reserve pour condition","identificateur","symbole inferieur",
"identificateur","mot reserv� pour une condition  "
,"mot reserv�","identificateur","caractere reserv�","valeur entiere","mot reserv�", "Mot reserve pour condition SINON", 
"Debut d'un sous programme","Affectation de valeur entre variable","identificateur","a","identificateur","mot reserv�",
"Mot reserve ","identificateur","mot reserv�", "identificateur","caractere reserv�","valeur reele","mot reserv�", "Fin d'un sous programme",
"Mot reserv� pour afficher un message a l'ecran","Affichage d'une valeur", "Example de commentaire", "Mot reserve Fin du programme" 
};
		int i = 0;
		while (i < mot_reserve.length) {
			if (chaine.equals(mot_reserve[i])) {
				return Affichage[i];
			}
			i++;
		}
		return null;
	}

/*********************************************************************************************************************/

	public String syntax(String chaine){
	
		if(chaine.equals("Start_Program")) return "D�but du programme";
		else if(chaine.equals("i:23")) return "Affectation d'une valeur a i";
		else if(chaine.equals("Else")) return "SINON";
		else if(chaine.equals("Start")) return "D�but d'un bloc";
		else if(chaine.equals("Finish")) return "Fin d'un bloc";
		else if(chaine.startsWith("//.")) return "un commentaire";
		else if(chaine.equals("End_Program ")) return "Fin du programme";
		else if(chaine.startsWith("ShowMes \" ") && chaine.endsWith(" \" //.")) return "Affichage d'un message � l'ecran";
		else if(chaine.contains(" ")) {
			mot = chaine.split(" ");
			int i=0, k=1;

				if(mot[i].equals("Int_Number")){
					i++;
					if(mot[i].equals(" "))
						i++;
						if(id(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								k++;
								if(id(mot[i]) != null) i++;
							}
							if(mot[i].equals("//.")) return "D�claration de "+k+" variables entiers";
						}
					

				}
				else if(mot[i].equals("Affect")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals(""))i++;
						if(num(mot[i]) == "Nombre entier") {
							i++;
							if(mot[i].equals("//.")) return "affectation dune valeur entiere � "+mot[i-2];
						}
						else if(num(mot[i]) == "Nombre reel"){
							i++;
							if(mot[i].equals("//.")) return "affectation dune valeur reel � "+mot[i-3];
						}

					
				}

				}
				
				else if(mot[i].equals("Real_Number")){
					i++;
					if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals("//.")) return "D�claration d'un reel";
						}


				
				
				else if(mot[i].equals("If")){
					i++;
					if(mot[i].equals("//.")){
						i++;
					if(id(mot[i]) != null){
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
						i++;
						if(id(mot[i]) != null){
							i++;
						if(mot[i].equals("//.")) return "condition"; 
							}}}}
				}
				
				
				
				else if(mot[i].equals("Give")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals("From")){
						i++;
						if(id(mot[i]) != null) {
							i++;
							if(mot[i].equals("//.")) return "affectation de "+mot[i-3]+" a "+mot[i-1];
						}

					}

				}

				}
				
				
				else if(mot[i].equals("ShowVal")){
					i++;
					if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals("//.")) return "Affichage d'une valeur "+mot[i-1];
						}
/*
				else if(mot[i].equals("ShowVal")){
					i++;
					if(mot[i].equals(" "))i++;
					if(mot[i].equals(g)){i++;s=1;}
						while(id(mot[i]) != null && s == 1){
							i++;
						if(mot[i].equals(g)){i++;s=0;}	
						}
							if(mot[i].equals("//.")) return "affichage d'un message ";
						}
		
*/
				

				
								}
		return "erreur de syntaxe";
	}

	
	
/*********************************************************************************************************************/


	public void lexicale(List<String> liste) {
		int i = 0;

		while (i < mots.size()) {
			if (UL_reserve(mots.get(i)) != null) {
				sortie_lexic.add(UL_reserve(mots.get(i)));
			} else if (id(mots.get(i)) != null) {
				sortie_lexic.add(id(mots.get(i)));
			} else if (num(mots.get(i)) != null) {
				sortie_lexic.add(num(mots.get(i)));
			}
			else sortie_lexic.add("Erreur");

			i++;
		}

	}

/*********************************************************************************************************************/
	
	public String semantique(String chaine){
		if(chaine.equals("Start_Program")) return "public static void main(String[] args) {";
		else if(chaine.equals("Else")) return "else";
		else if(chaine.equals("Start")) return "{";
		else if(chaine.equals("Finish")) return "}";
		else if(chaine.startsWith("//.")) return "/*ceci est un commentaire*/";
		else if(chaine.equals("End_program ")) return "}";
		else if(chaine.startsWith("ShowMes \" ") && chaine.endsWith(" \" //.")) return "System.out.println(\"Affichage d'un message � l'ecran\");";
		else if(chaine.contains(" ")) {
			mot = chaine.split(" ");
			int i=0;

				if(mot[i].equals("Int_Number")){
					i++;
					if(mot[i].equals(" "))
						i++;
						if(id(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								if(id(mot[i]) != null) i++;
							}
							if(mot[i].equals("//."))return "int"+" "+mot[i-7]+","+mot[i-5]+","+mot[i-3]+","+mot[i-1]+";";
						}
					

				}
				
				else if(mot[i].equals("Affect")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals(""))i++;
						if(num(mot[i]) == "Nombre entier") {
							i++;
							if(mot[i].equals("%.")) return mot[i-2]+"="+mot[i-1]+";";
						}
						else if(num(mot[i]) == "Nombre reel"){
							i++;
							if(mot[i].equals("//.")) return mot[i-3]+"="+mot[i-1]+";";
						}

					
				}

				}
				
				else if(mot[i].equals("Real_real")){
					i++;
					if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals("//.")) return "float "+mot[i-1]+";";
						}


				
				
				else if(mot[i].equals("If")){
					i++;
					if(mot[i].equals("--")){
						i++;
					if(id(mot[i]) != null){
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
						i++;
						if(id(mot[i]) != null){
							i++;
						if(mot[i].equals("--")) return "if"+"("+mot[i-3]+mot[i-2]+mot[i-1]+")"; 
							}}}}
				}
				
				
				
				else if(mot[i].equals("Give")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals("Affect")){
						i++;
						if(id(mot[i]) != null) {
							i++;
							if(mot[i].equals("//.")) return  mot[i-3]+"="+mot[i-1]+";";
						}

					}

				}

				}
				
				
				else if(mot[i].equals("ShowVal")){
					i++;
					if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals("//.")) return "System.out.println("+mot[i-1]+");";
						}

				

				
								}
		return "erreur symantique";
		
	}
	
	
	/*********************************************************************************************************************/
	

	 
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					analyseur window = new analyseur();
					window.frmMonAnalyseur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public analyseur() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMonAnalyseur = new JFrame();
		frmMonAnalyseur.setResizable(false);
		frmMonAnalyseur.setTitle("Analyseur lexicale,syntaxique et semantique cr�� par:BELHERAZ&BOUKOR");
		frmMonAnalyseur.getContentPane().setBackground(Color.BLACK);
		frmMonAnalyseur.getContentPane().setLayout(null);
		frmMonAnalyseur.setLocationRelativeTo(null);

		Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(5, 100, 245));
		panel.setBounds(10, 38, 218, 360);
		frmMonAnalyseur.getContentPane().add(panel);

				JButton btnNewButton = new JButton("Charger");
				btnNewButton.setCursor(cursor);
				btnNewButton.setBounds(10, 81, 195, 59);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							  textArea.setText("");
							charger();


							int i = 0;
							while (i < lignes.size()) {
								textArea.setText(textArea.getText()+lignes.get(i)+"\n");
								i++;}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
				panel.setLayout(null);
				btnNewButton.setForeground(Color.BLACK);
				btnNewButton.setBackground(UIManager.getColor("Button.foreground"));
				btnNewButton.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnNewButton);

				JButton btnAlexicale = new JButton("Analyse Lexicale");
				btnAlexicale.setCursor(cursor);
				btnAlexicale.setBounds(10, 151, 195, 59);
				btnAlexicale.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						lexicale(mots);
						int i = 0;
						while (i < mots.size()) {
							textArea.setText(textArea.getText()+mots.get(i) + " -:- " + sortie_lexic.get(i)+"\n");
							i++;}
					}
				});
				btnAlexicale.setForeground(Color.BLACK);
				btnAlexicale.setBackground(UIManager.getColor("Button.foreground"));
				btnAlexicale.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnAlexicale);

				JButton btnAsyntaxique = new JButton("Analyse Syntaxique");
				btnAsyntaxique.setCursor(cursor);
				btnAsyntaxique.setBounds(10, 221, 195, 59);
				btnAsyntaxique.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						int i = 0;
						while (i < lignes.size()) {
							textArea.setText(textArea.getText()+lignes.get(i) + " -:- " +syntax(lignes.get(i))+"\n");
							i++;}
					}
				});
				btnAsyntaxique.setForeground(Color.BLACK);
				btnAsyntaxique.setBackground(UIManager.getColor("Button.foreground"));
				btnAsyntaxique.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnAsyntaxique);

				JButton btnAsmantique = new JButton("Analyse S\u00E9mantique");
				btnAsmantique.setCursor(cursor);
				btnAsmantique.setBounds(10, 292, 195, 59);
				btnAsmantique.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						int i = 0;
						
						
						while (i < lignes.size()) {
							textArea.setText(textArea.getText()+lignes.get(i) + " -:- " +semantique(lignes.get(i))+"\n");
							
							i++;}
						
						i=0;
						/*
						try {
							 
						    // Create file
						    FileWriter fstream = new FileWriter("out.java");
						    BufferedWriter out = new BufferedWriter(fstream);
						    while (i < lignes.size()) { out.write(semantique(lignes.get(i))+"\n");
						    i++;
						      
						    						  }
						    
						    out.close();
						    
						    
					
						    						} 
						
						
						
						      catch (IOException x) {
						      System.err.println("Error: " + x.getMessage());
						                             } 
				            
						    
						*/
						
					}
				
				
				
				
				});
				btnAsmantique.setForeground(Color.BLACK);
				btnAsmantique.setBackground(UIManager.getColor("Button.foreground"));
				btnAsmantique.setFont(new Font("Roboto", Font.BOLD, 17));
				panel.add(btnAsmantique);

				JLabel lblNewLabel = new JLabel("Commandes :");
				lblNewLabel.setBounds(10, 11, 195, 59);
				lblNewLabel.setForeground(Color.BLACK);
				lblNewLabel.setFont(new Font("Roboto", Font.BOLD, 25));
				panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Sortie   :");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Book Antiqua", Font.BOLD, 20));
		lblNewLabel_1.setBounds(238, 0, 93, 48);
		frmMonAnalyseur.getContentPane().add(lblNewLabel_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(233, 38, 289, 360);
		frmMonAnalyseur.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 269, 338);
		panel_1.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("Perpetua", Font.BOLD, 16));
		frmMonAnalyseur.setBounds(100, 100, 535, 438);
		frmMonAnalyseur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
