package capsules;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Capsules 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        Scanner scan = new Scanner(new File("input"));
        int numTests = scan.nextInt();
        
        for(int i = 0; i < numTests; i++)
        {
            System.out.println(scan.nextInt());
            int[][] board = new int[scan.nextInt()][scan.nextInt()];
            for(int j = 0; j < board.length; j++)
            {
                for(int k = 0; k < board[j].length; k++)
                {
                    try
                    {
                        board[j][k] = Integer.parseInt(scan.next());
                    }
                    catch(NumberFormatException e)
                    {
                        board[j][k] = 0;
                    }
                }
            }
            int[][][] groupings = new int[scan.nextInt()][][];
            for(int j = 0; j < groupings.length; j++)
            {
                groupings[j] = new int[scan.nextInt()][2];
                for(int k = 0; k < groupings[j].length; k++)
                {
                    String s = scan.next();
                    int x = Integer.parseInt(s.substring(1, 2));
                    int y = Integer.parseInt(s.substring(3, 4));
                    groupings[j][k][0] = x - 1;
                    groupings[j][k][1] = y - 1;
                }
            }
            
            if(solve(groupings, board))
            {
                for(int j = 0; j < board.length; j++)
                {
                    for(int k = 0; k < board[j].length; k++)
                    {
                        System.out.print(board[j][k] + " ");
                    }
                    System.out.println();
                }
            }
        }
    }
    
    public static boolean solve(int[][][] groups, int[][] board)
    {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                if(board[i][j] == 0)
                {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty)
            {
                break;
            }
        }
        
        if(isEmpty)
        {
            return true;
        }
        
        int regionNum = -1;
        for(int i = 0; i < groups.length; i++)
        {
            for(int j = 0; j < groups[i].length; j++)
            {
                if (groups[i][j][0] == row && groups[i][j][1] == col)
                {
                    regionNum = i;
                    break;
                }
            }
            if(regionNum != -1)
            {
                break;
            }
        }
        for(int num = 1; num < groups[regionNum].length + 1; num++)
        {
            if(isSafe(board, groups[regionNum], row, col, num))
            {
                board[row][col] = num;
                if(solve(groups, board))
                {
                    return true;
                }
                else
                {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }
    
    public static boolean isSafe(int[][] board, int[][] group, int x, int y, int num)
    {
        boolean isNotInGroup = true;
        for(int i = 0; i < group.length; i++)
        {
            if(!(board[group[i][0]][group[i][1]] != num))
            {
                isNotInGroup = false;
                break;
            }
        }
        
        if (isNotInGroup && board[x][y] == 0 && (x - 1 == -1 || ((y - 1 == -1 || board[x - 1][y - 1] != num) && board[x - 1][y] != num && (y + 1 == board[x - 1].length || board[x - 1][y + 1] != num)))
                && ((y - 1 == -1 || board[x][y - 1] != num) && board[x][y] != num && (y + 1 == board[x].length || board[x][y + 1] != num))
                && (x + 1 == board.length || ((y - 1 == -1 || board[x + 1][y - 1] != num) && board[x + 1][y] != num && (y + 1 == board[x + 1].length || board[x + 1][y + 1] != num)))) {
            return true;
        }
        return false;
    }
}
