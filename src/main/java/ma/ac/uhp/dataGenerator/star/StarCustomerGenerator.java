package ma.ac.uhp.dataGenerator.star;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.snow.Customer;
import ma.ac.uhp.dataGenerator.snow.CustomerGenerator;
import ma.ac.uhp.dataGenerator.snow.Nation;
import ma.ac.uhp.dataGenerator.snow.NationGenerator;
import ma.ac.uhp.dataGenerator.snow.Region;
import ma.ac.uhp.dataGenerator.snow.RegionGenerator;
import ma.ac.uhp.dataGenerator.snow.CustomerGenerator.CustomerGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.NationGenerator.NationGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.RegionGenerator.RegionGeneratorIterator;
import ma.ac.uhp.dataGenerator.types.Entity;

public class StarCustomerGenerator implements Iterable<Entity> {
	int scaleFactor; 
	int step = 1; int children = 1; // default values 

	public StarCustomerGenerator(int scaleFactor){this.scaleFactor = scaleFactor;}
    
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

    		CustomerGenerator cGen = new CustomerGenerator(scaleFactor, step, children);
    		NationGenerator nGen = new NationGenerator();
    		RegionGenerator rGen = new RegionGenerator();
    		    		
    		cIt = (CustomerGeneratorIterator) cGen.iterator();
    		nIt = (NationGeneratorIterator) nGen.iterator();
    		rIt = (RegionGeneratorIterator) rGen.iterator();
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (!cIt.hasNext()) return endOfData();

            Customer cust = cIt.next(); 
            Nation nat = nIt.makeNation((int) cust.getNationKey());
            Region reg = rIt.makeRegion((int) nat.getRegionKey());
            return new StarCustomer(cust, nat.getName(), reg.getName());
        }
    }    
    	
	public static void main(String[] args) {
		StarCustomerGenerator gen = new StarCustomerGenerator(1); 
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
