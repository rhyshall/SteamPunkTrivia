package generator;


import java.util.Random;
import common.Constants;

public class GenBlackHoles 
{
  private static boolean[] blackHoles; /* true means black hole exists at given index */
  private static int[] blackList;
  private static int holeCnt;
  
  public GenBlackHoles()
  {
	initBlackHoles();
	initBlackList();
	genCoords();
  }
  
  public int[] get()
  {
	int[] blackHoleList;
	int i = 0;
	int index = 0;
	int coordCnt = 0;
	blackHoleList = new int[holeCnt];
	coordCnt = blackHoles.length;
	
	//create list of black hole indexes
	for (i= 0; i < coordCnt; i++)
	{
	  if (blackHoles[i] == true)
	  {
		blackHoleList[index] = i;
		index++;
	  }
	}
	
	//shuffle black hole index list
	blackHoleList = shuffleList(blackHoleList);
	
	return blackHoleList;
  }
  
  private static int[] shuffleList(int[] blackHoleList)
  {
    int i;
	Random randGen = new Random();
	int indexOne = 0;
	int indexTwo = 0;
	int upperBound = 0;
	int temp = 0;
	
	upperBound = blackHoleList.length;
	
	for (i = 0; i < 100; i++)
	{
	  indexOne = randGen.nextInt(upperBound);
	  indexTwo = randGen.nextInt(upperBound);
	  
	  temp = blackHoleList[indexOne];
	  blackHoleList[indexOne] = blackHoleList[indexTwo];
	  blackHoleList[indexTwo] = temp;
	}
	
	return blackHoleList;
  }
  
  private static void genCoords()
  {
	int i;
	Random randGen = new Random();
	int index = 0;
	int prevIndex = 0;
	int maxHoleCnt = Constants.MAX_BLACK_HOLE_CNT;
	int minHoleCnt = Constants.MIN_BLACK_HOLE_CNT;
	int upperBound = 0;
	int joinIndex = 0;
	boolean doJoin = false;
	
	upperBound = maxHoleCnt - minHoleCnt;
	
	holeCnt = randGen.nextInt(upperBound) + minHoleCnt;
	
	for (i = 0; i < holeCnt; i++)
	{
	  if (doJoin == true)
	  {
		//join next black hole to previous index
	    index = genJoinCoord(prevIndex);	    
	    doJoin = false;
	  }
	  
	  else
	  {
		//choose random index for next black hole
	    index = genCoord(); 
	  }
	  
	  blackHoles[index] = true;
	  
	  //if 0,1,2,3 or 4: choose random index
	  //if 5 or 6, join to previously generated coordinate
	  joinIndex = randGen.nextInt(7);
	  
	  if (joinIndex > 4)
	  {
		if (canJoin(index) == true)
		{		  
		  doJoin = true;
		}
		
		else
		{
		  doJoin = false;
		}
	  }
	  
	  prevIndex = index;
	}
  }
  
  private static int genCoord()
  {
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	int vertSquareCnt = Constants.VERT_SQUARE_CNT;
	int upperBound = horSquareCnt * vertSquareCnt;
	Random randGen = new Random();
	int index = 0;
    
	while (true)
	{
	  index = randGen.nextInt(upperBound);
	 
      if (blackHoles[index] == false)
      {
	    if (inBlackList(index) == false)
	    {
	      break;
	    }
      }
	}
	
	return index;
  }
  
  private static int genJoinCoord(int index)
  {
    Random randGen = new Random();
	int direction;
	int joinIndex = 0;
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	boolean breakLoop = false;
		
	while (breakLoop == false)
	{
	  direction = randGen.nextInt(Constants.DIRECTION_CNT);

	  switch (direction)
	  {
	    case Constants.UP: 
	    {
		  if (canJoinUp(index) == true)
		  {
			joinIndex = index - horSquareCnt;
			breakLoop = true;
			
			break;
		  }
		  
		  else
		  {
			continue;
		  }
	    }
	    
	    case Constants.RIGHT: 
	    {
	      if (canJoinRight(index) == true)
	      {
	    	joinIndex = index + 1;
	    	breakLoop = true;
	    	
	    	break;
	      }
	      
	      else 
	      {
	    	continue;
	      }
	    }
	    
	    case Constants.DOWN: 
	    {
		  if (canJoinDown(index) == true)
		  {
			joinIndex = index + horSquareCnt;
			breakLoop = true;
			
			break;
		  }
		  
		  else
		  {
			continue;
		  }
	    }
	    
	    case Constants.LEFT: 
	    {
	      if (canJoinLeft(index) == true)
	      {
	    	joinIndex = index - 1;
	    	breakLoop = true;
	    	
	    	break;
	      }
	      
	      else 
	      {
	    	continue;
	      }
	    }
	  }
	}
	
	return joinIndex;
  }
  
