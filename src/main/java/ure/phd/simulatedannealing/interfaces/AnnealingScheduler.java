package ure.phd.simulatedannealing.interfaces;

public interface AnnealingScheduler {

	/**
	 * initializes the scheduler
	 */
	public void init();

	/**
	 * is used for probabilty and also termination condition
	 * 
	 * @return current temperature
	 */
	public double getCurrentTemperature();

	/**
	 * gives freezing temperature of the system
	 * 
	 * @return freezing temperature
	 */
	public double getFreezingTemperature();

	/**
	 * solver calls this function for each try which also means the temperature
	 * is just changed so it is adviced to reset acceptedCount and
	 * sameTemperatureIterationCount
	 */
	public void incrementIterationCount();

	/**
	 * this function limits the number of tries in a temperature state,
	 * recommended to look for; 1) number of acceptances and 2) number of
	 * iterations for a certain temperature which ever comes first recommended
	 * to return true cooling down is done after this function returns true
	 * 
	 * @return true if the markov limit is reached.
	 */
	public boolean isMarkovChainLimitReached();

	/**
	 * should increment the accepted number of solutions per a temperature state
	 * and is called by problem solver when a solution is accepted this function
	 * is useful for isMarkovChainLimitReached check
	 */
	void incrementAcceptanceCount();

	/**
	 * should increment the number of iteration per a temperature state and is
	 * called by problem solver before a new iteration is done this function is
	 * useful for isMarkovChainLimitReached check
	 */
	void incrementSameTemperatureIterationCount();

	/**
	 * cools the current temperature down
	 * 
	 * @param deltaTemperature
	 *            is the temperature difference between the last two
	 *            temperatures, this argument is not a must to use for cooling
	 *            down however some cooling down schedulers may depends on it
	 * @return current temperature (after cooled down)
	 */
	public double coolDown(double deltaTemperature);

	/**
	 * should check if the current temperature is less than the freezing point
	 * 
	 * @return true if the current temperature is less than the freezing point
	 */
	public boolean isFreezingPointReached();
}
