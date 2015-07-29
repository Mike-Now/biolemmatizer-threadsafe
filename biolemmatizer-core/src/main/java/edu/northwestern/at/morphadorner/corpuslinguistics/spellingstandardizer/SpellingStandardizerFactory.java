package edu.northwestern.at.morphadorner.corpuslinguistics.spellingstandardizer;

/*  Please see the license information at the end of this file. */

import edu.northwestern.at.utils.ClassUtils;
import edu.northwestern.at.utils.UTF8Properties;

/** SpellingStandardizer factory.
 */

public class SpellingStandardizerFactory
{
    /** Get a spelling standardizer.
     *
     *  @return     The spelling standardizer.
     */

    public static SpellingStandardizer newSpellingStandardizer()
    {
        String className    =
            System.getProperty( "spellingstandardizer.class" );

        if ( className == null )
        {
            className   = "DefaultSpellingStandardizer";
        }

        return newSpellingStandardizer( className );
    }

    /** Get a spelling standardizer.
     *
     *  @param      properties      MorphAdorner properties.
     *
     *  @return     The spelling standardizer.
     */

    public static SpellingStandardizer newSpellingStandardizer
    (
        UTF8Properties properties
    )
    {
        String className    = null;

        if ( properties != null )
        {
            className   =
                properties.getProperty( "spellingstandardizer.class" );
        }

        if ( className == null )
        {
            className   = "DefaultSpellingStandardizer";
        }

        return newSpellingStandardizer( className );
    }

    /** Get an initial spelling standardizer.
     *
     *  @return     The spelling standardizer.
     */

    public static SpellingStandardizer newInitialSpellingStandardizer()
    {
        String className    =
            System.getProperty( "initialspellingstandardizer.class" );

        if ( className == null )
        {
            className   = "NoopSpellingStandardizer";
        }

        return newSpellingStandardizer( className );
    }

    /** Get an initial spelling standardizer.
     *
     *  @param      properties      MorphAdorner properties.
     *
     *  @return     The spelling standardizer.
     */

    public static SpellingStandardizer newInitialSpellingStandardizer
    (
        UTF8Properties properties
    )
    {
        String className    = null;

        if ( properties != null )
        {
            className   =
                properties.getProperty( "initialspellingstandardizer.class" );
        }

        if ( className == null )
        {
            className   = "NoopSpellingStandardizer";
        }

        return newSpellingStandardizer( className );
    }

    /** Get a spelling standardizer of a specified class name.
     *
     *  @param  className   Class name for the spelling standardizer.
     *
     *  @return             The spelling standardizer.
     */

    public static SpellingStandardizer newSpellingStandardizer
    (
        String className
    )
    {
        SpellingStandardizer spellingStandardizer   = null;

        try
        {
            spellingStandardizer    =
                (SpellingStandardizer)Class.forName(
                    className ).newInstance();
        }
        catch ( Exception e )
        {
            String fixedClassName   =
                ClassUtils.packageName
                (
                    SpellingStandardizerFactory.class.getName()
                ) +
                "." + className;

            try
            {
                spellingStandardizer    =
                    (SpellingStandardizer)Class.forName(
                        fixedClassName ).newInstance();
            }
            catch ( Exception e2 )
            {
                System.err.println(
                    "Unable to create spelling standardizer of class " +
                    fixedClassName + ", using default." );

                spellingStandardizer    = new DefaultSpellingStandardizer();
            }
        }

        return spellingStandardizer;
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



