package generator;

import java.util.Random;

import map.QuizSquares;

public class GenFlashCnt 
{
  public static int flashCnt;
	
  public GenFlashCnt()
  {
	Random randGen;
	int squareCnt;
	
	randGen = new Random();
	squareCnt = QuizSquares.entries.size();
	
	if (squareCnt >= 3)
	{
	  flashCnt = randGen.nextInt(14) + 2;
	}
	
	else
	{
	  flashCnt = randGen.nextInt(8) + 1;
	}
  }
}
