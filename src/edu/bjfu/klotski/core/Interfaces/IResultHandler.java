package edu.bjfu.klotski.core.Interfaces;

import edu.bjfu.klotski.core.BaseComponent.ChessStep;

public interface IResultHandler {

	void HandleResult(ChessStep[] s);
	void HandleInfo(int currentStep);
}
