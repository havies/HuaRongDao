package edu.bjfu.klotski.core.Interfaces;

import edu.bjfu.klotski.core.BaseComponent.ChessStep;

public interface ITreeList {

	void ClearAll();

	void insertNode(ChessStep c);

	ChessStep[] TraceResult(ChessStep cStep);

	void MoveCurrentToNext();

}
