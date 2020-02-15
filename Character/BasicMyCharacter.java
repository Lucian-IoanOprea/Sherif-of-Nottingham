package com.tema1.Character;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.Queue;

public class BasicMyCharacter extends MyCharacter {


    /****/
    // metoda este folosita pentru a ilustra stilul de joc in postura de comerciant
    public void playcomerciant() {
        playnormal();
        GoodsFactory myfactory = GoodsFactory.getInstance();
        Goods aux = myfactory.getGoodsById(Constants.getZeroValue());
        if (super.getcharactebag().isEmpty()) {
            playunnormal();
            super.setDeclaration(aux);
        }
    }
    // metoda auxiliara folosita pentru a cauta cartea legala ce trebuie adaugata
    // de catre jucator in sac
    /****/
    public final void playnormal() {
        int[] frequency = new int[Constants.getTwentyFive()];
        int maxfreq = Constants.getZeroValue();
        int maxprofit = Constants.getZeroValue();
        //   System.out.println(this.getIdentifier()+"da");
        for (Goods item: super.getgoodsdetained()) {
            //   System.out.println(item.getId() + " "+ item.getProfit());
            if (item.getType() == GoodsType.Legal) {
                frequency[item.getId()]++;
                if (frequency[item.getId()] > maxfreq) {
                    super.setDeclaration(item);
                    maxfreq = frequency[item.getId()];
                } else if (frequency[item.getId()] == maxfreq
                        && super.getdeclaration().getProfit()
                                < item.getProfit()) {
                    super.setDeclaration(item);
                    maxprofit = super.getdeclaration().getProfit();
                }

            }
        }
        for (Goods item: super.getgoodsdetained()) {
            if (item.getType() == GoodsType.Legal
                    &&
                    frequency[item.getId()] == maxfreq
                    &&
                    super.getdeclaration().getProfit() == item.getProfit()
                    && item.getId() > super.getdeclaration().getId()) {
                super.setDeclaration(item);
            }
        }
        for (Goods item: super.getgoodsdetained()) {
            try {
                if (item.getId() == super.getdeclaration().getId()
                        &&
                        super.getcharactebag().size() < Constants.getMaxBag()) {
                    super.getcharactebag().add(item);
                }
            } catch (NullPointerException ExtPtr) {

            }
        }
    }

    /****/
    public void playunnormal() {
        int maxprofit = Constants.getZeroValue();
        int idmax = -1;
        for (Goods item: super.getgoodsdetained()) {
            if (maxprofit < item.getProfit()
                    && item.getType() == GoodsType.Illegal
                    && super.getMoneySum() >= Constants.getFourValue()) {
                maxprofit = item.getProfit();
                idmax = item.getId();
            }
        }
        for (Goods item: super.getgoodsdetained()) {
            if (item.getId() == idmax) {
                super.getgoodsdetained().remove(item);
                break;
            }
        }
        if (idmax != -1) {
            GoodsFactory newfactory = GoodsFactory.getInstance();
            Goods aux = newfactory.getGoodsById(0);
            Goods stolenitem = newfactory.getGoodsById(idmax);
            super.getcharactebag().add(stolenitem);
        }
    }
    // ilustreaza postura de serif a jucatorului ce primeste ca parametru
    // jucatorii ce ii controleaza si pachetul de carti( pentru cazul in care se insereaza
    // bunurile confiscate
    @Override
    public Queue<Goods> playsherif(final ArrayList<MyCharacter> myCharacters,
                                   Queue<Goods> gamegoods) {
        for (MyCharacter i: myCharacters) {
            if (i.getIdentifier() != super.getIdentifier()
                    && super.getMoneySum() >= Constants.getValueSure()) {
                gamegoods = checkHisGoods(i, gamegoods);
                i.organizebag();
            }
        }
        return gamegoods;
    }

    @Override
    public void setRoundnumber(final int a) {

    }

    /**
     * @return 0
     */
    public int getRoundnumber() {
        return 0;
    }

}
