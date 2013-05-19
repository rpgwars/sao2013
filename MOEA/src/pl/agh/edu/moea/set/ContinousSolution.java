package pl.agh.edu.moea.set;

import java.util.Random;

import pl.agh.edu.moea.objective.ObjectiveFunction;

public class ContinousSolution implements Solution{
	
	private double[] solution; 
	private ObjectiveFunction objectiveFunction;
	private double fitness; 
	private double[] objectiveVector; 
	
	public ContinousSolution(double decisionSpaceWidth, ObjectiveFunction objectiveFunction,int decisionSpaceDimension){
		
		Random random = new Random();
		solution = new double[decisionSpaceDimension];
		for(int i =0; i<decisionSpaceDimension; i++)
			solution[i] = random.nextDouble()*decisionSpaceWidth; 
		this.objectiveFunction = objectiveFunction;
		this.objectiveVector = objectiveFunction.getObjectiveSpaceSolutionValues(this);
		
	}
	
	public ContinousSolution(){
		
	}
	
	@Override
	public Solution getSolutionCopy() {
		ContinousSolution copy = new ContinousSolution();
		copy.setObjectiveFunction(getObjectiveFunction());
		copy.setDecisionVector(getDecisionVector().clone());
		return copy;
	}


	@Override
	public int compareTo(Solution solution) {
		
		double objective1s1 = objectiveFunction.getObjectiveSpaceSolutionValues(this)[0]; 
		double objective1s2 = objectiveFunction.getObjectiveSpaceSolutionValues(solution)[0];
		if(objective1s1 > objective1s2)
			return 1; 
		if(objective1s1 < objective1s2)
			return -1;
		
		return 0;
	}


	@Override
	public double[] getDecisionVector() {
		return solution;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

    @Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


	@Override
	public double[] getObjectiveVector() {
		return objectiveVector;
	}


	@Override
	public double setDecisionVector(double[] decisionVector) {
		this.solution = decisionVector;
		this.objectiveVector = objectiveFunction.getObjectiveSpaceSolutionValues(this);
		return 0;
	}


	@Override
	public void setObjectiveFunction(ObjectiveFunction objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	@Override
	public ObjectiveFunction getObjectiveFunction() {	
		return objectiveFunction;
	}

	/*
	@Override 
	public boolean equals(Object solution){
		
		if(!(solution instanceof ContinousSolution))
			return false; 
		
		ContinousSolution anotherSolution = (ContinousSolution)solution;
		
		if(anotherSolution.objectiveFunction != objectiveFunction)
			return false; 
		for(int i =0; i< this.solution.length; i++)
			if(anotherSolution.solution[i] != this.solution[i])
				return false; 
		return true; 
	}
	*/
	
	

}
