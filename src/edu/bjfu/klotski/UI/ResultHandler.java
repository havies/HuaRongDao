package edu.bjfu.klotski.UI;


import java.util.logging.ConsoleHandler;

import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.Interfaces.IResultHandler;

public class ResultHandler implements IResultHandler{

	
	public void HandleResult(ChessStep[] steps) {
		// TODO Auto-generated method stub
		int x, y, p;

		for(int i=0; i<steps.length - 1; i++)
		{
			System.out.printf("%d :", i+1);
			System.out.println("");
			x = steps[i].chessmanPosition.x;
			y = steps[i].chessmanPosition.y;
			p = x + y * 4;
			//ShowHelp.ShowLayout(steps[i].layout, p);
			System.out.printf("Move (%d,%d) {%s}", x, y, steps[i].moveMethod);
			System.out.println("");
			System.out.println("");
		}


		System.out.println("Got the Answer!");
		ShowHelp.ShowLayout(steps[steps.length-1].layout, -1);
	}

	
	public void HandleInfo(int currentStep) {
		// TODO Auto-generated method stub
		System.out.println(currentStep);
	}

}
