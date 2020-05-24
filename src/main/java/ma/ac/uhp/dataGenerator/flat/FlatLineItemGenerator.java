package ma.ac.uhp.dataGenerator.flat;

import static ma.ac.uhp.dataGenerator.util.GenerateUtils.calculateRowCount;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.snow.Customer;
import ma.ac.uhp.dataGenerator.snow.CustomerGenerator;
import ma.ac.uhp.dataGenerator.snow.LineItem;
import ma.ac.uhp.dataGenerator.snow.LineItemGenerator;
import ma.ac.uhp.dataGenerator.snow.Nation;
import ma.ac.uhp.dataGenerator.snow.NationGenerator;
import ma.ac.uhp.dataGenerator.snow.Order;
import ma.ac.uhp.dataGenerator.snow.OrderGenerator;
import ma.ac.uhp.dataGenerator.snow.Part;
import ma.ac.uhp.dataGenerator.snow.PartGenerator;
import ma.ac.uhp.dataGenerator.snow.Region;
import ma.ac.uhp.dataGenerator.snow.RegionGenerator;
import ma.ac.uhp.dataGenerator.snow.Supplier;
import ma.ac.uhp.dataGenerator.snow.SupplierGenerator;
import ma.ac.uhp.dataGenerator.snow.CustomerGenerator.CustomerGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.LineItemGenerator.LineItemGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.NationGenerator.NationGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.OrderGenerator.OrderGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.PartGenerator.PartGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.RegionGenerator.RegionGeneratorIterator;
import ma.ac.uhp.dataGenerator.snow.SupplierGenerator.SupplierGeneratorIterator;
import ma.ac.uhp.dataGenerator.types.Entity;

public class FlatLineItemGenerator implements Iterable<Entity> {
	double scaleFactor; 
	int step = 1; int children = 1; // default values 

	public FlatLineItemGenerator(double scaleFactor){this.scaleFactor = scaleFactor;}
    
    @Override
    public Iterator<Entity> iterator()
    {

        return new FlatLineItemGeneratorIterator(scaleFactor, step, children); 
    }

    private static class FlatLineItemGeneratorIterator
    extends AbstractIterator<Entity>
    {
    	int step = 1; int children = 1; 
    	LineItemGeneratorIterator liIt;
    	OrderGeneratorIterator oIt;
    	SupplierGeneratorIterator suppIt; 
		PartGeneratorIterator partIt; 
		//Iterator<PartSupplier> partSuppIt; 
		NationGeneratorIterator s_natIt; 
		RegionGeneratorIterator s_regIt; 
		NationGeneratorIterator c_natIt; 
		RegionGeneratorIterator c_regIt; 
		CustomerGeneratorIterator custIt;
		
    	public FlatLineItemGeneratorIterator(double scaleFactor, int part, int partCount){ 

    		LineItemGenerator liGen = new LineItemGenerator(scaleFactor, step, children);
    		OrderGenerator oGen = new OrderGenerator(scaleFactor, step, children);
    		CustomerGenerator cGen = new CustomerGenerator(scaleFactor, step, children);
    		SupplierGenerator suppGen = new SupplierGenerator(scaleFactor, step, children);	
    		PartGenerator partGen = new PartGenerator(scaleFactor, step, children);
    		//PartSupplierGenerator partSuppGen = new PartSupplierGenerator(scaleFactor, step, children);
    		NationGenerator s_natGen = new NationGenerator(); 
    		RegionGenerator s_regGen = new RegionGenerator();
    		NationGenerator c_natGen = new NationGenerator(); 
    		RegionGenerator c_regGen = new RegionGenerator();

    		
    		liIt = (LineItemGeneratorIterator) liGen.iterator();
    		oIt = (OrderGeneratorIterator) oGen.iterator();
    		
    		suppIt = (SupplierGeneratorIterator) suppGen.iterator();
    		partIt = (PartGeneratorIterator) partGen.iterator();
    		//partSuppIt = partSuppGen.iterator();
    		s_natIt = (NationGeneratorIterator) s_natGen.iterator();
    		s_regIt = (RegionGeneratorIterator) s_regGen.iterator();

    		custIt = (CustomerGeneratorIterator) cGen.iterator(); 
    		c_natIt = (NationGeneratorIterator) c_natGen.iterator();
    		c_regIt = (RegionGeneratorIterator) c_regGen.iterator();

    		//rowCount = calculateRowCount(OrderGenerator.SCALE_BASE, scaleFactor, part, partCount); 
    	}
    	
        @Override
        protected Entity computeNext()
        {
                	
            if (!liIt.hasNext()) return endOfData();

            LineItem li = liIt.next(); 
            Order o = oIt.makeOrder(li.getOrderKey());  
            Customer cust = custIt.makeCustomer(o.getCustomerKey()); 
            Nation c_nat = c_natIt.makeNation((int) cust.getNationKey());
            Region c_reg = c_regIt.makeRegion((int) c_nat.getRegionKey());
            
            Part part = partIt.makePart(li.getPartKey()); 
            Supplier supp = suppIt.makeSupplier(li.getSupplierKey());
         			
			Nation s_nat = s_natIt.makeNation((int)supp.getNationKey());
			Region s_reg = s_regIt.makeRegion((int)s_nat.getRegionKey()); 

            return new FlatLineItem(li.getRowNumber(), li, o, cust, c_nat, c_reg, part, supp, s_nat, s_reg);
        }
    }    
    	
	public static void main(String[] args) {
		// simple test
		FlatLineItemGenerator gen = new FlatLineItemGenerator(1); 
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
		System.out.println("rowCount: "+ calculateRowCount(OrderGenerator.SCALE_BASE, 1, 1, 1));
	}

}
