package com.tema1.main;

import com.tema1.Character.BasicMyCharacter;
import com.tema1.Character.BribeMyCharacter;
import com.tema1.Character.GreedyMyCharacter;
import com.tema1.Character.MyCharacter;
import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;

import java.util.*;
import java.util.List;

public class GameLogic {
    /****/
    private Queue<Goods> gameGoods = new LinkedList<>();
    /****/
    private List<Integer> asset = new ArrayList<Integer>();
    /****/
    private List<String> names = new ArrayList<String>();
    /****/
    private ArrayList<MyCharacter> mycharacters = new ArrayList<MyCharacter>(7);
    /****/
    private int[] mygreedy = new int[20];
    private int rounds = Constants.getZeroValue();

    /*** @param mydates*/
    public void PrepareGame(final GameInput mydates) {
        asset = mydates.getAssetIds();
        names = mydates.getPlayerNames();
        int ii = 0;
        MyCharacter aux;
        GoodsFactory myfactory = GoodsFactory.getInstance();
        Map<Integer, Goods> mygoods = new HashMap<Integer, Goods>();
        mygoods = myfactory.getAllGoods();
        for (Integer i: asset) {
            gameGoods.add(mygoods.get(i));
        }
        for (String i: names) {
            if (i.equals("greedy")) {
                aux = new GreedyMyCharacter();
                mycharacters.add(aux);
                mycharacters.get(ii).setIdentifier(ii);
                mygreedy[ii] = 1;
            } else if (i.equals("basic")) {
                aux = new BasicMyCharacter();
                mycharacters.add(aux);
                mycharacters.get(ii).setIdentifier(ii);
            } else if (i.equals("bribed")) {
                aux = new BribeMyCharacter();
                mycharacters.add(aux);
                mycharacters.get(ii).setIdentifier(ii);

            }
            ii++;
        }
        rounds = mydates.getRounds();
    }
    public final void StartGame() {
        MyCharacter mysherrif;
        rounds = rounds * mycharacters.size();
        for (int i = Constants.getZeroValue(); i < rounds; i++) {
            if (i % mycharacters.size() == Constants.getZeroValue()) {
                for (MyCharacter item: mycharacters) {
                    if (mygreedy[item.getIdentifier()] == 1) {
                        int number = item.getRoundnumber() + 1;
                        item.setRoundnumber(number);
                    }
                }
            }
            mysherrif = mycharacters.get(i % mycharacters.size());
            for (MyCharacter item: mycharacters) {
                if (mysherrif.getIdentifier() != item.getIdentifier()) {
                    gameGoods = item.drawgoods(gameGoods);
                    item.playcomerciant();
                }
            }
            gameGoods = mysherrif.playsherif(mycharacters, gameGoods);
        }
    }
    // profitul bunurilor de pe taraba
    public final void computebasic() {
        Map<Goods, Integer> aux = new HashMap<Goods, Integer>();
        for (int i = 0; i < mycharacters.size(); i++) {
            aux = mycharacters.get(i).gettotalcards();
            for (Map.Entry<Goods, Integer> entry: aux.entrySet()) {
                if (entry.getValue() > Constants.getZeroValue()) {
                    mycharacters.get(i).addmoney(entry.getValue() * entry.getKey().getProfit());
                }
            }
        }
    }
    // matricile stocheaza la primul index id-ul cartii , la al doilea id-ul jucatorului
    // ce detine titul iar variabila stocheaza numarul maxim de bunuri din acel id obtinut
    /****/
    public final void computebonuses() {
        int[][] king = new int[Constants.getTwentyFive()][Constants.getHalfUse()];
        int[][] queen = new int[Constants.getTwentyFive()][Constants.getHalfUse()];
        GoodsFactory myfactory = GoodsFactory.getInstance();
        Map<Goods, Integer> aux = new HashMap<Goods, Integer>();
        for (int i = 0; i < mycharacters.size(); i++) {
            aux = mycharacters.get(i).gettotalcards();
            for (Map.Entry<Goods, Integer> entry: aux.entrySet()) {
                try {
                    if (entry.getValue() > king[entry.getKey().getId()][1]) {
                        queen[entry.getKey().getId()][1] = king[entry.getKey().getId()][1];
                        queen[entry.getKey().getId()][0] = king[entry.getKey().getId()][0];
                        king[entry.getKey().getId()][1] = entry.getValue();
                        king[entry.getKey().getId()][0] = mycharacters.get(i).getIdentifier();
                    }
                } catch (NullPointerException NullPtrExc) {

                }
                try {
                    if (entry.getValue() <= king[entry.getKey().getId()][1]
                            && entry.getValue() > queen[entry.getKey().getId()][1]
                            && entry.getValue() > 0
                            && king[entry.getKey().getId()][0]
                            != mycharacters.get(i).getIdentifier()) {
                        queen[entry.getKey().getId()][1] = entry.getValue();
                        queen[entry.getKey().getId()][0] = mycharacters.get(i).getIdentifier();
                    }
                } catch (NullPointerException NullPtrExc) {

                }
            }
        }

        for (int i = Constants.getZeroValue(); i <= Constants.getHandMax(); i++) {
            if (king[i][Constants.getOneValue()] > Constants.getZeroValue()) {
                mycharacters.get(king[i][Constants.getZeroValue()])
                        .addmoney(myfactory.getGoodsById(i).getKingBonus());
            }
            if (queen[i][1] > Constants.getZeroValue()) {
                mycharacters.get(queen[i][Constants.getZeroValue()])
                        .addmoney(myfactory.getGoodsById(i).getQueenBonus());
            }
        }

    }
    // afisare clasament
    public final void displayscores() {
        mycharacters.sort((final MyCharacter card1, final MyCharacter card2) ->
                card2.getMoneySum() - card1.getMoneySum());
        for (MyCharacter i: mycharacters) {
            System.out.println(i.getIdentifier() + " "
                    + names.get(i.getIdentifier()).toUpperCase() + "  " + i.getMoneySum());
        }

    }
}
