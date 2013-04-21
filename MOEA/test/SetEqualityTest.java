import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class SetEqualityTest {
	
	private SolutionSet set;
	
	@Before
	public void prepareSets(){
		set = new ContinousSolutionsSet(30, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = 0.4;
				objV[1] = 0.4;
				return objV;
				 
			}
		},Optimization.MAXIMALIZATION,0,0);
	}
	
	
	@Test
	public void testEquality(){
		
		SolutionSet anotherSet = set.getSetCopy();
		if(!anotherSet.equals(set))
			fail("Sets should stay equal");
		
		
		anotherSet.getSolutions().get(0).setDecisionVector(anotherSet.getSolutions().get(0).getDecisionVector()/2.0);
		if(anotherSet.equals(set))
			fail("Sets are diffrent");
		
		set.getSolutions().get(0).setDecisionVector(set.getSolutions().get(0).getDecisionVector()/2.0);
		if(!anotherSet.equals(set))
			fail("Sets should stay equal");
	}
	

}
