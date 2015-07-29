package edu.northwestern.at.morphadorner.tools;

/*  Please see the license information at the end of this file. */

import java.io.*;
import java.util.Map;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import edu.northwestern.at.morphadorner.WordAttributeNames;
import edu.northwestern.at.utils.HasID;
import edu.northwestern.at.utils.StringUtils;
import edu.northwestern.at.morphadorner.corpuslinguistics.adornedword.AdornedWord;
import edu.northwestern.at.morphadorner.corpuslinguistics.adornedword.BaseAdornedWord;

/** Information about a single XML word element.
 *
 *  <p>
 *  An ExtendedAdornedWord object extends the BaseAdornedWord object
 *  with the following additional information about a single word spelling
 *  externalized as an XML "<w>" or "<pc>" element.
 *  </p>
 *
 *  <ol>
 *  <li>The permanent word ID.</li>
 *  <li>The end of sentence flag (1 if word ends a sentence, 0 otherwise)
 *      </li>
 *  <li>The partial original token, if this is part of a word.</li>
 *  <li>The part flag.  "N" for a word which is not split;
 *      "I" for the first part of a split word; "M" for the middle
 *      parts of a split word; and "F" for the final part of a split word.
 *      </li>
 *  <li>The word ordinal within the text (starts at 1)</li>
 *  <li>FrontMiddleBack.FRONT, FrontMiddleBack.MIDDLE or
 *      FrontMiddleBack.BACK indicating if the word appears in
 *      front matter, middle matter (e.g., main text body), or
 *      back matter, respectively.
 *      </li>
 *  <li>MainSide.MAIN or MainSide.SIDE indicating if the word appears in the
 *      main text or side text, respectively.
 *      </li>
 *  <li>Nearest ancestor tag which is not a soft tag.</li>
 *  <li>Parent tag.</li>
 *  <li>A flag which indicates if the word appears in spoken text.
 *      True if the word is spoken, false otherwise.</li>
 *  <li>A link to the previous full word, in reading sequence.</li>
 *  <li>A link to the next full word, in reading sequence.</li>
 *  <li>A link to the previous word part, in reading sequence,
 *      for a split word.</li>
 *  <li>A link to the next word part, in reading sequence,
 *      for a split word.</li>
 *  <li>An XML path to the word.</li>
 *  <li>The sentence number to which this word belongs.
 *      Sentences start at 1.
 *      </li>
 *  <li>The number of the word in the sentence. Starts at 1.
 *      </li>
 *  <li>Boolean flag which indicates if word corresponds to gap.
 *      </li>
 *  <li>Boolean flag which indicates if word is descendant of
 *      a jump tag.
 *      </li>
 *  <li>Page number (starting at 0) on which the word appears.
 *      Page numbers come from from counting <pb> elements.
 *      Page numbers ignore the "n=" attributes, if any,
 *      present on the <pb> element.
 *      </li>
 *  <li>Word label.  Any extra text used to label a word.
 *      </li>
 *  <li>Rend.  Set of tokens describing how to render word.
 *      </li>
 *  <li>Type.  Token describing token type.  Usually used to mark
 *      word as "unclear".
 *      </li>
 *  <li>Div type.  The type= attribute value of the nearest ancestral
 *      div element.
 *      </li>
 *  <li>Blank precedes flag.  If a blank (<c> </c>) element
 *      precedes this word.
 *      </li>
 *  </ol>
 *
 *  <p>
 *  The following fields are inherited from the BaseAdornedWord object.
 *  </p>
 *
 *  <ol>
 *  <li>The complete original token.</li>
 *  <li>The corrected original spelling.</li>
 *  <li>The standard spelling.</li>
 *  <li>The lemma.</li>
 *  <li>The part of speech.</li>
 *  <li>The token type.</li>
 *  </ol>
 */

