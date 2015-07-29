package edu.northwestern.at.morphadorner.corpuslinguistics.lexicon;

/*  Please see the license information at the end of this file. */

import java.util.*;
import java.io.*;
import java.net.URL;

import edu.northwestern.at.utils.IsCloseable;
import edu.northwestern.at.morphadorner.corpuslinguistics.partsofspeech.*;

/** PennTreebankLexicon: Suffix lexicon which uses Penn Treebank tags.
 */

public class PennTreebankSuffixLexicon
    extends AbstractLexicon
    implements Lexicon, IsCloseable
{
    /** Resourch path to Penn Treebank lexicon. */

    protected static final String pennTreebankSuffixLexiconPath =
        "resources/pennsuffix.lex";

    /** Create an empty lexicon.
     */

    public PennTreebankSuffixLexicon()
        throws IOException
    {
                                //  Create empty lexicon.
        super();
                                //  Load default lexicon.
        loadLexicon
        (
            PennTreebankWordLexicon.class.getResource(
                pennTreebankSuffixLexiconPath ) ,
            "utf-8"
        );
    }

    /** Get the part of speech tag for a singular noun.
     *
     *  @return     The part of speech tag for a singular noun.
     */

    public String getSingularNounTag()
    {
        return "NN";
    }

    /** Get the part of speech tag for a plural noun.
     *
     *  @return     The part of speech tag for a plural noun.
     */

    public String getPluralNounTag()
    {
        return "NNS";
    }

    /** Get the part of speech tag for a singular proper noun.
     *
     *  @return     The part of speech tag for a singular proper noun.
     */

    public String getSingularProperNounTag()
    {
        return "NNP";
    }

    /** Get the part of speech tag for a plural proper noun.
     *
     *  @return     The part of speech tag for a plural proper noun.
     */

    public String getPluralProperNounTag()
    {
        return "NNPS";
    }

    /** Get the part of speech tag for a number.
     *
     *  @return     The part of speech tag for a number.
     */

    public String getNumberTag()
    {
        return "CD";
    }
}

/*
Copyright (c) 2008, 2013 by Northwestern University.
All rights reserved.

Developed by:
   Academic and Research Technologies
   Northwestern University
   http://www.it.northwestern.edu/about/departments/at/

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal with the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimers.

    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimers in the documentation and/or other materials provided
      with the distribution.

    * Neither the names of Academic and Research Technologies,
      Northwestern University, nor the names of its contributors may be
      used to endorse or promote products derived from this Software
      without specific prior written permission.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE CONTRIBUTORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
*/



