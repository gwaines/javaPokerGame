import java.util.Random;

public class Card {
	
	public enum Value {
		ACE   (1,  'A'),
		 n2   (2,  '2'),
		 n3   (3,  '3'),
		 n4   (4,  '4'),
		 n5   (5,  '5'),
		 n6   (6,  '6'),
		 n7   (7,  '7'),
		 n8   (8,  '8'),
		 n9   (9,  '9'),
		n10   (10, 'T'),
		JACK  (11, 'J'),
		QUEEN (12, 'Q'),
		KING  (13, 'K'),
		INVALID (-1, '?');
		public static final int NUM_VALUES = 13;
        
		// Added a comment
		private int rank_m;
		private char label_m;
		
		Value (int rank, char label) {
			this.rank_m = rank;
			this.label_m = label;
		}
		
		public static Value set(char label) {
			for (Value v : Value.values()) {
				if (label == v.label_m) {
					return v;
				}
			}
			return INVALID;
		}
		
		public static Value random(Random rand) {
		    int randomValue = rand.nextInt(Value.NUM_VALUES) + 1;
		    for (Value v : Value.values()) {
		    	randomValue--;
				if (randomValue == 0) {
					return v;
				}
			}
		    return INVALID;
		}
		
		
		public int  rank()  { return rank_m; }
		public char label() { return label_m; }
	};
	
	
	public enum Suit {
		hearts   ('H'),
		diamonds ('D'),
		spades   ('S'),
		clubs    ('C'),
		INVALID  ('?');
		public static final int NUM_SUITS = 4;

        
		private char label_m;
		
	    Suit (char label) {
			this.label_m = label;
		}
	    
	    public static Suit set (char label) {
			for (Suit s : Suit.values()) {
				if (label == s.label_m) {
					return s;
				}
			}
			return INVALID;
	    }
	    
		public static Suit random(Random rand) {
		    int randomSuit = rand.nextInt(Suit.NUM_SUITS) + 1;
		    for (Suit s : Suit.values()) {
		    	randomSuit--;
				if (randomSuit == 0) {
					return s;
				}
			}
		    return INVALID;
		}
		
		public char label() { return label_m; }
		

	};
	
	public static final int ERROR = -1;
	
	private Value value;
	private Suit suit;

	
	public Card() {
		
	}
	
	public Card(Value value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public int getValueRank() {
		return value.rank();
	}
	
	public static String generate() {
	    Random rand = new Random();

	    Suit  suit  = Card.Suit.random(rand);
	    Value value = Card.Value.random(rand);
	    
	    String card = "" + value.label() + suit.label();
	    return card;
	}
	
}