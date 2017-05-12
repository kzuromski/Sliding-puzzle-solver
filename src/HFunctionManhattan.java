import sac.State;
import sac.StateFunction;

public class HFunctionManhattan extends StateFunction
{
    @Override
    public double calculate(State state)
    {
        double numberOfTiles = 0;
        Puzzle puzzle = (Puzzle) state;
        for(int i = 0; i < puzzle.getN(); i++)
        {
            for(int j = 0; j < puzzle.getN(); j++)
            {
                numberOfTiles += manhattanDistance(puzzle, i, j);
            }
        }
        return numberOfTiles;
    }
    public int manhattanDistance(Puzzle puzzle, int i, int j)
    {
        return Math.abs(puzzle.board[i][j] / puzzle.getN() - i) + Math.abs(puzzle.board[i][j] % puzzle.getN() - j);
    }


}
