package ma.ac.uhp.dataGenerator.SHLM;

//import static new_bench.util.GenerateUtils.calculateRowCount;
import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.SnHLM.SnLineItem;
import ma.ac.uhp.dataGenerator.SnHLM.SnLineItemGenerator;
import ma.ac.uhp.dataGenerator.SnHLM.SnLineItemGenerator.LineItemGeneratorIterator;
import ma.ac.uhp.dataGenerator.types.Entity;

public class SHLMLineItemGenerator implements Iterable<Entity> {
	int scaleFactor; 
	int step = 1; int children = 1; // default values 

	public SHLMLineItemGenerator(int scaleFactor){this.scaleFactor = scaleFactor;}
    
    @Override
    public Iterator<Entity> iterator()
    {
        return new StarLineItemGeneratorIterator(scaleFactor, step, children); 
    }

    private static class StarLineItemGeneratorIterator
    extends AbstractIterator<Entity>
    {
    	int step = 1; int children = 1; 
    	LineItemGeneratorIterator liIt;
		
    	public StarLineItemGeneratorIterator(int scaleFactor, int part, int partCount){ 

    		SnLineItemGenerator liGen = new SnLineItemGenerator(scaleFactor, step, children);

    		
    		liIt = (LineItemGeneratorIterator) liGen.iterator();
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (!liIt.hasNext()) return endOfData();

            SnLineItem li = liIt.next(); 
            return new SHLMLineItem(li);
        }
    }    
    	
	public static void main(String[] args) {
		SHLMLineItemGenerator gen = new SHLMLineItemGenerator(1); 
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
		//System.out.println("rowCount: "+ calculateRowCount(OrderGenerator.SCALE_BASE, 1, 1, 1));
	}

}
