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
import ma.ac.uhp.dataGenerator.util.RandomText;
import ma.ac.uhp.dataGenerator.util.TextPool;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

public class SnNationGenerator
        implements Iterable<SnNation>
{
    private static final int COMMENT_AVERAGE_LENGTH = 72;

    private final Distributions distributions;
    private final TextPool textPool;

    public SnNationGenerator()
    {
        this(Distributions.getDefaultDistributions(), TextPool.getDefaultTestPool());
    }

    public SnNationGenerator(Distributions distributions, TextPool textPool)
    {
        this.distributions = checkNotNull(distributions, "distributions is null");
        this.textPool = checkNotNull(textPool, "textPool is null");
    }

    @Override
    public Iterator<SnNation> iterator()
    {
        return new NationGeneratorIterator(distributions.getNations(), textPool);
    }

    public static class NationGeneratorIterator
            extends AbstractIterator<SnNation>
    {
        private final Distribution nations;
        private final RandomText commentRandom;

        private int index;

        private NationGeneratorIterator(Distribution nations, TextPool textPool)
        {
            this.nations = nations;
            this.commentRandom = new RandomText( textPool, COMMENT_AVERAGE_LENGTH);
        }

        @Override
        protected SnNation computeNext()
        {
            if (index >= nations.size()) {return endOfData();}

            SnNation nation = makeNation(index); 
            index++;

            return nation;
        }
        
        public SnNation makeNation(int index){
        	return new SnNation(index,
                    index,
                    nations.getValue(index),
                    nations.getWeight(index),
                    commentRandom.getValue(index));
        }
    }
}
