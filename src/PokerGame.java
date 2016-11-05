
import java.util.ArrayList;

public class PokerGame {

	private ArrayList<Hand> handsList;
	
	public PokerGame() {
		handsList = new ArrayList<Hand>();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("NEW GAME:");
		
		//POPULATE pGame's list of hands with some hands
		PokerGame pGame = new PokerGame();

		pGame.addHand(new Hand(Hand.generate()));
		pGame.addHand(new Hand(Hand.generate()));
		pGame.addHand(new Hand(Hand.generate()));
		pGame.addHand(new Hand(Hand.generate()));
		
		ArrayList<Hand> bhs = pGame.findBestHands();
		
		pGame.print(bhs);
	}
	
	public void print(ArrayList<Hand> bhs) {
		int i=0;
		for(Hand h : handsList) {
			i++;
			System.out.format("\tHand %d: %s   (%15.15s - High Card: %c)\n", i, h.getStringRep(), 
					h.getRank().label(), h.getRankHighCard().label());
		}
		
		System.out.println(" ");
		System.out.format("WINNER: %s  (%s - High Card: %c)\n", bhs.get(0).getStringRep(),
					bhs.get(0).getRank().label(), bhs.get(0).getRankHighCard().label());
		for (i=2; i<=bhs.size(); i++) {
			System.out.format("      : %s  (%s - High Card: %c)\n", bhs.get(i-1).getStringRep(),
					bhs.get(i-1).getRank().label(), bhs.get(i-1).getRankHighCard().label());
		}
		
	}
	
	public ArrayList<Hand> findBestHands() {
		ArrayList<Hand> bestHands = new ArrayList<Hand>();
		int currentBestRank = -1; 
		int currentBestRankHighCard = -1;
		int rankOfHand;
		int rankHighCardOfHand;
		for(Hand h : handsList) {
			rankOfHand = h.getRank().rank();
			rankHighCardOfHand = h.getRankHighCard().rank();
			if((rankOfHand > currentBestRank ) ||
			   ((rankOfHand == currentBestRank) && (rankHighCardOfHand > currentBestRankHighCard))) {
				bestHands.removeAll(bestHands);
				bestHands.add(h);
				currentBestRank = rankOfHand;
				currentBestRankHighCard = rankHighCardOfHand;
			}
			else if((rankOfHand == currentBestRank) && (rankHighCardOfHand == currentBestRankHighCard)) {
				bestHands.add(h);
			}
		}
		return bestHands;
	}
	
	public void addHand(Hand h) {
		handsList.add(h);
	}

}
