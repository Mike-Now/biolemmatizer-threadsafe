package edu.northwestern.at.morphadorner.corpuslinguistics.outputter;

/*  Please see the license information at the end of this file. */

import java.io.*;
import java.util.*;

import edu.northwestern.at.morphadorner.corpuslinguistics.tokenizer.*;

/** Interface for generating adorned word output. */

public interface AdornedWordOutputter
{
    /** Outputs a word and its adornments (part of speech, lemmata, etc).
     *
     *  @param  wordAndAdornments   Word and its adornments as
     *                              an array of string.
     *
     *  @throws IOException         If an output error occurs.
     */

    public void outputWordAndAdornments( String[] wordAndAdornments )
        throws IOException;

    /** Outputs a word and its adornments (part of speech, lemmata, etc).
     *
     *  @param  wordAndAdornments   Word and its adornments as a list.
     *
     *  @throws IOException         If an output error occurs.
     */

    public void outputWordAndAdornments( List<String> wordAndAdornments )
        throws IOException;

    /** Create output file.
     *
     *  @param  fileName    The output file name.
     *  @param  encoding    The encoding for the output file.
     *  @param  sepChar     The separator character.
     */

    public void createOutputFile
    (
        String fileName ,
        String encoding ,
        char sepChar
    )
        throws IOException;

    /** Get output file name.
     *
     *  @return Output file name.  May be null.
     */

    public String getOutputFileName();

    /** Get output file encoding.
     *
     *  @return Output file encoding.
     */

    public String getOutputFileEncoding();

    /** Set word attribute names.
     *
     *  @param  wordAttributeNames  Word attribute names.
     */

    public void setWordAttributeNames( List<String> wordAttributeNames );

    /** Close outputter.
     */

    public void close();
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


