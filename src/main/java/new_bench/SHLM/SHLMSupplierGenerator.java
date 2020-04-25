package new_bench.SHLM;

import java.util.Iterator;

import new_bench.SnHLM.SnSupplier;
import new_bench.SnHLM.SnSupplierGenerator;
import new_bench.SnHLM.SnSupplierGenerator.SupplierGeneratorIterator;
import new_bench.SnHLM.SnNation;
import new_bench.SnHLM.SnNationGenerator;
import new_bench.SnHLM.SnRegion;
import new_bench.SnHLM.SnRegionGenerator;
import new_bench.SnHLM.SnNationGenerator.NationGeneratorIterator;
import new_bench.SnHLM.SnRegionGenerator.RegionGeneratorIterator;
import new_bench.types.Entity;

import com.google.common.collect.AbstractIterator;

public class SHLMSupplierGenerator implements Iterable<Entity> {
	int scaleFactor; 
	int step = 1; int children = 1; // default values 

	public SHLMSupplierGenerator(int scaleFactor){this.scaleFactor = scaleFactor;}
    
    @Override
    public Iterator<Entity> iterator()
    {
        return new StarSupplierGeneratorIterator(scaleFactor, step, children); 
    }

    private static class StarSupplierGeneratorIterator
    extends AbstractIterator<Entity>
    {
    	int step = 1; int children = 1; 
    	SupplierGeneratorIterator sIt;
    	NationGeneratorIterator nIt;
    	RegionGeneratorIterator rIt;
		
    	public StarSupplierGeneratorIterator(int scaleFactor, int part, int partCount){ 

    		SnSupplierGenerator sGen = new SnSupplierGenerator(scaleFactor, step, children);
    		SnNationGenerator nGen = new SnNationGenerator();
    		SnRegionGenerator rGen = new SnRegionGenerator();
    		    		
    		sIt = (SupplierGeneratorIterator) sGen.iterator();
    		nIt = (NationGeneratorIterator) nGen.iterator();
    		rIt = (RegionGeneratorIterator) rGen.iterator();
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (!sIt.hasNext()) return endOfData();

            SnSupplier supp = sIt.next(); 
            SnNation nat = nIt.makeNation((int) supp.getNationKey());
            SnRegion reg = rIt.makeRegion((int) nat.getRegionKey());
            return new SHLMSupplier(supp, nat.getName(), reg.getName());
        }
    }    
    	
	public static void main(String[] args) {
		SHLMSupplierGenerator gen = new SHLMSupplierGenerator(1); 
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
