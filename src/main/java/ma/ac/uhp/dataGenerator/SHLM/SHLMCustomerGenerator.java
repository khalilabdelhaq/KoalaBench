package ma.ac.uhp.dataGenerator.SHLM;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.SnHLM.SnCustomer;
import ma.ac.uhp.dataGenerator.SnHLM.SnCustomerGenerator;
import ma.ac.uhp.dataGenerator.SnHLM.SnNation;
import ma.ac.uhp.dataGenerator.SnHLM.SnNationGenerator;
import ma.ac.uhp.dataGenerator.SnHLM.SnRegion;
import ma.ac.uhp.dataGenerator.SnHLM.SnRegionGenerator;
import ma.ac.uhp.dataGenerator.SnHLM.SnCustomerGenerator.CustomerGeneratorIterator;
import ma.ac.uhp.dataGenerator.SnHLM.SnNationGenerator.NationGeneratorIterator;
import ma.ac.uhp.dataGenerator.SnHLM.SnRegionGenerator.RegionGeneratorIterator;
import ma.ac.uhp.dataGenerator.types.Entity;

public class SHLMCustomerGenerator implements Iterable<Entity> {
	int scaleFactor; 
	int step = 1; int children = 1; // default values 

	public SHLMCustomerGenerator(int scaleFactor){this.scaleFactor = scaleFactor;}
    
    @Override
    public Iterator<Entity> iterator()
    {
        return new StarCustomerGeneratorIterator(scaleFactor, step, children); 
    }

    private static class StarCustomerGeneratorIterator
    extends AbstractIterator<Entity>
    {
    	int step = 1; int children = 1; 
    	CustomerGeneratorIterator cIt;
    	NationGeneratorIterator nIt;
    	RegionGeneratorIterator rIt;
		
    	public StarCustomerGeneratorIterator(int scaleFactor, int part, int partCount){ 

    		SnCustomerGenerator cGen = new SnCustomerGenerator(scaleFactor, step, children);
    		SnNationGenerator nGen = new SnNationGenerator();
    		SnRegionGenerator rGen = new SnRegionGenerator();
    		    		
    		cIt = (CustomerGeneratorIterator) cGen.iterator();
    		nIt = (NationGeneratorIterator) nGen.iterator();
    		rIt = (RegionGeneratorIterator) rGen.iterator();
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (!cIt.hasNext()) return endOfData();

            SnCustomer cust = cIt.next(); 
            SnNation nat = nIt.makeNation((int) cust.getNationKey());
            SnRegion reg = rIt.makeRegion((int) nat.getRegionKey());
            return new SHLMCustomer(cust, nat.getName(), reg.getName());
        }
    }    
    	
	public static void main(String[] args) {
		SHLMCustomerGenerator gen = new SHLMCustomerGenerator(1); 
		System.out.println("-----"); 
		int count = 0; 
		//EntityPrinter p = new EntityPrinter();		
		for (Entity entity : gen) {
            System.out.println(entity.toJson(null));
			//long rnum = entity.getRowNumber();
            //if (rnum%100000 ==0) System.out.println(rnum);
            //p.print(custGen, "star_partsupplier", "json");
            count++; 
            if (count>200) break; 
        }		        	
	}

}