public class ExtendedAdornedWord
    extends BaseAdornedWord
    implements AdornedWord, HasID, Externalizable
{
    /** Front, middle, or back of text.
     */

    public static enum FrontMiddleBack { FRONT , MIDDLE , BACK };

    /** Main or side text (paratext).
     */

    public static enum MainSide { MAIN , SIDE };

    /** Word ID. */

//  protected String id;
    protected byte[] id;

    /** Original, possibly partial, word text. */

    protected String wordText;

    /** Word part flag.
     *
     *  <p>
     *  N for an unsplit word.
     *  I for the the first part of a split word.
     *  M for the middle part(s) of a split word.
     *  F for the final part of a split word.
     *  </p>
     */

    protected String part;

    /** End of sentence flag. */

    protected boolean eos;

    /** Word ordinal. */

    protected int ord;

    /** Main/side text marker. */

    protected MainSide mainSide;

    /** Spoken word flag. True if word is spoken. */

    protected boolean isSpoken;

    /** Verse flag. True if word appears in verse as opposed to prose. */

    protected boolean isVerse;

    /** Jump tag flag. True if word appears as descendant of jump tag. */

    protected boolean inJumpTag;

    /** Front/middle/back text marker. */

    protected FrontMiddleBack frontMiddleBack;

    /** Previous word. */

    protected ExtendedAdornedWord previousWord;
//  protected String previousWordID;

    /** Next word. */

    protected ExtendedAdornedWord nextWord;
//  protected String nextWordID;

    /** Previous word part for this word. */

    protected ExtendedAdornedWord previousWordPart;
//  protected String previousWordPartID;

    /** Next word part for this word. */

    protected ExtendedAdornedWord nextWordPart;
//  protected String nextWordPartID;

    /** XML word path. */

//  protected String path;
    protected byte[] path;

    /** Sentence number. */

    protected int sentenceNumber;

    /** Word number within sentence. */

    protected int wordNumber;

    /** True if word corresponds to a <gap>, false otherwise. */

    protected boolean isGap;

    /** Word index in list of words. */

    protected int wordIndex;

    /** Page number on which word appears.
     */

    protected int pageNumber;

    /** Word label. */

    protected String label;

    /** Unclear flag.   True if word is descendant of unclear tag. */

    protected boolean isUnclear;

    /** Rend.  How to render word. */

    protected String rend;

    /** Type.  Usually used to mark word as unclear. */

    protected String type;

    /** Div type.  Nearest ancestral div's type= attribute value. */

    protected String divType;

    /** Blank precedes flag.  True if blank <c> </c> precedes this word. */

    protected boolean blankPrecedes;

    /** Serial version UID. */

    protected static final long serialVersionUID    = -1L;

    /** Create empty ExtendedAdornedWord object.
     */

    public ExtendedAdornedWord()
    {
    }

    /** Create ExtendedAdornedWord object.
     *
     *  @param  wordText                <w> tag text.
     *  @param  atts                    XML attributes for a single
     *                                  "<w>" element.
     *  @param  frontMiddleBack         Front/middle/back classification.
     *  @param  mainSide                Main/side classification.
     *  @param  tagPath                 XML path to this tag.
     *  @param  isSpoken                Is spoken word flag.
     *  @param  isVerse                 Is verse flag.
     *  @param  inJumpTag               In jump tag flag.
     *  @param  isUnclear               Is unclear word.
     *  @param  divType                 Ancestral div type.
     *  @param  previousWord            Previous adorned word.
     *  @param  previousWordPart        Previous part for this word.
     *  @param  blankPrecedes           True if blank <c> element precedes.
     */

    public ExtendedAdornedWord
    (
        String wordText ,
        Attributes atts ,
        FrontMiddleBack frontMiddleBack ,
        MainSide mainSide ,
        String tagPath ,
        int pageNumber ,
        boolean isSpoken ,
        boolean isVerse ,
        boolean inJumpTag ,
        boolean isUnclear ,
        String divType ,
        ExtendedAdornedWord previousWord ,
        ExtendedAdornedWord previousWordPart ,
        boolean blankPrecedes
    )
    {
        this.wordText   = StringUtils.safeString( wordText );

        String idString = atts.getValue( WordAttributeNames.id );

        if ( idString == null )
        {
            idString    = atts.getValue( "id" );
        }

        if ( idString != null )
        {
            id  = StringUtils.getUTF8Bytes( idString );
        }

        ord             =
            StringUtils.stringToInt(
                atts.getValue( WordAttributeNames.ord ) ,
                0 );

        token           =
            StringUtils.safeString( atts.getValue( WordAttributeNames.tok ) );

        if ( token.length() == 0 )
        {
            token   = wordText;
        }

        spelling        =
            StringUtils.safeString( atts.getValue( WordAttributeNames.spe ) );

        if ( spelling.length() == 0 )
        {
            spelling    = wordText;
        }

        partsOfSpeech   = atts.getValue( WordAttributeNames.pos );

        if ( partsOfSpeech == null )
        {
            partsOfSpeech   =
                StringUtils.safeString
                (
                    atts.getValue( WordAttributeNames.teiPos )
                );

            if ( partsOfSpeech.startsWith( "#" ) )
            {
                partsOfSpeech   = partsOfSpeech.substring( 1 );
            }
        }
        else
        {
            partsOfSpeech   = StringUtils.safeString( partsOfSpeech );
        }

        part    = atts.getValue( WordAttributeNames.part );

        if ( part == null )
        {
            part    = "N";
        }

        eos =
            StringUtils.safeString
            (
                atts.getValue( WordAttributeNames.eos )
            ).equals( "1" );

        String unit =
            StringUtils.safeString( atts.getValue( "unit" ) );

        if ( unit.equals( "sentence" ) )
        {
            eos = true;
        }

        String lemAttr  = atts.getValue( WordAttributeNames.lem );

        if ( lemAttr == null )
        {
            lemAttr = atts.getValue( WordAttributeNames.teiLem );
        }

        lemmata         = StringUtils.safeString( lemAttr );

        standardSpelling    =
            StringUtils.safeString( atts.getValue( WordAttributeNames.reg ) );

        if ( tagPath == null )
        {
            path    =
                StringUtils.getUTF8Bytes
                (
                    atts.getValue( WordAttributeNames.p )
                );
        }
        else
        {
            path    = StringUtils.getUTF8Bytes( tagPath );
        }

        wordNumber      =
            StringUtils.stringToInt
            (
                atts.getValue( WordAttributeNames.wn ) ,
                -1
            );

        sentenceNumber  =
            StringUtils.stringToInt
            (
                atts.getValue( WordAttributeNames.sn ) ,
                -1
            );

        this.label  =
            StringUtils.safeString
            (
                atts.getValue( WordAttributeNames.n )
            );

        this.rend   =
            StringUtils.safeString
            (
                atts.getValue( WordAttributeNames.rend )
            );

        this.type   =
            StringUtils.safeString
            (
                atts.getValue( WordAttributeNames.type )
            );

        this.pageNumber             = pageNumber;

        this.frontMiddleBack        = frontMiddleBack;
        this.mainSide               = mainSide;

        this.isSpoken               = isSpoken;
        this.isVerse                = isVerse;
        this.inJumpTag              = inJumpTag;
        this.isUnclear              = isUnclear;
        this.divType                = divType;

        this.previousWord           = previousWord;
        nextWord                    = null;

        this.previousWordPart       = previousWordPart;
        nextWordPart                = null;
/*
        this.previousWordID         = "";

        if ( previousWord != null )
        {
            this.previousWordID         = previousWord.getID();
        }

        nextWordID                  = "";

        this.previousWordPartID     = "";

        if ( previousWordPart != null )
        {
            this.previousWordPartID     = previousWordPart.getID();
        }

        nextWordPartID              = "";
*/
        this.wordIndex              = 0;
        this.blankPrecedes          = blankPrecedes;
    }

    /** Create ExtendedAdornedWord object.
     *
     *  @param  wordText                <w> tag text.
     *  @param  atts                    XML attributes for a single
     *                                  word element.
     *  @param  frontMiddleBack         Front/middle/back classification.
     *  @param  mainSide                Main/side classification.
     *  @param  tagPath                 XML path to this tag.
     *  @param  isSpoken                Is spoken word flag.
     *  @param  isVerse                 Is verse flag.
     *  @param  inJumpTag               In jump tag flag.
     *  @param  previousWord            Previous adorned word.
     *  @param  previousWordPart        Previous part for this word.
     *  @param  blankPrecedes           True if blank <c> element precedes.
     */

    public ExtendedAdornedWord
    (
        String wordText ,
        Map<String, String> atts ,
        FrontMiddleBack frontMiddleBack ,
        MainSide mainSide ,
        String tagPath ,
        int pageNumber ,
        boolean isSpoken ,
        boolean isVerse ,
        boolean inJumpTag ,
        boolean isUnclear ,
        String divType ,
        ExtendedAdornedWord previousWord ,
        ExtendedAdornedWord previousWordPart ,
        boolean blankPrecedes
    )
    {
        this.wordText   = StringUtils.safeString( wordText );

        String idString = atts.get( WordAttributeNames.id );

        if ( idString == null )
        {
            idString    = atts.get( "id" );
        }

        if ( idString != null )
        {
            id  = StringUtils.getUTF8Bytes( idString );
        }

        ord             =
            StringUtils.stringToInt(
                atts.get( WordAttributeNames.ord ) ,
                0 );

        token           =
            StringUtils.safeString( atts.get( WordAttributeNames.tok ) );

        if ( token.length() == 0 )
        {
            token   = wordText;
        }

        spelling        =
            StringUtils.safeString( atts.get( WordAttributeNames.spe ) );

        if ( spelling.length() == 0 )
        {
            spelling    = wordText;
        }

        partsOfSpeech   = atts.get( WordAttributeNames.pos );

        if ( partsOfSpeech == null )
        {
            partsOfSpeech   =
                StringUtils.safeString
                (
                    atts.get( WordAttributeNames.teiPos )
                );

            if ( partsOfSpeech.startsWith( "#" ) )
            {
                partsOfSpeech   = partsOfSpeech.substring( 1 );
            }
        }
        else
        {
            partsOfSpeech   = StringUtils.safeString( partsOfSpeech );
        }

        part    = atts.get( WordAttributeNames.part );

        if ( part == null )
        {
            part    = "N";
        }

        eos =
            StringUtils.safeString
            (
                atts.get( WordAttributeNames.eos )
            ).equals( "1" );

        String unit =
            StringUtils.safeString( atts.get( "unit" ) );

        if ( unit.equals( "sentence" ) )
        {
            eos = true;
        }

        String lemAttr  = atts.get( WordAttributeNames.lem );

        if ( lemAttr == null )
        {
            lemAttr = atts.get( WordAttributeNames.teiLem );
        }

        lemmata         = StringUtils.safeString( lemAttr );

        standardSpelling    =
            StringUtils.safeString( atts.get( WordAttributeNames.reg ) );

        if ( tagPath == null )
        {
            path    =
                StringUtils.getUTF8Bytes
                (
                    atts.get( WordAttributeNames.p )
                );
        }
        else
        {
            path    = StringUtils.getUTF8Bytes( tagPath );
        }

        wordNumber      =
            StringUtils.stringToInt
            (
                atts.get( WordAttributeNames.wn ) ,
                -1
            );

        sentenceNumber  =
            StringUtils.stringToInt
            (
                atts.get( WordAttributeNames.sn ) ,
                -1
            );

        this.label  =
            StringUtils.safeString
            (
                atts.get( WordAttributeNames.n )
            );

        this.rend   =
            StringUtils.safeString
            (
                atts.get( WordAttributeNames.rend )
            );

        this.type   =
            StringUtils.safeString
            (
                atts.get( WordAttributeNames.type )
            );

        this.pageNumber             = pageNumber;

        this.frontMiddleBack        = frontMiddleBack;
        this.mainSide               = mainSide;

        this.isSpoken               = isSpoken;
        this.isVerse                = isVerse;
        this.inJumpTag              = inJumpTag;
        this.isUnclear              = isUnclear;
        this.divType                = divType;

        this.previousWord           = previousWord;
        nextWord                    = null;

        this.previousWordPart       = previousWordPart;
        nextWordPart                = null;
        this.wordIndex              = 0;

        this.blankPrecedes          = blankPrecedes;
    }

    /** Create ExtendedAdornedWord object.
     *
     *  @param  adornedWord         Populated adorned word.
     *  @param  id                  Word ID.
     *  @param  label               Word label.
     *  @param  part                String word part flag.
     *  @param  ord                 Word ordinal.
     *  @param  pageNumber          Page number.
     *  @param  eos                 EOS flag.
     *  @param  wordNumber          Word number in sentence.
     *  @param  sentenceNumber      Sentence number in sentence.
     *  @param  frontMiddleBack     Front/middle/back classification.
     *  @param  mainSide            Main/side classification.
     *  @param  tagPath             XML path to this tag.
     *  @param  isSpoken            Is spoken word flag.
     *  @param  isVerse             Is verse flag.
     *  @param  isUnclear           Is unclear flag.
     *  @param  rend                How to render word.
     *  @param  type                Type of word.
     *  @param  previousWord        Previous adorned word.
     *  @param  previousWordPart    Previous part for this word.
     *  @param  blankPrecedes           True if blank <c> element precedes.
     */

    public ExtendedAdornedWord
    (
        AdornedWord adornedWord ,
        String id ,
        String label ,
        String part ,
        int ord ,
        int pageNumber ,
        boolean eos ,
        int wordNumber ,
        int sentenceNumber ,
        FrontMiddleBack frontMiddleBack ,
        MainSide mainSide ,
        String tagPath ,
        boolean isSpoken ,
        boolean isVerse ,
        boolean isUnclear ,
        String rend ,
        String type ,
        ExtendedAdornedWord previousWord ,
        ExtendedAdornedWord previousWordPart ,
        boolean blankPrecedes
    )
    {
        this.wordText           = adornedWord.getToken();
        this.id                 = StringUtils.getUTF8Bytes( id );
        this.label              = label;
        this.ord                = ord;
        this.token              = adornedWord.getToken();
        this.spelling           = adornedWord.getSpelling();
        this.partsOfSpeech      = adornedWord.getPartsOfSpeech();
        this.lemmata            = adornedWord.getLemmata();
        this.standardSpelling   = adornedWord.getStandardSpelling();
        this.part               = part;
        this.eos                = eos;
        this.path               = StringUtils.getUTF8Bytes( tagPath );
        this.wordNumber         = wordNumber;
        this.sentenceNumber     = sentenceNumber;
        this.pageNumber         = pageNumber;

        this.frontMiddleBack    = frontMiddleBack;
        this.mainSide           = mainSide;

        this.rend               = StringUtils.safeString( rend );
        this.type               = StringUtils.safeString( type );
        this.isSpoken           = isSpoken;
        this.isVerse            = isVerse;
        this.isUnclear          = isUnclear;
        this.inJumpTag          = inJumpTag;

        this.previousWord       = previousWord;
        nextWord                = null;

        this.previousWordPart   = previousWordPart;
        nextWordPart            = null;

        this.blankPrecedes      = blankPrecedes;
/*
        this.previousWordID     = "";

        if ( previousWord != null )
        {
            this.previousWordID = previousWord.getID();
        }

        this.nextWordID             = "";
        this.previousWordPartID     = "";

        if ( previousWordPart != null )
        {
            this.previousWordPartID     = previousWordPart.getID();
        }

        nextWordPartID              = "";
*/
        this.wordIndex              = 0;
    }

    /** Get word ID.
     *
     *  @return     The word ID.
     */

    public String getID()
    {
        return StringUtils.getStringFromUTF8Bytes( id );
    }

    /** Set word ID.
     *
     *  @param  id  The word ID.
     */

    public void setID( String id )
    {
        this.id = StringUtils.getUTF8Bytes( id );
    }

    /** Get part flag.
     *
     *  @return     The part flag.
     */

    public String getPart()
    {
        return part;
    }

    /** Get path.
     *
     *  @return     The path.
     */

    public String getPath()
    {
        return StringUtils.getStringFromUTF8Bytes( path );
    }

    /** Check if word is first (or only) part of a split word.
     *
     *  @return     True if word is first part of a split word or
     *              only part of a non-split word.
     */

    public boolean isFirstPart()
    {
        return part.equals( "N" ) || part.equals( "I" );
    }

    /** Check if word is middle (or only) part of a split word.
     *
     *  @return     True if word is middle part of a split word or
     *              only part of a non-split word.
     */

    public boolean isMiddlePart()
    {
        return part.equals( "N" ) || part.equals( "M" );
    }

    /** Check if word is last (or only) part of a split word.
     *
     *  @return     True if word is last part of a split word or
     *              only part of a non-split word.
     */

    public boolean isLastPart()
    {
        return part.equals( "N" ) || part.equals( "F" );
    }

    /** Check if word is a split word.
     *
     *  @return     True if word is a split word.
     */

    public boolean isSplitWord()
    {
        return !part.equals( "N" );
    }

    /** Get word ordinal.
     *
     *  @return     The word ordinal.
     */

    public int getOrd()
    {
        return ord;
    }

    /** Set word ordinal.
     *
     *  @param  ord     The word ordinal.
     */

    public void setOrd( int ord )
    {
        this.ord    = ord;
    }

    /** Get sentence number for word.
     *
     *  @return     The sentence number in which this word appears.
     */

    public int getSentenceNumber()
    {
        return sentenceNumber;
    }

    /** Set sentence number for word.
     *
     *  @param  sentenceNumber  The sentence number in which this word appears.
     */

    public void setSentenceNumber( int sentenceNumber )
    {
        this.sentenceNumber = sentenceNumber;
    }

    /** Get word number in sentence.
     *
     *  @return     The word number in the sentence.
     */

    public int getWordNumber()
    {
        return wordNumber;
    }

    /** Set word number in sentence.
     *
     *  @param  wordNumber  The word number in the sentence.
     */

    public void setWordNumber( int wordNumber )
    {
        this.wordNumber = wordNumber;
    }

    /** Get page number in which word appears.
     *
     *  @return     The page number.
     */

    public int getPageNumber()
    {
        return pageNumber;
    }

    /** Set page number in which word appears.
     *
     *  @param  pageNumber  The page number.
     */

    public void setPageNumber( int pageNumber )
    {
        this.pageNumber = pageNumber;
    }

    /** Get word label.
     *
     *  @return     The word label.
     */

    public String getLabel()
    {
        return label;
    }

    /** Set word label.
     *
     *  @param  label   The word label.
     */

    public void setLabel( String label )
    {
        this.label  = label;
    }

    /** Get unclear flag.
     *
     *  @return     True if word descends from unclear tag.
     */

    public boolean getIsUnclear()
    {
        return isUnclear || ( type.equals( "unclear" ) );
    }

    /** Set unclear flag.
     *
     *  @param  isUnclear   True if word descends from unclear tag.
     */

    public void setIsUnclear( boolean isUnclear )
    {
        this.isUnclear  = isUnclear;
    }

    /** Get rend.
     *
     *  @return     rend string.
     */

    public String getRend()
    {
        return rend;
    }

    /** Set rend.
     *
     *  @param  rend    rend string.
     */

    public void setRend( String rend )
    {
        this.rend   = StringUtils.safeString( rend );
    }

    /** Append to rend.
     *
     *  @param  rend    String to append to rend.
     */

    public void appendRend( String rend )
    {
        if ( this.rend.length() > 0 )
        {
            this.rend   = this.rend + " " + rend;
        }
        else
        {
            this.rend   = rend;
        };
    }

    /** Get type.
     *
     *  @return     type string.
     */

    public String getType()
    {
        return type;
    }

    /** Set type.
     *
     *  @param  type    type string.
     */

    public void setType( String type )
    {
        this.type   = StringUtils.safeString( type );
    }

    /** Get end of sentence flag.
     *
     *  @return     The end of sentence flag.
     */

    public boolean getEOS()
    {
        return eos;
    }

    /** Set end of sentence flag.
     *
     *  @param  eos     The end of sentence flag.
     */

    public void setEOS( boolean eos )
    {
        this.eos    = eos;
    }

    /** Get main or side.
     *
     *  @return     The main or side string.
     */

    public MainSide getMainSide()
    {
        return mainSide;
    }

    /** Get front/middle/back.
     *
     *  @return     The main or side string.
     */

    public FrontMiddleBack getFrontMiddleBack()
    {
        return frontMiddleBack;
    }

    /** Get spoken flag.
     *
     *  @return     True if word is spoken.
     */

    public boolean getSpoken()
    {
        return isSpoken;
    }

    /** Set spoken flag.
     *
     *  @param  isSpoken    True if word is spoken.
     */

    public void setSpoken( boolean isSpoken )
    {
        this.isSpoken   = isSpoken;
    }

    /** Get verse flag.
     *
     *  @return     True if word is in verse.
     */

    public boolean getVerse()
    {
        return isVerse;
    }

    /** Set verse flag.
     *
     *  @param  isVerse True if word is in verse.
     */

    public void setVerse( boolean isVerse )
    {
        this.isVerse    = isVerse;
    }

    /** Get in jump tag flag.
     *
     *  @return     True if word is in jump tag.
     */

    public boolean getInJumpTag()
    {
        return inJumpTag;
    }

    /** Set in jump tag flag.
     *
     *  @param  inJumpTag   True if word is in jump tag.
     */

    public void setInJumpTag( boolean inJumpTag )
    {
        this.inJumpTag  = inJumpTag;
    }

    /** Get gap flag.
     *
     *  @return     True if word represents gap.
     */

    public boolean getGap()
    {
        return isGap;
    }

    /** Set gap flag.
     *
     *  @param  isGap   True if word represents gap.
     */

    public void setGap( boolean isGap )
    {
        this.isGap  = isGap;
    }

    /** Get word text.
     *
     *  @return     Word text.
     */

    public String getWordText()
    {
        return wordText;
    }

    /** Set word text.
     *
     *  @param  wordText    The word text.
     */

    public void setWordText( String wordText )
    {
        this.wordText   = wordText;
    }

    /** Set word text.
     *
     *  @param  ch      Array of characters.
     *  @param  start   The starting position in the array.
     *  @param  length  The number of characters.
     */

    public void setWordText( char ch[] , int start , int length )
    {
        this.wordText   = "";

        for ( int i = start ; i < start + length ; i++ )
        {
            this.wordText   = this.wordText + ch[ i ];
        }
    }

    /** Get next word.
     *
     *  @return     The next word.
     */

    public ExtendedAdornedWord getNextWord()
    {
        return nextWord;
    }

    /** Get next word ID.
     *
     *  @return     The next word ID.
     */
/*
    public String getNextWordID()
    {
        return nextWordID;
    }
*/
    /** Set next word.
     *
     *  @param  nextWord    The next word.
     */

    public void setNextWord( ExtendedAdornedWord nextWord )
    {
        this.nextWord   = nextWord;
    }

    /** Set next word ID.
     *
     *  @param  nextWordID  The next word ID.
     */
/*
    public void setNextWordID( String nextWordID )
    {
        this.nextWordID = nextWordID;
    }
*/
    /** Get previous word.
     *
     *  @return     The previous word.
     */

    public ExtendedAdornedWord getPreviousWord()
    {
        return previousWord;
    }

    /** Get previous word ID.
     *
     *  @return     The previous word ID.
     */
/*
    public String getPreviousWordID()
    {
        return previousWordID;
    }
*/
    /** Set previous word.
     *
     *  @param  previousWord    The previous word.
     */

    public void setPreviousWord( ExtendedAdornedWord previousWord )
    {
        this.previousWord   = previousWord;
    }

    /** Set previous word ID.
     *
     *  @param  previousWordID  The previous word ID.
     */
/*
    public void setPreviousWordID( String previousWordID )
    {
        this.previousWordID = previousWordID;
    }
*/
    /** Get next word part.
     *
     *  @return     The next word part.
     *              Null if this is the last part or a non-split word.
     */

    public ExtendedAdornedWord getNextWordPart()
    {
        return nextWordPart;
    }

    /** Get next word part ID.
     *
     *  @return     The next word part ID.
     *              Empty if this is the last part or a non-split word.
     */
/*
    public String getNextWordPartID()
    {
        return nextWordPartID;
    }
*/
    /** Set next word part.
     *
     *  @param  nextWordPart    The next word part.
     */

    public void setNextWordPart( ExtendedAdornedWord nextWordPart )
    {
        this.nextWordPart   = nextWordPart;
    }

    /** Set next word part ID.
     *
     *  @param  nextWordPartID  The next word part ID.
     */
/*
    public void setNextWordPartID( String nextWordPartID )
    {
        this.nextWordPartID = nextWordPartID;
    }
*/
    /** Get previous word part.
     *
     *  @return     The previous word part for a split word.
     *              Null if this is the first part or a non-split word.
     */

    public ExtendedAdornedWord getPreviousWordPart()
    {
        return previousWordPart;
    }

    /** Get previous word part ID.
     *
     *  @return     The previous word part for a split word.
     *              Empty if this is the first part or a non-split word.
     */
/*
    public String getPreviousWordPartID()
    {
        return previousWordPartID;
    }
*/
    /** Set previous word part.
     *
     *  @param  previousWordPart    The previous word part.
     */

    public void setPreviousWordPart( ExtendedAdornedWord previousWordPart )
    {
        this.previousWordPart   = previousWordPart;
    }

    /** Set previous word part ID.
     *
     *  @param  previousWordPartID  The previous word part ID.
     */
/*
    public void setPreviousWordPartID( String previousWordPartID )
    {
        this.previousWordPartID = previousWordPartID;
    }
*/
    /** Get word index.
     *
     *  @return     The word index.
     */

    public int getWordIndex()
    {
        return wordIndex;
    }

    /** Set word index.
     *
     *  @param  wordIndex   The word index.
     */

    public void setWordIndex( int wordIndex )
    {
        this.wordIndex  = wordIndex;
    }

    /** Get div type.
     *
     *  @return     Type= attribute from nearest ancestral div element.
     */

    public String getDivType()
    {
        return divType;
    }

    /** Set div type.
     *
     *  @param  divType Type= attribute from nearest ancestral div element.
     */

    public void setDivType( String divType )
    {
        this.divType    = divType;
    }

    /** Get blank precedes flag.
     *
     *  @return     True if blank <c> element precedes this word.
     */

    public boolean getBlankPrecedes()
    {
        return blankPrecedes;
    }

    /** Set blank precedes flag.
     *
     *  @param  blankPrecedes   True if blank <c> element precedes this word.
     */

    public void setBlankPrecedes( boolean blankPrecedes )
    {
        this.blankPrecedes  = blankPrecedes;
    }

    /** Append characters to word text.
     *
     *  @param  ch      Array of characters.
     *  @param  start   The starting position in the array.
     *  @param  length  The number of characters.
     */

    public void appendWordText( char ch[] , int start , int length )
    {
        for ( int i = start ; i < start + length ; i++ )
        {
            wordText    = wordText + ch[ i ];
        }

        if ( token == null )
        {
            token   = wordText;
        }

        if ( spelling == null )
        {
            spelling    = wordText;
        }

        if ( lemmata == null )
        {
            lemmata = wordText;
        }

        if ( standardSpelling == null )
        {
            standardSpelling    = wordText;
        }
    }

    /** Append string to word text.
     *
     *  @param  s   String to append to word text.
     */

    public void appendWordText( String s )
    {
        wordText    = wordText + s;

        if ( token == null )
        {
            token   = wordText;
        }

        if ( spelling == null )
        {
            spelling    = wordText;
        }

        if ( lemmata == null )
        {
            lemmata = wordText;
        }

        if ( standardSpelling == null )
        {
            standardSpelling    = wordText;
        }
    }

    /** Return spelling for toString.
     *
     *  @return     The spelling.
     */

    public String toString()
    {
        return spelling;
    }

    /** Writes the work set to an object output stream (serializes the object).
     *
     *  @param  out     Object output stream.
     *
     *  @throws IOException
     */

    public void writeExternal( ObjectOutput out )
        throws IOException
    {
        out.writeObject( token );
        out.writeObject( spelling );
        out.writeObject( standardSpelling );
        out.writeObject( lemmata );
        out.writeObject( partsOfSpeech );

        out.writeObject( id );
        out.writeObject( wordText );
        out.writeObject( part );
        out.writeBoolean( eos );
        out.writeInt( ord );
        out.writeObject( mainSide );
        out.writeBoolean( isSpoken );
        out.writeBoolean( isVerse );
        out.writeBoolean( inJumpTag );
        out.writeObject( frontMiddleBack );

        out.writeObject( previousWord );
        out.writeObject( nextWord );
        out.writeObject( previousWordPart );
        out.writeObject( nextWordPart );
/*
        out.writeObject( previousWordID );
        out.writeObject( nextWordID );
        out.writeObject( previousWordPartID );
        out.writeObject( nextWordPartID );
*/
        out.writeObject( path );
        out.writeInt( sentenceNumber );
        out.writeInt( wordNumber );
        out.writeInt( wordIndex );
        out.writeInt( pageNumber );
        out.writeObject( label );
        out.writeObject( rend );
        out.writeObject( type );
        out.writeObject( divType );
        out.writeObject( isUnclear );
        out.writeBoolean( isGap );
        out.writeBoolean( blankPrecedes );
    }

    /** Reads the work set from an object input stream (deserializes the object).
     *
     *  @param  in      Object input stream.
     *
     *  @throws IOException
     *
     *  @throws ClassNotFoundException
     */

    public void readExternal( ObjectInput in )
        throws IOException, ClassNotFoundException
    {
        token               = (String)in.readObject();
        spelling            = (String)in.readObject();
        standardSpelling    = (String)in.readObject();
        lemmata             = (String)in.readObject();
        partsOfSpeech       = (String)in.readObject();

        id                  = (byte[])in.readObject();
        wordText            = (String)in.readObject();
        part                = (String)in.readObject();
        eos                 = in.readBoolean();
        ord                 = in.readInt();
        mainSide            = (MainSide)in.readObject();
        isSpoken            = in.readBoolean();
        isVerse             = in.readBoolean();
        inJumpTag           = in.readBoolean();
        frontMiddleBack     = (FrontMiddleBack)in.readObject();

        previousWord        = (ExtendedAdornedWord)in.readObject();
        nextWord            = (ExtendedAdornedWord)in.readObject();
        previousWordPart    = (ExtendedAdornedWord)in.readObject();
        nextWordPart        = (ExtendedAdornedWord)in.readObject();
/*
        previousWordID      = (String)in.readObject();
        nextWordID          = (String)in.readObject();
        previousWordPartID  = (String)in.readObject();
        nextWordPartID      = (String)in.readObject();
*/
        path                = (byte[])in.readObject();
        sentenceNumber      = in.readInt();
        wordNumber          = in.readInt();
        wordIndex           = in.readInt();
        pageNumber          = in.readInt();
        label               = (String)in.readObject();
        rend                = (String)in.readObject();
        type                = (String)in.readObject();
        divType             = (String)in.readObject();
        isUnclear           = in.readBoolean();
        isGap               = in.readBoolean();
        blankPrecedes       = in.readBoolean();
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



