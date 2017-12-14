
import java.util.Scanner;
import java.io.*;

public class MancalaTester 
{
	public static void main(String[] args) throws IOException
	{
	    //String file = "Mancala1.txt";
        String file = "Mancala2.txt";
		Scanner input = new Scanner(new File(file));
        System.out.println("Reading from " + file);

		Mancala game = new Mancala(14, 4);

		int player = 1;

		int total = 0;



		while(input.hasNext())
		{

            int pit = input.nextInt();
			player = game.move(pit);
			String message = "Player " + player + " moved pit #" + pit + "\t";
			message += game;
			System.out.println(message);
		}
		
		if(game.gameOver())
		{
            String message = "\n" +
                             "Game Over!" + "\n" +
                             "Player " + game.getWinner() + " Wins!";
            System.out.println(message);
        }
	}
}
