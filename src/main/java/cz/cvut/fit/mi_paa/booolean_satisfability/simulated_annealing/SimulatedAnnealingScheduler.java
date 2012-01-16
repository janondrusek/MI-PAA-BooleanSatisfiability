package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import ure.phd.simulatedannealing.interfaces.AnnealingScheduler;

public class SimulatedAnnealingScheduler implements AnnealingScheduler {

	private int iterationCount;
	private int sameTemperatureIterationCount;
	private int requiredSameTemperatureIterationCount;
	private int acceptanceCount;
	private double initialTemperature;
	private double currentTemperature;
	private double freezingTemperature;
	private double coolingMultiplier;

	public SimulatedAnnealingScheduler() {
		currentTemperature = initialTemperature;
		iterationCount = 0;
		sameTemperatureIterationCount = 0;
		acceptanceCount = 0;
		freezingTemperature = 0.0001;
	}

	public SimulatedAnnealingScheduler(double initialTemperature, double freezingTemperature, double coolingMultiplier,
			int requiredSameTemperatureIterationCount) {
		this.initialTemperature = initialTemperature;
		this.freezingTemperature = freezingTemperature;
		this.coolingMultiplier = coolingMultiplier;
		this.requiredSameTemperatureIterationCount = requiredSameTemperatureIterationCount;
	}

	@Override
	public void init() {
	}

	public void reset() {
		currentTemperature = initialTemperature;
		iterationCount = 0;
		sameTemperatureIterationCount = 0;
		acceptanceCount = 0;
	}

	@Override
	public void incrementIterationCount() {
		iterationCount++;
		sameTemperatureIterationCount = 0;
	}

	@Override
	public boolean isMarkovChainLimitReached() {
		return getSameTemperatureIterationCount() == getRequiredSameTemperatureIterationCount();
	}

	@Override
	public void incrementAcceptanceCount() {
		acceptanceCount++;
	}

	@Override
	public void incrementSameTemperatureIterationCount() {
		sameTemperatureIterationCount++;
	}

	@Override
	public double coolDown(double deltaTemperature) {
		currentTemperature *= coolingMultiplier;
		return currentTemperature;
	}

	@Override
	public boolean isFreezingPointReached() {
		return getCurrentTemperature() < getFreezingTemperature();
	}

	public int getIterationCount() {
		return iterationCount;
	}

	public void setIterationCount(int iterationCount) {
		this.iterationCount = iterationCount;
	}

	public int getSameTemperatureIterationCount() {
		return sameTemperatureIterationCount;
	}

	public void setSameTemperatureIterationCount(int sameTemperatureIterationCount) {
		this.sameTemperatureIterationCount = sameTemperatureIterationCount;
	}

	public int getAcceptanceCount() {
		return acceptanceCount;
	}

	public void setAcceptanceCount(int acceptanceCount) {
		this.acceptanceCount = acceptanceCount;
	}

	@Override
	public double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	@Override
	public double getFreezingTemperature() {
		return freezingTemperature;
	}

	public void setFreezingTemperature(double freezingTemperature) {
		this.freezingTemperature = freezingTemperature;
	}

	public double getInitialTemperature() {
		return initialTemperature;
	}

	public void setInitialTemperature(double initialTemperature) {
		this.initialTemperature = initialTemperature;
	}

	public double getCoolingMultiplier() {
		return coolingMultiplier;
	}

	public void setCoolingMultiplier(double coolingMultiplier) {
		this.coolingMultiplier = coolingMultiplier;
	}

	public int getRequiredSameTemperatureIterationCount() {
		return requiredSameTemperatureIterationCount;
	}

	public void setRequiredSameTemperatureIterationCount(int requiredSameTemperatureIterationCount) {
		this.requiredSameTemperatureIterationCount = requiredSameTemperatureIterationCount;
	}

}
