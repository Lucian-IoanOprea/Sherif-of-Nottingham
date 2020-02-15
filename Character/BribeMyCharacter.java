package com.tema1.Character;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.Sort;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

// pentru acest jucator se retin jucatorii ce vor fi controlati constant
// anume cel din stanga si din dreapta
public final class BribeMyCharacter extends BasicMyCharacter {
    /****/
    private MyCharacter right = null;
    /****/
    private MyCharacter left = null;
    // se impart cartile dupa legale si ilegale
    // se sorteaza cartile dupa prioritatile din enunt
    // in functie de suma de bani se seteaza mita, ilegalele si legalele ce pot intra
    @Override
    public void playcomerciant() {
        ArrayList<Goods> bribedhand = new ArrayList<>();
        ArrayList<Goods> antiBribeHand = new ArrayList<>();
        GoodsFactory myfactory = GoodsFactory.getInstance();
        Goods todeclare = myfactory.getGoodsById(Constants.getZeroValue());
        int availablesum = super.getMoneySum();
        for (Goods item: super.getgoodsdetained()) {
            if (item.getType() == GoodsType.Illegal) {
                bribedhand.add(item);
            } else {
                antiBribeHand.add(item);
            }
        }
        Collections.sort(bribedhand, new Sort());
        Collections.sort(antiBribeHand, new Sort());
        if (bribedhand.size() == Constants.getZeroValue()
                || super.getMoneySum() <= Constants.getFiveValue()) {
            super.setMita(Constants.getZeroValue());
            super.playnormal();
            if (super.getcharactebag().size() == Constants.getZeroValue()) {
                super.playunnormal();
            }
        } else {
            super.setDeclaration(todeclare);
            int maxilegal = super.getMoneySum() / Constants.getFourValue();
            if (availablesum - Constants.getFourValue() * maxilegal
                    == Constants.getZeroValue()) {
                maxilegal--;
            }

            if (maxilegal > Constants.getHalfUse()
                    && bribedhand.size() > Constants.getHalfUse()
                    && super.getMoneySum() >= Constants.getHandMax()) {
                super.setMita(Constants.getHandMax());
                for (int i = Constants.getZeroValue();
                     i < Math.min(maxilegal, bribedhand.size())
                        && super.getcharactebag().size()
                             < Constants.getMaxBag(); i++) {
                    super.getcharactebag().add(bribedhand.get(i));
                }
                availablesum -= Constants.getFourValue()
                        * Math.min(maxilegal, bribedhand.size());

                int maxlegal = availablesum / Constants.getHalfUse();
                if (availablesum - Constants.getHalfUse() * maxlegal
                        == Constants.getZeroValue()) {
                    maxlegal--;
                }
                for (int i = Constants.getZeroValue();
                     i < Math.min(maxlegal, antiBribeHand.size())
                             && super.getcharactebag().size()
                             < Constants.getMaxBag(); i++) {
                        super.getcharactebag().add(antiBribeHand.get(i));

                }
            } else {
                super.setMita(Constants.getFiveValue());
                for (int i = Constants.getZeroValue();
                     i < Math.min(maxilegal, bribedhand.size())
                        && super.getcharactebag().size()
                             < Constants.getMaxBag(); i++) {
                    super.getcharactebag().add(bribedhand.get(i));
                }
                availablesum -= Constants.getFourValue()
                        * Math.min(maxilegal, bribedhand.size());
                int maxlegal = availablesum / Constants.getHalfUse();
                if (availablesum % Constants.getHalfUse()
                        == Constants.getZeroValue()
                        && availablesum - Constants.getHalfUse() * maxlegal
                        == Constants.getZeroValue()) {
                    maxlegal--;
                }
                for (int i = Constants.getZeroValue();
                     i < Math.min(maxlegal, antiBribeHand.size())
                             && super.getcharactebag().size()
                             < Constants.getMaxBag(); i++) {
                        super.getcharactebag().add(antiBribeHand.get(i));

                }
            }
        }
    }
   // jucatorul verifica jucatorii fixati iar restul isi pun bunurile
    // direct pe taraba
   @Override
    public Queue<Goods> playsherif(
            final ArrayList<MyCharacter> myCharacters,  Queue<Goods> gameGoods) {
        if (right == null) {
            findplayers(myCharacters);
        }
        if (right == left) {
            if (super.getMoneySum() >= Constants.getValueSure()) {
                gameGoods = checkHisGoods(right, gameGoods);
            }
            right.organizebag();
        } else if (right != left) {
            if (super.getMoneySum() >= Constants.getValueSure()) {
                gameGoods = checkHisGoods(right, gameGoods);
                gameGoods = checkHisGoods(left, gameGoods);
            }
            right.organizebag();
            left.organizebag();

        }
        for (MyCharacter item : myCharacters) {
            if (item.getIdentifier() != right.getIdentifier()
                    && item.getIdentifier() != left.getIdentifier()
                    && super.getIdentifier() != item.getIdentifier()) {
                if (item.hasmita()) {
                    super.takeMita(item.getMita());
                }
                item.organizebag();
            }
        }
        return gameGoods;
    }
    // metoda fixeaza vecinii jucatorului
    public void findplayers(final ArrayList<MyCharacter> myCharacters) {
        int index = Constants.getZeroValue();
        int i;
        for (i = Constants.getZeroValue(); i < myCharacters.size(); i++) {
            if (myCharacters.get(i).getIdentifier() == super.getIdentifier()) {
                index = i;
                break;
            }
        }
        if (myCharacters.size() == 2) {
            if (index == Constants.getOneValue()) {
                left = myCharacters.get(Constants.getZeroValue());
                right = myCharacters.get(Constants.getZeroValue());
            } else if (index == Constants.getZeroValue()) {
                left = myCharacters.get(Constants.getOneValue());
                right = myCharacters.get(Constants.getOneValue());
            }
        } else {
            right = myCharacters.get(((index + 1) % myCharacters.size()));
            if (index - 1 == -1) {
                left = myCharacters.get(myCharacters.size() - 1);
            } else {
                left = myCharacters.get(((index - 1) % myCharacters.size()));
            }

        }

    }

}
