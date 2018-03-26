/*
 * Assignment M4 Optical Barcode Readers
 * Program Description: Write a java program (Optical Barcode Readers) that combines
 * 2D arrays, interfaces (including Cloneable), and a very active industrial application,
 * optical scanning and pattern recognition.
 * Authors: Christian Guerrero, Jose Garcia, Grace Alvarez, Gabriel Loring
 * Last Changed: March 25th, 2018
 * 
 */

import java.util.*;

public class assignment4
{  
   public static void main(String[] args)
   {
      // Test Assignment
      System.out.println("Optical Barcode Reader\n");
         String[] sImageIn =
         {
            "                                               ",
            "                                               ",
            "                                               ",
            "     * * * * * * * * * * * * * * * * * * * * * ",
            "     *                                       * ",
            "     ****** **** ****** ******* ** *** *****   ",
            "     *     *    ****************************** ",
            "     * **    * *        **  *    * * *   *     ",
            "     *   *    *  *****    *   * *   *  **  *** ",
            "     *  **     * *** **   **  *    **  ***  *  ",
            "     ***  * **   **  *   ****    *  *  ** * ** ",
            "     *****  ***  *  * *   ** ** **  *   * *    ",
            "     ***************************************** ",  
            "                                               ",
            "                                               ",
            "                                               "

         };      
            
         
         String[] sImageIn_2 =
         {
               "                                          ",
               "                                          ",
               "* * * * * * * * * * * * * * * * * * *     ",
               "*                                    *    ",
               "**** *** **   ***** ****   *********      ",
               "* ************ ************ **********    ",
               "** *      *    *  * * *         * *       ",
               "***   *  *           * **    *      **    ",
               "* ** * *  *   * * * **  *   ***   ***     ",
               "* *           **    *****  *   **   **    ",
               "****  *  * *  * **  ** *   ** *  * *      ",
               "**************************************    ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          "

         };
     
         BarcodeImage bc = new BarcodeImage(sImageIn);
         DataMatrix dm = new DataMatrix(bc);

         // First secret message
         dm.translateImageToText();
         dm.displayTextToConsole();
         dm.displayImageToConsole();

         // second secret message
         bc = new BarcodeImage(sImageIn_2);
         dm.scan(bc);
         dm.translateImageToText();
         dm.displayTextToConsole();
         dm.displayImageToConsole();
         
         // create your own message
         dm.readText("What a great resume builder this is!");
         dm.generateImageFromText();
         dm.displayTextToConsole();
         dm.displayImageToConsole();
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
}

//Phase 2
class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;    //The exact internal dimensions of 2D data. 
   public static final int MAX_WIDTH = 65;    //The exact internal dimensions of 2D data. 
   private boolean[][] image_data;            //This is where to store your image.
   
   //Constructors  Two minimum, but you could have others:
   //Default Constructor -  instantiates a 2D array (MAX_HEIGHT x MAX_WIDTH) and stuffs it all with blanks (false).
   
   public BarcodeImage()
   {
      this.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      
      
       for(int i = 0; i < MAX_HEIGHT; i++)
      {
         for(int j = 0; j < MAX_WIDTH; j++)
         {
            image_data[i][j] = false;
         }
      }
   }
   //BarcodeImage(String[] str_data) -takes a 1D array of Strings and converts it to the internal 2D array of booleans. 
   /*
   HINT  -  This constructor is a little tricky because the incoming image is not the necessarily same size as the internal matrix.
   So, you have to pack it into the lower-left corner of the double array, causing a bit of stress if you don't like 2D counting. 
   This is good 2D array exercise.  The DataMatrix class will make sure that there is no extra space below or left of the image
   so this constructor can put it into the lower-left corner of the array.
   */
   public BarcodeImage(String[] str_data)
   {
     this.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
     
     
      for(int i = 0, k = 0; i < MAX_HEIGHT && k < str_data.length; i++, k++)
      {
         for(int j = 0; j < MAX_WIDTH && j < str_data[k].length(); j++)
         {
            if(str_data[k].charAt(j) == ' ')
               this.image_data[i][j] = false;
            else
               this.image_data[i][j] = true;
         }
      }
   }
   
   public boolean setPixel(int row, int col, boolean value)
   {
      if(isValid(row, col))
      {
         return this.image_data[row][col] = value;
      }
      return false;
   }
   
   public boolean getPixel(int row, int col)
   {
      //System.out.println("getPixel");
      if(isValid(row, col))
      {
         return image_data[row][col];
      }
   
      return false;
   }
   
   private boolean isValid(int row, int col)
   {
      return ((row >= 0 && row < MAX_HEIGHT) && (col >=0 && col < MAX_WIDTH));
   }
    
