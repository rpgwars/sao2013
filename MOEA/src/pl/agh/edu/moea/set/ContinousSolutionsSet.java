package pl.agh.edu.moea.set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SolutionBinaryCrossover;
import pl.agh.edu.moea.operation.SolutionMutation;

public class ContinousSolutionsSet implements SolutionSet{
	
	private List<Solution> solutions;
	private int size; 
	private Random random;
	
	private Optimization optimization; 
	private double horizontalBoundary;
	private double verticalBoundary; 
	
	
	public ContinousSolutionsSet(int size, double decisionSpaceWidth, ObjectiveFunction objectiveFunction, Optimization optimization,
			double horizontalBoundary, double verticalBoundary){
		
		solutions = new ArrayList<Solution>(size);
		this.size = size; 
		
		this.optimization = optimization;
		this.horizontalBoundary = horizontalBoundary;
		this.verticalBoundary = verticalBoundary;
		
		for(int i = 0; i<size; i++){
			solutions.add(new ContinousSolution(decisionSpaceWidth, objectiveFunction));
		}
		
		random = new Random(); 
	
	}
	
	public ContinousSolutionsSet(Optimization optimization, 
			double horizontalBoundary, double verticalBoundary, List<Solution> solutionList){
		this.solutions = solutionList; 
		this.size = solutionList.size();
		
		this.optimization = optimization;
		this.horizontalBoundary = horizontalBoundary;
		this.verticalBoundary = verticalBoundary;
		
		random = new Random(); 
			
	}

	public ContinousSolutionsSet(List<Solution> matingSolutionList){
		   random = new Random(); 
		   this.size = matingSolutionList.size(); 
		   solutions = matingSolutionList;
	}

	@Override
	public SolutionSet getSetCopy() {
		
		List<Solution> solutionCopies = new ArrayList<Solution>(); 
		for(Solution solution : solutions)
			solutionCopies.add(solution.getSolutionCopy());
		return new ContinousSolutionsSet(optimization, 
				horizontalBoundary, verticalBoundary, solutionCopies);
	}
	
	
	
	
	@Override
	public List<Solution> getRandomSolutions(int nrOfSolutionsToRemove, int alredyChosenSolution){
		
		if(2*nrOfSolutionsToRemove <= solutions.size() - 1)
			return getRandomSolutions(nrOfSolutionsToRemove, false, alredyChosenSolution);
		else{
			return getRandomSolutions(solutions.size() - nrOfSolutionsToRemove - 1, true, alredyChosenSolution);
		}
			
	}
	
	
	private List<Solution> getRandomSolutions(int r, boolean b, int alreadyChosenSolution){
		
		List<Solution> selectedSolutions = new ArrayList<Solution>(r); 
		boolean[] selectedSolutionsArray = new boolean[solutions.size()];
		
		
		for(int i = 0; i<selectedSolutionsArray.length; i++)
			selectedSolutionsArray[i] = b;
			
		while(r > 0){
			int selected = random.nextInt(solutions.size());
			if((selectedSolutionsArray[selected] == b) && (selected != alreadyChosenSolution)){
				r--; 
				selectedSolutionsArray[selected] = !b;
				selectedSolutions.add(solutions.get(selected));
					
			}
		}
		if(b){
			selectedSolutions.clear();
			for(int i =0; i<solutions.size(); i++){
				
				if(selectedSolutionsArray[i] && i != alreadyChosenSolution){
					selectedSolutions.add(solutions.get(i));
					
				}
			}
		}
		return selectedSolutions;
	}



	@Override
	public List<Solution> getSolutions() {
		return solutions;
	}


	@Override
	public SolutionSet spawnNewSetThroughBinaryTournament() {
		
		FitnessEvaluator evaluator = new FitnessEvaluator(); 
		Collections.sort(solutions);
		evaluator.assignFitness(size, optimization, horizontalBoundary, verticalBoundary, this);
		List<Solution> matingSelectionSolutionList = new ArrayList<Solution>(size); 
		for(int i=0; i<size; i++){
			Solution solutionA = getRandomSolution(); 
			Solution solutionB = getRandomSolution(); 
			ContinousSolution newCs = new ContinousSolution();
			if(solutionA.getFitness() > solutionB.getFitness()){
				
				newCs.setObjectiveFunction(solutionA.getObjectiveFunction());
				newCs.setDecisionVector(solutionA.getDecisionVector());
				
			}
				
			else{
				
				newCs.setObjectiveFunction(solutionB.getObjectiveFunction());
				newCs.setDecisionVector(solutionB.getDecisionVector());
				
			}
			
			
			matingSelectionSolutionList.add(newCs);
		}
		
		return new ContinousSolutionsSet(matingSelectionSolutionList);
		
	}
	
	private Solution getRandomSolution(){
		
		return solutions.get(random.nextInt(solutions.size()));
	}

