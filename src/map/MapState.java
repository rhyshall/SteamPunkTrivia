package map;

import common.Constants;
import generator.GenQuizSquare;


public class MapState 
{
  public static int[][] mapConfig;
  public static int playerXPos;
  public static int playerYPos;
  public static QuizSquare activeQuizSquare;
	
  public static void construct()
  {
    initMapConfig();
    activeQuizSquare = new QuizSquare();
  }
  
  public static boolean canMoveDown()
  {
	boolean canMove = true;
	
	if (playerYPos >= (Constants.VERT_SQUARE_CNT-1))
	{
	  canMove = false;
	}
	 
	else 
	{
	  if ((mapConfig[playerXPos][playerYPos+1] != Constants.GROUND_SQUARE)
	    && (mapConfig[playerXPos][playerYPos+1] != Constants.FINISH_SQUARE))
	  {
		canMove = false; 
	  }
	}
	
	return canMove;
  }
  
  public static boolean canMoveRight()
  {
	boolean canMove = true;
	
	if (playerXPos >= (Constants.HOR_SQUARE_CNT-1))
	{
	  canMove = false;
	}
	
	else 
	{
	  if ((mapConfig[playerXPos+1][playerYPos] != Constants.GROUND_SQUARE)
	    && (mapConfig[playerXPos+1][playerYPos] != Constants.FINISH_SQUARE))
	  {
		canMove = false;
	  }
	}
	
	return canMove;
  }
  
  public static boolean canMoveUp()
  {
	boolean canMove = true;
	  
	if (playerYPos <= 0)
	{
      canMove = false;
	}
		
	else 
	{
	  if ((mapConfig[playerXPos][playerYPos-1] != Constants.GROUND_SQUARE)
		&& (mapConfig[playerXPos][playerYPos-1] != Constants.FINISH_SQUARE))
	  {
	    canMove = false;
	  }
	} 
	
	return canMove;
  }

  public static boolean canMoveLeft()
  {
	boolean canMove = true;
	
	if (playerXPos <= 0)
	{
	  canMove = false;
	}
	
	else 
	{
	  if ((mapConfig[playerXPos-1][playerYPos] != Constants.GROUND_SQUARE)
		&& (mapConfig[playerXPos-1][playerYPos] != Constants.FINISH_SQUARE))
	  {
		canMove = false;
	  }
	}
	
	return canMove;
  }
  
  public static void movePlayerUp()
  {
	int nextSquareType; 
		
	nextSquareType = mapConfig[playerXPos][playerYPos-1];	
	removeQuizSquares();
	  
	mapConfig[playerXPos][playerYPos] = Constants.GROUND_SQUARE;
	mapConfig[playerXPos][playerYPos-1] = Constants.PLAYER_SQUARE;	
	playerYPos = playerYPos - 1;
	
	if (nextSquareType == Constants.FINISH_SQUARE)
	{
	  MapGUI.victory = true;
	}
	
	else
	{
	  syncQuizSquares();
	}
  }
  
  public static void movePlayerRight()
  {
	int nextSquareType; 
	
	nextSquareType = mapConfig[playerXPos+1][playerYPos];	
	removeQuizSquares();
	
	mapConfig[playerXPos][playerYPos] = Constants.GROUND_SQUARE;
	mapConfig[playerXPos+1][playerYPos] = Constants.PLAYER_SQUARE;			
	playerXPos = playerXPos + 1;
	
	if (nextSquareType == Constants.FINISH_SQUARE)
	{
	  MapGUI.victory = true;
	}
	
	else
	{
	  syncQuizSquares();
	}
  }
  
  public static void movePlayerDown()
  { 
	int nextSquareType; 
	
	nextSquareType = mapConfig[playerXPos][playerYPos+1];	  
	removeQuizSquares();
	
	mapConfig[playerXPos][playerYPos] = Constants.GROUND_SQUARE;
	mapConfig[playerXPos][playerYPos+1] = Constants.PLAYER_SQUARE;	
	playerYPos = playerYPos + 1;
	
	if (nextSquareType == Constants.FINISH_SQUARE)
	{
	  MapGUI.victory = true;
	}
	
	else
	{
	  syncQuizSquares();
	}
  }
  
  public static void movePlayerLeft()
  {
	int nextSquareType; 
		
	nextSquareType = mapConfig[playerXPos-1][playerYPos];
	removeQuizSquares();
	  
	mapConfig[playerXPos][playerYPos] = Constants.GROUND_SQUARE;
	mapConfig[playerXPos-1][playerYPos] = Constants.PLAYER_SQUARE;	
	playerXPos = playerXPos - 1;
	
	if (nextSquareType == Constants.FINISH_SQUARE)
	{
	  MapGUI.victory = true;
	}
	
	else
	{
	  syncQuizSquares();
	}
  }
  
  public static boolean isBesideQueston()
  {
	//if player not on farthest top block
	if (playerYPos < (Constants.VERT_SQUARE_CNT-1))
	{
	  //if next block up is quiz block
	  if (mapConfig[playerXPos][playerYPos+1] == Constants.QUIZ_SQUARE)
	  {
		return true;
	  }
	}
	
	//if player not on farthest right block
	if (playerXPos < (Constants.HOR_SQUARE_CNT-1))
	{
      //if block to right is quiz block
	  if (mapConfig[playerXPos+1][playerYPos] == Constants.QUIZ_SQUARE)
	  {
		return true;
	  }
	}
	
	//if player not on farthest bottom block
	if (playerYPos > 0)
	{
	  //if next block up is quiz block
	  if (mapConfig[playerXPos][playerYPos-1] == Constants.QUIZ_SQUARE)
	  {
		return true;
	  }
	}
	
	//if player not on farthest left block
	if (playerXPos > 0)
	{
	  //if block to left is quiz block
	  if (mapConfig[playerXPos-1][playerYPos] == Constants.QUIZ_SQUARE)
	  {
		return true;
	  }
	}
	
	return false;
  }
  
