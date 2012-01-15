package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.lang.management.ManagementFactory;
import java.util.Locale;

public class SATRunner {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("sk"));
		long startCpu = getCpuTime();
		long startTimestamp = System.currentTimeMillis();
		try {
			String[] files = { args[0] };
			for (String file : files) {
				System.out.println(file);
				SATReader kr = getSATReader(file);
				solveSAT(kr, Integer.parseInt(args[1]));
			}
		} catch (Exception e) {
			help(e.getMessage());
			e.printStackTrace();
		} finally {
			printTimeInfo("Total operation", startCpu, startTimestamp);
		}
	}

	private static SATReader getSATReader(String file) {
		return null;
	}

	private static void solveSAT(SATReader kr, int parseInt) {
		
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
