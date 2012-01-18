package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.cvut.fit.mi_paa.booolean_satisfability.builder.Builder;
import cz.cvut.fit.mi_paa.booolean_satisfability.builder.FormulaBuilder;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Row;
import cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing.SimulatedAnnealingResolver;
import cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing.SimulatedAnnealingResults;

public class SATRunner {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("sk"));
		long startCpu = getCpuTime();
		long startTimestamp = System.currentTimeMillis();
		try {
			String[] files = { "src/main/resources/cnf20_w/uf20-010_w.cnf",
					"src/main/resources/cnf20_w/uf20-011_w.cnf", "src/main/resources/cnf20_w/uf20-012_w.cnf" };
			Result[] results = new Result[files.length];
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
				FormulaReader kr = getFormulaReader(files[i]);
				results[i] = solveFormula(kr, Integer.parseInt(args[1]));
			}
			System.out.println(new SimulatedAnnealingResults(results));
		} catch (Exception e) {
			help(e.getMessage());
			e.printStackTrace();
		} finally {
			printTimeInfo("Total operation", startCpu, startTimestamp);
		}
	}

	private static FormulaReader getFormulaReader(String file) throws FileNotFoundException {
		return new FormulaReader(new File(file));
	}

	private static Result solveFormula(FormulaReader kr, int loopCount) {
		Result result = null;
		List<Row> rows = new ArrayList<Row>();
		while (kr.hasNext()) {
			rows.add(kr.next());
		}
		Builder<Formula> builder = new FormulaBuilder(rows.toArray(new Row[rows.size()]));
		builder.build();
		Formula formula = builder.getObject();
		Resolver[] resolvers = new Resolver[] { new SimulatedAnnealingResolver() };
		for (Resolver resolver : resolvers) {
			result = resolver.getResult(loopCount, formula);
		}
		return result;
	}

	@SuppressWarnings("restriction")
	private static long getCpuTime() {
		return ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean())
				.getProcessCpuTime();
	}

	private static void help(String message) {
		System.out.println(message);
		System.out.println("Usage: java -jar "
				+ SATRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath()
				+ " datafile.txt num_of_repeats");
	}

	private static void printTimeInfo(String operationName, long startCpu, long startTimestamp) {
		printTimeInfo(operationName, startCpu, startTimestamp, 1);
	}

	private static void printTimeInfo(String operationName, long startCpu, long startTimestamp, int numOfRepeats) {
		System.out.printf("%s took %.9f CPU s, real: %.9f s%n", operationName, (getCpuTime() - startCpu) / 1000000000D
				/ numOfRepeats, (System.currentTimeMillis() - startTimestamp) / 1000D / numOfRepeats);
	}
}