  public static void setActiveQuiz(GenQuizSquare quizSquare)
  {
	activeQuizSquare.xPos = quizSquare.xPos;
	activeQuizSquare.yPos = quizSquare.yPos;
  }
  
  private static void initMapConfig()
  {
	int vertSquareCnt = Constants.VERT_SQUARE_CNT;
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	
	//initialize memory for map state data
	mapConfig = new int[horSquareCnt][vertSquareCnt];
	
	//initialize whole map state with block obstacles
	initBlockSquares(vertSquareCnt,
			         horSquareCnt);
	
	//initialize player's original starting position
	initPlayerSquare(vertSquareCnt,
	                 horSquareCnt);
	
	//initialize first question squares
	initQuizSquares(vertSquareCnt,
	                horSquareCnt);
	
	//initialize finish line
	initFinishSquare(vertSquareCnt,
	                 horSquareCnt);
  }
  
  private static void initBlockSquares(int vertSquareCnt, int horSquareCnt)
  {
	int blockSquare = Constants.BLOCK_SQUARE;
	int i = 0;
	int j = 0;
	
	for (i = 0; i < horSquareCnt; i++)
	{	  
	  for (j = 0; j < vertSquareCnt; j++)
	  {
		mapConfig[i][j] = blockSquare;
	  }
	}
  }
  
  private static void initPlayerSquare(int vertSquareCnt, 
		                               int horSquareCnt)
  {
    mapConfig[horSquareCnt/2][vertSquareCnt-1] = Constants.PLAYER_SQUARE;
    
    playerXPos = horSquareCnt / 2; 
    playerYPos = vertSquareCnt-1;
  }
  
  private static void initQuizSquares(int vertSquareCnt, 
		                              int horSquareCnt)
  {
	int quizSquare = Constants.QUIZ_SQUARE;
	
	mapConfig[(horSquareCnt/2)-1][vertSquareCnt-1] = quizSquare;
	mapConfig[horSquareCnt/2][vertSquareCnt-2] = quizSquare;
	mapConfig[(horSquareCnt/2)+1][vertSquareCnt-1] = quizSquare;
  }
  
  private static void initFinishSquare(int vertSquareCnt, 
		                               int horSquareCnt)
  {
	mapConfig[horSquareCnt/2][0] = Constants.FINISH_SQUARE;
  }
  
  private static void removeQuizSquares()
  {
	if (playerYPos < (Constants.VERT_SQUARE_CNT-1))
	{
      if (mapConfig[playerXPos][playerYPos+1] == Constants.QUIZ_SQUARE)
	  {
	    mapConfig[playerXPos][playerYPos+1] = Constants.BLOCK_SQUARE;
	  }
	}
	
	if (playerYPos > 0)
	{
	  if (mapConfig[playerXPos][playerYPos-1] == Constants.QUIZ_SQUARE)
	  {
	    mapConfig[playerXPos][playerYPos-1] = Constants.BLOCK_SQUARE;
	  }
	}
	
	if (playerXPos > 0)
	{
	  if (mapConfig[playerXPos-1][playerYPos] == Constants.QUIZ_SQUARE)
	  {
	    mapConfig[playerXPos-1][playerYPos] = Constants.BLOCK_SQUARE;
	  }
    }
  
	if (playerXPos < (Constants.HOR_SQUARE_CNT-1))
	{
	  if (mapConfig[playerXPos+1][playerYPos] == Constants.QUIZ_SQUARE)
	  {
	    mapConfig[playerXPos+1][playerYPos] = Constants.BLOCK_SQUARE;
	  }
    }
  }
  
  private static void syncQuizSquares()
  {
	if (playerYPos < (Constants.VERT_SQUARE_CNT-1))
	{
      if (mapConfig[playerXPos][playerYPos+1] == Constants.BLOCK_SQUARE)
	  {
	    mapConfig[playerXPos][playerYPos+1] = Constants.QUIZ_SQUARE;
	  }
    }
	    
	if (playerYPos > 0)
	{
	  if (mapConfig[playerXPos][playerYPos-1] == Constants.BLOCK_SQUARE)
	  {
	    mapConfig[playerXPos][playerYPos-1] = Constants.QUIZ_SQUARE;
      }
	}
	 
	if (playerXPos > 0)
	{
	  if (mapConfig[playerXPos-1][playerYPos] == Constants.BLOCK_SQUARE)
	  {
	    mapConfig[playerXPos-1][playerYPos] = Constants.QUIZ_SQUARE;
	  }
	}
	      
	if (playerXPos < (Constants.HOR_SQUARE_CNT-1))
	{
	  if (mapConfig[playerXPos+1][playerYPos] == Constants.BLOCK_SQUARE)
	  {
	    mapConfig[playerXPos+1][playerYPos] = Constants.QUIZ_SQUARE;
	  }
	}
  }
}
