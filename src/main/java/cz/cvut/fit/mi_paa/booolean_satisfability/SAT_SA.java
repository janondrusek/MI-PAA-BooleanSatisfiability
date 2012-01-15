package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class SAT_SA {

	static int maxWeight, bestPrice, bestWeight, id, size;
	static long startTime, endTime;
	static Variable[] variables;

	static Clause[] formula;
	static State best, state, newState; // , temp;
	static FileWriter mySolutionFile;
	static FileOutputStream myTimesFile;
	static PrintWriter printToSolutionsFile;
	static PrintStream printToTimesFile;

	double temp; // temperature
	static double initTemp = 10000;
	static double finalTemp = 1;
	static int initSteps = 10000;
	int steps;
	static double coolFactor = 0.85;
	static double penaltyFactor = 10;
	static int numOfVariables = 0;
	static int clauses = 0;
	static String outputFileName = "";

	public static class Variable {
		public int variableNum;
		public int weight;

		public Variable(int variableNum, int weight) {
			this.variableNum = variableNum;
			this.weight = weight;

		}
	}

	public static class Clause {
		public int[] variableIndex = new int[3];
		public boolean[] negations = new boolean[3];

		public Clause(int var1, int var2, int var3) {
			variableIndex[0] = Math.abs(var1) - 1;
			if (var1 < 0) {
				negations[0] = true;
			} else {
				negations[0] = false;
			}
			variableIndex[1] = Math.abs(var2) - 1;
			if (var2 < 0) {
				negations[1] = true;
			} else {
				negations[1] = false;
			}
			variableIndex[2] = Math.abs(var3) - 1;
			if (var3 < 0) {
				negations[2] = true;
			} else {
				negations[2] = false;
			}
		}

		public boolean value(State state) { // returns value of clause
			boolean value1 = state.valuesOfVariables[variableIndex[0]] ^ negations[0];
			boolean value2 = state.valuesOfVariables[variableIndex[1]] ^ negations[1];
			boolean value3 = state.valuesOfVariables[variableIndex[2]] ^ negations[2];
			return (value1 || value2 || value3);
		}
	}

	public static class State {
		// public boolean satisfiability = false;
		// public int weight = 0;
		public boolean[] valuesOfVariables;

		public State(int numOfVariables) {
			valuesOfVariables = new boolean[numOfVariables];
			for (int i = 0; i < valuesOfVariables.length; i++) {
				valuesOfVariables[i] = false;
			}
		}

		public boolean satisfiable() {
			boolean ret = true;
			for (int i = 0; i < formula.length; i++) {
				ret = ret && formula[i].value(this);
			}
			return ret;
		}

		public int numOfSatisfiableClauses() {
			int ret = 0;
			for (int i = 0; i < formula.length; i++) {
				if (formula[i].value(this)) {
					ret++;
				}
			}
			return ret;
		}

		public int weight() {
			int ret = 0;
			for (int i = 0; i < valuesOfVariables.length; i++) {
				if (valuesOfVariables[i]) {
					ret += variables[i].weight;
				}

			}

			return ret;
		}
	}

	void SAThread(String[] args) {
		// tryNext(0,0,0);
		State initState = new State(numOfVariables);
		best = simulatedAnnealing(initState);

	}

	public boolean frozen() { // checks if the temperature is below finalTemp
		return (temp < finalTemp);
	}

	public double cool(double temp) { // lowers temperature using coolFactor
		temp = temp * coolFactor;
		return temp;
	}

	public boolean equilibrium() { // counts steps to equilibrium
		steps--;
		if (steps < 0) {
			return true;
		} else {
			return false;
		}
	}

	public State better(State state, State best) {
		// checks states for higher price
		if (state.satisfiable()) {
			if (best.satisfiable()) {
				if (state.weight() > best.weight()) {
					return state;
				} else {
					return best;
				}
			} else {
				return state;
			}
		} else {
			if (best.satisfiable()) {
				return best;
			} else {
				if (state.weight() > best.weight()) {
					return state;
				} else {
					return best;
				}
			}
		}
	}

	public State nextState(State state, double temp) {
		State newState = new State(numOfVariables);
		// lets's choose some randnom neighbour
		Random rnd = new Random();
		int neighbour = Math.abs(rnd.nextInt() % numOfVariables);
		System.arraycopy(state.valuesOfVariables, 0, newState.valuesOfVariables, 0, state.valuesOfVariables.length);
		double delta;
		newState.valuesOfVariables[neighbour] = !newState.valuesOfVariables[neighbour];
		delta = state.weight() - newState.weight();// state.numOfSatisfiableClauses()
													// -
													// newState.numOfSatisfiableClauses();
													// //delta = weight -
													// state.weight;
		// System.out.println(delta); //TODO comment
		if (!newState.satisfiable()) {
			// System.out.println("neni splnitelna");
			delta = delta / penaltyFactor;
		} else {
			delta = delta * penaltyFactor;
			System.out.println("je splnitelna, delta je " + delta);
			// System.out.println(newState.numOfSatisfiableClauses());
			// System.out.println(state.numOfSatisfiableClauses());
		}
		if (delta > 0) {
			// System.out.println("delta > 0");
			// lets compute if we can continue
			double r;
			// int weight = state.numOfSatisfiableClauses() -
			// variables[neighbour].weight;

			r = rnd.nextDouble(); // = (double)rand() / (double)RAND_MAX;

			if (r < Math.exp(-((double) delta / temp))) {

				// state.valuesOfVariables[neighbour] =
				// !state.valuesOfVariables[neighbour];
				state = newState;
			}

		} else { // the item is not present, so let's check if it fits in
		// System.out.println("delta < 0");
		// if ((state.weight + variables[neighbour].weight) <= maxWeight) {
			// ok it fits
			// state.valuesOfVariables[neighbour] =
			// !state.valuesOfVariables[neighbour];
			state = newState;
			// System.out.println("je splnitelna, delta je " + delta);
		}
		// TODO
		return state;
	}

	public State simulatedAnnealing(State initState) {
		temp = initTemp;
		State state = initState;
		State best = state;
		do {
			steps = initSteps;
			do {
				state = nextState(state, temp);
				// if(state.satisfiable()){System.out.println("state i tady!!");}
				best = better(best, state);
				// if(best.satisfiable()){System.out.println("best i tady!!");}
				// System.out.println(best.numOfSatisfiableClauses());
			} while (!equilibrium()); // delka vnitrni smycky
			temp = cool(temp);
		} while (!frozen());

		return best;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// maxweight = 20;
		// bestPrice = 0;
		// bestWeight = 0;

		String fileName;
		String dirName;
		String fileNameAdd;
		if (args.length > 2) {
			fileName = args[0];
			fileNameAdd = args[1];
			dirName = args[2];
			initTemp = Double.parseDouble(args[3]);
			finalTemp = Double.parseDouble(args[4]);
			initSteps = Integer.parseInt(args[5]);
			coolFactor = Double.parseDouble(args[6]);
			penaltyFactor = Double.parseDouble(args[7]);
			outputFileName = args[8];

		} else {
			fileName = "uf20-010_w.cnf";
			fileNameAdd = "";
			dirName = "cnf20_w";
		}
		System.out.println(dirName + "/" + fileName);
		File inputFile = new File(dirName, fileName);
		File outputDir = new File("solution", dirName);
		// outputDir.mkdirs();
		String[] inFile = (fileName).split("\\.");
		String mySolutionFileString = "solution/" + dirName + "/" + inFile[0] + "_" + fileNameAdd + "_sol_SA."
				+ inFile[1];
		String myTimesFileString = "solution/" + dirName + "/" + inFile[0] + "_" + fileNameAdd + "_tim_SA." + inFile[1];
		try {
			BufferedReader instancesFile = new BufferedReader(new FileReader(inputFile));
			mySolutionFile = new FileWriter(mySolutionFileString);
			myTimesFile = new FileOutputStream(myTimesFileString);
			printToSolutionsFile = new PrintWriter(mySolutionFile);
			printToTimesFile = new PrintStream(myTimesFile);
			String s = new String();
			String s2 = new String();
			String firstchar = new String();
			SAT_SA SATInst = new SAT_SA();
			Random rand = new Random();
			boolean commentsPart = true;
			numOfVariables = 0;
			clauses = 0;
			Scanner sScan;
			while (commentsPart && (s = instancesFile.readLine()) != null) {
				s2 += s + "\n";
				sScan = new Scanner(s);
				firstchar = sScan.next();
				if (firstchar.equals("p")) {
					commentsPart = false;
					String problemType = sScan.next();
					numOfVariables = sScan.nextInt();
					clauses = sScan.nextInt();
				}
			}

			variables = new Variable[numOfVariables];

			if ((s = instancesFile.readLine()) != null) {
				sScan = new Scanner(s);
				firstchar = sScan.next();
				if (firstchar.equals("w")) {
					for (int i = 0; i < numOfVariables; i++) {
						variables[i] = new Variable(i + 1, sScan.nextInt());

					}

				}

			}
			// for (int k = 0; k < variables.length; k++) {
			// variables[k] = new Variable(k+1,Math.abs(rand.nextInt()%100));
			// }

			formula = new Clause[clauses];

			for (int i = 0; i < clauses; i++) {
				s = instancesFile.readLine();
				s2 += s + "\n";
				sScan = new Scanner(s);
				formula[i] = new Clause(sScan.nextInt(), sScan.nextInt(), sScan.nextInt());
			}

			state = new State(numOfVariables);
			best = new State(numOfVariables);
			newState = new State(numOfVariables);

			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			// zacatek mereni
			startTime = System.nanoTime();
			SATInst.SAThread(args);
			// konec mereni
			endTime = System.nanoTime();
			// printIt();

			writeItToFile();

			// System.out.println(endTime-startTime);
			bestWeight = 0;
			bestPrice = 0;

			instancesFile.close();
			printToSolutionsFile.close();
			printToTimesFile.close();
			mySolutionFile.close();
			myTimesFile.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void writeItToFile() {

		printToSolutionsFile.print(best.numOfSatisfiableClauses() + " " + (best.satisfiable()) + " " + best.weight()
				+ "  ");
		for (int i = 0; i < best.valuesOfVariables.length; i++) {
			if (best.valuesOfVariables[i])
				printToSolutionsFile.print("1");
			else
				printToSolutionsFile.print("0");
			if (i != (best.valuesOfVariables.length - 1))
				printToSolutionsFile.print(" ");
		}
		printToSolutionsFile.println("");
		printToTimesFile.println(size + " " + (endTime - startTime));

	}

	public static void printIt() {
		// System.out.println("Final price:" + bestPrice );
		// System.out.println("Final weight:" + bestWeight );
		System.out.print(id + " " + best.weight() + " ");
		for (int i = 0; i < best.valuesOfVariables.length; i++) {
			if (best.valuesOfVariables[i])
				System.out.print("1");
			else
				System.out.print("0");
			if (i != (best.valuesOfVariables.length - 1))
				System.out.print(" ");
			// System.out.printToSolutionsFile(bestSolution[i] + " ");
			// System.out.printToSolutionsFile("w:" + v[i] + " ");
			// System.out.println("p:" + c[i] + " ");
		}
		System.out.println("");

	}

}
