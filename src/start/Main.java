package start;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.itextpdf.text.log.SysoLogger;

import presentation.SqlCommands;

public class Main {

	public static void main(String[] args) throws IOException {

		FileInputStream file = new FileInputStream(args[0]);
		InputStreamReader string = new InputStreamReader(file);
		BufferedReader buff = new BufferedReader(string);
		String input = buff.readLine();
		while (input != null) {
			System.out.println(input);
			SqlCommands sqlCommand = new SqlCommands();
			sqlCommand.parseInput(input);
			try {
				sqlCommand.command();
			} catch (Exception e) {
				System.out.println("Something Wrong!");
			}
			input = buff.readLine();
		}
	}

}
