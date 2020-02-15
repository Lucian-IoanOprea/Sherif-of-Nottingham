package com.tema1.Character;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;



import java.util.*;



public abstract class MyCharacter {
    /****/
    private ArrayList<Goods> goodsDetained;
    /****/
    private List<Goods> characterbag;
    /****/
    private Map<Goods, Integer> totalGoods;
    /****/
    private int moneySum;
    /****/
    private int mita;
    /****/
    private int identifier;
    /****/
    private Goods declaration;

    MyCharacter() {
        moneySum = Constants.getCoinsStart();
        mita = Constants.getZeroValue();
        this.identifier = -1;
        goodsDetained = new ArrayList<>();
        characterbag = new LinkedList<>();
        totalGoods = new HashMap<Goods, Integer>();
        GoodsFactory myfactory = GoodsFactory.getInstance();
        for (int i = Constants.getZeroValue();
            i <= Constants.getMaxIdCard(); i++) {
            totalGoods.put(myfactory.getGoodsById(i), 0);
        }
    }

    /**
     * @return true
     */
    public boolean hasmita() {
        if (mita > Constants.getZeroValue()) {
            return true;
        }
            return false;
    }

    /**
     * @param
     */
    public final void takeMita(final int something) {
        moneySum += something;
    }

    /**
     * @param value
     */
    public final void setIdentifier(final int value) {
        this.identifier = value;
    }
    public final void setDeclaration(final Goods item) {
        this.declaration = item;
    }

    /**
     * @return declaration.getId()
     */
    public final int getDeclarationId() {

        return declaration.getId();
    }
    public final void setMita(final int number) {
        mita = number;
    }
    /**
     * @return identifier
     */
    public final int getIdentifier() {
        return  identifier;
    }
    /**
     * @return goodsDetained
     */
    public final ArrayList<Goods> getgoodsdetained() {
        return goodsDetained;
    }
    /**
     * @return characterbag
     */
    public final List<Goods> getcharactebag() {
        return  characterbag;
    }
    /**
     * @return declaration
     */
    public final Goods getdeclaration() {
        return declaration;
    }
    /**
     * @return totalGoods
     */
    public Map<Goods, Integer> gettotalcards() {
        return totalGoods;
    }
    /**
     * @return moneysum
     */
    public int getMoneySum() {
        return  moneySum;
    }

    /**
     * @return mita
     */
    public int getMita() {
        moneySum -= mita;
        return  mita;
    }

    /**
     *
     * @param sum
     */
    public void addmoney(final int sum) {
        int sumCopy = sum;
        this.moneySum += sumCopy;
    }
    public final  void submoney(final int sum) {
        this.moneySum -= sum;
    }
    public final Queue<Goods> drawgoods(Queue<Goods> gamegoods) {
        int i = Constants.getOneValue();
        if (goodsDetained.size() != Constants.getZeroValue()) {
            goodsDetained.clear();
        }
        for (i = Constants.getOneValue(); i <= Constants.getHandMax(); i++) {
            goodsDetained.add(gamegoods.peek());
            gamegoods.remove();
        }
        return gamegoods;
    }
    // functia utilizata pentru jucatori in postura de serif pentru a vefica
    // bunurile din sac sa corespunda cu cartea declarata
    public final Queue<Goods> checkHisGoods(
            final MyCharacter comerciantul, Queue<Goods> gamegoods) {
        int sum = Constants.getZeroValue();
        int i = Constants.getZeroValue();
       ArrayList<Goods> removal = new ArrayList<Goods>();
       boolean liar = false;
        if (comerciantul.getcharactebag().size() != Constants.getZeroValue()) {
            for (Goods item : comerciantul.getcharactebag()) {
                if ((item.getType() != GoodsType.Legal)
                        || (item != comerciantul.getdeclaration())) {
                    liar = true;
                    sum += item.getPenalty();
                    removal.add(item);
                    gamegoods.add(item);
                }
                i++;
            }
            if (!liar) {
                for (Goods item : comerciantul.getcharactebag()) {
                    sum -= item.getPenalty();
                }
            }
            comerciantul.getcharactebag().removeAll(removal);
            moneySum += sum;
            comerciantul.submoney(sum);
        }
          return  gamegoods;
    }
    // in hashmap vor fi retinute bunurile totale(frecventa lor) analizand si cazul
    // celor ilegale al caror bonus genreaza alte bunurie legale
     public final void organizebag() {
        for (Goods item : characterbag) {
            if (item.getType() == GoodsType.Legal) {
                int number = totalGoods.get(item);
                totalGoods.put(item, number + Constants.getOneValue());
            } else {
                int number2 = totalGoods.get(item);
                totalGoods.put(item, number2 + Constants.getOneValue());
                Map<Goods, Integer> aux = item.getIllegalBonus();
                for (Map.Entry<Goods, Integer> entry : aux.entrySet()) {
                   Goods retain = entry.getKey();
                    int i = Constants.getZeroValue();
                    while( i < entry.getValue()) {
                        int number1 = totalGoods.get(retain);
                        totalGoods.put(retain, number1 + Constants.getOneValue());
                        i++;
                    }
                }
            }
        }
        characterbag.clear();
    }
    public abstract void setRoundnumber(int a);
    /**
     * @return 0
     */
    public abstract int getRoundnumber();

    /****/
    public abstract void playcomerciant();

    public abstract Queue<Goods>
    playsherif(ArrayList<MyCharacter> myCharacters, Queue<Goods> gamegoods);
}
