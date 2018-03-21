/*
 * Assignment M4 Optical Barcode Readers
 * Program to TODO
 * Authors: Christian Guerrero, Jose Garcia, Grace Alvarez, Gabriel Loring
 * Last Changed: March 21th, 2018
 * 
 */

import java.util.*;

public class assignment4
{  
   public static void main(String[] args)
   {
      // Test Of Card Class
      System.out.println("Optical Barcode Reader\n");
      
   }
}      

//Phase 1
interface BarcodeIO{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
   
   /*
   Define an interface, BarcodeIO, that contains these method signatures.  Any class that implements BarcodeIO is expected to store some version of an image and some version of the text associated with that image. 

   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
   Now, as I said, this is an interface.  So you should be able to do this part of the assignment in less than 60 seconds.  I'll time you.  Go.

   Here are the descriptions of what these will do when implemented in the DataMatrix class, however, descriptions in an interface don't pack any punch in practice. 

   public boolean scan( BarcodeImage bc ) - accepts some image, represented as a BarcodeImage object to be described below, and stores a copy of this image.  Depending on the sophistication of the implementing class, the internally stored image might be an exact clone of the parameter, or a refined, cleaned and processed image.  Technically, there is no requirement that an implementing class use a BarcodeImage object internally, although we will do so.  For the basic DataMatrix option, it will be an exact clone.  Also, no translation is done here - i.e., any text string that might be part of an implementing class is not touched, updated or defined during the scan.
   public boolean readText( String text ) - accepts a text string to be eventually encoded in an image. No translation is done here - i.e., any BarcodeImage that might be part of an implementing class is not touched, updated or defined during the reading of the text.
   public boolean generateImageFromText() - Not technically an I/O operation, this method looks at the internal text stored in the implementing class and produces a companion BarcodeImage, internally (or an image in whatever format the implementing class uses).  After this is called, we expect the implementing object to contain a fully-defined image and text that are in agreement with each other.
   public boolean translateImageToText() - Not technically an I/O operation, this method looks at the internal image stored in the implementing class, and produces a companion text string, internally.  After this is called, we expect the implementing object to contain a fully defined image and text that are in agreement with each other.
   public void displayTextToConsole() - prints out the text string to the console.
   void displayImageToConsole() - prints out the image to the console.  In our implementation, we will do this in the form of a dot-matrix of blanks and asterisks, e.g.,
   */
}


//Phase 2:
class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;    //The exact internal dimensions of 2D data. 
   public static final int MAX_WIDTH = 65;    //The exact internal dimensions of 2D data. 
   private boolean[][] image_data;            //This is where to store your image.
   
   /*
public static final int MAX_HEIGHT = 30;    public static final int MAX_WIDTH = 65;   The exact internal dimensions of 2D data. 
private boolean[][] image_data This is where to store your image.  If the incoming data is smaller than the max, instantiate memory anyway, but leave it blank (white). This data will be false for elements that are white, and true for elements that are black.
   METHODS

Constructors.  Two minimum, but you could have others:
Default Constructor -  instantiates a 2D array (MAX_HEIGHT x MAX_WIDTH) and stuffs it all with blanks (false).
BarcodeImage(String[] str_data) -takes a 1D array of Strings and converts it to the internal 2D array of booleans. 
HINT  -  This constructor is a little tricky because the incoming image is not the necessarily same size as the internal matrix.  So, you have to pack it into the lower-left corner of the double array, causing a bit of stress if you don't like 2D counting.  This is good 2D array exercise.  The DataMatrix class will make sure that there is no extra space below or left of the image so this constructor can put it into the lower-left corner of the array.
Accessor and mutator for each bit in the image:  boolean getPixel(int row, int col) and boolean setPixel(int row, int col, boolean value);   For the getPixel(), you can use the return value for both the actual data and also the error condition -- so that we don't "create a scene" if there is an error; we just return false.
Optional - A private utility method is highly recommended, but not required:  checkSize(String[] data)  It does the job of checking the incoming data for every conceivable size or null error.  Smaller is okay.  Bigger or null is not.
Optional - A displayToConsole() method that is useful for debugging this class, but not very useful for the assignment at large.
A clone() method that overrides the method of that name in Cloneable interface.    
   */
}

//Phase 3
class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';    
   private BarcodeImage image; 
   private String text;
   private int actualWidth;
   private int actualHeight;   
/*
 public static final char BLACK_CHAR = '*';
public static final char WHITE_CHAR = ' ';  
private BarcodeImage image - a single internal copy of any image scanned-in OR passed-into the constructor OR created by BarcodeIO's generateImageFromText().
private String text - a single internal copy of any text read-in OR passed-into the constructor OR created by BarcodeIO's translateImageToText().
private int actualWidth and actualHeight - two ints that are typically less than BarcodeImage.MAX_WIDTH and BarcodeImage.MAX_HEIGHT which represent the actual portion of the BarcodeImage that has the real signal.  This is dependent on the data in the image, and can change as the image changes through mutators.  It can be computed from the "spine" of the image.
   METHODS

Constructors.  Three minimum, but you could have more:
Default Constructor -  constructs an empty, but non-null, image and text value.  The initial image should be all white, however, actualWidth and actualHeight should start at 0, so it won't really matter what's in this default image, in practice.  The text can be set to blank, "", or something like "undefined".
DataMatrix(BarcodeImage image) - sets the image but leaves the text at its default value.  Call scan() and avoid duplication of code here.
DataMatrix(String text) - sets the text but leaves the image at its default value. Call readText() and avoid duplication of code here.
readText(String text) - a mutator for text.  Like the constructor;  in fact it is called by the constructor.
scan(BarcodeImage image) - a mutator for image.  Like the constructor;  in fact it is called by the constructor.  Besides calling the clone() method of the BarcodeImage class, this method will do a couple of things including calling cleanImage() and then set the actualWidth and actualHeight.  Because scan() calls clone(), it should deal with the CloneNotSupportedException by embeddingthe clone() call within a try/catch block.  Don't attempt to hand-off the exception using a "throws" clause in the function header since that will not be compatible with the underlying BarcodeIO interface.  The catches(...) clause can have an empty body that does nothing.
Accessors for actualWidth and actualHeight but no mutators! (why?)
private int computeSignalWidth() and private int computeSignalHeight() - Assuming that the image is correctly situated in the lower-left corner of the larger boolean array, these methods use the "spine" of the array (left and bottom BLACK) to determine the actual size.
Implementation of all BarcodeIO methods.
Private method:
private void cleanImage() - This private method will make no assumption about the placement of the "signal" within a passed-in BarcodeImage.  In other words, the in-coming BarcodeImage may not be lower-left justified.  Here's an example of  the placement of such an un-standardized image:
*/
}