  private static boolean canJoin(int index)
  {  
	if (canJoinUp(index) == true)
	{
	  return true;
	}
	
	if (canJoinRight(index) == true)
	{
	  return true;
	}
	
	if (canJoinDown(index) == true)
	{
	  return true;
	}
	
	if (canJoinLeft(index) == true)
	{
	  return true;
	}
	
	return false;
  }
  
  private static boolean canJoinUp(int index)
  {
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	boolean canJoinUp = true;
	
	//if at top boundary
	if (index < horSquareCnt)
	{
	  canJoinUp = false;
	}
	
	else
	{ 
	  //if "up" square already selected
	  if (inBlackList(index-horSquareCnt) == true)
	  {
	    canJoinUp = false;
	  }
	}
	
	return canJoinUp;
  }
  
  private static boolean canJoinRight(int index)
  {
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	boolean canJoinRight = true;

	//if at right boundary
	if ((index+1)%(horSquareCnt) == 0)
	{
	  canJoinRight = false;
	}
	
	else
	{
	  //if "right" square already selected
	  if (inBlackList(index+1) == true)
	  {
	    canJoinRight = false;
	  }
	}
	
	return canJoinRight;
  }
  
  private static boolean canJoinDown(int index)
  {
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	int vertSquareCnt = Constants.VERT_SQUARE_CNT;
	int upperBound = horSquareCnt * vertSquareCnt;
	boolean canJoinDown = true;
	
	//if at bottom boundary
	if (index >= (upperBound-horSquareCnt))
	{
	  canJoinDown = false;
	}
	
	else
	{ 
	  //if "down" square already selected
	  if (inBlackList(index+horSquareCnt) == true)
	  {
	    canJoinDown = false;
	  }
	}
	
	return canJoinDown;
  }
  
  private static boolean inBlackList(int index)
  {
	int i;
	int blackListSize = 0;
	boolean inBlackList = false;
	
	blackListSize = blackList.length;
	  
    for (i = 0; i < blackListSize; i++)
	{
	  if (blackList[i] == index)
	  {
	    inBlackList = true;
	    break;
	  }
	}
    
    return inBlackList;
  }

  private static boolean canJoinLeft(int index)
  {
	int horSquareCnt = Constants.HOR_SQUARE_CNT;
	boolean canJoinLeft = true;
	
	//if at left boundary
	if ((index+horSquareCnt)%horSquareCnt == 0)
	{
	  canJoinLeft = false;
	}
	
	else
	{
	  //if "left" square already selected
	  if (inBlackList(index-1) == true)
	  {
	    canJoinLeft = false;
	  }
	}
	
	return canJoinLeft;
  }
  
  private static void initBlackHoles()
  {
	int upperBound = 0;
	int i;
			
	upperBound = Constants.HOR_SQUARE_CNT * Constants.VERT_SQUARE_CNT;
	blackHoles = new boolean[upperBound];
		
	for (i = 0; i < upperBound; i++)
	{
	  blackHoles[i] = false;
	}
	
	holeCnt = 0;
  }
  
  private static void initBlackList()
  {
    int horSquareCnt = Constants.HOR_SQUARE_CNT;
	int vertSquareCnt = Constants.VERT_SQUARE_CNT;
	int upperBound = (horSquareCnt * vertSquareCnt)-1;	  
	
    blackList = new int[]{upperBound-(horSquareCnt/2), /* player square */
                          upperBound-(horSquareCnt/2)-1, /* left of player square */
                          upperBound-(horSquareCnt/2)-2, /* two-left of player square */
                          upperBound-(horSquareCnt/2)+1, /* right of player square */
                          upperBound-(horSquareCnt/2)+2, /* two-right of player square */
                          upperBound-(horSquareCnt/2)-horSquareCnt, /* top of player square */
                          upperBound-(horSquareCnt/2)-horSquareCnt-1, /* top left of player square */
                          upperBound-(horSquareCnt/2)-horSquareCnt+1, /* top right of player square */
                          upperBound-(horSquareCnt/2)-(horSquareCnt*2), /* two-top of player square */
                          horSquareCnt/2}; /* finish square */
  }
}
