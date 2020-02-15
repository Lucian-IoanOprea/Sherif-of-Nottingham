package com.tema1.goods;

import java.util.HashMap;
import java.util.Map;

public final class LegalGoods extends Goods {
	/****/
	private final int kingBonus;
	/****/
	private final int queenBonus;

	public LegalGoods(final int id, final int profit,
					  final int penalty,
					  final int kingBonus,
					  final int queenBonus) {
		super(id, GoodsType.Legal, profit, penalty);
		this.kingBonus = kingBonus;
		this.queenBonus = queenBonus;
	}

	@Override
	public int getKingBonus() {
		return kingBonus;
	}

	@Override
	public int getQueenBonus() {
		return queenBonus;
	}
	@Override
	public Map<Goods, Integer> getIllegalBonus() {
		Map<Goods, Integer> randommap = new HashMap<>();
		return  randommap;
	}
}
