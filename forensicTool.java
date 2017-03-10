
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JTextArea;
import java.util.*;
import java.util.concurrent.Callable;

import javax.swing.*;

public class forensicTool {

	public static JTextField keySok, keyExt, keyDecryptC;
	public static JMenu list, crypt, about, search;

	public static TextWindow searchField, filSearchField, cesarField, schifferField, egenField, bankField, telField,
			persField, cesarDekryptField;

	public forensicTool() {
		JMenuBar menuBar = new JMenuBar();
		JButton clearB = new JButton("Clear this window");
		JButton help = new JButton("Help me!");

		menuBar.add(list = new JMenu("Lista filer"));

		searchField = new TextWindow("Sok efter filer");
		filSearchField = new TextWindow("Ange filnamn");
		cesarField = new TextWindow("Sok efter filer");
		schifferField = new TextWindow("Sok efter filer");
		egenField = new TextWindow("Ange katalog");
		bankField = new TextWindow("Sok efter kontonummer");
		telField = new TextWindow("Sok efter telefonnummer");
		persField = new TextWindow("Sok efter personummer");
		cesarDekryptField = new TextWindow("Ange sokväg");

		JMenu sub1, sub2, subCesar, subSchiffer, subKrypt2, subKrypt3, subDekrypt, subDekrypt1, subDekrypt2, subSearch,
				subBanknr, subTelenr, subPersnr;

		JFrame frame = new JFrame("Judas Forensic Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		list.add(sub1 = new JMenu("Lista innehall i en sokväg:"));
		list.add(sub2 = new JMenu("Sok efter specifik fil"));
		sub1.add(searchField.window);
		sub2.add(keySok = new JTextField("Ange katalog"));
		sub2.add(filSearchField.window);

		menuBar.add(crypt = new JMenu("Kryptera / Dekryptera"));
		

		crypt.add(subCesar = new JMenu("Cesar kryptering"));

		subCesar.add(subDekrypt1 = new JMenu("Dekryptera"));
		subDekrypt1.add(keyDecryptC = new JTextField("Ange sokväg for key"));
		subDekrypt1.add(cesarDekryptField.window);
		subCesar.add(subKrypt3 = new JMenu("Kryptera"));
		subKrypt3.add(cesarField.window);

		menuBar.add(search = new JMenu("Sok specifikt"));
		search.add(subSearch = new JMenu("Ange filformat"));
		subSearch.add(keyExt = new JTextField("Ange filformat"));
		subSearch.add(egenField.window);
		search.add(subBanknr = new JMenu("Sok efter kontonummer."));
		subBanknr.add(bankField.window);
		search.add(subTelenr = new JMenu("Sok efter telefonnummer"));
		subTelenr.add(telField.window);
		search.add(subPersnr = new JMenu("Sok efter personnummer"));
		subPersnr.add(persField.window);

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(Box.createVerticalStrut(20));

		menuBar.add(clearB);
		menuBar.add(help);
		
		JTextArea textarea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textarea);

		clearB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textarea.setText("");
				// textfield.setText(null); //or use this
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    String helpString = ("Välkommen till Judas Forensiska verktyg !\n \n"
				    		+"<Lista filer>\n --Lista innehallet i sokväg-- är en funktion som listar alla mappar och filer i den specificerade katalogen. \n"
				    		+"<Lista filer>\n --Sok efter specifik fil-- Här kan du soka efter en fil med ett filnamn i den katalog du forväntar dig att hitta filen. \n"
				    		+ "<Kryptera/Dekryptera>\n -- Här kan du välja att med Cesar eller Schiffer kryptering skapa en krypterad kopia av en fil.\n"
				    		+ "<Sok Specifikt>\n -- Sok antingen efter olika filändelser (Pdf, txt, mp3) eller sok efter personuppgifter som telefonnummer, kontonummer\n personnummer"
				    		+ "i ett textdokument genom att specificera sokväg och filnamn."
				    		+ " ");
				        textarea.append("\n"+helpString);
				
			
			}});

		List<String> testList = new ArrayList<String>();

		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setSize(800, 700);
		frame.setLocationRelativeTo(menuBar);
		frame.add(scrollPane);
		frame.setVisible(true);

		setFunctions(testList, textarea);

		searchField.keyUpdate(searchField.window);
		filSearchField.keyUpdate(filSearchField.window);
		cesarField.keyUpdate(cesarField.window);
		schifferField.keyUpdate(schifferField.window);
		egenField.keyUpdate(egenField.window);
		bankField.keyUpdate(bankField.window);
		telField.keyUpdate(telField.window);
		persField.keyUpdate(persField.window);
		cesarDekryptField.keyUpdate(cesarDekryptField.window);

	}

	public static void setFunctions(List<String> testList, JTextArea textarea) {
		searchField.setCallable(new Callable<List<String>>() {
			public List<String> call() {
				Bibliotek.list(testList, searchField.window.getText(), 99);
				for (int i = 0; i < testList.size(); i++) {
					System.out.println(testList.get(i));
					textarea.append("\n");
					textarea.append(testList.get(i));
				}
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				textarea.append("New search");
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				testList.clear();
				return testList;
			}
		});

		filSearchField.setCallable(new Callable<List<String>>() {
			public List<String> call() {

				List<String> results = Bibliotek.search(testList, keySok.getText(), 99,
						filSearchField.window.getText());
				for (int i = 0; i < results.size(); i++) {
					System.out.println(results.get(i));
					textarea.append("\n");
					textarea.append(results.get(i));
				}
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				textarea.append("New search");
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				testList.clear();
				results.clear();
				return results;
			}
		});

		cesarField.setCallable(new Callable<String>() {
			public String call() {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(cesarField.window.getText()));
					List<String> newlines = new ArrayList<>();
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						newlines.add(line.replace(line, Bibliotek.CeasarCrypt(line)));
					}
					Files.write(Paths.get(
							Paths.get(cesarField.window.getText()).toAbsolutePath().getParent() + "\\resultText.txt"),
							newlines, StandardCharsets.UTF_8);
					Files.write(
							Paths.get(
									Paths.get(cesarField.window.getText()).toAbsolutePath().getParent() + "\\key.txt"),
							Bibliotek.cryptKey);
					textarea.append("Done encrypting" + cesarField.window.getText() + "\n");
				} catch (FileNotFoundException F) {
					textarea.append("File not found!\n");
				}  catch(IOException e){
					e.printStackTrace();
					
				}finally {
					try {
						if (br != null)
							br.close();
					} catch (IOException e) {

					}
				}

				return "Done!";
			}

		});
		cesarDekryptField.setCallable(new Callable<String>() {
			public String call() {
				BufferedReader br = null;
				BufferedReader br2 = null;
				List<String> newlines;
				try {
					br = new BufferedReader(new FileReader(cesarDekryptField.window.getText()));
					br2 = new BufferedReader(new FileReader(keyDecryptC.getText()));
					newlines = new ArrayList<>();
					String line2;
					int ind = 0;
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						if ((line2 = br2.readLine()) != null) {

							newlines.add(line.replace(line, Bibliotek.CeasarDecrypt(line, line2)));
						}
						System.out.println(newlines.get(ind));
						ind++;

					}
					for (int i = 0; i < newlines.size(); i++) {
						System.out.println(newlines.get(i));
					}
					Files.write(Paths.get(Paths.get(cesarDekryptField.window.getText()).toAbsolutePath().getParent()
							+ "\\decryptText.txt"), newlines, StandardCharsets.UTF_8);
				} catch (Exception e) {
					System.out.println("Shit went wrong");
					e.printStackTrace();
				} finally {
					try {
						if (br != null)
							br.close();
						if (br2 != null)
							br2.close();
					} catch (IOException e) {

					}
				}

				return "Done!";
			}
		});
		persField.setCallable(new Callable<List<String>>() {
			public List<String> call() throws IOException {

				List<String> results = Bibliotek.listPers(persField.window.getText());
						
				for (int i = 0; i < results.size(); i++) {
					System.out.println(results.get(i));
					textarea.append("\n");
					textarea.append(results.get(i));
				}
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				textarea.append("New search");
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				return results;
			}
		});
		bankField.setCallable(new Callable<List<String>>() {
			public List<String> call() throws IOException {

				List<String> results = Bibliotek.listKonto(bankField.window.getText());
						
				for (int i = 0; i < results.size(); i++) {
					System.out.println(results.get(i));
					textarea.append("\n");
					textarea.append(results.get(i));
				}
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				textarea.append("New search");
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				return results;
			}
		});
		telField.setCallable(new Callable<List<String>>() {
			public List<String> call() throws IOException {

				List<String> results = Bibliotek.listTelnr(telField.window.getText());
						
				for (int i = 0; i < results.size(); i++) {
					System.out.println(results.get(i));
					textarea.append("\n");
					textarea.append(results.get(i));
				}
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				textarea.append("New search");
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				return results;
			}
		});

		egenField.setCallable(new Callable<List<String>>() {
			public List<String> call() {

				List<String> results = Bibliotek.searchFileExtention(testList, egenField.window.getText(), 99,
						keyExt.getText());
				for (int i = 0; i < results.size(); i++) {
					System.out.println(results.get(i));
					textarea.append("\n");
					textarea.append(results.get(i));
				}
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				textarea.append("New search");
				textarea.append(
						"\n--------------------------------------------------------------------------------------------\n");
				return results;
			}
		});

	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				forensicTool GUI = new forensicTool();

			}
		});
	}

}