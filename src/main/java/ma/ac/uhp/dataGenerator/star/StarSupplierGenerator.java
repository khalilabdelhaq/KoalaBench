package ma.ac.uhp.dataGenerator.star;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.snow.Nation;
import ma.ac.uhp.dataGenerator.snow.NationGenerator;
import ma.ac.uhp.dataGenerator.snow.Region;
import ma.ac.uhp.dataGenerator.snow.RegionGenerator;
import ma.ac.uhp.dataGenerator.snow.Supplier;
import ma.ac.uhp.dataGenerator.snow.SupplierGenerator;
import ma.ac.uhp.dataGenerator.snow.NationGenerator.NationGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.RegionGenerator.RegionGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.SupplierGenerator.SupplierGeneratorIterator;
import ma.ac.uhp.dataGenerator.types.Entity;

public class StarSupplierGenerator implements Iterable<Entity> {
	int scaleFactor; 
	int step = 1; int children = 1; // default values 

	public StarSupplierGenerator(int scaleFactor){this.scaleFactor = scaleFactor;}
    
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

    		SupplierGenerator sGen = new SupplierGenerator(scaleFactor, step, children);
    		NationGenerator nGen = new NationGenerator();
    		RegionGenerator rGen = new RegionGenerator();
    		    		
    		sIt = (SupplierGeneratorIterator) sGen.iterator();
    		nIt = (NationGeneratorIterator) nGen.iterator();
    		rIt = (RegionGeneratorIterator) rGen.iterator();
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (!sIt.hasNext()) return endOfData();

            Supplier supp = sIt.next(); 
            Nation nat = nIt.makeNation((int) supp.getNationKey());
            Region reg = rIt.makeRegion((int) nat.getRegionKey());
            return new StarSupplier(supp, nat.getName(), reg.getName());
        }
    }    
    	
	public static void main(String[] args) {
		StarSupplierGenerator gen = new StarSupplierGenerator(1); 
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
