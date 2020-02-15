package com.tema1.Character;
import com.tema1.common.Constants;
import com.tema1.goods.Goods;

import java.util.ArrayList;
import java.util.Queue;

public final class GreedyMyCharacter extends BasicMyCharacter {
    /****/
    private int roundnumber = Constants.getZeroValue();
    @Override
    public void setRoundnumber(final int a) {
        roundnumber = a;
    }
    /**
     * @return round_number
     */
    public int getRoundnumber() {
        return roundnumber;
    }

    /****/
    public void playcomerciant() {
        super.playcomerciant();
        if ((roundnumber & Constants.getOneValue())
                == Constants.getZeroValue()
                &&
                super.getcharactebag().size() < Constants.getMaxBag()) {
            playunnormal();
        }
    }
  @Override
    public Queue<Goods> playsherif(ArrayList<MyCharacter> myCharacters,
                                    Queue<Goods> gamegoods) {
        for (MyCharacter item: myCharacters) {
            if (item.getIdentifier() != super.getIdentifier()) {
                if (item.hasmita() == false) {
                    if (super.getMoneySum() >= Constants.getValueSure()) {
                        gamegoods = checkHisGoods(item, gamegoods);
                    }
                } else {
                    super.takeMita(item.getMita());
                }
                item.organizebag();
            }
        }
        return gamegoods;
    }

}
