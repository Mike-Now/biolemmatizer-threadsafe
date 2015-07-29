package edu.northwestern.at.morphadorner.corpuslinguistics.namestandardizer;

/*  Please see the license information at the end of this file. */

import edu.northwestern.at.utils.ClassUtils;
import edu.northwestern.at.utils.UTF8Properties;

/** NameStandardizer factory.
 */

public class NameStandardizerFactory
{
    /** Get a name standardizer.
     *
     *  @return     The name standardizer.
     */

    public static NameStandardizer newNameStandardizer()
    {
        String className    =
            System.getProperty( "namestandardizer.class" );

        if ( className == null )
        {
            className   = "DefaultNameStandardizer";
        }

        return newNameStandardizer( className );
    }

    /** Get a name standardizer.
     *
     *  @param      properties      MorphAdorner properties.
     *
     *  @return     The name standardizer.
     */

    public static NameStandardizer newNameStandardizer
    (
        UTF8Properties properties
    )
    {
        String className    = null;

        if ( properties != null )
        {
            className   = properties.getProperty( "namestandardizer.class" );
        }

        if ( className == null )
        {
            className   = "DefaultNameStandardizer";
        }

        return newNameStandardizer( className );
    }

    /** Get a name standardizer of a specified class name.
     *
     *  @param  className   Class name for the name standardizer.
     *
     *  @return             The name standardizer.
     */

    public static NameStandardizer newNameStandardizer( String className )
    {
        NameStandardizer nameStandardizer   = null;

        try
        {
            nameStandardizer    =
                (NameStandardizer)Class.forName(
                    className ).newInstance();
        }
        catch ( Exception e )
        {
            String fixedClassName   =
                ClassUtils.packageName
                (
                    NameStandardizerFactory.class.getName()
                ) +
                "." + className;

            try
            {
                nameStandardizer    =
                    (NameStandardizer)Class.forName(
                        fixedClassName ).newInstance();
            }
            catch ( Exception e2 )
            {
                System.err.println(
                    "Unable to create name standardizer of class " +
                    fixedClassName + ", using default." );

                nameStandardizer    = new DefaultNameStandardizer();
            }
        }

        return nameStandardizer;
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