	@Override
	public void doBinaryCrossover(double probability, double[] boundaries) {
		
		
		for(int i=0; i<solutions.size(); i++){
			if(probability > random.nextDouble()){
				int j = random.nextInt(solutions.size());
				
				double beta = 1.0 + (random.nextDouble() - 0.5)/5.0;
				boolean crossover; 
				if(beta <= 1.0)
					crossover = random.nextDouble() > beta;
				else
					crossover = random.nextDouble() > (1.0/Math.pow(beta, 3));
				if(crossover){
					Solution s1 = solutions.get(i);
					Solution s2 = solutions.get(j);
					double dv1 = s1.getDecisionVector();
					double dv2 = s2.getDecisionVector();
					
					double av = (dv1 + dv2)/2.0; 
					
					dv1 = av -0.5*beta*(dv2 - dv1);
					dv2 = av +0.5*beta*(dv2 - dv1);
					
					if(dv1 < boundaries[0])
						dv1 = boundaries[0];
					if(dv1 > boundaries[1])
						dv1 = boundaries[1]; 
					
					if(dv2 < boundaries[0])
						dv2 = boundaries[0];
					if(dv2 > boundaries[1])
						dv2 = boundaries[1];
					
					s1.setDecisionVector(dv1);
					s2.setDecisionVector(dv2);
				}
				
				
			}
		}
		
		
	}

	@Override
	public void mutate(double upperBound, double lowerBound,
			double probabilityOfMutation, int distributionIndex,
			int nrOfGenerations) {
	
		
		
		SolutionBinaryCrossover solutionBinaryCrossover = new SolutionBinaryCrossover();
		SolutionMutation solutionMutation = new SolutionMutation();
		for(int i = 0; i<nrOfGenerations; i++){
			SolutionSet newSet = spawnNewSetThroughBinaryTournament();
			
			solutionBinaryCrossover.crossover(newSet);
			
			solutionMutation.mutateSet(newSet, upperBound, lowerBound, probabilityOfMutation, distributionIndex);
			
			
			mergeSets(newSet);
			//this.printFitness();
			
		}
		
		
		
		
		
	}
	
	
	private void mergeSets(SolutionSet newSet){
		
		solutions.addAll(newSet.getSolutions());
		FitnessEvaluator fitnessEvaluator = new FitnessEvaluator();
		Collections.sort(solutions);
		
		
		fitnessEvaluator.assignFitness(size, optimization, horizontalBoundary, verticalBoundary, this);
		
		
		
		List<Solution> dominatedSolutions = optimization.removeDominatedSolutions(solutions);
		
		Collections.shuffle(solutions);
		Collections.shuffle(dominatedSolutions);
	
		//za duzo niezdominowanych 
		if(solutions.size() > size){
			Collections.sort(solutions,new SolutionFitnessComparator());
			
			Iterator<Solution> it = solutions.iterator();
			while(solutions.size() > size){

				Solution x = it.next();
				it.remove();
				
			}
			
			
		}
		//za malo niezdominowanych
		else if(solutions.size() < size){
			
			Collections.sort(dominatedSolutions, new SolutionFitnessComparator());
			Iterator<Solution> it = dominatedSolutions.iterator();
			while(dominatedSolutions.size() > size - solutions.size()){
				it.next();
				it.remove();
				
				
			}
			solutions.addAll(dominatedSolutions);
		}
		
	}
	
	
	private class SolutionFitnessComparator implements Comparator<Solution>{

		@Override
		public int compare(Solution solution1, Solution solution2) {
			if(solution1.getFitness() > solution2.getFitness())
				return 1; 
			if(solution1.getFitness() < solution2.getFitness())
				return -1; 
			return 0; 
		}
		
	}


	@Override
	public void printFitness() {
		
		FitnessEvaluator fe = new FitnessEvaluator();
		fe.assignFitness(size, optimization, horizontalBoundary, verticalBoundary, this);
		double totalF = 0; 
		for(Solution solution : solutions){
			System.out.println("fitness: " + solution.getFitness() + " decission: " + solution.getDecisionVector());
			totalF+=solution.getFitness();
		}
		System.out.println("total " + totalF);
		
	}

	@Override
	public boolean equals(Object solutionsSet){
		
		if(!(solutionsSet instanceof ContinousSolutionsSet))
			return false;
		
		ContinousSolutionsSet css = (ContinousSolutionsSet)solutionsSet;
		
		List<Solution> anotherSet = css.getSolutions();
		for(Solution solution : solutions)
			if(!anotherSet.contains(solution))
				return false;
		
		if(anotherSet.size() != solutions.size())
			return false; 
		
		if(this.horizontalBoundary != css.horizontalBoundary)
			return false;
		if(this.verticalBoundary != css.verticalBoundary)
			return false; 
		
		return true; 
		
	}
	

}
