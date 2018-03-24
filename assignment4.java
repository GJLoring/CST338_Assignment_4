/*
 * Assignment M4 Optical Barcode Readers
 * Program Description TODO
 * Authors: Christian Guerrero, Jose Garcia, Grace Alvarez, Gabriel Loring
 * Last Changed: March 21th, 2018
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


/*********************PHASE 2 ***************************/
/********************************************************/

//Phase 2:
class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;    //The exact internal dimensions of 2D data. 
   public static final int MAX_WIDTH = 65;    //The exact internal dimensions of 2D data. 
   private boolean[][] image_data;            //This is where to store your image.
   
   //Constructors  Two minimum, but you could have others:
   //Default Constructor -  instantiates a 2D array (MAX_HEIGHT x MAX_WIDTH) and stuffs it all with blanks (false).
   
   public BarcodeImage()
   {
	   System.out.println("Default Constructor");
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
	 System.out.println("2nd Constructor");
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
	   System.out.println("setPixel");
	   if(isValid(row, col))
	   {
		   return this.image_data[row][col] = value;
	   }
	   return false;
   }
   
   
   
   public boolean getPixel(int row, int col)
   {
	   System.out.println("getPixel");
	   if(isValid(row, col))
	   {
		   return this.image_data[row][col];
	   }
	
	   return false;
   }
   

   
   private boolean isValid(int row, int col)
   {
	   System.out.println("isValid");
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
   
   public BarcodeImage clone()
   {
	   try {
		   return(BarcodeImage) super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
		   return null;
	   }
   }
} 

/*
Accessor and mutator for each bit in the image:  boolean getPixel(int row, int col) and boolean setPixel(int row, int col, boolean value);   For the getPixel(), you can use the return value for both the actual data and also the error condition -- so that we don't "create a scene" if there is an error; we just return false.
Optional - A private utility method is highly recommended, but not required:  checkSize(String[] data)  It does the job of checking the incoming data for every conceivable size or null error.  Smaller is okay.  Bigger or null is not.
Optional - A displayToConsole() method that is useful for debugging this class, but not very useful for the assignment at large.
A clone() method that overrides the method of that name in Cloneable interface.    
   */

/******************** END PHASE 2 ***********************/
/********************************************************/


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
      this.actualWidth=0;
      this.actualWidth=0;
      this.text="";
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
   
   //Accessors for actualWidth and actualHeight but no mutators! (why?)
   /*
          - Assuming that the image is correctly situated in the lower-left corner of the larger boolean array, these methods use the "spine" of the array (left and bottom BLACK) to determine the actual size.
   */
   private int computeSignalWidth(){
      return 0;
   }
   private int computeSignalHeight(){
         return 0;
      }

   //Implementation of all BarcodeIO methods.
   /*
    accepts some image, represented as a BarcodeImage object to be described below, and stores a copy of this image.  Depending on the sophistication of the implementing class, the internally stored image might be an exact clone of the parameter, or a refined, cleaned and processed image.  Technically, there is no requirement that an implementing class use a BarcodeImage object internally, although we will do so.  For the basic DataMatrix option, it will be an exact clone.  Also, no translation is done here - i.e., any text string that might be part of an implementing class is not touched, updated or defined during the scan.
   */
   public boolean scan(BarcodeImage bc){
      try
      {
         this.image = bc.clone();
         cleanImage();
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
         return true;
      }
      catch(Exception e) //catch(CloneNotSupportedException e)
      {
         //empty, no action
      }
      return false;
   }
   
   /*
   accepts a text string to be eventually encoded in an image. No translation is done here - i.e., any BarcodeImage that might be part of an implementing class is not touched, updated or defined during the reading of the text.
   */
   public boolean readText(String text){
      if(text == null)
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
     if(text == null)
      {
         return false;
      }
      else
      {
         image = new BarcodeImage();
         for(int i = 0; i < text.length(); i++)
         {
            image.setPixel(BarcodeImage.MAX_HEIGHT - 1, i, true);
            WriteCharToCol(0, 255);
         }
         for(int i = 1; i < text.length() + 1; i++)
         {
            if(!WriteCharToCol(i, (int) text.charAt(i)))
            {
               return false;
            }
         }
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
         return true;
      }
   }
   
   /*
    Not technically an I/O operation, this method looks at the internal image stored in the implementing class, and produces a companion text string, internally.  After this is called, we expect the implementing object to contain a fully defined image and text that are in agreement with each other.
   */
   public boolean translateImageToText(){
         String newText = "";
      int characterValue;
      
      if(image == null)
      {
         return false;
      }
      else
      {
         for(int i = 0; i < text.length(); i++)
         {
            characterValue = 0;
            for(int j = BarcodeImage.MAX_HEIGHT - 2; j > BarcodeImage.MAX_HEIGHT - 10; j--)
            {
               if(image.getPixel(j, i))
               {
                  characterValue += Math.pow(2, j - BarcodeImage.MAX_HEIGHT - 2);
               }
               newText += (char) characterValue;
            }
         }
         text = newText;
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
         return true;
      }
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
          for(int i = 0; i < text.length() + 1; i++)
      {
         for(int j = BarcodeImage.MAX_HEIGHT - actualHeight - 10; j < BarcodeImage.MAX_HEIGHT; j--)
         {
            if(image.getPixel(i, j))
            {
               System.out.println(BLACK_CHAR);
            }
            else
            {
               System.out.println(WHITE_CHAR);
            }
         }
      }
      }

   private boolean WriteCharToCol(int col, int code){
      return true;
   }

   //Private method:
   /* - This private method will make no assumption about the placement of the "signal" within a passed-in BarcodeImage.  In other words, the in-coming BarcodeImage may not be lower-left justified.  Here's an example of  the placement of such an un-standardized image:
   */
   private void cleanImage(){
      return;
   }
}

