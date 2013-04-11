package pl.agh.edu.moea.set;

import java.util.List;

import pl.agh.edu.moea.main.Optimization;

public interface SolutionSet {
	
	
	List<Solution> getSolutions();
	List<Solution> getRandomSolutions(int nrOfSolutionsToRemove, int nrOfSolutionWithoutFitness);
	SolutionSet spawnNewSetThroughBinaryTournament(); 
	void doBinaryCrossover(double probability, double[] boundaries);
	void mutate(double upperBoud, double lowerBound, double probabilityOfMutation, int distributionIndex, int nrOfGenerations);
	public void printFitness();
	

}
//http://www.google.pl/url?sa=t&rct=j&q=&esrc=s&source=web&cd=4&ved=0CEgQFjAD&url=http%3A%2F%2Fwww.cai.sk%2Fojs%2Findex.php%2Fcai%2Farticle%2Fdownload%2F113%2F95&ei=ihdOUcjADYbXPff8gKAE&usg=AFQjCNGBVAl1VTzrlEdTl-KeS6eEOFQWdw&sig2=IH-kLEuhtv_uRx-nQaulfA&bvm=bv.44158598,d.ZWU