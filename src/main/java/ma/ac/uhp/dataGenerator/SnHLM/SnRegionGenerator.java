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
package ma.ac.uhp.dataGenerator.SnHLM;

import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataGenerator.util.Distribution;
import ma.ac.uhp.dataGenerator.util.Distributions;
import ma.ac.uhp.dataGenerator.util.TextPool;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

public class SnRegionGenerator
        implements Iterable<SnRegion>
{
    //private static final int COMMENT_AVERAGE_LENGTH = 72;

    private final Distributions distributions;
    private final TextPool textPool;

    public SnRegionGenerator()
    {
        this(Distributions.getDefaultDistributions(), TextPool.getDefaultTestPool());
    }

    public SnRegionGenerator(Distributions distributions, TextPool textPool)
    {
        this.distributions = checkNotNull(distributions, "distributions is null");
        this.textPool = checkNotNull(textPool, "textPool is null");
    }

    @Override
    public Iterator<SnRegion> iterator()
    {
        return new RegionGeneratorIterator(distributions.getRegions(), textPool);
    }

    public static class RegionGeneratorIterator
            extends AbstractIterator<SnRegion>
    {
        private final Distribution regions;

        private int index;

        private RegionGeneratorIterator(Distribution regions, TextPool textPool)
        {
            this.regions = regions;
        }

        @Override
        protected SnRegion computeNext()
        {
            if (index >= regions.size()) { return endOfData(); }
            
            SnRegion region = makeRegion(index); 

            index++;

            return region;
        }
        public SnRegion makeRegion(int index){
        	return new SnRegion(index,
                    index,
                    regions.getValue(index)); 
        }
    }
}
