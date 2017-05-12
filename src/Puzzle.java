import sac.StateFunction;
import sac.graph.AStar;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

import java.util.*;


public class Puzzle extends GraphStateImpl
{
    private final int n = 3;
    private final static int up = 1;
    private final static int down = 2;
    private final static int left = 3;
    private final static int right = 4;
    private int x;
    private int y;
    byte[][] board;

    public Puzzle()
    {
        board = new byte[n][n];
        byte value = 0;
        for(int i = 0; i<n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                board[i][j] = value++;
            }
        }
        x = 0;
        y = 0;
        toString();
    }
    public Puzzle(Puzzle parent)
    {
        board = new byte[n][n];
        for(int i = 0; i<n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                board[i][j] = parent.board[i][j];
            }
        }
        x = parent.x;
        y = parent.y;
        toString();
    }
    public int getN()
    {
        return this.n;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public byte[][] getBoard()
    {
        return this.board;
    }

    public void shuffleBoard(int moves, Puzzle puzzle)
    {
        Random rand = new Random();
        for(int i = 0; i < moves; i++)
        {
            ArrayList<Integer>possibleMoves = puzzle.findPossibleMoves();
            int move =  possibleMoves.get(rand.nextInt(possibleMoves.size()));
            makeMove(move);
        }

    }
    public ArrayList<Integer> findPossibleMoves()
    {
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        if(x-1>=0)
        {
            possibleMoves.add(up);
        }
        if(x+1<n)
        {
            possibleMoves.add(down);
        }
        if(y-1>=0)
        {
            possibleMoves.add(left);
        }
        if(y+1<n)
        {
            possibleMoves.add(right);
        }
        return possibleMoves;
    }
    public void makeMove(int move)
    {
        int x2 = 0;
        int y2 = 0;
        if(move == up)
        {
            x2 = x-1;
            board[x][y] = board[x2][y];
            board[x2][y] = 0;
            x = x2;
        }
        if(move == down)
        {
            x2 = x+1;
            board[x][y] = board[x2][y];
            board[x2][y] = 0;
            x = x2;
        }
        if(move == left)
        {
            y2 = y-1;
            board[x][y] = board[x][y2];
            board[x][y2] = 0;
            y = y2;
        }
        if(move == right)
        {
            y2 = y+1;
            board[x][y] = board[x][y2];
            board[x][y2] = 0;
            y = y2;
        }
    }
    public String toString()
    {
        StringBuilder puzzleAsString = new StringBuilder();

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                puzzleAsString.append(board[i][j] + " ");
            }
            puzzleAsString.append("\n");
        }
        return puzzleAsString.toString();
    }
    public int hashCode()
    {
        byte[] linear = new byte[n*n];
        byte value = 0;
        for (int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                linear[value++] = board[i][j];
            }
        }
        return Arrays.hashCode(linear);
    }

    @Override
    public List<GraphState> generateChildren()
    {
        ArrayList<GraphState> list = new ArrayList<GraphState>();
        Iterator<Integer> iterator = findPossibleMoves().iterator();
        while(iterator.hasNext())
        {
            Puzzle children = new Puzzle(this);
            children.makeMove(iterator.next());
            String move = "";
            if(x - 1 == children.x)
            {
                move = "UP";
            }
            else if(x + 1 == children.x)
            {
                move = "DOWN";
            }
            else if(y - 1 == children.y)
            {
                move = "LEFT";
            }
            else if(y + 1 == children.y)
            {
                move = "RIGHT";
            }
            children.setMoveName(move);
            list.add(children);
        }
        return list;
    }

    @Override
    public boolean isSolution()
    {
        byte value = 0;
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(board[i][j] != value)
                {
                    return false;
                }
                value++;
            }
        }
        return true;
    }

    static
    {
        setHFunction(new StateFunction());
    }

    public static void main(String[] args)
    {
        for(int i = 0; i<4; i++)
        {
            Puzzle puzzle = new Puzzle();
            puzzle.shuffleBoard(1000, puzzle);
            StateFunction[] heuristics = {new HFunctionMisplacedTiles(), new HFunctionManhattan()};
            for(StateFunction h: heuristics)
            {
                Puzzle.setHFunction(h);
                GraphSearchAlgorithm algorithm = new AStar(puzzle);
                algorithm.execute();
                Puzzle solution = (Puzzle)algorithm.getSolutions().get(0);
                System.out.println("Sliding puzzle: ");
                System.out.println(puzzle.toString());
                System.out.println("Solution:\n" + solution);
                System.out.println("Path length: " + solution.getPath().size());
                System.out.println("Moves:\n" + solution.getMovesAlongPath());
                System.out.println("Closed states: " + algorithm.getClosedStatesCount());
                System.out.println("Open states: " + algorithm.getOpenSet().size());
                System.out.println("Time: " + algorithm.getDurationTime());
                System.out.println("Path cost: "+ solution.getG());
                System.out.println("Heuristic: " + h.getClass());
                System.out.println("\n-------------------------------");
            }
        }

    }
}
