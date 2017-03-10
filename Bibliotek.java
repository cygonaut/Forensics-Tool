import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bibliotek {
	public static Random rand;
	public static List<String> cryptKey = new ArrayList<String>();


	public static void list(List<String> str, String dir, int depth) {
		try {
			listFiles(Paths.get(dir), depth, 0, str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < str.size(); i++) {
			System.out.println(str.get(i));
		}
	}

	public static List<String> search(List<String> str, String dir, int depth, String key) {
		List<String> results = new ArrayList<String>();
		try {
			str = listFiles(Paths.get(dir), depth, 0, str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < str.size(); i++) {
			int testIndex = 0;
			for (int j = 0; j <= str.get(i).length(); j++) {
				if (str.get(i).substring(testIndex, j).toLowerCase().equals(key.toLowerCase())) {
					results.add(str.get(i));
				}
				if (j == str.get(i).length()) {
					testIndex++;
					j = testIndex;
				}
			}
		}
		return results;
	}

	public static List<String> searchFileExtention(List<String> str, String dir, int depth, String extention) {
		List<String> results = new ArrayList<String>();
		try {
			str = listFiles(Paths.get(dir), depth, 0, str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < str.size(); i++) {
			if (str.get(i).lastIndexOf(".") != -1 && str.get(i).substring(str.get(i).lastIndexOf(".") + 1).toLowerCase()
					.equals(extention.toLowerCase())) {
				results.add(str.get(i));
			}
		}
		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i));
		}
		return results;

	}

	public static List<String> listFiles(Path path, int maxDepth, int currentDepth, List<String> list)
			throws IOException {
		if (currentDepth < maxDepth) {
			try {
				DirectoryStream<Path> stream = Files.newDirectoryStream(path);
				for (Path i : stream) {
					if (Files.isDirectory(i)) {
						try {
							currentDepth++;
							list.add("-Dir  |  " + i.toAbsolutePath().toString());
							listFiles(i, maxDepth, currentDepth, list);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (Files.isRegularFile(i)) {
						list.add("-File |  " + i.toAbsolutePath().toString());
					}
				}
			} catch (IOException e) {
			}
		}
		return list;
	}


	public static String CeasarCrypt(String input) {
		Random rand = new Random();
		System.out.println("Input to encrypt");
		String encryptStr = "";
		String keyStr = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.substring(i, i + 1).contains("�") || input.substring(i, i + 1).contains("�")
					|| input.substring(i, i + 1).contains("�") || input.substring(i, i + 1).contains("�")
					|| input.substring(i, i + 1).contains("�") || input.substring(i, i + 1).contains("�")) {
				encryptStr += input.substring(i,i+1);
				keyStr +="0";

			} else if (input.substring(i, i + 1).contains(" ")) {
				encryptStr += " ";
				keyStr += " ";
			} else {
				keyStr += rand.nextInt(10);
				encryptStr += encrypt(input.charAt(i), Integer.parseInt(keyStr.substring(keyStr.length() - 1)));
			}

			System.out.println(keyStr);
			System.out.println(encryptStr);

		}
		cryptKey.add(keyStr);
		return encryptStr;
	}
	public static String CeasarDecrypt(String input, String key) {
		String decryptString = "";
		for (int i = 0; i < input.length(); i++) {
			try {
				decryptString += decrypt(input.charAt(i), Integer.parseInt(Character.toString(key.charAt(i))));
			} catch (NumberFormatException e) {
				System.out.println("Shit went wronger");
				decryptString += " ";
			}
		}

		return decryptString;

	}

	public static char encrypt(char ch, int key) {
		char newCh = (char) (ch + key);
		return newCh;
	}

	public static char decrypt(char ch, int key) {
		char newCh = (char) (ch - key);
		return newCh;
	}
	public static List<String> listPers (String path)
			throws IOException
			{
				
				List<String> list = new ArrayList<String>();
				String line;
				
				String regPers = "\\b(((20)((0[0-9])|(1[0-1])))|(([1][^0-8])?\\d{2}))((0[1-9])|1[0-2])((0[1-9])|(2[0-9])|(3[01]))[-+]?\\d{4}[,.]?\\b";
				Scanner fileScan = new Scanner(new File(path));
				Pattern p = Pattern.compile(regPers);
				Matcher m;
				
				while ((fileScan.hasNextLine()))
				{
					line = fileScan.nextLine();
					m = p.matcher(line);
					while (m.find()){
						if (m.group().length() != 0){
							list.add(m.group().trim());
						}
					}
				}
					
				return list;
				
				
			}
	public static List<String> listTelnr (String path)
			throws IOException
			{
				
				List<String> list = new ArrayList<String>();
				String line;
				
				String reg = "((\\+?46|0)7\\d{8})";
				Scanner fileScan = new Scanner(new File(path));
				Pattern p = Pattern.compile(reg);
				Matcher m;
				
				while ((fileScan.hasNextLine()))
				{
					line = fileScan.nextLine();
					m = p.matcher(line);
					while (m.find()){
						if (m.group().length() != 0){
							list.add(m.group().trim());
						}
					}
				}
					
				return list;
				
				
			}
	public static List<String> listKonto (String path)
			throws IOException
			{
				
				List<String> list = new ArrayList<String>();
				String line;
				
				String regM = "\\d{4}( |-)?\\d{4}( |-)?\\d{4}( |-)?\\d{4}";
				Scanner fileScan = new Scanner(new File(path));
				Pattern pMC = Pattern.compile(regM);
				Matcher mMC;
				
				while ((fileScan.hasNextLine()))
				{
					line = fileScan.nextLine();
					mMC = pMC.matcher(line);
					while (mMC.find()){
						if (mMC.group().length() != 0){
							list.add(mMC.group().trim());
						}
					}
				}
					
				return list;
				
				
			}
}