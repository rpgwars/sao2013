package pl.agh.edu.moea.set;

import pl.agh.edu.moea.objective.ObjectiveFunction;

public interface Solution extends Comparable<Solution>{

	double[] getDecisionVector();
	double setDecisionVector(double[] decisionVector);
	double[] getObjectiveVector(); 
	double getFitness(); 
	void setFitness(double fitness); 
	void setObjectiveFunction(ObjectiveFunction objectiveFunction);
	ObjectiveFunction getObjectiveFunction();
	public Solution getSolutionCopy();
	
}
