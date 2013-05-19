import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SetSelection;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class SetSelectionTest {
	private SolutionSet ss1; 
	private SolutionSet ss2;  
	
	private SolutionSet ss3; 
	private SolutionSet ss4;
	
	@Before
	public void before(){
	
		ss1 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector()[0];
				objV[1] = 1 - solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},Optimization.MAXIMALIZATION,0,0,1);
		
		ss1.getSolutions().get(0).setDecisionVector(new double[] { 0.1 });
		ss1.getSolutions().get(1).setDecisionVector(new double[] { 0.1 });
		ss1.getSolutions().get(2).setDecisionVector(new double[] { 0.1 });
		
		ss2 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector()[0];
				objV[1] = 1 - solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},Optimization.MAXIMALIZATION,0,0,1);
		
		ss2.getSolutions().get(0).setDecisionVector(new double[] { 0.75 });
		ss2.getSolutions().get(1).setDecisionVector(new double[] { 0.8 });
		ss2.getSolutions().get(2).setDecisionVector(new double[] { 0.9 });
		
		ss3 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector()[0];
				objV[1] = 1 - solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},Optimization.MINIMIZATION,1,1,1);
		
		ss3.getSolutions().get(0).setDecisionVector(new double[] { 0.2 });
		ss3.getSolutions().get(1).setDecisionVector(new double[] { 0.3 });
		ss3.getSolutions().get(2).setDecisionVector(new double[] { 0.4 });
		
		ss4 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector()[0];
				objV[1] = 1 - solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},Optimization.MINIMIZATION,1,1,1);
		
		ss4.getSolutions().get(0).setDecisionVector(new double[] { 0.5 });
		ss4.getSolutions().get(1).setDecisionVector(new double[] { 0.8 });
		ss4.getSolutions().get(2).setDecisionVector(new double[] { 0.9 });
	}
	
	
	@Test
	public void testSelection1(){
		List<SolutionSet> list = new ArrayList<SolutionSet>();
		list.add(ss1);
		list.add(ss2);
		
		SetSelection setSelection = new SetSelection();
		
		List<SolutionSet> result = 
				setSelection.doMatingAndEnvironmentalSelection(list, 0, 0, Optimization.MAXIMALIZATION);
		
		for(SolutionSet ss : result){
			System.out.println("solutions set");
			for(Solution solution : ss.getSolutions())
				System.out.println(solution.getDecisionVector()[0]);
		}
		
		FitnessEvaluator fe = new FitnessEvaluator(); 
		System.out.println("------------------");
		System.out.println("f2 " + fe.computeTotalFitness(result.get(1).getSolutions(), 0, 0, Optimization.MAXIMALIZATION, -1));
		System.out.println("------------------");
	}
	
	@Test
	public void testSelection2(){
		List<SolutionSet> list = new ArrayList<SolutionSet>();
		list.add(ss3);
		list.add(ss4);
		
		SetSelection setSelection = new SetSelection();
		
		List<SolutionSet> result = 
				setSelection.doMatingAndEnvironmentalSelection(list,1,1, Optimization.MINIMIZATION);
		
		for(SolutionSet ss : result){
			System.out.println("solutions set");
			for(Solution solution : ss.getSolutions())
				System.out.println(solution.getDecisionVector()[0]);
		}
		
		
	}

}
