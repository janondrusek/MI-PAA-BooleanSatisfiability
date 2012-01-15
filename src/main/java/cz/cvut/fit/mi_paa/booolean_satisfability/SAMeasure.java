package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SAMeasure {

	static String fileName; // = "knap_40.txt";
	static String fileNameAdd; // = "st";
	static String dirName; // = "startTemp/";
	static String startTemp; // = "1000";
	static String finTemp; // = "1";
	static String steps; // = "1000";
	static String coolFactor; // = "0.5";
	static String penaltyFactor;
	static File sub; // = new File("solution", dirName);
	static String[] params; // =
							// {fileName,fileNameAdd,dirName,startTemp,finTemp,steps,coolFactor};
	static String outputLine;

	private static void setInitValues() {
		fileName = "uf20-010.cnf";
		fileNameAdd = "";
		dirName = "cnf20";
		startTemp = "1000";
		finTemp = "10";
		steps = "10000";
		coolFactor = "0.85";
		penaltyFactor = "2";
		sub = new File("solution", dirName);
		String[] newParams = { fileName, fileNameAdd, dirName, startTemp, finTemp, steps, coolFactor, penaltyFactor };
		params = newParams.clone();
	}

	private static String createOutputLine(String dynTimeFileName, String dynSolFileName, String saTimeFileName,
			String saSolFileName) throws IOException {
		// BufferedReader dynTimeFile = null;
		// BufferedReader dynSolFile = null;
		BufferedReader saTimeFile = null;
		BufferedReader saSolFile = null;
		long time = 0;
		double relativeFault = 100.0;
		boolean saSatisfiability = false;
		try {
			// dynTimeFile = new BufferedReader(new
			// FileReader(dynTimeFileName));
			// dynSolFile = new BufferedReader(new FileReader(dynSolFileName));
			saTimeFile = new BufferedReader(new FileReader(saTimeFileName));
			saSolFile = new BufferedReader(new FileReader(saSolFileName));
			String saTimeLineToRead;
			int count = 0;

			// compute average time
			time = 0;
			Scanner saTimeScan;
			while ((saTimeLineToRead = saTimeFile.readLine()) != null) {
				count++;
				// s2 += s + "\n";
				// System.out.println(s);
				saTimeScan = new Scanner(saTimeLineToRead);
				saTimeScan.nextInt(); // forget the instance #
				time += saTimeScan.nextInt();
			}
			time = time / count;

			// and the average relative fault
			String saSolLineToRead, dynSolLineToRead;
			Scanner saSolScan, dynSolScan;
			int dynPrice, saPrice;

			while (((saSolLineToRead = saSolFile.readLine()) != null)) { // &&
																			// (dynSolLineToRead
																			// =
																			// dynSolFile.readLine())
																			// !=
																			// null)
																			// {
				saSolScan = new Scanner(saSolLineToRead);
				// dynSolScan = new Scanner(dynSolLineToRead);
				// if(saSolScan.nextInt() == dynSolScan.nextInt()){ // do we
				// compare the same instances?
				saSolScan.nextInt(); // we'll skip the number of items in
										// knapsack, we know that we're
										// comparing the same instance
				// dynSolScan.nextInt();
				// saPrice = saSolScan.nextInt();
				saSatisfiability = saSolScan.nextBoolean();
				// dynPrice = dynSolScan.nextInt();
				// relativeFault = ((dynPrice - saPrice)/(double) dynPrice) *
				// 100;
				// }
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(SAMeasure.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("fajlnotfound");
		} finally {
			try {
				// dynTimeFile.close();
				// dynSolFile.close();
				saTimeFile.close();
				saSolFile.close();
			} catch (IOException ex) {
				Logger.getLogger(SAMeasure.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return Boolean.toString(saSatisfiability) + " " + Long.toString(time) + " " + Double.toString(relativeFault);
	}

	public static void doTheMeasure(PrintStream out) throws IOException {
		String[] newParams = { fileName, fileNameAdd, dirName, startTemp, finTemp, steps, coolFactor, penaltyFactor };
		SAT_SA.main(newParams);
		String fileNameParts[] = fileName.split("\\.");

		String saTime = createOutputLine(null,
				null,
				// "solution/" + fileNameParts[0] + "_tim_dynprog." +
				// fileNameParts[1],
				// "solution/" + fileNameParts[0] + "_sol_dynprog." +
				// fileNameParts[1],
				"solution/" + dirName + "/" + fileNameParts[0] + "_" + fileNameAdd + "_tim_SA." + fileNameParts[1],
				"solution/" + dirName + "/" + fileNameParts[0] + "_" + fileNameAdd + "_sol_SA." + fileNameParts[1]);
		out.println(saTime);

	}

	public static void main(String[] args) throws IOException {
		// sub.mkdirs();
		setInitValues(); // TODO

		String numOfVariables = "20";
		String instanceNumber = "0";
		dirName = "cnf" + numOfVariables + "_w";
		sub = new File("solution", dirName);
		sub.mkdirs();

		File measures = new File("solution/" + dirName, "SATmeasures.txt");
		measures.createNewFile();
		FileOutputStream measuresOut = new FileOutputStream(measures);
		PrintStream measuresOutStream = new PrintStream(measuresOut);

		// String[] instanceNumbers = {}

		double[] penaltyFactors = { 0.5, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		for (int k = 0; k < penaltyFactors.length; k++) {
			for (int i = 1; i < 10; i++) {
				fileName = "uf" + numOfVariables + "-" + instanceNumber + Integer.toString(i) + "_w.cnf";
				fileNameAdd = "";// instanceNumber + Integer.toString(i);
				penaltyFactor = Double.toString(penaltyFactors[k]);
				doTheMeasure(measuresOutStream);
				// for (int j = 0; j < 10; j++) {
				// fileName = "uf" + numOfVariables + "-" + instanceNumber +
				// Integer.toString(i) + Integer.toString(j) + "_w.cnf";
				// fileNameAdd = instanceNumber + Integer.toString(i) +
				// Integer.toString(j);
				// doTheMeasure(measuresOutStream);
				// }

			}
		}

		//
		// DynProg.main(params);
		//
		// measuresOutStream.println("items");
		// setInitValues();
		// int[] fileNanmeInts = {10,15,20,22,25,27,30,32,40};
		// for (int i = 0; i < fileNanmeInts.length; i++) {
		// fileName = "knap_" + Integer.toString(fileNanmeInts[i]) + ".txt";
		// fileNameAdd = Integer.toString(fileNanmeInts[i]) ;
		// dirName = "startTemp/";
		// sub = new File("solution", dirName);
		// sub.mkdirs();
		// doTheMeasure(measuresOutStream);
		// }
		//
		// measuresOutStream.println("startTemp");
		// setInitValues();
		// double[] startTemps = {100,200,300,400,500,600,700,800,900,1000,1500,
		// 2000,3000,4000,5000};
		// for (int i = 0; i < startTemps.length; i++) {
		// startTemp = Double.toString(startTemps[i]);
		// fileNameAdd = Double.toString(startTemps[i]);
		// dirName = "startTemp/";
		// sub = new File("solution", dirName);
		// sub.mkdirs();
		// doTheMeasure(measuresOutStream);
		// }
		//
		// measuresOutStream.println("endTemp");
		// setInitValues();
		// double[] endTemps =
		// {0.01,0.5,1,2,3,4,5,6,7,8,9,10,15,20,25,30,40,50,60,
		// 70, 80, 90, 100, 200, 300};
		// for (int i = 0; i < endTemps.length; i++) {
		// finTemp = Double.toString(endTemps[i]);
		// fileNameAdd = Double.toString(endTemps[i]);
		// dirName = "endTemp/";
		// sub = new File("solution", dirName);
		// sub.mkdirs();
		// doTheMeasure(measuresOutStream);
		// }
		//
		// measuresOutStream.println("coolFact");
		// setInitValues();
		// double[] coolFactors = {0.01, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7,
		// 0.75,
		// 0.8, 0.85, 0.9};
		// for (int i = 0; i < coolFactors.length; i++) {
		// coolFactor = Double.toString(coolFactors[i]);
		// fileNameAdd = Double.toString(coolFactors[i]);
		// dirName = "coolFactor/";
		// sub = new File("solution", dirName);
		// sub.mkdirs();
		// doTheMeasure(measuresOutStream);
		//
		// }
		//
		// measuresOutStream.println("cycles");
		//
		// setInitValues();
		// int[] cycles = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
		// 1100,
		// 1200, 1300, 1400, 1500, 2000, 2500, 3000, 4000, 5000,
		// 7000, 10000, 15000, 20000, 30000};
		// for (int i = 0; i < cycles.length; i++) {
		// steps = Integer.toString(cycles[i]);
		// fileNameAdd = Integer.toString(cycles[i]);
		// dirName = "steps/";
		// sub = new File("solution", dirName);
		// sub.mkdirs();
		// doTheMeasure(measuresOutStream);
		//
		// }
		//
		// SA.main(params);

		measuresOutStream.close();
		measuresOut.close();

	}
}
