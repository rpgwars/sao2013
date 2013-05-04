package pl.agh.edu.moea.main;

import pl.agh.edu.moea.objective.ObjectiveFunction;

public class Parameters {
	
	private int nrOfSetsInPopulation; 
	private int nrOfSolutionsInSet;
	private ObjectiveFunction objectiveFunction; 
	private Optimization optimization; 
	private double function1Boundary; 
	private double function2Boundary;
	private int nrOfIterations; 
	private int numberOfMutationsInIteration; 
	private int polynomialIdstributionIndex; 
	private double decisionSpaceWidth; 
	private double probabilitiyOfMutation;
	
	public int getNrOfSetsInPopulation() {
		return nrOfSetsInPopulation;
	}
	public void setNrOfSetsInPopulation(int nrOfSetsInPopulation) {
		this.nrOfSetsInPopulation = nrOfSetsInPopulation;
	}
	public int getNrOfSolutionsInSet() {
		return nrOfSolutionsInSet;
	}
	public void setNrOfSolutionsInSet(int nrOfSolutionsInSet) {
		this.nrOfSolutionsInSet = nrOfSolutionsInSet;
	}
	public ObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}
	public void setObjectiveFunction(ObjectiveFunction objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}
	public Optimization getOptimization() {
		return optimization;
	}
	public void setOptimization(Optimization optimization) {
		this.optimization = optimization;
	}
	public double getFunction1Boundary() {
		return function1Boundary;
	}
	public void setFunction1Boundary(double function1Boundary) {
		this.function1Boundary = function1Boundary;
	}
	public double getFunction2Boundary() {
		return function2Boundary;
	}
	public void setFunction2Boundary(double function2Boundary) {
		this.function2Boundary = function2Boundary;
	}
	public int getNrOfIterations() {
		return nrOfIterations;
	}
	public void setNrOfIterations(int nrOfIterations) {
		this.nrOfIterations = nrOfIterations;
	}
	public int getNumberOfMutationsInIteration() {
		return numberOfMutationsInIteration;
	}
	public void setNumberOfMutationsInIteration(int numberOfMutationsInIteration) {
		this.numberOfMutationsInIteration = numberOfMutationsInIteration;
	}
	public int getPolynomialIdstributionIndex() {
		return polynomialIdstributionIndex;
	}
	public void setPolynomialIdstributionIndex(int polynomialIdstributionIndex) {
		this.polynomialIdstributionIndex = polynomialIdstributionIndex;
	}
	public double getDecisionSpaceWidth() {
		return decisionSpaceWidth;
	}
	public void setDecisionSpaceWidth(double decisionSpaceWidth) {
		this.decisionSpaceWidth = decisionSpaceWidth;
	}
	public double getProbabilitiyOfMutation() {
		return probabilitiyOfMutation;
	}
	public void setProbabilitiyOfMutation(double probabilitiyOfMutation) {
		this.probabilitiyOfMutation = probabilitiyOfMutation;
	}
	
	
	
	

}
