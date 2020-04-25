/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package new_bench.SnHLM;

import com.google.common.collect.AbstractIterator;

import java.util.Iterator;
import java.util.Random;

import new_bench.util.RandomBoundedInt;
import new_bench.util.RandomText;
import new_bench.util.TextPool;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static new_bench.util.GenerateUtils.calculateRowCount;
import static new_bench.util.GenerateUtils.calculateStartIndex;

public class SnPartSupplierGenerator
        implements Iterable<SnPartSupplier>
{
    private static final int SUPPLIERS_PER_PART = 4;

    private static final int AVAILABLE_QUANTITY_MIN = 1;
    private static final int AVAILABLE_QUANTITY_MAX = 9999;

    private static final int SUPPLY_COST_MIN = 100;
    private static final int SUPPLY_COST_MAX = 100000;

    private static final int COMMENT_AVERAGE_LENGTH = 124;

    private final double scaleFactor;
    private final int part;
    private final int partCount;

    private final TextPool textPool;

    public SnPartSupplierGenerator(double scaleFactor, int part, int partCount)
    {
        this(scaleFactor, part, partCount, TextPool.getDefaultTestPool());
    }

    public SnPartSupplierGenerator(double scaleFactor, int part, int partCount, TextPool textPool)
    {
        checkArgument(scaleFactor > 0, "scaleFactor must be greater than 0");
        checkArgument(part >= 1, "part must be at least 1");
        checkArgument(part <= partCount, "part must be less than or equal to part count");

        this.scaleFactor = scaleFactor;
        this.part = part;
        this.partCount = partCount;

        this.textPool = checkNotNull(textPool, "textPool is null");
    }

    @Override
    public Iterator<SnPartSupplier> iterator()
    {
        return new PartSupplierGeneratorIterator(
                textPool,
                scaleFactor,
                calculateStartIndex(SnPartGenerator.SCALE_BASE, scaleFactor, part, partCount),
                calculateRowCount(SnPartGenerator.SCALE_BASE, scaleFactor, part, partCount));
    }

    private static class PartSupplierGeneratorIterator
            extends AbstractIterator<SnPartSupplier>
    {
        private final double scaleFactor;
        private final long startIndex;
        private final long rowCount;

        private final RandomBoundedInt availableQuantityRandom;
        private final RandomBoundedInt supplyCostRandom;
        private final RandomText commentRandom;

        private long index;
        private int partSupplierNumber;

        private PartSupplierGeneratorIterator(TextPool textPool, double scaleFactor, long startIndex, long rowCount)
        {
            this.scaleFactor = scaleFactor;
            this.startIndex = startIndex;
            this.rowCount = rowCount;

            availableQuantityRandom = new RandomBoundedInt( AVAILABLE_QUANTITY_MIN, AVAILABLE_QUANTITY_MAX);
            supplyCostRandom = new RandomBoundedInt(SUPPLY_COST_MIN, SUPPLY_COST_MAX);
            commentRandom = new RandomText(textPool, COMMENT_AVERAGE_LENGTH);
        }

        @Override
        protected SnPartSupplier computeNext()
        {
            if (index >= rowCount) {
                return endOfData();
            }

            SnPartSupplier partSupplier = makePartSupplier(startIndex + index + 1);
            partSupplierNumber++;

            // advance next row only when all lines for the order have been produced
            if (partSupplierNumber >= SUPPLIERS_PER_PART) {
                index++;
                partSupplierNumber = 0;
            }

            return partSupplier;
        }

        private SnPartSupplier makePartSupplier(long partKey)
        {
        	Random rnd = new Random(213099);  long rndNum = (long) (partKey * rnd.nextFloat() + partSupplierNumber*rnd.nextFloat());   
            return new SnPartSupplier(partKey,
                    partKey,
                    selectPartSupplier(partKey, partSupplierNumber, scaleFactor),
                    availableQuantityRandom.getValue(rndNum),
                    supplyCostRandom.getValue(rndNum),
                    commentRandom.getValue(rndNum));
        }
    }

    static long selectPartSupplier(long partKey, long supplierNumber, double scaleFactor)
    {
        long supplierCount = (long) (SnSupplierGenerator.SCALE_BASE * scaleFactor);
        return ((partKey + (supplierNumber * ((supplierCount / SUPPLIERS_PER_PART) + ((partKey - 1) / supplierCount)))) % supplierCount) + 1;
    }

    public static void main(String[] argz){
    	// simple test
    	SnPartSupplierGenerator gen = new SnPartSupplierGenerator(1,1,1);
    	int i=0;
    	for (SnPartSupplier x: gen){
    		System.out.println(x.toJson(null));
    		i++; if (i>4) break; 
    	}
		
    	System.out.println();
    	
    	PartSupplierGeneratorIterator it = (PartSupplierGeneratorIterator) gen.iterator();
		System.out.println(it.makePartSupplier(1).toJson(null)); 
    }
    
}
