package edu.northwestern.at.morphadorner.corpuslinguistics.lemmatizer;

/*  Please see the license information at the end of this file. */

import edu.northwestern.at.utils.ClassUtils;
import edu.northwestern.at.utils.UTF8Properties;

/** Lemmatizer factory.
 */

public class LemmatizerFactory
{
    /** Get a lemmatizer.
     *
     *  @return     The lemmatizer.
     */

    public static Lemmatizer newLemmatizer()
    {
        String className    =
            System.getProperty( "lemmatizer.class" );

        if ( className == null )
        {
            className   = "DefaultLemmatizer";
        }

        return newLemmatizer( className );
    }

    /** Get a lemmatizer.
     *
     *  @param      properties      MorphAdorner properties.
     *
     *  @return     The lemmatizer.
     */

    public static Lemmatizer newLemmatizer
    (
        UTF8Properties properties
    )
    {
        String className    = null;

        if ( properties != null )
        {
            className   = properties.getProperty( "lemmatizer.class" );
        }

        if ( className == null )
        {
            className   = "DefaultLemmatizer";
        }

        return newLemmatizer( className );
    }

    /** Get a lemmatizer of a specified class name.
     *
     *  @param  className   Class name for the lemmatizer.
     *
     *  @return             The lemmatizer.
     */

    public static Lemmatizer newLemmatizer( String className )
    {
        Lemmatizer lemmatizer   = null;

        try
        {
            lemmatizer  =
                (Lemmatizer)Class.forName( className ).newInstance();
        }
        catch ( Exception e )
        {
            String fixedClassName   =
                ClassUtils.packageName
                (
                    LemmatizerFactory.class.getName()
                ) +
                "." + className;

            try
            {
                lemmatizer  =
                    (Lemmatizer)Class.forName(
                        fixedClassName ).newInstance();
            }
            catch ( Exception e2 )
            {
                System.err.println(
                    "Unable to create lemmatizer of class " +
                    fixedClassName + ", using default." );

                try
                {
                    lemmatizer  = new DefaultLemmatizer();
                }
                catch ( Exception e3 )
                {
                                //  Assume higher-level code will
                                //  catch null lemmatizer.
/*
                    System.err.println(
                        "Unable to create lemmatizer, " +
                        "MorphAdorner cannot continue." );

                    System.exit( 1 );
*/
                }
            }
        }

        return lemmatizer;
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



