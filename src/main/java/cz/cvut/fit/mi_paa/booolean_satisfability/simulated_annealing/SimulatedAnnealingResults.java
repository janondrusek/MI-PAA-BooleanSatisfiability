package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import cz.cvut.fit.mi_paa.booolean_satisfability.Result;
import cz.cvut.fit.mi_paa.booolean_satisfability.Results;

public class SimulatedAnnealingResults implements Results {

	private Result[] results;

	public SimulatedAnnealingResults(Result[] results) {
		this.results = results;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Result result : results) {
			sb.append(result);
			sb.append("\n");
		}
		return sb.toString();
	}

}
