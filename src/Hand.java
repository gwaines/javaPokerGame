import java.util.ArrayList;
import java.util.Random;


public class Hand {
	
	public enum Rank {
		HighCard       (1,  "High Card"),
		 Pair          (2,  "Pair"),
		 TwoPair       (3,  "Two Pair"),
		 ThreeOfAKind  (4,  "Three of a Kind"),
		 Straight      (5,  "Straight"),
		 Flush         (6,  "Flush"),
		 FullHouse     (7,  "Full House"),
		 FourOfAKind   (8,  "Four of a kind"),
		 StraightFlush (9,  "Straight Flush");
        
		private int    rank_m;
		private String label_m;
		
		Rank (int rank, String label) {
			this.rank_m = rank;
			this.label_m = label;
		}
		
		public int    rank()  { return rank_m; }
		public String label()  { return label_m; }
	};
	
	public static final int NUM_CARDS = 5;
	
	private ArrayList<Card> cardsInHand;
	private String 			stringRep;
	private Rank 			rank;
	private Card.Value		rankHighCard;
	
	//STRING MUST BE IN ORDER OF INCREASING CARD VALUE
	public Hand(String stringRep) {
		cardsInHand = new ArrayList<Card>();
		
		String[] tokens = stringRep.split("[ ]+");
		for (int i=0; i<=(tokens.length-1); i++) {		      
			Card.Value value = Card.Value.set(tokens[i].charAt(0));
			Card.Suit suit = Card.Suit.set(tokens[i].charAt(1));
			Card c = new Card(value, suit);
			cardsInHand.add(c);
		}
		sort();
		
		this.stringRep = "";
		for(Card c : cardsInHand) {
			this.stringRep = this.stringRep + c.getValue().label() + c.getSuit().label() + " ";
		}
		
		findRank();
	}
	
	
	private void sort() {
		int numCards = cardsInHand.size();
		for (int c=0; c<(numCards-1); c++) {
			for (int k=0; k<(numCards-1-c); k++) {
				if (cardsInHand.get(k).getValueRank() > cardsInHand.get(k+1).getValueRank()) {
					Card highCard = cardsInHand.get(k);
					cardsInHand.remove(k);
					cardsInHand.add(k+1, highCard);
				}
			}
		}
	}
	
	
	public String getStringRep() {
		return stringRep;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Card.Value getRankHighCard() {
		return rankHighCard;
	}
	
	//For now just return an integer value for the rank as outlined in tutorial videos
	public void findRank() {
	
		boolean flush = true;
		boolean straight = true;
				
		int counter = 0;
		Card.Value prevValue = Card.Value.INVALID;
		Card.Suit prevSuit = Card.Suit.INVALID;
		Card.Value highCard = Card.Value.INVALID;
	
		//Determining whether the hand is a straight or flush
		for(Card c : cardsInHand) {
			if (counter == 0) {
				 prevValue = c.getValue();
				 prevSuit = c.getSuit();
			}
			else {
				if(c.getValue().rank() != prevValue.rank() + 1) {
					straight = false;
				}
				if(c.getSuit() != prevSuit) {
					flush = false;
				}
				prevValue = c.getValue();
				prevSuit = c.getSuit();
			}
			highCard = c.getValue();
			counter++;
		}
		
		if(flush == true && straight == true) {
			rank = Rank.StraightFlush;
			rankHighCard = highCard;
			return;
		}
		else if (flush == true && straight == false) {
			rank = Rank.Flush;
			rankHighCard = highCard;
			return;
		}
		else if (flush == false && straight == true) {
			rank = Rank.Straight;
			rankHighCard = highCard;
			return;
		}
		else {
			//n of a kind or high card
			noak();
			return;
		}
		
	}

	private void noak() {
		
		int numberOfPairs = 0;
		int sizeOfPairs[] = new int[2]; //At most two different pairs
		boolean inPair = false;
		
		Card.Value prevValue = Card.Value.INVALID; 
		Card.Value highCard = Card.Value.INVALID;
		Card.Value highCard34oak = Card.Value.INVALID;


		boolean first = true;
		for(Card c : cardsInHand) {
			if(first) {
				prevValue = c.getValue();
				first = false;
			}
			else {
				if(c.getValue() == prevValue && inPair == false ) {
					inPair = true;
					numberOfPairs++;
					sizeOfPairs[numberOfPairs-1] = 2;
				    highCard = c.getValue();
				}
				else if(c.getValue() == prevValue && inPair == true) {
					sizeOfPairs[numberOfPairs-1]++;
					highCard34oak = c.getValue();
				}
				else if(c.getValue() != prevValue && inPair == true) {
					inPair = false;
				}
				else {
					highCard = c.getValue();
				}
				prevValue = c.getValue();
			}
		}
		if(numberOfPairs == 2) {
			if(sizeOfPairs[0] == 3 || sizeOfPairs[1] == 3) {
				rank = Rank.FullHouse;
				rankHighCard = highCard34oak;
				return;
			}
			else {
				rank = Rank.TwoPair;
				rankHighCard = highCard;
				return;
			}
		}
		else if(numberOfPairs == 1) {
			if(sizeOfPairs[0] == 2) {
				rank = Rank.Pair;
				rankHighCard = highCard;
				return;
			}
			else if(sizeOfPairs[0] == 3) {
				rank = Rank.ThreeOfAKind;
				rankHighCard = highCard34oak;
				return;
			}
			else {
				rank = Rank.FourOfAKind;
				rankHighCard = highCard34oak;
				return;
			}
		}
		else {
			rank = rank.HighCard;
			rankHighCard = highCard;
			return;
		}
	}
	
	public static String generate() {
	    String hand = "";
	    for (int i=1; i<=Hand.NUM_CARDS; i++) {
	    	hand = hand + Card.generate() + " ";
	    }
	    return hand;
	}
	
}
