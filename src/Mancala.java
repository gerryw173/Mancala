import java.util.Arrays;

public class Mancala
{
	private int[] board;

	/**
	 * Constructs a Mancala object using the specified
	 * board length and seeds.
	 * 
	 * Each pit will contain the specified number of seeds.
	 * Each mancala will contain 0 seeds.
	 * 
	 * Precondition: boardLength is a even number > 1.
	 * 
	 * @param boardLength total number of pits including mancalas
	 * @param seeds number of seeds to put in the non-mancala pits
	 */
	public Mancala(int boardLength, int seeds)
	{
		board = new int[boardLength];
		for (int i = 0; i < board.length; i++)
		{
			//remember to set mancala to 0
			board[i] = seeds;
		}

		board[0] = 0; // player 1 mancala
		board[board.length / 2] = 0; // player 2 mancala

	}
	
	/**
	 * @param player
	 * @return total number of seeds in the specified
	 * 			player's pits (not counting mancala seeds)
	 */
	public int getSeeds(int player)
	{
		int count = 0;

		if(player == 1)
		{
			for (int i = 1; i < board.length / 2; i++)
			{
				count += board[i];
			}
		}

		else if(player == 2)
		{
			for (int i = board.length / 2; i < board.length; i++)
			{
				count += board[i];
			}
		}

		return count;
	}

	/**
	 * @return board
	 */
	public int[] getBoard()
	{
		return board;
	}

	/**
	 * Simulates a Mancala move by removing all the seeds from the
	 * specified pit and "sowing" them into the subsequent pits.
	 * This method will detect and execute a capture if one exists.
	 * 
	 * Precondition: board[pit] > 0
	 * Precondition: 0 < pit < board.length / 2
	 * 				 board.length / 2 < pit < board.length
	 * 
	 * @param pit
	 * @return 1 if Player 1 should move next
	 * 		   2 if Player 2 should move next
	 */
	public int move(int pit)
	{
		int pitSeeds = board[pit];
		int player = whosePit(pit);  // active player
		int landed = 0; // index where last seed lands

		int count = pitSeeds; //number of seeds being moved

		if(player == 1)
		{
			for (int i = pit + 1; i < board.length; i++)
			{
				board[i]++;
				count--;

				landed = i;

				if(count == 0)
				{
					break;
				}
			}

			while(count > 0) // if seeds need to move beyond board[board.length]
			{
				for (int i = 1; i < board.length; i++)
				{
					board[i]++;
					count--;

					landed = i;

					if(count == 0)
					{
						break;
					}
				}
			}
		}

		else if(player == 2)
		{
			for (int i = pit + 1; i < board.length; i++)
			{
				board[i]++;
				count--;

				landed = i;

				if(count == 0)
				{
					break;
				}
			}

			while(count > 0) // if seeds need to move beyond board[board.length]
			{
				for (int i = 0; i < board.length; i++)
				{
					if(i == board.length / 2)
					{
						i++;
					}
					board[i]++;
					count--;

					landed = i;

					if(count == 0)
					{
						break;
					}
				}
			}
		}


		board[pit] -= pitSeeds; // removes seeds from index player picked up from

		//System.out.println(landed);
		//boardlength - pit

		int total = 0;

		if(whosePit(landed) == player && board[landed] == 1 && board[(board.length) - landed] > 0 && landed != 0 && landed != board.length / 2)
		{

			if(player == 2)
			{
				total += board[landed] + board[(board.length) - landed];

				board[0] += total;
			}

			else
			{
				total += board[landed] + board[(board.length) - landed];
				board[board.length / 2] += total;
			}

			board[landed] = 0;
			board[(board.length - landed)] = 0; //opposite side

			if(player == 1)
			{
				return 2;
			}

			if(player == 2)
			{
				return 1;
			}
		}

		if(pit > board.length / 2 && pit < board.length)
		{
			return 2;
		}

		else if(pit > 0 && pit < board.length / 2)
		{
			return 1;
		}

		return 0;
	}

	/**
	 * Precondition: 0 <= pit < board.length
	 *
	 * @param pit
	 * @return 1 or 2 depending on which player pit belongs to
	 */
	private int whosePit(int pit)
	{
		if(pit > board.length / 2 && pit < board.length)
		{
			return 2;
		}

		else if(pit > 0 && pit < board.length / 2)
		{
			return 1;
		}

		return 0;

	}

	// returns number of seeds in board[index]
	public int indexSeeds(int index)
	{
		return board[index];
	}
	
	/**
	 * @return 1 if Player 1 is the winner,
	 *         2 if Player 2 is the winner,
	 *         0 if the game is not over.
	 */
	public int getWinner()
	{
		if(getSeeds(1) > getSeeds(2))
		{
			return 1;
		}

		else
		{
			return 2;
		}
	}
	
	/**
	 * Returns true if the game is over, false otherwise.
	 * The game is over if a player cannot make a move i.e.
	 * there are no seeds on their side of the board.
	 * 
	 * @return true if the game is over, false otherwise.
	 */
	public boolean gameOver()
	{
		boolean empty1 = true;
		boolean empty2 = true;
		for (int i = 1; i < board.length / 2; i++)
		{
			if(board[i] != 0)
			{
				empty1 = false;
			}
		}

		for (int i = (board.length / 2) + 1 ; i < board.length; i++)
		{
			if(board[i] != 0)
			{
				empty2 = false;
			}
		}

		if(empty1 == true || empty2 == true)
		{
			return true;
		}

		return false;
	}

	/**
	 * @return the contents of board
	 */
	@Override
	public String toString()
	{
		String rtn = "[";

		for (int i = 0; i < board.length; i++)
		{
			if(i == board.length - 1)
			{
				rtn += board[i];
			}
			else
			{
				rtn += board[i] + ", ";
			}
		}

		rtn += "]";

		return rtn;
	}
}