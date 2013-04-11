import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class SolutionSetTest {
	
	
	private SolutionSet ss; 
	
	
	
	@Before
	public void before(){
	
		ss = new ContinousSolutionsSet(10, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
	
		ss.getSolutions().get(0).setDecisionVector(0.0);
		ss.getSolutions().get(1).setDecisionVector(0.1);
		ss.getSolutions().get(2).setDecisionVector(0.2);
		ss.getSolutions().get(3).setDecisionVector(0.3);
		ss.getSolutions().get(4).setDecisionVector(0.4);
		ss.getSolutions().get(5).setDecisionVector(0.5);
		ss.getSolutions().get(6).setDecisionVector(0.6);
		ss.getSolutions().get(7).setDecisionVector(0.7);
		ss.getSolutions().get(8).setDecisionVector(0.8);
		ss.getSolutions().get(9).setDecisionVector(0.9);

		
	}
	
	@Test
	public void test(){
		
		Random random = new Random(System.currentTimeMillis());
		int nr = Math.abs(random.nextInt())% ss.getSolutions().size();
		for(int i =0; i<ss.getSolutions().size(); i++ ){
			List<Solution> result = ss.getRandomSolutions(i, nr);
			result.add(ss.getSolutions().get(nr));
			for(int k = 0; k < result.size(); k++){
				for(int l = k + 1 ; l< result.size(); l++ ){
					if(k==l)
						fail("solutions are not distinct");
					
				}
					
			}
		}
		
	}
	

}
