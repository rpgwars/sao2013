package pl.agh.edu.moea.main;

import java.util.LinkedList;
import java.util.List;

import pl.agh.edu.moea.set.Solution;

public enum Optimization {
	
	MINIMIZATION
	  {
		
		@Override
		public List<Solution> removeDominatedSolutions(List<Solution> solutions) {
			
			List<Solution> dominatedSolutions = new LinkedList<Solution>();
			for(int i=solutions.size()-1; i>=0; i--){
				for(int j=i-1; j>=0; j--){
					if((solutions.get(i).getObjectiveVector()[1] > solutions.get(j).getObjectiveVector()[1])){
							dominatedSolutions.add(solutions.get(i));
							break;
					}
				}
			}
		
			solutions.removeAll(dominatedSolutions);
			return dominatedSolutions;
		
		}
	},
	MAXIMALIZATION {
		
		@Override
		public List<Solution> removeDominatedSolutions(List<Solution> solutions) {
			
			List<Solution> dominatedSolutions = new LinkedList<Solution>();
			for(int i=0; i<solutions.size(); i++){
				for(int j=i+1; j<solutions.size(); j++){
					if((solutions.get(i).getObjectiveVector()[1] < solutions.get(j).getObjectiveVector()[1])){
							dominatedSolutions.add(solutions.get(i));
							break;
					}
				}
			}
		
			solutions.removeAll(dominatedSolutions);
			return dominatedSolutions;
		}
	};
	
	
	//rozwiazania sa posortowane rosnaca ze wzgledu na pierwsze kryterium
	public abstract List<Solution> removeDominatedSolutions(List<Solution> solutions);

}
