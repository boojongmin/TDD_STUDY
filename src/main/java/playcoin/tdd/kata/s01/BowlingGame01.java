package playcoin.tdd.kata.s01;

public class BowlingGame01 {
	private int rolls[] = new int[21];
	private int currentRoll = 0;

	public void roll(int pins) {
		rolls[currentRoll++] = pins;
	}

	public int score() {
		int score = 0;
		int frameIndex =0;
		for (int frame =0; frame < 10; frame++) {

			if(isStrke(frameIndex)) {
				score += 10 + strikeBonus(frameIndex);
				frameIndex++;
			}
			else if(isSpare(frameIndex)) {
				score = 10 + spareBonus(frameIndex);
				frameIndex += 2;
			}
			else {
				score += sumOfBallsInFrame(frameIndex, frameIndex + 1);
				frameIndex += 2;
			}
		}
		return score;
	}

	private int sumOfBallsInFrame(int frameIndex, int i) {
		return rolls[frameIndex] + rolls[i];
	}

	private int spareBonus(int frameIndex) {
		return rolls[frameIndex + 2];
	}

	private int strikeBonus(int frameIndex) {
		return rolls[frameIndex + 1] + rolls[frameIndex + 2];
	}

	private boolean isStrke(int frameIndex) {
		return rolls[frameIndex] == 10;
	}

	private boolean isSpare(int frameIndex) {
		return rolls[frameIndex] + rolls[frameIndex+1] == 10;
	}
}
