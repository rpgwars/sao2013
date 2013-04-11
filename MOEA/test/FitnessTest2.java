import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class FitnessTest2 {
	
	private SolutionSet ss; 
	private FitnessEvaluator fe; 
	
	private SolutionSet ss2; 
	private SolutionSet ss3;
	
	
	@Before
	public void before(){
	
		ss = new ContinousSolutionsSet(2, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
		
		fe = new FitnessEvaluator();
		ss.getSolutions().get(0).setDecisionVector(0.2);
		ss.getSolutions().get(1).setDecisionVector(0.6);
		
		
		ss2 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
		
		
		ss2.getSolutions().get(0).setDecisionVector(0.1);
		ss2.getSolutions().get(1).setDecisionVector(0.5);
		ss2.getSolutions().get(2).setDecisionVector(0.7);
		
		ss3 = new ContinousSolutionsSet(5, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
		
		
		ss3.getSolutions().get(0).setDecisionVector(0.5);
		ss3.getSolutions().get(1).setDecisionVector(0.5);
		ss3.getSolutions().get(2).setDecisionVector(0.5);
		ss3.getSolutions().get(3).setDecisionVector(0.5);
		ss3.getSolutions().get(4).setDecisionVector(0.5);
		
		
	}
	
	
	@Test
	public void fitnessTestMinimization(){
		
		fe.assignFitness(ss.getSolutions().size(), Optimization.MINIMIZATION, 1, 1, ss);
		assertEquals(ss.getSolutions().get(0).getFitness(),0.32,0.00001);
		assertEquals(ss.getSolutions().get(1).getFitness(),0.40,0.00001);
		
		fe.computeTotalFitness(ss.getSolutions(), 1, 1, Optimization.MINIMIZATION);
	}
	
	
	@Test
	public void fitnessTestMaximization(){
		
		fe.assignFitness(ss.getSolutions().size(), Optimization.MAXIMALIZATION, 0, 0, ss);
		assertEquals(ss.getSolutions().get(0).getFitness(),0.32,0.00001);
		assertEquals(ss.getSolutions().get(1).getFitness(),0.40,0.00001);
		
		fe.computeTotalFitness(ss.getSolutions(), 0, 0, Optimization.MAXIMALIZATION);
	}
	
	
	@Test
	public void fitnessTestMaximization2(){
		
		fe.assignFitness(ss2.getSolutions().size(), Optimization.MAXIMALIZATION, 0, 0, ss2);
		boolean test = Math.abs(ss2.getSolutions().get(0).getFitness() - 0.341666)<0.0001 || Math.abs(ss2.getSolutions().get(0).getFitness() - 0.351666)<0.0001;
		boolean test2 = Math.abs(ss2.getSolutions().get(1).getFitness() - 0.511666)<0.0001 || Math.abs(ss2.getSolutions().get(1).getFitness() - 0.521666)<0.0001;
		boolean test3 = Math.abs(ss2.getSolutions().get(2).getFitness() - 0.481666)<0.0001 || Math.abs(ss2.getSolutions().get(2).getFitness() - 0.461666)<0.0001;
		assertEquals(true, test && test2 && test3);
		
		fe.computeTotalFitness(ss2.getSolutions(), 0, 0, Optimization.MAXIMALIZATION);
	}
	
	@Test
	public void fitnessTestMinimization2(){
		
		fe.assignFitness(ss2.getSolutions().size(), Optimization.MINIMIZATION, 1, 1, ss2);
		boolean test = Math.abs(ss2.getSolutions().get(0).getFitness() - 0.341666)<0.0001 || Math.abs(ss2.getSolutions().get(0).getFitness() - 0.351666)<0.0001;
		boolean test2 = Math.abs(ss2.getSolutions().get(1).getFitness() - 0.511666)<0.0001 || Math.abs(ss2.getSolutions().get(1).getFitness() - 0.521666)<0.0001;
		boolean test3 = Math.abs(ss2.getSolutions().get(2).getFitness() - 0.481666)<0.0001 || Math.abs(ss2.getSolutions().get(2).getFitness() - 0.461666)<0.0001;
		assertEquals(true, test && test2 && test3);
		
		fe.computeTotalFitness(ss2.getSolutions(), 1, 1, Optimization.MINIMIZATION);
	}
	
	//@Test
	public void fitnessTestRemovingDominatedMamimization(){
			
		fe.assignFitness(ss3.getSolutions().size(), Optimization.MAXIMALIZATION, 0, 0, ss3);
	}

}
