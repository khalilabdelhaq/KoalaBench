package new_bench.SHLM;

import java.util.Iterator;

import new_bench.SnHLM.SnNation;
import new_bench.SnHLM.SnNationGenerator;
import new_bench.SnHLM.SnPart;
import new_bench.SnHLM.SnPartGenerator;
import new_bench.SnHLM.SnPartSupplier;
import new_bench.SnHLM.SnPartSupplierGenerator;
import new_bench.SnHLM.SnRegion;
import new_bench.SnHLM.SnRegionGenerator;
import new_bench.SnHLM.SnSupplier;
import new_bench.SnHLM.SnSupplierGenerator;
import new_bench.SnHLM.SnNationGenerator.NationGeneratorIterator;
import new_bench.SnHLM.SnPartGenerator.PartGeneratorIterator;
import new_bench.SnHLM.SnRegionGenerator.RegionGeneratorIterator;
import new_bench.SnHLM.SnSupplierGenerator.SupplierGeneratorIterator;
import new_bench.types.Entity;

import com.google.common.collect.AbstractIterator;

public class SHLMPartSupplierGenerator implements Iterable<Entity> {
	int scaleFactor; 
	int step = 1; int children = 1; // default values 

	public SHLMPartSupplierGenerator(int scaleFactor){this.scaleFactor = scaleFactor;}
    
    @Override
    public Iterator<Entity> iterator()
    {

        return new PartSupplierStarGeneratorIterator(scaleFactor); 
    }

    private static class PartSupplierStarGeneratorIterator
    extends AbstractIterator<Entity>
    {
    	int step = 1; int children = 1; 
		SupplierGeneratorIterator suppIt; 
		PartGeneratorIterator partIt; 
		Iterator<SnPartSupplier> partSuppIt; 
		NationGeneratorIterator natIt; 
		RegionGeneratorIterator regIt; 

		long index = 0; long rowCount = 0 ; 

    	public PartSupplierStarGeneratorIterator(int scaleFactor){ 

    		SnSupplierGenerator suppGen = new SnSupplierGenerator(scaleFactor, step, children);	
    		SnPartGenerator partGen = new SnPartGenerator(scaleFactor, step, children);
    		SnPartSupplierGenerator partSuppGen = new SnPartSupplierGenerator(scaleFactor, step, children);
    		SnNationGenerator natGen = new SnNationGenerator(); 
    		SnRegionGenerator regGen = new SnRegionGenerator();
    		
    		suppIt = (SupplierGeneratorIterator) suppGen.iterator();
    		partIt = (PartGeneratorIterator) partGen.iterator();
    		partSuppIt = partSuppGen.iterator();
    		natIt = (NationGeneratorIterator) natGen.iterator();
    		regIt = (RegionGeneratorIterator) regGen.iterator();

    		rowCount = SnPartGenerator.SCALE_BASE * scaleFactor; 
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (index >= rowCount) { return endOfData();  }
			SnPartSupplier partSupp = partSuppIt.next();
			SnPart part = partIt.makePart(partSupp.getPartKey(),index);  
			SnSupplier supp = suppIt.makeSupplier(partSupp.getSupplierKey(),index);
			SnNation n = natIt.makeNation((int)supp.getNationKey());
			SnRegion r = regIt.makeRegion((int)n.getRegionKey());
            index++;
            return new SHLMPartSupplier(index, part, supp, partSupp, n, r);
        }

    }    
    
	
	public static void main(String[] args) {
		SHLMPartSupplierGenerator psgen = new SHLMPartSupplierGenerator(1); 
		//psgen.testGeneration();
		System.out.println("-----"); 
		/*EntityPrinter p = new EntityPrinter();		
		PartSupplierStarGenerator gen = new PartSupplierStarGenerator(1);
		for (TpchEntity entity : gen) {
            System.out.println(entity.toJson()); 
        }*/		        	

		//p.print(custGen, "data_gen/star_partsupplier", "json"); 

	}

}
