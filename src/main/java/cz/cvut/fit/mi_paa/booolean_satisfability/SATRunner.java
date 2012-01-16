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

public class SATRunner {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("sk"));
		long startCpu = getCpuTime();
		long startTimestamp = System.currentTimeMillis();
		try {
			String[] files = { args[0] };
			for (String file : files) {
				System.out.println(file);
				FormulaReader kr = getFormulaReader(file);
				solveFormula(kr, Integer.parseInt(args[1]));
			}
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

	private static void solveFormula(FormulaReader kr, int loopCount) {
		List<Row> rows = new ArrayList<Row>();
		while (kr.hasNext()) {
			rows.add(kr.next());
		}
		Builder<Formula> builder = new FormulaBuilder(rows.toArray(new Row[rows.size()]));
		builder.build();
		Formula formula = builder.getObject();
		Resolver[] resolvers = new Resolver[] { new SimulatedAnnealingResolver() };
		for (Resolver resolver : resolvers) {
			Result result = resolver.getResult(loopCount, formula);
			System.out.println(result);
		}
	}

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
