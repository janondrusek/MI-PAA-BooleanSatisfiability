/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class WeightGenerator {

	static public void generate(String fileName, String dirName, String fileNameAdd) {
		String outputDirName = dirName + "_w/";
		File inputFile = new File(dirName, fileName);
		File outPutDir = new File(outputDirName);
		outPutDir.mkdirs();
		String[] inFile = (fileName).split("\\.");
		String outputFileString = outputDirName + inFile[0] + "" + fileNameAdd + "_w." + inFile[1];
		// String myTimesFileString = "solution/" + dirName + inFile[0] + "_"
		// +fileNameAdd + "_tim_SA." + inFile[1];
		try {
			BufferedReader instancesFile = new BufferedReader(new FileReader(inputFile));
			FileOutputStream mySolutionFile = new FileOutputStream(outputFileString);
			// myTimesFile = new FileOutputStream(myTimesFileString);
			PrintStream printToSolutionsFile = new PrintStream(mySolutionFile);
			// Print printToTimesFile = new PrintStream(outputFileString);
			String s = new String();
			String s2 = new String();
			String firstchar = new String();
			// SAT_SA SATInst = new SAT_SA();
			Random rand = new Random();
			boolean commentsPart = true;
			int numOfVariables = 0;
			int clauses = 0;
			Scanner sScan;
			while (commentsPart && (s = instancesFile.readLine()) != null) {
				s2 += s + "\n";
				printToSolutionsFile.println(s);
				sScan = new Scanner(s);
				firstchar = sScan.next();
				if (firstchar.equals("p")) {
					commentsPart = false;
					String problemType = sScan.next();
					numOfVariables = sScan.nextInt();
					clauses = sScan.nextInt();
					printToSolutionsFile.print("w ");
					for (int i = 0; i < numOfVariables; i++) {
						printToSolutionsFile.print(Integer.toString(Math.abs(rand.nextInt() % 99 + 1)) + " ");
					}
					printToSolutionsFile.println("");

				}
			}
			while ((s = instancesFile.readLine()) != null) {
				printToSolutionsFile.println(s);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "uf20-0.cnf";
		String[] inFile = (fileName).split("\\.");
		String fileNameAdd = "";
		String dirName = "cnf20";
		for (int i = 1; i < 10; i++) {
			fileName = inFile[0] + i + "." + inFile[1];
			generate(fileName, dirName, fileNameAdd);
			for (int j = 0; j < 10; j++) {
				fileName = inFile[0] + i + j + "." + inFile[1];
				generate(fileName, dirName, fileNameAdd);
			}

		}

	}

}
