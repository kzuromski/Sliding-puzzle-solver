import sac.State;
import sac.StateFunction;

public class HFunctionMisplacedTiles extends StateFunction
{
    @Override
    public double calculate(State state)
    {
        int value = 0;
        double numberOfTiles = 0;
        Puzzle puzzle = (Puzzle)state;
        for(int i = 0; i < puzzle.getN(); i++)
        {
            for(int j = 0; j < puzzle.getN(); j++)
            {
                if((i != puzzle.getX() && j != puzzle.getY()) && (puzzle.board[i][j] != value) )
                {
                    numberOfTiles += 1;
                }
            }
            value++;
        }
        return numberOfTiles;
    }
}