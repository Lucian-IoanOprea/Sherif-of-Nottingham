package com.tema1.goods;

import java.util.Map;

public abstract class Goods {
	/****/
	private final int id;
	/****/
	private final GoodsType type;
	/****/
	private final int profit;
	/****/
	private final int penalty;

	/**
	 * @param id
	 * @param type
	 * @param profit
	 * @param penalty
	 */
	public Goods(final int id, final GoodsType type,
				 final int profit, final int penalty) {
		this.id = id;
		this.type = type;
		this.profit = profit;
		this.penalty = penalty;
	}


	/**
	 * @return
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @return type
	 */
	public final GoodsType getType() {
		return type;
	}

	/**
	 * @return profit
	 */
	public final int getProfit() {
		return profit;
	}

	/**
	 * @return penalty
	 */
	public final int getPenalty() {
		return penalty;
	}

	/**
	 * @return 0
	 */
	public int getKingBonus() {
        return 0;
	}

	/**
	 * @return 0
	 */
	public int getQueenBonus() {
          return 0;
	}
   public abstract Map<Goods, Integer> getIllegalBonus();

}