   public void displayToConsole()
   {
      for (int i = 0; i < MAX_HEIGHT; i++)
      {
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            if (this.getPixel(i, j))
            {
               System.out.print("*");
            }
            else
            {
               System.out.print(" ");
            }
         }
         System.out.println();
      }
   }
   
   public BarcodeImage clone() throws CloneNotSupportedException
   {
      BarcodeImage newObject = (BarcodeImage)super.clone();
      newObject.image_data = image_data.clone();
      return newObject;
   }
   
   public static int getMaxHeight()
   {
      return MAX_HEIGHT;
   }
   public static int getMaxWidth()
   {
      return MAX_WIDTH;
   }
} 

/*
Accessor and mutator for each bit in the image:  boolean getPixel(int row, int col) and boolean setPixel(int row, int col, boolean value);   For the getPixel(), you can use the return value for both the actual data and also the error condition -- so that we don't "create a scene" if there is an error; we just return false.
Optional - A private utility method is highly recommended, but not required:  checkSize(String[] data)  It does the job of checking the incoming data for every conceivable size or null error.  Smaller is okay.  Bigger or null is not.
Optional - A displayToConsole() method that is useful for debugging this class, but not very useful for the assignment at large.
A clone() method that overrides the method of that name in Cloneable interface.    
   */

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
   Constructors.  Three minimum, but you could have more:
   Default Constructor -  constructs an empty, but non-null, image and text value.  The initial image should be all white, however, actualWidth and actualHeight should start at 0, so it won't really matter what's in this default image, in practice.  The text can be set to blank, "", or something like "undefined".
   */
   DataMatrix(){
      this.image = null;
      this.actualWidth = 0;
      this.actualWidth = 0;
      this.text = "";
   }

   /* - sets the image but leaves the text at its default value.  Call scan() and avoid duplication of code here.*/
   DataMatrix(BarcodeImage image){
      scan(image);
   }
   
   /*
    - sets the text but leaves the image at its default value. Call readText() and avoid duplication of code here.
   */
   DataMatrix(String text){
      readText(text);
   }
   
   public int getActualWidth()
   {
      return actualWidth;
   }
   
   public int getActualHeight()
   {
      return actualHeight;
   }
   
   //Accessors for actualWidth and actualHeight but no mutators! (why?)
   /*
          - Assuming that the image is correctly situated in the lower-left corner of the larger boolean array, these methods use the "spine" of the array (left and bottom BLACK) to determine the actual size.
   */
   private int computeSignalWidth(){
      int width = 0;
 
      for(int col = 0; col < BarcodeImage.getMaxWidth(); col++)
      {
         if(image.getPixel((BarcodeImage.getMaxHeight()) - 1, col) == true)
         {
            width++;
         }
      }
      return width;
   }
   
   private int computeSignalHeight(){
      int height = 0;
      
      for(int row = 0; row < BarcodeImage.getMaxHeight(); row++)
       {
         if(image.getPixel(row, 0) == true)
         {
            height++;
         }
       }
       return height;
   }

   //Implementation of all BarcodeIO methods.
   /*
    accepts some image, represented as a BarcodeImage object to be described below, and stores a copy of this image.  Depending on the sophistication of the implementing class, the internally stored image might be an exact clone of the parameter, or a refined, cleaned and processed image.  Technically, there is no requirement that an implementing class use a BarcodeImage object internally, although we will do so.  For the basic DataMatrix option, it will be an exact clone.  Also, no translation is done here - i.e., any text string that might be part of an implementing class is not touched, updated or defined during the scan.
   */
   public boolean scan(BarcodeImage bc){
      try
      {
         image = bc.clone();

      }
      catch(CloneNotSupportedException e) //Exception e
      {
         //nothing is done
      }
      cleanImage();
      actualWidth = computeSignalWidth();
      actualHeight = computeSignalHeight();
      return true;
   }
   
   /*
   accepts a text string to be eventually encoded in an image. No translation is done here - i.e., any BarcodeImage that might be part of an implementing class is not touched, updated or defined during the reading of the text.
   */
   public boolean readText(String text){
      if(text.length() > BarcodeImage.getMaxWidth() - 2)
      {
         return false;
      }
      else
      {
         this.text = text;
         return true;
      }
   }
   
   /*
   Not technically an I/O operation, this method looks at the internal text stored in the implementing class and produces a companion BarcodeImage, internally (or an image in whatever format the implementing class uses).  After this is called, we expect the implementing object to contain a fully-defined image and text that are in agreement with each other.
   */
   public boolean generateImageFromText(){
      if(text.length() > BarcodeImage.getMaxWidth() - 2)
      {
         return false;
      }
      cleanImage();
      char colChar;
      byte charVal;
      int k;
      
      //fill borders on the right and left
      for(k = BarcodeImage.getMaxHeight() - 1; k > BarcodeImage.getMaxHeight() - 11; k--)
      {
         //sets left
         image.setPixel(k, 0, true);
         if(k % 2 == 1)
         {
            //sets right
            image.setPixel(k, text.length() + 1, true);
         }
      }
         
         //fill borders bottom and top
       for(k = 1; k < text.length() + 1; k++)
       {
          //sets bottom
          image.setPixel(BarcodeImage.getMaxHeight() - 1, k, true);
          if(k % 2 == 0)
          {
             //sets top
             image.setPixel(BarcodeImage.getMaxHeight() - 10, k, true);
          }
          
          //Filling columns
          colChar = text.charAt(k - 1);
          charVal = (byte)colChar;
          if(!writeCharToCol(k, charVal))
          {
             return false;
          }  
       }
       //Use new image data to set actualHeight and actualWidth
       actualWidth = computeSignalWidth();
       actualHeight = computeSignalHeight();
       return true;
   }
   
   private boolean writeCharToCol(int k, int charVal)
   {
      if((k < 1 || k >= BarcodeImage.getMaxWidth() || charVal < 0 || charVal > 127))
      {
         return false;
      }
      
      //actualHeight can't be used yet because it hasn't been set for new image
      for(int i = 0, row = BarcodeImage.getMaxHeight() - 9; i < 8; i++, row++)
      {
         if((charVal & (1 << (7 - i))) > 0)
         {
            image.setPixel(row, k, true);
         }
      }
      return true;
   }

   /*
    Not technically an I/O operation, this method looks at the internal image stored in the implementing class, and produces a companion text string, internally.  After this is called, we expect the implementing object to contain a fully defined image and text that are in agreement with each other.
   */
   public boolean translateImageToText(){
      int column;
      String newText = "";
      for(column = 1; column < actualWidth - 1; column++)
      {
         newText += readCharFromCol(column);
      }
      return readText(newText);
   }

   private char readCharFromCol(int column)
   {
      int powValue, row;
      byte charValue = 0;
      
      for(row = BarcodeImage.getMaxHeight() - 9, powValue = 7;
          row < BarcodeImage.getMaxHeight() - 1; row++, powValue--)
      {
         if(image.getPixel(row, column))
         {
            charValue += (byte) Math.pow(2, powValue);
         }
      }
      return (char)charValue;
   }

   /*
   prints out the text string to the console.
   */
   public void displayTextToConsole(){
         System.out.println(text);
      }

   /*
   prints out the image to the console.  In our implementation, we will do this in the form of a dot-matrix of blanks and asterisks, e.g.,
   */
   public void displayImageToConsole(){
      //these two println are for reference only, can be deleted at the end
      System.out.println("\n");
         int column, row;
         
         //border top row
         for(column = 0; column < actualWidth + 2; column++)
         {
            System.out.print("-");
         }
         System.out.print("\n");
         
         //added border characters for rows
         for(row = BarcodeImage.getMaxHeight() - actualHeight;
             row < BarcodeImage.getMaxHeight(); row++)
         {
            System.out.print("|");
            for(column = 0; column < actualWidth; column++)
            {
               if(image.getPixel(row, column))
               {
                  System.out.print("*");
               }
               else
               {
                  System.out.print(" ");
               }
            }
            System.out.print("|\n");
         }
         //border bottom row
         for(column = 0; column < actualWidth + 2; column++)
         {
            System.out.print("-");
         }
         System.out.print("\n");
      }

   //Private method:
   /* - This private method will make no assumption about the placement of the "signal" within a passed-in BarcodeImage.  In other words, the in-coming BarcodeImage may not be lower-left justified.  Here's an example of  the placement of such an un-standardized image:
   */
   private void cleanImage(){
         int inputColumn, inputRow;
         int outputColumn = 0;
         int outputRow = 0; 
         int offsetColumn = 0;
         int offsetRow = 0;
         boolean offsetFound = false;        

         // Find the lower left corner
         for (inputColumn = 0; inputColumn < BarcodeImage.getMaxWidth(); inputColumn++){
            for (inputRow =  BarcodeImage.getMaxHeight(); inputRow > 0;  inputRow--){
               if(image.getPixel(inputRow, inputColumn) & (offsetFound == false)){
                  offsetColumn = inputColumn;
                  offsetRow = BarcodeImage.getMaxHeight() - inputRow-1;  
                  offsetFound = true;  
               }
               if(offsetFound){
                  image.setPixel(inputRow+offsetRow, inputColumn-offsetColumn, image.getPixel(inputRow, inputColumn));  
                  image.setPixel(inputRow, inputColumn,false);
               } 
            }
         }
      return;
   }
}

/*  Program Output

Optical Barcode Reader

CSUMB CSIT online program is top notch.


-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|
-------------------------------------------
You did it!  Great work.  Celebrate.


----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
----------------------------------------
What a great resume builder this is!


----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|***** * ***** ****** ******* **** **  |
|* ************************************|
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *  *|
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  * *|
|** *  * * **   *****  **  *    ** *** |
|**************************************|
----------------------------------------

*/
