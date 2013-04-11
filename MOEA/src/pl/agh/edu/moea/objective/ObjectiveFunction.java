package pl.agh.edu.moea.objective;

import pl.agh.edu.moea.set.Solution;

public interface ObjectiveFunction {
	
	double[] getObjectiveSpaceSolutionValues(Solution solution);

}
