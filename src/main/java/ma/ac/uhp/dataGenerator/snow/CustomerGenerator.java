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
package ma.ac.uhp.dataGenerator.snow;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.util.Distributions;
import ma.ac.uhp.dataGenerator.util.RandomAlphaNumeric;
import ma.ac.uhp.dataGenerator.util.RandomBoundedInt;
import ma.ac.uhp.dataGenerator.util.RandomPhoneNumber;
import ma.ac.uhp.dataGenerator.util.RandomString;
import ma.ac.uhp.dataGenerator.util.TextPool;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Locale.ENGLISH;
import static ma.ac.uhp.dataGenerator.util.GenerateUtils.calculateRowCount;
import static ma.ac.uhp.dataGenerator.util.GenerateUtils.calculateStartIndex;

public class CustomerGenerator
        implements Iterable<Customer>
{
    public static final int SCALE_BASE = 150_000;
    private static final int ACCOUNT_BALANCE_MIN = -99999;
    private static final int ACCOUNT_BALANCE_MAX = 999999;
    private static final int ADDRESS_AVERAGE_LENGTH = 25;
    private static final int ORDER_KEY_SPARSE_BITS = 2;
    private static final int ORDER_KEY_SPARSE_KEEP = 3;
    //private static final int COMMENT_AVERAGE_LENGTH = 73;

    private final double scaleFactor;
    private final int part;
    private final int partCount;

    private final Distributions distributions;
    private final TextPool textPool;

    public CustomerGenerator(double scaleFactor, int part, int partCount)
    {
        this(scaleFactor, part, partCount, Distributions.getDefaultDistributions(), TextPool.getDefaultTestPool());
    }

    public CustomerGenerator(double scaleFactor, int part, int partCount, Distributions distributions, TextPool textPool)
    {
        checkArgument(scaleFactor > 0, "scaleFactor must be greater than 0");
        checkArgument(part >= 1, "part must be at least 1");
        checkArgument(part <= partCount, "part must be less than or equal to part count");

        this.scaleFactor = scaleFactor;
        this.part = part;
        this.partCount = partCount;

        this.distributions = checkNotNull(distributions, "distributions is null");
        this.textPool = checkNotNull(textPool, "textPool is null");
    }

    @Override
    public Iterator<Customer> iterator()
    {

        return new CustomerGeneratorIterator(
                distributions,
                textPool,
                calculateStartIndex(SCALE_BASE, scaleFactor, part, partCount),
                calculateRowCount(SCALE_BASE, scaleFactor, part, partCount));
    }

    public static class CustomerGeneratorIterator
            extends AbstractIterator<Customer>
    {
        private final RandomAlphaNumeric addressRandom = new RandomAlphaNumeric(881155353, ADDRESS_AVERAGE_LENGTH);
        private final RandomBoundedInt nationKeyRandom;
        private final RandomPhoneNumber phoneRandom = new RandomPhoneNumber();
        private final RandomBoundedInt accountBalanceRandom = new RandomBoundedInt(ACCOUNT_BALANCE_MIN, ACCOUNT_BALANCE_MAX);
        private final RandomString marketSegmentRandom;

        private final long startIndex;
        private final long rowCount;

        private long index=1;

        public CustomerGeneratorIterator(Distributions distributions, TextPool textPool, long startIndex, long rowCount)
        {
            this.startIndex = startIndex;
            this.rowCount = rowCount;

            nationKeyRandom = new RandomBoundedInt(0, distributions.getNations().size() - 1);
            marketSegmentRandom = new RandomString(distributions.getMarketSegments());
        }

        @Override
        public Customer computeNext()
        {
            if (index > rowCount) { return endOfData();  }
            Customer customer = makeCustomer(startIndex + index + 1);
            index++;
            return customer;
        }

        public Customer makeCustomer(long customerKey)
        {
            long nationKey = nationKeyRandom.getValue(customerKey);

            return new Customer(customerKey,
                    customerKey,
                    String.format(ENGLISH, "Customer#%09d", customerKey),
                    addressRandom.getValue(customerKey),
                    nationKey,
                    phoneRandom.getValue(customerKey, nationKey),
                    accountBalanceRandom.getValue(customerKey),
                    marketSegmentRandom.getValue(customerKey)       );
        }
    }
    
    static long makeCustomerKey(long orderIndex)
    {
        long low_bits = orderIndex & ((1 << ORDER_KEY_SPARSE_KEEP) - 1);

        long ok = orderIndex;
        ok = ok >> ORDER_KEY_SPARSE_KEEP;
        ok = ok << ORDER_KEY_SPARSE_BITS;
        ok = ok << ORDER_KEY_SPARSE_KEEP;
        ok += low_bits;

        return ok;
    }
    
    public static void main(String[] argz){
    	CustomerGenerator gen = new CustomerGenerator(1,1,1);
    	int i=0;
    	for (Customer cust: gen){
    		System.out.println(cust.toJson(null));
    		i++; if (i>4) break; 
    	}
		
    	System.out.println();
    	
		CustomerGeneratorIterator it = (CustomerGeneratorIterator) gen.iterator();
		System.out.println(it.makeCustomer(1).toJson(null)); 

    }
}
