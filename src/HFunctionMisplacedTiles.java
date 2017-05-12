import sac.State;
import sac.StateFunction;

public class HFunctionMisplacedTiles extends StateFunction
{
    @Override
    public double calculate(State state)
    {
        byte value = 0;
        double numberOfTiles = 0;
        Puzzle puzzle = (Puzzle)state;
        for(int i = 0; i < puzzle.getN(); i++)
        {
            for(int j = 0; j < puzzle.getN(); j++)
            {
                if((puzzle.board[i][j] != 0) && (puzzle.board[i][j] != value) )
                {
                    numberOfTiles += 1;
                }
            }
            value++;
        }
        return numberOfTiles;
    }
}